package com.example.a5SGonher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.a5SGonher.ui.main.FormularioCustomAuditoria;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CustomAuditoria extends AppCompatActivity {

    RequestQueue requestQueue;
    TextView tView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_auditoria);
        final String nombreAuditoria = getIntent().getStringExtra("EXTRA_SESSION_ID");

        tView=(TextView)findViewById(R.id.textvSPlanta);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs2);

        tView.setText(nombreAuditoria);



        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if(tab.getPosition()==0) {

                    Config(nombreAuditoria);
                }

                if(tab.getPosition()==1) {

                    buscarProducto("https://vvnorth.com/buscar_sCustom.php?planta="+ nombreAuditoria +"&ss="+"1"+ ""  ,nombreAuditoria,nombreAuditoria,nombreAuditoria);

                }

                if(tab.getPosition()==2) {
                    buscarProducto("https://vvnorth.com/buscar_sCustom.php?planta="+ nombreAuditoria +"&ss="+"2"+ ""  ,nombreAuditoria,nombreAuditoria,nombreAuditoria);

                }

                if(tab.getPosition()==3) {

                    buscarProducto("https://vvnorth.com/buscar_sCustom.php?planta="+ nombreAuditoria +"&ss="+"3"+ ""  ,nombreAuditoria,nombreAuditoria,nombreAuditoria);
                }


                if(tab.getPosition()==4) {

                    buscarProducto("https://vvnorth.com/buscar_sCustom.php?planta="+ nombreAuditoria +"&ss="+"4"+ ""  ,nombreAuditoria,nombreAuditoria,nombreAuditoria);
                }

                if(tab.getPosition()==5) {

                    buscarProducto("https://vvnorth.com/buscar_sCustom.php?planta="+ nombreAuditoria +"&ss="+"5"+ ""  ,nombreAuditoria,nombreAuditoria,nombreAuditoria);

                }



            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {


                if(tab.getPosition()==0) {

                    final LinearLayout ll3 = (LinearLayout)findViewById(R.id.layoutS);
                    ll3.removeAllViews();
                }

                if(tab.getPosition()==1) {
                    final LinearLayout ll3 = (LinearLayout)findViewById(R.id.layoutS);
                    ll3.removeAllViews();


                }

                if(tab.getPosition()==2) {

                    final LinearLayout ll3 = (LinearLayout)findViewById(R.id.layoutS);
                    ll3.removeAllViews();
                }

                if(tab.getPosition()==3) {
                    final LinearLayout ll3 = (LinearLayout)findViewById(R.id.layoutS);
                    ll3.removeAllViews();

                }
                if(tab.getPosition()==4) {
                    final LinearLayout ll3 = (LinearLayout)findViewById(R.id.layoutS);
                    ll3.removeAllViews();

                }

                if(tab.getPosition()==5) {
                    final LinearLayout ll3 = (LinearLayout)findViewById(R.id.layoutS);
                    ll3.removeAllViews();

                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        Config(nombreAuditoria);
      //  buscarProducto("http://192.168.1.101:8080/Ghoner/buscar_s.php?area="+nombreArea +"&planta="+ nombrePlanta+"&subarea="+ nombresubArea +"&subarea2="+"1" + "" ,nombrePlanta,nombreArea,nombresubArea);

        //buscarProducto("http://192.168.1.101:8080/Ghoner/buscar_sCustom.php?planta="+ nombreAuditoria + "" ,nombreAuditoria,nombreAuditoria,nombreAuditoria);

      //  buscarProducto("http://192.168.1.101:8080/Ghoner/buscar_sCustom.php?planta="+ nombreAuditoria +"&ss="+"5"+ ""  ,nombreAuditoria,nombreAuditoria,nombreAuditoria);



    }




    public void Config(final String nombrePlanta )
    {
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Button myButton = new Button(this);
        myButton.setText("Agregar");

        LinearLayout ll = (LinearLayout)findViewById(R.id.layoutS);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll.addView(myButton, lp);
        /////////////////////////
        myButton.setBackgroundColor(Color.rgb(128, 42, 42));
        myButton.setTextColor(Color.rgb(179, 179, 179));
        lp.setMargins(0, 0, 0, 10);
        myButton.setLayoutParams( lp);
        /////////////////////////////////////////
        myButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                formularioS(nombrePlanta );

            }
        });


    }

    private void buscarProducto(String URL,final String nombrePlanta,final String nombreArea,final String SubArea)
    {

        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        String nombre;
                        String nombre2;
                        jsonObject = response.getJSONObject(i);
                        // editT.setText(jsonObject.getString("Planta"));

                        nombre2=jsonObject.getString("NombreS");

                        boton(nombre2);


                    } catch (JSONException e) {
                        //    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //   Toast.makeText(getApplicationContext(),"ERRO DE CONEXION",Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }






    public void boton(final String nombreBoton)
    {
        Button myButton3 = new Button(this);

        myButton3.setText(nombreBoton);

        final LinearLayout ll3 = (LinearLayout)findViewById(R.id.layoutS);
        LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll3.addView(myButton3, lp3);


        //ll3.removeAllViews();

        /////////////////////////
        myButton3.setBackgroundColor(Color.rgb(128, 42, 42));
        myButton3.setTextColor(Color.rgb(179, 179, 179));
        lp3.setMargins(0, 0, 0, 10);
        myButton3.setLayoutParams( lp3);
        /////////////////////////////////////////
        myButton3.setOnClickListener(new View.OnClickListener()  {
            // String NomPlanta =nFinal;
            public void onClick(View view) {

             //   AuditoriaS(nombrePlanta,nombreArea,nombreSubarea,nombreBoton,Datos);

            }
        });
    }




    public void formularioS(String nPlanta)
    {
        Intent intent =new Intent(this, FormularioCustomAuditoria.class);
        intent.putExtra("EXTRA_SESSION_ID", nPlanta);


        startActivity(intent);
    }


    private void ejecutarservicio(String URL,final String NPlanta,final String NArea,final String NSubArea,final String NS,final String NPregunta,final String NDescripcion)
    {
        StringRequest stringRequest=new  StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "OPERACION EXITOSA", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros =new HashMap<String,String>();
                // int radioId=radioGroup.getCheckedRadioButtonId();
                //  radioButton= findViewById(radioId);
                // String strI = String.valueOf(radioButton.getText());

                parametros.put("ss",NPregunta); ////////////Nombre de la pregunta

                parametros.put("datos", NDescripcion);       //////// Descripcion de la pregunta
                parametros.put("NombrePlanta",NPlanta);
                parametros.put("NombreArea",NArea);
                parametros.put("NombreAreaSS",NS);                          //////////////numero de la S


                parametros.put("NombrenombreArea",NSubArea);

                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }





}
