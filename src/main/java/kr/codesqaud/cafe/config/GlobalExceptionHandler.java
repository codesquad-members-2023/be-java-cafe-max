package kr.codesqaud.cafe.config;

import kr.codesqaud.cafe.dto.error.ErrorResponse;
import kr.codesqaud.cafe.exception.common.ApiException;
import kr.codesqaud.cafe.exception.common.BadRequestException;
import kr.codesqaud.cafe.exception.common.NotFoundException;
import kr.codesqaud.cafe.exception.common.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException e) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value());
        e.getFieldErrors()
            .forEach(error -> errorResponse.addMessage(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ResponseBody
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getStatus());
        errorResponse.addMessage("errorMessage", e.getMessage());
        return ResponseEntity.status(e.getStatus())
            .body(errorResponse);
    }

    @ExceptionHandler(BadRequestException.class)
    public String handleBadRequestException(BadRequestException e, Model model) {
        BindingResult bindingResult = new BeanPropertyBindingResult(e.getErrorData(), e.getErrorDataClassName());
        bindingResult.rejectValue(e.getField(), e.getErrorCode());
        model.addAttribute(String.format("org.springframework.validation.BindingResult.%s",
            bindingResult.getObjectName()), bindingResult);
        model.addAttribute(bindingResult.getObjectName(), e.getErrorData());
        return e.getViewName();
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public String handleUnauthorizedException(UnauthorizedException e, Model model) {
        model.addAttribute("status", HttpStatus.UNAUTHORIZED.value());
        model.addAttribute("error", HttpStatus.UNAUTHORIZED.getReasonPhrase());
        model.addAttribute("message", e.getMessage());
        return "error/4xx";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public String handleNotFoundException(NotFoundException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "error/404";
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException() {
        return "error/500";
    }
}
