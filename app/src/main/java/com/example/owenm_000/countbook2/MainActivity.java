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
import android.widget.TextView;
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


    // Initializng variables
    ListView listView ;

    // Codes for startActivityResult()
    final int INIT_CODE = 1;
    final int UPDATE_CODE = 2;

    // Initializing the .sav, arraylist and array adapter to use ListView
    final String FILENAME = "file.sav";
    public ArrayList<Counter> counters = new ArrayList<Counter>();;
    public ArrayAdapter<Counter> adapter;

    // Indicates what the last selected counter position was, used for deletion
    private int currentCounter = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button);
        // Get ListView object from xml

        //Creating Listview
        listView = (ListView) findViewById(R.id.list);
        Intent intent;

        //Initializing counters. stores the current counters
        counters = new ArrayList<Counter>();

        // Initializing adapter, use to convert counters to ListView usable assets
        adapter = new ArrayAdapter<Counter>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, counters);

        // Setting ListView's adapter
        listView.setAdapter(adapter);

        //Loading any existing counters from file.sav
        loadFromFile();

        //Calling refreshScreen which will save to file, inform adapter there is changes and update the counter counter
        refreshScreen();


        // button which will start the DetailsActivity sending INIT_CODE to indicate that a new counter is being made
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);

                // Passing in a new counter to the details activity
                Intent intent = new Intent(MainActivity.this,DetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("item", new Counter());
                intent.putExtras(bundle);
                startActivityForResult(intent,INIT_CODE);

            }
        });

        // listView item click listener,
        // starts the DetailsActivity sending UPDATE_CODE to indicate that a counter is being edited

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;
                currentCounter = position;

                // Passing in the counter selected by the user
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

    //We need to Override onStart() b/c the ListView needs to be updated every time
    // that the detailActivity Finishes and it goes back in the activity stack

    @Override
    protected void onStart() {
        super.onStart();
        adapter = new ArrayAdapter<Counter>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, counters);
        listView.setAdapter(adapter);
        refreshScreen();
        loadFromFile();

    }

    //counterData(): Simple function to update a textview onscreen with the current number of counters
    private void counterData(){
        TextView currVal = (TextView) findViewById(R.id.counterData);
        currVal.setText("Number of counters:" + counters.size());
    }

    @Override

    // onActivityResult: handler that is called when DetailsActivity finishes, as it was
    //  started using startActivityForResult
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        try {
            super.onActivityResult(requestCode, resultCode, intent);
            // The requestCode indicates whether this DetailsActivity was for an existing counter
            //  or a new counter
            // Recieves the counter via GSON

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

                // Expected case when the user hits delete, it sends a null counter back indicating to delete that counter
                if(newCounter == null){
                    counters.remove(currentCounter);
                    adapter.notifyDataSetChanged();
                }

                // Otherwise update the counter with the newCounter values
                else{
                    counters.set(currentCounter, newCounter);
                }

                //call to refresh screen
                refreshScreen();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void refreshScreen() {
        //Saves to file.sav
        saveInFile();

        //informs adapter there was an update to the data set
        adapter.notifyDataSetChanged();

        //updates the number counter
        counterData();
    }

    private void loadFromFile() {

        // Sourced from Lab3, written by me
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Counter>>() {}.getType();
            ArrayList<Counter> check = new ArrayList<Counter>();
            counters = gson.fromJson(in,listType);
            if(counters ==null){
                counters = new ArrayList<Counter>();
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            counters = new ArrayList<Counter>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }

    }

    private void saveInFile() {

        // Sourced from Lab3, written by me
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