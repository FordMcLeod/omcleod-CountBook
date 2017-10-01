package com.example.owenm_000.countbook2;

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

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ListView listView ;
    final int INIT_CODE = 1;
    final int UPDATE_CODE = 1;
    public ArrayList<Counter> counters = new ArrayList<Counter>();;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button);
        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);
        Intent intent;

        // Defined Array values to show in ListView
        String[] values = new String[] { "Android List View",
                "Adapter implementation",
                "Simple List View In Android",
                "Create List View Android",
                "Android Example",
                "List View Source Code",
                "List View Array Adapter",
                "Android Example List View"
        };

        for(int i=0;i<30;i++){
            counters.add(new Counter(String.valueOf(i),i,i+5,""));
        }

        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        ArrayAdapter<Counter> adapter = new ArrayAdapter<Counter>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, counters);


        // Assign adapter to ListView
        listView.setAdapter(adapter);

        // ListView Item Click Listener


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

                Intent intent = new Intent(MainActivity.this,DetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("item", (Counter)listView.getItemAtPosition(position));
                intent.putExtras(bundle);
                startActivityForResult(intent,UPDATE_CODE);


                // ListView Clicked item value
                Counter  itemValue    = (Counter) listView.getItemAtPosition(position);

                //Show Alert
                Toast.makeText(getApplicationContext(),
                        "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_SHORT)
                        .show();

            }

        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //this.onActivityResult();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        try {
            super.onActivityResult(requestCode, resultCode, intent);

            if (requestCode == INIT_CODE && resultCode == RESULT_OK) {

                Gson gson = new Gson();
                String gsonString = intent.getStringExtra("gsonCounter");
                Counter newCounter = gson.fromJson(gsonString, Counter.class);
                counters.add(newCounter);
                updateScreen();
            } else if (requestCode == UPDATE_CODE && resultCode == RESULT_OK) {

                Gson gson = new Gson();


                String arrayIndex = intent.getStringExtra("arrayIndex");
                String deleteCounter = intent.getStringExtra("delete");
                if (Boolean.parseBoolean(deleteCounter)) {
                    counters.remove(Integer.parseInt(arrayIndex));
                    updateScreen();
                    return;
                }
                String gsonString = intent.getStringExtra("gsonCounter");
                Counter newCounter = gson.fromJson(gsonString, Counter.class);
                counters.set(Integer.parseInt(arrayIndex), newCounter);
                updateScreen();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateScreen() {


    }
}