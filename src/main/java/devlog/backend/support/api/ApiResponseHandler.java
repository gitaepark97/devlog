package devlog.backend.support.api;

import devlog.backend.support.exception.ApplicationException;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice(basePackages = "devlog.backend.web", basePackageClasses = ApiResponseHandler.class)
@Slf4j
public class ApiResponseHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(
        @Nullable MethodParameter returnType,
        @Nullable Class<? extends HttpMessageConverter<?>> converterType
    ) {
        return MappingJackson2HttpMessageConverter.class.isAssignableFrom(converterType);
    }

    @Override
    public ApiResponse<?> beforeBodyWrite(
        Object body,
        @Nullable MethodParameter returnType,
        @Nullable MediaType selectedContentType,
        @Nullable Class<? extends HttpMessageConverter<?>> selectedConverterType,
        @Nullable ServerHttpRequest request,
        @Nullable ServerHttpResponse response
    ) {
        if (body instanceof ApiResponse<?> apiResponse) {
            response.setStatusCode(apiResponse.status());
            return apiResponse;
        }

        HttpStatus status = extractResponseStatus(returnType);
        response.setStatusCode(status);
        return ApiResponse.of(status, body);
    }

    @ExceptionHandler(Exception.class)
    ApiResponse<?> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return ApiResponse.of(new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다."));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ApiResponse<?> handleException(MethodArgumentNotValidException e) {
        log.warn(e.getMessage(), e);
        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ApiResponse.of(new ApplicationException(HttpStatus.BAD_REQUEST, "잘못된 입력입니다."), errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    ApiResponse<?> handleException(HttpMessageNotReadableException e) {
        log.warn(e.getMessage(), e);
        return ApiResponse.of(new ApplicationException(HttpStatus.BAD_REQUEST, "잘못된 입력입니다."));
    }

    @ExceptionHandler(ApplicationException.class)
    ApiResponse<?> handleApplicationException(ApplicationException e) {
        return ApiResponse.of(e);
    }

    private HttpStatus extractResponseStatus(MethodParameter returnType) {
        ResponseStatus responseStatus = returnType.getMethodAnnotation(ResponseStatus.class);
        return responseStatus == null ? HttpStatus.OK : responseStatus.value();
    }

}
