package com.example.a5SGonher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

public class AgregarSAuditoriaL extends AppCompatActivity implements ExampleDialog2.ExampleDialogListener {

    RequestQueue requestQueue;
    EditText editT;
    // Button myButton3 = new Button(this);
    String control;

    String  gnombreBoton;
    String   gnumeroEmpresa;
    String gnombrePlanta;
    String       gnombreArea;
    String gnombreSubarea;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final String nombrePlanta = getIntent().getStringExtra("EXTRA_SESSION_ID");
        final String nombreArea = getIntent().getStringExtra("EXTRA_SESSION_ID2");
        final String nombresubArea = getIntent().getStringExtra("EXTRA_SESSION_ID3");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_s_auditoria_l);
        // editT=(EditText)findViewById(R.id.editT);






        buscarProducto("https://vvnorth.com/buscar_sOriginal.php" ,nombrePlanta,nombreArea,nombresubArea);

        //////////////////////////////
    }

    public void OpenDialog( )
    {

        ExampleDialog2 dialog = new ExampleDialog2();
        dialog.show(getSupportFragmentManager(),"example dialog");

    }


    @Override
    public void onYesClicked() {

        ejecutarservicio("https://vvnorth.com/ConveritirAuditoria.php",gnombreBoton,gnombrePlanta,gnombreArea,gnombreSubarea);
        agregarS(gnombrePlanta,gnombreArea,gnombreSubarea);

    }
    public void agregarS(String nombrePlanta,String nArea,String nSubarea)
    {
        Intent intent =new Intent(this,AgregarS.class);
        intent.putExtra("EXTRA_SESSION_ID2", nArea);
        intent.putExtra("EXTRA_SESSION_ID", nombrePlanta);
        intent.putExtra("EXTRA_SESSION_ID3", nSubarea);
        startActivity(intent);
    }


    public void boton(final String nombreBoton, int numeroEmpresa,final String nombrePlanta,final String nombreArea ,final String nombreSubarea)
    {
        Button myButton3 = new Button(this);

        myButton3.setText(nombreBoton);

        LinearLayout ll3 = (LinearLayout)findViewById(R.id.layoutSPlanta);
        LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll3.addView(myButton3, lp3);

        /////////////////////////
        myButton3.setBackgroundColor(Color.rgb(128, 42, 42));
        myButton3.setTextColor(Color.rgb(179, 179, 179));
        lp3.setMargins(0, 0, 0, 10);
        myButton3.setLayoutParams( lp3);
        /////////////////////////////////////////
        myButton3.setOnClickListener(new View.OnClickListener()  {
            // String NomPlanta =nFinal;
            public void onClick(View view) {

              //  control=nombreBoton;
         gnombreBoton=       nombreBoton;

          gnombrePlanta  =    nombrePlanta;
              gnombreArea     =     nombreArea;
             gnombreSubarea  = nombreSubarea;
                OpenDialog( );
            }
        });
    }

    private void ejecutarservicio(String URL,final String nombreBoton,final String nombrePlanta,final String nombreArea ,final String nombreSubarea)
    {
        StringRequest stringRequest=new  StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "OPERACION EXITOSA", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros =new HashMap<String,String>();
                // int radioId=radioGroup.getCheckedRadioButtonId();
                //  radioButton= findViewById(radioId);
                // String strI = String.valueOf(radioButton.getText());

                parametros.put("datos",nombreBoton);

                parametros.put("nombrePlanta",nombrePlanta);
                parametros.put("nombreArea",nombreArea);
                parametros.put("nombreSubarea",nombreSubarea);



                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    public void agregarArea(String nombrePlanta)
    {
        Intent intent =new Intent(this,AgregarArea.class);
        intent.putExtra("EXTRA_SESSION_ID", nombrePlanta);
        startActivity(intent);
    }

    private void buscarProducto(String URL,final String nombrePlanta,final String nombreArea,final String SubArea)
    {

        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        String nombre;
                        jsonObject = response.getJSONObject(i);
                        // editT.setText(jsonObject.getString("Planta"));
                        nombre=jsonObject.getString("Auditoria");
                        boton(nombre,i,nombrePlanta,nombreArea,SubArea);



                    } catch (JSONException e) {
                        //    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //   Toast.makeText(getApplicationContext(),"ERRO DE CONEXION",Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
}
