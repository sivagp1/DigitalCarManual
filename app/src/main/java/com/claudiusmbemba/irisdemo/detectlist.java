package com.claudiusmbemba.irisdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.claudiusmbemba.irisdemo.models.IrisData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class detectlist extends AppCompatActivity {

    ListView listview;
    String[] ListElements = new String[]{};
    String[] symbols = new String[]{"ABS light","air bag","battery","brake","check engine/malfunction indicator","door ajar","fog lamp","high beam","oil pressure system","parking lights","power steering","seat belt","security alert"};
    IrisData food_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detectlist);
        Toolbar mTopToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        mTopToolbar.setTitle("Detected Lights");
        setSupportActionBar(mTopToolbar);

        listview = (ListView) findViewById(R.id.listview1);
        final List< String > ListElementsArrayList = new ArrayList< String >
                (Arrays.asList(ListElements));


        final ArrayAdapter< String > adapter = new ArrayAdapter < String >
                (detectlist.this, android.R.layout.simple_list_item_1,
                        ListElementsArrayList);


        listview.setAdapter(adapter);
        food_item = (IrisData) getIntent().getParcelableExtra(MainActivity.FOOD_RESULT);
        for (int i=0;i<food_item.getClassifications().size();i++){
            if(food_item.getClassifications().get(i).getProbability()*100>60){
                ListElementsArrayList.add(food_item.getClassifications().get(i).getClass_());
                adapter.notifyDataSetChanged();
            }
        }
        if(ListElementsArrayList.size()==0){
            Intent intent=new Intent(detectlist.this,snap.class);
            startActivity(intent);
        }
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String s = listview.getItemAtPosition(i).toString();
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                switch (s){
                    case "ABS light":startActivity(new Intent(getApplicationContext(),abslight.class));break;
                    case "air bag":startActivity(new Intent(getApplicationContext(),airbag.class));break;
                    case "battery":startActivity(new Intent(getApplicationContext(),battery.class));break;
                    case "brake" :startActivity(new Intent(getApplicationContext(),showbreak.class));break;
                    case "check engine/malfunction indicator":startActivity(new Intent(getApplicationContext(),check.class));break;
                    case "door ajar":startActivity(new Intent(getApplicationContext(),door.class));break;
                    case "fog lamp":startActivity(new Intent(getApplicationContext(),fog.class));break;
                    case "high beam":startActivity(new Intent(getApplicationContext(),highbeam.class));break;
                    case "oil pressure system":startActivity(new Intent(getApplicationContext(),oil.class));break;
                    case "parking lights":startActivity(new Intent(getApplicationContext(),parking.class));break;
                    case "power steering":startActivity(new Intent(getApplicationContext(),steering.class));break;
                    case "seat belt": startActivity(new Intent(getApplicationContext(),seatbelt.class));break;
                    case "security alert":startActivity(new Intent(getApplicationContext(),security.class));break;
                }

               // adapter.dismiss(); // If you want to close the adapter
            }
        });
        //ListElementsArrayList.add(GetValue.getText().toString());
       // adapter.notifyDataSetChanged();
    }
}
