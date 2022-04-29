package com.example.nativeandroidapplicationbasic.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.nativeandroidapplicationbasic.Singleton;

import java.util.ArrayList;

public class DBManager extends SQLiteOpenHelper
{
    //Etiqueta logcat
    private static final String LOG = "DBManager";

    //Información de la base de datos
    private static final String nameDB = "subjects.sqlite";
    private static final int versionDB = 1;

    //Tabla de las asignaturas
    private static final String tableSubject = "Subject";
    private static final String createTableSubjectQuery = "CREATE TABLE " + tableSubject +
            "(idSubject INTEGER PRIMARY KEY AUTOINCREMENT, mark DOUBLE, name TEXT, date DATE);";

    //Estructura de la consulta para eliminar una tabla
    private static final String DROP_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS ";

    //Estructura para realizar una consulta de una tabla completa
    private static final String SELECT_FROM = "SELECT * FROM ";

    //Estructura para realizar una inserción a una tabla
    private static final String INSERT_INTO = "INSERT INTO ";

    public DBManager(Context context) {
        super(context, nameDB, null, versionDB);
    }


    /**
     * Realiza la creación de las tablas de la base de datos en el móvil
     * @param db Base de datos de SQLite
     */
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        try
        {
            Log.i(LOG, "Creando la base de datos de las asignaturas");
            //Se crea la tabla de las asignaturas
            db.execSQL(createTableSubjectQuery);
            insertInitData(db);
        }
        catch(final SQLException e)
        {
            e.printStackTrace();
        }
        catch(RuntimeException e)
        {
            Log.e(LOG, "Error al crear la base de datos: " + e);
        }
        Log.i(LOG, "Base de datos creada");
    }

    /**
     * Realiza la actualización de la base de datos en el dispositivo móvil
     * @param db Base de datos de SQLite
     * @param oldVersion Número de la versión anterior de la base de datos
     * @param newVersion Número de la versión actual de la base de datos
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        try
        {
            Log.i(LOG, "Actualizando la base de datos");
            //Se elimina el contenido de las tablas para volverlas a crear
            db.execSQL(DROP_TABLE_IF_EXISTS + tableSubject);
            onCreate(db);
        }
        catch(final SQLException e)
        {
            e.printStackTrace();
        }
        catch (RuntimeException e)
        {
            Log.i(LOG, "Error al actualizar la base de datos: " + e);
        }
    }

    /**
     * Elimina el contenido de la tabla de las asignaturas
     */
    private void deleteTableSubject()
    {
        try (SQLiteDatabase db = getReadableDatabase()) {
            db.execSQL(DROP_TABLE_IF_EXISTS + tableSubject);
            db.execSQL(createTableSubjectQuery);
        } catch (final SQLException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            Log.i(LOG, "Error al eliminar la tabla: " + e);
        }
    }

    private void insertInitData(SQLiteDatabase pDB)
    {
        SQLiteDatabase db = pDB;
        ArrayList<Subject> subjects = Singleton.getInstance().getListSubjects();
        try
        {
            for(int i = 0; i < subjects.size(); i++)
            {
                Subject subject = subjects.get(i);
                final String sql = INSERT_INTO + tableSubject + "  (mark, name, date) VALUES (?,?,?);";
                SQLiteStatement statement = db.compileStatement(sql);

                statement.bindDouble(1, subject.getMark());
                statement.bindString(2, subject.getName());
                statement.bindString(3, subject.getDate());
                statement.executeInsert();
            }
        }
        catch(final SQLException e)
        {
            e.printStackTrace();
        }
        catch (RuntimeException e)
        {
            Log.e(LOG, "Error al realizar la inserción de datos inicial.");
        }
    }

    /**
     * Realiza la inserción de los datos de una asignatura a la base de datos
     * @param subject Datos de la asignatura
     * @param pDB Base de datos de SQLite
     * @return Valor booleano indicando si se ha introducido o no la asignatura a la base de datos
     */
    public boolean newSubject(Subject subject, SQLiteDatabase pDB)
    {
        boolean verify = false;
        SQLiteDatabase db = pDB;

        try
        {
            //Comprueba si la base datos ya tiene una conexión activa
            if(null == db)
            {
                db = getWritableDatabase();
            }
            final String sql = INSERT_INTO + tableSubject + "  (mark, name, date) VALUES (?,?,?);";
            SQLiteStatement statement = db.compileStatement(sql);

            statement.bindDouble(1, subject.getMark());
            statement.bindString(2, subject.getName());
            statement.bindString(3, subject.getDate());
            statement.executeInsert();
            verify = true;
        }
        catch(final SQLException e)
        {
            e.printStackTrace();
        }
        catch(RuntimeException e)
        {
            Log.e(LOG, "Error al insertar la asignatura = " + e);
            verify = false;
        }
        finally
        {
            if(db != null)
            {
                db.close();
            }
        }
        return verify;
    }

    /**
     * Obtiene el listado de asignaturas disponible en la base de datos
     * @return Listado de asignaturas completo disponible
     */
    public ArrayList<Subject> getListSubjects()
    {
        ArrayList<Subject> listSubjects = new ArrayList<>();
        SQLiteDatabase db = null;
        try
        {
            String selectQuery = SELECT_FROM + tableSubject;
            db = getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if(cursor.moveToFirst())
            {
                /*
                    Se recorre el cursor hasta que no haya más registros de las asignaturas en la base de datos,
                    y se añade a la lista de asignaturas los datos almacenados en la misma
                 */
                do
                {
                    Subject subject = new Subject();
                    subject.setIdSubject(cursor.getInt(cursor.getColumnIndexOrThrow("idSubject")));
                    subject.setMark(cursor.getDouble(cursor.getColumnIndexOrThrow("mark")));
                    subject.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
                    subject.setDate(cursor.getString(cursor.getColumnIndexOrThrow("date")));
                    listSubjects.add(subject);
                }
                while(cursor.moveToNext());
            }
            cursor.close();
        }
        catch(RuntimeException e)
        {
            Log.e(LOG, "Error al obtener el listado de asignaturas: " + e);
            listSubjects = new ArrayList<>();
        }
        finally
        {
            if(null != db)
            {
                db.close();
            }
        }
        return listSubjects;
    }

    /**
     * Obtiene los datos de una asignatura de la base de datos
     * @param idSubject identificador de una asignatura
     * @return Datos del una asignatura
     */
    public Subject getSubject(int idSubject)
    {
        Subject subject = new Subject();
        SQLiteDatabase db = null;
        try
        {
            String selectQuery = SELECT_FROM + tableSubject + " WHERE idSubject = " + idSubject;

            db = getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if(cursor.moveToFirst())
            {
                do
                {
                    subject.setIdSubject(cursor.getInt(cursor.getColumnIndexOrThrow("idSubject")));
                    subject.setMark(cursor.getDouble(cursor.getColumnIndexOrThrow("mark")));
                    subject.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
                    subject.setDate(cursor.getString(cursor.getColumnIndexOrThrow("date")));
                }
                while(cursor.moveToNext());
            }
            cursor.close();
        }
        catch(RuntimeException e)
        {
            Log.e(LOG, "Error al obtener la asignatura: " + e);
            subject = new Subject();
        }
        finally
        {
            if(null != db)
            {
                db.close();
            }
        }
        return subject;
    }

    /**
     * Actualizar los datos de una asignatura de la base de datos
     * @param subject Datos de una asignatura
     */
    public void updateSubject(Subject subject)
    {
        try
        {
            // Se ejecuta la consulta para actualizar los datos de la asignatura a partir del id de la asignatura
            SQLiteDatabase db = getReadableDatabase();
            ContentValues values = new ContentValues();
            values.put("mark", subject.getMark());
            values.put("name", subject.getName());
            values.put("date", subject.getDate());
            db.update(tableSubject, values, "idSubject=" + subject.getIdSubject(), null);
        }
        catch(final NumberFormatException e)
        {
            e.printStackTrace();
        }
        catch(RuntimeException e)
        {
            Log.e(LOG, "Error al actualizar los datos de la asignatura: " + e);
        }
    }

    /**
     * Eliminar una asignatura de la base de datos
     * @param idSubject identificador de una asignatura
     */
    public void deleteSubject(int idSubject)
    {
        try
        {
            // Se ejecuta la consulta para eliminar la asignatura a partir del id de la asignatura
            SQLiteDatabase db = getReadableDatabase();
            db.delete(tableSubject, "idSubject=" + idSubject, null);

        }
        catch(final NumberFormatException e)
        {
            e.printStackTrace();
        }
        catch(RuntimeException e)
        {
            Log.e(LOG, "Error al eliminar una asignatura: " + e);
        }
    }
}
