package com.roger.appvisitajava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
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
        DefinirLocalizacion();
    }

    private void DefinirLocalizacion(){
        String provider = LocationManager.NETWORK_PROVIDER;
        LocationManager locationManager;
        String serviceString = Context.LOCATION_SERVICE;
        locationManager = (LocationManager)getSystemService(serviceString);

        Location location;
        try {
            location = locationManager.getLastKnownLocation(provider);
            updateWithNewLocation(location);
            locationManager.requestLocationUpdates(provider,1000,1,locationListener  );
            if (location!=null){


            }else{
                Toast.makeText(getApplicationContext(),"No se pudo recoger localizacion",Toast.LENGTH_LONG).show();
            }
        }catch (SecurityException e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            updateWithNewLocation(location);
        }

        @Override
        public void onProviderDisabled(String provider) {
            updateWithNewLocation(null);
        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

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

        Location location;

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
        /*LocationManager locationManager;
        String serviceString = Context.LOCATION_SERVICE;
        locationManager = (LocationManager)getSystemService(serviceString);
        String provider = LocationManager.PASSIVE_PROVIDER;
        Location location;*/
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

}
