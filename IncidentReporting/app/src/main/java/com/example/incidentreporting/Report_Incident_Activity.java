package com.example.incidentreporting;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Report_Incident_Activity extends MainActivity {
    //Declaring variables
    ArrayAdapter<String> adapter;
    EditText incidentId, empNumber, empName, department, position, date;
    RadioGroup gender;
    RadioButton male, female;
    Spinner shift, incidentType, injuredPart;
    String[] shifts = {"Shift A", "Shift B", "Shift C"};
    String[] incidentTypeList = {"Near Miss", "First Aid", "Medical Aid"};
    ArrayAdapter<String> adapter1, adapter2, adapter3;
    String genderSelected, shiftSelected, injuredPartSelected, incidentTypeSelected;
    Button report, imageCapture, ok;
    ImageView image;
    boolean addRecord;
    protected static final int CAMERA_PIC_REQUEST = 1;
    Uri URI ;
    Bitmap bitmap;
    File photo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report__incident_);

        //referring XML layout using Java objects
        date = (EditText)findViewById(R.id.textDate);
        incidentId = (EditText)findViewById(R.id.textIncidentId);
        date = (EditText)findViewById(R.id.textDate);
        empNumber = (EditText)findViewById(R.id.textEmpNo);
        empName = (EditText)findViewById(R.id.textEmpName);
        department = (EditText)findViewById(R.id.textDepartment);
        position = (EditText)findViewById(R.id.textPosition);
        gender = (RadioGroup)findViewById(R.id.radioGroupGender);
        male = (RadioButton)findViewById(R.id.radioMale);
        female = (RadioButton)findViewById(R.id.radioFemale);
        shift = (Spinner)findViewById(R.id.spinnerShift);
        incidentType = (Spinner)findViewById(R.id.spinnerIncidentType);
        injuredPart = (Spinner)findViewById(R.id.spinnerInjuredPart);
        report = (Button) findViewById(R.id.buttonReportIncident);
        imageCapture = (Button)findViewById(R.id.buttonCapture);
        image = (ImageView)findViewById(R.id.image);
        ok = (Button)findViewById(R.id.buttonOk);
        //ArrayList<String> bodyPartList = databaseHandler.getAllList();
        //Spinner injuredPart = findViewById(R.id.spinnerInjuredPart);
        //adapter = new ArrayAdapter<>(this,R.layout.spinner_layout,R.id.txt, bodyPartList);
       // injuredPart.setAdapter(adapter);

        //setting adapters for list spinners
        adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, shifts);
        shift.setAdapter(adapter1);

        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, incidentTypeList);
        incidentType.setAdapter(adapter2);

        adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        injuredPart.setAdapter(adapter3);

        //getting selected items
        shift.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                shiftSelected = shifts[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        incidentType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                incidentTypeSelected = incidentTypeList[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        injuredPart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                injuredPartSelected = list[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            //on Click event related with radio button
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(male.isChecked())
                {
                    genderSelected = "male";
                }
                if(female.isChecked())
                {
                    genderSelected = "female";
                }
            }
        });


        //Getting and setting date
        Date cal = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = dateFormat.format(cal);
        date.setText(formattedDate);




        //setting ok button functionalities
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = empNumber.getText().toString();
                Cursor data = databaseHandler.Showtbl_Employee(num);
                String nameEMP = "", dep="", pos="";
                StringBuffer buffer = new StringBuffer();
                if (data.getCount() == 0) {
                    ShowRecords("Error!", "No Record Found.");
                    return;
                }


                while (data.moveToNext()) {
                   nameEMP = data.getString(2);
                    dep = data.getString(3);
                    pos = data.getString(4);
                }
                empName.setText(nameEMP);
                department.setText(dep);
                position.setText(pos);
                //Shows the records retrieved
            }
        });



        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getApplicationContext(), "clicked", Toast.LENGTH_SHORT).show();
                //if no course is selected in list shows an toast to inform user
                //Validating inputs
                if(shift.getSelectedItemId() == -1){
                    Toast.makeText(getApplicationContext(), "Select Shift", Toast.LENGTH_SHORT).show();
                }
                else{
                    //if no credit is checked shows an toast to inform user
                    if (gender.getCheckedRadioButtonId() == -1) {
                        Toast.makeText(getApplicationContext(), "Select gender", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        //getting inputs
                        String dateString = date.getText().toString();
                        String empNumberString = empNumber.getText().toString();
                        String empNameString = empName.getText().toString();
                        String departmentString = department.getText().toString();
                        String positionString = position.getText().toString();

                        //Insert the record in to the table
                        addRecord = databaseHandler.AddRecordTo_tbl_IncidentHistory(dateString, empNumberString, empNameString, genderSelected, shiftSelected, departmentString, positionString, incidentTypeSelected, injuredPartSelected);
                        if(addRecord == true)
                        {
                            StringBuffer buffer = new StringBuffer();
                            buffer.append("incident Details are:\n");
                            buffer.append("Incident Date: " + dateString + "\n");
                            buffer.append("Employee Number: " + empNumberString + "\n");
                            buffer.append("Employee Name: " + empNameString + "\n");
                            buffer.append("Gender: " + genderSelected + "\n");
                            buffer.append("Shift: " + shiftSelected + "\n");
                            buffer.append("Department: " + departmentString + "\n");
                            buffer.append("Position: " + positionString + "\n");
                            buffer.append("Incident Type: " + incidentTypeSelected + "\n");
                            buffer.append("Injured Body Part: " + injuredPartSelected + "\n");
                            String details = buffer.toString();
                            sendMail(details);

                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Inserting record failed!", Toast.LENGTH_SHORT).show();
                        }

                        //Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        //startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    }
                }
            }
        });

        imageCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });
        String idString = "";
        Cursor data = databaseHandler.showRecords();
        if (data.getCount() == 0) {
            incidentId.setText("1");
        }
        while(data.moveToNext()){
            idString = data.getString(0);
        }
        int id = Integer.parseInt(idString);
        id = id + 1;
        incidentId.setText(""+id);
    }

    private void sendMail(String details){
        //setting details for sending mails
        String recipient =  "ptchanchal@gmail.com";
        String subject = "HR Incident Reporting";
        String message = details;

        //setting intends with data to be send
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, recipient);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        //intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(photo));
        /*if (Uri.fromFile(photo) != null) {
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(photo));
        }*/
        intent.setType("message/rfc822");
        //starting activity
        startActivity(Intent.createChooser(intent, "Choose an email client"));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //getting photo
        bitmap = (Bitmap)data.getExtras().get("data");
        image.setImageBitmap(bitmap);
        try {
            File root = Environment.getExternalStorageDirectory();
            if (root.canWrite()){
                photo = new File(root, "photo.jpeg");
                FileOutputStream out = new FileOutputStream(photo);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
            }
        } catch (IOException e) {
            Log.e("BROKEN", "Error occurred while writing file " + e.getMessage());
        }
    }


}
