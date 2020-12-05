package com.example.misel.moneymanager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.chart.common.dataentry.ValueDataEntry;

import java.util.ArrayList;
import java.util.List;

public class IncomeShowDataActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    TextView display;
    ListView listView;
    List<String> appendData = new ArrayList<>();
    List<String> amount = new ArrayList<>();
    List<String> date = new ArrayList<>();
    List<String> category = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_show_data_layout);
        myDb = new DatabaseHelper(this);
        display = findViewById(R.id.display);
        listView = findViewById(R.id.List1);

        ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        ViewData();

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.activity_income_show_data,R.id.display,appendData);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String amount1 = parent.getItemAtPosition(position).toString();
                String part3= "";
                if(amount1.contains(":") ){
                   String[] parts = amount1.split(":",2);
                    String part1 = parts[0];
                    String part2 = parts[1];
                    if(part2.contains("\n")){
                        String parts1[] =  part2.split("\n",2);
                        part3 = parts1[0];
                        String part4 = parts1[1];
                    }
                }

                Cursor data = myDb.getItemID(part3);
                int itemID = -1;
                while (data.moveToNext()) {
                    itemID = data.getInt(0);
                }
                    if(itemID>-1){
                        Intent deleteScreen = new Intent(IncomeShowDataActivity.this, DeleteItemActivity.class);
                        deleteScreen.putExtra("id",itemID);
                        deleteScreen.putExtra("amount1",amount1);
                        deleteScreen.putExtra("part3",part3);
                        startActivity(deleteScreen);
                    }
                }

        });

    }

    public void ViewData(){
        Cursor res = myDb.getAllData();

        if(res.getCount() == 0){
            ShowMessage("Error","No data");
            return;
        }

        StringBuffer buffer = new StringBuffer();

            while (res.moveToNext()) {
                amount.add(res.getString(2));
                date.add(res.getString(1));
                category.add(res.getString(3));
            }

            for(int i=0 ; i<amount.size();i++){
                String poso = amount.get(i);
                String imerominia = date.get(i);
                String katigoria = category.get(i);
              appendData.add("Ποσό:"+poso+"\n"+"Ημερομηνία:"+imerominia+"\n"+"Κατηγορία:"+katigoria);
            }
    }

    public void ShowMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
