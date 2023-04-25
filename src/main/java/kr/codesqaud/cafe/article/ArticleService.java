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
    public long save(Article article) {
        // TODO: title/content 비어있는지 등 유효성 검증
        long id = articleRepository.save(article);
        logger.info("게시글 저장 성공, 게시글 id: {}", id);
        return id;
    }

    /**
     * 전체 게시글 조회
     * @return 저장소 내 전체 게시글
     */
    public List<Article> findArticles() {
        return articleRepository.findAll(); // TODO: 불변값으로 수정
    }

    /**
     * @param id Article id
     * @return 게시글
     */
    public Optional<Article> findOne(long id) {
        return articleRepository.findOneById(id);
    }

    /**
     * @param id Article id
     */
    public long edit(long id, Article article) {
        String originWriter = articleRepository.findIdBySequence(id);
        String requesterId = article.getLoginId();

        if (!originWriter.equals(requesterId)) {
            logger.info("게시글 수정 요청 ID와 기존 게시글 ID 불일치, requesterId: {}, originId: {}", requesterId, originWriter);
            throw new InvalidRequesterIdException();
        }

        articleRepository.update(id, article);
        logger.info("게시글 수정 성공, 게시글 id: {}", id);
        return id;
    }

}
