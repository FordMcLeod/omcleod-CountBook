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

import org.w3c.dom.Text;

public class DetailsActivity extends AppCompatActivity {


        public Counter counter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_details);
            Button saveButton = (Button) findViewById(R.id.saveButton);
            final TextView name = (TextView) findViewById(R.id.name);
            final TextView currVal = (TextView) findViewById(R.id.currVal);
            final TextView initVal = (TextView) findViewById(R.id.initVal);
            final TextView comment = (TextView) findViewById(R.id.comment);
            final TextView date = (TextView) findViewById(R.id.date);


            Intent intent = this.getIntent();
            Bundle bundle = intent.getExtras();

            Counter counter = (Counter) bundle.getSerializable("item");

            name.setText(counter.getName());
            currVal.setText(String.valueOf(counter.getValue()));
            initVal.setText(String.valueOf(counter.getInitValue()));
            comment.setText(String.valueOf(counter.getComment()));
            date.setText(String.valueOf(counter.getDate()));




            saveButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    Counter counter = new Counter(name.getText().toString(),
                            Integer.parseInt(currVal.getText().toString()),
                            Integer.parseInt(initVal.getText().toString()),
                            comment.getText().toString());

                }


            });


        }


}

