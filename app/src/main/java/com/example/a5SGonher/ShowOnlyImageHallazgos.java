package com.example.a5SGonher;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ShowOnlyImageHallazgos extends AppCompatActivity {
    RequestQueue requestQueue;
    private ImageView imageview;
    private String  GlobalAyudaVisual,subarea,Anterior,Pregunta,Pregunta2;
    String ServerName;
    TextView tView;
    String NombrePregunta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_only_image_hallazgos);
        GlobalClass globalClass =(GlobalClass)getApplicationContext();
        tView=(TextView)findViewById(R.id.textView9);
        ServerName=globalClass.getName();
        imageview= findViewById(R.id.imageViewOnlyImage);
       // GlobalAyudaVisual = getIntent().getStringExtra("EXTRA_SESSION_ID");
       // subarea = getIntent().getStringExtra("EXTRA_SESSION_ID2");
        Anterior = getIntent().getStringExtra("EXTRA_SESSION_ID3");
        NombrePregunta = getIntent().getStringExtra("EXTRA_SESSION_ID4");
        String CodigoAyuda = getIntent().getStringExtra("EXTRA_SESSION_ID5");
      // Toast.makeText(getApplicationContext(),Pregunta2, Toast.LENGTH_SHORT).show();
        GlobalAyudaVisual = getIntent().getStringExtra("EXTRA_SESSION_ID");
        subarea = getIntent().getStringExtra("EXTRA_SESSION_ID2");

        requestImage();
        buscarProducto(ServerName+"/5sGhoner/buscar_ComentarioAnterior.php?AuditoriaAnterior="+Anterior +"&NombrePregunta="+ NombrePregunta+"&CodigoAyuda="+ GlobalAyudaVisual+"" );
    }
    public static String removeLastChar(String str) {
        return removeLastChars(str, 1);
    }

    public static String removeLastChars(String str, int chars) {
        return str.substring(0, str.length() - chars);
    }

    private void requestImage() {

        RequestQueue requestQueue= Volley.newRequestQueue(this);
       String noLastChar= removeLastChar(NombrePregunta);
       // String noLastChar=  Anterior.substring(0, Anterior.length() - 1);
       // Toast.makeText(getApplicationContext(),noLastChar,Toast.LENGTH_SHORT).show();

        String Foto=noLastChar+"%3f";
        ImageRequest imageRequest= new ImageRequest("https://vvnorth.com/5sGhoner/FotosAuditorias/"+Anterior+"/"+GlobalAyudaVisual+"/"+Foto+"/1.jpeg", new Response.Listener<Bitmap>() {
      //  ImageRequest imageRequest= new ImageRequest("https://vvnorth.com/5sGhoner/subareas/"+subarea+"/"+GlobalAyudaVisual+".jpg", new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {

                imageview.setImageBitmap(response);

            }
        }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  requestImage(subareaTemportal,nombresNS,"1"  );
                //  numeroFoto=1;
                return;

            }
        });
        requestQueue.add(imageRequest);
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
                        nombre=jsonObject.getString("DescripcionError");
                     //   boton(nombre,i);

                      //  Toast.makeText(getApplicationContext(), nombre, Toast.LENGTH_SHORT).show();

                        tView.setText(nombre);
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