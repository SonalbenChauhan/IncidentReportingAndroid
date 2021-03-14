package com.example.incidentreporting;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {
    //DatabaseHandler Constructor
    public DatabaseHandler(@Nullable Context context)
    {
        super(context, "Incident", null,1);
    }


    //Function for creating the tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS tbl_IncidentHistory(Incident_Id INTEGER PRIMARY KEY AUTOINCREMENT, Incident_Date VARCHAR, Employee_Number INTEGER," +
                " Employee_Name VARCHAR, Gender VARCHAR, Shift VARCHAR, Department VARCHAR, Position VARCHAR, Incident_Type VARCHAR, Injured_Body_Part VARCHAR);");
        db.execSQL("CREATE TABLE IF NOT EXISTS tbl_BodyParts(Id INTEGER PRIMARY KEY AUTOINCREMENT, Body_Part VARCHAR);");
        db.execSQL("CREATE TABLE IF NOT EXISTS tbl_Employee(Id INTEGER PRIMARY KEY AUTOINCREMENT, Employee_Number INTEGER, Employee_Name VARCHAR, Department VARCHAR, Position VARCHAR);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //Function to add new record in the table tbl_BodyParts
    public boolean AddRecordTo_tbl_BodyParts (String bodyPart) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Body_Part", bodyPart);
        db.insert("tbl_BodyParts", null, contentValues);
        return true;
    }

    //Function to add new record in the table tbl_BodyParts
    public boolean AddRecordTo_tbl_Employee (int employee_Number, String employee_Name, String department, String position) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Employee_Number", employee_Number);
        contentValues.put("Employee_Name", employee_Name);
        contentValues.put("Department", department);
        contentValues.put("Position", position);
        db.insert("tbl_Employee", null, contentValues);
        return true;
    }

    //Function to add new record in the table tbl_IncidentHistory
    public boolean AddRecordTo_tbl_IncidentHistory (String incident_Date, String employee_Number, String employee_Name, String gender, String shift, String department,
                              String position, String incident_Type, String injured_Body_Part) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Incident_Date", incident_Date);
        contentValues.put("Employee_Number", employee_Number);
        contentValues.put("Employee_Name", employee_Name);
        contentValues.put("Gender", gender);
        contentValues.put("Shift", shift);
        contentValues.put("Department", department);
        contentValues.put("Position", position);
        contentValues.put("Incident_Type", incident_Type);
        contentValues.put("Injured_Body_Part", injured_Body_Part);
        db.insert("tbl_IncidentHistory", null, contentValues);
        return true;
    }


    //function for showing all records in tbl_BodyParts
    public Cursor showRecords_tbl_BodyParts(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor records = db.rawQuery("SELECT * FROM " + "tbl_BodyParts", null);
        return records;
    }

    //function for showing all records in tbl_IncidentHistory
    public Cursor showRecords(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor records = db.rawQuery("SELECT * FROM " + "tbl_IncidentHistory", null);
        return records;
    }

    //function for showing id in tbl_IncidentHistory
    public Cursor showId(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor records = db.rawQuery("SELECT Incident_Id FROM " + "tbl_IncidentHistory", null);
        return records;
    }

    //function for showing records by id
    public Cursor showRecordsById(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor records = db.rawQuery("SELECT * FROM " + "tbl_IncidentHistory" +" WHERE id ="+ id, null);
        return records;
    }

    //function for showing records by id
    public Cursor Showtbl_Employee(String num) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor records = db.rawQuery("SELECT * FROM " + "tbl_Employee" +" WHERE Employee_Number ="+ num, null);
        return records;
    }

    //function for showing all records in tbl_Employee
    public Cursor Showtbl_Employees() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor records = db.rawQuery("SELECT * FROM " + "tbl_Employee", null);
        return records;
    }



    //function deleting records from tbl_BodyParts
    public Integer deleteRecord(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("tbl_BodyParts", "?", new String[] {id});
    }

    //function deleting records from tbl_Employee
    public Integer deleteRecord_tbl_Employee(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("tbl_Employee", "?", new String[] {id});
    }


//Function for polulating body part list by arraylist
    public ArrayList<String> getAllList(){
        ArrayList<String> list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try{
            String selectQuery = "SELECT * FROM tbl_BodyParts";
            Cursor cursor = db.rawQuery(selectQuery, null);
            if(cursor.getCount()>0){
                while(cursor.moveToNext()){
                    String bodyPart = cursor.getString(cursor.getColumnIndex("Body_Part"));
                    list.add(bodyPart);
                }
            }
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            {
                db.endTransaction();
                db.close();
            }
            return list;
        }

    }
}
