package com.example.misel.moneymanager.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.example.misel.moneymanager.DatabaseHelper;
import com.example.misel.moneymanager.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TotalBalanceChart extends Fragment {
    AnyChartView anyChartView;
    DatabaseHelper myDb;
    SQLiteDatabase sqLiteDatabase;
    ArrayList<Double> amount = new ArrayList<>();
    ArrayList<String> months = new ArrayList<>();
    ArrayList<Double> amountExp = new ArrayList<>();
    ArrayList<String> months1 = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.total_balance_chart,container,false);
        myDb = new DatabaseHelper(getActivity());
        sqLiteDatabase =myDb.getWritableDatabase();
        anyChartView = view.findViewById(R.id.any_chart_view_main_balance);
        try {
            setupBalanceChart();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return view;
    }

    public void setupBalanceChart() throws ParseException {
        Cartesian cartesian = AnyChart.line();
        Cursor balance = myDb.getAllData();
        Cursor balance1 = myDb.getExpenditure();

        if(balance.getCount() ==0 || balance1.getCount()==0){
            Toast.makeText(this.getContext(),"Δεν υπαρχουν δεδομένα διαθέσιμα για προβολή",Toast.LENGTH_LONG).show();
            return;
        }

        while(balance.moveToNext()){
            amount.add(balance.getDouble(2));
            months.add(balance.getString(1));
        }

        while (balance1.moveToNext()){
            amountExp.add(balance1.getDouble(2));
            months1.add(balance1.getString(1));
        }

        cartesian.animation(true);

        List<DataEntry> dataEntries = new ArrayList<>();
        for(int y=0;y<amountExp.size();y++) {
        for(int i=0; i<amount.size();i++){
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date parse = sdf.parse(months.get(i));
            Date parse1 = sdf.parse(months1.get(y));
            Calendar c = Calendar.getInstance();
            Calendar calendar = Calendar.getInstance();
            c.setTime(parse);
            calendar.setTime(parse1);
            String month = c.getDisplayName(Calendar.MONTH,Calendar.LONG, Locale.getDefault());
            String month1 = calendar.getDisplayName(Calendar.MONTH,Calendar.LONG,Locale.getDefault());
                dataEntries.add(new CustomDataEntry(month,month1 ,amount.get(i), amountExp.get(y)));
            }
        }
        Set set = Set.instantiate();
        set.data(dataEntries);

        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
        Mapping series2Mapping = set.mapAs("{ x: 'x2', value: 'value2' }");

        Line series1 = cartesian.line(series1Mapping);
        series1.name("Εσοδα");
        series1.hovered().markers().enabled(true);
        series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series1.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series2 = cartesian.line(series2Mapping);
        series2.name("Εξοδα");
        series2.hovered().markers().enabled(true);
        series2.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series2.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        cartesian.legend().enabled(true);
        cartesian.legend().fontSize(13d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);

        anyChartView.setChart(cartesian);
    }

    private class CustomDataEntry extends ValueDataEntry {
        CustomDataEntry(String x,String x2, Number value, Number value2) {
            super(x, value);
            setValue("value2", value2);
            setValue("x2",x2);
        }
    }
}
