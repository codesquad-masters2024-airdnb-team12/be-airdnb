package com.airbnb.domain.common;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String zipcode;

    @Column(nullable = false)
    private String address;
    private String detailedAddress;

    @Builder
    private Address(String country, String zipcode, String address, String detailedAddress) {
        this.country = country;
        this.zipcode = zipcode;
        this.address = address;
        this.detailedAddress = detailedAddress;
    }
}
