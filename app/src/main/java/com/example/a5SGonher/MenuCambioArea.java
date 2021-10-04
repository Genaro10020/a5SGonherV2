package com.example.a5SGonher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

public class MenuCambioArea extends AppCompatActivity implements ExampleDialog.ExampleDialogListener {
    RequestQueue requestQueue;
    String DeleteThis;
    EditText editT;
    // Button myButton3 = new Button(this);
    TextView tView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_cambio_planta);
        // editT=(EditText)findViewById(R.id.editT);
        final String nombrePlanta = getIntent().getStringExtra("EXTRA_SESSION_ID");
        tView=(TextView)findViewById(R.id.textView4);

        tView.setText("Cambiar Area");
        ////////////////////////////////////////////////////////////////



        buscarProducto("https://vvnorth.com/buscar_area.php?Planta="+nombrePlanta +"",nombrePlanta);

        //////////////////////////////
    }

    public void Cambiar(final String nombreArea,final String nombrePlanta)
    {
        Intent intent =new Intent(this,CambiarArea.class);
        intent.putExtra("EXTRA_SESSION_ID", nombrePlanta);
        intent.putExtra("EXTRA_SESSION_ID2", nombreArea);
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

    public void boton(final String nombreBoton, final String nombrePlanta)
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

                //   DeleteThis=nombreBoton;
                //  OpenDialog();
                Cambiar(nombreBoton,nombrePlanta);
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

    private void buscarProducto(String URL,final String nombrePlanta)
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
                        nombre=jsonObject.getString("NombreArea");
                        boton(nombre,nombrePlanta);


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


        // ejecutarservicio("https://vvnorth.com/eliminar_planta.php",DeleteThis);

        //Cambiar();

    }

}
