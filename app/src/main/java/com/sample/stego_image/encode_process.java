package com.sample.stego_image;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

public class encode_process extends AppCompatActivity implements View.OnClickListener {

    public static final int PICK_IMAGE = 2;
    private static int RESULT_LOAD_IMG = 1;
    private ProgressDialog progress;
    String status = "-";
    String fname = "";
    Double d = 0.0;
    int pixel = 25;
    static int MAX_COUNT = 160;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encode_process);

        Button bChooseImage = (Button)findViewById(R.id.bChooseImage);
        bChooseImage.setOnClickListener(this);
        Button bEncodeProcess= (Button)findViewById(R.id.bEncodeProcess);
        bEncodeProcess.setOnClickListener(this);

        EditText txtStatus = (EditText)findViewById(R.id.etTextEncode);
        final TextView lblCount = (TextView)findViewById(R.id.tv_char);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Encode Process");


        // Attached Listener to Edit Text Widget
        txtStatus.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // Display Remaining Character with respective color
                int count = MAX_COUNT - s.length();
                lblCount.setText(Integer.toString(count));
                if(count == 0) {
                    AlertDialog alertDialog = new AlertDialog.Builder(encode_process.this).create();
                    alertDialog.setTitle("Warning");
                    alertDialog.setMessage("Max Karakter "+ MAX_COUNT); // memunculkan alert max karakter
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }

                    });
                    alertDialog.show(); //aletr dialog show
                }

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bChooseImage: //di klik menuju choose image
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                // Start the Intent
                startActivityForResult(Intent.createChooser(galleryIntent, "Select Image"), RESULT_LOAD_IMG);
                break;

            case R.id.bEncodeProcess: //di klik menuju encode proses
                Encodeprocessing();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.encode, menu); // untuk tampilan atas pojok kanan
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar Item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        AlertDialog dialog = new AlertDialog.Builder(encode_process.this)
                .setTitle("Hasil Proses Encoding")
                .setMessage("\n Status \t : " + status + " \n PSNR \t :  "+ d +"\n\n Stego Image Name : \n"+fname+"\n")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            this.finish();
            return true;
        }
        dialog.show();
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) { //memilih dan memunculkan gambar yang kita pilih
        super.onActivityResult(paramInt1, paramInt2, paramIntent);
        // When an Image is picked
        if (paramInt1 == RESULT_LOAD_IMG && paramInt2 == RESULT_OK && paramIntent != null && paramIntent.getData() != null) {
            Uri uri = paramIntent.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                ImageView imageView = (ImageView) findViewById(R.id.ivImageEncode);
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


    public void Encodeprocessing() {
        EditText txtPesan = (EditText) findViewById(R.id.etTextEncode);
        String pesan = txtPesan.getText().toString();

        if (pesan.replaceAll(" ", "") == "") {
            Toast.makeText(getApplicationContext(), "Please write a message", Toast.LENGTH_LONG).show();
            status = "Please write a message";
            return;
        }

        ImageView localImageView = (ImageView) findViewById(R.id.ivImageEncode);
        if (localImageView.getDrawable() == null) {
            Toast.makeText(getApplicationContext(), "Please attach an image", Toast.LENGTH_LONG).show();
            status = "Please attach an image";
            return;
        }

        Bitmap localBitmap = ((BitmapDrawable) localImageView.getDrawable()).getBitmap();
        Bitmap Cover_Image = localBitmap.copy(Bitmap.Config.ARGB_8888,true);

        Formula fm = new Formula();
        String hasil = fm.stringtobinary(pesan).concat("0000000000000000"); // ubah pesan ke binary dan di tambahakn enol di belakgannya
        int pjg_hasil = hasil.length();
        int total_lsb = fm.sumlsb(Cover_Image);


        if (Cover_Image == null) {

            Toast.makeText(getApplicationContext(), "Please attach an image", Toast.LENGTH_LONG).show();
            status = "Please attach an image";
            return;
        }

        if (total_lsb < pjg_hasil) {
            Toast.makeText(getApplicationContext(), "Please select another image", Toast.LENGTH_LONG).show();
            status = "Please select another image";
            return;
        }

        else{
            Bitmap Stego_Image = insertMessage(hasil); // masuk ke metthod insert message
            SaveImage(Stego_Image);
            d = fm.hitungPSNR(Cover_Image, Stego_Image); //menghitung PSNR
            Toast.makeText(getApplicationContext(), "Image Saved :  " + fname+" PSNR : " +d, Toast.LENGTH_LONG).show();
            //startActivity(new Intent(this, MainActivity.class));
            status = "encoding berhasil";

        }

    }

    public void SaveImage(Bitmap paramBitmap) {

        File localFile1 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Image-Stego");
        localFile1.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        fname = "Singgih-" + n + ".jpg";
        File localFile2 = new File(localFile1, fname);
        scanMedia(localFile2);

        if (localFile2.exists()) {
            localFile2.delete();
        }

        try {
            FileOutputStream out = new FileOutputStream(localFile2);
            paramBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    private Bitmap insertMessage(String encryptedMessage) {

        ImageView ivLoadImg = (ImageView) findViewById(R.id.ivImageEncode);
        Bitmap bi2 = ((BitmapDrawable)ivLoadImg.getDrawable()).getBitmap();
        Bitmap bi1 = bi2.copy(Bitmap.Config.ARGB_8888,true);

        int a,b;

        if (bi1.getHeight()<pixel){a = bi1.getHeight();}
        else {a = pixel;}

        if (bi1.getWidth()<pixel){b = bi1.getWidth();}
        else {b = pixel;}

        Formula sm = new Formula();
        int charIndex = 0;
        String r3, g3, b3;
        int pjgpesan = encryptedMessage.length();

        for (int i = 0; i < a; i++) {
            // pass through each row
            for (int j = 0; j < b; j++) {
                // holds the pixel that is currently being processed
                int pixel = bi1.getPixel(j, i);
                // Mengubag semua nilai pixel Lsb menjadi 0
                int A = (pixel >> 24) & 0xff;
                int R = (pixel >> 16) & 0xff;
                int G = (pixel >> 8) & 0xff;
                int B = (pixel) & 0xff;
                String r1 = Integer.toBinaryString(R);
                String g1 = Integer.toBinaryString(G);
                String b1 = Integer.toBinaryString(B);

                String rr = sm.bintoeightbin(r1);//menjadi binari dengan pjg 8
                String r2 = rr.substring(0, 7); // mengambil bilai sebanyak 7 dari 8
                String gg = sm.bintoeightbin(g1);
                String g2 = gg.substring(0, 7);
                String bb = sm.bintoeightbin(b1);
                String b2 = bb.substring(0, 7);

                //red
                if (charIndex < pjgpesan) {
                    String PesanR = encryptedMessage.substring(charIndex, charIndex + 1); // index 0, 1 alias indeks ke - 0;
                    if ( Integer.valueOf(PesanR) == 1) {
                        r3 = r2.concat("1"); //mengganti bit paling belakang menjadi 1
                    }
                    else{
                        r3 = r2.concat("0"); //mengganti bit paling belaka menjadi 0
                    }
                    R = sm.binarytointeger(r3); // nilai pixel R baru
                    charIndex++; // char index di tambah sebanyak 1
                }

                //green
                if (charIndex<pjgpesan) {
                    String PesanG = encryptedMessage.substring(charIndex, charIndex + 1); // lnjut dari index atasnya
                    if ( Integer.valueOf(PesanG) == 1) {
                        g3 = g2.concat("1");
                    }
                    else{
                        g3 = g2.concat("0");
                    }
                    G = sm.binarytointeger(g3);
                    charIndex++; //char index di tambah sebanyak 1
                }

                //blue
                if (charIndex<pjgpesan){
                    String PesanB = encryptedMessage.substring(charIndex, charIndex + 1); // lnjut dari index atasnya
                    if ( Integer.valueOf(PesanB) == 1) {
                        b3 = b2.concat("1");
                    }
                    else{
                        b3 = b2.concat("0");
                    }
                    B = sm.binarytointeger(b3);
                    charIndex++; //char index di tambah sebanyak 1
                }

                if (charIndex>=pjgpesan){
                    return bi1;
                }

                int rgba = (A<<24)|(R<<16)|(G<<8)|(B); //gabungkan 3  komponen warna
                bi1.setPixel(j, i, rgba); //settting pixel baru

            }
        }
        return bi1;
    }


    private void scanMedia(File paramFile) {
        sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(paramFile)));
    }

}
