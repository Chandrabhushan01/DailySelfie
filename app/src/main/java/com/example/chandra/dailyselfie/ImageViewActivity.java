package com.example.chandra.dailyselfie;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageViewActivity extends AppCompatActivity {

    ImageView imageview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        Intent i = getIntent();

        // Get the position
        int position = i.getExtras().getInt("position");

        // Get String arrays FilePathStrings
        String[] filepath = i.getStringArrayExtra("filepath");

        // Get String arrays FileNameStrings
        String[] filename = i.getStringArrayExtra("filename");

        // Locate the TextView in view_image.xml
        //text = (TextView) findViewById(R.id.imagetext);

        // Load the text into the TextView followed by the position
       // text.setText(filename[position]);

        // Locate the ImageView in view_image.xml
        imageview = (ImageView) findViewById(R.id.full_image_view);

        // Decode the filepath with BitmapFactory followed by the position
       // Bitmap bmp = BitmapFactory.decodeFile(filepath[position]);

        // Set the decoded bitmap into ImageView
        imageview.setImageBitmap(getBitmap(filepath[position]));
    }


    private Bitmap getBitmap(String path) {
        try {
            // Get the dimensions of the bitmap
           // int targetW = imageview.getWidth();
           // int targetH = imageview.getHeight();


            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, opts);
            int photoW = opts.outWidth;
            int photoH = opts.outHeight;

            // Get the dimensions of the screen
           Display displaymetrics = getWindowManager().getDefaultDisplay();//new DisplayMetrics();
            //getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);;
            Point size = new Point();
            displaymetrics.getRealSize(size);
            int targetW = size.x;
            int targetH = size.y;
            //int targetW = displaymetrics.getWidth();//widthPixels;
            //int targetH = displaymetrics.heightPixels;
            int scaleFactor = 1;
            if ((targetW > 0) || (targetH > 0)) {
                scaleFactor = Math.min(photoW/targetW, photoH/targetH);
            }

            // Determine how much to scale down the image
            //int scaleFactor = Math.max(photoW, photoH) / Math.min(screenW, screenH);

            // Decode the image file into a Bitmap sized to fill the View
            opts.inJustDecodeBounds = false;
            opts.inSampleSize = scaleFactor;
            opts.inPurgeable = true;

            return BitmapFactory.decodeFile(path, opts);

        } catch (Exception e) {
        }

        return null;
    }


}
