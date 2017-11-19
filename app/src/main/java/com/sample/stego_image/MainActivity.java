package com.sample.stego_image;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar Item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                .setTitle("Contact Person")
                .setMessage("\n Name \t    :   Singgih Juniawan  \n Country \t :   (+62)-Indonesia  \n" +
                        " Phone \t    :   (+62)896-6608-7499  \n Email \t    :   Singgihj4@gmail.com  \n")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();

        //noinspection SimplifiableIfStatement
        if (id == R.id.home) {
            return true;
        }
        dialog.show();
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view Item clicks here.
        int id = item.getItemId();

        if (id == R.id.bEncoding) { //masuk ke encoding

            Intent intent = new Intent(this, encode_process.class);
            startActivity(intent);

        } else if (id == R.id.bDecoding) { //masuk ke decoding

            Intent intent1  = new Intent(this, decode_process.class);
            startActivity(intent1);

        } else if (id == R.id.bStego_image) { //untuk masuk kedalam es file exploere

            Intent intent = getPackageManager().getLaunchIntentForPackage("com.estrongs.android.pop");
            File localFile1 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Image-Stego");
            if (intent != null ) {
                // We found the activity now start the activity
                if (localFile1.canRead()) {
                    Uri uri = Uri.parse("/sdcard/Pictures/Image-Stego"); // a directory
                    intent.setDataAndType(uri, "*/*");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    startActivity(intent);
                }

                else {
                    localFile1.mkdir();
                    Toast.makeText(this, "Folder Image-Stego Created", Toast.LENGTH_SHORT).show();
                }


            } else {
                // Bring user to the market or let them choose an app?
                intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.parse("market://details?id=" + "com.estrongs.android.pop")); //masuk ke google play store
                startActivity(intent);
            }



        } else if (id == R.id.bHelp) { //masuk ke intent help
            Intent intent2 = new Intent(this, Help.class);
            startActivity(intent2);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
