package com.example.a5SGonher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.a5SGonher.ui.main.CrearAuditoria;

public class AjustesMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes_menu);

        Button btnU;
        Button btnF;
        Button btnR;
        Button btnCAu;

        btnU=(Button) findViewById(R.id.BtnCrearAudtoria);
        btnF=(Button) findViewById(R.id.btnAgregaF);
        btnR=(Button) findViewById(R.id.btnAgregaRes);
        btnCAu=(Button) findViewById(R.id.crearAuditoria);


        btnU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityUsuarios();

            }
        });

        btnF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityPlanta();

            }
        });
        btnR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityResponsable();

            }
        });

        btnCAu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CrearAuditoria();

            }
        });

    }

    public void openActivityUsuarios()
    {
        Intent intent =new Intent(this,AgregarUsuario.class);
        startActivity(intent);
    }

    public void openActivityResponsable()
    {
        Intent intent =new Intent(this,AgregarResponsable.class);
        startActivity(intent);
    }
    public void openActivityPlanta()
    {
        Intent intent =new Intent(this,AgregarPlanta.class);
        startActivity(intent);
    }

    public void CrearAuditoria()
    {
        Intent intent =new Intent(this, CrearAuditoria.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Intent intent =new Intent(this,MainMenu.class);
        startActivity(intent);
    }

}
