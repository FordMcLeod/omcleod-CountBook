package com.example.owenm_000.countbook2;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;

public class DetailsActivity extends AppCompatActivity {


        public Counter counter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_details);

            // Creating objects corresponding to UI that will be change
            // In the future I would implement this Acitivty in a cleaner way as this seems like an obscene number of objects
            Button saveButton = (Button) findViewById(R.id.saveButton);
            Button increment = (Button) findViewById(R.id.increment);
            Button decrement = (Button) findViewById(R.id.decrement);
            Button reset = (Button) findViewById(R.id.reset);
            Button delete = (Button) findViewById(R.id.delete);
            final TextView name = (TextView) findViewById(R.id.name);
            final TextView currVal = (TextView) findViewById(R.id.currVal);
            final TextView initVal = (TextView) findViewById(R.id.initVal);
            final TextView comment = (TextView) findViewById(R.id.comment);
            final TextView date = (TextView) findViewById(R.id.date);



            //getting intent that was passed to this from MainActivity, via a bundle
            final Intent intent = this.getIntent();
            Bundle bundle = intent.getExtras();
            Counter counter = (Counter) bundle.getSerializable("item");


            //Filling in TextViews with current counter information
            name.setText(counter.getName());
            currVal.setText(String.valueOf(counter.getValue()));
            initVal.setText(String.valueOf(counter.getInitValue()));
            comment.setText(String.valueOf(counter.getComment()));
            date.setText(String.valueOf(counter.getDate()));



            //On Clickm the save button will submit the current data as a string using GSON
            saveButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    Counter counter = new Counter(name.getText().toString(),
                            Integer.parseInt(currVal.getText().toString()),
                            Integer.parseInt(initVal.getText().toString()),
                            comment.getText().toString());

                    Gson gson = new Gson();
                    String json = gson.toJson(counter);
                    intent.putExtra("newCounter",json);
                    setResult(RESULT_OK,intent);

                }


            });


            // Increments by one
            increment.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    int val = Integer.parseInt(currVal.getText().toString());
                    currVal.setText(String.valueOf(val + 1));

                }


            });

            // Resets the counter to it's init val
            reset.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    int val = Integer.parseInt(initVal.getText().toString());
                    currVal.setText(String.valueOf(val));

                }


            });

            // Decremnts by one can't go below zero, utilzies Toast to notify user if at 0
            decrement.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    int val = Integer.parseInt(currVal.getText().toString());
                    if(val>0) {
                        currVal.setText(String.valueOf(val - 1));
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Cannot decrement below zero!",Toast.LENGTH_SHORT)
                                .show();
                    }
                }


            });


            // Deletes the counter, and passes back a null counter to MainActivty through GSON
            delete.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {

                    Gson gson = new Gson();
                    Counter counter = null;
                    String json = gson.toJson(counter);
                    intent.putExtra("newCounter",counter);
                    setResult(RESULT_OK,intent);
                }


            });

        }

}

