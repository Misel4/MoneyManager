package com.example.misel.moneymanager;

import android.database.Cursor;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class IncomeShowDataActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_show_data);
        myDb = new DatabaseHelper(this);
        display = findViewById(R.id.display);

        ViewData();
    }

    public void ViewData(){
        Cursor res = myDb.getAllData();

        if(res.getCount() == 0){
            ShowMessage("Error","No data");
            return;
        }

        StringBuffer buffer = new StringBuffer();

            while(res.moveToNext()){
                buffer.append("Amount:" +res.getString(2)+"€"+"\n");
                buffer.append("Date:" +res.getString(1)+"\n");
                buffer.append("Category:" +res.getString(3)+ "\n"+"\n");
        }
        display.setText(buffer);
    }

    public void ShowMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
