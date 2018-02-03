package com.example.kelsayed.converter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ConverterActivity extends AppCompatActivity {
    private BigDecimal usdAmount = new BigDecimal("0.00");
    private BigDecimal inrAmount = new BigDecimal("0.00");
    private final BigDecimal usdRate = new BigDecimal("0.016");
    private final BigDecimal inrRate = new BigDecimal("63.89");
    private EditText usdInput;
    private EditText inrInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);

        usdInput = findViewById(R.id.usd_amount);
        inrInput = findViewById(R.id.inr_amount);

        Button convertButton = findViewById(R.id.convert_button);

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convert();
            }
        });
    }

    private void convert() {
        String usdInputStr = usdInput.getText().toString();
        String inrInputStr = inrInput.getText().toString();

        // Convert USD to INR
        if (usdInput.isFocused()) {
            if (usdInputStr.isEmpty() || usdInputStr.equals(".")) {
                usdInput.setError("Please enter a value.");
                return;
            }
            usdAmount = new BigDecimal(usdInputStr);
            inrAmount = usdAmount.multiply(inrRate).setScale(2, RoundingMode.HALF_UP);
            inrInput.setText(inrAmount.toString());
        }

        // Convert INR to USD
        if (inrInput.isFocused()) {
            if (inrInputStr.isEmpty() || inrInputStr.equals(".")) {
                inrInput.setError("Please enter a value.");
                return;
            }
            inrAmount = new BigDecimal(inrInputStr);
            usdAmount = inrAmount.multiply(usdRate).setScale(2, RoundingMode.HALF_UP);
            usdInput.setText(usdAmount.toString());
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("USDAmount", usdAmount.toString());
        outState.putString("INRAmount", inrAmount.toString());
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        usdAmount = new BigDecimal(savedInstanceState.getString("USDAmount"));
        inrAmount = new BigDecimal(savedInstanceState.getString("INRAmount"));
    }
}