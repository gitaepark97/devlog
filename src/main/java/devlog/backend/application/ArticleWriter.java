package devlog.backend.application;

import devlog.backend.domain.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
class ArticleWriter {

    private final IdProvider idProvider;
    private final ClockProvider clockProvider;
    private final ArticleRepository articleRepository;

    Article create(Long userId, String title, String content) {
        // 게시글 생성
        Article newArticle = Article.create(idProvider.nextId(), userId, title, content, clockProvider.millis());
        articleRepository.save(newArticle);

        return newArticle;
    }

}
