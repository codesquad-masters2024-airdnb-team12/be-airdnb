package com.airbnb.domain.member.entity.bankAccount;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum BankType {
    KAKAO("카카오뱅크"),
    KB("국민은행"),
    IBK("기업은행"),
    SHINHAN("신한은행"),
    NH("농협은행"),
    KDB("산업은행"),
    WOORI("우리은행"),
    CITY("한국씨티은행"),
    HANA("하나은행"),
    SC("SC제일은행"),
    BNK("부산은행"),
    KFCC("새마을금고"),
    SH("수협은행"),
    KBANK("케이뱅크"),
    TOSS("토스뱅크"),
    ;

    private final String korean;

    BankType(String korean) {
        this.korean = korean;
    }

    public static BankType of(String korean) {
        return Arrays.stream(BankType.values())
            .filter(bankType -> bankType.korean.equals(korean))
            .findAny()
            .orElseThrow();
    }
}
