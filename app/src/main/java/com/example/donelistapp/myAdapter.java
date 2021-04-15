package com.example.donelistapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;


public class myAdapter extends ArrayAdapter<MyData> {

    private ArrayList<MyData> myData;
    private Context context;
    public String tglIni, jamIni;


    public myAdapter(@NonNull Context context, int resource, ArrayList<MyData> myData) {
        super(context, resource, myData);
        this.myData = myData;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        TextView tvId = convertView.findViewById(R.id.txtId);
        TextView tvData = convertView.findViewById(R.id.txtData);
        TextView tvTgl = convertView.findViewById(R.id.txtTgl);
        TextView tvJam = convertView.findViewById(R.id.txtJam);
        TextView tvStatus = convertView.findViewById(R.id.txtStatus);
        Button btnEdit = convertView.findViewById(R.id.btnEdit);
        Button btnDone = convertView.findViewById(R.id.btnDone);
        LinearLayout llButton = convertView.findViewById(R.id.llButton);
        DatabaseHelper databaseHelper = new DatabaseHelper(convertView.getContext());

        tvId.setText(myData.get(position).getId());
        tvData.setText(myData.get(position).getData());
        tvTgl.setText(myData.get(position).getTgl());
        tvJam.setText(myData.get(position).getJam());

        tglIni = ""; jamIni = "";
        updateDateAndTime();

        if(myData.get(position).getStatus().equals("0")){
            if(cekTgl(tglIni, tvTgl.getText().toString()) == 0){
                if(cekJam(jamIni, tvJam.getText().toString())){
                    tvStatus.setText("Status: -");
                }
                else{
                    tvStatus.setText("Status: Overdue");
                }
            }
            else if(cekTgl(tglIni, tvTgl.getText().toString()) == 1){
                tvStatus.setText("Status: -");
            }
            else{
                tvStatus.setText("Status: Overdue");
            }

            llButton.setVisibility(View.VISIBLE);
//            btnEdit.setVisibility(View.VISIBLE);
//            btnDone.setVisibility(View.VISIBLE);
        }
        else if(myData.get(position).getStatus().equals("1")){
            tvStatus.setText("Status: Done");
            llButton.setVisibility(View.GONE);
//            btnEdit.setVisibility(View.INVISIBLE);
//            btnDone.setVisibility(View.INVISIBLE);
        }
        else if(myData.get(position).getStatus().equals("2")){
            tvStatus.setText("Status: Overdue");
            llButton.setVisibility(View.GONE);
//            btnEdit.setVisibility(View.INVISIBLE);
//            btnDone.setVisibility(View.INVISIBLE);
        }

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, addText.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id_data", tvId.getText());
                intent.putExtra("data", tvData.getText());
                intent.putExtra("tgl", tvTgl.getText());
                intent.putExtra("jam", tvJam.getText());
                intent.putExtra("tombol", "edit");
                context.startActivity(intent);
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkUpdate;
                updateDateAndTime();
                if(cekTgl(tglIni, tvTgl.getText().toString()) == 0){
                    if(cekJam(jamIni, tvJam.getText().toString())){
                        checkUpdate = databaseHelper.updateListStatusDone(tvId.getText().toString(), tvTgl.getText().toString() + " | Finish: " + tglIni, tvJam.getText().toString() + " | Finish: " + jamIni, "1");
                    }
                    else{
                        checkUpdate = databaseHelper.updateListStatusDone(tvId.getText().toString(), tvTgl.getText().toString() + " | Finish: " + tglIni, tvJam.getText().toString() + " | Finish: " + jamIni, "2");
                    }
                }
                else if(cekTgl(tglIni, tvTgl.getText().toString()) == 1){
                    checkUpdate = databaseHelper.updateListStatusDone(tvId.getText().toString(), tvTgl.getText().toString() + " | Finish: " + tglIni, tvJam.getText().toString() + " | Finish: " + jamIni, "1");
                }
                else{
                    checkUpdate = databaseHelper.updateListStatusDone(tvId.getText().toString(), tvTgl.getText().toString() + " | Finish: " + tglIni, tvJam.getText().toString() + " | Finish: " + jamIni, "2");
                }


                if (checkUpdate == true) {
                    Toast.makeText(context, "Data berhasil ter-checklist", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(context, menu.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent1);
                } else {
                    Toast.makeText(v.getContext(), "Data belum berhasil ter-checklist", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return convertView;
    }

    public void updateDateAndTime(){
        tglIni = ""; jamIni = "";
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
//        String tglIni = "", jamIni = "";
        if(day >= 10){
            tglIni = "" + day;
        }
        else {
            tglIni = "0" + day;
        }

        if(month >= 10){
            tglIni += "/" + month + "/" + year;
        }
        else {
            tglIni += "/0" + month + "/" + year;
        }

        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        if(hour >= 10){
            jamIni = "" + hour;
        }
        else{
            jamIni = "0" + hour;
        }

        if(minute >= 10){
            jamIni += ":" + minute;
        }
        else{
            jamIni += ":0" + minute;
        }
    }

    public int cekTgl (String tglIni, String tglData){
        int dayIni = Integer.parseInt(tglIni.charAt(0) + "" + tglIni.charAt(1));
        int dayData = Integer.parseInt(tglData.charAt(0) + "" + tglData.charAt(1));
        int monthIni = Integer.parseInt(tglIni.charAt(3) + "" + tglIni.charAt(4));
        int monthData = Integer.parseInt(tglData.charAt(3) + "" + tglData.charAt(4));
        int yearIni = Integer.parseInt(tglIni.charAt(6) + "" + tglIni.charAt(7) + "" + tglIni.charAt(8) + "" + tglIni.charAt(9));
        int yearData = Integer.parseInt(tglData.charAt(6) + "" + tglData.charAt(7) + "" + tglData.charAt(8) + "" + tglData.charAt(9));

        if(yearIni == yearData && monthIni == monthData && dayIni == dayData){
            return 0;
        }
        else{
            if(yearIni <= yearData){
                if(monthIni <= monthData){
                    if (dayIni <= dayData){
                        return 1;
                    }
                }
            }
        }
        return 2;
    }

    public boolean cekJam (String jamIni, String jamData){
        int hourIni = Integer.parseInt(jamIni.charAt(0) + "" + jamIni.charAt(1));
        int hourData = Integer.parseInt(jamData.charAt(0) + "" + jamData.charAt(1));
        int minuteIni = Integer.parseInt(jamIni.charAt(3) + "" + jamIni.charAt(4));
        int minuteData = Integer.parseInt(jamData.charAt(3) + "" + jamData.charAt(4));

        if(hourIni <= hourData){
            if(minuteIni <= minuteData){
                return true;
            }
        }
        return false;
    }

}
