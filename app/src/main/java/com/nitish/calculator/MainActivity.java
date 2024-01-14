package com.nitish.calculator;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    AppCompatButton btnMod, btnAdd, btnSub, btnMul, btnDiv, btnDot, btnEqual, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnAC, btnX;
    TextView tvInput, tvResult;

    private boolean status = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }

    private void init() {
        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btnMod = findViewById(R.id.btnMod);
        btnMul = findViewById(R.id.btnMul);
        btnAdd = findViewById(R.id.btnAdd);
        btnSub = findViewById(R.id.btnSub);
        btnDiv = findViewById(R.id.btnDiv);
        btnDot = findViewById(R.id.btnDot);
        btnEqual = findViewById(R.id.btnEqual);
        btnAC = findViewById(R.id.btnAC);
        btnX = findViewById(R.id.btnX);

        tvInput = findViewById(R.id.tvInput);
        tvResult = findViewById(R.id.tvResult);
    }

    public void onLasClearClick(View view) {
        String text = "";
        if (!tvInput.getText().toString().isEmpty() && tvInput.length() > 1) {

            Log.d("TAG", "onLasClearClick: " + tvInput.getText().toString().substring(tvInput.length() - 2, tvInput.length() - 1));

            if (tvInput.getText().toString().substring(tvInput.length() - 1).charAt(0) == ' ') {
                text = tvInput.getText().toString().substring(0, tvInput.length() - 3);
            } else {
                text = tvInput.getText().toString().substring(0, tvInput.length() - 1);
            }

        }

        try {

            tvInput.setText(text);
            if (tvInput.length() >=1&&tvInput.getText().toString().charAt(tvInput.length()-1)!=' '){
                onEqual();
            }

            Log.d("TAG", "clear");

        } catch (Exception e) {

            tvInput.setText("");
            tvResult.setText("");

            Log.d("TAG", e.toString());

        }
    }

    public void onAllClearClick(View view) {
        tvResult.setText("");
        tvInput.setText("");
    }

    public void onOperatorClick(View view) {
        tvInput.append(" " + ((AppCompatButton) view).getText().toString() + " ");
    }

    public void onDigitClick(View view) {
        try {
            Log.d("TAG", "true " + status);
            tvInput.append(((AppCompatButton) view).getText().toString());
        } catch (Exception e) {
            tvResult.setText("Error");
        }
        onEqual();
    }

    public void onEqualClick(View view) {
        onEqual();
        tvInput.setText(tvResult.getText().toString());
    }

    public void onDotClick(View view) {
        if (!tvInput.getText().toString().equals("")) {
            char lastCh = tvInput.getText().toString().substring(tvInput.length() - 1).charAt(0);
            int lastvalue = (int) lastCh;
            if (lastvalue >= 48 && lastvalue <= 57)
                tvInput.append(".");
            else {
                tvInput.append("0.");
            }
        } else {
            tvInput.append("0.");
        }
        onEqual();
    }

    public void onEqual() {
        if (tvResult.getText().toString().length()>10){
            tvResult.setTextSize(40f);
        }
        if (!status) {
            String text = tvInput.getText().toString();
            try {
                double result = expression(text);
                String resultText = String.valueOf(result);
                if (resultText.contains(".0")) {
                    tvResult.setText(resultText.substring(0, resultText.length() - 2));
                } else {
                    tvResult.setText(resultText);
                }
            } catch (Exception e) {
                tvResult.setText("Error");
                Log.d("Error", e.toString());
            }
        }
    }


    private double expression(String text) throws IllegalAccessException {

        String[] token = text.split(" ");
        Log.d("TAG", "token : "+ Arrays.toString(token));
        double[] num = new double[token.length / 2 + 1];
        char[] op = new char[token.length / 2];
        Log.d("TAG", "come" + token.length);
        for (int i = 0, j = 0; i < token.length; i++) {
            Log.d("TAG", "expression: " + num[j]);
            if (i % 2 == 0) {
                if (!token[i].isEmpty())
                    num[j] = Double.parseDouble(token[i]);
            } else {
                op[j++] = token[i].charAt(0);
            }
        }

        double result = num[0];
        for (int i = 0; i < op.length; i++) {
            double nextNum = num[i + 1];

            switch (op[i]) {
                case '+' -> result += nextNum;
                case '-' -> result -= nextNum;
                case '*' -> result *= nextNum;
                case '/' -> result /= nextNum;
                case '%' -> result %= nextNum;
                default -> throw new IllegalAccessException("Invalid operator " + op[i]);
            }
        }
        return result;
    }
}