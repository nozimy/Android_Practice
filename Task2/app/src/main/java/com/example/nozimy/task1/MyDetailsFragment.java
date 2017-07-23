package com.example.nozimy.task1;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class MyDetailsFragment extends Fragment {

    private static final String IMAGE_KEY = "image_key";
    private Image mImage;
    private OnFragmentStopListener mStopListener;

    // Container Activity must implement this interface
    public interface OnFragmentStopListener {
        void onFragmentDestroyView();
        void onDeleted(Image im);
    }

    /**
     * Create a new instance of MyDetailsFragment, initialized to
     * show the details of image at 'index'.
     */
    public static MyDetailsFragment newInstance(Image  image) {
        MyDetailsFragment f = new MyDetailsFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putSerializable(IMAGE_KEY, image);

        f.setArguments(args);

        return f;
    }
    public Image getImage(){ return (Image) getArguments().getSerializable(IMAGE_KEY); }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = getActivity(); //todo: check for null
        try {
            mStopListener = (OnFragmentStopListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnFragmentStopListener");
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        if (container == null) {
            // We have different layouts, and in one of them this
            // fragment's containing frame doesn't exist.  The fragment
            // may still be created from its saved state, but there is
            // no reason to try to create its view hierarchy because it
            // won't be displayed.  Note this is not needed -- we could
            // just run the code below, where we would create and return
            // the view hierarchy; it would just never be used.
            return null;
        }

        View view = inflater.inflate(R.layout.details_fragment, container, false);

        TextView imDescription = (TextView) view.findViewById(R.id.tv_image_description);
        ImageView imageView = (ImageView) view.findViewById(R.id.im_image_link);

        mImage = getImage();

        Picasso mPicasso = Picasso.with(imageView.getContext());
        mPicasso.setIndicatorsEnabled(true);
        mPicasso.load(mImage.getLink())
                .resize(1280, 720)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(imageView);

        imDescription.setText(mImage.getName());

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mStopListener.onFragmentDestroyView();

    }

    public void backButtonWasPressed() {
//        if (this != null) {
//            Toast.makeText(getActivity(), "Back button pressed", Toast.LENGTH_LONG)
//                    .show();
//        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.details_fragment_options, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_delete:
                mStopListener.onDeleted(mImage);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
