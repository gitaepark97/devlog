package devlog.backend.application;

import devlog.backend.domain.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleReader articleReader;
    private final UserReader userReader;
    private final ArticleWriter articleWriter;

    public Article readArticle(Long articleId) {
        // 게시글 조회
        return articleReader.read(articleId);
    }

    public List<Article> readAllArticles(Long pageSize, Long lastArticleId) {
        // 게시글 조회
        return articleReader.readAll(pageSize, lastArticleId);
    }

    public List<Article> readAllArticles(Long writerId, Long pageSize, Long lastArticleId) {
        // 작성자 확인
        userReader.checkUserExists(writerId);

        // 게시글 조회
        return articleReader.readAll(writerId, pageSize, lastArticleId);
    }

    public Article createArticle(Long userId, String title, String content) {
        // 회원 확인
        userReader.checkUserExists(userId);

        // 게시글 생성
        return articleWriter.create(userId, title, content);
    }

    public Article updateArticle(Long articleId, Long userId, String title, String content) {
        // 게시글 수정
        return articleWriter.update(articleId, userId, title, content);
    }

    public void deleteArticle(Long articleId, Long userId) {
        // 게시글 삭제
        articleWriter.delete(articleId, userId);
    }

}
