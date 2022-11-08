package com.example.qrscanner;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    //Makes permission granted permanent
    public static final int CAMERA_REQUEST_PERMISSION = 1;
    public static final int SCANNING_REQUEST_CODE = 2;
    Button button;

ActivityResultLauncher<Intent> activityResultLauncher=registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult scanning_result) {
                int result = scanning_result.getResultCode();
                Intent data =scanning_result.getData();
                new Intent(MainActivity.this,ScanningActivity.class);
            }
        }

);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.btnscan);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ScanningActivity.class);
                activityResultLauncher.launch(intent);
                StartScanningActivity();
                //Start Scanning
            }

        });

    }

    //Checks if android version is better than api
    private void StartScanningActivity (){
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //start Scanning
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                //asks for permission
                requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_PERMISSION);
            } else {
                //Start scanning if passes check
                activityResultLauncher.launch(intent);
            }
        }else{
            activityResultLauncher.launch(intent);

            }
            }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode== CAMERA_REQUEST_PERMISSION){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(MainActivity.this,ScanningActivity.class);
                activityResultLauncher.launch(intent);

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==SCANNING_REQUEST_CODE){
            if (resultCode == RESULT_OK){
                button.setText(data.getStringExtra("scanning_result"));
            }
        }
    }
}