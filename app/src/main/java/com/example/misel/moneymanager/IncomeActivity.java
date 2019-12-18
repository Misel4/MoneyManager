package com.example.misel.moneymanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class IncomeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    DatabaseHelper myDb;
    EditText editAmount;
    Button btnAddData,btnShowData,btnDate;
    TextView textView,categoryText;
    Spinner catSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_income);
        myDb = new DatabaseHelper(this);

        textView = findViewById(R.id.date);
        btnDate = findViewById(R.id.but_date);
        editAmount = findViewById(R.id.txt_amount);
        btnAddData = findViewById(R.id.but_addData);
        btnShowData = findViewById(R.id.but_viewIncome);
        categoryText = findViewById(R.id.category);
        catSpinner = findViewById(R.id.spinner_category);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        catSpinner.setAdapter(adapter);
        catSpinner.setOnItemSelectedListener(this);

        AddData();



        String date = getIntent().getStringExtra("date");
        if(date!= null)
            textView.setText(date);

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IncomeActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });

        btnShowData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IncomeActivity.this,IncomeShowDataActivity.class);
                startActivity(intent);
            }
        });

    }
    public void AddData() {
        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDb.insertData(editAmount.getText().toString(),textView.getText().toString(),categoryText.getText().toString());
                if(isInserted = true)
                    Toast.makeText(IncomeActivity.this,"Data inserted",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(IncomeActivity.this,"Data not inserted",Toast.LENGTH_LONG).show();

            }
        });

    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        categoryText.setText(text);
        Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
