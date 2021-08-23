package com.navyanshkesarwani.cowinguard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.common.hash.Hashing;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

public class BeneficiaryVerification extends AppCompatActivity {

    // Generate OTP Variables
    String mobile_number;
    EditText mob_number;
    TextView otp_notify;

    // Verify OTP Variables
    EditText otp_number;
    String otp;
    String token;
    String txnID;
    private static final Charset UTF_8 = StandardCharsets.UTF_8;

    // Some Other Variables
    ProgressBar progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beneficiary_verification);
        progress = findViewById(R.id.progressBar);
        progress.setVisibility(View.GONE);
    }


    public void generateOTP(View view) {
        String postUrl = "https://cdn-api.co-vin.in/api/v2/auth/public/generateOTP";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject postData = new JSONObject();
        mob_number = findViewById(R.id.mobile_number);
        mobile_number = mob_number.getText().toString();
        try {
            postData.put("mobile", mobile_number);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                postUrl,
                postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            otp_notify = findViewById(R.id.otp_notification);
                            otp_notify.setText("An OTP has been sent to your entered Mobile Number");
                            txnID = response.getString("txnId");
                        } catch (JSONException e) {
                            Toast.makeText(BeneficiaryVerification.this, "Some Error Occured!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
    });
        requestQueue.add(jsonObjectRequest);
    }


    public void verify_OTP(View view){
        otp_number = findViewById(R.id.otp);
        otp = otp_number.getText().toString();
        otp = Hashing.sha256()
                .hashString(otp, StandardCharsets.UTF_8)
                .toString();
        String postUrl = "https://cdn-api.co-vin.in/api/v2/auth/public/confirmOTP";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject postData = new JSONObject();
        try {
            postData.put("otp", (String) otp);
            postData.put("txnId", (String) txnID);
            Log.d("Verification", "OTP is: "+otp);
            Log.d("Verification", "txnID is: "+txnID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        progress.setVisibility(View.VISIBLE);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                postUrl,
                postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                             token = response.getString("token");
                             Toast.makeText(BeneficiaryVerification.this, "Verified Successfully", Toast.LENGTH_SHORT).show();
                             Intent intent = new Intent(BeneficiaryVerification.this, DownloadVaccinationCertificate.class);
                             intent.putExtra("token", token);
                             startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Toast.makeText(BeneficiaryVerification.this, "Some Error Occured!", Toast.LENGTH_SHORT).show();
                Toast.makeText(BeneficiaryVerification.this,"Some Error Occurred: "+error.networkResponse.statusCode, Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);

    }


    public void warpToDownload(View view) {
        Intent intent = new Intent(BeneficiaryVerification.this, DownloadVaccinationCertificate.class);
        startActivity(intent);
    }
}