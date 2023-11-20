package com.example.jpql.ex;

import javax.persistence.Embeddable;

@Embeddable
public class Address {

    private String city;

    private String street;

    private String zipcode;

}
