package com.example.nativeandroidapplicationbasic.db;

import java.util.Date;

public class Subject {
    private int idSubject;
    private double mark;
    private String name;
    private String date;

    public Subject() {
        this.idSubject = 0;
        this.mark = 0;
        this.name = "";
        this.date = new Date().toString();
    }

    public Subject(int idSubject, double mark, String name, String date) {
        this.idSubject = idSubject;
        this.mark = mark;
        this.name = name;
        this.date = date;
    }

    public int getIdSubject() {
        return idSubject;
    }

    public void setIdSubject(int idSubject) {
        this.idSubject = idSubject;
    }

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
