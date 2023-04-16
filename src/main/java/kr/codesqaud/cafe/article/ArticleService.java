package kr.codesqaud.cafe.article;

import java.util.List;
import java.util.Optional;
import kr.codesqaud.cafe.exception.article.InvalidRequesterIdException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 게시글 관련 서비스 로직을 관리하는 객체
 */
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    /**
     * 게시글 저장
     */
    public Article save(ArticleDTO articleDTO, String writer) {
        // TODO: title/content 비어있는지 등 유효성 검증
        Article article = new Article(writer, articleDTO.getTitle(), articleDTO.getContents());
        return articleRepository.save(article);
    }

    /**
     * 전체 게시글 조회
     * @return 저장소 내 전체 게시글
     */
    public List<Article> findArticles() {
        return articleRepository.findAll();
    }

    /**
     * index 로 특정 게시글 조회
     * @param index 게시글 index
     * @return 게시글
     */
    public Optional<Article> findOne(long index) {
        return articleRepository.findBySequence(index);
    }

    public Article edit(long index, String requesterId, ArticleDTO articleDTO) {
        String originWriter = articleRepository.findIdBySequence(index);
        if (!originWriter.equals(requesterId)) {
            logger.info("게시글 수정 요청 ID와 기존 게시글 ID 불일치");
            throw new InvalidRequesterIdException();
        }
        Article article = new Article(articleDTO.getTitle(), articleDTO.getContents());
        logger.info("게시글 수정 성공");
        return articleRepository.update(index, article);
    }

}
