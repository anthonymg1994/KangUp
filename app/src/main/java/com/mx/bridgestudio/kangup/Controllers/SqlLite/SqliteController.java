package com.mx.bridgestudio.kangup.Controllers.SqlLite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.hardware.camera2.params.StreamConfigurationMap;

import com.mx.bridgestudio.kangup.Models.User;

/**
 * Created by USUARIO on 18/11/2016.
 */

public class SqliteController extends SQLiteOpenHelper {

    SQLiteDatabase db;
    private String CrearUsuarios ="CREATE TABLE Usuarios(id INTEGER, nombre TEXT, apellidos TEXT," +
            "telefono TEXT, email TEXT, fecha_nacimiento TEXT, ciudad TEXT, password TEXT, id_forma_pago INTEGER, status TEXT);";

    private User us [];

    public SqliteController(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CrearUsuarios);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public SQLiteDatabase Connect(){
        db = this.getWritableDatabase();
        return db;
    }

    public void Close(){
        if(db != null){
            db.close();
        }
    }

    public User loginUsuario(String user, String pass){
        int id=0;
        String nombre="";
        String apellidos="";
        String email="";
        String password="";
        User u = new User();

        db = getReadableDatabase();
        Cursor c=db.rawQuery("SELECT id,nombre,apellidos,email,password FROM Usuarios WHERE email= '"+ user + "' AND password= '"+ pass +"'" ,null);
        if(c.moveToFirst())
        {
            do{
                id = c.getInt(0);
                nombre = c.getString(1);
                apellidos = c.getString(2);
                email = c.getString(3);
                password = c.getString(4);
            }while(c.moveToNext());
        }
        u.setId(id);
        u.setFirstName(nombre);
        u.setLastName(apellidos);
        u.setEmail(email);
        u.setPassword(password);

        db.close();
        return u;
    }
    public User userById(){
        int idd=0;
        String nombre="";
        String apellidos="";
        String email="";
        String password="";
        User u = new User();

        db = getReadableDatabase();
        Cursor c=db.rawQuery("SELECT id,nombre,apellidos,email,password FROM Usuarios" ,null);
        if(c.moveToFirst())
        {
            do{
                idd = c.getInt(0);
                nombre = c.getString(1);
                apellidos = c.getString(2);
                email = c.getString(3);
                password = c.getString(4);
            }while(c.moveToNext());
        }
        u.setId(idd);
        u.setFirstName(nombre);
        u.setLastName(apellidos);
        u.setEmail(email);
        u.setPassword(password);

        db.close();
        return u;
    }

    public void insertUsuario(User user)
    {
        db = getWritableDatabase();
        try{
            SQLiteStatement stmt = db.compileStatement("INSERT INTO Usuarios (id,nombre,apellidos,telefono,email,fecha_nacimiento,ciudad,password,id_forma_pago,status) "+
                    "VALUES (?,?,?,?,?,?,?,?,?,?)");
            stmt.bindLong(1,user.getId());
            stmt.bindString(2, user.getFirstName());
            stmt.bindString(3, user.getLastName());
            stmt.bindString(4, user.getCellphone());
            stmt.bindString(5, user.getEmail());
            stmt.bindString(6, user.getFnacimiento());
            stmt.bindString(7, user.getCiudad());
            stmt.bindString(8, user.getPassword());
            stmt.bindLong(9, user.getPay());
            stmt.bindString(10, user.getStatus());
            stmt.execute();
        }
        catch (SQLiteConstraintException e){
            System.out.println("Exception SQLite: " + e.getMessage());

        }
        db.close();
    }
}
