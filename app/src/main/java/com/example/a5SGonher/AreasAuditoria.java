package com.example.a5SGonher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
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

public class AreasAuditoria extends AppCompatActivity implements DialogOptions1.DialogOptions1Listener,DialogOptions7.DialogOptions1Listener{
    RequestQueue requestQueue;
    TextView tView,tView2,tView3;
    String boton6;
    String auditoriaNumeroSeis;
    String globalNombrePlanta;
    String globalNombreArea;
    String globanumeroAuditoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_areas_auditoria);
        final String nombrePlanta = getIntent().getStringExtra("EXTRA_SESSION_ID");
        final String nombreArea = getIntent().getStringExtra("EXTRA_SESSION_ID2");
        final String numeroAuditoria = getIntent().getStringExtra("EXTRA_SESSION_ID3");
       final String numeroInicio = getIntent().getStringExtra("INICIO");
        tView=(TextView)findViewById(R.id.textvSPlanta);
        //tView2=(TextView)findViewById(R.id.textvSArea);
        tView3=(TextView)findViewById(R.id.textvSubArea);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        globalNombrePlanta=nombrePlanta;
       globalNombreArea=nombreArea;
       globanumeroAuditoria=numeroAuditoria;
     int  myNum=  Integer.parseInt(numeroInicio);

       // tView2.setText(nombreArea);
        tView3.setText( "Número de Auditoría: " +numeroAuditoria);
        auditoriaNumeroSeis=numeroAuditoria;
        tabLayout.getTabAt(myNum).select();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                 String numeroS="0" ;
                if(tab.getPosition()==1) {

                    numeroS="1";
                    buscarProducto("https://vvnorth.com/buscar_auditoriaCustom.php?area="+nombreArea +"&planta="+ nombrePlanta+"&subarea="+ numeroAuditoria +"&subarea2="+numeroS + "" ,nombrePlanta,nombreArea,numeroAuditoria,numeroS);
                }

                if(tab.getPosition()==2) {
                    numeroS="2";
                    buscarProducto("https://vvnorth.com/buscar_auditoriaCustom.php?area="+nombreArea +"&planta="+ nombrePlanta+"&subarea="+ numeroAuditoria +"&subarea2="+numeroS + "" ,nombrePlanta,nombreArea,numeroAuditoria,numeroS);


                }

                if(tab.getPosition()==3) {
                    numeroS="3";
                    buscarProducto("https://vvnorth.com/buscar_auditoriaCustom.php?area="+nombreArea +"&planta="+ nombrePlanta+"&subarea="+ numeroAuditoria +"&subarea2="+numeroS + "" ,nombrePlanta,nombreArea,numeroAuditoria,numeroS);

                }

                if(tab.getPosition()==4) {
                    numeroS="4";
                    buscarProducto("https://vvnorth.com/buscar_auditoriaCustom.php?area="+nombreArea +"&planta="+ nombrePlanta+"&subarea="+ numeroAuditoria +"&subarea2="+numeroS + "" ,nombrePlanta,nombreArea,numeroAuditoria,numeroS);

                }
                if(tab.getPosition()==5) {

                    numeroS="5";
                    buscarProducto("https://vvnorth.com/buscar_auditoriaCustom.php?area="+nombreArea +"&planta="+ nombrePlanta+"&subarea="+ numeroAuditoria +"&subarea2="+numeroS + "" ,nombrePlanta,nombreArea,numeroAuditoria,numeroS);
                }

                if(tab.getPosition()==0) {

                    numeroS="6";
                    buscarProductoSeis("https://vvnorth.com/buscar_auditoriaCustom.php?area="+nombreArea +"&planta="+ nombrePlanta+"&subarea="+ numeroAuditoria +"&subarea2="+numeroS + "" ,nombrePlanta,nombreArea,numeroAuditoria,numeroS);
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

                if(myNum==0) { buscarProductoSeis("https://vvnorth.com/buscar_auditoriaCustom.php?area="+nombreArea +"&planta="+ nombrePlanta+"&subarea="+ numeroAuditoria +"&subarea2="+"6" + "" ,nombrePlanta,nombreArea,numeroAuditoria,"1"); }
        if(myNum==1) {   String numeroS="1" ;      buscarProducto("https://vvnorth.com/buscar_auditoriaCustom.php?area="+nombreArea +"&planta="+ nombrePlanta+"&subarea="+ numeroAuditoria +"&subarea2="+numeroS + "" ,nombrePlanta,nombreArea,numeroAuditoria,numeroS); }
        if(myNum==2) {   String numeroS="2" ;      buscarProducto("https://vvnorth.com/buscar_auditoriaCustom.php?area="+nombreArea +"&planta="+ nombrePlanta+"&subarea="+ numeroAuditoria +"&subarea2="+numeroS + "" ,nombrePlanta,nombreArea,numeroAuditoria,numeroS); }
        if(myNum==3) {   String numeroS="3" ;      buscarProducto("https://vvnorth.com/buscar_auditoriaCustom.php?area="+nombreArea +"&planta="+ nombrePlanta+"&subarea="+ numeroAuditoria +"&subarea2="+numeroS + "" ,nombrePlanta,nombreArea,numeroAuditoria,numeroS); }
        if(myNum==4) {   String numeroS="4" ;      buscarProducto("https://vvnorth.com/buscar_auditoriaCustom.php?area="+nombreArea +"&planta="+ nombrePlanta+"&subarea="+ numeroAuditoria +"&subarea2="+numeroS + "" ,nombrePlanta,nombreArea,numeroAuditoria,numeroS); }
        if(myNum==5) {   String numeroS="5" ;      buscarProducto("https://vvnorth.com/buscar_auditoriaCustom.php?area="+nombreArea +"&planta="+ nombrePlanta+"&subarea="+ numeroAuditoria +"&subarea2="+numeroS + "" ,nombrePlanta,nombreArea,numeroAuditoria,numeroS); }


        //  tView.setText("holaMundo");
        buscarProducto2("https://vvnorth.com/porcentaje_auditoria.php?numeroAuditoria="+numeroAuditoria +"&planta="+ nombrePlanta+"&subarea="+ numeroAuditoria +"&Estado="+"1" + "" );
    }

    public void openDialog()
    {
        String numeroAuditoria=globanumeroAuditoria;
        ejecutarservicioExcel("https://vvnorth.com/excel/excel1.php",numeroAuditoria);
        DialogOptions1 dialogOptions1 = new DialogOptions1();
        dialogOptions1.show(getSupportFragmentManager(),"example Dialog");

    }

    public void openDialog2()
    {
        DialogOptions7 dialogOptions7 = new DialogOptions7();
        dialogOptions7.show(getSupportFragmentManager(),"example Dialog2");
    }


    @Override
    public void onYesClicked() {
        Intent intent =new Intent(this,MainMenu.class);

        startActivity(intent);
    }
    @Override
    public void onNoClicked2() {

        ejecutarservicio("https://vvnorth.com/insertar_AudiFinal6.php","1");


    }

    @Override
    public void onYesClicked2() {

        ejecutarservicio("https://vvnorth.com/insertar_AudiFinal6.php","5");




    }

    private void ejecutarservicio(String URL,final String calificacion)
    {
        StringRequest stringRequest=new  StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
              //  Toast.makeText(getApplicationContext(), "OPERACION EXITOSA", Toast.LENGTH_SHORT).show();
                String numeroS="6" ;
                final LinearLayout ll3 = (LinearLayout)findViewById(R.id.layoutS);
                ll3.removeAllViews();

                buscarProductoSeis("https://vvnorth.com/buscar_auditoriaCustom.php?area="+globalNombreArea +"&planta="+ globalNombrePlanta+"&subarea="+ globanumeroAuditoria +"&subarea2="+numeroS + "" ,globalNombrePlanta,globalNombreArea,globanumeroAuditoria,numeroS);

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



                parametros.put("AuditoriaNumero",auditoriaNumeroSeis);///////////////esta si ocupamos ponerle cual es
                parametros.put("NombrePregunta",boton6);
                parametros.put("5s","6");

                parametros.put("NCalificacion",calificacion);








                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void buscarProducto2(String URL)
    {

        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        String nombre;
                        int valor;
                        jsonObject = response.getJSONObject(i);
                        // editT.setText(jsonObject.getString("Planta"));
                        nombre=jsonObject.getString("porcentaje");
                       // boton(nombre,i);

                        tView.setText("Avance: " + nombre + "%");
                        if(nombre.equals("100")) {
                           // Dialog3 dialog3 = new Dialog3();
                          //  dialog3.show(getSupportFragmentManager(), "example dialog");
                            openDialog();
                        }

                    } catch (JSONException e) {
                      //  Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              //  Toast.makeText(getApplicationContext(),"ERRO DE CONEXION",Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
    private void buscarProducto(String URL,final String nombrePlanta,final String nombreArea,final String numeroAuditoria,final String numeroS)
    {

        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        String nombre;
                        String nombre2;
                        String nombre3;
                        String nombre4;
                        jsonObject = response.getJSONObject(i);
                        // editT.setText(jsonObject.getString("Planta"));
                        nombre=jsonObject.getString("NombrePregunta");
                        nombre2=jsonObject.getString("Descripcion");
                        nombre3=jsonObject.getString("Estado");
                        nombre4=jsonObject.getString("SubArea");

                        boton(nombre,i,nombrePlanta,nombreArea,numeroAuditoria,nombre2,numeroS,nombre3,nombre4);


                    } catch (JSONException e) {
                          //  Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  Toast.makeText(getApplicationContext(),"ERRO DE CONEXION",Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void buscarProductoSeis(String URL,final String nombrePlanta,final String nombreArea,final String numeroAuditoria,final String numeroS)
    {

        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        String nombre;
                        String nombre2;
                        String nombre3;
                        String nombre4;
                        jsonObject = response.getJSONObject(i);
                        // editT.setText(jsonObject.getString("Planta"));
                        nombre=jsonObject.getString("NombrePregunta");
                        nombre2=jsonObject.getString("Comentario");
                        nombre3=jsonObject.getString("Estado");
                        nombre4=jsonObject.getString("SubArea");

                        botonSeis(nombre,i,nombrePlanta,nombreArea,numeroAuditoria,nombre2,numeroS,nombre3,nombre4);


                    } catch (JSONException e) {
                     //   Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  Toast.makeText(getApplicationContext(),"ERRO DE CONEXION",Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    public void botonSeis(final String nombreBoton, int numeroEmpresa,final String nombrePlanta,final String nombreArea ,final String numeroAuditoria,final String Datos,final String numeroS,final String estado,final String SubArea)
    {
        Button myButton3 = new Button(this);

        myButton3.setText(Datos);

        final LinearLayout ll3 = (LinearLayout)findViewById(R.id.layoutS);
        LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll3.addView(myButton3, lp3);


        //ll3.removeAllViews();

        /////////////////////////
        myButton3.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30);
        myButton3.setBackgroundColor(Color.rgb(128, 42, 42));
        myButton3.setTextColor(Color.rgb(179, 179, 179));
        if(estado.equals("1")){
            myButton3.setBackgroundColor(Color.rgb(0, 125, 0));
        }
        lp3.setMargins(0, 0, 0, 10);
        myButton3.setLayoutParams( lp3);
        /////////////////////////////////////////
        myButton3.setOnClickListener(new View.OnClickListener()  {
            // String NomPlanta =nFinal;
            public void onClick(View view) {

                boton6=nombreBoton;
                openDialog2();

            }
        });
    }

    public void boton(final String nombreBoton, int numeroEmpresa,final String nombrePlanta,final String nombreArea ,final String numeroAuditoria,final String Datos,final String numeroS,final String estado,final String SubArea)
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
        if(estado.equals("1")){
            myButton3.setBackgroundColor(Color.rgb(0, 125, 0));
    }
        lp3.setMargins(0, 0, 0, 10);
        myButton3.setLayoutParams( lp3);
        /////////////////////////////////////////
        myButton3.setOnClickListener(new View.OnClickListener()  {
            // String NomPlanta =nFinal;
            public void onClick(View view) {

                  AuditoriaS(nombrePlanta,nombreArea,numeroAuditoria,nombreBoton,Datos,numeroS,SubArea);

            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent =new Intent(this,AuAudit2.class);
        startActivity(intent);
    }

    public void AuditoriaS(String nombrePlanta,String nArea,String numeroAuditoria,String nombreBoton,String Datos,String numeroS,String Subarea)
    {
        Intent intent =new Intent(this,AuditoriaLlenado.class);
        intent.putExtra("EXTRA_SESSION_ID2", nArea);
        intent.putExtra("EXTRA_SESSION_ID", nombrePlanta);
        intent.putExtra("EXTRA_SESSION_ID3", numeroAuditoria);
        intent.putExtra("EXTRA_SESSION_ID4", nombreBoton);
         intent.putExtra("EXTRA_SESSION_ID5", Datos);
        intent.putExtra("EXTRA_SESSION_ID6", numeroS);
        intent.putExtra("EXTRA_SESSION_ID7", Subarea);
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

    private void ejecutarservicioExcel(String URL, final String numeroAuditoria)
    {
        StringRequest stringRequest=new  StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //     ejecutarservicio2("https://vvnorth.com/excel/AntesMail.php",GlobalNumeroAuditoria); DESCOMENTAR PARA QUE MANDE EL MAIL
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros =new HashMap<String,String>();
                parametros.put("NumeroAuditoria",numeroAuditoria);



                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
