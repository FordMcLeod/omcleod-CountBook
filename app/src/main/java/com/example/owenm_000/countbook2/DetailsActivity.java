package com.example.owenm_000.countbook2;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class DetailsActivity extends AppCompatActivity {


        public Counter counter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_details);

            Intent intent = this.getIntent();
            Bundle bundle = intent.getExtras();

            final Counter counter = (Counter) bundle.getSerializable("item");

        }

}

