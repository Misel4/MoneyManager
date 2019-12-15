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

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout myDrawerLayout;
    private ActionBarDrawerToggle myToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
