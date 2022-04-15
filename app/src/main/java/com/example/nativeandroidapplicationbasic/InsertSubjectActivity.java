package com.example.nativeandroidapplicationbasic;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nativeandroidapplicationbasic.db.DBManager;
import com.example.nativeandroidapplicationbasic.db.Subject;

import java.util.regex.Pattern;

public class InsertSubjectActivity extends AppCompatActivity {
    //Etiqueta logcat
    private static final String LOG = "InsertSubjectActivity";
    private EditText inputNameSubject;
    private EditText inputMarkSubject;
    private DBManager dbManager;
    private Button insertNewSubjectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_subject);
        inputNameSubject = findViewById(R.id.nameSubject);
        inputMarkSubject = findViewById(R.id.markSubject);
        dbManager = new DBManager(this.getApplicationContext());
        insertNewSubjectButton = findViewById(R.id.insertNewSubjectButton);

        /*
            Permite ir observando en cada momento si se está introduciendo correctamente la nota
            de la asignatura para impedir que aparezca un valor para la nota no válido
         */
        inputMarkSubject.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //Ajusta el formato válido para la nota de la asignatura
                inputMarkSubject.setFilters(new InputFilter[]{new DecimalDigitsInputFilter()});
            }
        });

        insertNewSubjectButton.setOnClickListener(v -> {
            String nameSubject = String.valueOf(inputNameSubject.getText());
            String markSubjectText = String.valueOf(inputMarkSubject.getText());
            Double markSubject = 0.0;

            //Comprueba si los campos del nombre y nota de la nueva asignatura han sido rellenados
            if(nameSubject.equals("") || markSubjectText.equals(""))
            {
                /*
                    Comprueba primero si no se han rellenado ninguno de los dos campos para
                    indicárselo al usuario al tratar de introducirlos
                 */
                if(nameSubject.equals("") && markSubjectText.equals(""))
                {
                    new AlertDialog.Builder(this)
                            .setTitle(R.string.AlertNoNameMark)
                            .setMessage(R.string.MessageNoNameMark)
                            .show();
                    inputNameSubject.setBackgroundColor(Color.parseColor("#F44336"));
                    inputMarkSubject.setBackgroundColor(Color.parseColor("#F44336"));
                }
                /*
                    Comprueba primero si no se ha rellenado el campo del nombre para indicárselo al
                    usuario al tratar de introducirlo
                 */
                else if(nameSubject.equals(""))
                {
                    new AlertDialog.Builder(this)
                            .setTitle(R.string.AlertNoName)
                            .setMessage(R.string.MessageNoName)
                            .show();
                    inputNameSubject.setBackgroundColor(Color.parseColor("#F44336"));
                    inputMarkSubject.setBackgroundColor(Color.parseColor("#FFFFFF"));

                }
                /*
                    Comprueba primero si no se ha rellenado el campo de la nota para indicárselo al
                    usuario al tratar de introducirla
                */
                else
                {
                    new AlertDialog.Builder(this)
                            .setTitle(R.string.AlertNoMark)
                            .setMessage(R.string.MessageNoMark)
                            .show();
                    inputNameSubject.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    inputMarkSubject.setBackgroundColor(Color.parseColor("#F44336"));
                }
            }
            else
            {
                markSubject = Double.parseDouble(markSubjectText.trim());
                /*
                    Comprueba que la nota introducida se encuentra entre 0 y 10, ambos incluidos,
                    sino le muestra una alerta
                */
                if(markSubject >= 0 && markSubject <= 10)
                {
                    Subject subject = new Subject();
                    subject.setName(nameSubject);
                    subject.setMark(markSubject);
                    inputNameSubject.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    inputMarkSubject.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    boolean verify = dbManager.newSubject(subject, null);
                    if(verify)
                    {
                        Intent intent = new Intent(InsertSubjectActivity.this, MainActivity.class);
                        this.startActivity(intent);
                        finish();
                    }
                    else
                    {
                        new AlertDialog.Builder(this)
                                .setTitle(R.string.AlertErrorSendInsert)
                                .setMessage(R.string.MessageErrorSendInsert)
                                .show();
                    }
                }
                else
                {
                    new AlertDialog.Builder(this)
                            .setTitle(R.string.AlertNoValidMark)
                            .setMessage(R.string.MessageNoNameMark)
                            .show();
                    inputNameSubject.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    inputMarkSubject.setBackgroundColor(Color.parseColor("#F44336"));
                }
            }
        });
    }

    /*
        Establece la redirección si se presiona el botón para ir hacia atrás a la actividad principal
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.startActivity(new Intent(InsertSubjectActivity.this, MainActivity.class));
        this.finish();
    }

}