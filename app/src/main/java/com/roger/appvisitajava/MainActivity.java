package com.roger.appvisitajava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnRegisterClient,btnListClients,btnPositionsList,btnEliminar,btnEnviar,btnActualizar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnRegisterClient = findViewById(R.id.btnRegisterClient);
        btnRegisterClient.setOnClickListener(this);
        btnListClients = findViewById(R.id.btnListClients);
        btnListClients.setOnClickListener(this);
        btnPositionsList = findViewById(R.id.btnPositionsList);
        btnPositionsList.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        if(v.getId() == btnRegisterClient.getId())
        {
            i = new Intent(getApplicationContext(),RegisterVisitActivity.class);
        }else{
            i = new Intent(getApplicationContext(),RegisterVisitActivity.class);
        }
        startActivity(i);
    }
}
