package com.evan.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private Button button;
    private TextView userInput, resuts;
    private Boolean add = false;
    private Boolean minus = false;
    private Boolean divide = false;
    private Boolean multiply = false;
    private Boolean selected = false;

    private String firstValue = "";
    private String secondValue = "";


    private boolean gotvalue;
    private float value1, value2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gotvalue = false;

        resuts = (TextView) findViewById(R.id.userResult);
        userInput = (TextView) findViewById(R.id.inputOfUser);


    }

    //method called when the buttons are pressed
    public void process(View view) {
        //cast the view to button
        button = (Button) view;
        //get the string of the button
        String v = button.getText().toString();
        if (v.isEmpty() || v == null) {
            Log.d("Nothing", "process: put something in there. ");
        } else {
            //see what action user took and act accordingly...
            if (v.equalsIgnoreCase(getResources().getString(R.string.zero))) {
                gotvalue = true;
                userInput.setText(userInput.getText() + getResources().getString(R.string.zero));
            } else if (v.equalsIgnoreCase(getResources().getString(R.string.one))) {
                gotvalue = true;
                userInput.setText(userInput.getText() + getResources().getString(R.string.one));
            } else if (v.equalsIgnoreCase(getResources().getString(R.string.two))) {
                gotvalue = true;
                userInput.setText(userInput.getText() + getResources().getString(R.string.two));
            } else if (v.equalsIgnoreCase(getResources().getString(R.string.three))) {
                gotvalue = true;

                userInput.setText(userInput.getText() + getResources().getString(R.string.three));

            } else if (v.equalsIgnoreCase(getResources().getString(R.string.four))) {
                gotvalue = true;

                userInput.setText(userInput.getText() + getResources().getString(R.string.four));

            } else if (v.equalsIgnoreCase(getResources().getString(R.string.five))) {
                gotvalue = true;

                userInput.setText(userInput.getText() + getResources().getString(R.string.five));

            } else if (v.equalsIgnoreCase(getResources().getString(R.string.six))) {
                gotvalue = true;

                userInput.setText(userInput.getText() + getResources().getString(R.string.six));

            } else if (v.equalsIgnoreCase(getResources().getString(R.string.seven))) {
                gotvalue = true;

                userInput.setText(userInput.getText() + getResources().getString(R.string.seven));

            } else if (v.equalsIgnoreCase(getResources().getString(R.string.eight))) {
                gotvalue = true;

                userInput.setText(userInput.getText() + getResources().getString(R.string.eight));

            } else if (v.equalsIgnoreCase(getResources().getString(R.string.nine))) {
                gotvalue = true;

                userInput.setText(userInput.getText() + getResources().getString(R.string.nine));

            } else if (v.equalsIgnoreCase(getResources().getString(R.string.add))) {
                if (!gotvalue) {
                    Log.d("main", "process: look");
                } else {
                    //get value of first calculation
                    firstValue = userInput.getText().toString();

                    value1 = Float.parseFloat(userInput.getText() + "");
                    add = true;
                    userInput.setText(null);
                }

            } else if (v.equalsIgnoreCase(getResources().getString(R.string.minus))) {
                if (!gotvalue) {
                    Log.d("main", "process: look");
                } else {
                    //get value of first calculation
                    firstValue = userInput.getText().toString();

                    value1 = Float.parseFloat(userInput.getText() + "");
                    minus = true;
                    userInput.setText(null);
                }

            } else if (v.equalsIgnoreCase(getResources().getString(R.string.multiply))) {
                if (!gotvalue) {
                    Log.d("main", "process: look");
                } else {
                    //get value of first calculation
                    firstValue = userInput.getText().toString();

                    value1 = Float.parseFloat(userInput.getText() + "");
                    multiply = true;
                    userInput.setText(null);
                }


            } else if (v.equalsIgnoreCase(getResources().getString(R.string.divide))) {
                if (!gotvalue) {
                    Log.d("main", "process: look");
                } else {
                    //get value of first calculation
                    firstValue = userInput.getText().toString();

                    value1 = Float.parseFloat(userInput.getText() + "");
                    divide = true;
                    userInput.setText(null);
                }

            } else if (v.equalsIgnoreCase(getResources().getString(R.string.C))) {
                userInput.setText("");
                resuts.setText("0");
                add = false;
                minus = false;
                multiply = false;
                divide = false;
                gotvalue = false;
            } else if (v.equalsIgnoreCase(getResources().getString(R.string.percentage))) {
                if (!gotvalue) {
                    Log.d("main", "process: look");
                } else {
                    if (userInput == null) {
                        userInput.setText("");
                    } else {
                        float temp = Float.parseFloat(userInput.getText() + "");
                        temp = temp % 100;

                        userInput.setText(temp + "");
                    }
                }

            } else if (v.equalsIgnoreCase(getResources().getString(R.string.dot))) {
                userInput.setText(userInput.getText() + getResources().getString(R.string.dot));

            } else if (v.equalsIgnoreCase(getResources().getString(R.string.erase))) {
                if (userInput == null) {
                    userInput.setText("");
                } else {
                    String f = userInput.getText() + getResources().getString(R.string.eight);
                    f.substring(0, (f.length() - 1));
                    userInput.setText(f.substring(0, (f.length() - 1)));
                }

                //if the user pressed the equals button, you want to process results
            } else if (v.equalsIgnoreCase(getResources().getString(R.string.equal))) {
                if (userInput == null) {

                } else {
                    value2 = Float.parseFloat(userInput.getText() + "");
                    if (add) {
                        resuts.setText(value1 + value2 + "");
                        add = false;
                        userInput.setText(value1 + value2 + "");
                    }
                    if (minus) {
                        resuts.setText(value1 - value2 + "");
                        minus = false;
                        userInput.setText(value1 - value2 + "");

                    }
                    if (multiply) {
                        resuts.setText(value1 * value2 + "");
                        multiply = false;
                        userInput.setText(value1 * value2 + "");
                    }
                    if (divide) {
                        resuts.setText(value1 % value2 + "");
                        divide = false;
                        userInput.setText(value1 * value2 + "");
                    }
                }


            }
        }

    }
}
