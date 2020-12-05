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
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.example.misel.moneymanager.DatabaseHelper;
import com.example.misel.moneymanager.MainActivity;
import com.example.misel.moneymanager.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainChartFragment extends Fragment {
    AnyChartView anyChartView;
    DatabaseHelper myDb;
    SQLiteDatabase sqLiteDatabase;
    ArrayList<Double> amount = new ArrayList<>();
    ArrayList<String> months = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_chart_fragment,container,false);

        myDb = new DatabaseHelper(getActivity());
        sqLiteDatabase =myDb.getWritableDatabase();
        anyChartView = view.findViewById(R.id.any_chart_view_main);
        try {
            setupColumnChart();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return view;
    }

    public void setupColumnChart() throws ParseException {
        Cartesian cartesian = AnyChart.column();
        Cursor incBalance = myDb.getAllData();
        Cursor expBalance = myDb.getExpenditure();

        if(incBalance.getCount() ==0){
            Toast.makeText(this.getContext(),"Δεν υπαρχουν δεδομένα διαθέσιμα για προβολή",Toast.LENGTH_LONG).show();
            return;
        }

        while(incBalance.moveToNext()){
            months.add(incBalance.getString(1));
            amount.add(incBalance.getDouble(2));
        }
        List<DataEntry> dataEntries = new ArrayList<>();
        for(int i=0; i<amount.size();i++){
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date parse = sdf.parse(months.get(i));
            Calendar c = Calendar.getInstance();
            c.setTime(parse);
            String month = c.getDisplayName(Calendar.MONTH,Calendar.LONG, Locale.getDefault());
            dataEntries.add(new ValueDataEntry(month, amount.get(i)));
        }

        Column column = cartesian.column(dataEntries);

        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("${%Value}{groupsSeparator: }");
        cartesian.animation(true);
        cartesian.title("Εσοδα");

        cartesian.yScale().minimum(0d);

        cartesian.yAxis(0).labels().format("${%Value}{groupsSeparator: }");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        cartesian.xAxis(0).title("Μήνας");
        cartesian.yAxis(0).title("Ποσό");

        anyChartView.setChart(cartesian);


    }
    private class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(String x, Double value, Double value2) {
            super(x, value);
            setValue("value2", value2);
        }

    }
}
