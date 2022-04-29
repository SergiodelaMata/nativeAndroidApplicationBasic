package com.example.nativeandroidapplicationbasic;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.nativeandroidapplicationbasic.db.Subject;

import java.util.ArrayList;

public class DynamicTable {
    //Etiqueta logcat
    private static final String LOG = "DynamicTable";
    private final MainActivity mainActivity;
    private final TableLayout tableLayout;
    private final Context context;
    private int[] header;
    private ArrayList<Subject> data;
    private TableRow tableRow;
    private TextView textCell;

    private int indexCell;
    private int indexRow;

    /**
     * Constructor de la tabla dinámica
     * @param mainActivity Actividad principal
     * @param tableLayout Tabla donde se añadirán los datos
     * @param context Contexto de la aplicación
     */
    public DynamicTable(MainActivity mainActivity, TableLayout tableLayout, Context context) {
        this.mainActivity = mainActivity;
        this.tableLayout = tableLayout;
        this.context = context;
    }

    /**
     * Añade los textos que aparecerán en la cabecera de la tabla
     * @param header Textos que aparecerán como cabeceras
     */
    public void addHeader(int[] header)
    {
        this.header = header;
        createHeader();
    }

    /**
     * Añade los datos de las asignaturas que aparecerán en la tabla
     * @param data Datos de las distintas asignaturas que aparecerán en el cuerpo de la tabla
     */
    public void addData(ArrayList<Subject> data)
    {
        this.data = data;
        createDataTable();
    }

    /**
     * Crea una nueva fila de la tabla
     */
    public void newRow()
    {
        tableRow = new TableRow(context);
        tableRow.setWeightSum(1);
    }

    /**
     * Crea una nueva celda para la tabla
     */
    public void newCellHeader()
    {
        textCell = new TextView(context);
        textCell.setGravity(Gravity.CENTER);
        textCell.setTextSize(22);
        textCell.setTextColor(Color.parseColor("#000000"));
    }

    /**
     * Crea una nueva celda para la tabla
     */
    public void newCell()
    {
        textCell = new TextView(context);
        textCell.setGravity(Gravity.CENTER);
        textCell.setTextSize(22);
        textCell.setTextColor(Color.parseColor("#000000"));
        textCell.setWidth((int) (getScreenWidth() * 0.20));
    }

    /**
     * Obtiene el ancho de la pantalla del dispositivo
     * @return Valor en píxeles del ancho de la pantalla
     */
    public int getScreenWidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        mainActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    /**
     * Crea la cabecera de la tabla
     */
    public void createHeader()
    {
        indexCell = 0;
        newRow();
        while(indexCell < header.length)
        {
            newCellHeader();
            textCell.setText(context.getResources().getString(header[indexCell++]));
            tableRow.addView(textCell, newTableRowParams());
        }
        tableLayout.addView(tableRow);
    }

    /**
     * Crea el cuerpo de la tabla
     */
    private void createDataTable()
    {
        String info;

        // Se crea una fila por cada asignatura
        for(indexRow = 0; indexRow < data.size(); indexRow++)
        {
            newRow();
            // Se crea una celda por cada dato de la asignatura
            for(indexCell = 0; indexCell < header.length; indexCell++)
            {
                newCell();
                Subject subject = data.get(indexRow);
                // Se obtiene el identificador de la celda a partir del identificador de la cabecera para introducir el dato correspondiente en cada celda
                switch(indexCell)
                {
                    //Muestra el campo de la nota de la asignatura
                    case 0:
                        info = String.valueOf(subject.getMark());
                        textCell.setText(info);
                        textCell.setPadding(0, 25, 0, 25);
                        //En caso de que la nota sea menor a 5, la nota aparecerá con fondo rojo
                        if(subject.getMark() < 5)
                        {
                            textCell.setBackground(ContextCompat.getDrawable(mainActivity, R.drawable.rounded_rectangle_red));
                        }
                        //En caso de que la nota sea mayor a 5 y menor que 8.5, la nota aparecerá con fondo amarillo
                        else if(subject.getMark() >= 5 && subject.getMark() <= 8.5)
                        {
                            textCell.setBackground(ContextCompat.getDrawable(mainActivity, R.drawable.rounded_rectangle_yellow));
                        }
                        //En caso de que la nota sea mayor a 8.5, la nota aparecerá con fondo verde
                        else
                        {
                            textCell.setBackground(ContextCompat.getDrawable(mainActivity, R.drawable.rounded_rectangle_green));
                        }
                        tableRow.addView(textCell, newTableRowParams());
                        break;
                    //Muestra el botón con el nombre de la asignatura y el acceso a los datos de la misma
                    case 1:
                        //Se establece la estructura del botón para acceder a los datos de la asignatura en una nueva actividad
                        Button buttonSubject = new Button(context);
                        buttonSubject.setId(subject.getIdSubject());
                        buttonSubject.setText(subject.getName());
                        buttonSubject.setBackgroundColor(Color.parseColor("#FFFFFF"));
                        buttonSubject.setTextColor(Color.parseColor("#000000"));
                        buttonSubject.setBackground(ContextCompat.getDrawable(mainActivity, R.drawable.rounded_rectangle_white));
                        buttonSubject.setTextSize(16);
                        buttonSubject.setGravity(Gravity.CENTER);
                        buttonSubject.setWidth((int) (getScreenWidth() * 0.72));
                        buttonSubject.setAllCaps(false);
                        tableRow.addView(buttonSubject, newTableRowParams());
                        //Se establece el acceso a los datos de la asignatura a través de su botón
                        buttonSubject.setOnClickListener(v -> {
                            Bundle bundle = new Bundle();
                            bundle.putInt("idSubject", subject.getIdSubject());
                            Intent intent = new Intent(mainActivity, CheckModifyDeleteActivity.class);
                            intent.putExtras(bundle);
                            mainActivity.startActivity(intent);
                            mainActivity.finish();
                        });
                        break;
                }
            }
            tableRow.setGravity(Gravity.CENTER_VERTICAL);
            tableLayout.addView(tableRow);
        }
    }

    /**
     * Establece los márgenes de las filas de la tabla
     * @return Ajustes para los márgenes de las filas de la tabla
     */
    private TableRow.LayoutParams newTableRowParams()
    {
        TableRow.LayoutParams params = new TableRow.LayoutParams();
        params.setMargins(1,1,1,1);
        params.weight = 1;
        return params;
    }

    /**
     * Realiza la limpieza de todos campos que había en la tabla
     */
    public void resetTable()
    {
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tableRow.removeAllViews();
                tableLayout.removeAllViewsInLayout();
            }
        });
    }

    /**
     * Introduce a la estructura de la tabla, la cabecera a partir de los datos almacenados en el Singleton
     */
    public void addHeader()
    {
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                addHeader(Singleton.getInstance().getHeaderTableTextSubjects());
            }
        });
    }

    /**
     * Introduce a la estructura de la tabla, los datos de las distintas asignaturas guardadas
     */
    public void addData()
    {
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                data = new ArrayList<>();
                mainActivity.setRows(new ArrayList<>());
                addData(mainActivity.getData());
            }
        });
    }
}
