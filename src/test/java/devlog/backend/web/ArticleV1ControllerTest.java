package devlog.backend.web;

import devlog.backend.application.ArticleService;
import devlog.backend.domain.Article;
import devlog.backend.web.request.ArticleCreateRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ArticleV1Controller.class)
@ExtendWith(RestDocumentationExtension.class)
class ArticleV1ControllerTest extends TestController {

    @MockitoBean
    private ArticleService articleService;

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

}
