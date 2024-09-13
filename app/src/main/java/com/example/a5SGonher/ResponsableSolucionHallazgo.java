package com.example.a5SGonher;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ResponsableSolucionHallazgo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_responsable_solucion_hallazgo);
        TextView titulo = (TextView)findViewById(R.id.titulo_toolbar);
        titulo.setText("Solución Hallazgo");
    }
}