package kr.codesqaud.cafe.web.service;

import java.util.List;
import java.util.stream.Collectors;
import kr.codesqaud.cafe.domain.article.Article;
import kr.codesqaud.cafe.domain.article.ArticleRepository;
import kr.codesqaud.cafe.domain.user.User;
import kr.codesqaud.cafe.web.dto.article.ArticleResponseDto;
import kr.codesqaud.cafe.web.dto.article.ArticleSavedRequestDto;
import kr.codesqaud.cafe.web.exception.article.ArticleExceptionType;
import kr.codesqaud.cafe.web.exception.article.ArticleNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    private final ArticleRepository repository;
    private final UserService userService;

    public ArticleService(ArticleRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    public ArticleResponseDto write(ArticleSavedRequestDto requestDto) {
        User user = userService.findUser(requestDto.getUserId());
        Article save = repository.save(requestDto.toEntity(user));
        return new ArticleResponseDto(save);
    }

    public List<ArticleResponseDto> getAllArticles() {
        return repository.findAll().stream()
            .map(ArticleResponseDto::new)
            .sorted()
            .collect(Collectors.toUnmodifiableList());
    }

    public ArticleResponseDto findArticle(Long id) {
        Article article = validateExistArticle(id);
        return new ArticleResponseDto(article);
    }

    private Article validateExistArticle(Long id) {
        return repository.findById(id).orElseThrow(() -> {
            throw new ArticleNotFoundException(ArticleExceptionType.NOT_FOUND_USER);
        });
    }
}
