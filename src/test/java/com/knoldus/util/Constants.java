package com.knoldus.util;

public enum Constants {

    NAME("__naas_smoke_test_client_"), EMP_ID("K123");

    private final String name;

    Constants(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
