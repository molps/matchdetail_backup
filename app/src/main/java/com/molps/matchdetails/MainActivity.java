package com.molps.matchdetails;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<List<PlayerStats>>> {

    private RecAdapter mAdapter;
    private String x = "";
    private int prvi;
    private int poslednji;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recView);
        mAdapter = new RecAdapter(this, Glide.with(this), recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setHasFixedSize(true);


        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<List<List<PlayerStats>>> onCreateLoader(int id, Bundle args) {

        return new NetworkAsyncTaskLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<List<PlayerStats>>> loader, List<List<PlayerStats>> data) {
        Toast.makeText(this, "Load finished", Toast.LENGTH_SHORT).show();
        mAdapter.swapData(data);
    }

    @Override
    public void onLoaderReset(Loader<List<List<PlayerStats>>> loader) {

    }

    private static class NetworkAsyncTaskLoader extends AsyncTaskLoader<List<List<PlayerStats>>> {

        private List<List<PlayerStats>> mData;

        public NetworkAsyncTaskLoader(Context context) {
            super(context);
        }

        @Override
        protected void onStartLoading() {
            if (mData != null)
                deliverResult(mData);
            else
                forceLoad();
        }

        @Override
        public List<List<PlayerStats>> loadInBackground() {
            return NetworkUtils.fetchData(3319664483L);
        }

        @Override
        public void deliverResult(List<List<PlayerStats>> data) {
            mData = data;
            super.deliverResult(data);
        }
    }
}
