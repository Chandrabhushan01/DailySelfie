package com.example.chandra.dailyselfie;

///import BaseAdapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chandra.dailyselfie.R;

/**
 * Created by Chandra on 2/7/2017.
 */

public class ImageViewAdapter extends BaseAdapter {


    private String[] filepath;
    private String[] filename;

    private static LayoutInflater inflater = null;
    private Context mContext;

    public ImageViewAdapter(Context context,String[] fpath, String[] fname) {
        filepath = fpath;
        filename = fname;
        mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    public int getCount() {
        int n=0;
        try {
            n=filepath.length;
        }
        catch (NullPointerException e)
        {

        }

        return  n;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View newView = convertView;
        ViewHolder holder;

        if (convertView == null) {

            holder = new ViewHolder();

            newView = inflater.inflate(R.layout.layout, null);

            holder.text = (TextView) newView.findViewById(R.id.text);

            holder.image = (ImageView) newView.findViewById(R.id.image);
            newView.setTag(holder);

        } else {
            holder = (ViewHolder) newView.getTag();
        }

        //Bitmap bmp = ;

        holder.image.setImageBitmap(decodeSampledBitmapFromResource(filepath[position], 100, 100));
        if(filename[position]!= null)
            holder.text.setText(filename[position]);

        return newView;



    }

    static class ViewHolder {

        ImageView image;
        TextView text;

    }


    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }


    public static Bitmap decodeSampledBitmapFromResource(String path,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

}
