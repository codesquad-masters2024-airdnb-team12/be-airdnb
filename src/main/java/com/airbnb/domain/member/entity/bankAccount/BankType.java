package com.airbnb.domain.member.entity.bankAccount;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
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

    public static BankType from(String bankType) {
        return Arrays.stream(BankType.values())
            .filter(bt -> bt.getKorean().equals(bankType) || bt.name().equalsIgnoreCase(bankType))
            .findAny()
            .orElseThrow();
    }
}
