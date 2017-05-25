package com.example.chandra.dailyselfie;

//import BaseAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Chandra on 2/4/2017.
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

            newView = inflater.inflate(R.layout.image_view, null);

            holder.text = (TextView) newView.findViewById(R.id.text);

            holder.image = (ImageView) newView.findViewById(R.id.image);
            newView.setTag(holder);

        } else {
            holder = (ViewHolder) newView.getTag();
        }

        Bitmap bmp = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(filepath[position]),64,64);

        holder.image.setImageBitmap(bmp);
        if(filename[position]!= null)
        holder.text.setText(filename[position]);

        return newView;



    }

    static class ViewHolder {

        ImageView image;
        TextView text;

    }
    public void removeAllViews() {

        for(int i=0;i<getCount();i++)
        {
            Bitmap bm= BitmapFactory.decodeFile(filepath[i]);
            bm.recycle();
        }
        filepath= new String[0];
        filename= new String[0];
        this.notifyDataSetChanged();
    }

}
