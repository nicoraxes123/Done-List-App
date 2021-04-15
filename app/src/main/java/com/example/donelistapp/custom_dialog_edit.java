package com.example.donelistapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class custom_dialog_edit extends AppCompatDialogFragment {

    EditText edData, edTgl, edJam;
    DatabaseHelper databaseHelper;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.layout_dialog_edit, null);
        databaseHelper = new DatabaseHelper(this.getContext());

        edData = view.findViewById(R.id.edInput);
        edTgl = view.findViewById(R.id.edTgl);
        edJam = view.findViewById(R.id.edJam);

        builder.setView(view)
                .setTitle("Edit")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                })
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String input = edData.getText().toString(), tgl = edTgl.getText().toString(), jam = edJam.getText().toString();

                        boolean checkInsert = databaseHelper.updateList(input, tgl, jam, MainActivity.USER_ID);

                        if(checkInsert == true){
                            Toast.makeText(getContext(), "Edit Berhasil ", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), menu.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(getContext(), "Data Tidak Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return builder.create();
    }
}
