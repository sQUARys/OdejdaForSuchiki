package com.example.mac.suchik;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Community extends AsyncTask<Void, Void, Response> {
    private Context mCtx;
    private double lat, lng;
    private Callbacks callbacks;

    public Community(Context context, String[] location, Callbacks callbacks) {
        this.mCtx = context;
        this.lat = Double.valueOf(location[0]);
        this.lng = Double.valueOf(location[1]);
        this.callbacks = callbacks;
    }

    @Override

    protected Response doInBackground(Void... voids) {
        Geocoder geocoder = new Geocoder(mCtx, Locale.getDefault());
        String result = null;
        try {
            List<Address> list = geocoder.getFromLocation(lat, lng, 1);
            if (list != null && list.size() > 0) {
                Address address = list.get(0);
                result = address.getLocality();
                //Log.d("Community", result);
            }
        } catch (IOException e) {
            Log.e("Community", "Impossible to connect to Geocoder", e);
        }
        return new Response<>(ResponseType.COMMUNITY, result);
    }

    @Override
    protected void onPostExecute(Response response) {
        super.onPostExecute(response);
        callbacks.onLoad(response);
    }
}

