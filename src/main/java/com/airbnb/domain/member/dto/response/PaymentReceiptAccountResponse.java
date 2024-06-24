package com.airbnb.domain.member.dto.response;

import com.airbnb.domain.member.entity.bankAccount.BankType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentReceiptAccountResponse {

    private String bankType;
    private String accountNumber;

    public static PaymentReceiptAccountResponse of(BankType bankType, String accountNumber) {
        return new PaymentReceiptAccountResponse(bankType.getKorean(), accountNumber);
    }
}
