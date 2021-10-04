package com.example.a5SGonher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuAuditoria extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_auditoria);

        Button btnAjustes;
        Button btnAuditar;


        btnAjustes=(Button) findViewById(R.id.BtnCrearAudtoria);
        btnAuditar=(Button) findViewById(R.id.btnAgregaRes);
        btnAjustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityAuditar();

            }
        });

        btnAuditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityAuditar2();

            }
        });
    }


    @Override
    public void onBackPressed() {
        Intent intent =new Intent(this,MainMenu.class);
        startActivity(intent);
    }

    public void openActivityAuditar()
    {
        Intent intent =new Intent(this,AuAudit.class);
        startActivity(intent);
    }

    public void openActivityAuditar2()
    {
        Intent intent =new Intent(this,AuAudit2.class);
        startActivity(intent);
    }
}
