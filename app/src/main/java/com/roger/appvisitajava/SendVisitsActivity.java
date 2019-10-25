package com.roger.appvisitajava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class SendVisitsActivity extends AppCompatActivity {
    private static String Method="RegistrarVisitas";
    private static String NAMESPACE = "http://tempuri.org/";
    private static String URL = "http://192.168.29.1/Prueba/WSCajaTrujillo.asmx?WSDL";
    private static String SOAPMethod = NAMESPACE+Method;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_visits);
    }
    private class WSTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            SoapObject request = new SoapObject(NAMESPACE,Method);
            request.addProperty("json",params[0]);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            try{
                androidHttpTransport.call(SOAPMethod, envelope);
                SoapPrimitive resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();
                return resultsRequestSOAP.toString();
            }catch(Exception ex){

            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
