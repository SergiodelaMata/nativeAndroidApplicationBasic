package com.example.nativeandroidapplicationbasic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbManager = new DBManager(this.getApplicationContext());
        tableLayout = findViewById(R.id.tableSubjects);
        rows = new ArrayList<>();
        DynamicTable dynamicTable = new DynamicTable(this, tableLayout, this.getApplicationContext());
        dynamicTable.addHeader(headerTable);
        dynamicTable.addData(getData());
    }

    public ArrayList<Subject> getData()
    {
        rows = dbManager.getListSubjects();
        return rows;
    }

    public ArrayList<Subject> getRows() {
        return rows;
    }

    public void setRows(ArrayList<Subject> rows)
    {
        this.rows = rows;
    }

}