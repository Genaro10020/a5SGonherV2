package com.example.a5SGonher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainResponsable extends AppCompatActivity {
    TextView titular_session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_responsable);
        TextView titulo = (TextView)findViewById(R.id.titulo_toolbar);
        titulo.setText("Menú Responsable");
        titular_session=(TextView)findViewById(R.id.textViewSession);

        //Recupero Informacion
        SharedPreferences preferences=getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String user= preferences.getString("User","No existe Usuario");
        String Rol= preferences.getString("Rol","No existe Usuario");
        String nombreAuditor= preferences.getString("NombreAuditor","No existe Usuario");
        String planta= preferences.getString("Planta","No existe Planta");
        String numeroNomina= preferences.getString("NumeroNomina","Sin Número de Nómina");
        titular_session.setText("Responsable:  "+nombreAuditor+" ("+numeroNomina+") "+planta);
        Button btnAbierto = (Button)findViewById(R.id.BtnAbiertos);
        Button btnCerrados = (Button)findViewById(R.id.BtnCerrados);
        btnAbierto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentAbiertos();
            }
        });

    }

    public void intentAbiertos(){
        Intent intent = new Intent(this,ResponsableListaHallazgos.class);
        String tipoHallazgo = "";//Abiertos
        intent.putExtra("tipoHallazgo",tipoHallazgo);
        startActivity(intent);
    }
    public void intentCerrados(){
        Intent intent = new Intent(this,ResponsableListaHallazgos.class);
        String tipoHallazgo = "Finalizado";//Finalizados
        intent.putExtra("tipoHallazgo",tipoHallazgo);
        startActivity(intent);
    }

    public void onBackPressed(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}