package com.mx.bridgestudio.kangup.Controllers.SqlLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.hardware.camera2.params.StreamConfigurationMap;

import com.mx.bridgestudio.kangup.Models.Reservacion;
import com.mx.bridgestudio.kangup.Models.User;

/**
 * Created by USUARIO on 18/11/2016.
 */

public class SqliteController extends SQLiteOpenHelper {

    SQLiteDatabase db;
    private String CrearUsuarios ="CREATE TABLE Usuarios(id INTEGER, nombre TEXT, apellidos TEXT," +
            "telefono TEXT, domicilio TEXT,email TEXT, fecha_nacimiento TEXT, ciudad TEXT, password TEXT, id_forma_pago INTEGER, status TEXT);";

    private String Memento ="CREATE TABLE Usuarios(id INTEGER, nombre TEXT, apellidos TEXT," +
            "telefono TEXT, email TEXT, fecha_nacimiento TEXT, ciudad TEXT, password TEXT, id_forma_pago INTEGER, status TEXT);";

    private String reservacion ="CREATE TABLE Reservacion(id INTEGER, fecha TEXT, hora TEXT);";

    private String Destinations ="CREATE TABLE Destinations(id INTEGER PRIMARY KEY, origen TEXT, destino TEXT);";

    private String insertReservation = "INSERT INTO Reservacion(id, fecha, hora) VALUES (1,'dd/mm/yyyy','00:00:00')";


    private User us [];

    public SqliteController(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CrearUsuarios);
        db.execSQL(reservacion);
        db.execSQL(insertReservation);
        db.execSQL(Destinations);
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
    public User user(){
        User u = new User();
        db = getReadableDatabase();
        Cursor c=db.rawQuery("SELECT id,nombre,apellidos,email,password,ciudad,fecha_nacimiento,telefono,domicilio FROM Usuarios" ,null);
        if(c.moveToFirst())
        {
            do{
                u.setId(c.getInt(0));
                u.setFirstName(c.getString(1));
                u.setLastName(c.getString(2));
                u.setEmail(c.getString(3));
                u.setPassword(c.getString(4));
                u.setCiudad(c.getString(5));
                u.setFnacimiento(c.getString(6));
                u.setCellphone(c.getString(7));
                u.setAddress(c.getString(8));
            }while(c.moveToNext());
        }
        db.close();
        return u;
    }

    public void insertUsuario(User user)
    {
        db = getWritableDatabase();
        try{
            SQLiteStatement stmt = db.compileStatement("INSERT INTO Usuarios (id,nombre,apellidos,telefono,domicilio,email,fecha_nacimiento,ciudad,password,id_forma_pago,status) "+
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?)");
            stmt.bindLong(1,user.getId());
            stmt.bindString(2, user.getFirstName());
            stmt.bindString(3, user.getLastName());
            stmt.bindString(4, user.getCellphone());
            stmt.bindString(5, user.getAddress());
            stmt.bindString(6, user.getEmail());
            stmt.bindString(7, user.getFnacimiento());
            stmt.bindString(8, user.getCiudad());
            stmt.bindString(9, user.getPassword());
            stmt.bindLong(10, user.getPay());
            stmt.bindString(11, user.getStatus());
            stmt.execute();
        }
        catch (SQLiteConstraintException e){
            System.out.println("Exception SQLite: " + e.getMessage());

        }
        db.close();
    }

    public void insertRuta(String origen, String destino)
    {
        db = getWritableDatabase();
        try{
            SQLiteStatement stmt = db.compileStatement("INSERT INTO Destinations (id,origen,destino) "+
                    "VALUES (?,?,?)");
            stmt.bindLong(1,1);
            stmt.bindString(2,origen);
            stmt.bindString(3,destino);

            stmt.execute();
        }
        catch (SQLiteConstraintException e){
            System.out.println("Exception SQLite: " + e.getMessage());

        }
        db.close();
    }

    public Reservacion getCurrent(){
        Reservacion u = new Reservacion();
        db = getReadableDatabase();
        Cursor c=db.rawQuery("SELECT id,origen,destino FROM Destinations" ,null);
        if(c.moveToFirst())
        {
            do{
                u.setId(c.getInt(0));
                u.setOrigen(c.getString(1));
                u.setDestination(c.getString(2));
            }while(c.moveToNext());
        }
        db.close();
        return u;
    }


    public void updateReservacionFecha(String fecha)
    {
        db = getWritableDatabase();
        try{
            String actualizar= "Update Reservacion set fecha='"+fecha+"', Where id=1";
            db.execSQL(actualizar);
        }
        catch (SQLiteConstraintException e){
            System.out.println("Exception SQLite: " + e.getMessage());

        }
        db.close();
    }

    public void updateProfile(User user)
    {
        int numberOfRowsAffected = 0;
        db = getWritableDatabase();
        try{
            ContentValues cv = new ContentValues();
            cv.put("nombre",user.getFirstName()); //These Fields should be your String values of actual column names
            cv.put("apellidos",user.getLastName());
            cv.put("telefono",user.getCellphone());
            cv.put("domicilio",user.getAddress());
            cv.put("email",user.getEmail());
            cv.put("fecha_nacimiento",user.getFnacimiento());
            cv.put("ciudad",user.getCiudad());
            cv.put("password",user.getPassword());
            db.update("Usuarios", cv, "id="+user.getId(), null);

            db.close();
        }
        catch (SQLiteConstraintException e){
            System.out.println("Exception SQLite: " + e.getMessage());

        }
    }


    public void updateReservacionHora(String hora)
    {
        db = getWritableDatabase();
        try{
            String actualizar= "Update Reservacion set hora='"+hora+"', Where id=1";
            db.execSQL(actualizar);
        }
        catch (SQLiteConstraintException e){
            System.out.println("Exception SQLite: " + e.getMessage());

        }
        db.close();
    }

    public Reservacion getReservacion(){
        Reservacion r = new Reservacion();
        db = getReadableDatabase();
        Cursor c=db.rawQuery("SELECT fecha,hora FROM Reservacion ",null);
        if(c.moveToFirst())
        {
            do{
                r.setDate(c.getString(1));
                r.setHourI(c.getString(2));
            }while(c.moveToNext());
        }
        db.close();
        return r;
    }

}
