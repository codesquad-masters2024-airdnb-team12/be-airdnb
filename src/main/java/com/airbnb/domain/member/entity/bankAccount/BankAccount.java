package com.airbnb.domain.member.entity.bankAccount;

import lombok.Getter;

@Getter
public class BankAccount {

    private final BankType type;
    private final String number;

    public BankAccount(String bankName, String number) {
        this.type = BankType.from(bankName);
        this.number = number;
    }
}