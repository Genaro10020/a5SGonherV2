package com.example.a5SGonher;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class SelectCambioPlanta extends AppCompatActivity implements ExampleDialog.ExampleDialogListener {
    RequestQueue requestQueue;
    String DeleteThis;
    EditText editT;
    String ServerName;
    // Button myButton3 = new Button(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        GlobalClass globalClass =(GlobalClass)getApplicationContext();
        ServerName=globalClass.getName();
        setContentView(R.layout.activity_eliminar_planta);
        // editT=(EditText)findViewById(R.id.editT);


        ////////////////////////////////////////////////////////////////



        buscarProducto(ServerName+"/buscar_empresa.php");

        //////////////////////////////
    }

    public void Eliminar()
    {
        Intent intent =new Intent(this,AgregarPlanta.class);
        startActivity(intent);
    }

    private void ejecutarservicio(String URL,final String nombreBoton)
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
                // parametros.put("NombrePlanta",edtEmpresa.getText().toString());
                parametros.put("NombrePlanta",nombreBoton);
                //parametros.put("NMail",edtMail.getText().toString());

                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void boton(final String nombreBoton, int numeroEmpresa)
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

              //  ejecutarservicio("https://vvnorth.com/eliminar_planta.php",nombreBoton);

              //  Eliminar();

                DeleteThis=nombreBoton;
                OpenDialog();

            }
        });
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

    private void buscarProducto(String URL)
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
    public void OpenDialog( )
    {

        ExampleDialog dialog = new ExampleDialog();
        dialog.show(getSupportFragmentManager(),"example dialog");

    }


    @Override
    public void onYesClicked() {


          ejecutarservicio(ServerName+"/eliminar_planta.php",DeleteThis);

         Eliminar();

    }

}
