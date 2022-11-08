package com.example.qrscanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

public class ScanningActivity extends AppCompatActivity {
    private CodeScanner mCodeScanner;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanning);

        // Starts Scanning preview
        CodeScannerView scannerView =  findViewById(R.id.scanner_view);
        mCodeScanner =  new  CodeScanner (ScanningActivity.this,scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override//Will hold the value that is scanned and pass it to MainACtivity
            public void onDecoded(@NonNull Result result) {
                Intent intent = new Intent();
                intent.putExtra("scanning_result",(result.getText()));
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        mCodeScanner.startPreview();
    }
}