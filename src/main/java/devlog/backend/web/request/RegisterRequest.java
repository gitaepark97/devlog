package devlog.backend.web.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
    @NotBlank(message = "이메일은 필수값입니다.")
    @Email(message = "이메일 양식이 아닙니다.")
    String email,

    @NotBlank(message = "비밀번호는 필수값입니다.")
    @Pattern(
        regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
        message = "비밀번호는 최소 8자이며, 영문, 숫자, 특수문자(@$!%*?&)를 포함해야 합니다."
    )
    String password,

    @NotBlank(message = "이름은 필수값입니다.")
    @Size(min = 4, max = 20, message = "이름은 최소 4자, 최대 20자입니다.")
    String username
) {

}
