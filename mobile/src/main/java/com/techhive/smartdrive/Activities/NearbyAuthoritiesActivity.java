package com.techhive.smartdrive.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.techhive.smartdrive.R;
import com.techhive.smartdrive.model.PlacesList;

import java.util.ArrayList;

public class NearbyAuthoritiesActivity extends AppCompatActivity {

    private Button hospital,police,fire;
    private ArrayList<String> places = new ArrayList<>();

    private Double latitude,longitude,radius;
    private int PROXIMITY_RADIUS = 10000;

    private static final String API_KEY ="AIzaSyBHy-bVSFWltdOOGgD1usXNBXNXm6Y544s";

    private static final String PLACES_SEARCH_URL = "https://maps.googleapis.com/maps/api/place/search/json?";
    private static final String PLACES_TEXT_SEARCH_URL = "https://maps.googleapis.com/maps/api/place/search/json?";
    private static final String PLACES_DETAILS_URL = "https://maps.googleapis.com/maps/api/place/details/json?";

    // https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.21.1725569,79.05884500000002&radius=2000&type=hospital&key=AIzaSyAZhumdvOPw_R4u9J_40YVDZM5DnMaUG2Y

//AIzaSyDoYtYu_KiFHt-X36oaR0gKl0EHtBmJIq4




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_authorities);

        latitude=21.1725569;
        longitude=79.05884500000002;

        hospital=findViewById(R.id.hospital_bt);
        police=findViewById(R.id.police_bt);
        fire=findViewById(R.id.fire_station_bt);

        places.add("hospital");
        places.add("fire_station");
        places.add("police");

        hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = getUrl(latitude, longitude, places.get(0));
                Object[] DataTransfer = new Object[2];
                DataTransfer[0] = null;
                DataTransfer[1] = url;
                Log.d("onClick", url);
                GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
                getNearbyPlacesData.execute(DataTransfer);
                Toast.makeText(NearbyAuthoritiesActivity.this,"Nearby Hospitals", Toast.LENGTH_LONG).show();
            }
        });

        police.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        fire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private String getUrl(double latitude, double longitude, String nearbyPlace) {

        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=" + latitude + "," + longitude);
        googlePlacesUrl.append("&radius=" + PROXIMITY_RADIUS);
        googlePlacesUrl.append("&type=" + nearbyPlace);
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + API_KEY);
        Log.d("getUrl", googlePlacesUrl.toString());
        return (googlePlacesUrl.toString());
    }
}
