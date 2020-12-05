package com.example.misel.moneymanager;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import com.example.misel.moneymanager.fragments.PieFragment;

public class IncomeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    DatabaseHelper myDb;
    EditText editAmount;
    Button btnAddData,btnShowData,btnDate;
    TextView textView,categoryText;
    Spinner catSpinner;
    DatePickerDialog datePickerDialog;

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

        btnShowData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IncomeActivity.this,IncomeShowDataActivity.class);
                startActivity(intent);
            }
        });

        ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        AddData();
        GetDate();
        //Balance();
        PieFragment pieFragment= new PieFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.incomeActivity,pieFragment);

    }

    public void AddData() {
        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDb.insertData(editAmount.getText().toString(),textView.getText().toString(),categoryText.getText().toString());
                if(isInserted = true)
                    Toast.makeText(IncomeActivity.this,"Επιτυχής καταχώρηση",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(IncomeActivity.this,"Απέτυχε",Toast.LENGTH_LONG).show();
            }
        });

    }
    public void GetDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dates = sdf.format(new Date());
        textView.setText(dates);

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(IncomeActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                textView.setText(day + "/" + month + "/" + year);
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
        categoryText.setText(text);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
