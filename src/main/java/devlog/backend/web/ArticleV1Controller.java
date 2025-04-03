package devlog.backend.web;

import devlog.backend.application.ArticleService;
import devlog.backend.web.request.ArticleCreateRequest;
import devlog.backend.web.response.ArticleResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/articles")
class ArticleV1Controller {

    private final ArticleService articleService;

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

}
