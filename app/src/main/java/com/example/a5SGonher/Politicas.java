package com.example.a5SGonher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Politicas extends AppCompatActivity {

    RadioButton radioPolitica1,radioPolitica2,radioPolitica3,radioPolitica4,radioPolitica5;
    ImageView escuchar_voz,iconNumero1,newSubArea,iconNumero2,iconNumero3,iconNumero4,iconNumero5, photo1,limpiar;
    TextView textPolit1,textPolit2,textPolit3,textPolit4,textPolit5,waitingSaved,btn_crear_area;
    EditText editNomina,editNombre,editComentario,editTextNewArea;
    Button btnGuardar,btnCloseEdit;
    String Nomina,Nombre,Comentario;
    Bitmap bitmap1;
    DrawView drawView;
    LinearLayout layoutData,btnHistorialPoliticas;
    ScrollView scrollPoliticas;
    TableLayout tableLayout;
    String ServerName,Planta,numeroNomina,descripcionPolitica;
    Spinner spinnerAreas;
    Spinner spinnerPlantas;
    private String currentPhotoPath;
    ArrayList<String> ListAreas;
    int takenPhoto = 0,politicaIncumplida=0, idHallazgo =0;
    View layout_crear_area;
    RequestQueue requestQueue;
    private static final int REQUEST_CODE_SPEECH_INPUT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_politicas);
        // Establecer el título de la toolbar
        TextView titulo_toolbar = findViewById(R.id.titulo_toolbar);
        titulo_toolbar.setText("Políticas");

        //Recupero Informacion
        SharedPreferences preferences=getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String user= preferences.getString("User","No existe Usuario");
        String Rol= preferences.getString("Rol","No existe Usuario");
        String nombreAuditor= preferences.getString("NombreAuditor","No existe Usuario");
        Planta= preferences.getString("Planta","No existe Planta");
        numeroNomina= preferences.getString("NumeroNomina","Sin Número de Nómina");

        btnHistorialPoliticas = (LinearLayout)findViewById(R.id.layout_historial);
        spinnerPlantas = findViewById(R.id.snipperPlanta);


        GlobalClass globalClass = (GlobalClass)getApplicationContext();
        ServerName = globalClass.getName();
        layoutData = findViewById(R.id.linearLayoutDatos);
        scrollPoliticas = findViewById(R.id.scrollPoliticas);

        tableLayout = findViewById(R.id.TableCanva);

        escuchar_voz=findViewById(R.id.img_record);
        limpiar = (ImageView)findViewById(R.id.btn_limpiar);

        // Luego inicializamos los RadioButton después de inflar el layout
        radioPolitica1 = findViewById(R.id.radioButtonPolitica1);
        radioPolitica2 = findViewById(R.id.radioButtonPolitica2);
        radioPolitica3 = findViewById(R.id.radioButtonPolitica3);
        radioPolitica4 = findViewById(R.id.radioButtonPolitica4);
        radioPolitica5 = findViewById(R.id.radioButtonPolitica5);

        newSubArea = findViewById(R.id.newSubArea);

        iconNumero1 = findViewById(R.id.number1);
        iconNumero2 = findViewById(R.id.number2);
        iconNumero3 = findViewById(R.id.number3);
        iconNumero4 = findViewById(R.id.number4);
        iconNumero5 = findViewById(R.id.number5);

        textPolit1 = findViewById(R.id.textViewP1);
        textPolit2 = findViewById(R.id.textViewP2);
        textPolit3 = findViewById(R.id.textViewP3);
        textPolit4 = findViewById(R.id.textViewP4);
        textPolit5 = findViewById(R.id.textViewP5);
        waitingSaved = findViewById(R.id.waitingSaved);

        editNomina = findViewById(R.id.editNomina);
        editNombre = findViewById(R.id.editNombre);
        editComentario = findViewById(R.id.editComentario);

        photo1 = findViewById(R.id.imageEvidence);
        drawView = findViewById(R.id.EspacioCanva);

        btnCloseEdit = findViewById(R.id.btnSaveEdit);
        btnGuardar = findViewById(R.id.btnGuardar);

        btn_crear_area = findViewById(R.id.btn_crear_area);
        spinnerAreas = findViewById(R.id.spinneArea);
        ListAreas = new ArrayList<>();
        editTextNewArea = findViewById(R.id.editTextNewArea);

        limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editComentario.setText("");
            }
        });

        escuchar_voz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSpeechToText();
            }
        });


        newSubArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_crear_area = findViewById(R.id.include_layout_crear_nueva_area);
                TextView btnCancelar = findViewById(R.id.btn_cancelar);
                layout_crear_area.setVisibility(View.VISIBLE);
                consultarPlantas(ServerName+"5sGhoner/consultarPlantas.php");
                btnCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        layout_crear_area.setVisibility(View.GONE);
                    }
                });
            }
        });

        btn_crear_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNewArea(ServerName+"5sGhoner/crearAreaPoliticas.php");
            }
        });

        // Establecer los listeners para los RadioButton
        radioPolitica1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    desmarcarOtros("radioPolitica1");
                politicaIncumplida = 1;
                descripcionPolitica = textPolit1.getText().toString();
            }
        });
        radioPolitica2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    desmarcarOtros("radioPolitica2");
                politicaIncumplida = 2;
                descripcionPolitica = textPolit2.getText().toString();

            }
        });
        radioPolitica3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    desmarcarOtros("radioPolitica3");
                politicaIncumplida = 3;
                descripcionPolitica = textPolit3.getText().toString();
            }
        });
        radioPolitica4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    desmarcarOtros("radioPolitica4");;
                politicaIncumplida = 4;
                descripcionPolitica = textPolit4.getText().toString();
            }
        });
        radioPolitica5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    desmarcarOtros("radioPolitica5");
                politicaIncumplida = 5;
                descripcionPolitica = textPolit5.getText().toString();
            }
        });

        photo1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                TakePhoto();
            }
        });

        btnCloseEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutData.setVisibility(View.VISIBLE);
                scrollPoliticas.setVisibility(View.VISIBLE);
                tableLayout.setVisibility(View.GONE);
            }
        });

        consultAreas(ServerName+"5sGhoner/consultarAreasPoliticas.php");
        btnHistorialPoliticas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lanzarHistorial();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.i("Clickie","btnGuardar");
                waitingSaved.setVisibility(View.GONE);
                btnGuardar.setEnabled(true);


                Nomina = editNomina.getText().toString();
                Nombre = editNombre.getText().toString();
                Comentario = editComentario.getText().toString();

                View mensajeCuadro = getLayoutInflater().inflate(R.layout.toast_verificando_foto_evidencia,(ViewGroup)findViewById(R.id.layout_toast_fotografia));
                Toast toastMensaje = new Toast(getApplicationContext());
                TextView textoTitulo =mensajeCuadro.findViewById(R.id.textView35);
                TextView mensaje =mensajeCuadro.findViewById(R.id.mensaje1);
                ImageView fondo = mensajeCuadro.findViewById(R.id.imageView25);
                toastMensaje.setDuration(Toast.LENGTH_SHORT);
                toastMensaje.setView(mensajeCuadro);
                toastMensaje.setGravity(Gravity.CENTER,0,0);

                toastMensaje.setGravity(Gravity.CENTER_HORIZONTAL,0,0);


                if (Nomina.isEmpty()) { // Verifica si Nomina es un String y si contiene solo números
                    fondo.setImageResource(R.drawable.fondo_toast_next_pruebas);
                    textoTitulo.setText("¿QUIÉN INCUMPLE?");
                    mensaje.setText("Revise el número de nómina.");
                    toastMensaje.show();
                }else if (!Nomina.matches("\\d+")) {  // Verifica que la cadena contenga solo dígitos
                    fondo.setImageResource(R.drawable.fondo_toast_next_pruebas);
                    textoTitulo.setText("¿TEXTO EN NÓMINA?");
                    mensaje.setText("Solo número en número de nómina.");
                    toastMensaje.show();
                }else if(Nombre.isEmpty()){
                    fondo.setImageResource(R.drawable.fondo_toast_next_pruebas);
                    textoTitulo.setText("¿QUIÉN INCUMPLE?");
                    mensaje.setText("Favor de colocar el nombre.");
                    toastMensaje.show();
                }else if (spinnerAreas.getSelectedItemPosition() == 0) {
                    fondo.setImageResource(R.drawable.fondo_toast_next_pruebas);
                    textoTitulo.setText("¿SIN ÁREA?");
                    mensaje.setText("Seleccione un área.");
                    toastMensaje.show();
                }else if(Comentario.isEmpty()){
                    fondo.setImageResource(R.drawable.fondo_toast_next_pruebas);
                    textoTitulo.setText("¿SIN COMENTARIO?");
                    mensaje.setText("Favor de agregar un comentario.");
                    toastMensaje.show();
                }else if(takenPhoto!=1){
                    fondo.setImageResource(R.drawable.fondo_toast_next_pruebas);
                    textoTitulo.setText("¿SIN FOTOGRAFÍA?");
                    mensaje.setText("Tomar la Evidencia.");
                    toastMensaje.show();
                } else {
                    if (radioPolitica1.isChecked() || radioPolitica2.isChecked() || radioPolitica3.isChecked() || radioPolitica4.isChecked() || radioPolitica5.isChecked()) {
                        btnGuardar.setEnabled(false);
                        waitingSaved.setVisibility(View.VISIBLE);
                        saveIncumplimiento(ServerName+"5sGhoner/guardarEvidenciaIncumplimiento.php");

                    } else {
                        fondo.setImageResource(R.drawable.fondo_toast_next_pruebas);
                        textoTitulo.setText("¿SIN POLITICA?");
                        mensaje.setText("Favor elija la politica.");
                        toastMensaje.show();
                    }
                }
            }
        });
    }


        private void startSpeechToText() {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Habla ahora...");
            try {
                startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);

            } catch (Exception e) {
                Toast toas = Toast.makeText(getApplicationContext(),"Dispositivo no soporta esta opción",Toast.LENGTH_SHORT);
                toas.show();
            }
        }

                public void saveIncumplimiento(String URL){
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            View procesando = getLayoutInflater().inflate(R.layout.toast_procesando,(ViewGroup)findViewById(R.id.layout_toast_procesando));
                            ImageView fondo = procesando.findViewById(R.id.imageView25);
                            Toast toasprocesando =  new Toast(Politicas.this);
                            TextView textoTitulo =procesando.findViewById(R.id.textView35);
                            TextView mensaje =procesando.findViewById(R.id.mensaje1);
                            toasprocesando.setDuration(Toast.LENGTH_SHORT);
                            toasprocesando.setView(procesando);
                            toasprocesando.setGravity(Gravity.CENTER,0,0);
                            Log.i("Respuesta OK: ",response);

                            try {
                                JSONArray jsonArray = new JSONArray(response);

                                if(jsonArray.getBoolean(0)){//si es true entrara si es false no
                                    btnGuardar.setEnabled(true);
                                    waitingSaved.setVisibility(View.GONE);
                                    fondo.setImageResource(R.drawable.fondo_toast_procesando);
                                    textoTitulo.setText("EVIDENCIA GUARDADA!!");
                                    mensaje.setText("Se guardado correctamente");
                                    toasprocesando.show();
                                    lanzarHistorial();
                                   /* if (jsonArray.length() > 2) {
                                        try {
                                            int valor = jsonArray.getInt(2);
                                            if (valor > 0) {
                                                idHallazgo = valor;
                                            } else {
                                                idHallazgo = 0;
                                            }
                                        } catch (JSONException e) {
                                            idHallazgo = 0;
                                        }
                                    } else {
                                        idHallazgo = 0;
                                    }*/
                                }else{
                                    btnGuardar.setEnabled(true);
                                    waitingSaved.setVisibility(View.GONE);
                                    fondo.setImageResource(R.drawable.fondo_toast_next_pruebas);
                                    textoTitulo.setText("INTENTELO NUEVAMENTE..");
                                    mensaje.setText("Tenemos problemas para guardar.");
                                    toasprocesando.show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                btnGuardar.setEnabled(true);
                                waitingSaved.setVisibility(View.GONE);
                                fondo.setImageResource(R.drawable.fondo_toast_next_pruebas);
                                textoTitulo.setText("ALGO NO ESTA BIEN..");
                                mensaje.setText("JSON no es correcto.");
                                toasprocesando.show();
                                Log.e("Error JSON", "Error al parsear JSON: " + e.getMessage());
                            }

                        }
                    }, new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            btnGuardar.setEnabled(true);
                            waitingSaved.setVisibility(View.GONE);
                            View procesando = getLayoutInflater().inflate(R.layout.toast_procesando,(ViewGroup)findViewById(R.id.layout_toast_procesando));
                            ImageView fondo = procesando.findViewById(R.id.imageView25);
                            Toast toasprocesando =  new Toast(Politicas.this);
                            TextView textoTitulo =procesando.findViewById(R.id.textView35);
                            TextView mensaje =procesando.findViewById(R.id.mensaje1);
                            fondo.setImageResource(R.drawable.fondo_toast_next_pruebas);
                            textoTitulo.setText("ALGO SALIO MAL..");
                            mensaje.setText("Verifique su conexión, intente nuevamente.");
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                          Map<String,String> parametros = new HashMap<String,String>();


                            // Ejemplo: el ítem que el usuario selecciona del Spinner
                            String selectedItem = spinnerAreas.getSelectedItem().toString();  // Obtener el texto seleccionado
                            // Dividir el texto en dos partes: antes y después del paréntesis
                            String[] parts = selectedItem.split("\\(");
                            // El nombre del área es la parte antes del paréntesis
                            String nombreArea = parts[0].trim();  // Usamos trim() para quitar espacios extra
                            // El nombre de la planta es la parte dentro del paréntesis
                            String nombrePlanta = parts[1].replace(")", "").trim();  // Eliminar el paréntesis de cierre y recortar espacios
                            // Imprimir los resultados para verificar
                            Log.i("Seleccionado", "Nombre del área: " + nombreArea);
                            Log.i("Seleccionado", "Nombre de la planta: " + nombrePlanta);



                          String nomina=editNomina.getText().toString();
                          String nombre=editNombre.getText().toString();
                          String comentario = editComentario.getText().toString();
                              parametros.put("Planta",Planta);
                              parametros.put("nominaUsuario",numeroNomina);
                              parametros.put("idHallago",String.valueOf(idHallazgo));
                              parametros.put("nomina",nomina);
                              parametros.put("nombre",nombre);
                              parametros.put("numero",String.valueOf(politicaIncumplida));// Conversión a String
                              parametros.put("plantaSelected",nombrePlanta);
                              parametros.put("areaSelected",nombreArea);
                              parametros.put("descripcion",descripcionPolitica);
                              parametros.put("comentario",comentario);

                          String imageData = imageToString(bitmap1);
                          parametros.put("imagen",imageData);
                          return parametros;
                        }
                    };
                    requestQueue= Volley.newRequestQueue(this);
                    requestQueue.add(stringRequest);
                }


            public void saveNewArea(String URL){
                final String plantaSeleccionada = spinnerPlantas.getSelectedItem().toString();
                final String nuevaArea=editTextNewArea.getText().toString();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Respuesta nueva area: ",response);

                            if(response.equals("true")){//si es true entrara si es false no
                                mostrarToastProcesando("¡¡SE AGREGÓ CON ÉXITO!!", "Registro realizado.",1);
                                layout_crear_area.setVisibility(View.GONE);
                                ListAreas.add(nuevaArea + " (" + plantaSeleccionada + ")");
                            }else{
                                mostrarToastProcesando("NO SE RECUPERARÓN PLANTAS..", "Intentelo nuevamente.",0);
                            }
                    }
                    }, new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            mostrarToastProcesando("ALGO SALIO MAL","Verifique su conexión, intente nuevamente.",0);
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> parametros = new HashMap<String,String>();

                            parametros.put("planta",Planta);
                            parametros.put("plantaSeleccionada",plantaSeleccionada);
                            parametros.put("nuevaArea",nuevaArea);
                            return parametros;
                        }
                    };
                    requestQueue= Volley.newRequestQueue(this);
                    requestQueue.add(stringRequest);
            }


            public void consultarPlantas(String URL){
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Respuesta plantas: ",response);
                        try {
                            JSONArray respuestArreglo = new JSONArray(response);
                            if(respuestArreglo.length() > 0){//si es true entrara si es false no
                                List<String> plantaList = new ArrayList<>();
                                for (int i = 0; i < respuestArreglo.length(); i++) {
                                    JSONObject jsonObject = respuestArreglo.getJSONObject(i);
                                    String nombrePlanta = jsonObject.getString("Planta");
                                    if(!nombrePlanta.equals("PlantaGe") && !nombrePlanta.equals("ENERYA-RIASA")){//para que no aparezca en la lista
                                        plantaList.add(nombrePlanta);
                                    }
                                }
                                plantaList.add(0, "Seleccione planta...");
                                // Crear y configurar el ArrayAdapter
                                configurarSpinnerPlantilla(plantaList, spinnerPlantas);
                            }else{
                                mostrarToastProcesando("NO SE RECUPERARÓN PLANTAS..", "Intentelo nuevamente.",0);
                                layout_crear_area.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            mostrarToastProcesando("INTENTELO NUEVAMENTE..", "Tenemos problemas la consultar plantas.",0);
                            Log.e("Error JSON", "Error en área al parsear JSON: " + e.getMessage());
                            layout_crear_area.setVisibility(View.GONE);
                        }

                    }
                }, new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        btnGuardar.setEnabled(true);
                        waitingSaved.setVisibility(View.GONE);
                        mostrarToastProcesando("ALGO SALIO MAL","Verifique su conexión, intente nuevamente.",0);
                        layout_crear_area.setVisibility(View.GONE);
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> parametros = new HashMap<String,String>();
                        parametros.put("planta",Planta);
                        return parametros;
                    }
                };
                requestQueue= Volley.newRequestQueue(this);
                requestQueue.add(stringRequest);
            }

            public void configurarSpinnerPlantilla(List<String> listaDinamica, Spinner spinnerDinamico) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        this, // o Politicas.this si estás dentro de una clase interna
                        android.R.layout.simple_spinner_dropdown_item,
                        listaDinamica
                ) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        TextView textView = (TextView) view;
                        int colorAccent = getResources().getColor(R.color.colorAccent);
                        textView.setTextSize(12);
                        textView.setTextColor(colorAccent);
                        return view;
                    }

                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        View view = super.getDropDownView(position, convertView, parent);
                        TextView textView = (TextView) view;
                        textView.setTextSize(12);
                        textView.setPadding(50, 0, 0, 0);
                        return view;
                    }
                };
                // Asignar el adapter al Spinner
                spinnerDinamico.setAdapter(adapter);
            }


            private void mostrarToastProcesando(String titulo, String mensaje,int estado) {
                // Inflar la vista personalizada
                View procesando = getLayoutInflater().inflate(R.layout.toast_procesando, (ViewGroup) findViewById(R.id.layout_toast_procesando));

                // Obtener las vistas dentro del layout del Toast
                ImageView fondo = procesando.findViewById(R.id.imageView25);

                TextView textoTitulo = procesando.findViewById(R.id.textView35);
                TextView mensajeView = procesando.findViewById(R.id.mensaje1);

                fondo.setImageResource(estado == 1 ? R.drawable.fondo_toast_procesando : R.drawable.fondo_toast_next_pruebas);

                // Crear el Toast
                Toast toasprocesando = new Toast(Politicas.this);
                toasprocesando.setDuration(Toast.LENGTH_SHORT);
                toasprocesando.setView(procesando);
                toasprocesando.setGravity(Gravity.CENTER, 0, 0);

                // Establecer el título y mensaje dinámicamente
                textoTitulo.setText(titulo);
                mensajeView.setText(mensaje);

                // Mostrar el Toast
                toasprocesando.show();
            }


            public void consultAreas(String URL){
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        View procesando = getLayoutInflater().inflate(R.layout.toast_procesando,(ViewGroup)findViewById(R.id.layout_toast_procesando));
                        ImageView fondo = procesando.findViewById(R.id.imageView25);

                        TextView textoTitulo =procesando.findViewById(R.id.textView35);
                        TextView mensaje =procesando.findViewById(R.id.mensaje1);
                        Toast toasprocesando =  new Toast(Politicas.this);
                        toasprocesando.setDuration(Toast.LENGTH_SHORT);
                        toasprocesando.setView(procesando);
                        toasprocesando.setGravity(Gravity.CENTER,0,0);
                        Log.i("Respuesta áreas OK: ",response);

                        try {
                            JSONObject responseObject = new JSONObject(response);
                            boolean success = responseObject.getBoolean("success");
                            if(success){//si es true entrara si es false no
                                JSONArray respuestArreglo = responseObject.getJSONArray("data");
                                ListAreas = new ArrayList<>();
                                // Recorrer el JSONArray para obtener los datos y agregarlos al ArrayList
                                for (int i = 0; i < respuestArreglo.length(); i++) {
                                    JSONObject jsonObject = respuestArreglo.getJSONObject(i);
                                    String nombrePlanta = jsonObject.getString("planta");
                                    String nombreArea = jsonObject.getString("area");


                                    // Usar StringBuilder para la concatenación eficiente
                                    if(!nombrePlanta.equals("PlantaGe")){
                                        ListAreas.add(nombreArea + " (" + nombrePlanta + ")");
                                    }
                                }
                                // Insertar la opción inicial al principio de la lista
                                ListAreas.add(0, "Seleccione área...");
                                // Crear y configurar el ArrayAdapter
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                                        Politicas.this,
                                        android.R.layout.simple_spinner_dropdown_item, // Estilo desplegado
                                        ListAreas
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
                                // Establecer el adapter al spinner
                                spinnerAreas.setAdapter(adapter);


                            }else{
                                fondo.setImageResource(R.drawable.fondo_toast_next_pruebas);
                                textoTitulo.setText("NO SE RECUPERARÓN ÁREAS..");
                                mensaje.setText("Intentelo nuevamente.");
                                toasprocesando.show();
                                VolverMenuInicial();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            fondo.setImageResource(R.drawable.fondo_toast_next_pruebas);
                            textoTitulo.setText("INTENTELO NUEVAMENTE..");
                            mensaje.setText("Tenemos problemas la consultar áreas.");
                            toasprocesando.show();
                            Log.e("Error JSON", "Error en área al parsear JSON: " + e.getMessage());
                            VolverMenuInicial();
                        }

                    }
                }, new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        btnGuardar.setEnabled(true);
                        waitingSaved.setVisibility(View.GONE);
                        View procesando = getLayoutInflater().inflate(R.layout.toast_procesando,(ViewGroup)findViewById(R.id.layout_toast_procesando));
                        ImageView fondo = procesando.findViewById(R.id.imageView25);
                        Toast toasprocesando =  new Toast(Politicas.this);
                        TextView textoTitulo =procesando.findViewById(R.id.textView35);
                        TextView mensaje =procesando.findViewById(R.id.mensaje1);
                        fondo.setImageResource(R.drawable.fondo_toast_next_pruebas);
                        textoTitulo.setText("ALGO SALIO MAL..");
                        mensaje.setText("Verifique su conexión, intente nuevamente.");
                        VolverMenuInicial();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> parametros = new HashMap<String,String>();
                        parametros.put("planta",Planta);
                        return parametros;
                    }
                };
                requestQueue= Volley.newRequestQueue(this);
                requestQueue.add(stringRequest);
            }

                // Método para desmarcar los demás RadioButtons
                private void desmarcarOtros(String selected) {

                    // Verifica cuál RadioButton está seleccionado y desmarca los demás
                    if (!selected.equals("radioPolitica1")) {
                        radioPolitica1.setChecked(false);
                        // Cambiar el ícono a gris si no está seleccionado
                        iconNumero1.setImageResource(R.drawable.ic_number_one); // Imagen gris
                        textPolit1.setTextColor(ContextCompat.getColor(this,R.color.gris));
                    } else {
                        // Cambiar el ícono a rojo si está seleccionado
                        iconNumero1.setImageResource(R.drawable.ic_number_one_activado); // Imagen roja
                        textPolit1.setTextColor(ContextCompat.getColor(this,R.color.rojobajito));

                    }

                    if (!selected.equals("radioPolitica2")) {
                        radioPolitica2.setChecked(false);
                        // Cambiar el ícono a gris si no está seleccionado
                        iconNumero2.setImageResource(R.drawable.ic_number_two); // Imagen gris
                        textPolit2.setTextColor(ContextCompat.getColor(this,R.color.gris));
                    } else {
                        // Cambiar el ícono a rojo si está seleccionado
                        iconNumero2.setImageResource(R.drawable.ic_number_two_activado); // Imagen roja
                        textPolit2.setTextColor(ContextCompat.getColor(this,R.color.rojobajito));
                    }

                    if (!selected.equals("radioPolitica3")) {
                        radioPolitica3.setChecked(false);
                        // Cambiar el ícono a gris si no está seleccionado
                        iconNumero3.setImageResource(R.drawable.ic_number_three); // Imagen gris
                        textPolit3.setTextColor(ContextCompat.getColor(this,R.color.gris));
                    } else {
                        // Cambiar el ícono a rojo si está seleccionado
                        iconNumero3.setImageResource(R.drawable.ic_number_three_activado); // Imagen roja
                        textPolit3.setTextColor(ContextCompat.getColor(this,R.color.rojobajito));
                    }

                    if (!selected.equals("radioPolitica4")) {
                        radioPolitica4.setChecked(false);
                        // Cambiar el ícono a gris si no está seleccionado
                        iconNumero4.setImageResource(R.drawable.ic_number_four); // Imagen gris
                        textPolit4.setTextColor(ContextCompat.getColor(this,R.color.gris));
                    } else {
                        // Cambiar el ícono a rojo si está seleccionado
                        iconNumero4.setImageResource(R.drawable.ic_number_four_activado); // Imagen roja
                        textPolit4.setTextColor(ContextCompat.getColor(this,R.color.rojobajito));
                    }

                    if (!selected.equals("radioPolitica5")) {
                        radioPolitica5.setChecked(false);
                        // Cambiar el ícono a gris si no está seleccionado
                        iconNumero5.setImageResource(R.drawable.ic_number_five); // Imagen gris
                        textPolit5.setTextColor(ContextCompat.getColor(this,R.color.gris));
                    } else {
                        // Cambiar el ícono a rojo si está seleccionado
                        iconNumero5.setImageResource(R.drawable.ic_number_five_activado); // Imagen roja
                        textPolit5.setTextColor(ContextCompat.getColor(this,R.color.rojobajito));

                    }
                }

                private String imageToString(Bitmap bitmap)
                {
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG,20, outputStream);//peso
                    byte[] imageBytes= outputStream.toByteArray();
                    String encodeImage= Base64.encodeToString(imageBytes,Base64.DEFAULT);
                    return encodeImage;
                }

                public void TakePhoto()
                {
                    String fileName="photo";
                    File StorageDirectory= getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                    try {
                        File imageFile=File.createTempFile(fileName,".jpg",StorageDirectory);
                        currentPhotoPath=imageFile.getAbsolutePath();
                        Uri imageUri=  FileProvider.getUriForFile(this,
                        "com.example.a5SGonher.fileprovider",imageFile);

                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                        startActivityForResult(intent,1);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
                    super.onActivityResult(requestCode, resultCode, data);

                    if (requestCode == REQUEST_CODE_SPEECH_INPUT && resultCode == RESULT_OK && data != null) {
                        ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                        if (result != null && !result.isEmpty()) {
                            String spokenText = result.get(0);
                            editComentario.setText(spokenText);
                        }
                    }

                    if (requestCode == 1 && resultCode == RESULT_OK) {
                        takenPhoto = 1;
                        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);

                        //Orientando fotografia
                        Matrix matrix = new Matrix();
                        matrix.postRotate(90);
                        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                        int newWidth = 1000;
                        int newHeight = 1000;;
                        Bitmap resizedBitmap = Bitmap.createScaledBitmap(rotatedBitmap, newWidth, newHeight, false);
                        photo1.setImageBitmap(resizedBitmap);

                        Bitmap bitmapt = ((BitmapDrawable)photo1.getDrawable()).getBitmap();
                        ViewGroup.LayoutParams param = drawView.getLayoutParams();
                        param.width = 1000;
                        param.height = 1000;

                        drawView.setBitmap(bitmapt);
                        bitmap1 = drawView.getBitmap();
                        ViewGroup.LayoutParams params = photo1.getLayoutParams();

                        params.width = 300;
                        params.height = 300;
                        photo1.setImageBitmap(bitmap1);
                        layoutData.setVisibility(View.GONE);
                        scrollPoliticas.setVisibility(View.GONE);
                        tableLayout.setVisibility(View.VISIBLE);
                     }
                }

                public void VolverMenuInicial(){
                    Intent intent = new Intent(this,MainMenu.class);
                    startActivity(intent);
                }
                public void lanzarHistorial(){
                    Intent intent = new Intent(this,HistorialPoliticasInclumplidas.class);
                    startActivity(intent);
                }

}