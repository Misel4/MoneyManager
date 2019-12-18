package com.example.misel.moneymanager;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ShowExpenditureActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_expenditure);
        myDb = new DatabaseHelper(this);
        display = findViewById(R.id.display_expenditure);

        ShowExpenditure();


    }

    public void ShowExpenditure(){
        Cursor result = myDb.getExpenditure();
        if(result.getCount() == 0){
            ShowMessage("Error","No data");
            return;
        }

        StringBuffer buffer = new StringBuffer();

        while(result.moveToNext()){
            buffer.append("Amount:" +result.getString(2)+"€"+"\n");
            buffer.append("Date:" +result.getString(1)+"\n");
            buffer.append("Category:" +result.getString(3)+ "\n"+"\n");
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
