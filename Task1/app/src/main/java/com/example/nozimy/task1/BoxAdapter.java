package com.example.nozimy.task1;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class BoxAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Image> objects;

    BoxAdapter(Context context, ArrayList<Image> images){
        ctx = context;
        objects = images;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item, parent, false);
        }

        Image im = getImage(position);

        ((TextView) view.findViewById(R.id.im_name)).setText(im.name);
        //((ImageView) view.findViewById(R.id.im_image_link)).setImageResource(im.defaultImage);
        ImageView imageView = (ImageView) view.findViewById(R.id.im_image_link);
        Picasso mPicasso = Picasso.with(ctx);
        mPicasso.setIndicatorsEnabled(true);
        mPicasso.load(im.link)
                .resize(400, 200)
                .centerCrop()
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(imageView);

        return view;

    }


    Image getImage (int position) {
        return ((Image) getItem(position));
    }

//// NOTE: I don't need an AsyncTask for loading images, since Picasso already does decoding in background
//    private void loadImage(){
//
//    }
//
//    public class LoadImageTask extends AsyncTask<Object, Void, String> {
//
//        private ImageView imView;
//        @Override
//        protected String doInBackground(Object... params) {
//            ImageView imView = (ImageView) params[0];
//            String imUrl = (String) params[1];
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//        }
//    }
/////////////

}
