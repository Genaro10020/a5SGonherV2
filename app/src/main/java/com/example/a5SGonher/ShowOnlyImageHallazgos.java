package com.example.a5SGonher;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ShowOnlyImageHallazgos extends AppCompatActivity {
    RequestQueue requestQueue;
    private ImageView imageview,imageview2,imageview3,imageview4;
    private String  GlobalAyudaVisual,subarea,Anterior,Pregunta,Pregunta2;
    private int NumeroImagenes=0;
    String ServerName, Planta;
    TextView tView;
    String NombrePregunta;
    AlertDialog.Builder builder;
    String Url1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_only_image_hallazgos);
        GlobalClass globalClass =(GlobalClass)getApplicationContext();
        tView=(TextView)findViewById(R.id.textView9);
        ServerName=globalClass.getName();
        imageview= findViewById(R.id.imageViewOnlyImage);
        imageview2= findViewById(R.id.imageViewOnlyImage2);
        imageview3= findViewById(R.id.imageViewOnlyImage3);
        imageview4= findViewById(R.id.imageViewOnlyImage4);

        SharedPreferences preferences=getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        Planta = preferences.getString("Planta","No existe la planta");

       // GlobalAyudaVisual = getIntent().getStringExtra("EXTRA_SESSION_ID");
       // subarea = getIntent().getStringExtra("EXTRA_SESSION_ID2");
        Anterior = getIntent().getStringExtra("EXTRA_SESSION_ID3");
        NombrePregunta = getIntent().getStringExtra("EXTRA_SESSION_ID4");
        String CodigoAyuda = getIntent().getStringExtra("EXTRA_SESSION_ID5");
      // Toast.makeText(getApplicationContext(),Pregunta2, Toast.LENGTH_SHORT).show();
        GlobalAyudaVisual = getIntent().getStringExtra("EXTRA_SESSION_ID");
        subarea = getIntent().getStringExtra("EXTRA_SESSION_ID2");
        TextView tituto_tool = (TextView)findViewById(R.id.titulo_toolbar);
        tituto_tool.setText("Prueba");


        buscarProducto(ServerName+"/5sGhoner/buscar_ComentarioAnterior.php?AuditoriaAnterior="+Anterior +"&NombrePregunta="+ NombrePregunta+"&CodigoAyuda="+ GlobalAyudaVisual+"&Planta="+Planta);

    }
    public static String removeLastChar(String str) {
        return removeLastChars(str, 1);
    }

    public static String removeLastChars(String str, int chars) {
        return str.substring(0, str.length() - chars);
    }





    private void compartir(int num, Bitmap bitmap){//aparece la las opciones de compartir


        // Guarda la imagen en el almacenamiento externo
        String filename = GlobalAyudaVisual+" hallazgo "+num+".jpg";
        File imagePath = new File(getExternalCacheDir(), filename);
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Crea un URI para el archivo de imagen
        Uri imageUri = FileProvider.getUriForFile(ShowOnlyImageHallazgos.this, "com.example.a5SGonher.fileprovider", imagePath);

        // Crea un intent para compartir la imagen
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/jpeg");
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        startActivity(Intent.createChooser(shareIntent, "Share Image"));

    }

    private void requestImage() {
        Log.i("Cantidad Imagenes",""+NumeroImagenes);
        RequestQueue requestQueue= Volley.newRequestQueue(this);
       String noLastChar= removeLastChar(NombrePregunta);
       // String noLastChar=  Anterior.substring(0, Anterior.length() - 1);
       // Toast.makeText(getApplicationContext(),noLastChar,Toast.LENGTH_SHORT).show();

        final String Foto=noLastChar+"%3f";

        //Log.e("showIMG",""+"https://vvnorth.com/5sGhoner/FotosAuditorias/"+Anterior+"/"+GlobalAyudaVisual+"/"+Foto+"/1.jpeg");

        //MOSTRANDO IMAGEN 1
        if (NumeroImagenes ==1 ){
            ImageRequest imageRequest= new ImageRequest("https://vvnorth.com/5sGhoner/FotosAuditorias/"+Anterior+"/"+GlobalAyudaVisual+"/"+Foto+"/1.jpeg", new Response.Listener<Bitmap>() {

                @Override
                public void onResponse(Bitmap response) {

                    final int num= 1;
                    // Obtén la imagen del ImageView

                    LinearLayout layouts = findViewById(R.id.LayoutImaganesHallazgos);// los creare desde el layout para que primero aparezcan los botones.
                    Button btnCompartir1 = new Button(ShowOnlyImageHallazgos.this);
                    btnCompartir1.setText("Compartir Hallázgo");
                    LinearLayout.LayoutParams parametos = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                    parametos.setMargins(0,5,0,5);
                    parametos.gravity = Gravity.CENTER;
                    btnCompartir1.setLayoutParams(parametos);
                    btnCompartir1.setBackgroundResource(R.drawable.boton_compartir);
                    btnCompartir1.setTextColor(Color.WHITE);
                    layouts.addView(btnCompartir1);


                    imageview.setImageBitmap(response);
                    BitmapDrawable drawable = (BitmapDrawable) imageview.getDrawable();
                    final Bitmap bitmap = drawable.getBitmap();

                    btnCompartir1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            compartir(num,bitmap);
                        }
                    });




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
            imageview2.setVisibility(View.GONE);
            imageview3.setVisibility(View.GONE);
            imageview4.setVisibility(View.GONE);

        }else if(NumeroImagenes== 2) {
            final LinearLayout layouts = findViewById(R.id.LayoutImaganesHallazgos);
            Log.e("Numero de imagenes",":"+NumeroImagenes);
                ImageRequest imageRequest= new ImageRequest("https://vvnorth.com/5sGhoner/FotosAuditorias/"+Anterior+"/"+GlobalAyudaVisual+"/"+Foto+"/1.jpeg", new Response.Listener<Bitmap>() {
                    //ImageRequest imageRequest= new ImageRequest("https://vvnorth.com/5sGhoner/subareas/"+subarea+"/"+GlobalAyudaVisual+".jpg", new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        imageview.setImageBitmap(response);
                        final int num= 1;
                        // Obtén la imagen del ImageView
                        BitmapDrawable drawable = (BitmapDrawable) imageview.getDrawable();
                        final Bitmap bitmap = drawable.getBitmap();


                        Button btnCompartir1 = new Button(ShowOnlyImageHallazgos.this);
                        btnCompartir1.setText("Compartir Hallázgo");
                        LinearLayout.LayoutParams parametos = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                        parametos.setMargins(0,5,0,5);
                        parametos.gravity = Gravity.CENTER;
                        btnCompartir1.setLayoutParams(parametos);
                        btnCompartir1.setBackgroundResource(R.drawable.boton_compartir);
                        btnCompartir1.setTextColor(Color.WHITE);
                        layouts.addView(btnCompartir1);

                        btnCompartir1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                compartir(num,bitmap);
                            }
                        });

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

                ImageRequest imageRequest2= new ImageRequest("https://vvnorth.com/5sGhoner/FotosAuditorias/"+Anterior+"/"+GlobalAyudaVisual+"/"+Foto+"/2.jpeg", new Response.Listener<Bitmap>() {
                    //ImageRequest imageRequest= new ImageRequest("https://vvnorth.com/5sGhoner/subareas/"+subarea+"/"+GlobalAyudaVisual+".jpg", new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        imageview2.setImageBitmap(response);
                        final int num= 2;
                        // Obtén la imagen del ImageView
                        BitmapDrawable drawable = (BitmapDrawable) imageview2.getDrawable();
                        final Bitmap bitmap = drawable.getBitmap();
                        Button btnCompartir2 = new Button(ShowOnlyImageHallazgos.this);
                        btnCompartir2.setText("Compartir Hallázgo");
                        LinearLayout.LayoutParams parametos = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                        parametos.setMargins(0,5,0,5);
                        parametos.gravity = Gravity.CENTER;
                        btnCompartir2.setLayoutParams(parametos);
                        btnCompartir2.setBackgroundResource(R.drawable.boton_compartir);
                        btnCompartir2.setTextColor(Color.WHITE);
                        layouts.addView(btnCompartir2);

                        btnCompartir2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                compartir(num,bitmap);
                            }
                        });
                    }
                }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  requestImage(subareaTemportal,nombresNS,"1"  );
                        //  numeroFoto=1;
                        return;
                    }
                });
                requestQueue.add(imageRequest2);
            imageview3.setVisibility(View.GONE);
            imageview4.setVisibility(View.GONE);


        }else if(NumeroImagenes == 3){
            ImageRequest imageRequest= new ImageRequest("https://vvnorth.com/5sGhoner/FotosAuditorias/"+Anterior+"/"+GlobalAyudaVisual+"/"+Foto+"/1.jpeg", new Response.Listener<Bitmap>() {
                //ImageRequest imageRequest= new ImageRequest("https://vvnorth.com/5sGhoner/subareas/"+subarea+"/"+GlobalAyudaVisual+".jpg", new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    imageview.setImageBitmap(response);
                    final int num= 1;
                    BitmapDrawable drawable = (BitmapDrawable) imageview.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                   // crearBotonCompartir(num, bitmap);
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

            ImageRequest imageRequest2= new ImageRequest("https://vvnorth.com/5sGhoner/FotosAuditorias/"+Anterior+"/"+GlobalAyudaVisual+"/"+Foto+"/2.jpeg", new Response.Listener<Bitmap>() {
                //ImageRequest imageRequest= new ImageRequest("https://vvnorth.com/5sGhoner/subareas/"+subarea+"/"+GlobalAyudaVisual+".jpg", new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    imageview2.setImageBitmap(response);
                    final int num= 2;
                    BitmapDrawable drawable = (BitmapDrawable) imageview2.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                   // crearBotonCompartir(num, bitmap);
                }
            }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //  requestImage(subareaTemportal,nombresNS,"1"  );
                    //  numeroFoto=1;
                    return;
                }
            });
            requestQueue.add(imageRequest2);

            ImageRequest imageRequest3= new ImageRequest("https://vvnorth.com/5sGhoner/FotosAuditorias/"+Anterior+"/"+GlobalAyudaVisual+"/"+Foto+"/3.jpeg", new Response.Listener<Bitmap>() {
                //ImageRequest imageRequest= new ImageRequest("https://vvnorth.com/5sGhoner/subareas/"+subarea+"/"+GlobalAyudaVisual+".jpg", new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    imageview3.setImageBitmap(response);
                    final int num= 3;
                    BitmapDrawable drawable = (BitmapDrawable) imageview3.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                    //crearBotonCompartir(num, bitmap);
                }
            }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //  requestImage(subareaTemportal,nombresNS,"1"  );
                    //  numeroFoto=1;
                    return;
                }
            });
            requestQueue.add(imageRequest3);


            imageview4.setVisibility(View.GONE);

        }else if(NumeroImagenes == 4){

            ImageRequest imageRequest= new ImageRequest("https://vvnorth.com/5sGhoner/FotosAuditorias/"+Anterior+"/"+GlobalAyudaVisual+"/"+Foto+"/1.jpeg", new Response.Listener<Bitmap>() {
                //ImageRequest imageRequest= new ImageRequest("https://vvnorth.com/5sGhoner/subareas/"+subarea+"/"+GlobalAyudaVisual+".jpg", new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    imageview.setImageBitmap(response);
                    final int num= 1;
                    BitmapDrawable drawable = (BitmapDrawable) imageview.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                   // crearBotonCompartir(num, bitmap);
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

            ImageRequest imageRequest2= new ImageRequest("https://vvnorth.com/5sGhoner/FotosAuditorias/"+Anterior+"/"+GlobalAyudaVisual+"/"+Foto+"/2.jpeg", new Response.Listener<Bitmap>() {
                //ImageRequest imageRequest= new ImageRequest("https://vvnorth.com/5sGhoner/subareas/"+subarea+"/"+GlobalAyudaVisual+".jpg", new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    imageview2.setImageBitmap(response);
                    final int num= 2;
                    BitmapDrawable drawable = (BitmapDrawable) imageview2.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                  //  crearBotonCompartir(num, bitmap);
                }
            }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //  requestImage(subareaTemportal,nombresNS,"1"  );
                    //  numeroFoto=1;
                    return;
                }
            });
            requestQueue.add(imageRequest2);

            ImageRequest imageRequest3= new ImageRequest("https://vvnorth.com/5sGhoner/FotosAuditorias/"+Anterior+"/"+GlobalAyudaVisual+"/"+Foto+"/3.jpeg", new Response.Listener<Bitmap>() {
                //ImageRequest imageRequest= new ImageRequest("https://vvnorth.com/5sGhoner/subareas/"+subarea+"/"+GlobalAyudaVisual+".jpg", new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    imageview3.setImageBitmap(response);
                    final int num= 3;
                    BitmapDrawable drawable = (BitmapDrawable) imageview3.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                    //crearBotonCompartir(num, bitmap);
                }
            }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //  requestImage(subareaTemportal,nombresNS,"1"  );
                    //  numeroFoto=1;
                    return;
                }
            });
            requestQueue.add(imageRequest3);

            ImageRequest imageRequest4= new ImageRequest("https://vvnorth.com/5sGhoner/FotosAuditorias/"+Anterior+"/"+GlobalAyudaVisual+"/"+Foto+"/4.jpeg", new Response.Listener<Bitmap>() {
                //ImageRequest imageRequest= new ImageRequest("https://vvnorth.com/5sGhoner/subareas/"+subarea+"/"+GlobalAyudaVisual+".jpg", new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    imageview4.setImageBitmap(response);
                    final int num= 4;
                    BitmapDrawable drawable = (BitmapDrawable) imageview4.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                   // crearBotonCompartir(num, bitmap);
                }
            }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //  requestImage(subareaTemportal,nombresNS,"1"  );
                    //  numeroFoto=1;
                    return;
                }
            });
            requestQueue.add(imageRequest4);
        }else{

            imageview2.setVisibility(View.GONE);
            imageview3.setVisibility(View.GONE);
            imageview4.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(),"No existe imagen por mostrar.",Toast.LENGTH_LONG).show();
        }
    }

    private void buscarProducto(String URL)
    {

        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        String nombre, cantidadImagenes;
                        jsonObject = response.getJSONObject(i);
                        // editT.setText(jsonObject.getString("Planta"));
                        nombre=jsonObject.getString("DescripcionError");
                        cantidadImagenes =jsonObject.getString("NumeroImagenes");
                        if(cantidadImagenes.equals("") || cantidadImagenes.equals(null)){
                            cantidadImagenes = "0";
                        }
                        Log.e(cantidadImagenes,":"+cantidadImagenes);
                        NumeroImagenes = Integer.parseInt(cantidadImagenes);
                     //   boton(nombre,i);

                      //  Toast.makeText(getApplicationContext(), nombre, Toast.LENGTH_SHORT).show();

                        tView.setText(nombre);
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                requestImage();
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