package com.roger.appvisitajava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.roger.Entities.VisitClient;
import com.roger.dao.VisitClientDAO;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.List;

public class SendVisitsActivity extends AppCompatActivity implements View.OnClickListener {
    private static String Method="RegistrarVisitas";
    private static String NAMESPACE = "http://tempuri.org/";
    private static String URL = "http://192.168.29.1/Prueba/WSCajaTrujillo.asmx?WSDL";
    private static String SOAPMethod = NAMESPACE+Method;
    Button btnSendVisits;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_visits);
        btnSendVisits = findViewById(R.id.btnSendVisits);
        btnSendVisits.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == btnSendVisits.getId())
        {
            VisitClientDAO dao = new VisitClientDAO(getApplicationContext());
            List<VisitClient> lst = dao.ListarPosiciones();
            String json = new Gson().toJson(lst);

            WSTask envio = new WSTask();
            envio.execute(json);
        }
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
