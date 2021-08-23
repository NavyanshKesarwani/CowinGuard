package com.navyanshkesarwani.cowinguard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void vaccineCertificateDownload(View view) {
        Intent intent = new Intent(this, BeneficiaryVerification.class);
        startActivity(intent);
    }

    public void checkVaccineSlots(View view) {
        Intent intent = new Intent(this, CheckVaccineSlotAvailability.class);
        startActivity(intent);
    }
}