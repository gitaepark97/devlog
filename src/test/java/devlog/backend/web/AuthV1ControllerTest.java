package devlog.backend.web;

import devlog.backend.application.AuthService;
import devlog.backend.application.dto.Token;
import devlog.backend.web.request.LoginRequest;
import devlog.backend.web.request.PasswordUpdateRequest;
import devlog.backend.web.request.RegisterRequest;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthV1Controller.class)
@ExtendWith(RestDocumentationExtension.class)
class AuthV1ControllerTest extends TestController {

    @MockitoBean
    private AuthService authService;

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
        // given
        LoginRequest loginRequest = new LoginRequest(
            "test@example.com",
            "Qwer1234!"
        );
        when(authService.login(anyString(), anyString()))
            .thenReturn(new Token("accessToken", "refreshToken"));

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
        // given
        when(authService.reissueToken(anyString()))
            .thenReturn(new Token("accessToken", "refreshToken"));

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
    void logout() throws Exception {
        // given

        // when
        ResultActions response = mockMvc.perform(
            post("/api/v1/auth/logout")
                .with(authentication(authentication))
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
    void updatePassword() throws Exception {
        // given
        PasswordUpdateRequest passwordUpdateRequest = new PasswordUpdateRequest(
            "Qwer1234!",
            "qWer1234!"
        );

        // when
        ResultActions response = mockMvc.perform(
            put("/api/v1/auth/password")
                .with(authentication(authentication))
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