package com.sample.stego_image;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by Singgih on 19/11/2016.
 */

public class Formula {
    public String bintoeightbin(String binary){ //mengubah binary dari pixel menajdi 8 kompenn yang sama semua
        String zero = "0";
        int a = binary.length();
        for (int k = 0; k < 8-a; k++){
            binary = zero.concat(binary);
        }

        return binary;
    }


    public Integer binarytointeger (String binary) { //mengubah dari binari ke integer
        char[] numbers = binary.toCharArray();
        Integer result =0;
        int count = 0;
        for (int i = numbers.length - 1; i >= 0; i--) {
            if (numbers[i] == '1') result += (int) Math.pow(2, count);
            count++;
        }
        return result;
    }

    public String binarytostring(String pesan1) { //mengubah dari binari ke string sekalian di embed dengan 0000000000

        String pesan = pesan1.concat("0000000000000000");
        StringBuilder sb = new StringBuilder();
        char[] chars = pesan.replaceAll("\\s", "").toCharArray();

        int[] mapping = {1, 2, 4, 8, 16, 32, 64, 128};

        for (int j = 0; j < chars.length; j += 8) { // j di tambah sebanyak 8 dan j kurang dari panjang pesan

            int idx = 0;
            int sum = 0;

            for (int i = 7; i >= 0; i--) {
                if (chars[i + j] == '1') {
                    sum += mapping[idx];
                }
                idx++;
            }

            if (sum == 0 ){

                j = chars.length;
            }

            sb.append(Character.toChars(sum));
        }

        return sb.toString();
    }


    public String stringtobinary (String pesan){ //mengubah dari string ke binary
        String result = "";
        ArrayList<String> messageList = new ArrayList<String>();
        byte[] bytes = pesan.getBytes();
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
        }
        messageList.add(binary.toString());
        for (String object : messageList) {
            result +=object;
        }

        return result;
    }

    public double hitungPSNR(Bitmap Cover_Image, Bitmap Stego_Image) { //image1 , image2

        double totalRed = 0;
        double totalGreen = 0;
        double totalBlue = 0;

        for (int i = 0; i < Cover_Image.getWidth(); i++) {
            for (int j = 0; j < Cover_Image.getHeight(); j++) {

                int cover = Cover_Image.getPixel(i, j);
                int r1 = (cover>>16)&0xff;
                int g1 = (cover>>8)&0xff;
                int b1 = (cover)&0xff;

                int stego = Stego_Image.getPixel(i, j);
                int r2 = (stego>>16)&0xff;
                int g2 = (stego>>8)&0xff;
                int b2 = (stego)&0xff;

                final int red = r2 - r1;
                final int green = g2 - g1;
                final int blue = b2 - b1;

                totalRed += red*red;
                totalGreen += green*green;
                totalBlue += blue*blue;
            }
        }

        double meanSquaredError = (totalRed+totalGreen+totalBlue) / (Cover_Image.getWidth() * Cover_Image.getHeight()*3);

        if (meanSquaredError == 0) {
            return 0.0;
        }
        double psnr = 10 * StrictMath.log10((255*255) / meanSquaredError);

        return psnr;
    }


    public int sumlsb(Bitmap image){ //total semua lsb yang tersedia
        int sumlsb = (image.getHeight()*image.getWidth()*3);

        return sumlsb;
    }
}