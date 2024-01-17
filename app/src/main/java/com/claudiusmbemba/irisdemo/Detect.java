package com.claudiusmbemba.irisdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;
import com.claudiusmbemba.irisdemo.models.IrisData;


public class Detect extends AppCompatActivity {

    TextView tv ; //(TextView) findViewById(R.id.detprobs);
    IrisData food_item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detect);

        food_item = (IrisData) getIntent().getParcelableExtra(MainActivity.FOOD_RESULT);
        tv = (TextView) findViewById(R.id.detprobs);
        loadText();
    }
    private void loadText(){
        String s = "";
        IrisData irisData;
        for (int i = 0; i < food_item.getClassifications().size(); i++) {
            s+= food_item.getClassifications().get(i).getClass_()+" : "+String.valueOf((int) food_item.getClassifications().get(i).getProbability()*100)+"%"+"\n";
        }

         tv.setMovementMethod(new ScrollingMovementMethod());
        tv.setText(s);
    }
}
