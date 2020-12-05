package com.example.misel.moneymanager.fragments;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.misel.moneymanager.DatabaseHelper;
import com.example.misel.moneymanager.R;

public class MainBalanceFragment extends Fragment {
    TextView balanceTxt;
    DatabaseHelper myDb;
    SQLiteDatabase sqLiteDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_balance_fragment_layout,container,false);
        balanceTxt = view.findViewById(R.id.balance_textView);
        myDb = new DatabaseHelper(getActivity());
        Balance();

        return view;
    }

    public void Balance(){
        int data = myDb.getTotalBalance();
        StringBuffer buffer = new StringBuffer();
        buffer.append( +data +"€");
        balanceTxt.setText(buffer);
    }
}
