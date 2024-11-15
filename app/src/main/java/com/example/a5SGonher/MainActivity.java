package com.example.a5SGonher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import java.nio.charset.Charset;

public class MainActivity extends AppCompatActivity {

    EditText edtNombre,edtPassword;
    Button btnAgregar2;
    RequestQueue requestQueue;
    TextView tView,tView2;
    int selectedId ;
    String planta_seleccionada;
    boolean passwordMatched = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GlobalClass globalClass =(GlobalClass)getApplicationContext();
      //  TextView name=findViewById(R.id.mensaje2);


        btnAgregar2=(Button) findViewById(R.id.btnAgregarInicio);
        edtNombre=(EditText)findViewById(R.id.NombreLog);
        edtPassword=(EditText)findViewById(R.id.ClaveLog);

        RadioGroup tipoPlanta = findViewById(R.id.tipo_planta);
        RadioButton enerya_riasa = findViewById(R.id.option_1);
        RadioButton filtros = findViewById(R.id.option_2);

        // Obtener el ID del RadioButton seleccionado por defecto
        int checkedButtonId = tipoPlanta.getCheckedRadioButtonId();

        // Obtener la referencia al RadioButton seleccionado por defecto
        RadioButton checkedRadioButton = findViewById(checkedButtonId);

        // Obtener el texto del RadioButton seleccionado por defecto
        planta_seleccionada = checkedRadioButton.getText().toString();

        if(planta_seleccionada.equals("Enerya/Riasa")){
            planta_seleccionada = "Enerya";
            Log.e("Planta Seleccionada",planta_seleccionada);
        }else if(planta_seleccionada.equals("Filtros")){
            planta_seleccionada = "Filtros";
            Log.e("Planta Seleccionada",planta_seleccionada);
        }else if(planta_seleccionada.equals("Gonhermex")){
            planta_seleccionada = "";
            Log.e("Planta Seleccionada",planta_seleccionada);
        }



        tipoPlanta.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton selectedRadioButton = findViewById(i);
                String selectedText = selectedRadioButton.getText().toString();
                if (selectedText.equals("Enerya/Riasa")){
                    planta_seleccionada = "Enerya";
                        Log.e("Planta Seleccionada",planta_seleccionada);
                } else if(selectedText.equals("Filtros")){
                    planta_seleccionada = "Filtros";
                     Log.e("Planta Seleccionada",planta_seleccionada);
                } else if(selectedText.equals("Gonhermex")){
                    planta_seleccionada = "Gonhermex";
                    Log.e("Planta Seleccionada",planta_seleccionada);
                }
            }
        });

        // name.setText(globalClass.getName());
        //   tView=(TextView)findViewById(R.id.textViewrol1);
      //  tView2=(TextView)findViewById(R.id.textViewrol2);

        //cargarPreferencias();

        //comparacion();


        btnAgregar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             buscarProducto("https://vvnorth.com/buscar_auditorClave.php?Planta="+edtNombre.getText().toString() +"&PlantaSeleccionada="+planta_seleccionada);


            }
        });



    }

    protected void onResume() {
        super.onResume();
        passwordMatched = false;
    }


    public void openActivity2()
    {
      //  Toast.makeText(getApplicationContext(),"Entrol",Toast.LENGTH_SHORT).show();

        Intent intent =new Intent(this,MainMenu.class);
        startActivity(intent);


    }

    public void openResponsable(){
        Intent intento = new Intent(this,MainResponsable.class);
        startActivity(intento);
    }

public void comparacion()
    {
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        String user = preferences.getString("User", "No existe Usuario");
        String Rol = preferences.getString("Rol", "No existe Usuario");
        Integer Conected = preferences.getInt("Conected", 0);


           /* if(Conected==1)
    {
        openActivity2();
    }*/
    }

    //Guardando informacion incial
    private void guardarPreferencias(String Rol, String nombreAuditor, String numeroNomina)
    {
        SharedPreferences preferences=getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String Usuario=edtNombre.getText().toString();
        SharedPreferences.Editor editor = preferences.edit();


        editor.putString("User", Usuario);
        editor.putString("Rol", Rol);
        editor.putString("NumeroNomina",numeroNomina);
        editor.putString("Planta",planta_seleccionada);
        editor.putString("NombreAuditor", nombreAuditor);
        editor.putInt("Conected", 1);
        editor.commit();

    }

    private void cargarPreferencias() {

        SharedPreferences preferences=getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        String user= preferences.getString("User","No existe Usuario");
        String Rol= preferences.getString("Rol","No existe Usuario");
      //  tView.setText(user);
        //tView2.setText(Rol);65799

    }



    private void buscarProducto(String URL)
    {
        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                Log.i("Respueta Verificando",""+response);

                if(response.length()>0){
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            String numeroNomina;
                            String password;
                            String rol;
                            String nombreAuditor;// Genaro

                            jsonObject = response.getJSONObject(i);
                           Log.e("LoginRespuesta",""+response);
                            // editT.setText(jsonObject.getString("Planta"));
                            nombreAuditor=jsonObject.getString("NombreAuditor");
                            password=jsonObject.getString("Password");
                            numeroNomina=jsonObject.getString("auditor");
                            rol=jsonObject.getString("Rol");

                                    if(password.equals(edtPassword.getText().toString()))
                                    {
                                        passwordMatched = true;
                                     // Toast.makeText(getApplicationContext(),nombreAuditor,Toast.LENGTH_SHORT).show();
                                        guardarPreferencias(rol,nombreAuditor,numeroNomina);
                                        openActivity2();
                                        break;

                                    }
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    if (!passwordMatched) {
                        buscarResponsable("https://vvnorth.com/buscar_responsableClave.php?Planta="+edtNombre.getText().toString() +"&PlantaSeleccionada="+planta_seleccionada);
                    }
                }else{
                    if (!passwordMatched) {
                        buscarResponsable("https://vvnorth.com/buscar_responsableClave.php?Planta="+edtNombre.getText().toString() +"&PlantaSeleccionada="+planta_seleccionada);
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Completa los campos y verifica tu conexión",Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }


    private void buscarResponsable(String URL)
    {
        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                Log.i("BuscandoResponsable",""+response);

                if(response.length()>0){
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            String numeroNomina;
                            String password;
                            String rol;
                            String nombreResponsable;// Genaro

                            jsonObject = response.getJSONObject(i);
                            Log.e("Consulta Responsable",""+response);
                            // editT.setText(jsonObject.getString("Planta"));
                            nombreResponsable=jsonObject.getString("Responsable");
                            password=jsonObject.getString("Password");
                            numeroNomina=jsonObject.getString("NumeroNomina");
                            rol="Responsable";

                            if(password.equals(edtPassword.getText().toString()))
                            {
                                passwordMatched = true;
                                //Toast.makeText(getApplicationContext(),nombreResponsable,Toast.LENGTH_SHORT).show();
                                // Toast.makeText(getApplicationContext(),nombreAuditor,Toast.LENGTH_SHORT).show();
                                guardarPreferencias(rol,nombreResponsable,numeroNomina);
                                openResponsable();
                                break;

                            }

                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    if (!passwordMatched) {
                        Dialog2 dialog = new Dialog2();
                        dialog.show(getSupportFragmentManager(),"");
                    }
                }else{
                    if (!passwordMatched) {
                        Dialog2 dialog = new Dialog2();
                        dialog.show(getSupportFragmentManager(),"");
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Completa los campos y verifica tu conexión",Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    public void onBackPressed(){
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
        finishAffinity();
    }

}
