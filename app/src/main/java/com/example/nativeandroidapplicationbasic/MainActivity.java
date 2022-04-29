package com.example.nativeandroidapplicationbasic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;

import com.example.nativeandroidapplicationbasic.db.DBManager;
import com.example.nativeandroidapplicationbasic.db.Subject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //Etiqueta logcat
    private static final String LOG = "MainActivity";
    private TableLayout tableLayout;
    private int[] headerTable = Singleton.getInstance().getHeaderTableTextSubjects();
    private ArrayList<Subject> rows;
    private DBManager dbManager;
    private Button insertNewSubjectButton;

    /**
     * Método que se ejecuta al crear la actividad
     * @param savedInstanceState Bundle de los datos guardados de la instancia anterior
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        insertNewSubjectButton = findViewById(R.id.addNewSubject);
        dbManager = new DBManager(this.getApplicationContext());
        tableLayout = findViewById(R.id.tableSubjects);
        rows = new ArrayList<>();
        //Se elabora la estructura de la tabla con las cabeceras y los datos de la base de datos de las asignaturas
        DynamicTable dynamicTable = new DynamicTable(this, tableLayout, this.getApplicationContext());
        dynamicTable.addHeader(headerTable);
        dynamicTable.addData(getData());

        /*
            Se crea el listener para el botón de añadir una nueva asignatura para acceder a la actividad centrada
            en la que se puede añadir una nueva asignatura
         */
        insertNewSubjectButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, InsertSubjectActivity.class);
            this.startActivity(intent);
            finish();
        });
    }

    /**
     * Obtiene los datos de la base de datos de las asignaturas
     * @return ArrayList de las asignaturas con los datos de la base de datos
     */
    public ArrayList<Subject> getData()
    {
        rows = dbManager.getListSubjects();
        return rows;
    }

    /**
     * Obtiene el listado de filas de las asignaturas que se dispone para introducir en la tabla
     * @return listado de filas de las asignaturas que se dispone para introducir en la tabla
     */
    public ArrayList<Subject> getRows() {
        return rows;
    }

    /**
     * Inserta el listado de filas de las asignaturas que se dispondrá en la tabla
     * @param rows listado de las asignaturas que se dispondrá en la tabla
     */
    public void setRows(ArrayList<Subject> rows)
    {
        this.rows = rows;
    }

}