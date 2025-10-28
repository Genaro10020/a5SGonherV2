package com.example.a5SGonher;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

public class ShowOnlyImagePolicies extends AppCompatActivity {

    private ImageView imageview;
    private String Planta,idCumplimiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_only_image_policies);

        SharedPreferences preferences=getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        Planta= preferences.getString("Planta","No existe Planta");

        idCumplimiento = getIntent().getStringExtra("idCumplimiento");
        imageview = findViewById(R.id.imageViewOnlyImage);

        requestImage();
        ZoomImagenes zoom = new ZoomImagenes(imageview);
        zoom.setOnTouchListener(zoom);
    }


    private void requestImage() {

        RequestQueue requestQueue= Volley.newRequestQueue(this);

        //Recupero Informacion
        String url="";
        if (Planta.equals("Enerya")){
            url= "https://vvnorth.com/5sGhoner/FotosPoliticasInclumplidas/Enerya/";
        }else if(Planta.equals("Filtros")){
            url= "https://vvnorth.com/5sGhoner/FotosPoliticasInclumplidas/Filtros/";
        }else if(Planta.equals("Gonhermex")){
            url= "https://vvnorth.com/5sGhoner/FotosPoliticasInclumplidas/Gonhermex/";
        }

        ImageRequest imageRequest= new ImageRequest(url+idCumplimiento+"/1.jpeg", new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {

                imageview.setImageBitmap(response);

            }
        }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  requestImage(subareaTemportal,nombresNS,"1"  );
                //  numeroFoto=1;
                return ;

            }
        });
        requestQueue.add(imageRequest);
    }

}