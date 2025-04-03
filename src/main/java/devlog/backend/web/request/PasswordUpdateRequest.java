package devlog.backend.web.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record PasswordUpdateRequest(
    @NotBlank(message = "기존 비밀번호는 필수값입니다.")
    String oldPassword,

    @NotBlank(message = "신규 비밀번호는 필수값입니다.")
    @Pattern(
        regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
        message = "신규 비밀번호는 최소 8자이며, 영문, 숫자, 특수문자(@$!%*?&)를 포함해야 합니다."
    )
    String newPassword
) {

}
