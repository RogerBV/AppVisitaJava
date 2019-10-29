package com.roger.appvisitajava;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.roger.db.VisitClientTable;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private int nTipo = 0;
    private double nLongitud = 0;
    private double nLatitud = 0;
    private String cDNI = "";
    private String cClient = "";
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Bundle bundle = getIntent().getExtras();
        nTipo = bundle.getInt("nTipo");
        if(nTipo == 3 || nTipo == 1 )
        {
            nLongitud = bundle.getDouble(VisitClientTable.nLength );
            nLatitud = bundle.getDouble(VisitClientTable.nLatitude);

        }
        if(nTipo == 1){
            cDNI = bundle.getString(VisitClientTable.cDocument);
            cClient = bundle.getString(VisitClientTable.cClient);
        }


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(nLatitud, nLongitud);
        if (nTipo == 3)
        {
            mMap.addMarker(new MarkerOptions().position(sydney).title("Vista Previa"));
        }else
        {
            mMap.addMarker(new MarkerOptions().position(sydney).title(cClient));
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setMinZoomPreference(0);
        mMap.setMaxZoomPreference(2090);
    }
}
