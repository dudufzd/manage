package com.generate.util;

import java.io.Serializable;

public class MyCalendar implements Serializable {
    private Integer date;
    private Integer number;
    private Integer reservations;

    public MyCalendar() {
    }

    public MyCalendar(Integer date, Integer number, Integer reservations) {
        this.date = date;
        this.number = number;
        this.reservations = reservations;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getReservations() {
        return reservations;
    }

    public void setReservations(Integer reservations) {
        this.reservations = reservations;
    }

    @Override
    public String toString() {
        return "Calendar{" +
                "date=" + date +
                ", number=" + number +
                ", reservations=" + reservations +
                '}';
    }
}
