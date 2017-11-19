package com.sample.stego_image;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Help2 extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help2);

        Button bnext2 = (Button) findViewById(R.id.bnext2);
        bnext2.setOnClickListener(this);

        Button bback1 = (Button) findViewById(R.id.bback1);
        bback1.setOnClickListener(this);

        getSupportActionBar().setTitle("Help - Decode_Process");


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
            case R.id.bnext2:
                Intent intent = new Intent(this, Help3.class);
                startActivity(intent);
                break;

            case R.id.bback1:
                Intent intent1 = new Intent(this, Help1.class);
                startActivity(intent1);
                break;

        }
    }

}