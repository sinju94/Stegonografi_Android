package com.sample.stego_image;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Help3 extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help3);

        Button bback2 = (Button) findViewById(R.id.bback2);
        bback2.setOnClickListener(this);


        getSupportActionBar().setTitle("Help - Pengiriman Pesan");


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
            case R.id.bback2:
                Intent intent1 = new Intent(this, Help2.class);
                startActivity(intent1);
                break;

        }
    }

}
