package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.controller.dto.ArticleDTO;
import kr.codesqaud.cafe.repository.ArticleRepository;
import kr.codesqaud.cafe.repository.impl.MemoryArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    private ArticleRepository ArticleRepository;

    public ArticleService() {
        this.ArticleRepository = new MemoryArticleRepository();
    }

    public void post(ArticleDTO articleDTO){
        ArticleRepository.save(articleDTO.toArticle());
    }

    public List<ArticleDTO> getArticleList(){
        return ArticleRepository.findAll().stream()
                .map(article -> article.toDTO())
                .collect(Collectors.toUnmodifiableList());
    }

    public ArticleDTO findArticleById(int id){
        return ArticleRepository.findArticleById(id).toDTO();
    }
}
