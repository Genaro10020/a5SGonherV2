package com.example.a5SGonher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.LinearLayout;
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HallazgosRecorridos extends AppCompatActivity {
    String User,NumeroNomina,Planta;
    Button nuevoHallazgo;
    String ID_recorrido,creadoPor,Codigo,ServerName;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hallazgos_recorridos);
        GlobalClass globalClass =(GlobalClass)getApplicationContext();
        ServerName=globalClass.getName();
        TextView titulo_toolbar = (TextView)findViewById(R.id.titulo_toolbar);
        nuevoHallazgo = (Button)findViewById(R.id.btnNuevoHallazgo);
        SharedPreferences preferences=getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        Planta = preferences.getString("Planta","No existe Número Nómina");
        NumeroNomina = preferences.getString("NumeroNomina","No existe Número Nómina");
        titulo_toolbar.setText("Hallazgos Recorrido");
        Intent intent = getIntent();

        ID_recorrido = intent.getStringExtra("ID_recorrido");
        Codigo = intent.getStringExtra("Codigo");
        creadoPor = intent.getStringExtra("creadoPor");
        Log.e("Creado Por: ",creadoPor);

        nuevoHallazgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevoHallazgo.setBackgroundResource(R.drawable.boton_verde);
                intentDatosHallazgoRecorrido();
            }
        });
        buscarHallazgosDeRecorrido(ServerName+"5sGhoner/consultarHallazgosRecorrido.php");
    }


    private void buscarHallazgosDeRecorrido(String URL) {
        // Limpiar la tabla
        LinearLayout tabla = (LinearLayout) findViewById(R.id.layoutHallazgosR);
        tabla.removeAllViews();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Hallazgos encontrados",response);
                        JSONObject jsonObject = null;
                        String hallazgo = "",responsable="",id_hallazgo="";
                        try {
                            JSONArray respuestArreglo = new JSONArray(response);
                            if(respuestArreglo.length()<=0){
                                View procesando = getLayoutInflater().inflate(R.layout.toast_siguiente_pruebas,(ViewGroup)findViewById(R.id.layout_toast_prueba));
                                Toast toastMensaje =  new Toast(HallazgosRecorridos.this);
                                TextView textoTitulo =procesando.findViewById(R.id.textView35);
                                TextView mensaje =procesando.findViewById(R.id.mensaje);
                                textoTitulo.setText("SIN HALLAZGOS!!");
                                mensaje.setText("Agregue Hallazgos");

                                toastMensaje.setDuration(Toast.LENGTH_SHORT);
                                toastMensaje.setView(procesando);
                                toastMensaje.setGravity(Gravity.CENTER,0,0);
                                toastMensaje.show();
                            }else{
                                for (int i = 0; i < respuestArreglo.length(); i++) {
                                    jsonObject = respuestArreglo.getJSONObject(i);
                                    hallazgo = jsonObject.getString("hallazgo");
                                    responsable = jsonObject.getString("responsable");
                                    id_hallazgo = jsonObject.getString("id_hallazgo");
                                    crearBoton((respuestArreglo.length()-i),id_hallazgo,hallazgo,responsable);
                                }
                            }

                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Problemas al conectar intente nuevamente.", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("planta", Planta);
                parametros.put("auditor", NumeroNomina);//por si se require no se usa en consulta, se uso.
                parametros.put("codigo", Codigo);
                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void crearBoton(int cantidad,String id_hallazgo ,String hallazgo, String responsable){

        Button btnHallazgo = new Button(this);

        btnHallazgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intentHallazgosRecorrido(id_recorrido,codigo);
            }
        });



        TextView textContador = new TextView(this);
        String numero = "<font color='#000000'>Hallázgo #"+cantidad+"</font>";
        textContador.setText(Html.fromHtml(numero));
        textContador.setGravity(Gravity.CENTER_HORIZONTAL);


        ImageView imgHallazgo = new ImageView(this);
        String urlImagen = "https://vvnorth.com/5sGhoner/FotosRecorridos/"+creadoPor+"/"+ID_recorrido+"/"+id_hallazgo+".jpeg";
        Picasso.get().load(urlImagen).into(imgHallazgo);
        imgHallazgo.setLayoutParams(new ViewGroup.LayoutParams(500, 500));


        btnHallazgo.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        String texto = "<font color='#a1a1a1'>Descripción:</font> <br> <font color='#c7c7c7'>" + hallazgo + "</font><br><br>" + "<font color='#a1a1a1'>Responsable:</font> <br><font color='#c7c7c7'>" + responsable+"</font>";
        btnHallazgo.setText(Html.fromHtml(texto));

        btnHallazgo.setAllCaps(false);

        // Obtenemos el TableLayout
        LinearLayout tabla = (LinearLayout) findViewById(R.id.layoutHallazgosR);



        LinearLayout fila = new LinearLayout(this);
        LinearLayout filaImagen = new LinearLayout(this);
        LinearLayout.LayoutParams filaImagenParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        filaImagenParams.setMargins(0,20,0,0);
        filaImagen.setGravity(Gravity.CENTER_HORIZONTAL);
        filaImagen.setLayoutParams(filaImagenParams);

        LinearLayout.LayoutParams filaHallazgoParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        filaHallazgoParams.setMargins(0,0,0,30);
        fila.setLayoutParams(filaHallazgoParams);

        LinearLayout.LayoutParams paramContador = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        paramContador.setMargins(0,0,100,0);
        textContador.setLayoutParams(paramContador);

        LinearLayout.LayoutParams filaParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        //filaHallazgoParams.setMargins(0,10,0,20);



        // Establecemos el fondo de la fila
        LinearLayout lineaInferior = new LinearLayout(this);
        LinearLayout.LayoutParams lineaParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 10);
        lineaInferior.setLayoutParams(lineaParams1);
        lineaInferior.setBackgroundResource(R.drawable.sombra_inferior);

        LinearLayout lineaSuperior = new LinearLayout(this);
        LinearLayout.LayoutParams lineaParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 10);
        lineaSuperior.setLayoutParams(lineaParams2);
        lineaSuperior.setBackgroundResource(R.drawable.sombra);


        // Establecemos el peso de cada columna

        LinearLayout.LayoutParams paramsHallazgo = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        // Establecemos el ancho fijo para el botón fecha
        //paramsFecha.width = 400; // ejemplo, 100dp

        //btnHallazgo.setLines(1);
        //btnHallazgo.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        //btnResponsable.setLines(1);
        //btnResponsable.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);

        //Cuando el texto es muy grande agregara ...
        //btnHallazgo.setEllipsize(TextUtils.TruncateAt.END);
        btnHallazgo.setBackgroundResource(R.color.blaco);
        filaImagen.setBackgroundResource(R.color.blaco);
        btnHallazgo.setPadding(15,20,10,20);

        // Agregamos los botones a la fila con sus respectivos pesos
        filaImagen.addView(imgHallazgo);
        fila.addView(btnHallazgo,paramsHallazgo);
        // Agregamos la fila a la tabla
        tabla.addView(lineaSuperior);
        tabla.addView(textContador);
        tabla.addView(filaImagen);
        tabla.addView(fila);
        tabla.addView(lineaInferior);
    }




    private void intentDatosHallazgoRecorrido(){
        Intent intent = new Intent(this,DatosHallazgoRecorrido.class);
        intent.putExtra("ID_recorrido",ID_recorrido);
        intent.putExtra("Codigo",Codigo);
        intent.putExtra("creadoPor",creadoPor);
        startActivity(intent);
    }

    protected void onResume(){
        super.onResume();
        nuevoHallazgo.setBackgroundResource(R.drawable.boton_crear);
    }

    public void onBackPressed() {
        Intent intent = new Intent(HallazgosRecorridos.this, Recorridos.class);
        startActivity(intent);
    }


}