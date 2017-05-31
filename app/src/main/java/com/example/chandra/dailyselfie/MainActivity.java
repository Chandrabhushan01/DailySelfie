package com.example.chandra.dailyselfie;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private String[] FilePathStrings;
    private String[] FileNameStrings;
    private File[] listFile;

    private static final int CAMERA_REQUEST = 1888;
    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";
    ImageView mImageView,mImageView1;
    private String mCurrentPhotoPath;
    ImageViewAdapter mAdapter;
    ListView imageListView;
    MyReceiver alarm = new MyReceiver();




    private String getAlbumName() {
        return getString(R.string.app_name);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mImageView = (ImageView) findViewById(R.id.image);
        // mImageView1=  (ImageView) findViewById(R.id.image1);

        imageListView = (ListView) findViewById(R.id.list);



        if (getAlbumDir().isDirectory() ) {
            listFile = getAlbumDir().listFiles();
            Log.i("DailySelfie", getAlbumDir().toString());
            // Create a String array for FilePathStrings
            if(listFile != null) {
                FilePathStrings = new String[listFile.length];
                // Create a String array for FileNameStrings
                FileNameStrings = new String[listFile.length];

                for (int i = 0; i < listFile.length; i++) {
                    // Get the path of the image file
                    FilePathStrings[i] = listFile[i].getAbsolutePath();
                    // Get the name image file
                    FileNameStrings[i] = listFile[i].getName();
                }
            }
        }

        mAdapter = new ImageViewAdapter(getApplicationContext(),FilePathStrings,FileNameStrings);
        imageListView.setAdapter(mAdapter);


        imageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // mImageView.setImageBitmap(getBitmap(FilePathStrings[position]));

                Intent i = new Intent(MainActivity.this, ImageViewActivity.class);
                // Pass String arrays FilePathStrings
                i.putExtra("filepath", FilePathStrings);
                // Pass String arrays FileNameStrings
                i.putExtra("filename", FileNameStrings);
                // Pass click position
                i.putExtra("position", position);
                startActivity(i);

            }

        });

        //alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // setRepeatingAlarm();
        alarm.setAlarm(this);


    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (getAlbumDir().isDirectory()) {
            listFile = getAlbumDir().listFiles();
            Log.i("DailySelfie", getAlbumDir().toString());
            if(listFile != null) {
                // Create a String array for FilePathStrings
                FilePathStrings = new String[listFile.length];
                // Create a String array for FileNameStrings
                FileNameStrings = new String[listFile.length];

                for (int i = 0; i < listFile.length; i++) {
                    // Get the path of the image file
                    FilePathStrings[i] = listFile[i].getAbsolutePath();
                    // Get the name image file
                    FileNameStrings[i] = listFile[i].getName();
                }
            }
        }

        mAdapter = new ImageViewAdapter(getApplicationContext(),FilePathStrings,FileNameStrings);
        imageListView.setAdapter(mAdapter);


    }

   /* public void setRepeatingAlarm() {
        //Intent notificationIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
        //notificationIntent.addCategory("android.intent.category.DEFAULT");

        Intent intent = new Intent(this, MyReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 100, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), (2* 60 * 1000), pendingIntent);
    }*/

    public void takeImageFromCamera() {

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File f = null;



        try {
            f = setUpPhotoFile();
            mCurrentPhotoPath = f.getAbsolutePath();
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        } catch (IOException e) {
            e.printStackTrace();
            f = null;
            mCurrentPhotoPath = null;
        }

        startActivityForResult(cameraIntent, CAMERA_REQUEST);

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            //Bitmap mphoto = (Bitmap) data.getExtras().get("data");
            /*OutputStream stream = null;
            stream = new FileOutputStream(file);
           // mphoto.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.flush();
            stream.close();*/
            // mImageView1.setImageBitmap(mphoto);
            // Log.i("DailySelfie", "failed to print file");

            // Parse the gallery image url to uri
            // Uri savedImageURI = Uri.parse(mCurrentPhotoPath);
            // Bitmap img = BitmapFactory.decodeFile(mCurrentPhotoPath);

            // Display the saved image to ImageView
            //  mImageView.setImageBitmap(getBitmap(savedImageURI));





        }
    }

    private File getAlbumDir() {
        File storageDir = null;

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

            File pictureFolder = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES
            );

            storageDir = new File(pictureFolder,getAlbumName());

            if (storageDir != null) {
                if (! storageDir.mkdirs()) {
                    if (! storageDir.exists()){
                        Log.d("CameraSample", "failed to create directory");
                        return null;
                    }
                }
            }

        } else {
            Log.v(getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
        }

        Log.i("Di is",storageDir.toString());

        return storageDir;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
        File albumF = getAlbumDir();
        File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
        return imageF;
    }

    private File setUpPhotoFile() throws IOException {

        File f = createImageFile();
        mCurrentPhotoPath = f.getAbsolutePath();
        Log.i("Path is",mCurrentPhotoPath);
        return f;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_camera:
               /* if (isIntentAvailable(this, MediaStore.ACTION_IMAGE_CAPTURE )) {
                    dispatchTakePictureIntent(ACTION_TAKE_PHOTO_B);
                }
                return true;*/
                takeImageFromCamera();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private Bitmap getBitmap(String path) {
        try {
            // Get the dimensions of the bitmap
            int targetW = mImageView.getWidth();
            int targetH = mImageView.getHeight();


            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, opts);
            int photoW = opts.outWidth;
            int photoH = opts.outHeight;

            // Get the dimensions of the screen
           /* DisplayMetrics displaymetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);;
            int targetW = displaymetrics.widthPixels;
            int targetH = displaymetrics.heightPixels;*/
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
