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

public class AgregarS extends AppCompatActivity {
    RequestQueue requestQueue;
    TextView tView,tView2,tView3;
    String control;
   String nombrePlanta2;
    String nombreArea2;
String nombresubArea2;
    String DeleteThis;
    String GNombrePlanta;
    String GNombreArea;
    String GNombreSubArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_s);
        final String nombrePlanta = getIntent().getStringExtra("EXTRA_SESSION_ID");
        final String nombreArea = getIntent().getStringExtra("EXTRA_SESSION_ID2");
        final String nombresubArea = getIntent().getStringExtra("EXTRA_SESSION_ID3");
        nombrePlanta2=nombrePlanta;
         nombreArea2=nombreArea;
         nombresubArea2=nombresubArea;
        tView=(TextView)findViewById(R.id.textvSPlanta);
      //  tView2=(TextView)findViewById(R.id.textvSArea);
      //  tView3=(TextView)findViewById(R.id.textvSubArea);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs2);

        tView.setText(nombrePlanta+"/"+nombreArea+"/"+nombresubArea);
      //  tView2.setText(nombreArea);
      //  tView3.setText(nombresubArea);
        buscarProducto2("https://vvnorth.com/buscar_s.php?area="+nombreArea +"&planta="+ nombrePlanta+"&subarea="+ nombresubArea +"&subarea2="+1 + "" ,nombrePlanta,nombreArea,nombresubArea,"1");

        Config(nombrePlanta,nombreArea,nombresubArea);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String numeroS="0" ;
                if(tab.getPosition()==0) {

                    Config(nombrePlanta,nombreArea,nombresubArea);


                }

                if(tab.getPosition()==1) {

                    numeroS="1";

                    buscarProducto("https://vvnorth.com/buscar_s.php?area="+nombreArea +"&planta="+ nombrePlanta+"&subarea="+ nombresubArea +"&subarea2="+numeroS + "" ,nombrePlanta,nombreArea,nombresubArea,numeroS);

                    //  buscarProducto2("https://vvnorth.com/buscar_sCustom.php?planta="+ control +"&ss="+"1"+ ""  ,nombrePlanta,nombreArea,nombresubArea,"1");

                }

                if(tab.getPosition()==2) {

                    numeroS="2";
                    buscarProducto("https://vvnorth.com/buscar_s.php?area="+nombreArea +"&planta="+ nombrePlanta+"&subarea="+ nombresubArea +"&subarea2="+numeroS + "" ,nombrePlanta,nombreArea,nombresubArea,numeroS);

                    //    buscarProducto2("https://vvnorth.com/buscar_sCustom.php?planta="+ control +"&ss="+"2"+ ""  ,nombrePlanta,nombreArea,nombresubArea,"2");

                }

                if(tab.getPosition()==3) {

                    numeroS="3";
                    buscarProducto("https://vvnorth.com/buscar_s.php?area="+nombreArea +"&planta="+ nombrePlanta+"&subarea="+ nombresubArea +"&subarea2="+numeroS + "" ,nombrePlanta,nombreArea,nombresubArea,numeroS);

                    //   buscarProducto2("https://vvnorth.com/buscar_sCustom.php?planta="+ control +"&ss="+"3"+ ""  ,nombrePlanta,nombreArea,nombresubArea,"3");

                }


                if(tab.getPosition()==4) {

                    numeroS="4";
                    buscarProducto("https://vvnorth.com/buscar_s.php?area="+nombreArea +"&planta="+ nombrePlanta+"&subarea="+ nombresubArea +"&subarea2="+numeroS + "" ,nombrePlanta,nombreArea,nombresubArea,numeroS);


                }

                if(tab.getPosition()==5) {
                    numeroS="5";

                    buscarProducto("https://vvnorth.com/buscar_s.php?area="+nombreArea +"&planta="+ nombrePlanta+"&subarea="+ nombresubArea +"&subarea2="+numeroS + "" ,nombrePlanta,nombreArea,nombresubArea,numeroS);


                }



            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {


                    final LinearLayout ll3 = (LinearLayout)findViewById(R.id.layoutS);
                    ll3.removeAllViews();

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });




    }


    public void S1(final String nombrePlanta,final String nombreArea,final String nombresubArea)
    {
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Button myButton = new Button(this);
        myButton.setText("Pregunta1");

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
                ejecutarservicio("https://vvnorth.com/insertar_s.php",nombrePlanta,nombreArea,nombresubArea,"1","pregunta numero 1","Descripcion de la pregunta1");



            }
        });
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Button myButton2 = new Button(this);
        myButton2.setText("Pregunta2");

        LinearLayout ll2 = (LinearLayout)findViewById(R.id.layoutS);
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll2.addView(myButton2, lp2);
        /////////////////////////
        myButton2.setBackgroundColor(Color.rgb(128, 42, 42));
        myButton2.setTextColor(Color.rgb(179, 179, 179));
        lp2.setMargins(0, 0, 0, 10);
        myButton2.setLayoutParams( lp2);
        /////////////////////////////////////////
        myButton2.setOnClickListener(new View.OnClickListener() {


            public void onClick(View view) {
                ejecutarservicio("https://vvnorth.com/insertar_s.php",nombrePlanta,nombreArea,nombresubArea,"1","pregunta numero 2","Descripcion de la pregunta2");

            }
        });


        Button myButton3 = new Button(this);
        myButton3.setText("Pregunta3");

        LinearLayout ll3 = (LinearLayout)findViewById(R.id.layoutS);
        LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll3.addView(myButton3, lp3);
        /////////////////////////
        myButton3.setBackgroundColor(Color.rgb(128, 42, 42));
        myButton3.setTextColor(Color.rgb(179, 179, 179));
        lp3.setMargins(0, 0, 0, 10);
        myButton3.setLayoutParams( lp3);
        /////////////////////////////////////////
        myButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ejecutarservicio("https://vvnorth.com/insertar_s.php",nombrePlanta,nombreArea,nombresubArea,"1","pregunta numero 3","Descripcion de la pregunta 3");
            }
        });

        Button myButton4 = new Button(this);
        myButton4.setText("Pregunta4");

        LinearLayout ll4 = (LinearLayout)findViewById(R.id.layoutS);
        LinearLayout.LayoutParams lp4 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll4.addView(myButton4, lp4);
        /////////////////////////
        myButton4.setBackgroundColor(Color.rgb(128, 42, 42));
        myButton4.setTextColor(Color.rgb(179, 179, 179));
        lp4.setMargins(0, 0, 0, 10);
        myButton4.setLayoutParams( lp4);
        /////////////////////////////////////////
        myButton4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ejecutarservicio("https://vvnorth.com/insertar_s.php",nombrePlanta,nombreArea,nombresubArea,"1","pregunta numero 4","Descripcion de la pregunta 4");
            }
        });
        Button myButton5 = new Button(this);
        myButton5.setText("Pregunta5");

        LinearLayout ll5 = (LinearLayout)findViewById(R.id.layoutS);
        LinearLayout.LayoutParams lp5 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll5.addView(myButton5, lp5);
        /////////////////////////
        myButton5.setBackgroundColor(Color.rgb(128, 42, 42));
        myButton5.setTextColor(Color.rgb(179, 179, 179));
        lp5.setMargins(0, 0, 0, 10);
        myButton5.setLayoutParams( lp5);
        /////////////////////////////////////////
        myButton5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ejecutarservicio("https://vvnorth.com/insertar_s.php",nombrePlanta,nombreArea,nombresubArea,"1","pregunta numero 5","Descripcion de la pregunta 5");
            }
        });


    }

    public void Config(final String nombrePlanta,final String nombreArea,final String nombresubArea )
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

                formularioS(nombrePlanta,nombreArea,nombresubArea );

            }
        });
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Button myButton88 = new Button(this);
        myButton88.setText("Eliminar");

        LinearLayout ll88 = (LinearLayout)findViewById(R.id.layoutS);
        LinearLayout.LayoutParams lp88 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll88.addView(myButton88, lp88);
        /////////////////////////
        myButton88.setBackgroundColor(Color.rgb(128, 42, 42));
        myButton88.setTextColor(Color.rgb(179, 179, 179));
        lp88.setMargins(0, 0, 0, 10);
        myButton88.setLayoutParams( lp);
        /////////////////////////////////////////
        myButton88.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

               Eliminar(nombrePlanta,nombreArea,nombresubArea);

            }
        });
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Button myButton99 = new Button(this);
        myButton99.setText("Agregar Auditoria lista");

        LinearLayout ll99 = (LinearLayout)findViewById(R.id.layoutS);
        LinearLayout.LayoutParams lp99 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll88.addView(myButton99, lp99);
        /////////////////////////
        myButton99.setBackgroundColor(Color.rgb(128, 42, 42));
        myButton99.setTextColor(Color.rgb(179, 179, 179));
        lp99.setMargins(0, 0, 0, 10);
        myButton99.setLayoutParams( lp);
        /////////////////////////////////////////
        myButton99.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Cambiar(nombrePlanta,nombreArea,nombresubArea);

            }
        });
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Button myButton2 = new Button(this);
        myButton2.setText("Asignar Auditor");

        LinearLayout ll2 = (LinearLayout)findViewById(R.id.layoutS);
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll2.addView(myButton2, lp2);
        /////////////////////////
        myButton2.setBackgroundColor(Color.rgb(128, 42, 42));
        myButton2.setTextColor(Color.rgb(179, 179, 179));
        lp2.setMargins(0, 0, 0, 10);
        myButton2.setLayoutParams( lp2);
        /////////////////////////////////////////
        myButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {


                agregarS(nombrePlanta,nombreArea,nombresubArea);
                //   formularioS(nombrePlanta,nombreArea,nombresubArea );

            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Button myButton3 = new Button(this);
        myButton3.setText("Asignar Responsables");

        LinearLayout ll3 = (LinearLayout)findViewById(R.id.layoutS);
        LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll3.addView(myButton3, lp3);
        /////////////////////////
        myButton3.setBackgroundColor(Color.rgb(128, 42, 42));
        myButton3.setTextColor(Color.rgb(179, 179, 179));
        lp3.setMargins(0, 0, 0, 10);
        myButton3.setLayoutParams( lp3);
        /////////////////////////////////////////
        myButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {


                agregarS2(nombrePlanta,nombreArea,nombresubArea);
                //   formularioS(nombrePlanta,nombreArea,nombresubArea );

            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Button myButton4 = new Button(this);
        myButton4.setText("Terminar");

        LinearLayout ll4 = (LinearLayout)findViewById(R.id.layoutS);
        LinearLayout.LayoutParams lp4 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll4.addView(myButton4, lp4);
        /////////////////////////
        myButton4.setBackgroundColor(Color.rgb(128, 42, 42));
        myButton4.setTextColor(Color.rgb(179, 179, 179));
        lp3.setMargins(0, 0, 0, 10);
        myButton4.setLayoutParams( lp4);
        /////////////////////////////////////////
        myButton4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {


                agregarS3();
                //   formularioS(nombrePlanta,nombreArea,nombresubArea );

            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

       // buscarProducto("http://192.168.1.101:8080/Ghoner/buscar_sOriginal.php?area="+nombreArea +"&planta="+ nombrePlanta+"&subarea="+ nombresubArea + "" ,nombrePlanta,nombreArea,nombresubArea);
       // buscarProducto("https://vvnorth.com/buscar_sOriginal.php" ,nombrePlanta,nombreArea,nombresubArea);

    }

    public void onBackPressed() {
        // super.onBackPressed();
        Intent intent =new Intent(this,AgregarSubarea.class);
        intent.putExtra("EXTRA_SESSION_ID2", nombreArea2);
        intent.putExtra("EXTRA_SESSION_ID", nombrePlanta2);
        intent.putExtra("EXTRA_SESSION_ID3", nombresubArea2);
        startActivity(intent);

    }

    public void Eliminar(final String nombrePlanta,final String nArea,final String nSubarea)
    {
        Intent intent =new Intent(this,EliminarS.class);
        intent.putExtra("EXTRA_SESSION_ID2", nArea);
        intent.putExtra("EXTRA_SESSION_ID", nombrePlanta);
        intent.putExtra("EXTRA_SESSION_ID3", nSubarea);
        startActivity(intent);
    }

    public void Cambiar(final String nombrePlanta,final String nArea,final String nSubarea)
    {
        Intent intent =new Intent(this,AgregarSAuditoriaL.class);
        intent.putExtra("EXTRA_SESSION_ID2", nArea);
        intent.putExtra("EXTRA_SESSION_ID", nombrePlanta);
        intent.putExtra("EXTRA_SESSION_ID3", nSubarea);
        startActivity(intent);
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

    private void buscarProducto2(String URL,final String nombrePlanta,final String nombreArea,final String SubArea,final String nS)
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
                        nombre=jsonObject.getString("DescripcionS");

                        boton2(nombre2,nombrePlanta,nombreArea,SubArea,nS,nombre);


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

    public void boton(final String nombreBoton, int numeroEmpresa,final String nombrePlanta,final String nombreArea ,final String nombreSubarea,final String Datos,final String numeroS) {
        Button myButton3 = new Button(this);

        myButton3.setText(nombreBoton);

        final LinearLayout ll3 = (LinearLayout) findViewById(R.id.layoutS);
        LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll3.addView(myButton3, lp3);


        //ll3.removeAllViews();

        /////////////////////////
        myButton3.setBackgroundColor(Color.rgb(128, 42, 42));
        myButton3.setTextColor(Color.rgb(179, 179, 179));
        lp3.setMargins(0, 0, 0, 10);
        myButton3.setLayoutParams(lp3);
        /////////////////////////////////////////
        myButton3.setOnClickListener(new View.OnClickListener() {
            // String NomPlanta =nFinal;
            public void onClick(View view) {
                  DeleteThis=nombreBoton;
                  GNombreArea=nombreArea;
                  GNombrePlanta=nombrePlanta;
                  GNombreSubArea=nombreSubarea;

                  AuditoriaS(nombrePlanta,nombreArea,nombreSubarea,nombreBoton,Datos,numeroS);

            }
        });
    }
    public void AuditoriaS(String nombrePlanta,String nArea,String nSubarea,String nombreBoton,String Datos,String numeroS)
    {
        Intent intent =new Intent(this,CambiarS.class);
        intent.putExtra("EXTRA_SESSION_ID2", nArea);
        intent.putExtra("EXTRA_SESSION_ID", nombrePlanta);
        intent.putExtra("EXTRA_SESSION_ID3", nSubarea);
        intent.putExtra("EXTRA_SESSION_ID4", nombreBoton);
        intent.putExtra("EXTRA_SESSION_ID5", Datos);
        intent.putExtra("EXTRA_SESSION_ID6", numeroS);
        startActivity(intent);
    }

    public void boton2(final String nombreBoton,final String nombrePlanta,final String nombreArea,final String nombresubArea,final String nS,final String nD)
    {
        Button myButton3 = new Button(this);

        myButton3.setText(nombreBoton);

        final LinearLayout ll3 = (LinearLayout)findViewById(R.id.layoutS);
        LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll3.addView(myButton3, lp3);

        /////////////////////////
        myButton3.setBackgroundColor(Color.rgb(128, 42, 42));
        myButton3.setTextColor(Color.rgb(179, 179, 179));
        lp3.setMargins(0, 0, 0, 10);
        myButton3.setLayoutParams( lp3);
        /////////////////////////////////////////
        myButton3.setOnClickListener(new View.OnClickListener()  {
            // String NomPlanta =nFinal;
            public void onClick(View view) {

                ejecutarservicio("https://vvnorth.com/insertar_s.php",nombrePlanta,nombreArea,nombresubArea,nS,nombreBoton,nD);

            }
        });
    }

    public void agregarS(String nombrePlanta,String nArea,String nSubarea)
    {
        Intent intent =new Intent(this,SelectAuditors.class);
        intent.putExtra("EXTRA_SESSION_ID2", nArea);
        intent.putExtra("EXTRA_SESSION_ID", nombrePlanta);
        intent.putExtra("EXTRA_SESSION_ID3", nSubarea);
       // intent.putExtra("EXTRA_SESSION_ID4", nS);
        startActivity(intent);
    }

    public void agregarS2(String nombrePlanta,String nArea,String nSubarea)
    {
        Intent intent =new Intent(this,SelectResponsable.class);
        intent.putExtra("EXTRA_SESSION_ID2", nArea);
        intent.putExtra("EXTRA_SESSION_ID", nombrePlanta);
        intent.putExtra("EXTRA_SESSION_ID3", nSubarea);
        // intent.putExtra("EXTRA_SESSION_ID4", nS);
        startActivity(intent);
    }
    public void agregarS3()
    {
        Intent intent =new Intent(this,MainMenu.class);

        // intent.putExtra("EXTRA_SESSION_ID4", nS);
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
