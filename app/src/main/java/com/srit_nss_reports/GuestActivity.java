package com.srit_nss_reports;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class GuestActivity extends AppCompatActivity {

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);

        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);


        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final NavigationView nav_view=(NavigationView)findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();


            if(id==R.id.existed_reports){
                Toast.makeText(GuestActivity.this,"Existed Report",Toast.LENGTH_SHORT).show();
                openExisted();
            }
            else if(id==R.id.newsletters){
                Toast.makeText(GuestActivity.this,"Newsletter",Toast.LENGTH_SHORT).show();
                openNewsletters();
            }
            else if(id==R.id.graphs){
                Toast.makeText(GuestActivity.this,"Graphs",Toast.LENGTH_SHORT).show();
                openGraphs();
            }

            return true;
        });
    }
    public void openExisted(){
        Intent intent = new Intent(this,ExistedReports.class);
        startActivity(intent);
    }
    public void openNewsletters(){
        Intent intent = new Intent(this,Newsletters.class);
        startActivity(intent);
    }
    public void openGraphs(){
        Intent intent = new Intent(this,Graphs.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}