package kr.codesqaud.cafe.article;

import java.util.Collections;
import java.util.List;
import kr.codesqaud.cafe.exception.article.ArticleNotFoundException;
import kr.codesqaud.cafe.exception.article.InvalidArticleDeletionException;
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
        return Collections.unmodifiableList(articleRepository.findAll());
    }

    /**
     * @param id Article id
     * @return 게시글
     */
    public Article findOne(long id) {
        Article findArticle = articleRepository.findOneById(id).orElseThrow(ArticleNotFoundException::new);
        if (findArticle.getIsDeleted()) {
            throw new ArticleNotFoundException();
        }
        return findArticle;
    }

    /**
     * @param id Article id
     */
    public long edit(long id, Article article) {
        String originLoginId = articleRepository.findLoginIdOf(id);
        String requesterId = article.getLoginId();

        if (!originLoginId.equals(requesterId)) {
            logger.info("게시글 수정 요청 ID와 기존 게시글 ID 불일치, requesterId: {}, originLoginId: {}", requesterId, originLoginId);
            throw new InvalidRequesterIdException();
        }

        articleRepository.update(id, article);
        logger.info("게시글 수정 성공, 게시글 id: {}", id);
        return id;
    }

    public void delete(long articleId, String requesterId) {
        Article findArticle = articleRepository.findOneById(articleId).orElseThrow(ArticleNotFoundException::new);
        String originLoginId = articleRepository.findLoginIdOf(articleId); // TODO: findOneById 조인 쿼리 수정 필요, 수정 후 값을 getter로 가져올 예정
        boolean hasReply = findArticle.getHasReply();

        if (!originLoginId.equals(requesterId)) {
            logger.info(
                    "게시글 삭제 요청 Id와 기존 게시글 Id 불일치, requesterId: {}, originLoginId: {}", requesterId, originLoginId
            );
            throw new InvalidRequesterIdException();
        }

        if (hasReply) {
            logger.info("게시글에 댓글이 있어 삭제 불가, requesterId: {}, articleId: {}", requesterId, articleId);
            throw new InvalidArticleDeletionException();
        }

        articleRepository.delete(articleId);
        logger.info("게시글 삭제 성공, 게시글 id: {}", articleId);
    }
}
