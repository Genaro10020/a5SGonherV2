package com.example.a5SGonher;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
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

public class DownloadImage extends AppCompatActivity {

    private ImageView imageview,imageViewLeft,imageViewRight;
    int numeroFoto=0;
    String subareaTemportal;
    TextView numbers,textPrueba;
    RequestQueue requestQueue;
    String ServerName;
    int numeroImagenes=0;
    String  stringNumeroImagenes="0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_image);
        GlobalClass globalClass =(GlobalClass)getApplicationContext();
        ServerName=globalClass.getName();
        imageview= findViewById(R.id.imageView2);
        imageViewLeft=findViewById(R.id.imageViewLeft);
        imageViewRight=findViewById(R.id.imageViewRight);
        numbers=findViewById(R.id.textView25);
        textPrueba=findViewById(R.id.textPPrueba);
        final String nombresNS = getIntent().getStringExtra("EXTRA_SESSION_ID");
        final String subarea = getIntent().getStringExtra("EXTRA_SESSION_ID2");
        subareaTemportal=subarea;
       // String subarea ="mamus";
      //  String nS="waldos.jpeg";

        buscarProducto(ServerName+"/buscar_numeroFotos.php?NombreSubArea="+subarea +"&NombrePregunta="+nombresNS+"");
        requestImage(subarea,nombresNS ,"1" );


        imageViewLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(numeroImagenes!=1)
                {


                    if(numeroFoto<=0)
                    {
                        numeroFoto=numeroImagenes-1;
                    }else{ numeroFoto--;}
                    if(numeroFoto==0) { Click( subarea, nombresNS,"1"); }
                    if(numeroFoto==1) {Click( subarea, nombresNS,"2"); }
                    if(numeroFoto==2) { Click( subarea, nombresNS,"3"); }
                    if(numeroFoto==3) { Click( subarea, nombresNS,"4"); }
                    if(numeroFoto==4) { Click( subarea, nombresNS,"5"); }
                    if(numeroFoto==5) { Click( subarea, nombresNS,"6"); }
                    if(numeroFoto==6) { Click( subarea, nombresNS,"7"); }
                    if(numeroFoto==7) { Click( subarea, nombresNS,"8"); }
                    if(numeroFoto==8) { Click( subarea, nombresNS,"9"); }
                    if(numeroFoto==9) { Click( subarea, nombresNS,"10"); }
                    if(numeroFoto==10) { Click( subarea, nombresNS,"11"); }
                    if(numeroFoto==11) { Click( subarea, nombresNS,"12"); }
                    if(numeroFoto==12) { Click( subarea, nombresNS,"13"); }
                    if(numeroFoto==13) { Click( subarea, nombresNS,"14"); }
                    if(numeroFoto==14) { Click( subarea, nombresNS,"15"); }
                    if(numeroFoto==15) { Click( subarea, nombresNS,"16"); }
                    if(numeroFoto==16) { Click( subarea, nombresNS,"17"); }
                    if(numeroFoto==17) { Click( subarea, nombresNS,"18"); }
                    if(numeroFoto==18) { Click( subarea, nombresNS,"19"); }
                    if(numeroFoto==19) { Click( subarea, nombresNS,"20"); }
                    if(numeroFoto==20) { Click( subarea, nombresNS,"21"); }
                    if(numeroFoto==21) { Click( subarea, nombresNS,"22"); }
                    if(numeroFoto==22) { Click( subarea, nombresNS,"23"); }
                    if(numeroFoto==23) { Click( subarea, nombresNS,"24"); }
                    if(numeroFoto==24) { Click( subarea, nombresNS,"25"); }
                    if(numeroFoto==25) { Click( subarea, nombresNS,"26"); }
                    if(numeroFoto==26) { Click( subarea, nombresNS,"27"); }
                    if(numeroFoto==27) { Click( subarea, nombresNS,"28"); }
                    if(numeroFoto==28) { Click( subarea, nombresNS,"29"); }
                    if(numeroFoto==29) { Click( subarea, nombresNS,"30"); }


                }

