package com.example.nozimy.task1;


import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;


import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements MyDetailsFragment.OnFragmentStopListener, SelectableImageListAdapterViewHolder.OnItemSelectedListener, SelectableImageListAdapterViewHolder.OnLongClickListener {

    private RecyclerView mRecyclerView;

    private MyDetailsFragment detailsFragment;

    private SelectableImageListAdapter selectableAdapter;
    private boolean menuDeleteItemVisiible = false;

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

        selectableAdapter = new SelectableImageListAdapter(this,this, MockDataHelper.getImages(), true);
        mRecyclerView.setAdapter(selectableAdapter);

        detailsFragment = (MyDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
    }


    @Override
    public void onFragmentDestroyView() {
        mRecyclerView.setVisibility(mRecyclerView.VISIBLE);
    }

    @Override
    public void onDeleted(SelectableImage im) {
        getSupportFragmentManager().popBackStack();

        ArrayList<SelectableImage> toDelete = new ArrayList<SelectableImage>();
        toDelete.add(im);
        selectableAdapter.deleteSelectedItems(toDelete);

        selectableAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        MyDetailsFragment fragment = (MyDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .remove(fragment)
                    .commitAllowingStateLoss();
        }
    }

    @Override
    public void onItemSelected(SelectableImage selectableImage) {

        ArrayList<SelectableImage> selectedItems = selectableAdapter.getSelectedItems();

        if (selectedItems.size() == 0){
            menuDeleteItemVisiible = false;
            invalidateOptionsMenu();
        }
    }

    @Override
    public void onItemCliked(SelectableImage image) {
        mRecyclerView.setVisibility(mRecyclerView.INVISIBLE);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        detailsFragment = MyDetailsFragment.newInstance(image);

        fragmentTransaction.replace(R.id.fragment_container , detailsFragment);
        fragmentTransaction.setTransition(fragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onLongClick(boolean isAdapterCheckable) {
        if (isAdapterCheckable){
            menuDeleteItemVisiible = true;
            invalidateOptionsMenu();
        }
    }

    @Override
    public boolean isAdapterCheckable() {
        return selectableAdapter.isAdapterCheckable();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_options, menu);

        if (menuDeleteItemVisiible){
            menu.findItem(R.id.list_action_delete).setVisible(true);
        }else{
            menu.findItem(R.id.list_action_delete).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.list_action_delete:
                selectableAdapter.deleteSelectedItems(selectableAdapter.getSelectedItems());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
