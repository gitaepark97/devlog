package devlog.backend.web;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import devlog.backend.application.AuthService;
import devlog.backend.application.TestContainer;
import devlog.backend.support.api.ApiResponseHandler;
import devlog.backend.web.request.LoginRequest;
import devlog.backend.web.request.RegisterRequest;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;

import static devlog.backend.application.fake.FakeTokenProvider.EXAMPLE_TOKEN;
import static devlog.backend.application.fake.FakeUUIDProvider.EXAMPLE_UUID;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(RestDocumentationExtension.class)
class AuthV1ControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private AuthService authService;
    private MockMvc mockMvc;

    @BeforeEach
    void init(RestDocumentationContextProvider provider) {
        objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        TestContainer container = new TestContainer();
        authService = container.getAuthService();
        mockMvc = MockMvcBuilders.standaloneSetup(new AuthV1Controller(authService))
            .setControllerAdvice(new ApiResponseHandler())
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
        // given
        LoginRequest loginRequest = new LoginRequest(
            "test@example.com",
            "Qwer1234!"
        );

        authService.register(loginRequest.email(), loginRequest.password(), "test");

        // when
        ResultActions response = mockMvc.perform(
            post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest))
        );

        // then
        response.andExpect(status().isOk())
            .andExpect(cookie().value("refreshToken", EXAMPLE_UUID))
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value("성공"))
            .andExpect(jsonPath("$.data.token").value(EXAMPLE_TOKEN))
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
        authService.register("test@example.com", "Qwer1234!", "test");
        ResultActions loginResponse = mockMvc.perform(
            post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new LoginRequest("test@example.com", "Qwer1234!")))
        );
        String refreshToken = Objects.requireNonNull(loginResponse.andReturn().getResponse().getCookie("refreshToken"))
            .getValue();

        // when
        ResultActions response = mockMvc.perform(
            post("/api/v1/auth/token")
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(new Cookie("refreshToken", refreshToken))
        );

        // then
        response.andExpect(status().isOk())
            .andExpect(cookie().value("refreshToken", EXAMPLE_UUID))
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value("성공"))
            .andExpect(jsonPath("$.data.token").value(EXAMPLE_TOKEN))
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


}