package com.example.a5SGonher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.a5SGonher.ui.ListosEnviar;

public class MainMenu extends AppCompatActivity {
    Button btnAjustesr,btnAuditar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Button BotonsCrearAuditoria;
        Button btnAuditar;
        Button btnReportes;
        Button btnLogOut;
        Button btnHistorial;
        Button btnHallazgos;
        TextView titulo_too, titular_session;


        titular_session=(TextView)findViewById(R.id.textViewSession);
        BotonsCrearAuditoria=(Button) findViewById(R.id.BtnCrearAudtoria);
        btnAuditar=(Button) findViewById(R.id.btnAgregaRes);
        titulo_too =(TextView) findViewById(R.id.titulo_toolbar);
        titulo_too.setText("Menú");


        btnHistorial=(Button) findViewById(R.id.btnAgregaF);
        btnHallazgos=(Button) findViewById(R.id.crearAuditoria);
        cargarPreferencias();

         //Recupero Informacion Genaro
        SharedPreferences preferences=getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String user= preferences.getString("User","No existe Usuario");
        String Rol= preferences.getString("Rol","No existe Usuario");
        String nombreAuditor= preferences.getString("NombreAuditor","No existe Usuario");
        titular_session.setText("Auditor:  "+nombreAuditor);


            if(!Rol.equals("administrador"))
            {

             //  BotonsCrearAuditoria.setEnabled(false);
            //   BotonsCrearAuditoria.setBackgroundColor(Color.rgb(115, 125, 132));

            }



        BotonsCrearAuditoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityAjustes();

            }
        });

        btnAuditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityAuditar();

            }
        });
        btnHallazgos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityReportes();

            }
        });




        btnHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityHistorial();

            }
        });


    }
    @Override
    public void onBackPressed() {
       // Intent intent =new Intent(this,MainActivity.class);
        //startActivity(intent);
    }
    private void cargarPreferencias() {

        SharedPreferences preferences=getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String user= preferences.getString("User","No existe Usuario");
        String Rol= preferences.getString("Rol","No existe Usuario");

    }

//CREAR AUDITORIA Genaro
    public void openActivityAjustes()
    {
        Intent intent =new Intent(this,AgregarPlanta.class);
        startActivity(intent);
    }
    public void openActivityAuditar()
    {
        Intent intent =new Intent(this,AuAudit2.class);
        startActivity(intent);
    }

    public void openActivityHistorial()
    {
       Intent intent =new Intent(this, ListosEnviar.class);
        startActivity(intent);
    }

    public void openActivityHallazgos()
    {
        Intent intent =new Intent(this,DownloadImage.class);
        startActivity(intent);
    }

    public void openActivityReportes()
    {

            SharedPreferences preferences=getSharedPreferences("credenciales", Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = preferences.edit();


            editor.putString("User", "User");
            editor.putString("Rol", "Rol");
            editor.putInt("Conected", 0);
            editor.commit();

        Intent intent =new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
