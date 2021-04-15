package com.example.donelistapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

public class menu extends AppCompatActivity {

    private FloatingActionButton fabAdd, fabLogOut;
    private TextView txtHi;
    private ListView listViewToDo, listViewDone;

    //ArrayList<String> listItem;
    ArrayList<MyData> myDataToDo, myDataDone;
    myAdapter adapter1, adapter2;

    DatabaseHelper databaseHelper;

    public void openDialog(View view){
        custom_dialog_edit custom_dialog_edit = new custom_dialog_edit();
        custom_dialog_edit.show(getSupportFragmentManager(), "Edit Data");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        setId();
        txtHi.setText("Hi " + MainActivity.NAME + "!");
        databaseHelper = new DatabaseHelper(this);
        //listItem = new ArrayList<>();

        myDataToDo = new ArrayList<>();
        myDataDone = new ArrayList<>();
        populateMyData();

        adapter1 = new myAdapter(getApplicationContext(), R.layout.list_item, myDataToDo);
        adapter2 = new myAdapter(getApplicationContext(), R.layout.list_item, myDataDone);
        listViewToDo.setAdapter(adapter1);
        listViewDone.setAdapter(adapter2);

        //viewData();
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menu.this, addText.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("tombol", "add");
                startActivity(intent);
            }
        });

        fabLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
                Toast.makeText(menu.this, "Logout Berhasil", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(menu.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void logOut(){
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_id", "");
        editor.putString("name", "");
        editor.apply();
    }

    public void populateMyData(){
        Cursor res = databaseHelper.getDataList();

        if(res.getCount() != 0) {
            while (res.moveToNext()) {
                if (res.getString(1).equals(MainActivity.USER_ID)) {
                    if(res.getString(5).equals("0")){
                        myDataToDo.add(new MyData(res.getString(0), res.getString(2), res.getString(3), res.getString(4), res.getString(5)));
                    }
                    else{
                        myDataDone.add(new MyData(res.getString(0), res.getString(2), res.getString(3), res.getString(4), res.getString(5)));
                    }
                }
            }
        }
    }

//    private void viewData(){
//        Cursor res = databaseHelper.getDataList();
//        int get = 0;
//        if(res.getCount() != 0) {
//            while (res.moveToNext()) {
//                if (res.getString(1).equals(MainActivity.USER_ID)) {
//                    get++;
//                    listItem.add(res.getString(2));
//                }
//            }
//
//            if (get != 0) {
//                adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItem);
//                listView.setAdapter(adapter);
//            } else {
//                Toast.makeText(menu.this, "Data Kosong", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            AlertDialog.Builder dialog = new AlertDialog.Builder(menu.this);
            dialog.setTitle("PERINGATAN").setMessage("Apakah Anda yakin ingin keluar?").setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            finishAffinity();
                            System.exit(0);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = dialog.create();
            alert.setTitle("PERINGATAN");
            alert.show();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void setId(){
        listViewToDo = findViewById(R.id.lvTodo);
        listViewDone = findViewById(R.id.lvDone);
        fabAdd = (FloatingActionButton) findViewById(R.id.floAdd);
        txtHi = findViewById(R.id.txtHi);
        fabLogOut = findViewById(R.id.floOut);
    }
}