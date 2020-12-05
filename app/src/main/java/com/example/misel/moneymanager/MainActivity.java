package com.example.misel.moneymanager;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout myDrawerLayout;
    private ActionBarDrawerToggle myToggle;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);
        myDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        myToggle = new ActionBarDrawerToggle(this,myDrawerLayout,R.string.open,R.string.close);
        myDrawerLayout.addDrawerListener(myToggle);
        myToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SetNavigationViewListener();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(myToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId())
        {
            case R.id.nav_income:{
                Intent intent = new Intent(MainActivity.this,IncomeActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.nav_outgoing:{
                Intent outintent = new Intent(MainActivity.this,ExpenditureActivity.class);
                startActivity(outintent);
                break;
            }
            case R.id.nav_info:{
                Intent outintent = new Intent(MainActivity.this,InfoActivity.class);
                startActivity(outintent);
                break;
            }
            case R.id.nav_diagrams: {
                Intent graphIntent = new Intent(MainActivity.this, DiagramsActivity.class);
                startActivity(graphIntent);
                break;
            }
        }
        myDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void SetNavigationViewListener(){
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
}
