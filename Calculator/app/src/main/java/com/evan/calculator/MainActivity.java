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
    Boolean add = false;
    Boolean minus = false;
    Boolean divide = false;
    Boolean multiply = false;
    Boolean selected = false;

    String firstValue ="";
    String secondValue ="";

    float value1,value2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resuts = (TextView) findViewById(R.id.userResult);
        userInput = (TextView)findViewById(R.id.inputOfUser);




    }
    public void process(View view){
        button= (Button)view;
        String v = button.getText().toString();
            if(v.equalsIgnoreCase(getResources().getString(R.string.zero))){
                userInput.setText(userInput.getText()+getResources().getString(R.string.zero));
            }
            else if(v.equalsIgnoreCase(getResources().getString(R.string.one))){
                userInput.setText(userInput.getText()+getResources().getString(R.string.one));
            }
            else if(v.equalsIgnoreCase(getResources().getString(R.string.two))){

                userInput.setText(userInput.getText()+getResources().getString(R.string.two));
            }
            else if(v.equalsIgnoreCase(getResources().getString(R.string.three))){

                userInput.setText(userInput.getText()+getResources().getString(R.string.three));

            }
            else if(v.equalsIgnoreCase(getResources().getString(R.string.four))){
                userInput.setText(userInput.getText()+getResources().getString(R.string.four));

            }
            else if(v.equalsIgnoreCase(getResources().getString(R.string.five))){
                userInput.setText(userInput.getText()+getResources().getString(R.string.five));

            }
            else if(v.equalsIgnoreCase(getResources().getString(R.string.six))){
                userInput.setText(userInput.getText()+getResources().getString(R.string.six));

            }
            else if(v.equalsIgnoreCase(getResources().getString(R.string.seven))){
                userInput.setText(userInput.getText()+getResources().getString(R.string.seven));

            }
            else if(v.equalsIgnoreCase(getResources().getString(R.string.eight))){
                userInput.setText(userInput.getText()+getResources().getString(R.string.eight));

            }
            else if(v.equalsIgnoreCase(getResources().getString(R.string.nine))){
                userInput.setText(userInput.getText()+getResources().getString(R.string.nine));

            }
            else if(v.equalsIgnoreCase(getResources().getString(R.string.add))){
                //get value of first calculation
                firstValue = userInput.getText().toString();

                value1 = Float.parseFloat(userInput.getText() +"");
                add = true;
                userInput.setText(null);

            }
            else if(v.equalsIgnoreCase(getResources().getString(R.string.minus))){
                //get value of first calculation
                firstValue = userInput.getText().toString();

                value1 = Float.parseFloat(userInput.getText() +"");
                minus = true;
                userInput.setText(null);



            }
            else if(v.equalsIgnoreCase(getResources().getString(R.string.multiply))){
                //get value of first calculation
                firstValue = userInput.getText().toString();

                value1 = Float.parseFloat(userInput.getText() +"");
                multiply = true;
                userInput.setText(null);


            }
            else if(v.equalsIgnoreCase(getResources().getString(R.string.divide))){
                //get value of first calculation
                firstValue = userInput.getText().toString();

                value1 = Float.parseFloat(userInput.getText() +"");
                divide = true;
                userInput.setText(null);

            }
            else if(v.equalsIgnoreCase(getResources().getString(R.string.C))){
                userInput.setText("");
                resuts.setText("0");
                add=false;
                minus=false;
                multiply=false;
                divide=false;
            }
            else if(v.equalsIgnoreCase(getResources().getString(R.string.percentage))){
                if(userInput==null){
                    userInput.setText("");
                }else {
                    float temp = Float.parseFloat(userInput.getText() + "");
                    temp = temp % 100;

                    userInput.setText(temp + "");
                }

            }
            else if(v.equalsIgnoreCase(getResources().getString(R.string.dot))){
                userInput.setText(userInput.getText()+getResources().getString(R.string.dot));

            }
            else if(v.equalsIgnoreCase(getResources().getString(R.string.erase))){
                if(userInput==null){
                    userInput.setText("");
                }
                else{
                    String f = userInput.getText()+getResources().getString(R.string.eight);
                      f.substring(0,(f.length()-1));
                     userInput.setText(f.substring(0,(f.length()-1)));
                }

            }
            else if(v.equalsIgnoreCase(getResources().getString(R.string.equal))){
                if(userInput==null){

                }
                else {
                    value2 = Float.parseFloat(userInput.getText() + "");
                    if (add) {
                        resuts.setText(value1 + value2 + "");
                        add = false;
                        userInput.setText(value1+value2+"");
                    }
                    if (minus) {
                        resuts.setText(value1 - value2 + "");
                        minus = false;
                        userInput.setText(value1 - value2 +"");

                    }
                    if (multiply) {
                        resuts.setText(value1 * value2 + "");
                        multiply = false;
                        userInput.setText(value1 * value2 +"");
                    }
                    if (divide) {
                        resuts.setText(value1 % value2 + "");
                        divide = false;
                        userInput.setText(value1 * value2 +"");
                    }
                }


            }








    }
}
