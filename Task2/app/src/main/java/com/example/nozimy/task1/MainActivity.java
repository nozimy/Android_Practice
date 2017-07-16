package com.example.nozimy.task1;


import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.FragmentManager;


import com.example.nozimy.task1.ImageGalleryAdapter.ImageGalleryAdapterOnClickHandler;


public class MainActivity extends AppCompatActivity implements ImageGalleryAdapterOnClickHandler, MyDetailsFragment.OnFragmentStopListener {

    //private TextView mainTextView;
    private TextView mErrorMessageDisplay;

    private RecyclerView mRecyclerView;
    private ImageGalleryAdapter mImageGalleryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mainTextView = (TextView) findViewById(R.id.main_textView);
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
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

    Toast mToast;

    @Override
    public void onClick(Image image) {
        Context context = this;
        if (mToast == null) { // Initialize toast if needed
            mToast = Toast.makeText(context, "", Toast.LENGTH_LONG);
        }
        mToast.setText(image.name);
        mToast.show();

        mRecyclerView.setVisibility(mRecyclerView.INVISIBLE);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MyDetailsFragment detailsFragment = MyDetailsFragment.newInstance(image.name, image.link);

        fragmentTransaction.add(R.id.fragment_container , detailsFragment);
        fragmentTransaction.setTransition(fragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    //    private void showErrorMessage() {
//               // TODO (44) Hide mRecyclerView, not mWeatherTextView
//              // COMPLETED (44) Hide mRecyclerView, not mWeatherTextView
//         /* First, hide the currently visible data */
//                mWeatherTextView.setVisibility(View.INVISIBLE);
//                mRecyclerView.setVisibility(View.INVISIBLE);
//         /* Then, show the error */
//        mErrorMessageDisplay.setVisibility(View.VISIBLE);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_delete) {
            //mainTextView.setText("WORKING!");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentStopped() {
        mRecyclerView.setVisibility(mRecyclerView.VISIBLE);
    }
}
