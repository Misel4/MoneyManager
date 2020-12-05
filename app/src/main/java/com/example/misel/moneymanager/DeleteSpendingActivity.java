package com.example.misel.moneymanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DeleteSpendingActivity extends AppCompatActivity {

    Button deleteBtn, backBtn;
    TextView textAmount;
    DatabaseHelper myDb;
    private int selectedID;
    private String selectedAmount;
    private String selectedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_spending);

        deleteBtn = findViewById(R.id.delete_button_spending);
        backBtn = findViewById(R.id.but_backFromEdit2);
        textAmount = findViewById(R.id.text_edit_spending);
        myDb = new DatabaseHelper(this);

        Intent receiveIntent = getIntent();

        selectedID = receiveIntent.getIntExtra("id",-1);

        selectedAmount = receiveIntent.getStringExtra("part3");

        selectedText = receiveIntent.getStringExtra("amount1");

        textAmount.setText(selectedText);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDb.deleteItemSpending(selectedID,selectedAmount);
                textAmount.setText("");
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homePage = new Intent(DeleteSpendingActivity.this, MainActivity.class);
                startActivity(homePage);
                finish();
            }
        });
    }
}
