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
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EliminarS extends AppCompatActivity  implements ExampleDialog.ExampleDialogListener {
    RequestQueue requestQueue;
    TextView tView,tView2,tView3;
    String DeleteThis;
    String GNombrePlanta;
    String GNombreArea;
    String GNombreSubArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_s);
        final String nombrePlanta = getIntent().getStringExtra("EXTRA_SESSION_ID");
        final String nombreArea = getIntent().getStringExtra("EXTRA_SESSION_ID2");
        final String nombresubArea = getIntent().getStringExtra("EXTRA_SESSION_ID3");
        tView=(TextView)findViewById(R.id.textvSPlanta);
        tView2=(TextView)findViewById(R.id.textvSArea);
        tView3=(TextView)findViewById(R.id.textvSubArea);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);



        tView.setText(nombrePlanta);
        tView2.setText(nombreArea);
        tView3.setText(nombresubArea);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String numeroS="0" ;
                if(tab.getPosition()==0) {

                    numeroS="1";
                    buscarProducto("https://vvnorth.com/buscar_s.php?area="+nombreArea +"&planta="+ nombrePlanta+"&subarea="+ nombresubArea +"&subarea2="+numeroS + "" ,nombrePlanta,nombreArea,nombresubArea,numeroS);
                }

                if(tab.getPosition()==1) {
                    numeroS="2";
                    buscarProducto("https://vvnorth.com/buscar_s.php?area="+nombreArea +"&planta="+ nombrePlanta+"&subarea="+ nombresubArea +"&subarea2="+numeroS + "" ,nombrePlanta,nombreArea,nombresubArea,numeroS);


                }

                if(tab.getPosition()==2) {
                    numeroS="3";
                    buscarProducto("https://vvnorth.com/buscar_s.php?area="+nombreArea +"&planta="+ nombrePlanta+"&subarea="+ nombresubArea +"&subarea2="+numeroS + "" ,nombrePlanta,nombreArea,nombresubArea,numeroS);

                }

                if(tab.getPosition()==3) {
                    numeroS="4";
                    buscarProducto("https://vvnorth.com/buscar_s.php?area="+nombreArea +"&planta="+ nombrePlanta+"&subarea="+ nombresubArea +"&subarea2="+numeroS + "" ,nombrePlanta,nombreArea,nombresubArea,numeroS);

                }
                if(tab.getPosition()==4) {

                    numeroS="5";
                    buscarProducto("https://vvnorth.com/buscar_s.php?area="+nombreArea +"&planta="+ nombrePlanta+"&subarea="+ nombresubArea +"&subarea2="+numeroS + "" ,nombrePlanta,nombreArea,nombresubArea,numeroS);
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

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        buscarProducto("https://vvnorth.com/buscar_s.php?area="+nombreArea +"&planta="+ nombrePlanta+"&subarea="+ nombresubArea +"&subarea2="+"1" + "" ,nombrePlanta,nombreArea,nombresubArea,"1");


    }

    private void ejecutarservicio(String URL,final String nombreBoton)
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
                // parametros.put("NombrePlanta",edtEmpresa.getText().toString());
                parametros.put("NombreS",nombreBoton);
                //parametros.put("NMail",edtMail.getText().toString());

                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void buscarProducto(String URL,final String nombrePlanta,final String nombreArea,final String SubArea,final String numeroS)
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
                        nombre=jsonObject.getString("5S");
                        nombre2=jsonObject.getString("Datos");

                        boton(nombre,i,nombrePlanta,nombreArea,SubArea,nombre2,numeroS);


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

    public void boton(final String nombreBoton, int numeroEmpresa,final String nombrePlanta,final String nombreArea ,final String nombreSubarea,final String Datos,final String numeroS)
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
                DeleteThis=nombreBoton;
                GNombreArea=nombreArea;
                GNombrePlanta=nombrePlanta;
                GNombreSubArea=nombreSubarea;
                OpenDialog();
              //  AuditoriaS(nombrePlanta,nombreArea,nombreSubarea,nombreBoton,Datos,numeroS);

            }
        });
    }
    public void Eliminar(final String nombrePlanta,final String nArea,final String nSubarea)
    {
        Intent intent =new Intent(this,AgregarS.class);
        intent.putExtra("EXTRA_SESSION_ID2", nArea);
        intent.putExtra("EXTRA_SESSION_ID", nombrePlanta);
        intent.putExtra("EXTRA_SESSION_ID3", nSubarea);
        startActivity(intent);
    }

    public void AuditoriaS(String nombrePlanta,String nArea,String nSubarea,String nombreBoton,String Datos,String numeroS)
    {
        Intent intent =new Intent(this,AuditoriaLlenado.class);
        intent.putExtra("EXTRA_SESSION_ID2", nArea);
        intent.putExtra("EXTRA_SESSION_ID", nombrePlanta);
        intent.putExtra("EXTRA_SESSION_ID3", nSubarea);
        intent.putExtra("EXTRA_SESSION_ID4", nombreBoton);
        intent.putExtra("EXTRA_SESSION_ID5", Datos);
        intent.putExtra("EXTRA_SESSION_ID6", numeroS);
        startActivity(intent);
    }
    public void formularioS(String nPlanta,String nArea,String nSubarea)
    {
        Intent intent =new Intent(this,FormularioS.class);
        intent.putExtra("EXTRA_SESSION_ID", nPlanta);
        intent.putExtra("EXTRA_SESSION_ID2", nArea);
        intent.putExtra("EXTRA_SESSION_ID3", nSubarea);

        startActivity(intent);
    }

    public void OpenDialog( )
    {

        ExampleDialog dialog = new ExampleDialog();
        dialog.show(getSupportFragmentManager(),"example dialog");

    }


    @Override
    public void onYesClicked() {

        ejecutarservicio("https://vvnorth.com/eliminar_s.php",DeleteThis);
        Eliminar(GNombrePlanta,GNombreArea,GNombreSubArea);

    }
}
