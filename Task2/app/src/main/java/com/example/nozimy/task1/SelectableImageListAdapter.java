package com.example.nozimy.task1;


import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckedTextView;

import java.util.ArrayList;

public class SelectableImageListAdapter extends RecyclerView.Adapter<SelectableImageListAdapter.SelectableImageListAdapterViewHolder> implements SelectableImageListAdapter.SelectableImageListAdapterViewHolder.OnItemSelectedListener{

    private final ArrayList<SelectableImage> imagesData;
    private boolean isMultiSelectionEnabled = false;
    SelectableImageListAdapterViewHolder.OnItemSelectedListener listener;

    public SelectableImageListAdapter(SelectableImageListAdapterViewHolder.OnItemSelectedListener listener,
                                      ArrayList<SelectableImage> items, boolean isMultiSelectionEnabled) {
        this.listener = listener;
        this.isMultiSelectionEnabled = isMultiSelectionEnabled;

        imagesData = items;
        notifyDataSetChanged();

    }

    @Override
    public SelectableImageListAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_gallery_list_item, parent, false);

        return new SelectableImageListAdapterViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        SelectableImageListAdapterViewHolder holder = (SelectableImageListAdapterViewHolder) viewHolder;
        SelectableImage selectableImage = getImage(position);
        String name = selectableImage.getName();
        holder.itemTextView.setText(name);
        if (isMultiSelectionEnabled) {
            TypedValue value = new TypedValue();
            holder.itemTextView.getContext().getTheme().resolveAttribute(android.R.attr.listChoiceIndicatorMultiple, value, true);
            int checkMarkDrawableResId = value.resourceId;
            holder.itemTextView.setCheckMarkDrawable(checkMarkDrawableResId);
        } else {
            TypedValue value = new TypedValue();
            holder.itemTextView.getContext().getTheme().resolveAttribute(android.R.attr.listChoiceIndicatorSingle, value, true);
            int checkMarkDrawableResId = value.resourceId;
            holder.itemTextView.setCheckMarkDrawable(checkMarkDrawableResId);
        }

        holder.mSelectableImage = selectableImage;
        holder.setChecked(holder.mSelectableImage.isSelected());
    }

    @Override
    public int getItemCount() {
        if (null == imagesData) return 0;
        return imagesData.size();
    }


    public class SelectableImageListAdapterViewHolder extends RecyclerView.ViewHolder {

        public static final int MULTI_SELECTION = 2;
        public static final int SINGLE_SELECTION = 1;
        SelectableImage mSelectableImage;
        CheckedTextView itemTextView;
        OnItemSelectedListener itemSelectedListener;

        public SelectableImageListAdapterViewHolder(View view, OnItemSelectedListener listener){
            super(view);

            itemSelectedListener = listener;
            itemTextView = (CheckedTextView) view.findViewById(R.id.checked_text_item);

            itemTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (mSelectableImage.isSelected() && getItemViewType() == MULTI_SELECTION) {
                        setChecked(false);
                    } else {
                        setChecked(true);
                    }
                    itemSelectedListener.onItemSelected(mSelectableImage);

                }
            });

        }

        public void setChecked(boolean value) {
            if (value) {
                itemTextView.setBackgroundColor(Color.LTGRAY);
            } else {
                itemTextView.setBackgroundColor(Color.WHITE);
            }
            mSelectableImage.setSelected(value);
            itemTextView.setChecked(value);
        }
        public interface OnItemSelectedListener {
            void onItemSelected(SelectableImage image);
        }

    }


    private SelectableImage getImage (int position) {
        Object im = imagesData.get(position);
        return ((SelectableImage) im);
    }


}
