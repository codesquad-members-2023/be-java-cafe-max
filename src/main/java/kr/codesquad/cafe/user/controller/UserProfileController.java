package kr.codesquad.cafe.user.controller;

import kr.codesquad.cafe.global.PagesInfo;
import kr.codesquad.cafe.post.PostService;
import kr.codesquad.cafe.post.dto.SimplePostForm;
import kr.codesquad.cafe.user.UserService;
import kr.codesquad.cafe.user.annotation.ValidUserIdPath;
import kr.codesquad.cafe.user.domain.User;
import kr.codesquad.cafe.user.dto.ProfileEditForm;
import kr.codesquad.cafe.user.dto.ProfileForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class UserProfileController {
    private static final String USER_ID = "userId";
    private static final String PROFILE_FORM = "profileForm";
    private static final String PROFILE_SETTING_FORM = "profileEditForm";
    private static final String ATTRIBUTE_USER = "user";
    private static final String SIMPLE_FORMS = "simpleForms";
    private static final int DEFAULT_PAGE = 1;
    private static final String PAGES_INFO = "pagesInfo";

    private final UserService userService;
    private final PostService postService;

    public UserProfileController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    /**
     * @param userId AuthBeforeAdvice 에서 접근 권한을 확인하기 위하여 사용합니다.
     */
    @ValidUserIdPath
    @GetMapping("/users/{userId}")
    public String ViewUserPage(Model model, @PathVariable long userId, @SessionAttribute User user) {
        addAttributeForUserPage(model, userId, user, DEFAULT_PAGE);
        return "user/info";
    }

    @ValidUserIdPath
    @GetMapping(value = "/users/{userId}", params = "page")
    public String sendUserOfPostsAjax(Model model, @PathVariable long userId, @SessionAttribute User user, @RequestParam("page") Optional<Integer> page) {
        addAttributeForUserPage(model, userId, user, page.orElse(0));
        return "user/info :: #postsPage";
    }

    private void addAttributeForUserPage(Model model, long userId, User user, Integer currentPage) {
        List<SimplePostForm> simpleForms = postService.getAllSimplePostFormByUser(userId, currentPage);

        PagesInfo pagesInfo = postService.getPagesInfoByUser(currentPage, userId);

        model.addAttribute(PROFILE_FORM, ProfileForm.from(user));
        model.addAttribute(SIMPLE_FORMS, simpleForms);
        model.addAttribute(PAGES_INFO, pagesInfo);
    }


    @ValidUserIdPath
    @GetMapping("/users/{userId}/profile")
    public String viewUser(Model model, @PathVariable long userId, @SessionAttribute User user) {
        model.addAttribute(PROFILE_FORM, ProfileForm.from(user));
        model.addAttribute(USER_ID, userId);
        return "user/profile";
    }

    @ValidUserIdPath
    @GetMapping("/users/{userId}/profile/editForm")
    public String viewUserProfileEditForm(Model model, @PathVariable long userId, @SessionAttribute User user) {
        model.addAttribute(USER_ID, userId);
        model.addAttribute(PROFILE_SETTING_FORM, ProfileEditForm.from(user));
        return "user/profileEditForm";
    }


    /**
     * @param userId AuthBeforeAdvice 에서 접근 권한을 확인하기 위하여 사용합니다.
     */
    @ValidUserIdPath
    @PutMapping("/users/{userId}/profile")
    public String updateUserProfile(@ModelAttribute @Valid ProfileEditForm profileEditForm, BindingResult bindingResult,
                                    @PathVariable long userId, @SessionAttribute User user, HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "user/profileEditForm";
        }
        userService.checkEditInfo(user, profileEditForm);
        User updateUser = userService.update(user, profileEditForm);
        httpSession.setAttribute(ATTRIBUTE_USER, updateUser);
        return "redirect:/users/{userId}/profile";
    }
}
