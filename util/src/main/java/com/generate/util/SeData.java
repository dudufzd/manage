package com.generate.util;

import java.io.Serializable;
import java.util.Arrays;

public class SeData implements Serializable {
    private String sex;
    private Integer[] age;
    private String date;

    public SeData() {
    }

    public SeData(String sex, Integer[] age, String date) {
        this.sex = sex;
        this.age = age;
        this.date = date;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer[] getAge() {
        return age;
    }

    public void setAge(Integer[] age) {
        this.age = age;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "SeData{" +
                "sex='" + sex + '\'' +
                ", age=" + Arrays.toString(age) +
                ", date='" + date + '\'' +
                '}';
    }
}
