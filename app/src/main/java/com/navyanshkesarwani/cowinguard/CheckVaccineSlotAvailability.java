package com.navyanshkesarwani.cowinguard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CheckVaccineSlotAvailability extends AppCompatActivity {
    String pincode;
    String date;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_vaccine_slot_availability);
        progressBar = findViewById(R.id.availabilityProgressBar);
        progressBar.setVisibility(View.GONE);
    }

    public void search_availability(View view) {
        DatePicker datePicker = (DatePicker) findViewById(R.id.date);
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1;
        int year = datePicker.getYear();
        date = day+"-"+month+"-"+year;
        EditText pincode_field = findViewById(R.id.pincode);
        pincode = pincode_field.getText().toString();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/findByPin?pincode="+pincode+"&date="+date;
        progressBar.setVisibility(View.VISIBLE);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray sessions = response.getJSONArray("sessions");
                            Log.d("JSONData", sessions.toString());
                            if (sessions.length() > 0) {
                                Intent showAvailability = new Intent(CheckVaccineSlotAvailability.this, ShowAvailability.class);
                                showAvailability.putExtra("sessions", sessions.toString());
                                progressBar.setVisibility(View.GONE);
                                startActivity(showAvailability);
                            }
                            else{
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(CheckVaccineSlotAvailability.this, "No sessions available in the given Pincode", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CheckVaccineSlotAvailability.this, "Some Error Occurred! Please try again later!", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });
        queue.add(jsonObjectRequest);
    }
}