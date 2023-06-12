package com.example.a5SGonher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

public class ShowOnlyImage extends AppCompatActivity {

    private ImageView imageview;
    private String  GlobalAyudaVisual,subarea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_only_image);

        imageview= findViewById(R.id.imageViewOnlyImage);
        GlobalAyudaVisual = getIntent().getStringExtra("EXTRA_SESSION_ID");
        subarea = getIntent().getStringExtra("EXTRA_SESSION_ID2");
        requestImage();
        ZoomImagenes zoom = new ZoomImagenes(imageview);
        zoom.setOnTouchListener(zoom);
    }


    private void requestImage() {

        RequestQueue requestQueue= Volley.newRequestQueue(this);

        //Recupero Informacion Genaro
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String planta=preferences.getString("Planta","No existe Planta");
        String url="";
        if (planta.equals("Enerya")){
            url= "https://vvnorth.com/5sGhoner/subareas/";
        }else if(planta.equals("Filtros")){
            url= "https://vvnorth.com/5sGhoner/subareasFiltros/";
        }

        ImageRequest imageRequest= new ImageRequest(url+subarea+"/"+GlobalAyudaVisual+".jpg", new Response.Listener<Bitmap>() {
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




}