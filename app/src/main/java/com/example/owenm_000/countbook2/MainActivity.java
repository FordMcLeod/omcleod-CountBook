package com.example.owenm_000.countbook2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    ListView listView ;
    final int INIT_CODE = 1;
    final int UPDATE_CODE = 2;
    final String FILENAME = "file.sav";
    public ArrayList<Counter> counters = new ArrayList<Counter>();;
    public ArrayAdapter<Counter> adapter;
    private int currentCounter = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button);
        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);
        Intent intent;



        adapter = new ArrayAdapter<Counter>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, counters);
        listView.setAdapter(adapter);


        loadFromFile();
        refreshScreen();

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                Intent intent = new Intent(MainActivity.this,DetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("item", new Counter());
                intent.putExtras(bundle);
                startActivityForResult(intent,INIT_CODE);

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;
                currentCounter = position;
                Intent intent = new Intent(MainActivity.this,DetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("item", (Counter)listView.getItemAtPosition(position));
                intent.putExtras(bundle);
                startActivityForResult(intent,UPDATE_CODE);


                // ListView Clicked item value
                Counter  itemValue    = (Counter) listView.getItemAtPosition(position);


            }

        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        refreshScreen();
        adapter = new ArrayAdapter<Counter>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, counters);
        listView.setAdapter(adapter);
        loadFromFile();

    }

        // Assign adapter to ListView
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        try {
            super.onActivityResult(requestCode, resultCode, intent);



            if (requestCode == INIT_CODE && resultCode == RESULT_OK) {



                Gson gson = new Gson();
                String gsonString = intent.getStringExtra("newCounter");
                Counter newCounter = gson.fromJson(gsonString, Counter.class);
                counters.add(newCounter);
                refreshScreen();

            } else if (requestCode == UPDATE_CODE && resultCode == RESULT_OK) {

                Gson gson = new Gson();
                String gsonString = intent.getStringExtra("newCounter");
                Counter newCounter = gson.fromJson(gsonString, Counter.class);
                if(newCounter == null){
                    counters.remove(currentCounter);
                }
                else{
                    counters.set(currentCounter, newCounter);
                }
                refreshScreen();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void refreshScreen() {
        saveInFile();
        adapter.notifyDataSetChanged();

    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Counter>>() {}.getType();
            ArrayList<Counter> check = new ArrayList<Counter>();
            counters = gson.fromJson(in,listType);


        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            counters = new ArrayList<Counter>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }

    }

    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(counters,writer);
            writer.flush();
            fos.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}