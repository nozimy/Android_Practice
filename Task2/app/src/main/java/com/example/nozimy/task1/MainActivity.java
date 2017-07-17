package com.example.nozimy.task1;


import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v4.app.FragmentManager;


import com.example.nozimy.task1.ImageGalleryAdapter.ImageGalleryAdapterOnClickHandler;


public class MainActivity extends AppCompatActivity implements ImageGalleryAdapterOnClickHandler, MyDetailsFragment.OnFragmentStopListener {

    private RecyclerView mRecyclerView;
    private ImageGalleryAdapter mImageGalleryAdapter;

    private MyDetailsFragment detailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_image_gallery);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        /*
        * Use this setting to improve performance if you know that changes in content do not
        * change the child layout size in the RecyclerView
        */
        mRecyclerView.setHasFixedSize(true);

        mImageGalleryAdapter = new ImageGalleryAdapter(this);

        mImageGalleryAdapter.setImagesData(MockDataHelper.getImages());

        mRecyclerView.setAdapter(mImageGalleryAdapter);

    }

    @Override
    public void onClick(Image image) {


        mRecyclerView.setVisibility(mRecyclerView.INVISIBLE);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        detailsFragment = MyDetailsFragment.newInstance(image);

        fragmentTransaction.add(R.id.fragment_container , detailsFragment);
        fragmentTransaction.setTransition(fragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    @Override
    public void onFragmentStopped() {
        mRecyclerView.setVisibility(mRecyclerView.VISIBLE);
    }

    @Override
    public void onDeleted() {
        getFragmentManager().popBackStack();
        mRecyclerView.invalidate();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        detailsFragment.backButtonWasPressed();
    }
}
