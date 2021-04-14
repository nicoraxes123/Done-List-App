package com.example.donelistapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class addText extends AppCompatActivity {

    private TextView txtWhat;
    private EditText edInput;
    private Button btnTambah;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_text);

        setId();
        txtWhat.setText("What you gonna do " + MainActivity.NAME + "?");

        databaseHelper = new DatabaseHelper(this);
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = edInput.getText().toString();

                if(input.equals("")){
                    Toast.makeText(addText.this, "Input masih kosong. Tolong untuk diisi...", Toast.LENGTH_SHORT).show();
                }
                else{
                    boolean checkInsert = databaseHelper.insertList(MainActivity.USER_ID, input);

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
    }

    private void setId(){
        txtWhat = findViewById(R.id.txtWhat);
        edInput = findViewById(R.id.edInput);
        btnTambah = findViewById(R.id.btnTambah);
    }
}