              //  textPrueba.setText(String.valueOf(numeroFoto));

            }
        });

        imageViewRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(numeroImagenes!=1)
                {

                    numeroFoto++;
                    if(numeroFoto>=numeroImagenes)
                    {
                        numeroFoto=0;
                    }

                    if(numeroFoto==0) { Click( subarea, nombresNS,"1"); }
                    if(numeroFoto==1) {Click( subarea, nombresNS,"2"); }
                    if(numeroFoto==2) { Click( subarea, nombresNS,"3"); }
                    if(numeroFoto==3) { Click( subarea, nombresNS,"4"); }
                    if(numeroFoto==4) { Click( subarea, nombresNS,"5"); }
                    if(numeroFoto==5) { Click( subarea, nombresNS,"6"); }
                    if(numeroFoto==6) { Click( subarea, nombresNS,"7"); }
                    if(numeroFoto==7) { Click( subarea, nombresNS,"8"); }
                    if(numeroFoto==8) { Click( subarea, nombresNS,"9"); }
                    if(numeroFoto==9) { Click( subarea, nombresNS,"10"); }
                    if(numeroFoto==10) { Click( subarea, nombresNS,"11"); }
                    if(numeroFoto==11) { Click( subarea, nombresNS,"12"); }
                    if(numeroFoto==12) { Click( subarea, nombresNS,"13"); }
                    if(numeroFoto==13) { Click( subarea, nombresNS,"14"); }
                    if(numeroFoto==14) { Click( subarea, nombresNS,"15"); }
                    if(numeroFoto==15) { Click( subarea, nombresNS,"16"); }
                    if(numeroFoto==16) { Click( subarea, nombresNS,"17"); }
                    if(numeroFoto==17) { Click( subarea, nombresNS,"18"); }
                    if(numeroFoto==18) { Click( subarea, nombresNS,"19"); }
                    if(numeroFoto==19) { Click( subarea, nombresNS,"20"); }
                    if(numeroFoto==20) { Click( subarea, nombresNS,"21"); }
                    if(numeroFoto==21) { Click( subarea, nombresNS,"22"); }
                    if(numeroFoto==22) { Click( subarea, nombresNS,"23"); }
                    if(numeroFoto==23) { Click( subarea, nombresNS,"24"); }
                    if(numeroFoto==24) { Click( subarea, nombresNS,"25"); }
                    if(numeroFoto==25) { Click( subarea, nombresNS,"26"); }
                    if(numeroFoto==26) { Click( subarea, nombresNS,"27"); }
                    if(numeroFoto==27) { Click( subarea, nombresNS,"28"); }
                    if(numeroFoto==28) { Click( subarea, nombresNS,"29"); }
                    if(numeroFoto==29) { Click( subarea, nombresNS,"30"); }

                   // textPrueba.setText(stringNumeroImagenes);
                }

              //  textPrueba.setText(String.valueOf(numeroFoto));

            }
        });

        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

    public void Click(String subarea,String nombresNS,String numeroImagen)

    {  numbers.setText(numeroImagen+"/"+stringNumeroImagenes);
        requestImage(subarea,nombresNS,numeroImagen);

    }

    public void Click2(String subarea,String nombresNS,String numeroImagen)

    {requestImage(subarea,nombresNS,numeroImagen  );
        numbers.setText(numeroImagen+"/"+stringNumeroImagenes);
        numeroFoto++;
        if(numeroImagenes<=numeroFoto)
        {  numeroFoto=0;  }

    }


    private void buscarProducto(String URL)
    {

        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        String nFotos;
                        int myNum = 0;
                        jsonObject = response.getJSONObject(i);
                        // editT.setText(jsonObject.getString("Planta"));
                        nFotos=jsonObject.getString("NumeroFotos");

                       stringNumeroImagenes=nFotos;
                        myNum=  Integer.parseInt(nFotos);
                        numeroImagenes=myNum;
                        numbers.setText("1/"+stringNumeroImagenes);
                        if(myNum==0)
                        {numbers.setText("0/0");
                        }
                     //   if(myNum==3)
                       // { textPrueba.setText(numeroImagenes);}

                    //    boton(nombre,i);


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

    private void requestImage(final String Subarea,final String nombresNS,final String numeroImagen) {

        RequestQueue requestQueue= Volley.newRequestQueue(this);

        ImageRequest imageRequest= new ImageRequest("https://vvnorth.com//SubAreas//"+Subarea+"//"+nombresNS+"//"+numeroImagen+".jpeg", new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {

                imageview.setImageBitmap(response);

            }
        }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                requestImage(subareaTemportal,nombresNS,"1"  );
                    numeroFoto=1;
                    return;

            }
        });
        requestQueue.add(imageRequest);
    }
}
