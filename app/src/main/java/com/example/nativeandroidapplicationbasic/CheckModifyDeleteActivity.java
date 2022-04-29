package com.example.nativeandroidapplicationbasic;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.nativeandroidapplicationbasic.db.DBManager;
import com.example.nativeandroidapplicationbasic.db.Subject;

public class CheckModifyDeleteActivity extends AppCompatActivity {
    //Etiqueta logcat
    private static final String LOG = "CheckModifyDeleteSubjectActivity";
    private EditText inputNameSubject;
    private EditText inputMarkSubject;
    private TextView inputIdSubject;
    private TextView inputDateSubject;
    private DBManager dbManager;
    private Button modifySubjectButton;
    private Button deleteSubjectButton;

    /**
     * Método que se ejecuta al crear la actividad
     * @param savedInstanceState Bundle de los datos guardados de la instancia anterior
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_modify_delete);
        inputNameSubject = findViewById(R.id.nameSubject);
        inputMarkSubject = findViewById(R.id.markSubject);
        inputMarkSubject.setFilters(new InputFilter[]{new DecimalDigitsInputFilter()});
        inputIdSubject = findViewById(R.id.idSubject);
        inputDateSubject = findViewById(R.id.dateLastUpdateText);
        dbManager = new DBManager(this.getApplicationContext());
        modifySubjectButton = findViewById(R.id.modifySubjectButton);
        deleteSubjectButton = findViewById(R.id.deleteSubjectButton);

        /*
         * Obtiene el intent que ha llamado a esta actividad y se obtiene el id del objeto a consultar,
         * modificar o eliminar
         */
        Intent intentSaved = getIntent();
        Bundle bundle = intentSaved.getExtras();
        Subject subject = dbManager.getSubject(bundle.getInt("idSubject"));
        //Se establecen los valores de los campos de texto con los datos del objeto consultado
        inputIdSubject.setText(String.valueOf(subject.getIdSubject()));
        inputNameSubject.setText(subject.getName());
        inputMarkSubject.setText(String.valueOf(subject.getMark()));
        inputDateSubject.setText(subject.getDate());

        //Se establece el listener del botón para modificar el objeto de la asignatura
        modifySubjectButton.setOnClickListener(v -> {
            int idSubject = Integer.parseInt(String.valueOf(inputIdSubject.getText()));
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
                    Subject subjectAux = new Subject();
                    subjectAux.setIdSubject(idSubject);
                    subjectAux.setName(nameSubject);
                    subjectAux.setMark(markSubject);
                    inputNameSubject.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    inputMarkSubject.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    dbManager.updateSubject(subjectAux);
                    Intent intent = new Intent(CheckModifyDeleteActivity.this, MainActivity.class);
                    this.startActivity(intent);
                    finish();
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

        /*
            Establece la estructura para que, al presionar el botón para el eliminar una asignatura,
            la elimine de la base de datos y luego redirige a la página principal
         */
        deleteSubjectButton.setOnClickListener(v ->{
            int idSubject = Integer.parseInt(String.valueOf(inputIdSubject.getText()));
            dbManager.deleteSubject(idSubject);
            Intent intent = new Intent(CheckModifyDeleteActivity.this, MainActivity.class);
            this.startActivity(intent);
            finish();
        });
    }

    /*
        Establece la redirección si se presiona el botón para ir hacia atrás a la actividad principal
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.startActivity(new Intent(CheckModifyDeleteActivity.this, MainActivity.class));
        this.finish();
    }

}