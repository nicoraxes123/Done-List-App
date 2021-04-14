package com.example.donelistapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class masuk extends AppCompatActivity {

    private EditText edEmail, edPassword;
    private Button btnMasuk;


    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masuk);

        setId();
//        getSession();
//        sessionCondition();
        databaseHelper = new DatabaseHelper(this);

        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = databaseHelper.getDataUser();
                String email = edEmail.getText().toString();
                String pass = edPassword.getText().toString();

                if(res.getCount() != 0){
                    boolean stop = false;

                    while (res.moveToNext() && !stop){
                        if(email.equals(res.getString(2)) && pass.equals(res.getString(3))){
                            saveSession(res.getString(0), res.getString(1));
                            stop = true;
                        }
                    }

                    if(!stop){
                        Toast.makeText(masuk.this, "Login Salah", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Intent intent = new Intent(masuk.this, menu.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }
                else{
                    Toast.makeText(masuk.this, "Login Salah", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

//    public void getSession(){
//        @SuppressLint("WrongConstant")
//        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_APPEND);
//        MainActivity.USER_ID = sharedPreferences.getString("user_id", "");
//        MainActivity.NAME = sharedPreferences.getString("name", "");
//    }
//
//    public void sessionCondition(){
//        if(!MainActivity.USER_ID.equals("") && !MainActivity.NAME.equals("")){
//            Intent intent = new Intent(masuk.this, menu.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(intent);
//        }
//    }

    public void saveSession(String user_id, String nama){
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_id", user_id);
        editor.putString("name", nama);
        MainActivity.USER_ID = user_id;
        MainActivity.NAME = nama;
        editor.apply();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        return super.onKeyDown(keyCode, event);
    }

    private void setId(){
        edEmail = findViewById(R.id.edEmail);
        edPassword = findViewById(R.id.edPassword);
        btnMasuk = findViewById(R.id.btnMasuk);
    }
}