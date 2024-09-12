package com.example.a5SGonher;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
                            String nombreAuditor = jsonObjectHallazgos.getString("Responsable");
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
       layoutHijo.setBackgroundResource(R.drawable.linea_titularsession);
       TextView textDatos = new TextView(this);
       ImageView imagen = new ImageView(this);


       LinearLayout.LayoutParams paramsText = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 2f);
       LinearLayout.LayoutParams paramsImage = new LinearLayout.LayoutParams(400, 600, 1f);
       paramsImage.gravity = Gravity.CENTER_VERTICAL;
       textDatos.setLayoutParams(paramsText);
       imagen.setLayoutParams(paramsImage);
       String urlImagen = "https://vvnorth.com/5sGhoner/FotosRecorridos/"+nominaAuditor+"/"+idRecorrido+"/"+idHallazgo+".jpeg";
       Picasso.get().load(urlImagen).into(imagen);

       String informacion = "<font color='#863828'>Codigo Auditoria:<br></font>"+codigoAuditoria+
                            "<br><font color='#863828'>Auditor:<br></font>"+nombreAuditor+
                            "<br><font color='#863828'>ID Hallazgo:<br></font>"+idHallazgo+
                            "<br><font color='#863828'>Hallazgo:<br></font>"+hallazgo+
                            "<br><font color='#863828'>Fecha Hallazgo:<br></font>"+fechaHallazgo+
                            "<br><font color='#863828'>Estatus:<br></font>"+statusHallazgo;

       textDatos.setText(Html.fromHtml(informacion));
       layoutHijo.setOrientation(LinearLayout.HORIZONTAL);
       layoutHijo.addView(textDatos);
       layoutHijo.addView(imagen);
       layoutPadre.addView(layoutHijo);
    }
}