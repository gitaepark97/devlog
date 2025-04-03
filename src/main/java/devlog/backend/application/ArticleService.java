package devlog.backend.application;

import devlog.backend.domain.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ArticleService {

    private final UserReader userReader;
    private final ArticleWriter articleWriter;

    public Article createArticle(Long userId, String title, String content) {
        // 회원 확인
        userReader.checkUserExists(userId);

        // 게시글 생성
        return articleWriter.create(userId, title, content);
    }

}
