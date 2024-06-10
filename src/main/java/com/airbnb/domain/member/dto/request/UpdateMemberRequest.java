package com.airbnb.domain.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class UpdateMemberRequest {

    @NotBlank(message = "사용자 이름을 입력해주세요.")
    @Size(min = 3, max = 20, message = "사용자 이름은 3자 이상 20자 이하이어야 합니다.")
    @Pattern(regexp = "^[a-zA-Z가-힣\\s]{3,20}$", message = "사용자 이름 형식이 올바르지 않습니다.")
    private String name;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하이어야 합니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$",
        message = "비밀번호 형식이 올바르지 않습니다.")
    private String password;
    private String bankName;
    private String accountNumber;
}
