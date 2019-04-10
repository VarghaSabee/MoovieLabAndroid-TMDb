package com.example.moovielab.data.services;

import android.app.IntentService;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;

import com.example.moovielab.R;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */
public abstract class ListService extends IntentService {

    public final String TAG = "SERVICE_" + getClass().getSimpleName();

    protected static final String PAGE = ListService.class.getName() + ".id";

    public ListService() {
        super("MovieListService");
    }

    /**
     * Detect if wifi is on or not
     * @return true if connected by wifi
     */
    protected boolean isWifi() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        return mWifi.isConnected();
    }

    /**
     * Detect the user preferences for downloading the lists
     * @return true if wifi is connected and the preference allow to download by wifi
     */
    protected boolean canDownload() {
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(this);
        boolean onlyByWifi = p.getBoolean(getResources().getString(R.string.only_wifi), false);

        return !onlyByWifi || isWifi();
    }
}
