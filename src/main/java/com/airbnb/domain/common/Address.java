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
    private String district;    // 면, 읍, 구, 군, 시명 (district)

    @Column(nullable = false)
    private String region;      // 시, 도명 (city/province)

    @Column(nullable = false)
    private String address;
    private String detailedAddress;

    @Builder
    private Address(String country, String zipcode, String district, String region, String address, String detailedAddress) {
        this.country = country;
        this.zipcode = zipcode;
        this.district = district;
        this.region = region;
        this.address = address;
        this.detailedAddress = detailedAddress;
    }
}
