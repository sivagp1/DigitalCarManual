package com.claudiusmbemba.irisdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class snap extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snap);
        ImageView img=(ImageView)findViewById(R.id.snap);
        Button btn=(Button)findViewById(R.id.retry);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "null", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(snap.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
