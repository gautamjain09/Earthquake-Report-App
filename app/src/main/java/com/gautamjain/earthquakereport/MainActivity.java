package com.gautamjain.earthquakereport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Event>> {

    TextView mEmptyStateTextView;

    private static final int EARTHQUAKE_LOADER_ID = 1;

    EarthquakeAdapter adapter;

    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=6&limit=10";

    private Loader<List<Event>> loader;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView earthquake_ListView = findViewById(R.id.list);

        adapter = new EarthquakeAdapter(MainActivity.this, new ArrayList<Event>());

        earthquake_ListView.setAdapter(adapter);

        // Intent for Opening website on click
        earthquake_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Event curr_Earthquake = adapter.getItem(position);
                Uri uri = Uri.parse(curr_Earthquake.getUrl());

                Intent web_intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(web_intent);
            }
        });

        // Empty View -> When no data
        mEmptyStateTextView =  findViewById(R.id.empty_view);
        earthquake_ListView.setEmptyView(mEmptyStateTextView);

        // Internet Connectivity
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected())
        {
            // Loader
            LoaderManager loaderManager = getSupportLoaderManager();
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
        }
        else if (networkInfo == null)
        {
            mEmptyStateTextView.setText(R.string.no_internet_connection);
            earthquake_ListView.setEmptyView(mEmptyStateTextView);
        }
        
    }

    @Override
    public Loader<List<Event>> onCreateLoader(int id,Bundle args)
    {
        return new EarthquakeLoader(this, USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Event>> loader, List<Event> data)
    {
        mEmptyStateTextView.setText(R.string.no_earthquakes);

        adapter.clear();

        if (data != null && !data.isEmpty())
        {
            adapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {
        adapter.clear();
    }

}