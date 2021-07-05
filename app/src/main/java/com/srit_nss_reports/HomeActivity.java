package com.srit_nss_reports;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {


    private DrawerLayout d1;
    private ActionBarDrawerToggle abdt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        d1=(DrawerLayout)findViewById(R.id.d1);
        abdt = new ActionBarDrawerToggle(this, d1, R.string.open, R.string.close);
        abdt.setDrawerIndicatorEnabled(true);

        d1.addDrawerListener(abdt);
        abdt.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final NavigationView nav_view=(NavigationView)findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if(id==R.id.create_report){
                Toast.makeText(HomeActivity.this,"Create Report",Toast.LENGTH_SHORT).show();
                openNew();
            }
            else if(id==R.id.existed_reports){
                Toast.makeText(HomeActivity.this,"Existed Report",Toast.LENGTH_SHORT).show();
                openExisted();
            }
            else if(id==R.id.newsletters){
                Toast.makeText(HomeActivity.this,"Newsletter",Toast.LENGTH_SHORT).show();
                openNewsletters();
            }
            else if(id==R.id.graphs){
                Toast.makeText(HomeActivity.this,"Graphs",Toast.LENGTH_SHORT).show();
                openGraphs();
            }

            return true;
        });



    }
    public void openNew(){
        Intent intent = new Intent(this,NewActivity.class);
        startActivity(intent);
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
    public boolean onOptionsItemSelected( MenuItem item)
    {
        if(abdt.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}