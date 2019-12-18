package com.blue.json.test;

public enum TestEnum {

    TEST_1(1),
    TEST_2(2),
    TEST_3(3),
    TEST_4(4);

    private Integer value;

    TestEnum(Integer value){
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
