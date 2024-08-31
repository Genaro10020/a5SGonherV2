package com.example.a5SGonher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
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

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class FormularioNuevoRecorrido extends AppCompatActivity {
    String ServerName;
    CalendarView calendarioFecha;
    Spinner spinnerPlanta; // Cambia a Spinner en lugar de View
    RequestQueue requestQueue;
    String Planta,NumeroNomina;
    ArrayList<String> plantaList; // Declara el plantaList aquí
    EditText nombreRecorrido,objetivoRecorrido;
    Button crearRecorrido;
    int añoActual,mesActual,diaActual;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences=getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        Planta = preferences.getString("Planta","No existe Planta");
        NumeroNomina = preferences.getString("NumeroNomina","No existe Número Nómina");
        GlobalClass globalClass = (GlobalClass)getApplicationContext();
        ServerName=globalClass.getName();
        setContentView(R.layout.activity_formulario_nuevo_recorrido);
        crearRecorrido =(Button) findViewById(R.id.btnCrearRecorrido);
        objetivoRecorrido =(EditText) findViewById(R.id.editObjetivo);
        TextView titulo = (TextView)findViewById(R.id.titulo_toolbar);
        titulo.setText("Crear Recorrido");
        nombreRecorrido = (EditText)findViewById(R.id.editNombreRecorrido);
        spinnerPlanta= findViewById(R.id.spinnerPlanta);
        plantaList = new ArrayList<>();
        calendarioFecha = findViewById(R.id.calendarView);

        // Establece la fecha mínima en el primer día del año actual
        añoActual = Calendar.getInstance().get(Calendar.YEAR);
        mesActual = Calendar.getInstance().get(Calendar.MONTH);
        diaActual = 1; // Primer día del mes


        Calendar fechaMinima = Calendar.getInstance();
        fechaMinima.set(añoActual, mesActual, diaActual);
        calendarioFecha.setMinDate(fechaMinima.getTimeInMillis());
        consultandoPlantas(ServerName+"5sGhoner/consultarPlantas.php");


        calendarioFecha.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // Actualiza la fecha seleccionada
                añoActual = year;
                mesActual = month+1;
                diaActual = dayOfMonth;
            }

        });

        crearRecorrido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    crearRecorrido.setBackgroundResource(R.drawable.boton_verde);
                if(!nombreRecorrido.getText().toString().equals("") && !objetivoRecorrido.getText().toString().equals("")  ){
                    String plantaSeleccionada = spinnerPlanta.getSelectedItem().toString();
                    String nombre_recorrido = nombreRecorrido.getText().toString();
                    String objetivo = objetivoRecorrido.getText().toString();
                    guardarRecorrido(ServerName+"5sGhoner/guardarNuevoRecorrido.php",nombre_recorrido,plantaSeleccionada,objetivo);
                    Log.i("Presionaste","Nombre Recorrido: "+nombre_recorrido+"Anio: "+añoActual+"Mes: "+mesActual+"Dia: "+diaActual+"Planta Seleccionada: "+plantaSeleccionada);
                }else{
                    crearRecorrido.setBackgroundResource(R.drawable.boton_crear);
                    Toast.makeText(getApplicationContext(), "Nombre y Objetivo de recorrido son requeridos", Toast.LENGTH_LONG).show();

                }

            }
        });

    }

    public void consultandoPlantas(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject = null;
                        String planta="";
                        try {
                            JSONArray respuestArreglo = new JSONArray(response);
                            for (int i = 0; i < respuestArreglo.length(); i++) {
                                jsonObject = respuestArreglo.getJSONObject(i);
                                planta = jsonObject.getString("Planta");
                                plantaList.add(planta);

                                Log.e("","\n"+planta);

                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(FormularioNuevoRecorrido.this, android.R.layout.simple_spinner_item, plantaList);
                            spinnerPlanta.setAdapter(adapter);


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
                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void guardarRecorrido(final String URL, final String nombre, final String plantaSeleccionado, final String objetivo){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Respueta",response);
                        if(response.equals("true")){
                            intentRecorridos();
                        }else{
                            crearRecorrido.setBackgroundResource(R.drawable.boton_crear);
                            Toast.makeText(getApplicationContext(),"No se creo correctamente el Recorrido",Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        crearRecorrido.setBackgroundResource(R.drawable.boton_crear);
                        Toast.makeText(getApplicationContext(), "Problemas al conectar intente nuevamente.", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("planta",Planta);
                parametros.put("nombre_recorrido",nombre);
                parametros.put("objetivo_recorrido",objetivo);
                parametros.put("planta_seleccionada", plantaSeleccionado);
                parametros.put("auditor",NumeroNomina);
                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void intentRecorridos(){
        crearRecorrido.setBackgroundResource(R.drawable.boton_crear);
        Intent intent = new Intent(this,Recorridos.class);
        startActivity(intent);
        finish();
    }


}