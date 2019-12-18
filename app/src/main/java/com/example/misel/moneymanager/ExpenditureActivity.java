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

public class ExpenditureActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    DatabaseHelper myDb;
    EditText editAmount;
    Button btnDate,btnShowData,btnAddData;
    TextView dateText,CategoryText;
    Spinner catSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenditure);
        myDb = new DatabaseHelper(this);
        editAmount = findViewById(R.id.txt_spend_amount);
        btnDate = findViewById(R.id.but_spend_date);
        btnShowData =  findViewById(R.id.but_viewExpenditure);
        btnAddData = findViewById(R.id.but_spend_addData);
        dateText = findViewById(R.id.spend_date);
        CategoryText = findViewById(R.id.spend_category);
        catSpinner = findViewById(R.id.spinner_spend_category);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Spending_Categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        catSpinner.setAdapter(adapter);
        catSpinner.setOnItemSelectedListener(this);


        String date = getIntent().getStringExtra("date");
        if(date!= null)
            dateText.setText(date);

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExpenditureActivity.this,CalendarActivity.class);
                startActivity(intent);
            }
        });

        btnShowData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExpenditureActivity.this,IncomeShowDataActivity.class);
                startActivity(intent);
            }
        });

        AddSpending();
    }
    public void AddSpending(){
        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInserted = myDb.insertSpendingData(editAmount.getText().toString(),dateText.getText().toString(),CategoryText.getText().toString());
                if(isInserted = true)
                    Toast.makeText(ExpenditureActivity.this,"Data inserted",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(ExpenditureActivity.this,"Data not inserted", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        CategoryText.setText(text);
        Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
