package com.example.a5SGonher.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.a5SGonher.DialogOptions3;
import com.example.a5SGonher.FormularioUsuario;
import com.example.a5SGonher.MainMenu;
import com.example.a5SGonher.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class    ListosEnviar extends AppCompatActivity implements DialogOptions3.DialogOptions1Listener{
    RequestQueue requestQueue;
    EditText editT;
    String Planta;
    String numeroAuditoria;
    String GlobalSubarea;
    String GlobalUser;
    String GlobalNumeroAuditoria;
    String arraySubareas[]= new String[10000];

    // Button myButton3 = new Button(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listos_enviar);


        // editT=(EditText)findViewById(R.id.editT);
        TextView titulo_tool = (TextView)findViewById(R.id.titulo_toolbar);
        titulo_tool.setText("Historial");
        SharedPreferences preferences=getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        String user= preferences.getString("User","No existe Usuario");
        String Rol= preferences.getString("Rol","No existe Usuario");
        Planta = preferences.getString("Planta","No Existe Planta");
        GlobalUser=user;
        toastPersonalizado();
        buscarProducto("https://vvnorth.com/buscar_auditoriasN.php?Auditor=" + user + "&Planta="+Planta,user);
        //////////////////////////////
    }

    public void toastPersonalizado(){
        View viewToast = getLayoutInflater().inflate(R.layout.toast_personalizado,(ViewGroup)findViewById(R.id.layout_toast));
        Toast toast = new Toast(this);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(viewToast);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private void ejecutarservicio(String URL,final String numeroAudi)
    {
        StringRequest stringRequest=new  StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
              ejecutarservicio2("https://vvnorth.com/excel/mailBien.php",GlobalNumeroAuditoria);
            //  ejecutarservicio2("https://vvnorth.com/excel/mailBienAuditor.php",GlobalNumeroAuditoria);
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
                parametros.put("NumeroAuditoria",numeroAudi);
                parametros.put("Planta",Planta);
                GlobalNumeroAuditoria=numeroAudi;


                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void ejecutarservicio2(String URL, final String numeroAudi)
    {
        StringRequest stringRequest=new  StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //  buscarProducto("https://vvnorth.com/comparacion_auditorf.php",NPlanta);    CREO QUE NO SIRVE
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
                parametros.put("SubArea",GlobalSubarea);
                parametros.put("User",GlobalUser);
                parametros.put("NumeroAuditoria",numeroAudi);
                parametros.put("Planta",Planta);

                //   parametros.put("NombrePlanta",NPlanta);

                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void openDialog()
    {
        DialogOptions3 dialogOptions3 = new DialogOptions3();
        dialogOptions3.show(getSupportFragmentManager(),"example Dialog2");
    }

    @Override
    public void onYesClicked() {

        ejecutarservicio("https://vvnorth.com/excel/excel2.php",numeroAuditoria);
       // agregarArea();

    }

    @Override
    public void onNoClicked() {
    }
    public void onBackPressed() {
        Intent intent =new Intent(this, MainMenu.class);
        startActivity(intent);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void boton(final  String auditoria[])
    {


        for (int i=0; i < auditoria.length;i++) {
            Button myButton3 = new Button(this);


            if(auditoria[i].indexOf("100%")!=-1){
                myButton3.setText(auditoria[i]);
                //System.out.println("subareas:"+arraySubareas[i]);
                LinearLayout ll3 = (LinearLayout) findViewById(R.id.layoutSPlanta);
                LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                ll3.addView(myButton3, lp3);
                Typeface fuenteitem = getResources().getFont(R.font.mitre);
                myButton3.setTypeface(fuenteitem);
                myButton3.setTextSize(11);
                myButton3.setTextColor(Color.rgb(220, 220, 220));
                myButton3.setBackgroundResource(R.drawable.boton_completado);
                lp3.setMargins(0, 0, 0, 10);
                myButton3.setLayoutParams(lp3);

                int cantidad =auditoria[i].indexOf(" ");
                final String codigoAud= auditoria[i].substring(0, cantidad);//obtengo el codigo de auditoria
                final String nombreSub= arraySubareas[i];
                myButton3.setOnClickListener(new View.OnClickListener() {
                    // String NomPlanta =nFinal;
                    public void onClick(View view) {
                        numeroAuditoria = codigoAud;//numero de auditori que llegue
                        GlobalSubarea = nombreSub; //verificar al pareces es la subarea
                        //      ejecutarservicio("https://vvnorth.com/CrearAuditoria.php",user,user);       SI ALGO SALE MAL PUEDE SER PORQUE COMENTE ESTO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                        //   agregarArea(nombrePlanta, nombreArea, user, nombreAuditor);
                        confirmar_reporte();
                       // openDialog();
                    }
                });
            }
        }
    }

    public void confirmar_reporte(){
        TextView btnAceptar = (TextView)findViewById(R.id.btn_aceptar);
        TextView btnCancelar = (TextView)findViewById(R.id.btn_cancelar);
        final View include_confirmar_excel = findViewById(R.id.id_layout_confirmar_excel);
        include_confirmar_excel.setVisibility(View.VISIBLE);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                include_confirmar_excel.setVisibility(View.GONE);
            }
        });

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                include_confirmar_excel.setVisibility(View.GONE);
                ejecutarservicio("https://vvnorth.com/excel/excel2.php",numeroAuditoria);
            }
        });


    }

    public void formularioPlanta()
    {
        Intent intent =new Intent(this, FormularioUsuario.class);
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
                    //System.out.println("nombre de la subarea"+subArea);

                    int r =1;
                    int s =1;
                    int d=1;
                    // System.out.println("tamanio"+tamanio);
                    for (int i = 0; i < arreglo.length; i++){// utilizo para organizar los botones llegan desordenados y aqui los reacomodo
                        //Log.i("Codigo",""+codigo+"=="+arreglo[i]);


                        if (arreglo[i].equals(codigo)) {

                            arreglo[i] =codigo+"    "+subArea+"    "+fecha+"    "+porcentaje+"%";
                            arraySubareas[i]=subArea;
                            for (int j=0; j < arreglo.length; j++){// ahora ya que esta en orden verificare que todas las posiciones tengas % para crear el boton
                                if (arreglo[j].indexOf("%") !=-1 ){
                                    r++;
                                    if (r==(tamanio+1)){// si todas la posiones ya tienes % crea el nuevo arreglo correcto.
                                        String nuevoArray[]= new String[tamanio];
                                        for(int k=0; k < nuevoArray.length;k++){
                                            s++;
                                            nuevoArray[k] = arreglo[k];
                                            //System.out.println("s="+s+"tamanio ="+(tamanio));
                                            if (s==tamanio+1){ //una vez creado mandalo para generar los botones
                                                //System.out.println(Arrays.toString(arregloSubareas)+"ARREGLO SUB");
                                                boton(nuevoArray);
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

    private void buscarProducto3(String URL)
    {

        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        String calificacion;
                        String nombreAuditor="nada";
                        //  String nombreSubarea="nada";
                        String nombreArea="nada";
                        String nombrePlanta="nada";
                        int valor;
                        jsonObject = response.getJSONObject(i);
                        // editT.setText(jsonObject.getString("Planta"));
                        calificacion=jsonObject.getString("porcentaje");

                       // boton(nombrePlanta,nombreArea,nombre,nombreAuditor,user,nombreSubarea);


                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
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

    public void agregarArea()
    {
        Intent intent =new Intent(this, MainMenu.class);

        startActivity(intent);
    }

    private void buscarProducto(String URL, final String user)
    {

        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                int tamanio =response.length();
                String[] arreglo = new String[tamanio];
                String[] arreglosub = new String[tamanio];
                for (int i = 0; i < response.length(); i++) {
                    try {
                        String nombreAuditor="nada";
                        String nombreSubarea="nada";
                        String subarea="nadita";
                        String codigoAuditoria="nada";
                        String nombreArea="nada";
                        String nombrePlanta="nada";
                        jsonObject = response.getJSONObject(i);
                        // editT.setText(jsonObject.getString("Planta"));
                        codigoAuditoria=jsonObject.getString("CodigoAuditoria");
                        subarea=jsonObject.getString("SubArea");
                        //System.out.println("auditori"+nombreSubarea);

                        arreglo[i] = codigoAuditoria;
                        if (i==tamanio-1){
                            for (int j =0; j < arreglo.length;j++){
                               // Log.i("Sacar porcentaje",":  "+arreglo[j]);
                                procesarPorcentaje("https://vvnorth.com/porcentaje.php?numeroAuditoria="+arreglo[j]+"&Planta="+Planta,arreglo,tamanio);
                            }
                        }
                        // buscarProducto2("https://vvnorth.com/porcentaje_auditoria.php?numeroAuditoria="+nombreSubarea +"&planta="+ nombreSubarea+"&subarea="+ nombreSubarea +"&Estado="+"1" + "",nombreSubarea,nombreSubarea2);



                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"ERRO DE CONEXION",Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
}