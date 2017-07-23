package com.example.nozimy.task1;


import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;

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

    public class ImageGalleryAdapterViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

        private final View view;
        private final TextView itemTitle;
        private final ImageView itemImage;

        public ImageGalleryAdapterViewHolder(View itemView) {
            super(itemView);

            view = itemView;
            itemTitle = (TextView) itemView.findViewById(R.id.im_name);
            itemImage = (ImageView) itemView.findViewById(R.id.im_image_link);
            itemView.setOnClickListener(this);


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
        final Image im = getImage(position);
        holder.itemTitle.setText(im.getName());
    }

    @Override
    public int getItemCount() {
        if (null == imagesData) return 0;
        return imagesData.size();
    }

    private Image getImage (int position) {
        Object im = imagesData.get(position);
        return ((Image) im);
    }

    void setImagesData(ArrayList<Image> images) {
            //imagesData = new ArrayList<>(images);
            imagesData = images;
            // обновляем отображаемый список
            notifyDataSetChanged();
    }
}
