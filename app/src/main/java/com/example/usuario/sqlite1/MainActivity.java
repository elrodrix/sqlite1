package com.example.usuario.sqlite1;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private EditText eDni, eNom, eCol, eMesa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        eDni = (EditText) findViewById(R.id.eDni);
        eNom = (EditText) findViewById(R.id.eNom);
        eCol = (EditText) findViewById(R.id.eCol);
        eMesa = (EditText) findViewById(R.id.eMesa);
    }

    public void alta (View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        String dni = eDni.getText().toString();
        String nombre = eNom.getText().toString();
        String colegio = eCol.getText().toString();
        String mesa = eMesa.getText().toString();

        Cursor fila = db.rawQuery("SELECT * FROM votantes WHERE dni=" + dni, null);

        if (!fila.moveToFirst()){
            ContentValues registro = new ContentValues();

            registro.put("dni", dni);
            registro.put("nombre", nombre);
            registro.put("colegio", colegio);
            registro.put("nmesa", mesa);

            db.insert("votantes", null, registro);
            db.close();
            eDni.setText("");
            eNom.setText("");
            eCol.setText("");
            eMesa.setText("");

            Toast.makeText(this, "Datos guardados correctamente", Toast.LENGTH_SHORT).show();
        }else{
            db.close();
            Toast.makeText(this, "DNI ya existe", Toast.LENGTH_SHORT).show();
        }
    }

    public void consulta (View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        String dni = eDni.getText().toString();

        if (!dni.matches("")){
            //devuelve 0 o 1

            Cursor fila = db.rawQuery("SELECT nombre, colegio, nmesa FROM votantes WHERE dni=" + dni, null);

            if (fila.moveToFirst()){
                eNom.setText(fila.getString(0));
                eCol.setText(fila.getString(1));
                eMesa.setText(fila.getString(2));
            }else{
                Toast.makeText(this, "No existe DNI", Toast.LENGTH_SHORT).show();
            }
            db.close();
        }else{
            Toast.makeText(this, "Debe ingresar DNI", Toast.LENGTH_SHORT).show();
        }
    }


    public void baja (View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        String dni = eDni.getText().toString();

        int cant = db.delete("votantes", "dni=" + dni, null); //Este delete devuelve un entero
        db.close();

        eDni.setText("");
        eNom.setText("");
        eCol.setText("");
        eMesa.setText("");

        if (cant == 1) {
            Toast.makeText(this, "Registro ELIMINADO correctamente", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "No existe DNI",  Toast.LENGTH_SHORT).show();
        }
    }


    public void modificar (View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        String dni = eDni.getText().toString();
        String nombre = eNom.getText().toString();
        String colegio = eCol.getText().toString();
        String mesa = eMesa.getText().toString();

        ContentValues registro = new ContentValues();

        registro.put("nombre", nombre);
        registro.put("colegio", colegio);
        registro.put("nmesa", mesa);

        int cant = db.update("votantes", registro, "dni=" + dni, null);

        if (cant == 1){
            Toast.makeText(this, "Modificacion realizada correctamente", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "No existe DNI", Toast.LENGTH_SHORT).show();
        }
    }


    public void inicio(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        Cursor fila = db.rawQuery("SELECT * FROM votantes ORDER BY dni ASC", null);

        if (fila.moveToFirst()){
            eDni.setText(fila.getString(0));
            eNom.setText(fila.getString(1));
            eCol.setText(fila.getString(2));
            eMesa.setText(fila.getString(3));
        }else{
            Toast.makeText(this, "No hay registros", Toast.LENGTH_SHORT).show();
        }
    }


    public void fin(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        Cursor fila = db.rawQuery("SELECT * FROM votantes ORDER BY dni ASC", null);

        if (fila.moveToLast()){
            eDni.setText(fila.getString(0));
            eNom.setText(fila.getString(1));
            eCol.setText(fila.getString(2));
            eMesa.setText(fila.getString(3));
        }else{
            Toast.makeText(this, "No hay registros", Toast.LENGTH_SHORT).show();
        }
    }


    public void onReset(View view){
        eDni.setText("");
        eNom.setText("");
        eCol.setText("");
        eMesa.setText("");
    }


    public void anterior(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        Cursor fila = db.rawQuery("SELECT * FROM votantes ORDER BY dni ASC", null);
        try{
            if (!fila.isFirst()){
                fila.moveToPrevious();
                eDni.setText(fila.getString(0));
                eNom.setText(fila.getString(1));
                eCol.setText(fila.getString(2));
                eMesa.setText(fila.getString(3));
            }else{
                Toast.makeText(this, "Principio de tabla", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void siguiente(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        Cursor fila = db.rawQuery("SELECT * FROM votantes ORDER BY dni ASC", null);
        try{
            if (!fila.isLast()){
                fila.moveToNext();
                eDni.setText(fila.getString(0));
                eNom.setText(fila.getString(1));
                eCol.setText(fila.getString(2));
                eMesa.setText(fila.getString(3));
            }else{
                Toast.makeText(this, "FINAL de tabla", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
