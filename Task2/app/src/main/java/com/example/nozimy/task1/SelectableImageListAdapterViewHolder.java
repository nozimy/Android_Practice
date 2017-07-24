package com.example.nozimy.task1;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckedTextView;

public class SelectableImageListAdapterViewHolder extends RecyclerView.ViewHolder {

    public static final int MULTI_SELECTION = 2;
    public static final int SINGLE_SELECTION = 1;
    SelectableImage mSelectableImage;
    CheckedTextView itemTextView;
    OnItemSelectedListener itemSelectedListener;

    OnLongClickListener mLongClickListener;

    public SelectableImageListAdapterViewHolder(View view, OnItemSelectedListener listener, OnLongClickListener longClickListener){
        super(view);

        itemSelectedListener = listener;
        itemTextView = (CheckedTextView) view.findViewById(R.id.checked_text_item);

        mLongClickListener = longClickListener;

        itemTextView.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View view) {
                mLongClickListener.onLongClick(true);
                itemTextView.callOnClick();
                return true;
            }
        });

        itemTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mLongClickListener.isAdapterCheckable()) {
                    if (mSelectableImage.isSelected() && getItemViewType() == MULTI_SELECTION) {
                        setChecked(false);
                    } else {
                        setChecked(true);
                    }
                    itemSelectedListener.onItemSelected(mSelectableImage);
                } else {
                    itemSelectedListener.onItemCliked(mSelectableImage);
                }
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
        void onItemCliked(SelectableImage image);
    }

    public interface OnLongClickListener {
        void onLongClick(boolean isAdapterCheckable);
        boolean isAdapterCheckable();
    }

}