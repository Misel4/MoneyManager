package com.example.misel.moneymanager.fragments;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.example.misel.moneymanager.DatabaseHelper;
import com.example.misel.moneymanager.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PieFragment extends Fragment {
    AnyChartView anyChartView;
    DatabaseHelper myDb;
    SQLiteDatabase sqLiteDatabase;
    ArrayList<String> months = new ArrayList<>();
    ArrayList<Double> amount = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.pie_fragment_layout,container,false);
        myDb = new DatabaseHelper(getActivity());
        sqLiteDatabase =myDb.getWritableDatabase();

        anyChartView = view.findViewById(R.id.any_chart_view1);
        try {
            setupPieChart();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return view;
    }

    public void setupPieChart() throws ParseException {
        Pie pie = AnyChart.pie();
        Cursor values = myDb.getAllData();

        if(values.getCount() ==0 ){
            Toast.makeText(this.getContext(),"Δεν υπαρχουν δεδομένα διαθέσιμα για προβολή",Toast.LENGTH_LONG).show();
            return;
        }

        while(values.moveToNext()){
            months.add(values.getString(1));
            amount.add(values.getDouble(2));
        }

        List<DataEntry> dataEntries = new ArrayList<>();
        for(int i=0; i<months.size();i++){
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date parse = sdf.parse(months.get(i));
            Calendar c = Calendar.getInstance();
            c.setTime(parse);
            String month = c.getDisplayName(Calendar.MONTH,Calendar.LONG, Locale.getDefault());
            dataEntries.add(new ValueDataEntry(month, amount.get(i)));
        }
        pie.title("Εσοδα");
        pie.labels().position("outside");

        pie.legend().title().enabled(true);
        pie.legend().title()
                .text("Μηνιαίο Διάγραμμα")
                .padding(0d, 0d, 10d, 0d);

        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);
        pie.data(dataEntries);
        anyChartView.setChart(pie);
    }
}
