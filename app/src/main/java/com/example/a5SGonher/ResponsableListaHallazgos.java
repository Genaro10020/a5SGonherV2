package com.example.a5SGonher;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ResponsableListaHallazgos extends AppCompatActivity {
    RequestQueue requestQueue;
    TextView titular_session;
    String Planta;
    ViewGroup includeConfirmar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_responsable_lista_hallazgos);
        TextView titulo = (TextView)findViewById(R.id.titulo_toolbar);
        titulo.setText("Hallazgos Abiertos");
        includeConfirmar = findViewById(R.id.includeMensaje);
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
                            String comentario_solucion = jsonObjectHallazgos.getString("comentario_solucion");
                            String fechaCompromiso = jsonObjectHallazgos.getString("fecha_final");
                            String fechaCompromisoDate = "0000-00-00";
                            if(!fechaCompromiso.equals("0000-00-00")){
                                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                                Date date = inputFormat.parse(fechaCompromiso);
                                SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
                                fechaCompromisoDate = outputFormat.format(date);
                            }


                            espacioHallazgo(nominaAuditor,id_recorrido,codigoAuditoria,nombreAuditor,idHallazgo,hallazgo,fechaHallazgo,statusHallazgo,fechaCompromisoDate,comentario_solucion);
                        }

                    }else{
                        Toast.makeText(getApplicationContext(),"Sin Hallazgos",Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException | ParseException e) {
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

   public void espacioHallazgo(String nominaAuditor, String idRecorrido, String codigoAuditoria, String nombreAuditor, final String idHallazgo, String hallazgo, String fechaHallazgo, String statusHallazgo, String fechaCompromiso, final String comentario_solucion)
   {
       LinearLayout layoutPadre = (LinearLayout)findViewById(R.id.layoutHallazgos);
       LinearLayout layoutHijo = new LinearLayout(this);
       final LinearLayout layoutHijo2 = new LinearLayout(this);
       final LinearLayout layoutHijo3 = new LinearLayout(this);


       TextView textDatos = new TextView(this);
       TextView textStatus = new TextView(this);
       ImageView imagen = new ImageView(this);
       Button boton = new Button(this);
       final Button FinalizarHallazgo = new Button(this);
       TextView textoFecha = new TextView(this);
       final Button botonCalendario = new Button(this);
       FinalizarHallazgo.setText("Finalizar Hallazgo");
       FinalizarHallazgo.setPadding(20,0,20,0);
       layoutHijo3.setBackgroundResource(R.drawable.linea_titularsession);


       if(statusHallazgo.equals("100")){
           FinalizarHallazgo.setVisibility(View.VISIBLE);

       }else{
           FinalizarHallazgo.setVisibility(View.GONE);
       }


       textoFecha.setText("Fecha Compromiso:");
       textoFecha.setGravity(Gravity.CENTER);
       botonCalendario.setText(fechaCompromiso);
       botonCalendario.setBackgroundResource(R.drawable.boton_nocompletado);
       botonCalendario.setTextColor(Color.WHITE);

       botonCalendario.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               // Get the current date
               Calendar initialDate = Calendar.getInstance();
               // Construct the dialog
               DatePickerDialog datePickerDialog = new DatePickerDialog(
                       ResponsableListaHallazgos.this,
                       new DatePickerDialog.OnDateSetListener() {
                           @Override
                           public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                               // Use the selected date
                               Calendar date = Calendar.getInstance();
                               date.set(Calendar.YEAR, year);
                               date.set(Calendar.MONTH, month);
                               date.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                               SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
                               String formattedDate = simpleDateFormat.format(date.getTime());
                               botonCalendario.setText(formattedDate);

                               SimpleDateFormat mysqlDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                               String mysqlFormattedDate = mysqlDateFormat.format(date.getTime());
                               guardarFecha(idHallazgo,mysqlFormattedDate);
                           }
                       },
                       initialDate.get(Calendar.YEAR),
                       initialDate.get(Calendar.MONTH),
                       initialDate.get(Calendar.DAY_OF_MONTH)
               );
               // Show the dialog
               datePickerDialog.show();
           }
       });

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
       final boolean[] spinnerInitialized = {false};
       spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//al seleccionar otro elemento guardara
           @Override
           public void onItemSelected(AdapterView<?> datos, View view, int posicion, long id) {
               if (!spinnerInitialized[0]) {
                   spinnerInitialized[0] = true;
                   return;
               }
                String selectValue = datos.getItemAtPosition(posicion).toString();


               if (selectValue.equals("100")){
                   FinalizarHallazgo.setVisibility(View.VISIBLE);
               }else{
                   FinalizarHallazgo.setVisibility(View.GONE);
               }


                guardarStatus(idHallazgo,selectValue);

           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });

       LinearLayout.LayoutParams paramsHijo3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
       paramsHijo3.setMargins(0,0,0,100);
       layoutHijo3.setLayoutParams(paramsHijo3);

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


       LinearLayout.LayoutParams paramsButton = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT,1f);
       paramsButton.setMargins(10,50,10,20);
       boton.setLayoutParams(paramsButton);
       boton.setBackgroundResource(R.drawable.boton_crear);
       boton.setTextColor(Color.WHITE);

       LinearLayout.LayoutParams paramsButtonF = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT,1f);
       paramsButtonF.setMargins(0,50,0,20);
       FinalizarHallazgo.setLayoutParams(paramsButtonF);
       FinalizarHallazgo.setBackgroundResource(R.drawable.boton_enviado);
       FinalizarHallazgo.setTextColor(Color.WHITE);


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

       FinalizarHallazgo.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               confirmarFinalizarHallazgo(idHallazgo);
           }
       });

       layoutHijo.setOrientation(LinearLayout.HORIZONTAL);
       layoutHijo.addView(textDatos);
       layoutHijo.addView(imagen);
       layoutHijo3.setOrientation(LinearLayout.HORIZONTAL);
       layoutHijo3.setGravity(Gravity.CENTER_HORIZONTAL);

       layoutHijo2.addView(textStatus);
       layoutHijo2.addView(spinnerStatus);
       layoutHijo3.addView(boton);
       layoutHijo3.addView(FinalizarHallazgo);
       layoutPadre.addView(textoFecha);
       layoutPadre.addView(botonCalendario);
       layoutPadre.addView(layoutHijo);
       layoutPadre.addView(layoutHijo2);
       layoutPadre.addView(layoutHijo3);
       boton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               intenSolucionarHallazgo(idHallazgo,comentario_solucion);
           }
       });
    }

    public void intenSolucionarHallazgo(String idHallazgo,String comentarioSolucion){
        Intent intent = new Intent(this,ResponsableSolucionHallazgo.class);
        intent.putExtra("ID_HALLAZGO",idHallazgo);
        intent.removeExtra("COMENTARIO_SOLUCION");
        intent.putExtra("COMENTARIO_SOLUCION",comentarioSolucion);
        startActivity(intent);
    }


    public void guardarStatus(final String idHallazgo, final String statusHallazgo){
        String URL = "https://vvnorth.com/5sGhoner/guardarEstatusHallazgo.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String respuesta) {
                        if(respuesta.equals("true")){
                            View layoutToast = getLayoutInflater().inflate(R.layout.toast_procesando,(ViewGroup)findViewById(R.id.layout_toast_procesando));
                            Toast toasprocesando =  new Toast(ResponsableListaHallazgos.this);
                            TextView textoTitulo =layoutToast.findViewById(R.id.textView35);
                            TextView mensaje =layoutToast.findViewById(R.id.mensaje1);
                            textoTitulo.setText("SE GUARDO ESTATUS!!");
                            mensaje.setText("Se guardado correctamente");

                            toasprocesando.setDuration(Toast.LENGTH_SHORT);
                            toasprocesando.setView(layoutToast);
                            toasprocesando.setGravity(Gravity.CENTER,0,0);
                            toasprocesando.show();
                        }else{
                            View layoutToast = getLayoutInflater().inflate(R.layout.mensaje_mal,(ViewGroup)findViewById(R.id.layout_toast_comentario));
                            Toast toasprocesando =  new Toast(ResponsableListaHallazgos.this);
                            TextView textoTitulo =layoutToast.findViewById(R.id.textView35);
                            TextView mensaje =layoutToast.findViewById(R.id.mensaje1);
                            textoTitulo.setText("ALGO SALIO MAL!!");
                            mensaje.setText("Intentelo de nuevo");

                            toasprocesando.setDuration(Toast.LENGTH_SHORT);
                            toasprocesando.setView(layoutToast);
                            toasprocesando.setGravity(Gravity.CENTER,0,0);
                            toasprocesando.show();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("planta", Planta);
                parametros.put("ID_Hallazgo", idHallazgo);
                parametros.put("Estatus", statusHallazgo);
                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void guardarFecha(final String idHallazgo, final String fechaCompromiso){
        String URL = "https://vvnorth.com/5sGhoner/guardarFechaCompromisoHallazgo.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String respuesta) {
                        Log.e("Respuesa","Fecha: "+respuesta);
                        if(respuesta.equals("true")){
                            View layoutToast = getLayoutInflater().inflate(R.layout.toast_procesando,(ViewGroup)findViewById(R.id.layout_toast_procesando));
                            Toast toasprocesando =  new Toast(ResponsableListaHallazgos.this);
                            TextView textoTitulo =layoutToast.findViewById(R.id.textView35);
                            TextView mensaje =layoutToast.findViewById(R.id.mensaje1);
                            textoTitulo.setText("SE GUARDO FECHA!!");
                            mensaje.setText("Se guardado correctamente");

                            toasprocesando.setDuration(Toast.LENGTH_SHORT);
                            toasprocesando.setView(layoutToast);
                            toasprocesando.setGravity(Gravity.CENTER,0,0);
                            toasprocesando.show();
                        }else{
                            View layoutToast = getLayoutInflater().inflate(R.layout.mensaje_mal,(ViewGroup)findViewById(R.id.layout_toast_comentario));
                            Toast toasprocesando =  new Toast(ResponsableListaHallazgos.this);
                            TextView textoTitulo =layoutToast.findViewById(R.id.textView35);
                            TextView mensaje =layoutToast.findViewById(R.id.mensaje1);
                            textoTitulo.setText("ALGO SALIO MAL!!");
                            mensaje.setText("Intentelo de nuevo");

                            toasprocesando.setDuration(Toast.LENGTH_SHORT);
                            toasprocesando.setView(layoutToast);
                            toasprocesando.setGravity(Gravity.CENTER,0,0);
                            toasprocesando.show();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("planta", Planta);
                parametros.put("ID_Hallazgo", idHallazgo);
                parametros.put("fecha_Compromiso", fechaCompromiso);
                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void guardarFinalizado(final String idHallazgo){
        String URL = "https://vvnorth.com/5sGhoner/guardarFinalizadoHallazgo.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String respuesta) {
                        Log.e("Finalizo",":"+respuesta);
                        if(respuesta.equals("true")){
                            View layoutToast = getLayoutInflater().inflate(R.layout.toast_procesando,(ViewGroup)findViewById(R.id.layout_toast_procesando));
                            Toast toasprocesando =  new Toast(ResponsableListaHallazgos.this);
                            TextView textoTitulo =layoutToast.findViewById(R.id.textView35);
                            TextView mensaje =layoutToast.findViewById(R.id.mensaje1);
                            textoTitulo.setText("Finalizado!!");
                            mensaje.setText("Hallazgo Finalizado Correctamente");

                            toasprocesando.setDuration(Toast.LENGTH_SHORT);
                            toasprocesando.setView(layoutToast);
                            toasprocesando.setGravity(Gravity.CENTER,0,0);
                            toasprocesando.show();
                            intenResfrescar();
                        }else{
                            View layoutToast = getLayoutInflater().inflate(R.layout.mensaje_mal,(ViewGroup)findViewById(R.id.layout_toast_comentario));
                            Toast toasprocesando =  new Toast(ResponsableListaHallazgos.this);
                            TextView textoTitulo =layoutToast.findViewById(R.id.textView35);
                            TextView mensaje =layoutToast.findViewById(R.id.mensaje1);
                            textoTitulo.setText("Algo salio Mal!!");
                            mensaje.setText("Intentelo de nuevo");

                            toasprocesando.setDuration(Toast.LENGTH_SHORT);
                            toasprocesando.setView(layoutToast);
                            toasprocesando.setGravity(Gravity.CENTER,0,0);
                            toasprocesando.show();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("planta", Planta);
                parametros.put("ID_Hallazgo", idHallazgo);
                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void confirmarFinalizarHallazgo(final String idHallazgo){
        includeConfirmar.setVisibility(View.VISIBLE);
        TextView btnAceptar = (TextView)findViewById(R.id.btn_aceptar);
        TextView btnCancelar = (TextView)findViewById(R.id.btn_cancelar);
            btnCancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    includeConfirmar.setVisibility(View.GONE);
                }
            });

            btnAceptar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    guardarFinalizado(idHallazgo);
                }
            });

    }

    public void intenResfrescar(){
        Intent intent = new Intent(this,ResponsableListaHallazgos.class);
        String tipoHallazgo = "";//Abiertos
        intent.putExtra("tipoHallazgo",tipoHallazgo);
        startActivity(intent);
        finish();
    }

    public void onBackPressed() {
        Intent intent = new Intent(this, MainResponsable.class);
        startActivity(intent);
    }



}