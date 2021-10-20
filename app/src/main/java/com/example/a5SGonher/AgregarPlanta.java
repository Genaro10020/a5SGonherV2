package com.example.a5SGonher;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Type;

public class AgregarPlanta extends AppCompatActivity {

    RequestQueue requestQueue;
    EditText editT;
    TextView titulo, leyandaplanta, porcentaje;
    String ServerName;
   // Button myButton3 = new Button(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_planta);

        titulo=(TextView)findViewById(R.id.titulo_toolbar);
        titulo.setText("Crear Auditoría");

        porcentaje =(TextView)findViewById(R.id.textView8);
        porcentaje.setVisibility(View.GONE);

        GlobalClass globalClass =(GlobalClass)getApplicationContext();
        ServerName=globalClass.getName();
        leyandaplanta = (TextView)findViewById(R.id.textViewPlanta);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////




        buscarProducto(ServerName+"/buscar_empresa.php");

        //////////////////////////////
    }

    public void Eliminar()
    {
        Intent intent =new Intent(this,EliminarPlanta.class);
        startActivity(intent);
    }
    public void Cambiar()
    {
        Intent intent =new Intent(this,MenuCambioPlanta.class);
        startActivity(intent);
    }



    //Nota esta linea se requier para que funcione la fuente personalizada llamada fuenteitems,
    //hay otra en metodo buscarProducto puede eliminar la lineas y la fuente fuenteitems.
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void boton(final String nombreBoton, int numeroEmpresa)
    {
        Button myButton3 = new Button(this);
        myButton3.setText(nombreBoton);
        LinearLayout ll3 = (LinearLayout)findViewById(R.id.layoutSPlanta);
        LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll3.addView(myButton3, lp3);

        /////////////////////////
        Typeface fuenteitems = getResources().getFont(R.font.mitre);
        myButton3.setTypeface(fuenteitems);
        myButton3.setBackgroundResource(R.drawable.nuevos_botones_listado);
        //myButton3.setBackgroundColor(Color.rgb(150, 0, 0));
        myButton3.setTextColor(Color.rgb(236, 236, 236));
        lp3.setMargins(0, 0, 0, 50);
        myButton3.setLayoutParams( lp3);

        /////////////////////////////////////////
        myButton3.setOnClickListener(new View.OnClickListener()  {
          // String NomPlanta =nFinal;
            public void onClick(View view) {

               agregarArea(nombreBoton);

            }
        });
    }

    private Context getContext() {
        return null;
    }

    public void formularioPlanta()
    {
        Intent intent =new Intent(this,FormularioPlanta.class);
        startActivity(intent);
    }

    public void agregarArea(String nombrePlanta)
    {
        Intent intent =new Intent(this,AgregarArea.class);
        intent.putExtra("EXTRA_SESSION_ID", nombrePlanta);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Intent intent =new Intent(this,MainMenu.class);
        startActivity(intent);
    }

    private void buscarProducto(String URL)
    {
        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        String nombre;
                        jsonObject = response.getJSONObject(i);
                       // editT.setText(jsonObject.getString("Planta"));
                        nombre=jsonObject.getString("Planta");
                        boton(nombre,i);


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
