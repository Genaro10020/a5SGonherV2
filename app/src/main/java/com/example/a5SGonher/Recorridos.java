package com.example.a5SGonher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;
import static android.widget.Toast.makeText;


public class Recorridos extends AppCompatActivity {
    String ServerName;
    String Planta,User,NumeroNomina,Rol;
    RequestQueue requestQueue;
    Button btnCrearRecorrido;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GlobalClass globalClass =(GlobalClass)getApplicationContext();
        ServerName=globalClass.getName();

        setContentView(R.layout.activity_recorridos);

        SharedPreferences preferences=getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        User= preferences.getString("User","No existe Usuario");
        NumeroNomina = preferences.getString("NumeroNomina","No existe Número Nómina");
        Rol= preferences.getString("Rol","No existe Usuario");
        Planta = preferences.getString("Planta","No existe Planta");

        TextView titulo = (TextView)findViewById(R.id.titulo_toolbar);
        titulo.setText("Recorridos");
        buscarAuditorDeRecorrido(ServerName+"5sGhoner/consultarRecorrido.php");
        btnCrearRecorrido = (Button)findViewById(R.id.botonCrearRecorrido);

        btnCrearRecorrido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnCrearRecorrido.setBackgroundResource(R.drawable.boton_verde);
                intenCrearRecorrido();
            }
        });
        ///Log.e("Servidor",ServerName+"/")

    }

    private void buscarAuditorDeRecorrido(String URL) {
        // Limpiar la tabla
        TableLayout tabla = (TableLayout) findViewById(R.id.tablaRecorridos);
        tabla.removeAllViews();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Recorridos Existentes",response);
                        JSONObject jsonObject = null;
                        String fecha = "",nombre_recorrido="", objetivo="", codigo="",creadoPor="",id_recorrido="",cerrado="";
                        try {
                            JSONArray respuestArreglo = new JSONArray(response);

                            for (int i = 0; i < respuestArreglo.length(); i++) {
                                jsonObject = respuestArreglo.getJSONObject(i);
                                id_recorrido = jsonObject.getString("id");
                                creadoPor = jsonObject.getString("auditor");
                                codigo = jsonObject.getString("codigo");
                                nombre_recorrido = jsonObject.getString("nombre_recorrido");
                                objetivo = jsonObject.getString("objetivo");
                                fecha = jsonObject.getString("fecha_creacion");
                                cerrado = jsonObject.getString("cerrado");

                                //Log.e("","\n"+nombre_recorrido+"\n"+fecha);
                                crearBoton(id_recorrido,codigo,creadoPor,nombre_recorrido,objetivo,fecha,cerrado);
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
                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void crearBoton(final String id_recorrido, final String codigo, final String creadoPor, String nombre_recorrido, String objetivo, String fecha, final String cerrado){

        Button miBotonNombre = new Button(this);
        Button miBotonEnviarReporte = new Button(this);

       miBotonNombre.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               intentHallazgosRecorrido(id_recorrido,codigo,creadoPor);
           }
       });
        String texto = "<font color='#863828'><b>Nombre recorrido:</b> <font/><br>"+nombre_recorrido+
                "<br><font color='#863828'><b>Fecha: <font/></b> "+fecha;/*+"<br><br><font color='#863828'><b>Objetivo:</b> <font/><br>"+objetivo*/
        miBotonNombre.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);

        miBotonNombre.setText(Html.fromHtml(texto));

        //.setBackgroundResource(R.drawable.send);
        miBotonEnviarReporte.setText(fecha);
        if(cerrado.equals("Si")){
            miBotonEnviarReporte.setText("Cerrado");
        }else{
            miBotonEnviarReporte.setText("Enviar Correos");
        }



        // Obtenemos el TableLayout
        TableLayout tabla = (TableLayout) findViewById(R.id.tablaRecorridos);


        // Creamos un TableRow
        TableRow fila = new TableRow(this);
        LinearLayout linea = new LinearLayout(this);
        LinearLayout.LayoutParams lineaParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 5);
        linea.setLayoutParams(lineaParams);
        linea.setBackgroundResource(R.drawable.sombra);

        TableLayout.LayoutParams filaParams = new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        filaParams.setMargins(0,0,0,0);
        fila.setLayoutParams(filaParams);
        fila.setGravity(Gravity.CENTER_VERTICAL);
        // Establecemos el fondo de la fila
        //fila.setBackgroundResource(R.drawable.lista_recorridos);

       /* float density = getResources().getDisplayMetrics().density; // Obtener la densidad de la pantalla
        int widthInDp = 80; // Ancho en dp
        int widthInPx = (int) (widthInDp * density); // Convertir a píxeles*/

        // Establecemos el peso de cada columna
        TableRow.LayoutParams paramsNombre = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        TableRow.LayoutParams paramsFecha = new TableRow.LayoutParams(320, ViewGroup.LayoutParams.MATCH_PARENT);
        paramsFecha.height = TableRow.LayoutParams.MATCH_PARENT;

        miBotonNombre.setLayoutParams(paramsNombre);
        miBotonEnviarReporte.setLayoutParams(paramsFecha);
        //miBotonEnviarReporte.setPadding(50, 10, 10, 50);
        miBotonNombre.setGravity(Gravity.START|Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
        //miBotonEnviarReporte.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);--
        miBotonNombre.setTypeface(null, Typeface.NORMAL);

        //miBotonEnviarReporte.setTypeface(null, Typeface.NORMAL);--
        //miBotonNombre.setBackgroundResource(R.drawable.boton_recorridos);
        //miBotonEnviarReporte.setBackgroundResource(R.drawable.boton_recorridos);
        // Establecemos el ancho fijo para el botón fecha
        //paramsFecha.width = 400; // ejemplo, 100dp

        /*miBotonNombre.setLines(4);
        miBotonNombre.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        miBotonEnviarReporte.setLines(1);
        miBotonEnviarReporte.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);

        //Cuando el texto es muy grande agregara ...
        miBotonNombre.setEllipsize(TextUtils.TruncateAt.END);*/
        miBotonNombre.setAllCaps(false);
        //miBotonNombre.setPadding(25, 0, 40, 0);

        // Agregamos los botones a la fila con sus respectivos pesos

        fila.addView(miBotonNombre);
        fila.addView(miBotonEnviarReporte);
        // Ajustamos el alto de ambos botones para que sean iguales

        miBotonEnviarReporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    //Log.e("Cerrado","ID:"+id_recorrido+"Cerrado:"+cerrado);
                if(!cerrado.equals("Si")){
                    enviarCorreoResponsablesMasivo(ServerName+"5sGhoner/enviarCorreoMasivoHallazgosOpex.php",id_recorrido,creadoPor);
                }else{
                    View mensajeCuadro = getLayoutInflater().inflate(R.layout.toast_verificando_foto_evidencia,(ViewGroup)findViewById(R.id.layout_toast_fotografia));
                    Toast toastMensaje = new Toast(getApplicationContext());
                    TextView textoTitulo =mensajeCuadro.findViewById(R.id.textView35);
                    TextView mensaje =mensajeCuadro.findViewById(R.id.mensaje1);
                    textoTitulo.setText("YA ESTA CERRADA!!");
                    mensaje.setText("Correos se enviarón con anterioridad.");
                    toastMensaje.setDuration(Toast.LENGTH_SHORT);
                    toastMensaje.setView(mensajeCuadro);
                    toastMensaje.setGravity(Gravity.CENTER,0,0);
                    toastMensaje.show();
                    toastMensaje.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
                    toastMensaje.show();
                }

            }
        });

        // Agregamos la fila a la tabla
        tabla.addView(fila);
        tabla.addView(linea);
    }


    private void enviarCorreoResponsablesMasivo(String URL, final String id_recorrido, final String creadoPor) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Respuesta","Envio de correos: "+response);
                        if(response.equals("Todos enviados")){
                            View procesando = getLayoutInflater().inflate(R.layout.toast_procesando,(ViewGroup)findViewById(R.id.layout_toast_procesando));
                            Toast toasprocesando =  new Toast(Recorridos.this);
                            TextView textoTitulo =procesando.findViewById(R.id.textView35);
                            TextView mensaje =procesando.findViewById(R.id.mensaje1);
                            textoTitulo.setText("TODOS LOS CORREOS SE ENVIARON!!");
                            mensaje.setText("El recorrido cerrado");
                            toasprocesando.setDuration(Toast.LENGTH_SHORT);
                            toasprocesando.setView(procesando);
                            toasprocesando.setGravity(Gravity.CENTER,0,0);
                            toasprocesando.show();

                        }else{
                            View mensajeCuadro = getLayoutInflater().inflate(R.layout.toast_verificando_foto_evidencia,(ViewGroup)findViewById(R.id.layout_toast_fotografia));
                            Toast toastMensaje = new Toast(getApplicationContext());
                            TextView textoTitulo =mensajeCuadro.findViewById(R.id.textView35);
                            TextView mensaje =mensajeCuadro.findViewById(R.id.mensaje1);
                            textoTitulo.setText("NO SE ENVIARON LOS CORRES!!");
                            mensaje.setText("Solo intente una vez más.");
                            toastMensaje.setDuration(Toast.LENGTH_SHORT);
                            toastMensaje.setView(mensajeCuadro);
                            toastMensaje.setGravity(Gravity.CENTER,0,0);
                            toastMensaje.show();
                            toastMensaje.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
                            toastMensaje.show();
                        }
                        intentoRegresandoAquiMismo();

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
                parametros.put("Planta",Planta);
                parametros.put("ID_Recorrido", id_recorrido);
                parametros.put("CreadoPor",creadoPor);
                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void intentHallazgosRecorrido(String id_recorrido, String codigo,String creadoPor){
        Intent intent = new Intent(this,HallazgosRecorridos.class);
        intent.putExtra("ID_recorrido",id_recorrido);
        intent.putExtra("Codigo",codigo);
        intent.putExtra("creadoPor",creadoPor);
        startActivity(intent);
    }

    private boolean isReturningFromFormularioNuevoRecorrido = false;

    public void intenCrearRecorrido(){
        isReturningFromFormularioNuevoRecorrido = true;
        Intent intent = new Intent(this,FormularioNuevoRecorrido.class);
        startActivity(intent);
    }

    protected void onResume() {
        super.onResume();
            btnCrearRecorrido.setBackgroundResource(R.drawable.boton_crear);
            if (isReturningFromFormularioNuevoRecorrido) {
                buscarAuditorDeRecorrido(ServerName+"5sGhoner/consultarRecorrido.php");
            }
        }
    public void onBackPressed(){
        Intent intent = new Intent(Recorridos.this,MainMenu.class);
        startActivity(intent);
    }

    public void intentoRegresandoAquiMismo(){
        Intent intent = new Intent(this,Recorridos.class);
        startActivity(intent);
    }


}