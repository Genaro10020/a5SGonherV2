package com.example.a5SGonher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class ConfirmarCrearAuditoria extends AppCompatActivity {
    String numeroNomina, Planta;
    TextView leyenda_creando;
    TextView btncrear;
    boolean onClickEnabled = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_crear_auditoria);
        TextView dato = (TextView)findViewById(R.id.mostrandoR);
        leyenda_creando = (TextView)findViewById(R.id.leyenda_creando_auditoria);
        final String recibiendosubArea = getIntent().getStringExtra("subArea");
        dato.setText("Crear auditoría en "+recibiendosubArea+" ?");

        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        numeroNomina = preferences.getString("NumeroNomina","No existe Número de nómina");
        Planta = preferences.getString("Planta","No existe Planta");
        //Log.e("Numoer",":"+numeroNomina);

        TextView titulo = (TextView)findViewById(R.id.titulo_toolbar);
        titulo.setText("Crear Auditoría");

        btncrear = (TextView)findViewById(R.id.text_crear);
        btncrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Log.e("Clic","en btn");

                if (onClickEnabled){
                    btncrear.setText("Espere creado..");
                    btncrear.setBackgroundResource(R.drawable.btn_desabilitado);
                    onClickEnabled=false;
                    leyenda_creando.setVisibility(View.VISIBLE);
                    crearAuditoria("https://vvnorth.com/5sGhoner/crearAuditoria_v2.php",recibiendosubArea);
                }else{
                    //Toast.makeText(getApplicationContext(),"Espere, creando auditoría",Toast.LENGTH_LONG).show();
                  Toast toast = Toast.makeText(getApplicationContext(),"Espere, creando auditoría",Toast.LENGTH_SHORT);
                  toast.setGravity(Gravity.CENTER,0,0);
                  toast.show();
                }


            }
        });
    }


    public void cancelar(View view){
        Intent intent = new Intent(this,MainMenu.class);
        startActivity(intent);
    }

        private void crearAuditoria(String URL, final String recibiendosubArea){
            StringRequest stringRequest=new  StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("Crear AUDITORIA",":"+response);
                    if (response.equals("Existe una sin contestar")){
                        retornarListaAuditorias();
                        Toast toas = Toast.makeText(getApplicationContext(),"Ya existe una auditoria creada",Toast.LENGTH_LONG);
                        toas.setGravity(Gravity.CENTER,0,0);
                        toas.show();
                    }else{
                        retornaraMenu();
                        //Toast.makeText(getApplicationContext(),"Creada con éxito",Toast.LENGTH_SHORT).show();
                        Toast toas = Toast.makeText(getApplicationContext(),"Creada con éxito",Toast.LENGTH_LONG);
                        toas.setGravity(Gravity.CENTER,0,0);
                        toas.show();
                    }
                    // buscarProducto("https://vvnorth.com/comparacion_auditorf.php",NPlanta);



                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    onClickEnabled=true;
                    btncrear.setText("Crear");
                    btncrear.setBackgroundResource(R.drawable.boton_crear);
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                }
            })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> parametros =new HashMap<String,String>();
                    parametros.put("NombreSubArea",recibiendosubArea);
                    parametros.put("NumeroNomina",numeroNomina);
                    parametros.put("Planta",Planta);

                    return parametros;
                }
            };
            RequestQueue requestQueue= Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }


    public void retornaraMenu(){

        Intent intent =new Intent(this,MainMenu.class);
        startActivity(intent);
    }

    public void retornarListaAuditorias(){
        Intent intent = new Intent(this,AuAudit2.class);
        startActivity(intent);
    }

}