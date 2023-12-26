package com.example.GameMine.Utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationHelper {

    private Context context;

    public LocationHelper(Context context) {
        this.context = context;
    }

    public void getLocationDetails(double latitude, double longitude) {
        new GeocodeAsyncTask().execute(latitude, longitude);
    }

    private class GeocodeAsyncTask extends AsyncTask<Double, Void, String> {

        @Override
        protected String doInBackground(Double... params) {
            double latitude = params[0];
            double longitude = params[1];

            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            String locationDetails = "jjjjjj";

            try {
                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

                if (addresses != null && addresses.size() > 0) {
                    Address address = addresses.get(0);
                    String city = address.getLocality();
                    String country = address.getCountryName();

                    locationDetails = "City: " + city + "\nCountry: " + country;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            Log.d("f1",locationDetails);
            return locationDetails;
        }

        @Override
        protected void onPostExecute(String result) {
            showToast(context, result);
        }
    }

    private void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
