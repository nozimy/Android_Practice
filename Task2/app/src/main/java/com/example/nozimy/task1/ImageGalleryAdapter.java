package com.example.nozimy.task1;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;

import java.util.ArrayList;

public class ImageGalleryAdapter extends RecyclerView.Adapter<ImageGalleryAdapter.ImageGalleryAdapterViewHolder> {

    private ArrayList<Image> imagesData;

    private final ImageGalleryAdapterOnClickHandler mClickHandler;

    public interface ImageGalleryAdapterOnClickHandler {
        void onClick(Image image);
    }

    public ImageGalleryAdapter(ImageGalleryAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class ImageGalleryAdapterViewHolder extends RecyclerView.ViewHolder implements OnClickListener{

        public final TextView itemTitle;
        public final ImageView itemImage;


        public ImageGalleryAdapterViewHolder(View view) {
            super(view);
            itemTitle = (TextView) view.findViewById(R.id.im_name);
            itemImage = (ImageView) view.findViewById(R.id.im_image_link);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Image image = imagesData.get(adapterPosition);
            mClickHandler.onClick(image);
        }
    }

    @Override
    public ImageGalleryAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.image_gallery_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);

        return new ImageGalleryAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageGalleryAdapterViewHolder holder, int position) {
        Image im = getImage(position);
        holder.itemTitle.setText(im.name);


    }

    @Override
    public int getItemCount() {
        if (null == imagesData) return 0;
        return imagesData.size();
    }

    Image getImage (int position) {
        Object im = imagesData.get(position);
        return ((Image) im);
    }

    void setImagesData(ArrayList<Image> images) {
            imagesData = new ArrayList<>(images);
            // обновляем отображаемый список
            notifyDataSetChanged();
    }
}
