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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        insertNewSubjectButton = findViewById(R.id.addNewSubject);
        dbManager = new DBManager(this.getApplicationContext());
        tableLayout = findViewById(R.id.tableSubjects);
        rows = new ArrayList<>();
        DynamicTable dynamicTable = new DynamicTable(this, tableLayout, this.getApplicationContext());
        dynamicTable.addHeader(headerTable);
        dynamicTable.addData(getData());

        insertNewSubjectButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, InsertSubjectActivity.class);
            this.startActivity(intent);
            finish();
        });
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