package com.example.nozimy.task1;


import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;

public class SelectableImageListAdapter extends RecyclerView.Adapter<SelectableImageListAdapterViewHolder> implements SelectableImageListAdapterViewHolder.OnItemSelectedListener, SelectableImageListAdapterViewHolder.OnLongClickListener{

    private final ArrayList<SelectableImage> imagesData;
    private boolean isMultiSelectionEnabled = false;
    SelectableImageListAdapterViewHolder.OnItemSelectedListener listener;
    SelectableImageListAdapterViewHolder.OnLongClickListener mLongClickListener;
    private boolean isCheckable = false;

    public SelectableImageListAdapter(SelectableImageListAdapterViewHolder.OnItemSelectedListener listener,
                                      SelectableImageListAdapterViewHolder.OnLongClickListener onLongClickListener,
                                      ArrayList<Image> items, boolean isMultiSelectionEnabled) {
        this.listener = listener;
        this.isMultiSelectionEnabled = isMultiSelectionEnabled;

        this.mLongClickListener = onLongClickListener;

        imagesData = new ArrayList<>();
        for (Image im: items){
            imagesData.add(new SelectableImage(im, false));
        }
    }

    @Override
    public SelectableImageListAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new SelectableImageListAdapterViewHolder(itemView, this, this);
    }

    @Override
    public void onBindViewHolder(SelectableImageListAdapterViewHolder holder, int position) {

        SelectableImage selectableImage = getImage(position);
        String name = selectableImage.getName();
        holder.itemTextView.setText(name);

        holder.mSelectableImage = selectableImage;

        if (isCheckable) {
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

            holder.setChecked(holder.mSelectableImage.isSelected());
        } else {
            holder.itemTextView.setCheckMarkDrawable(null);
        }
    }

    @Override
    public int getItemCount() {
        if (null == imagesData) return 0;
        return imagesData.size();
    }

    public ArrayList<SelectableImage> getSelectedItems() {

        ArrayList<SelectableImage> selectedItems = new ArrayList<>();
        for (SelectableImage item : imagesData) {
            if (item.isSelected()) {
                selectedItems.add(item);
            }
        }
        return selectedItems;
    }

    @Override
    public int getItemViewType(int position) {
        if(isMultiSelectionEnabled){
            return SelectableImageListAdapterViewHolder.MULTI_SELECTION;
        }
        else{
            return SelectableImageListAdapterViewHolder.SINGLE_SELECTION;
        }
    }

    @Override
    public void onItemSelected(SelectableImage item) {
        if (!isMultiSelectionEnabled) {

            for (SelectableImage selectableImage : imagesData) {
                if (!selectableImage.equals(item)
                        && selectableImage.isSelected()) {
                    selectableImage.setSelected(false);
                } else if (selectableImage.equals(item)
                        && item.isSelected()) {
                    selectableImage.setSelected(true);
                }
            }
            notifyDataSetChanged();
        }

        if (getSelectedItems().size() == 0){
            setCheckable(false);
            notifyDataSetChanged();
        }

        listener.onItemSelected(item);
    }

    @Override
    public void onItemCliked(SelectableImage image) {
        listener.onItemCliked(image);
    }

    private SelectableImage getImage (int position) {
        Object im = imagesData.get(position);
        return ((SelectableImage) im);
    }

    public void setCheckable(boolean checkable) {
        isCheckable = checkable;
    }

    public boolean isCheckable() {
        return isCheckable;
    }

    @Override
    public void onLongClick(boolean checkable) {
        setCheckable(checkable);
        notifyDataSetChanged();

        mLongClickListener.onLongClick(checkable);
    }

    @Override
    public boolean isAdapterCheckable(){
        return isCheckable();
    }

    public void deleteSelectedItems(ArrayList<SelectableImage> selectedItemsToDelete){
        imagesData.removeAll(selectedItemsToDelete);
        notifyDataSetChanged();

    }
}