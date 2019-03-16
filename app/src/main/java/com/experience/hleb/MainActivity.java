package com.experience.hleb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.abs;

public class MainActivity extends AppCompatActivity {

    private String equation;
    private double result;
    private int MAX_OUTPUT_LENGTH;
    private int MAX_SYMBOLS_IN_DOUBLE_WEXP;
    private List<String> functions;
    public MatchParser parser;
    private TextView midResultEquation;
    private TextView resultEquation;
    private HorizontalScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        equation = "";
        result = 0.0;
        MAX_OUTPUT_LENGTH = 19;
        MAX_SYMBOLS_IN_DOUBLE_WEXP = 9;
        parser = new MatchParser();
        midResultEquation = findViewById(R.id.midResultEquation);
        resultEquation = findViewById(R.id.resultEquation);
        scrollView = findViewById(R.id.scroll);
        functions = new ArrayList<>(Arrays.asList("+", "-", "*", "/"));
    }

    public void onClickNumbers(View view) {
        switch (view.getId()) {
            case R.id.buttonZero:
                equation += ('0');
                break;
            case R.id.buttonOne:
                equation += ('1');
                break;
            case R.id.buttonTwo:
                equation += ('2');
                break;
            case R.id.buttonThree:
                equation += ('3');
                break;
            case R.id.buttonFour:
                equation += ('4');
                break;
            case R.id.buttonFive:
                equation += ('5');
                break;
            case R.id.buttonSix:
                equation += ('6');
                break;
            case R.id.buttonSeven:
                equation += ('7');
                break;
            case R.id.buttonEight:
                equation += ('8');
                break;
            case R.id.buttonNine:
                equation += ('9');
                break;
            case R.id.buttonPoint:
                equation += (".");
                break;
            case R.id.buttonLeftPar:
                equation += ("(");
                break;
            case R.id.buttonRightPar:
                equation += (")");
                break;
        }
        resultEquation.setText(equation);
        scrollLeft();
        if (findFunc()) {
            Log.d("CALC", equation);
            count(false);
        }
    }

    public void onClickFunctions(View view) {
        switch (view.getId()) {
            case R.id.buttonAdd:
                equation += ("+");
                break;
            case R.id.buttonMinus:
                equation += ("-");
                break;
            case R.id.buttonDivide:
                equation += ("/");
                break;
            case R.id.buttonMultiply:
                equation += ("*");
                break;
        }
        resultEquation.setText(equation);
        scrollLeft();
        Log.d("CALC", equation);
        count(false);
    }

    public void onClickUtils(View view) {
        switch (view.getId()) {
            case R.id.buttonEquals:
                count(true);
                midResultEquation.setText(null);
                break;
            case R.id.buttonClearEverything:
                equation = "";
                result = 0.0;
                resultEquation.setText(null);
                midResultEquation.setText(null);
                break;
            case R.id.buttonClear:
                if (equation.length() == 1) {
                    midResultEquation.setText("");
                    equation = "";
                } else if (equation.length() == 0)
                    equation = "";
                else
                    equation = equation.substring(0, equation.length() - 1);
                resultEquation.setText(equation);
                Log.d("CALC", equation);
                count(false);
        }
    }

    private boolean checkForInt() {
        /*
          Checks if result can be put in output as integer(e.g. 12.0 -> 12)
          @param [result] Result of equation
          @return [true] if Int [false] if not
         */
        String resultToStr = Double.toString(result);

        return resultToStr.substring(resultToStr.length() - 2).equals(".0");
    }

    private void roundResult() {
        /*
          Rounds double result, if it contains lots of digits with exponent.
          @return nothing
         */
        String resultToStr = Double.toString(result);

        if (resultToStr.length() > MAX_OUTPUT_LENGTH && resultToStr.contains("E")) {
            try {
                double exp = abs(Double.parseDouble(resultToStr.substring(resultToStr.indexOf("E") + 1, resultToStr.lastIndexOf(resultToStr) + 1)));
                result = Math.round(result * pow(10.0, exp + MAX_SYMBOLS_IN_DOUBLE_WEXP)) / pow(10.0, exp + MAX_SYMBOLS_IN_DOUBLE_WEXP);
            } catch (StringIndexOutOfBoundsException ignored) {
            }
        }
    }

    private void scrollLeft() {
        /*
          Shifts content of editText to the right, when called
          @params none
          @return nothing
         */
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_RIGHT);
            }
        });
    }

    private void count(boolean isEqualsPressed) {
        /*
          Counts the expression result, catches possible exceptions
         */

        parser = new MatchParser();

        try {
            Log.d("CALC", "Equation before parsing: " + equation);
            result = parser.Parse(equation);
            Log.d("CALC", "Result: " + Double.toString(result));
        } catch (IllegalArgumentException e) {
            Log.d("CALC", e.getMessage());
            if (isEqualsPressed) {
                midResultEquation.setText(null);
                equation = "";
                setOutput(isEqualsPressed);
            }
            return;
        } catch (ArithmeticException e) {
            Log.d("CALC", e.getMessage());
            equation = "";
            result = 0.0;
            return;
        }
        setOutput(isEqualsPressed);
    }

    private void setOutput(boolean isEqualsPressed) {
        /*
          Sets output, whether equal button was pressed or not
         */
        String strResult;

        if (checkForInt())
            strResult = Integer.toString((int) result);
        else {
            roundResult();
            strResult = Double.toString(result);
        }
        if (isEqualsPressed) {
            resultEquation.setText(strResult);
            equation = strResult;
        } else {
            midResultEquation.setText(strResult);
        }
    }

    private boolean findFunc() {
        /*

          Finds functions in equation string. Returns true if function was find or false
          @return boolean
         */
        for (int i = 0; i < equation.length(); i++) {
            for (int j = 0; j < functions.size(); j++) {
                if (equation.charAt(i) == functions.get(j).charAt(0))
                    return true;
            }
        }
        return false;
    }
}




