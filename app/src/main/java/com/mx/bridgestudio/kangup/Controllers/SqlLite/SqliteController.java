package com.mx.bridgestudio.kangup.Controllers.SqlLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.mx.bridgestudio.kangup.Models.Lists.ListRoutes;
import com.mx.bridgestudio.kangup.Models.Reservacion;
import com.mx.bridgestudio.kangup.Models.User;

/**
 * Created by USUARIO on 18/11/2016.
 */

public class SqliteController extends SQLiteOpenHelper {

    Context context;
    SQLiteDatabase db;
    private String CrearUsuarios ="CREATE TABLE Usuarios(id INTEGER, nombre TEXT, apellido_paterno TEXT, apellido_materno TEXT," +
            "telefono TEXT, email TEXT, fecha_nacimiento TEXT, ciudad TEXT, password TEXT, id_forma_pago INTEGER, status TEXT, foto TEXT);";

    private String reservacion ="CREATE TABLE Reservacion(id INTEGER PRIMARY KEY, fecha CURRENT_TIMESTAMP , hora_inicio CURRENT_TIMESTAMP, hora_termino CURRENT_TIMESTAMP );";

    //  private String insertReservation = "INSERT INTO Reservacion(id, fecha, hora) VALUES (1,'dd/mm/yyyy','00:00:00')";

    private String packageTable="CREATE TABLE Package(id INTEGER);";

    private String resNextTable="CREATE TABLE NextRes(id INTEGER, id_reservacion INTEGER);";
    private String insertResNext = "INSERT INTO NextRes(id,id_reservacion) VALUES (1,1)";

    private String routesTable="CREATE TABLE Routes(id INTEGER PRIMARY KEY AUTOINCREMENT,origen TEXT,destino TEXT,id_reservacion INTEGER,posicion INTEGER);";

    private User us [];

    public SqliteController(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CrearUsuarios);
        db.execSQL(reservacion);
        //db.execSQL(insertReservation);
        db.execSQL(packageTable);
        db.execSQL(resNextTable);
        db.execSQL(insertResNext);
        db.execSQL(routesTable);
        //   db.execSQL(insertReservation);
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
        String apellido_pat="";
        String apellido_mat="";
        String email="";
        String password="";
        String foto ="";
        User u = new User();

        db = getReadableDatabase();
        Cursor c=db.rawQuery("SELECT id,nombre,apellido_paterno, apellido_materno,email,password,foto FROM Usuarios WHERE email= '"+ user + "' AND password= '"+ pass +"'" ,null);
        if(c.moveToFirst())
        {
            do{
                id = c.getInt(0);
                nombre = c.getString(1);
                apellido_pat = c.getString(2);
                apellido_mat = c.getString(3);
                email = c.getString(4);
                password = c.getString(5);
                foto = c.getString(6);
            }while(c.moveToNext());
        }
        u.setId(id);
        u.setFirstName(nombre);
        u.setAp_paterno(apellido_pat);
        u.setAp_materno(apellido_mat);
        u.setEmail(email);
        u.setPassword(password);
        u.setPhoto(foto);

