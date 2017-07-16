package com.example.nozimy.task1;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class MyDetailsFragment extends Fragment {

    OnFragmentStopListener mStopListener;
    // Container Activity must implement this interface
    public interface OnFragmentStopListener {
        public void onFragmentStopped();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mStopListener = (OnFragmentStopListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnArticleSelectedListener");
        }

    }

    /**
     * Create a new instance of MyDetailsFragment, initialized to
     * show the details of image at 'index'.
     */
    public static MyDetailsFragment newInstance(String imageDesc, String imageLink) {
        MyDetailsFragment f = new MyDetailsFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putString("description", imageDesc);
        args.putString("link", imageLink);
        f.setArguments(args);

        return f;
    }

    public String getDescription() {
        return getArguments().getString("description", "");
    }

    public String getLink() {
        return getArguments().getString("link", "");
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

        Picasso mPicasso = Picasso.with(imageView.getContext());
        mPicasso.setIndicatorsEnabled(true);
        mPicasso.load(getLink())
                .resize(400, 200)
                .centerCrop()
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(imageView);

        imDescription.setText(getDescription());

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mStopListener.onFragmentStopped();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mStopListener.onFragmentStopped();
    }
}
