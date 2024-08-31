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
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    String ID_recorrido,Codigo,ServerName;
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
                        String hallazgo = "",responsable="";
                        try {
                            JSONArray respuestArreglo = new JSONArray(response);

                            for (int i = 0; i < respuestArreglo.length(); i++) {
                                jsonObject = respuestArreglo.getJSONObject(i);
                                hallazgo = jsonObject.getString("hallazgo");
                                responsable = jsonObject.getString("responsable");
                                crearBoton((i+1),hallazgo,responsable);
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
                parametros.put("auditor", NumeroNomina);
                parametros.put("codigo", Codigo);
                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void crearBoton(int cantidad,String hallazgo, String responsable){

        Button miBotonNombre = new Button(this);
        Button miBotonFecha = new Button(this);

        miBotonNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intentHallazgosRecorrido(id_recorrido,codigo);
            }
        });

        miBotonNombre.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        miBotonNombre.setText(hallazgo);
        miBotonFecha.setText(responsable);

        // Obtenemos el TableLayout
        LinearLayout tabla = (LinearLayout) findViewById(R.id.layoutHallazgosR);


        // Creamos un TableRow
        TableRow fila = new TableRow(this);
        TableRow.LayoutParams filaParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
        fila.setLayoutParams(filaParams);


        // Establecemos el fondo de la fila
        fila.setBackgroundResource(R.drawable.lista_recorridos);


        // Establecemos el peso de cada columna
        TableRow.LayoutParams paramsNombre = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT,2f);
        TableRow.LayoutParams paramsFecha = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT,2f);
        // Establecemos el ancho fijo para el botón fecha
        //paramsFecha.width = 400; // ejemplo, 100dp

        miBotonNombre.setLines(1);
        miBotonNombre.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        miBotonFecha.setLines(1);
        miBotonFecha.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);

        //Cuando el texto es muy grande agregara ...
        miBotonNombre.setEllipsize(TextUtils.TruncateAt.END);
        miBotonNombre.setPadding(25, 0, 50, 0);

        // Agregamos los botones a la fila con sus respectivos pesos
        fila.addView(miBotonNombre, paramsNombre);
        fila.addView(miBotonFecha, paramsFecha);
        // Agregamos la fila a la tabla
        tabla.addView(fila);
    }




    private void intentDatosHallazgoRecorrido(){
        Intent intent = new Intent(this,DatosHallazgoRecorrido.class);
        intent.putExtra("ID_recorrido",ID_recorrido);
        startActivity(intent);
    }

    protected void onResume(){
        super.onResume();
        nuevoHallazgo.setBackgroundResource(R.drawable.boton_crear);
    }


}