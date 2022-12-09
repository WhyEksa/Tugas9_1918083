package com.wes.project_1918083_tokopakaian;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.wes.project_1918083_tokopakaian.databinding.ActivityMainBinding;
import android.view.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.HorizontalScrollView;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;

    RecyclerView recylerView;
    RecyclerView.LayoutManager recylerViewLayoutManager;

    String s1[], s2[],s3[];
    int images[] = {R.drawable.pakaian_pria_1,R.drawable.pakaian_pria_2,R.drawable.pakaian_pria_3,R.drawable.pakaian_pria_4};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        final OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(MyWorker.class).build();
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkManager.getInstance().enqueueUniqueWork("Notifikasi", ExistingWorkPolicy.REPLACE, request);
            }
        });

    recylerView = findViewById(R.id.recyclerView);
        s1 = getResources().getStringArray(R.array.pakaian_pria);
        s2 = getResources().getStringArray(R.array.harga_pria);
        s3 = getResources().getStringArray(R.array.star_pria);
        PakaianAdapter appAdapter = new PakaianAdapter(this,s1,s2,s3,images);
        recylerView.setAdapter(appAdapter);
        recylerViewLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recylerView.setLayoutManager(recylerViewLayoutManager);

        dl = (DrawerLayout)findViewById(R.id.dl);
        abdt = new ActionBarDrawerToggle(this,dl,R.string.Open,R.string.Close);
        abdt.setDrawerIndicatorEnabled(true);
        dl.addDrawerListener(abdt);
        abdt.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView nav_view =  (NavigationView)findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_pp){
                    Intent a = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(a);
                }else if (id == R.id.nav_pw){
                    Intent a = new Intent(MainActivity.this, MainActivity2.class);
                    startActivity(a);
                }else if (id == R.id.nav_by){
                    Intent a = new Intent(MainActivity.this, MainActivity3.class);
                    startActivity(a);
                }else if (id == R.id.nav_cy){
                    Intent a = new Intent(MainActivity.this, MainActivity4.class);
                    startActivity(a);
                }

                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return abdt.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
}