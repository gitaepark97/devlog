package devlog.backend.web;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import devlog.backend.application.AuthService;
import devlog.backend.application.dto.Token;
import devlog.backend.web.request.LoginRequest;
import devlog.backend.web.request.PasswordUpdateRequest;
import devlog.backend.web.request.RegisterRequest;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthV1Controller.class)
@ExtendWith(RestDocumentationExtension.class)
class AuthV1ControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private WebApplicationContext context;

    @MockitoBean
    private AuthService authService;

    private MockMvc mockMvc;

    @BeforeEach
    void init(RestDocumentationContextProvider provider) {
        objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(documentationConfiguration(provider))
            .build();
    }

    @Test
    void register() throws Exception {
        // given
        RegisterRequest registerRequest = new RegisterRequest(
            "test@example.com",
            "Qwer1234!",
            "test"
        );

        // when
        ResultActions response = mockMvc.perform(
            post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest))
        );

        // then
        response.andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value("성공"))
            .andDo(
                document("register",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestFields(
                        fieldWithPath("email").description("이메일"),
                        fieldWithPath("password").description("비밀번호"),
                        fieldWithPath("username").description("이름")
                    ),
                    responseFields(
                        fieldWithPath("status").description("상태"),
                        fieldWithPath("message").description("메세지")
                    )
                )
            );
    }

    @Test
    void login() throws Exception {
        when(authService.login(anyString(), anyString()))
            .thenReturn(new Token("accessToken", "refreshToken"));

        // given
        LoginRequest loginRequest = new LoginRequest(
            "test@example.com",
            "Qwer1234!"
        );

        // when
        ResultActions response = mockMvc.perform(
            post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest))
        );

        // then
        response.andExpect(status().isOk())
            .andExpect(cookie().value("refreshToken", "refreshToken"))
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value("성공"))
            .andExpect(jsonPath("$.data.token").value("accessToken"))
            .andDo(
                document("login",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestFields(
                        fieldWithPath("email").description("이메일"),
                        fieldWithPath("password").description("비밀번호")
                    ),
                    responseFields(
                        fieldWithPath("status").description("상태"),
                        fieldWithPath("message").description("메세지"),
                        fieldWithPath("data.token").description("토큰")
                    )
                )
            );
    }

    @Test
    void reissueToken() throws Exception {
        when(authService.reissueToken(anyString()))
            .thenReturn(new Token("accessToken", "refreshToken"));

        // given

        // when
        ResultActions response = mockMvc.perform(
            post("/api/v1/auth/token")
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(new Cookie("refreshToken", "refreshToken"))
        );

        // then
        response.andExpect(status().isOk())
            .andExpect(cookie().value("refreshToken", "refreshToken"))
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value("성공"))
            .andExpect(jsonPath("$.data.token").value("accessToken"))
            .andDo(
                document("reissue-token",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    responseFields(
                        fieldWithPath("status").description("상태"),
                        fieldWithPath("message").description("메세지"),
                        fieldWithPath("data.token").description("토큰")
                    )
                )
            );
    }

    @Test
    @WithMockUser(username = "1")
    void logout() throws Exception {
        // given

        // when
        ResultActions response = mockMvc.perform(
            post("/api/v1/auth/logout")
                .header("Authorization", "Bearer token")
        );

        // then
        response.andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value("성공"))
            .andDo(
                document("logout",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestHeaders(headerWithName("Authorization").description("Bearer Token")),
                    responseFields(
                        fieldWithPath("status").description("상태"),
                        fieldWithPath("message").description("메세지")
                    )
                )
            );
    }

    @Test
    @WithMockUser(username = "1")
    void updatePassword() throws Exception {
        // given
        PasswordUpdateRequest passwordUpdateRequest = new PasswordUpdateRequest(
            "Qwer1234!",
            "qWer1234!"
        );

        // when
        ResultActions response = mockMvc.perform(
            put("/api/v1/auth/password")
                .header("Authorization", "Bearer token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(passwordUpdateRequest))
        );

        // then
        response.andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value("성공"))
            .andDo(
                document("update-password",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestHeaders(headerWithName("Authorization").description("Bearer Token")),
                    requestFields(
                        fieldWithPath("oldPassword").description("기존 비밀번호"),
                        fieldWithPath("newPassword").description("신규 비밀번호")
                    ),
                    responseFields(
                        fieldWithPath("status").description("상태"),
                        fieldWithPath("message").description("메세지")
                    )
                )
            );
    }


}