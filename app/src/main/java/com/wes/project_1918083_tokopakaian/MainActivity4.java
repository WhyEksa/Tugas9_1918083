package com.wes.project_1918083_tokopakaian;

import androidx.appcompat.app.AppCompatActivity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import com.wes.project_1918083_tokopakaian.databinding.ActivityMain4Binding;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Intent;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
public class MainActivity4 extends AppCompatActivity implements View.OnClickListener{
    String index;
    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;
    private ActivityMain4Binding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain4Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.fetchButton.setOnClickListener(this);

        dl = (DrawerLayout)findViewById(R.id.dl);
        abdt = new ActionBarDrawerToggle(this,dl,R.string.Open,R.string.Close);
        abdt.setDrawerIndicatorEnabled(true);
        dl.addDrawerListener(abdt);
        abdt.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView nav_view = (NavigationView)findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_pp){
                    Intent a = new Intent(MainActivity4.this, MainActivity.class);
                    startActivity(a);
                }else if (id == R.id.nav_pw){
                    Intent a = new Intent(MainActivity4.this, MainActivity2.class);
                    startActivity(a);
                }else if (id == R.id.nav_by){
                    Intent a = new Intent(MainActivity4.this, MainActivity3.class);
                    startActivity(a);
                }else if (id == R.id.nav_cy){
                    Intent a = new Intent(MainActivity4.this, MainActivity4.class);
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

    //onclik button fetch
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.fetch_button){
            index = binding.inputId.getText().toString();
            try {
                getData();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }
    //get data using api link
    public void getData() throws MalformedURLException {
        Uri uri = Uri.parse("https://coinmap.org/api/v1/venues/").buildUpon().build();
        URL url = new URL(uri.toString());
        new DOTask().execute(url);
    }
    class DOTask extends AsyncTask<URL, Void, String>{
        //connection request
        @Override
        protected String doInBackground(URL... urls) {
            URL url = urls [0];
            String data = null;
            try {
                data = NetworkUtils.makeHTTPRequest(url);
            }catch (IOException e){
                e.printStackTrace();
            }
            return data;
        }
        @Override
        protected void onPostExecute(String s){
            try {
                parseJson(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //get data json
        public void parseJson(String data) throws JSONException{
            JSONObject innerObj = null;
            try {
                innerObj = new JSONObject(data);
            }catch (JSONException e){
                e.printStackTrace();
            }
//            JSONObject innerObj = jsonObject.getJSONObject("data");
            JSONArray cityArray = innerObj.getJSONArray("venues");
            for (int i =0; i <cityArray.length(); i++){
                JSONObject obj = cityArray.getJSONObject(i);
                String Sobj = obj.get("id").toString();
                if (Sobj.equals(index)){
                    String id = obj.get("id").toString();
                    String lat = obj.get("lat").toString();
                    String lon = obj.get("lon").toString();
                    String category = obj.get("category").toString();
                    String name = obj.get("name").toString();
                    String created_on = obj.get("created_on").toString();
                    String geolocation_degrees = obj.get("geolocation_degrees").toString();

                    binding.resultId.setText(id);
                    binding.resultLat.setText(lat);
                    binding.resultLon.setText(lon);
                    binding.resultCategory.setText(category);
                    binding.resultName.setText(name);
                    binding.resultCreatedOn.setText(created_on);
                    binding.resultGeolocationDegrees.setText(geolocation_degrees);
                    break;
                }
                else{
                    binding.resultName.setText("Not Found");
                }
            }
        }
    }
}