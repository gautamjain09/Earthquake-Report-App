package com.gautamjain.earthquakereport;

import android.content.Context;
import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

public class EarthquakeLoader extends AsyncTaskLoader<List<Event>> {

    private static final String LOG_TAG = EarthquakeLoader.class.getName();

    private String mUrl;

    public EarthquakeLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Event> loadInBackground() {

        if (mUrl == null) {
            return null;
        }

        List<Event> result = QueryUtils_API.fetchEarthquakeData(mUrl);
        return result;
    }

}
