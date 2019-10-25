package com.roger.appvisitajava;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.ViewCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.roger.Entities.VisitClient;
import com.roger.dao.VisitClientDAO;

public class RegisterVisitActivity extends AppCompatActivity implements View.OnClickListener {
    EditText txtCliente,txtDNI;
    Button btnRegistrarCliente,btnVistaPrevia;
    Boolean bVistaPrevia = false;
    double nLongitud,nLatitud;
    LocationManager locationManager;
    double longitudeGPS=0,latitudeGPS = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_visit);
        txtCliente = (EditText)findViewById(R.id.txtCliente);
        txtDNI = (EditText)findViewById(R.id.txtDNI);


        btnRegistrarCliente = (Button)findViewById(R.id.btnRegistrarCliente);
        btnRegistrarCliente.setOnClickListener(this);
        btnVistaPrevia = (Button)findViewById(R.id.btnVistaPrevia);
        btnVistaPrevia.setOnClickListener(this);
        String serviceString = Context.LOCATION_SERVICE;
        locationManager = (LocationManager)getSystemService(serviceString);
        if(!checkLocation())
        {
            return;
        }else{
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                DefinirLocalizacion();
            }

        }

    }

    private void DefinirLocalizacion(){
        String provider = LocationManager.NETWORK_PROVIDER;
        String serviceString = Context.LOCATION_SERVICE;
        locationManager = (LocationManager)getSystemService(serviceString);

        Location location;
        try {
            location = locationManager.getLastKnownLocation(provider);
            updateWithNewLocation(location);
            locationManager.requestLocationUpdates(provider,1000,1,locationListenerGPS  );
            if (location!=null){

            }else{
                Toast.makeText(getApplicationContext(),"No se pudo recoger localizacion",Toast.LENGTH_LONG).show();
            }
        }catch (SecurityException e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private final LocationListener locationListenerGPS = new LocationListener() {
        public void onLocationChanged(Location location) {
            longitudeGPS = location.getLongitude();
            latitudeGPS = location.getLatitude();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    //RegistrarCoordenadas(latitudeGPS,longitudeGPS);

                }
            });
        }
        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        @Override
        public void onProviderEnabled(String s) {
        }
        @Override
        public void onProviderDisabled(String s) {
        }
    };

    private void updateWithNewLocation(Location location){
        if (location!=null){
            nLatitud = location.getLatitude();
            nLongitud = location.getLongitude();
        }else{
            nLatitud = 0;
            nLongitud = 0;
        }
    }

    private void RegistrarLocalizacion(){
        try {
            //location = locationManager.getLastKnownLocation(provider);
            //locationManager.requestLocationUpdates(provider,2000,10000,locationListener);
            if (( nLatitud != 0) && ( nLongitud != 0 )){
                VisitClient clienteVisita = new VisitClient();
                clienteVisita.setcClient( txtCliente.getText().toString()  );
                clienteVisita.setcDocument( txtDNI.getText().toString()  );
                clienteVisita.setnLatitude(nLatitud);
                clienteVisita.setnLength(nLongitud);

                VisitClientDAO clienteVisitaDAO = new VisitClientDAO(getApplicationContext());
                clienteVisitaDAO.Ingresar(clienteVisita);
                Toast.makeText(getApplicationContext(),"Cliente Registrado",Toast.LENGTH_LONG).show();
                finish();


            }else{
                Toast.makeText(getApplicationContext(),"No se pudo recoger localizacion",Toast.LENGTH_LONG).show();
            }


        } catch (SecurityException e) {
            //dialogGPS(this.getContext()); // lets the user know there is a problem with the gps
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btnRegistrarCliente.getId() ){
            if (Validar()) {
                RegistrarLocalizacion();
            }

        }else if ( v.getId() == btnVistaPrevia.getId() ){
            if ( VerVistaPrevia()  ){
                Intent i = new Intent(getApplicationContext(),MapsActivity.class);
                i.putExtra("iTipo",3);
                i.putExtra("nLatitud",nLatitud);
                i.putExtra("nLongitud",nLongitud);
                startActivity(i);
                bVistaPrevia = true;
            }else{
                Toast.makeText(getApplicationContext(),"No se pudo recoger localizacion",Toast.LENGTH_LONG).show();
                bVistaPrevia = false;
            }
        }
    }

    private boolean VerVistaPrevia(){
        try {
            //location = locationManager.getLastKnownLocation(provider);
            if ((nLatitud!=0) && (nLongitud!=0) ) {
                return true;
            }else{

                return false;
            }
        }catch (SecurityException e){
            return false;
        }
    }

    private boolean Validar(){
        if ( txtCliente.getText().toString().trim() == ""  ){
            Toast.makeText(getApplicationContext(),"Se debe indicar un cliente",Toast.LENGTH_LONG).show();
            return false;
        }else if( txtDNI.getText().toString().trim() == ""  ){
            Toast.makeText(getApplicationContext(),"Se debe indicar el nro de DNI",Toast.LENGTH_LONG).show();
            return false;
        }else if(bVistaPrevia == false){
            Toast.makeText(getApplicationContext(),"Se debe revisar la vista previa",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
    private boolean checkLocation() {
        if (!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }
    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Su ubicación esta desactivada.\npor favor active su ubicación " +
                        "usa esta app")
                .setPositiveButton("Configuración de ubicación", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }

}
