package devlog.backend.application;

import devlog.backend.domain.Article;
import devlog.backend.domain.ArticleNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
class ArticleWriter {

    private final IdProvider idProvider;
    private final ClockProvider clockProvider;
    private final ArticleRepository articleRepository;

    @Transactional
    Article create(Long userId, String title, String content) {
        // 게시글 생성
        Article newArticle = Article.create(idProvider.nextId(), userId, title, content, clockProvider.millis());
        articleRepository.save(newArticle);

        return newArticle;
    }

    @Transactional
    Article update(Long articleId, Long userId, String title, String content) {
        // 게시글 조회
        Article article = read(articleId);

        // 게시글 수정
        Article updatedArticle = article.update(userId, title, content, clockProvider.millis());
        articleRepository.save(updatedArticle);

        return updatedArticle;
    }

    @Transactional
    void delete(Long articleId, Long userId) {
        // 게시글 조회
        Article article = read(articleId);

        // 작성자 확인
        article.checkIsWriter(userId);

        // 게시글 삭제
        articleRepository.deleteById(articleId);
    }

    private Article read(Long articleId) {
        return articleRepository.findById(articleId).orElseThrow(ArticleNotFound::new);
    }

}
