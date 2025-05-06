package devlog.backend.application;

import devlog.backend.domain.Article;
import devlog.backend.domain.ArticleNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
class ArticleReader {

    private final ArticleRepository articleRepository;

    Article read(Long articleId) {
        // 게시글 조회
        return articleRepository.findById(articleId).orElseThrow(ArticleNotFound::new);
    }

    List<Article> readAll(Long pageSize, Long lastArticleId) {
        // 게시글 목록 조회
        return lastArticleId == null
            ? articleRepository.findAll(pageSize)
            : articleRepository.findAll(pageSize, lastArticleId);
    }

    List<Article> readAll(Long writerId, Long pageSize, Long lastArticleId) {
        // 특정 작성자의 게시글 목록 조회
        return lastArticleId == null
            ? articleRepository.findAllByWriterId(writerId, pageSize)
            : articleRepository.findAllByWriterId(writerId, pageSize, lastArticleId);
    }

}
