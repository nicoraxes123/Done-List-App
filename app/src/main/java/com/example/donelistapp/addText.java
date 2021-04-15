package com.example.donelistapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class addText extends AppCompatActivity {

    private TextView txtWhat;
    private EditText edInput, edJam, edTgl;
    private Button btnTambah, btnEdit;

    DatabaseHelper databaseHelper;
    DatePickerDialog.OnDateSetListener setListener;

    int thour, tminute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_text);

        setId();
        Bundle bundle = getIntent().getExtras();
        if(bundle.getString("tombol").equals("add")){
            txtWhat.setText("What you gonna do " + MainActivity.NAME + "?");
            btnEdit.setVisibility(View.GONE);
            btnTambah.setVisibility(View.VISIBLE);
        }
        else if(bundle.getString("tombol").equals("edit")){
            txtWhat.setText("Edit Event");
            btnEdit.setVisibility(View.VISIBLE);
            btnTambah.setVisibility(View.GONE);
            edInput.setText(bundle.getString("data"));
            edTgl.setText(bundle.getString("tgl"));
            edJam.setText(bundle.getString("jam"));
        }


        databaseHelper = new DatabaseHelper(this);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        edTgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        addText.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener, year, month, day
                );
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        edJam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        addText.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                        thour = hourOfDay;
//                        tminute = minute;

//                        Calendar calendar1 = Calendar.getInstance();
//                        calendar1. set(0, 0, 0, thour, tminute);
//                        edJam.setText(DateFormat.format("hh:mm", calendar1));
                        String sHour = "", sMinute = "";
                        if(hourOfDay >= 10){
                            sHour = "" + hourOfDay;
                        }
                        else{
                            sHour = "0" + hourOfDay;
                        }

                        if(minute >= 10){
                            sMinute = "" + minute;
                        }
                        else{
                            sMinute = "0" + minute;
                        }

                        edJam.setText(sHour+":"+sMinute);
                    }
                }, 24, 0, true);

                timePickerDialog.updateTime(thour, tminute);
                timePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = "", sDay = "";
                if(dayOfMonth >= 10){
                    sDay = "" + dayOfMonth;
                }
                else{
                    sDay = "0" + dayOfMonth;
                }


                if(month >= 10){
                    date = sDay+"/"+month+"/"+year;
                }
                else {
                    date = sDay+"/0"+month+"/"+year;
                }
                edTgl.setText(date);
            }
        };

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = edInput.getText().toString(), tgl = edTgl.getText().toString(), jam = edJam.getText().toString();

                if(input.equals("") && tgl.equals("") && jam.equals("")){
                    Toast.makeText(addText.this, "Input masih kosong. Tolong untuk diisi...", Toast.LENGTH_SHORT).show();
                }
                else{
                    boolean checkInsert = databaseHelper.insertList(MainActivity.USER_ID, input, tgl, jam);

                    if(checkInsert == true){
                        Toast.makeText(addText.this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(addText.this, menu.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(addText.this, "Data Tidak Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = edInput.getText().toString(), tgl = edTgl.getText().toString(), jam = edJam.getText().toString();

                if(input.equals("") && tgl.equals("") && jam.equals("")){
                    Toast.makeText(addText.this, "Input masih kosong. Tolong untuk diisi...", Toast.LENGTH_SHORT).show();
                }
                else{
                    boolean checkUpdate = databaseHelper.updateList(input, tgl, jam, bundle.getString("id_data"));

                    if(checkUpdate == true){
                        Toast.makeText(addText.this, "Data Berhasil Ter-update", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(addText.this, menu.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(addText.this, "Data Tidak Berhasil Ter-update", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void setId(){
        txtWhat = findViewById(R.id.txtWhat);
        edInput = findViewById(R.id.edInput);
        edJam = findViewById(R.id.edJam);
        edTgl = findViewById(R.id.edTgl);
        btnTambah = findViewById(R.id.btnTambah);
        btnEdit = findViewById(R.id.btnEdit);
    }
}