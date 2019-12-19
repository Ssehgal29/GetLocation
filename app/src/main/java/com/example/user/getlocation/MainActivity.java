package com.example.user.getlocation;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
TextView textView;

LocationManager lm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=findViewById(R.id.textView);

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) //checking for permission
                != PackageManager.PERMISSION_GRANTED && // cause in the beginning permission is not granted !=
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                                               Manifest.permission.ACCESS_FINE_LOCATION},0);
            return; // coarse saves current longitude and latitude
                    // fine saves stationary longitude and latitude

        }

        lm= (LocationManager) getSystemService(LOCATION_SERVICE);  //location manager will have current location reference
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {  // 0 0 for current time and location
            @Override
            public void onLocationChanged(Location location)
            {
                double latitude=location.getLatitude();
                double longitude=location.getLongitude();
                textView.setText("LATITUDE:-"+latitude+"\nLONGITUDE:-"+longitude);

                Geocoder geocoder = new Geocoder(MainActivity.this); // this will latitude and longitude in address
                try
                {
                 List<Address> adr = geocoder.getFromLocation(latitude,longitude,1);
                 String country=adr.get(0).getCountryName();
                 String locality =  adr.get(0).getLocality();
                 String postalcode = adr.get(0).getPostalCode();
                 String address = adr.get(0).getAddressLine(0); //0 stores the address
                    textView.append("\n\n"+country+","+locality+","+postalcode+","+address);
                }
                catch (Exception e)
                {

                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle)
            {

            }

            @Override
            public void onProviderEnabled(String s)
            {

            }

            @Override
            public void onProviderDisabled(String s)
            {

            }
        }) ;
    }
}