        db.close();
        return u;
    }
    public User user(){
        User u = new User();
        db = getReadableDatabase();
        Cursor c=db.rawQuery("SELECT id,nombre,apellido_paterno,apellido_materno,email,password,ciudad,fecha_nacimiento,telefono,foto FROM Usuarios" ,null);
        if(c.moveToFirst())
        {
            do{
                u.setId(c.getInt(0));
                u.setFirstName(c.getString(1));
                u.setAp_paterno(c.getString(2));
                u.setAp_materno(c.getString(3));
                u.setEmail(c.getString(4));
                u.setPassword(c.getString(5));
                u.setCiudad(c.getString(6));
                u.setFnacimiento(c.getString(7));
                u.setCellphone(c.getString(8));
                u.setPhoto(c.getString(9));
            }while(c.moveToNext());
        }
        db.close();
        return u;
    }

    public void insertUsuario(User user)
    {
        db = getWritableDatabase();
        try{
            SQLiteStatement stmt = db.compileStatement("INSERT INTO Usuarios (id,nombre,apellido_paterno,apellido_materno,telefono,email,fecha_nacimiento,ciudad,password,id_forma_pago,status,foto) "+
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
            stmt.bindLong(1,user.getId());
            stmt.bindString(2, user.getFirstName());
            stmt.bindString(3, user.getAp_paterno());
            stmt.bindString(4, user.getAp_materno());
            stmt.bindString(5, user.getCellphone());
            stmt.bindString(6, user.getEmail());
            stmt.bindString(7, user.getFnacimiento());
            stmt.bindString(8, user.getCiudad());
            stmt.bindString(9, user.getPassword());
            stmt.bindLong(10, user.getPay());
            stmt.bindString(11, user.getStatus());
            stmt.bindString(12, user.getPhoto());
            stmt.execute();
        }
        catch (SQLiteConstraintException e){
            System.out.println("Exception SQLite: " + e.getMessage());

        }
        db.close();
    }

    public void updateReservacionFecha(String fecha)
    {
        db = getWritableDatabase();
        try{
            String actualizar= "Update Reservacion set fecha='"+fecha+"' Where id=1";
            db.execSQL(actualizar);
        }
        catch (SQLiteConstraintException e){
            System.out.println("Exception SQLite: " + e.getMessage());

        }
        db.close();
    }

    public void updateReservacionHora(String hora)
    {
        db = getWritableDatabase();
        try{
            String actualizar= "Update Reservacion set hora='"+hora+"' Where id=1";
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

    public void insertRutas(String origen, String destino, int id_reservacion, int posicion)
    {
        db = getWritableDatabase();
        try{
            SQLiteStatement stmt = db.compileStatement("INSERT INTO Routes (origen,destino,id_reservacion,posicion) "+
                    "VALUES (?,?,?,?)");
            stmt.bindString(1,origen);
            stmt.bindString(2,destino);
            stmt.bindLong(3,id_reservacion);
            stmt.bindLong(4,posicion);

            stmt.execute();
        }
        catch (SQLiteConstraintException e){
            System.out.println("Exception SQLite: " + e.getMessage());

        }
        db.close();
    }

    public ListRoutes[] getRutas(){
        db = getReadableDatabase();
        Cursor c=db.rawQuery("SELECT id,origen,destino,id_reservacion,posicion FROM Routes ",null);
        ListRoutes[] r = new ListRoutes[c.getCount()];
        int flag = 0;
        if(c.moveToFirst())
        {

            do{
             //   for(int i = 0; i< c.getCount(); i++){
                    //  rutas[i] = new Rutas();
                    r[flag] = new ListRoutes();
                    r[flag].setIdSQL(c.getInt(0));
                    r[flag].setOrigen(c.getString(1));
                    r[flag].setDestiny(c.getString(2));
                    r[flag].setId(c.getInt(3));
                    r[flag].setPosicion(c.getInt(4));
                flag++;
             //   }
            }while(c.moveToNext());
        }

        //ReservacionRutasActivity.adapterRoutes.notifyDataSetChanged();
        db.close();
        return r;
    }

    public void updateRoutePosition(int position,int idSQLite)
    {
        db = getWritableDatabase();
        try{
            String actualizar= "Update Routes set hora="+position+" Where id="+idSQLite;
            db.execSQL(actualizar);
        }
        catch (SQLiteConstraintException e){
            System.out.println("Exception SQLite: " + e.getMessage());

        }
        db.close();
    }

    public void insertPackage(int id)
    {
        db = getWritableDatabase();
        try{
            SQLiteStatement stmt = db.compileStatement("INSERT INTO Package (id) "+
                    "VALUES (?)");
            stmt.bindLong(1,id);

            stmt.execute();
        }
        catch (SQLiteConstraintException e){
            System.out.println("Exception SQLite: " + e.getMessage());

        }
        db.close();
    }

    public int[] getIdPackages(int lenght){
        int[] pa = new int[lenght];
        db = getReadableDatabase();
        Cursor c=db.rawQuery("SELECT id FROM Package" ,null);
        if(c.moveToFirst())
        {
            do{
                for(int i = 0; i< c.getCount(); i++){
                    pa[i] = c.getInt(0);
                }
            }while(c.moveToNext());
        }
        db.close();
        return pa;
    }

    public void updateReservacionNext(int id)
    {
        db = getWritableDatabase();
        try{
            String actualizar= "Update NextRes set id_reservacion="+id+" Where id=1";
            db.execSQL(actualizar);
        }
        catch (SQLiteConstraintException e){
            System.out.println("Exception SQLite: " + e.getMessage());

        }
        db.close();
    }

    public int getReservacionIdNext(){
        int r = 0;
        db = getReadableDatabase();
        Cursor c=db.rawQuery("SELECT id_reservacion FROM NextRes ",null);
        if(c.moveToFirst())
        {
            do{
                r = c.getInt(0);
            }while(c.moveToNext());
        }
        db.close();
        return r;
    }

    public void deletePackages()
    {
        db = getWritableDatabase();
        try{
            String delete= "DELETE FROM Package";
            db.execSQL(delete);
        }
        catch (SQLiteConstraintException e){
            System.out.println("Exception SQLite: " + e.getMessage());

        }
        db.close();
    }

    public void deleteRoutes()
    {
        db = getWritableDatabase();
        try{
            String delete= "DELETE FROM Routes";
            db.execSQL(delete);
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


    public void updateProfile(User user)
    {
        int numberOfRowsAffected = 0;
        db = getWritableDatabase();
        try{
            ContentValues cv = new ContentValues();
            cv.put("nombre",user.getFirstName()); //These Fields should be your String values of actual column names
            cv.put("apellido_paterno",user.getAp_paterno());
            cv.put("apellido_materno",user.getAp_materno());
            cv.put("telefono",user.getCellphone());
            cv.put("domicilio",user.getAddress());
            cv.put("email",user.getEmail());
            cv.put("fecha_nacimiento",user.getFnacimiento());
            cv.put("ciudad",user.getCiudad());
            cv.put("password",user.getPassword());
            cv.put("foto",user.getPhoto());
            db.update("Usuarios", cv, "id="+user.getId(), null);

            db.close();
        }
        catch (SQLiteConstraintException e){
            System.out.println("Exception SQLite: " + e.getMessage());

        }
    }

    public void insertInfoFilter(Reservacion reservacion)
    {
        db = getWritableDatabase();
        try{
            SQLiteStatement stmt = db.compileStatement("INSERT INTO Reservacion (fecha,hora_inicio,hora_termino) "+
                    "VALUES (?,?,?)");
            stmt.bindString(1, reservacion.getDate());
            stmt.bindString(2, reservacion.getHourI());
            stmt.bindString(3, reservacion.getHourF());
            stmt.execute();
        }
        catch (SQLiteConstraintException e){
            System.out.println("Exception SQLite: " + e.getMessage());

        }
        db.close();
    }
    public void updateInfoFilter(Reservacion reservacion)
    {
        int numberOfRowsAffected = 0;
        db = getWritableDatabase();
        try{
            ContentValues cv = new ContentValues();
            cv.put("fecha",reservacion.getDate()); //These Fields should be your String values of actual column names
            cv.put("hora_inicio",reservacion.getHourI());
            cv.put("hora_termino",reservacion.getHourF());

            db.update("Reservacion", cv, "id="+reservacion.getId(), null);

            db.close();
        }
        catch (SQLiteConstraintException e){
            System.out.println("Exception SQLite: " + e.getMessage());

        }
    }
    public Reservacion getInfoFilter(){
        Reservacion u = new Reservacion();
        db = getReadableDatabase();
        Cursor c=db.rawQuery("SELECT id,fecha,hora_inicio,hora_termino FROM Reservacion" ,null);
        if(c.moveToFirst())
        {
            do{
                u.setId(c.getInt(0));
                u.setDate(c.getString(1));
                u.setHourI(c.getString(2));
                u.setHourF(c.getString(3));
            }while(c.moveToNext());
        }
        db.close();
        return u;
    }


}
