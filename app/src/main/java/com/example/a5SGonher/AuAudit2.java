package com.example.a5SGonher;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.lang.reflect.Array;
import java.net.URL;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class AuAudit2 extends AppCompatActivity {
    RequestQueue requestQueue;
    EditText editT;
    // Button myButton3 = new Button(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_au_audit2);
        // editT=(EditText)findViewById(R.id.editT);
        SharedPreferences preferences=getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        String user= preferences.getString("User","No existe Usuario");
        String Rol= preferences.getString("Rol","No existe Usuario");

        TextView titulobar = (TextView)findViewById(R.id.titulo_toolbar);
        titulobar.setText("Auditar");
        //Toast.makeText(getApplicationContext(),"User: "+user,Toast.LENGTH_SHORT).show();
        //user es el numero de nomina
        toastPersonalizado();
        buscarProducto("https://vvnorth.com/buscar_auditoriasN.php?Auditor=" + user + "",user);

        //////////////////////////////
    }

    private void buscarProducto(String URL, final String user)
    {
        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                Log.i("tamaño","Arreglo Auditorias:"+response.length());
                int tamanio =response.length();
                String[] arreglo = new String[tamanio];
                for (int i = 0; i < response.length(); i++) {
                    try {
                        String nombreSubarea;
                        String nombreAuditor="nada";
                        String codigoAuditoria="nada";
                        String nombreSubarea2="nada";
                        String nombreArea="nada";
                        String nombrePlanta="nada";
                        jsonObject = response.getJSONObject(i);
                        // editT.setText(jsonObject.getString("Planta"));
                        codigoAuditoria=jsonObject.getString("CodigoAuditoria");
                        nombreSubarea=jsonObject.getString("SubArea");

                        arreglo[i] = codigoAuditoria;
                        if (i==tamanio-1){
                            for (int j =0; j < arreglo.length;j++){
                                Log.i("Sacar porcentaje",":  "+arreglo[j]);
                               procesarPorcentaje("https://vvnorth.com/porcentaje.php?numeroAuditoria="+arreglo[j],arreglo,tamanio);
                            }
                        }
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),"No hay auditorías para mostrar",Toast.LENGTH_SHORT).show();

            }
        }
        );
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }



    public void toastPersonalizado(){
        View viewToast = getLayoutInflater().inflate(R.layout.toast_personalizado,(ViewGroup)findViewById(R.id.layout_toast));
        Toast toast = new Toast(this);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(viewToast);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

    }
    private void ejecutarservicio(String URL, final String nombreSubarea,final String user)
    {
        StringRequest stringRequest=new  StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

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
                parametros.put("NombrePlanta",nombreSubarea);
                parametros.put("Usuario",user);

                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public void onBackPressed() {
        Intent intent =new Intent(this,MainMenu.class);
        startActivity(intent);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void boton(final  String auditoria[],String nombreSubArea2)
    {
        for (int i=0; i < auditoria.length;i++){

                Button myButton3 = new Button(this);

                myButton3.setText(auditoria[i]);
                int porcentaje=100;




                            LinearLayout ll3 = (LinearLayout)findViewById(R.id.layoutSPlanta);
                            LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            ll3.addView(myButton3, lp3);



                            /////////////////////////
                            myButton3.setBackgroundColor(Color.rgb(128, 42, 42));
                                if(auditoria[i].indexOf("100%")!=-1){
                                    myButton3.setBackgroundResource(R.drawable.boton_completado);
                                  }else{
                                    myButton3.setBackgroundResource(R.drawable.boton_nocompletado);
                                }

            int cantidad =auditoria[i].indexOf(" ");
            final String codigoAuditoria= auditoria[i].substring(0, cantidad);//obtengo el codigo de auditoria

                            Typeface fuenteitem = getResources().getFont(R.font.mitre);
                            myButton3.setTypeface(fuenteitem);
                            myButton3.setTextSize(11);
                            //myButton3.setBackgroundColor(Color.rgb(150, 0, 0));
                            myButton3.setTextColor(Color.rgb(220, 220, 220));
                            lp3.setMargins(0, 0, 0, 10);
                            myButton3.setLayoutParams( lp3);
                            /////////////////////////////////////////
                            myButton3.setOnClickListener(new View.OnClickListener()  {
                                // String NomPlanta =nFinal;
                                public void onClick(View view) {
                            //Log.i("Presionando en Btn","user"+numeroAuditoria);
                              //      ejecutarservicio("https://vvnorth.com/CrearAuditoria.php",user,user);       SI ALGO SALE MAL PUEDE SER PORQUE COMENTE ESTO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                                    agregarArea(codigoAuditoria);

                    }
                });
        }
    }

    public void formularioPlanta()
    {
        Intent intent =new Intent(this,FormularioUsuario.class);
        startActivity(intent);
    }


    public void procesarPorcentaje(String URL1, final String arreglo[], final int tamanio){
        JsonArrayRequest respuesta = new JsonArrayRequest(Request.Method.GET, URL1, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONArray response) {
                try {

                    JSONObject datos = response.getJSONObject(0);
                    String codigo = datos.getString("codigoAuditoria");
                    String subArea = datos.getString("subArea");
                    String porcentaje = datos.getString("porcentaje");
                    String fecha = datos.getString("fecha");
                    System.out.println("nombre de la subarea"+subArea);
                    String subAreas=subArea;
                    int r =1;
                    int s =1;
                   // System.out.println("tamanio"+tamanio);
                    for (int i = 0; i < arreglo.length; i++){// utilizo para organizar los botones llegan desordenados y aqui los reacomodo
                        //Log.i("Codigo",""+codigo+"=="+arreglo[i]);
                        if (arreglo[i].equals(codigo)) {
                            arreglo[i] =codigo+"    "+subArea+"    "+fecha+"    "+porcentaje+"%";
                            for (int j=0; j < arreglo.length; j++){// ahora ya que esta en orden verificare que todas las posiciones tengas % para crear el boton
                                if (arreglo[j].indexOf("%") !=-1 ){
                                    r++;

                                    if (r==(tamanio+1)){// si todas la posiones ya tienes % crea el nuevo arreglo correcto.

                                        String nuevoArray[]= new String[tamanio];
                                        for(int k=0; k < nuevoArray.length;k++){
                                            s++;
                                            nuevoArray[k] = arreglo[k];
                                            System.out.println("s="+s+"tamanio ="+(tamanio));
                                            if (s==tamanio+1){ //una vez creado mandalo para generar los botones
                                                System.out.println(Arrays.toString(nuevoArray));
                                                boton(nuevoArray,subArea);
                                            }
                                        }
                                    }
                                }else{
                                    //System.out.println("NO hay en"+j+" %");
                                }
                            }
                          //
                          }


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


                // Log.i("procesados","arreglo"+arreglo[0]+"arreglo"+arreglo[1]);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(respuesta);
    }



//pasando datos a la actividad NuevaAuditoria.
    public void agregarArea(String numeroAuditoria)
    {
        //String nombrePlanta = "nada";
        //String nombreArea = "nada";
        //String nombreAuditor= "nada";
        Intent intent =new Intent(this,NuevaAuditoria.class);
        //intent.putExtra("EXTRA_SESSION_ID", nombrePlanta);// Comentado por Genaro y datos recibido elimados tenian canda Nada
        //intent.putExtra("EXTRA_SESSION_ID2", nombreArea);// Comentado por Genaro y datos recibido elimados tenian canda Nada
        intent.putExtra("EXTRA_SESSION_ID3", numeroAuditoria);
        //intent.putExtra("EXTRA_SESSION_ID4", nombreAuditor); //Comentado por Genaro y datos recibido elimados tenian canda Nada
        intent.putExtra("EXTRA_SESSION_ID5", "1");
        intent.putExtra("INICIO", "0");
        startActivity(intent);
    }


}