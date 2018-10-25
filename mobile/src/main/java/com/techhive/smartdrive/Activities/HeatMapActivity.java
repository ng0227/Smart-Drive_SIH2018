package com.techhive.smartdrive.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
import com.google.maps.android.heatmaps.WeightedLatLng;
import com.techhive.smartdrive.R;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class HeatMapActivity extends AppCompatActivity {

    private HeatmapTileProvider mProvider;
    private TileOverlay mOverlay;
    private ArrayList<WeightedLatLng> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heat_map);
    }

    private void addHeatMap() {
        List<LatLng> list = null;

        // Create a heat map tile provider, passing it the latlngs of the police stations.
        mProvider = new HeatmapTileProvider.Builder()
                .weightedData(data)
                .build();

        // Add a tile overlay to the map, using the heat map tile provider.
       // mOverlay = googleMaps.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));
    }
}
