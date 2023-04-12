package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.controller.dto.ArticleDTO;
import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService(final ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void write(final ArticleDTO articleDto) {
        Article article = articleDto.toEntity();
        articleRepository.save(article);
    }

    /*
        todo : 이전 방식은 Article class에 setter를 두어서 기존 객체에 dto 내용을 덮어씌기 하도록 구현.
                현재는 builder 패턴을 사용해서 아예 새로운 객체를 생성.
                새로운 객체를 생성하는 것이 메모리 낭비가 심하지 않은지, entity 개념에 부합한지 모르겠음.
     */
    public void modify(final long id, final ArticleDTO articleDTO) {
        Article originArticle = articleRepository.findById(id).orElse(null);
        Article modifiedArticle = Article.builder(originArticle.getId(), articleDTO.getTitle(), articleDTO.getContent(), originArticle.getAuthor())
                .updatedAt(articleDTO.getUpdatedTime())
                .build();
        articleRepository.update(modifiedArticle);
    }

    public List<ArticleDTO> gatherPosts() {
        return articleRepository.gatherAll().stream()
                .map(ArticleDTO::from)
                .collect(Collectors.toList());
    }

    public ArticleDTO clickOne(final long id) {
        Optional<Article> wantedPost = articleRepository.findById(id);
        return wantedPost.map(ArticleDTO::from).orElse(null);
    }
}
