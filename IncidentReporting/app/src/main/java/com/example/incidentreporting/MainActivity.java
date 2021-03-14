package com.example.incidentreporting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    //variable declaration
    DatabaseHandler databaseHandler;
    Button btnReportIncident;
    //EditText incidentId;
    boolean addRecordTo_tbl_BodyParts;
    EditText date;

    //Storing list of body parts
    String[] list = {"Ankle-left", "Ankle-right", "Arm-Both", "Arm-Left Upper", "Arm-Right Upper", "Back-All",
            "Back-Lower", "Back-Middle", "Back-Upper", "Chest", "Ear-Both", "Ear-left", "Ear-Right", "Ears-Both",
    "Elbow-Left", "Elbow-Right", "Eye-both", "Eye-left", "Eye-right", "Face", "Feet-Both", "Foot-left", "Foot-right", "Forearm-left",
    "Forearm-right", "Hand-left", "Hand-Palm-Left", "Hand-Palm-right", "Hand-right", "Hands-Both", "Head-Rear", "Head-Front",
    "Head-Left", "Head-Right", "Hip-Left", "Hip-Right", "Index Finger-left", "Index Finger-right", "Knee-left",
    "Knee-right", "Leg-Both", "Leg-Left Lower", "Leg-Left Upper", "Leg-Right Lower", "Leg-Right Upper", "Middle Finger-left",
    "Middle Finger-right", "Mouth", "Neck", "Nose", "Pinky Finger-left", "Pinky Finger-right", "Ring Finger-left",
            "Ring Finger-right", "Shoulder-Right", "Shoulder-Left", "Thumb-left", "Thumb-right", "Wrist-Left", "Wrist-Right",
    "Other-See Notes", "Groin", "Abdomen", "Multiple-See Notes", "N/A", "Internal"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //incidentId = (EditText)findViewById(R.id.textIncidentId);
        //creating database class object
        databaseHandler = new DatabaseHandler(this);
        //calling function for deleting record
        databaseHandler.deleteRecord("1");
        for(int i=0; i<list.length; i++)
        {
            addRecordTo_tbl_BodyParts = databaseHandler.AddRecordTo_tbl_BodyParts(list[i]);
        }
        //inserting record in employee table
        Cursor data = databaseHandler.Showtbl_Employees();
        if (data.getCount() == 0) {
            databaseHandler.AddRecordTo_tbl_Employee(1,"John Wick", "Accounts and Finance", "Manager");
            databaseHandler.AddRecordTo_tbl_Employee(2,"Jack Wick", "Sales and marketing", "Marketing Associate");
            databaseHandler.AddRecordTo_tbl_Employee(3,"John Jack", "Infrastructures", "Manager");
            databaseHandler.AddRecordTo_tbl_Employee(4,"Roy Jose", "IT services", "tester");
            databaseHandler.AddRecordTo_tbl_Employee(5,"Jose Roy", "Product development", "developer");
            databaseHandler.AddRecordTo_tbl_Employee(6,"Mary John", "Admin department", "IT Admin");
            databaseHandler.AddRecordTo_tbl_Employee(7,"Run Jose", "Accounts and Finance", "Business Analyst");
            databaseHandler.AddRecordTo_tbl_Employee(8,"Harry Ciz", "Accounts and Finance", "Manager");
            databaseHandler.AddRecordTo_tbl_Employee(9,"Henry Jon", "Sales and marketing", "Marketing Associate");
            databaseHandler.AddRecordTo_tbl_Employee(10,"Kim Jas", "Infrastructures", "Manager");
            databaseHandler.AddRecordTo_tbl_Employee(11,"Koi Khan", "IT services", "tester");
            databaseHandler.AddRecordTo_tbl_Employee(12,"David kar", "Product development", "developer");
            databaseHandler.AddRecordTo_tbl_Employee(13,"Jackson Mic", "Admin department", "IT Admin");
            databaseHandler.AddRecordTo_tbl_Employee(14,"Ken John", "Accounts and Finance", "Business Analyst");
        }


    }

    //inflating option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    //linking activities
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id==R.id.item1)
        {
            startActivity(new Intent(this, Report_Incident_Activity.class));
        }
        else if(id==R.id.item2)
        {
            startActivity(new Intent(this, View_Incidents_Activity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    //Alert box creation
    public void ShowRecords(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
