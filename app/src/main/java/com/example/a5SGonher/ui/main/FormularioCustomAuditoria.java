package com.example.a5SGonher.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.a5SGonher.CustomAuditoria;
import com.example.a5SGonher.R;

import java.util.HashMap;
import java.util.Map;

public class FormularioCustomAuditoria extends AppCompatActivity {
    TextView tView;
    Button btnAgregarS;
    EditText edtRquermiento,edtDatos;
    RadioGroup radioGroup;
    RadioButton radioButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_custom_auditoria);
        tView=(TextView)findViewById(R.id.textViewSS);
        edtRquermiento=(EditText)findViewById(R.id.requText);
        edtDatos=(EditText)findViewById(R.id.datosText);
        btnAgregarS=(Button) findViewById(R.id.botonSS);
        final String nombreAuditoria = getIntent().getStringExtra("EXTRA_SESSION_ID");

        radioGroup=findViewById(R.id.radioGroup);
        tView.setText(nombreAuditoria);
        btnAgregarS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ejecutarservicio("https://vvnorth.com/insertar_AuditoriaInformation.php",nombreAuditoria);
                openActivity(nombreAuditoria);



            }
        });
    }
    public void checkButton (View v)
    {
        int radioId=radioGroup.getCheckedRadioButtonId();
        radioButton= findViewById(radioId);

        Toast.makeText(this,radioButton.getText(),Toast.LENGTH_SHORT).show();

    }
    public void openActivity(String nombrePlanta)
    {
        Intent intent =new Intent(this, CustomAuditoria.class);
        intent.putExtra("EXTRA_SESSION_ID", nombrePlanta);


        startActivity(intent);
    }

    private void ejecutarservicio(String URL,final String nAuditoria)
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
                int radioId=radioGroup.getCheckedRadioButtonId();
                radioButton= findViewById(radioId);
                String strI = String.valueOf(radioButton.getText());

               parametros.put("ss",edtRquermiento.getText().toString());
                parametros.put("datos",edtDatos.getText().toString());
                parametros.put("NombrePlanta",nAuditoria);

               parametros.put("NombreAreaSS",strI);




                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
