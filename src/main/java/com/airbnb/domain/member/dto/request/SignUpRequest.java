package com.airbnb.domain.member.dto.request;

import com.airbnb.domain.common.LoginType;
import com.airbnb.domain.common.Role;
import com.airbnb.domain.member.entity.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class SignUpRequest {

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")
    private String email;

    @NotBlank(message = "사용자 이름을 입력해주세요.")
    @Size(min = 3, max = 20, message = "사용자 이름은 3자 이상 20자 이하이어야 합니다.")
    @Pattern(regexp = "^[a-zA-Z가-힣\\s]{3,20}$", message = "사용자 이름 형식이 올바르지 않습니다.")  // 한글 또는 영문 구성
    private String name;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하이어야 합니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$", // 숫자, 영문, 특수문자 1개 이상 포함
        message = "비밀번호 형식이 올바르지 않습니다.")
    private String password;

    public Member toEntity(PasswordEncoder passwordEncoder) {
        return Member.builder()
            .email(email)
            .loginType(LoginType.DEFAULT) // 일반 email 로그인
            .role(Role.GUEST) // 게스트(default) 권한 부여
            .name(name)
            .encodedPassword(passwordEncoder.encode(password))
            .build();
    }
}