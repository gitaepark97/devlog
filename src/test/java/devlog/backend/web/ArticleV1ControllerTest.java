package devlog.backend.web;

import devlog.backend.application.ArticleService;
import devlog.backend.domain.Article;
import devlog.backend.web.request.ArticleCreateRequest;
import devlog.backend.web.request.ArticleUpdateRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ArticleV1Controller.class)
@ExtendWith(RestDocumentationExtension.class)
class ArticleV1ControllerTest extends TestController {

    @MockitoBean
    private ArticleService articleService;

    @Test
    void readAllArticles() throws Exception {
        // given
        List<Article> articles = new ArrayList<>();
        for (int i = 10; i > 0; i--) {
            articles.add(
                Article.create(
                    (long) i,
                    (long) i,
                    "title" + i,
                    "content" + i,
                    1735689600000L + i
                )
            );
        }
        when(articleService.readAllArticles(anyLong(), nullable(Long.class)))
            .thenReturn(articles);

        // when
        ResultActions response = mockMvc.perform(
            get("/api/v1/articles")
                .with(authentication(authentication))
                .header("Authorization", "Bearer token")
        );

        // then
        response.andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value("성공"))
            .andExpect(jsonPath("$.data[0].id").value(articles.getFirst().id()))
            .andExpect(jsonPath("$.data[0].writerId").value(articles.getFirst().writerId()))
            .andExpect(jsonPath("$.data[0].title").value(articles.getFirst().title()))
            .andExpect(jsonPath("$.data[0].content").value(articles.getFirst().content()))
            .andExpect(jsonPath("$.data[0].createTime").value(articles.getFirst().createTime()))
            .andExpect(jsonPath("$.data[0].updateTime").value(articles.getFirst().updateTime()))
            .andDo(
                document("read-all-articles",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestHeaders(headerWithName("Authorization").description("Bearer Token")),
                    queryParameters(
                        parameterWithName("pageSize").description("페이지 크기 (default 10)").optional(),
                        parameterWithName("lastArticleId").description("마지막 게시글 ID (optional)").optional()
                    ),
                    responseFields(
                        fieldWithPath("status").description("상태"),
                        fieldWithPath("message").description("메세지"),
                        fieldWithPath("data[].id").description("게시글 ID"),
                        fieldWithPath("data[].writerId").description("작성자 ID"),
                        fieldWithPath("data[].title").description("제목"),
                        fieldWithPath("data[].content").description("내용"),
                        fieldWithPath("data[].createTime").description("생성시간"),
                        fieldWithPath("data[].updateTime").description("수정시간")
                    )
                )
            );
    }

    @Test
    void readUsersAllArticles() throws Exception {
        // given
        List<Article> articles = new ArrayList<>();
        for (int i = 10; i > 0; i--) {
            articles.add(
                Article.create(
                    (long) i,
                    1L,
                    "title" + i,
                    "content" + i,
                    1735689600000L + i
                )
            );
        }
        when(articleService.readAllArticles(anyLong(), anyLong(), nullable(Long.class)))
            .thenReturn(articles);

        // when
        ResultActions response = mockMvc.perform(
            get("/api/v1/articles/users/{userId}", 1L)
                .with(authentication(authentication))
                .header("Authorization", "Bearer token")
        );

        // then
        response.andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value("성공"))
            .andExpect(jsonPath("$.data[0].id").value(articles.getFirst().id()))
            .andExpect(jsonPath("$.data[0].writerId").value(1L))
            .andExpect(jsonPath("$.data[0].title").value(articles.getFirst().title()))
            .andExpect(jsonPath("$.data[0].content").value(articles.getFirst().content()))
            .andExpect(jsonPath("$.data[0].createTime").value(articles.getFirst().createTime()))
            .andExpect(jsonPath("$.data[0].updateTime").value(articles.getFirst().updateTime()))
            .andDo(
                document("read-users-all-articles",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestHeaders(headerWithName("Authorization").description("Bearer Token")),
                    pathParameters(
                        parameterWithName("userId").description("회원 ID")
                    ),
                    queryParameters(
                        parameterWithName("pageSize").description("페이지 크기 (default 10)").optional(),
                        parameterWithName("lastArticleId").description("마지막 게시글 ID (optional)").optional()
                    ),
                    responseFields(
                        fieldWithPath("status").description("상태"),
                        fieldWithPath("message").description("메세지"),
                        fieldWithPath("data[].id").description("게시글 ID"),
                        fieldWithPath("data[].writerId").description("작성자 ID"),
                        fieldWithPath("data[].title").description("제목"),
                        fieldWithPath("data[].content").description("내용"),
                        fieldWithPath("data[].createTime").description("생성시간"),
                        fieldWithPath("data[].updateTime").description("수정시간")
                    )
                )
            );
    }

    @Test
    void createArticle() throws Exception {
        // given
        ArticleCreateRequest articleCreateRequest = new ArticleCreateRequest(
            "title",
            "content"
        );
        Article article = Article.create(
            1L,
            1L,
            articleCreateRequest.title(),
            articleCreateRequest.content(),
            1735689600000L
        );
        when(articleService.createArticle(anyLong(), anyString(), anyString()))
            .thenReturn(article);

        // when
        ResultActions response = mockMvc.perform(
            post("/api/v1/articles")
                .with(authentication(authentication))
                .header("Authorization", "Bearer token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(articleCreateRequest))
        );

        // then
        response.andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value("CREATED"))
            .andExpect(jsonPath("$.message").value("성공"))
            .andExpect(jsonPath("$.data.id").value(article.id()))
            .andExpect(jsonPath("$.data.writerId").value(article.writerId()))
            .andExpect(jsonPath("$.data.title").value(article.title()))
            .andExpect(jsonPath("$.data.content").value(article.content()))
            .andExpect(jsonPath("$.data.createTime").value(article.createTime()))
            .andExpect(jsonPath("$.data.updateTime").value(article.updateTime()))
            .andDo(
                document("create-article",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestHeaders(headerWithName("Authorization").description("Bearer Token")),
                    requestFields(
                        fieldWithPath("title").description("제목"),
                        fieldWithPath("content").description("내용")
                    ),
                    responseFields(
                        fieldWithPath("status").description("상태"),
                        fieldWithPath("message").description("메세지"),
                        fieldWithPath("data.id").description("게시글 ID"),
                        fieldWithPath("data.writerId").description("작성자 ID"),
                        fieldWithPath("data.title").description("제목"),
                        fieldWithPath("data.content").description("내용"),
                        fieldWithPath("data.createTime").description("생성시간"),
                        fieldWithPath("data.updateTime").description("수정시간")
                    )
                )
            );
    }

    @Test
    void updateArticle() throws Exception {
        // given
        ArticleUpdateRequest articleUpdateRequest = new ArticleUpdateRequest(
            "new title",
            "new content"
        );
        Article article = Article.create(
            1L,
            1L,
            "title",
            "content",
            1735689600000L
        ).update(1L, articleUpdateRequest.title(), articleUpdateRequest.content(), 1735689700000L);
        when(articleService.updateArticle(anyLong(), anyLong(), anyString(), anyString()))
            .thenReturn(article);

        // when
        ResultActions response = mockMvc.perform(
            put("/api/v1/articles/{articleId}", article.id())
                .with(authentication(authentication))
                .header("Authorization", "Bearer token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(articleUpdateRequest))
        );

        // then
        response.andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value("성공"))
            .andExpect(jsonPath("$.data.id").value(article.id()))
            .andExpect(jsonPath("$.data.writerId").value(article.writerId()))
            .andExpect(jsonPath("$.data.title").value(article.title()))
            .andExpect(jsonPath("$.data.content").value(article.content()))
            .andExpect(jsonPath("$.data.createTime").value(article.createTime()))
            .andExpect(jsonPath("$.data.updateTime").value(article.updateTime()))
            .andDo(
                document("update-article",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestHeaders(headerWithName("Authorization").description("Bearer Token")),
                    pathParameters(
                        parameterWithName("articleId").description("게시글 ID")
                    ),
                    requestFields(
                        fieldWithPath("title").description("제목"),
                        fieldWithPath("content").description("내용")
                    ),
                    responseFields(
                        fieldWithPath("status").description("상태"),
                        fieldWithPath("message").description("메세지"),
                        fieldWithPath("data.id").description("게시글 ID"),
                        fieldWithPath("data.writerId").description("작성자 ID"),
                        fieldWithPath("data.title").description("제목"),
                        fieldWithPath("data.content").description("내용"),
                        fieldWithPath("data.createTime").description("생성시간"),
                        fieldWithPath("data.updateTime").description("수정시간")
                    )
                )
            );
    }

    @Test
    void deleteArticle() throws Exception {
        // given

        // when
        ResultActions response = mockMvc.perform(
            delete("/api/v1/articles/{articleId}", 1L)
                .with(authentication(authentication))
                .header("Authorization", "Bearer token")
        );

        // then
        response.andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value("성공"))
            .andDo(
                document("delete-article",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestHeaders(headerWithName("Authorization").description("Bearer Token")),
                    pathParameters(
                        parameterWithName("articleId").description("게시글 ID")
                    ),
                    responseFields(
                        fieldWithPath("status").description("상태"),
                        fieldWithPath("message").description("메세지")
                    )
                )
            );
    }

}
