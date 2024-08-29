package com.example.a5SGonher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class HallazgosRecorridos extends AppCompatActivity {
    String User,NumeroNomina;
    Button nuevoHallazgo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hallazgos_recorridos);
        TextView titulo_toolbar = (TextView)findViewById(R.id.titulo_toolbar);
        nuevoHallazgo = (Button)findViewById(R.id.btnNuevoHallazgo);
        SharedPreferences preferences=getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        NumeroNomina = preferences.getString("NumeroNomina","No existe Número Nómina");
        titulo_toolbar.setText("Hallazgos Recorrido");
        Intent intent = getIntent();
        String codigo = intent.getStringExtra("Codigo");

        nuevoHallazgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevoHallazgo.setBackgroundResource(R.drawable.boton_verde);
                intentDatosHallazgoRecorrido();
            }
        });
    }


    private void intentDatosHallazgoRecorrido(){
        Intent intent = new Intent(this,DatosHallazgoRecorrido.class);
        startActivity(intent);
    }

    protected void onResume(){
        super.onResume();
        nuevoHallazgo.setBackgroundResource(R.drawable.boton_crear);
    }


}