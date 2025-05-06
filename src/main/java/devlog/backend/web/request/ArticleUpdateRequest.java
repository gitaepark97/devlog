package devlog.backend.web.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ArticleUpdateRequest(
    @NotBlank(message = "제목은 필수값입니다.")
    @Size(max = 50, message = "제목은 최대 50자입니다.")
    String title,

    @NotBlank(message = "내용은 필수값입니다.")
    String content
) {

}
