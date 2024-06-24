package com.airbnb.domain.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class PaymentReceiptAccountRequest {

    @NotBlank
    private String bankType;

    @Pattern(regexp = "^\\d{10,16}$", message = "계좌번호는 공백 없는 숫자 10-16자리여야 합니다.")
    private String accountNumber;
}
