package devlog.backend.web;

import devlog.backend.application.ArticleService;
import devlog.backend.web.request.ArticleCreateRequest;
import devlog.backend.web.request.ArticleUpdateRequest;
import devlog.backend.web.response.ArticleResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/articles")
class ArticleV1Controller {

    private final ArticleService articleService;

    @GetMapping
    List<ArticleResponse> readAllArticles(
        @RequestParam(required = false, defaultValue = "10") Long pageSize,
        @RequestParam(required = false) Long lastArticleId
    ) {
        return articleService.readAllArticles(pageSize, lastArticleId)
            .stream()
            .map(ArticleResponse::from)
            .toList();
    }

    @GetMapping("/users/{userId}")
    List<ArticleResponse> readUsersAllArticles(
        @PathVariable Long userId,
        @RequestParam(required = false, defaultValue = "10") Long pageSize,
        @RequestParam(required = false) Long lastArticleId
    ) {
        return articleService.readAllArticles(userId, pageSize, lastArticleId)
            .stream()
            .map(ArticleResponse::from)
            .toList();
    }

    @GetMapping("/{articleId}")
    ArticleResponse readArticle(@PathVariable Long articleId) {
        return ArticleResponse.from(articleService.readArticle(articleId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ArticleResponse createArticle(
        @AuthenticationPrincipal Long userId,
        @Valid @RequestBody ArticleCreateRequest request
    ) {
        return ArticleResponse.from(
            articleService.createArticle(userId, request.title(), request.content())
        );
    }

    @PutMapping("/{articleId}")
    ArticleResponse updateArticle(
        @PathVariable Long articleId,
        @AuthenticationPrincipal Long userId,
        @Valid @RequestBody ArticleUpdateRequest request
    ) {
        return ArticleResponse.from(
            articleService.updateArticle(articleId, userId, request.title(), request.content())
        );
    }

    @DeleteMapping("/{articleId}")
    void deleteArticle(
        @PathVariable Long articleId,
        @AuthenticationPrincipal Long userId
    ) {
        articleService.deleteArticle(articleId, userId);
    }

}
