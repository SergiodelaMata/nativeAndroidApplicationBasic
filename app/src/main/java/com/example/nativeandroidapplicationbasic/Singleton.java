package com.example.nativeandroidapplicationbasic;

import com.example.nativeandroidapplicationbasic.db.Subject;

import java.util.ArrayList;

public class Singleton {
    private static Singleton instance;
    private int[] headerTableTextSubjects = {R.string.Mark, R.string.Subject};
    private static ArrayList<Subject> listSubjects;

    public static synchronized Singleton getInstance()
    {
        if(instance == null)
        {
            listSubjects = new ArrayList<>();
            instance = new Singleton();
            Subject subject1 = new Subject(1, 8.5, "Metodologías Ágiles", "2021-03-15 12:00");
            listSubjects.add(subject1);
            Subject subject2 = new Subject(2, 9, "Cloud Computing y Contenedores", "2021-03-13 8:00");
            listSubjects.add(subject2);
            Subject subject3 = new Subject(3, 9.4, "Integración Continua en el Desarrollo Ágil", "2021-03-12 10:30");
            listSubjects.add(subject3);
            Subject subject4 = new Subject(4, 9.2, "Frameworks Backend", "2021-03-10 18:00");
            listSubjects.add(subject4);
        }
        return instance;
    }

    public int[] getHeaderTableTextSubjects() {
        return headerTableTextSubjects;
    }

    public void setHeaderTableTextSubjects(int[] headerTableTextSubjects) {
        this.headerTableTextSubjects = headerTableTextSubjects;
    }

    public static ArrayList<Subject> getListSubjects() {
        return listSubjects;
    }

    public static void setListSubjects(ArrayList<Subject> listSubjects) {
        Singleton.listSubjects = listSubjects;
    }
}
