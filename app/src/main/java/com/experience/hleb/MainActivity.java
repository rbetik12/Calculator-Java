package com.experience.hleb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String equation;
    private double result;
    private int MAX_OUTPUT_LENGTH;
    private int MAX_SYMBOLS_IN_DOUBLE_WEXP;
    private ArrayList<String> functions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        equation = " ";
        result = 0.0;
        MAX_OUTPUT_LENGTH = 19;
        MAX_SYMBOLS_IN_DOUBLE_WEXP = 9;
        functions = new ArrayList<String>();
        functions.add("+");
        functions.add("-");
        functions.add("*");
        functions.add("/");
    }

    public void onClickNumbers(View view){

    }
}


