package com.example.a5SGonher;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class ResponsableListaHallazgos extends AppCompatActivity {
    RequestQueue requestQueue;
    TextView titular_session;
    String Planta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_responsable_lista_hallazgos);
        TextView titulo = (TextView)findViewById(R.id.titulo_toolbar);
        titulo.setText("Hallazgos Abiertos");
        //Recupero Informacion
        SharedPreferences preferences=getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String user= preferences.getString("User","No existe Usuario");
        String Rol= preferences.getString("Rol","No existe Usuario");
        String nombreAuditor= preferences.getString("NombreAuditor","No existe Usuario");
        Planta= preferences.getString("Planta","No existe Planta");
        String numeroNomina= preferences.getString("NumeroNomina","Sin Número de Nómina");
        titular_session=(TextView)findViewById(R.id.textViewSession);
        titular_session.setText("Responsable:  "+nombreAuditor+" ("+numeroNomina+") "+Planta);

        final String tipoHallazgo = getIntent().getStringExtra("tipoHallazgo");
        buscarHallazgos("https://vvnorth.com/5sGhoner/consultarHallazgosResponsable.php",numeroNomina,tipoHallazgo);

    }

    private void buscarHallazgos(String URL, final String numeroNomina, final String tipoHallazgo)
    {
        StringRequest jsonArrayRequest= new StringRequest(Request.Method.POST,URL, new Response.Listener<String>() {

            public void onResponse(String response) {
                Log.e("Consultado Hallazgos","Responsable: "+response);
                try {
                    JSONArray arrayRespuesta = new JSONArray(response);
                    if(arrayRespuesta.length()>0){
                        for (int i = 0; i < arrayRespuesta.length(); i++) {
                            JSONObject jsonObjectHallazgos = arrayRespuesta.getJSONObject(i);
                            String nominaAuditor = jsonObjectHallazgos.getString("auditor");
                            String id_recorrido = jsonObjectHallazgos.getString("id_recorrido");
                            String codigoAuditoria = jsonObjectHallazgos.getString("codigo");
                            String nombreAuditor = jsonObjectHallazgos.getString("NombreAuditor");
                            String idHallazgo = jsonObjectHallazgos.getString("id_hallazgo");
                            String hallazgo = jsonObjectHallazgos.getString("hallazgo");
                            String fechaHallazgo = jsonObjectHallazgos.getString("fecha_hallazgo");
                            String statusHallazgo = jsonObjectHallazgos.getString("estatus");


                            espacioHallazgo(nominaAuditor,id_recorrido,codigoAuditoria,nombreAuditor,idHallazgo,hallazgo,fechaHallazgo,statusHallazgo);
                        }

                    }else{
                        Toast.makeText(getApplicationContext(),"Sin Hallazgos",Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"ERROR DE CONEXION",Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("NumeroNomina", numeroNomina);
                params.put("planta", Planta);
                params.put("tipoHallazgo", tipoHallazgo);
                return params;
            }
        };
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

   public void espacioHallazgo(String nominaAuditor,String idRecorrido,String codigoAuditoria,String nombreAuditor,String idHallazgo,String hallazgo, String fechaHallazgo,String statusHallazgo){
       LinearLayout layoutPadre = (LinearLayout)findViewById(R.id.layoutHallazgos);
       LinearLayout layoutHijo = new LinearLayout(this);
       LinearLayout layoutHijo2 = new LinearLayout(this);
       layoutHijo2.setBackgroundResource(R.drawable.linea_titularsession);
       TextView textDatos = new TextView(this);
       TextView textStatus = new TextView(this);
       ImageView imagen = new ImageView(this);
       Button boton = new Button(this);

       boton.setText("Subir Evidencia");
       // Create a Spinner for statusHallazgo
       Spinner spinnerStatus = new Spinner(this);
       ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item) {
           @Override
           public View getView(int position, View convertView, ViewGroup parent) {
               View view = super.getView(position, convertView, parent);
               TextView textView = (TextView) view;
               textView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
               textView.setGravity(Gravity.CENTER_HORIZONTAL); //texto
               return view;
           }

           @Override
           public View getDropDownView(int position, View convertView, ViewGroup parent) {
               View view = super.getDropDownView(position, convertView, parent);
               TextView textView = (TextView) view;
               textView.setTextColor(Color.BLUE); // color
               textView.setGravity(Gravity.CENTER_HORIZONTAL); // horientacion
               return view;
           }
       };

       for (int i = 0; i <= 100; i += 10) {
           adapter.add(String.valueOf(i));
       }
       adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       spinnerStatus.setAdapter(adapter);


       // Establesco el valor inicial statusHallazgo
       int selectedIndex = adapter.getPosition(statusHallazgo);
       spinnerStatus.setSelection(selectedIndex);

       LinearLayout.LayoutParams paramsText = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 2f);
       LinearLayout.LayoutParams paramsImage = new LinearLayout.LayoutParams(400, 600, 1f);
       paramsImage.gravity = Gravity.CENTER_VERTICAL;
       textDatos.setLayoutParams(paramsText);
       imagen.setLayoutParams(paramsImage);



       LinearLayout.LayoutParams paramsTextStatus = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT,1f);
       paramsTextStatus.gravity = Gravity.CENTER_VERTICAL;
       textStatus.setGravity(Gravity.END);
       paramsTextStatus.setMargins(0,20,0,0);
       textStatus.setLayoutParams(paramsTextStatus);


       LinearLayout.LayoutParams paramsSpinner = new LinearLayout.LayoutParams(0,100,1f);
       paramsSpinner.gravity = Gravity.CENTER_VERTICAL;
       paramsSpinner.setMargins(0,20,100,0);
       spinnerStatus.setBackgroundResource(R.drawable.formulario_confirmacion);
       spinnerStatus.setLayoutParams(paramsSpinner);


       LinearLayout.LayoutParams paramsButton = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT,2f);
       paramsButton.setMargins(0,50,100,20);
       boton.setLayoutParams(paramsButton);
       boton.setBackgroundResource(R.drawable.boton_crear);
       boton.setTextColor(Color.WHITE);

       String urlImagen = "https://vvnorth.com/5sGhoner/FotosRecorridos/"+nominaAuditor+"/"+idRecorrido+"/"+idHallazgo+".jpeg";
       Picasso.get().load(urlImagen).into(imagen);

       String informacion = "<font color='#863828'>Codigo Auditoria:<br></font>"+codigoAuditoria+
                            "<br><font color='#863828'>Auditor:<br></font>"+nombreAuditor+
                            "<br><font color='#863828'>ID Hallazgo:<br></font>"+idHallazgo+
                            "<br><font color='#863828'>Hallazgo:<br></font><b>"+hallazgo+"</b>"+
                            "<br><font color='#863828'>Fecha Hallazgo:<br></font>"+fechaHallazgo;
       textDatos.setText(Html.fromHtml(informacion));

       String status = "<br><font color='#863828'>Estatus:<br></font>";
       textStatus.setText(Html.fromHtml(status));

       layoutHijo.setOrientation(LinearLayout.HORIZONTAL);
       layoutHijo.addView(textDatos);
       layoutHijo.addView(imagen);
       layoutHijo2.addView(textStatus);
       layoutHijo2.addView(spinnerStatus);
       layoutHijo2.addView(boton);
       layoutPadre.addView(layoutHijo);
       layoutPadre.addView(layoutHijo2);
    }
}