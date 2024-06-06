package com.airbnb.domain.common;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {

    private String country;
    private String zipcode;
    private String address;
    private String detailedAddress;
}
