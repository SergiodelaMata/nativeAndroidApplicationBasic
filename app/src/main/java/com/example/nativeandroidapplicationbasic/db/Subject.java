package com.example.nativeandroidapplicationbasic.db;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Subject {
    private int idSubject;
    private double mark;
    private String name;
    private String date;

    /**
     * Contructor de la clase la asignatura
     */
    public Subject() {
        this.idSubject = 0;
        this.mark = 0;
        this.name = "";
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        this.date = sf.format(date).toString();
    }

    /**
     * Contructor de la clase la asignatura
     * @param idSubject id de la asignatura
     * @param mark nota de la asignatura
     * @param name nombre de la asignatura
     * @param date fecha de la inserción de la nota de la asignatura
     */
    public Subject(int idSubject, double mark, String name, String date) {
        this.idSubject = idSubject;
        this.mark = mark;
        this.name = name;
        this.date = date;
    }

    /**
     * Obtiene el id de la asignatura
     * @return id de la asignatura
     */
    public int getIdSubject() {
        return idSubject;
    }

    /**
     * Introduce el id de la asignatura
     * @param idSubject id de la asignatura
     */
    public void setIdSubject(int idSubject) {
        this.idSubject = idSubject;
    }

    /**
     * Obtiene la nota de la asignatura
     * @return nota de la asignatura
     */
    public double getMark() {
        return mark;
    }

    /**
     * Introduce la nota de la asignatura
     * @param mark nota de la asignatura
     */
    public void setMark(double mark) {
        this.mark = mark;
    }

    /**
     * Obtiene el nombre de la asignatura
     * @return nombre de la asignatura
     */
    public String getName() {
        return name;
    }

    /**
     * Introduce el nombre de la asignatura
     * @param name nombre de la asignatura
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtiene la fecha de la inserción de la nota de la asignatura
     * @return fecha de la inserción de la nota de la asignatura
     */
    public String getDate() {
        return date;
    }

    /**
     * Introduce la fecha de la inserción de la nota de la asignatura
     * @param date fecha de la inserción de la nota de la asignatura
     */
    public void setDate(String date) {
        this.date = date;
    }
}
