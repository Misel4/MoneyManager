package com.example.misel.moneymanager;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;

public class CalendarExpenditureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_expenditure);

        CalendarView calendar2View = findViewById(R.id.calendarExpView);

        calendar2View.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "/" + month +"/" + year;

                Intent intent = new Intent(CalendarExpenditureActivity.this,ExpenditureActivity.class);
                intent.putExtra("date",date);
                startActivity(intent);

            }
        });
    }
}
