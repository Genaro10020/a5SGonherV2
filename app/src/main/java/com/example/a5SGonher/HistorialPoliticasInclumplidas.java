package com.example.a5SGonher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HistorialPoliticasInclumplidas extends AppCompatActivity {

    TextView titulo;
    String Planta;
    String numeroNomina,ServerName,plantaSeleccionada="",areaSeleccionada="";
    RequestQueue requestQueue;
    Spinner spinnerPlanta;
    Spinner spinnerArea;
    int llenadoSelectInicial = 0;
    Set<String> setPlantas;
    Set<String> setAreas;
    List<String> listaPlantas;
    List<String> listaAreas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_politicas_inclumplidas);

        //Recupero Informacion
        SharedPreferences preferences=getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String user= preferences.getString("User","No existe Usuario");
        String Rol= preferences.getString("Rol","No existe Usuario");
        String nombreAuditor= preferences.getString("NombreAuditor","No existe Usuario");
        Planta= preferences.getString("Planta","No existe Planta");
        numeroNomina= preferences.getString("NumeroNomina","Sin Número de Nómina");

        spinnerPlanta = findViewById(R.id.spinnerPlanta);
        spinnerArea = findViewById(R.id.spinnerArea);

        titulo = (TextView)findViewById(R.id.titulo_toolbar);
        titulo.setText("Historial Políticas");

        GlobalClass globalClass = (GlobalClass)getApplicationContext();
        ServerName = globalClass.getName();
        consultHistorialPoliticas(ServerName+"5sGhoner/consultarHistorialPoliticas.php");


        spinnerPlanta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (position != 0) {
                    plantaSeleccionada = spinnerPlanta.getSelectedItem().toString();
                }else{
                    plantaSeleccionada = "";
                }
                consultHistorialPoliticas(ServerName+"5sGhoner/consultarHistorialPoliticas.php");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // No hacer nada
            }
        });

        spinnerArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (position != 0) {
                    areaSeleccionada = spinnerArea.getSelectedItem().toString();
                }else{
                    areaSeleccionada = "";
                }
                consultHistorialPoliticas(ServerName+"5sGhoner/consultarHistorialPoliticas.php");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // No hacer nada
            }
        });

    }


    public void consultHistorialPoliticas(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                View procesando = getLayoutInflater().inflate(R.layout.toast_procesando,(ViewGroup)findViewById(R.id.layout_toast_procesando));
                ImageView fondo = procesando.findViewById(R.id.imageView25);

                TextView textoTitulo =procesando.findViewById(R.id.textView35);
                TextView mensaje =procesando.findViewById(R.id.mensaje1);
                Toast toasprocesando =  new Toast(HistorialPoliticasInclumplidas.this);
                toasprocesando.setDuration(Toast.LENGTH_LONG);
                toasprocesando.setView(procesando);
                toasprocesando.setGravity(Gravity.CENTER,0,0);
                Log.i("Respuesta historial OK:",response);

                LinearLayout mainLayout = findViewById(R.id.mainLayout);
                mainLayout.removeAllViews();
                LinearLayout principalLayout = new LinearLayout(HistorialPoliticasInclumplidas.this);
                principalLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                principalLayout.setOrientation(LinearLayout.HORIZONTAL);
                principalLayout.setGravity(Gravity.CENTER_VERTICAL);  // Para alinear los TextViews verticalmente


                // Crear los 5 TextViews con los datos del JSON
                TextView textView1P = new TextView(HistorialPoliticasInclumplidas.this);
                textView1P.setLayoutParams(new LinearLayout.LayoutParams(
                        0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));  // Peso para distribuir el espacio
                textView1P.setGravity(Gravity.CENTER);
                textView1P.setText("NÓMINA");  // Asigna el valor correcto de tu JSON
                textView1P.setBackgroundResource(R.drawable.nuevos_botones_listado);
                textView1P.setTypeface(null, Typeface.BOLD);
                textView1P.setTextColor(ContextCompat.getColor(HistorialPoliticasInclumplidas.this, R.color.blaco));

                // Crear los 5 TextViews con los datos del JSON
                TextView textView2P = new TextView(HistorialPoliticasInclumplidas.this);
                textView2P.setLayoutParams(new LinearLayout.LayoutParams(
                        0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));  // Peso para distribuir el espacio
                textView2P.setGravity(Gravity.CENTER);
                textView2P.setText("PLANTA");  // Asigna el valor correcto de tu JSON
                textView2P.setBackgroundResource(R.drawable.nuevos_botones_listado);
                textView2P.setTypeface(null, Typeface.BOLD);
                textView2P.setTextColor(ContextCompat.getColor(HistorialPoliticasInclumplidas.this, R.color.blaco));


                // Crear los 5 TextViews con los datos del JSON
                TextView textView3P = new TextView(HistorialPoliticasInclumplidas.this);
                textView3P.setLayoutParams(new LinearLayout.LayoutParams(
                        0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));  // Peso para distribuir el espacio
                textView3P.setGravity(Gravity.CENTER);
                textView3P.setText("ÁREA");  // Asigna el valor correcto de tu JSON
                textView3P.setBackgroundResource(R.drawable.nuevos_botones_listado);
                textView3P.setTypeface(null, Typeface.BOLD);
                textView3P.setTextColor(ContextCompat.getColor(HistorialPoliticasInclumplidas.this, R.color.blaco));


                TextView textView4P = new TextView(HistorialPoliticasInclumplidas.this);
                textView4P.setLayoutParams(new LinearLayout.LayoutParams(
                        0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                textView4P.setGravity(Gravity.CENTER);
                textView4P.setText("EMPLEADO");
                textView4P.setBackgroundResource(R.drawable.nuevos_botones_listado);
                textView4P.setTypeface(null, Typeface.BOLD);
                textView4P.setTextColor(ContextCompat.getColor(HistorialPoliticasInclumplidas.this, R.color.blaco));



                TextView textView5P = new TextView(HistorialPoliticasInclumplidas.this);
                textView5P.setLayoutParams(new LinearLayout.LayoutParams(
                        0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                textView5P.setGravity(Gravity.CENTER);
                textView5P.setText("COMENTARIO");
                textView5P.setBackgroundResource(R.drawable.nuevos_botones_listado);
                textView5P.setTypeface(null, Typeface.BOLD);
                textView5P.setTextColor(ContextCompat.getColor(HistorialPoliticasInclumplidas.this, R.color.blaco));


                TextView textView6P = new TextView(HistorialPoliticasInclumplidas.this);
                textView6P.setLayoutParams(new LinearLayout.LayoutParams(
                        0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                textView6P.setGravity(Gravity.CENTER);
                textView6P.setText("DESCRIPCIÓN");
                textView6P.setBackgroundResource(R.drawable.nuevos_botones_listado);
                textView6P.setTypeface(null, Typeface.BOLD);
                textView6P.setTextColor(ContextCompat.getColor(HistorialPoliticasInclumplidas.this, R.color.blaco));


                TextView textView7P = new TextView(HistorialPoliticasInclumplidas.this);
                textView7P.setLayoutParams(new LinearLayout.LayoutParams(
                        0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                textView7P.setGravity(Gravity.CENTER);
                textView7P.setText("FECHA");
                textView7P.setBackgroundResource(R.drawable.nuevos_botones_listado);
                textView7P.setTypeface(null, Typeface.BOLD);
                textView7P.setTextColor(ContextCompat.getColor(HistorialPoliticasInclumplidas.this, R.color.blaco));

                TextView textView8P = new TextView(HistorialPoliticasInclumplidas.this);
                textView8P.setLayoutParams(new LinearLayout.LayoutParams(
                        0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                textView8P.setGravity(Gravity.CENTER);
                textView8P.setText("EVIDENCIA");
                textView8P.setBackgroundResource(R.drawable.nuevos_botones_listado);
                textView8P.setTypeface(null, Typeface.BOLD);
                textView8P.setTextColor(ContextCompat.getColor(HistorialPoliticasInclumplidas.this, R.color.blaco));

                try {
                    JSONArray respuestArreglo = new JSONArray(response);
                    if(respuestArreglo.length() > 0){//si es true entrara si es false no

                        //mainLayout.setPadding(15,0,15,0);
                        // Recorrer el JSONArray para obtener los datos y agregarlos al ArrayList

                        int colorLayout;
                        if(llenadoSelectInicial==0) {//Solo al iniciar
                            setPlantas = new HashSet<>();
                            setAreas = new HashSet<>();
                        }
                        for (int i = 0; i < respuestArreglo.length(); i++) {
                            JSONObject jsonObject = respuestArreglo.getJSONObject(i);

                            if(llenadoSelectInicial==0){//Solo al iniciar
                                String planta = jsonObject.getString("planta");
                                String area = jsonObject.getString("area");
                                setPlantas.add(planta); // Agregar a la lista de plantas
                                setAreas.add(area); // Agregar a la lista de áreas
                            }

                            // Crear un nuevo LinearLayout para cada fila (Horizontal)
                            LinearLayout horizontalLayout = new LinearLayout(HistorialPoliticasInclumplidas.this);
                            horizontalLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
                            horizontalLayout.setGravity(Gravity.CENTER_VERTICAL);
                            colorLayout = (i % 2 == 0) ? R.color.gris2 : R.color.gris3;
                            horizontalLayout.setBackgroundResource(colorLayout);
                            Typeface miFuente = ResourcesCompat.getFont(HistorialPoliticasInclumplidas.this, R.font.saira_condensed_medium);

                            // Crear los 5 TextViews con los datos del JSON
                            TextView textView1 = new TextView(HistorialPoliticasInclumplidas.this);
                            textView1.setLayoutParams(new LinearLayout.LayoutParams(
                                    0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));  // Peso para distribuir el espacio
                            textView1.setGravity(Gravity.CENTER);
                            textView1.setText(jsonObject.getString("nomina"));  // Asigna el valor correcto de tu JSON

                            TextView textView2 = new TextView(HistorialPoliticasInclumplidas.this);
                            textView2.setLayoutParams(new LinearLayout.LayoutParams(
                                    0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));  // Peso para distribuir el espacio
                            textView2.setGravity(Gravity.CENTER);
                            textView2.setText(jsonObject.getString("planta"));  // Asigna el valor correcto de tu JSON

                            TextView textView3 = new TextView(HistorialPoliticasInclumplidas.this);
                            textView3.setLayoutParams(new LinearLayout.LayoutParams(
                                    0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));  // Peso para distribuir el espacio
                            textView3.setGravity(Gravity.LEFT);
                            textView3.setText(jsonObject.getString("area"));  // Asigna el valor correcto de tu JSON

                            TextView textView4 = new TextView(HistorialPoliticasInclumplidas.this);
                            textView4.setLayoutParams(new LinearLayout.LayoutParams(
                                    0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                            textView4.setGravity(Gravity.LEFT);
                            textView4.setText(jsonObject.getString("nombre"));
                            textView4.setTypeface(miFuente);


                            TextView textView5 = new TextView(HistorialPoliticasInclumplidas.this);
                            textView5.setLayoutParams(new LinearLayout.LayoutParams(
                                    0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                            textView5.setGravity(Gravity.LEFT);
                            textView5.setText(jsonObject.getString("comentario"));
                            textView5.setTypeface(miFuente);


                            TextView textView6 = new TextView(HistorialPoliticasInclumplidas.this);
                            textView6.setLayoutParams(new LinearLayout.LayoutParams(
                                    0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                            textView6.setGravity(Gravity.LEFT);
                            textView6.setText(jsonObject.getString("descripcion"));
                            textView6.setTypeface(miFuente);


                            TextView textView7 = new TextView(HistorialPoliticasInclumplidas.this);
                            textView7.setLayoutParams(new LinearLayout.LayoutParams(
                                    0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                            textView7.setGravity(Gravity.CENTER);
                            textView7.setText(jsonObject.getString("fecha"));
                            textView7.setTypeface(miFuente);

                            ImageView imagenEvidencia = new ImageView(HistorialPoliticasInclumplidas.this);
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                    0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                            params.gravity = Gravity.CENTER;

                            imagenEvidencia.setLayoutParams(params);
                            imagenEvidencia.setImageResource(R.drawable.ic_evidence); // o con Glide/Picasso si viene de URL
                            imagenEvidencia.setAdjustViewBounds(true);
                            imagenEvidencia.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

                            final String idIncummplimiento = jsonObject.getString("id");

                            imagenEvidencia.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    // Crear un Intent y pasar las variables directamente como extras
                                    Intent intent = new Intent(HistorialPoliticasInclumplidas.this, ShowOnlyImagePolicies.class);
                                    intent.putExtra("Planta", Planta);  // Pasar la variable directamente
                                    intent.putExtra("idCumplimiento", idIncummplimiento);  // Pasar la variable directamente
                                    startActivity(intent);
                                }
                            });

                            horizontalLayout.addView(textView1);
                            horizontalLayout.addView(textView2);
                            horizontalLayout.addView(textView3);
                            horizontalLayout.addView(textView4);
                            horizontalLayout.addView(textView5);
                            horizontalLayout.addView(textView6);
                            horizontalLayout.addView(textView7);
                            horizontalLayout.addView(imagenEvidencia);

                            // Agregar los TextViews al LinearLayout horizontal
                            if (i==0){
                                principalLayout.addView(textView1P);
                                principalLayout.addView(textView2P);
                                principalLayout.addView(textView3P);
                                principalLayout.addView(textView4P);
                                principalLayout.addView(textView5P);
                                principalLayout.addView(textView6P);
                                principalLayout.addView(textView7P);
                                principalLayout.addView(textView8P);
                                mainLayout.addView(principalLayout);
                            }



                            // Agregar el LinearLayout horizontal al layout principal

                            mainLayout.addView(horizontalLayout);
                        }




                                    // Crear los adaptadores para los spinners
                        if (llenadoSelectInicial==0){//Solo al iniciar
                                    listaPlantas = new ArrayList<>(setPlantas);
                                    listaAreas = new ArrayList<>(setAreas);
                                    listaPlantas.add(0, "Todas las plantas...");
                                    listaAreas.add(0, "Todas las áreas...");

                                    // Crear y configurar el ArrayAdapter
                                    ArrayAdapter<String> plantaAdapter = new ArrayAdapter<String>(
                                            HistorialPoliticasInclumplidas.this,
                                            android.R.layout.simple_spinner_dropdown_item, // Estilo desplegado
                                            listaPlantas
                                    ) {
                                        @Override
                                        public View getView(int position, View convertView, ViewGroup parent) {
                                            View view = super.getView(position, convertView, parent);
                                            TextView textView = (TextView) view;
                                            // Optimización: obtener el color solo una vez
                                            int colorAccent = getResources().getColor(R.color.colorAccent);
                                            textView.setTextSize(12);
                                            textView.setTextColor(colorAccent);
                                            return view;
                                        }
                                        public View getDropDownView(int position, View convertView, ViewGroup parent) {
                                            // Este es el método para la lista desplegada
                                            View view = super.getDropDownView(position, convertView, parent);
                                            TextView textView = (TextView) view;

                                            // Establecer el color y tamaño de texto para la lista desplegada
                                            textView.setTextSize(12); // Establecer tamaño de texto
                                            textView.setPadding(50,0,0,0);

                                            return view;
                                        }
                                    };
                                    spinnerPlanta.setAdapter(plantaAdapter);



                                    // Crear y configurar el ArrayAdapter
                                    ArrayAdapter<String> areaAdapter = new ArrayAdapter<String>(
                                            HistorialPoliticasInclumplidas.this,
                                            android.R.layout.simple_spinner_dropdown_item, // Estilo desplegado
                                            listaAreas
                                    ) {
                                        @Override
                                        public View getView(int position, View convertView, ViewGroup parent) {
                                            View view = super.getView(position, convertView, parent);
                                            TextView textView = (TextView) view;
                                            // Optimización: obtener el color solo una vez
                                            int colorAccent = getResources().getColor(R.color.colorAccent);
                                            textView.setTextSize(12);
                                            textView.setTextColor(colorAccent);
                                            return view;
                                        }
                                        public View getDropDownView(int position, View convertView, ViewGroup parent) {
                                            // Este es el método para la lista desplegada
                                            View view = super.getDropDownView(position, convertView, parent);
                                            TextView textView = (TextView) view;

                                            // Establecer el color y tamaño de texto para la lista desplegada
                                            textView.setTextSize(12); // Establecer tamaño de texto
                                            textView.setPadding(50,0,0,0);

                                            return view;
                                        }
                                    };
                                    spinnerArea.setAdapter(areaAdapter);
                        }
                        llenadoSelectInicial = 1;


                    }else{
                        mainLayout.removeAllViews();
                        fondo.setImageResource(R.drawable.fondo_toast_procesando);
                        textoTitulo.setText("SIN HISTORIAL..");
                        mensaje.setText("No hay incumplimientos,por ahora.");
                        toasprocesando.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    fondo.setImageResource(R.drawable.fondo_toast_next_pruebas);
                    textoTitulo.setText("INTENTELO NUEVAMENTE..");
                    mensaje.setText("Algo no esta funcionando correctamente..");
                    toasprocesando.show();
                    Log.e("Error JSON", "Error en área al parsear JSON: " + e.getMessage());
                    RegresarAPoliticas();
                }

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                View procesando = getLayoutInflater().inflate(R.layout.toast_procesando,(ViewGroup)findViewById(R.id.layout_toast_procesando));
                ImageView fondo = procesando.findViewById(R.id.imageView25);
                TextView textoTitulo =procesando.findViewById(R.id.textView35);
                TextView mensaje =procesando.findViewById(R.id.mensaje1);
                fondo.setImageResource(R.drawable.fondo_toast_next_pruebas);
                textoTitulo.setText("ALGO SALIO MAL..");
                mensaje.setText("Verifique su conexión, intente nuevamente.");
                RegresarAPoliticas();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String,String>();
                parametros.put("planta",Planta);
                parametros.put("plantaSeleccionada",plantaSeleccionada);
                parametros.put("areaSeleccionada",areaSeleccionada);
                return parametros;
            }
        };
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }




    public void RegresarAPoliticas(){
        Intent intent = new Intent(this,MainMenu.class);
        startActivity(intent);
    }
}