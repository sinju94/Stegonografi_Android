package com.sample.stego_image;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Help extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        getSupportActionBar().setTitle("Help - Stego_Image");

        Button bnext = (Button) findViewById(R.id.bnext);
        bnext.setOnClickListener(this);

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
            case R.id.bnext:
                Intent intent = new Intent(this, Help1.class);
                startActivity(intent);
                break;


        }
    }


}
