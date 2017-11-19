package com.sample.stego_image;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class decode_process extends AppCompatActivity implements OnClickListener {

    private static int RESULT_LOAD_IMG2 = 1;
    String status = "-";
    int pixel = 25;


    private String extractMessage(Bitmap bi) {
        int a,b;

        if (bi.getHeight()<pixel){a = bi.getHeight();}
        else {a = pixel;}

        if (bi.getWidth()<pixel){b = bi.getWidth();}
        else {b = pixel;}

        String extractedText = "";
        Formula sm = new Formula();


        for (int i = 0; i < a; i++) {
            // pass through each row
            for (int j = 0; j < b; j++) {
                // holds the pixel that is currently being processed
                int pixel = bi.getPixel(j, i);
                int R1 = (pixel >> 16) & 0xff;
                int G1 = (pixel >> 8) & 0xff;
                int B1 = (pixel) & 0xff;

                String r1 = Integer.toBinaryString(R1);
                String g1 = Integer.toBinaryString(G1);
                String b1 = Integer.toBinaryString(B1);

                String rr = sm.bintoeightbin(r1);
                String R = rr.substring(7, 8);

                String gg = sm.bintoeightbin(g1);
                String G = gg.substring(7, 8);

                String bb = sm.bintoeightbin(b1);
                String B = bb.substring(7, 8);

                extractedText += R+G+B;

            }
        }

        return extractedText;
    }


    private void Decodeprocessing() {


        if (((ImageView)findViewById(R.id.ivImageDecode)).getDrawable() == null) {
            Toast.makeText(getApplicationContext(), "Please attach an stegano image", Toast.LENGTH_LONG).show();
            status = "Please attach an stegano image";
            return;
        }

        else {

            ImageView ivImageResult = (ImageView) findViewById(R.id.ivImageDecode);
            Bitmap bi3 = ((BitmapDrawable)ivImageResult.getDrawable()).getBitmap();
            Bitmap bi2 = bi3.copy(Bitmap.Config.ARGB_8888,true);
            Formula sm = new Formula();

            String hasilExtract = extractMessage(bi2);
            String sb = sm.binarytostring(hasilExtract);

            EditText txtResult = (EditText) findViewById(R.id.etTextDecode);
            txtResult.setText(sb);
            Toast.makeText(getApplicationContext(), "Decode Finished", Toast.LENGTH_LONG).show();
            status = "Decode Finished";


        }
    }


    @Override
    protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
        super.onActivityResult(paramInt1, paramInt2, paramIntent);
        // When an Image is picked
        if (paramInt1 == RESULT_LOAD_IMG2 && paramInt2 == RESULT_OK && paramIntent != null && paramIntent.getData() != null) {
            Uri uri = paramIntent.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                ImageView imageView = (ImageView) findViewById(R.id.ivImageDecode);
                imageView.setImageBitmap(bitmap);
                Toast.makeText(this, "Image Selected", Toast.LENGTH_SHORT).show();
                status = "Image Selected";
            }

            catch (Exception e) {
                Toast.makeText(this, "Incorrect Image Selected", Toast.LENGTH_SHORT).show();
                status = "Incorrect Image Selected";
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decode_process);

        Button bChooseImage2 = (Button)findViewById(R.id.bChooseImage2);
        bChooseImage2.setOnClickListener(this);
        Button bDecodeProcess= (Button)findViewById(R.id.bDecodeProcess);
        bDecodeProcess.setOnClickListener(this);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Decode Process");

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()    ) {
            case R.id.bChooseImage2:
                Intent galleryIntent2 = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent2.setType("image/*");
                // Start the Intent
                startActivityForResult(Intent.createChooser(galleryIntent2, "Select Image"), RESULT_LOAD_IMG2);
                EditText txtResult = (EditText) findViewById(R.id.etTextDecode);
                txtResult.setText("");
                TextView textView3 = (TextView) findViewById(R.id.textView2);
                textView3.setText("");
                break;
            case R.id.bDecodeProcess:
                Decodeprocessing();
                TextView textView2 = (TextView) findViewById(R.id.textView2);
                textView2.setText("Your Message");
                break;
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.decode, menu); // untuk tampilan atas pojok kanan
        return true;
    }

    @Override


    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar Item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        AlertDialog dialog = new AlertDialog.Builder(decode_process.this)
                .setTitle("Hasil Proses Decoding")
                .setMessage("\n Status \t : " + status + "\n")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        if (id == android.R.id.home) {
            this.finish();
            return true;
        }
        dialog.show();
        return super.onOptionsItemSelected(item);
    }





}
