package com.example.donelistapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class daftar extends AppCompatActivity {

    private EditText edNama, edEmail, edPassword;
    private Button btnDaftar;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        setId();
        databaseHelper = new DatabaseHelper(this);

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = edNama.getText().toString();
                String email = edEmail.getText().toString();
                String pass = edPassword.getText().toString();

                if(!nama.equals("") && !email.equals("") && !pass.equals("")){
                    boolean checkInsert = databaseHelper.insertUser(nama, email, pass);

                    if(checkInsert == true){
                        Toast.makeText(daftar.this, "Daftar user berhasil", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(daftar.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(daftar.this, "Daftar user tidak berhasil", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(daftar.this, "Tolong diisi dengan lengkap dan benar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        return super.onKeyDown(keyCode, event);
    }

    private void setId(){
        btnDaftar = findViewById(R.id.btnDaftar);
        edNama = findViewById(R.id.edNama);
        edEmail = findViewById(R.id.edEmail);
        edPassword = findViewById(R.id.edPassword);
    }
}