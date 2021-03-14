package com.example.incidentreporting;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;

public class View_Incidents_Activity extends MainActivity {


    //variable declaration
    DatabaseHandler databaseHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__incidents_);

        // object creation of DatabaseHandler class
        databaseHandler = new DatabaseHandler(this);

        //Calling function for all records
        Cursor record = databaseHandler.showRecords();

        if (record.getCount() == 0) {
            DisplayData("Error", "No Data Found.");
            return;
        }
        // adding the data result to string buffer
        StringBuffer buffer = new StringBuffer();
        while (record.moveToNext()) {
            buffer.append("ID: " + record.getInt(0) + "\n");
            buffer.append("Incident Date: " + record.getString(1) + "\n");
            buffer.append("Employee Number: " + record.getString(2) + "\n");
            buffer.append("Employee Name: " + record.getString(3) + "\n");
            buffer.append("Gender: " + record.getString(4) + "\n");
            buffer.append("Shift: " + record.getString(5) + "\n");
            buffer.append("Department: " + record.getString(6) + "\n");
            buffer.append("Position: " + record.getString(7) + "\n");
            buffer.append("Incident Type: " + record.getString(8) + "\n");
            buffer.append("Injured Body Part: " + record.getString(9) + "\n\n\n");

        }
        // invoking DisplayData function
        DisplayData("Incident Details:", buffer.toString());
    }

    //Alert box creation
    public void DisplayData(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
