package com.sample.stego_image;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Help1 extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help1);


        Button bnext1 = (Button) findViewById(R.id.bnext1);
        bnext1.setOnClickListener(this);

        Button bback = (Button) findViewById(R.id.bback);
        bback.setOnClickListener(this);


        getSupportActionBar().setTitle("Help - Encode_Process");
    }

    @Override
    public void onBackPressed() {

        Intent homeintent = new Intent(this, MainActivity.class);
        homeintent.addCategory(Intent.CATEGORY_HOME);
        homeintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeintent);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bnext1:
                Intent intent = new Intent(this, Help2.class);
                startActivity(intent);
                break;

            case R.id.bback:
                Intent intent1 = new Intent(this, Help.class);
                startActivity(intent1);
                break;

        }
    }







}
