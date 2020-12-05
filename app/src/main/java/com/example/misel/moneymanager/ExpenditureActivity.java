package com.example.misel.moneymanager;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.misel.moneymanager.fragments.PieFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import com.example.misel.moneymanager.fragments.SpendingPieFragment;

public class ExpenditureActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    DatabaseHelper myDb;
    EditText editAmount;
    Button btnDate,btnShowData,btnAddData;
    TextView dateText,CategoryText;
    Spinner catSpinner;
    DatePickerDialog datePickerDialog;

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

        ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        btnShowData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExpenditureActivity.this,ShowExpenditureActivity.class);
                startActivity(intent);
            }
        });

        AddSpending();
        GetDate();

        SpendingPieFragment spendingPieFragment= new SpendingPieFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.expenditureActivity,spendingPieFragment);
    }

    public void AddSpending(){
        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInserted = myDb.insertSpendingData(editAmount.getText().toString(),dateText.getText().toString(),CategoryText.getText().toString());
                if(isInserted = true)
                    Toast.makeText(ExpenditureActivity.this,"Επιτυχής καταχώρηση",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(ExpenditureActivity.this,"Απέτυχε", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void GetDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dates = sdf.format(new Date());
        dateText.setText(dates);

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(ExpenditureActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                dateText.setText(day + "/" + month + "/" + year);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        CategoryText.setText(text);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
