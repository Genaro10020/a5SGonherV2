package com.example.a5SGonher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import android.content.SharedPreferences;

public class MainActivity extends AppCompatActivity {

    EditText edtNombre,edtPassword;
    Button btnAgregar2;
    RequestQueue requestQueue;
    TextView tView,tView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GlobalClass globalClass =(GlobalClass)getApplicationContext();
      //  TextView name=findViewById(R.id.mensaje2);

        btnAgregar2=(Button) findViewById(R.id.btnAgregarInicio);
        edtNombre=(EditText)findViewById(R.id.NombreLog);
        edtPassword=(EditText)findViewById(R.id.ClaveLog);

      // name.setText(globalClass.getName());
     //   tView=(TextView)findViewById(R.id.textViewrol1);

      //  tView2=(TextView)findViewById(R.id.textViewrol2);

        cargarPreferencias();

comparacion();


        btnAgregar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             buscarProducto("https://vvnorth.com/buscar_auditorClave.php?Planta="+edtNombre.getText().toString() +"");


            }
        });



    }
    public void openActivity2()
    {
      //  Toast.makeText(getApplicationContext(),"Entrol",Toast.LENGTH_SHORT).show();

        Intent intent =new Intent(this,MainMenu.class);
        startActivity(intent);


    }

public void comparacion()

    {
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        String user = preferences.getString("User", "No existe Usuario");
        String Rol = preferences.getString("Rol", "No existe Usuario");
        Integer Conected = preferences.getInt("Conected", 0);


            if(Conected==1)
    {
        openActivity2();
    }
    }

    private void guardarPreferencias(String Rol)
    {
        SharedPreferences preferences=getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String Usuario=edtNombre.getText().toString();
        SharedPreferences.Editor editor = preferences.edit();


        editor.putString("User", Usuario);
        editor.putString("Rol", Rol);
        editor.putInt("Conected", 1);
        editor.commit();

    }

    private void cargarPreferencias() {

        SharedPreferences preferences=getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        String user= preferences.getString("User","No existe Usuario");
        String Rol= preferences.getString("Rol","No existe Usuario");
      //  tView.setText(user);
        //tView2.setText(Rol);

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
                        String password;
                        String rol;

                        jsonObject = response.getJSONObject(i);
                        // editT.setText(jsonObject.getString("Planta"));
                        nombre=jsonObject.getString("auditor");

                        password=jsonObject.getString("Password");
                        rol=jsonObject.getString("Rol");


                                if(password.equals(edtPassword.getText().toString()))
                                {

                                 //   Toast.makeText(getApplicationContext(),"Entro",Toast.LENGTH_SHORT).show();
                                    guardarPreferencias(rol);
                                    openActivity2();

                                }
                                else{
                                    Dialog2 dialog = new Dialog2();
                                    dialog.show(getSupportFragmentManager(),"example dialog");

                                }



                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Completa los campos y verifica tu coneccion",Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }


}
