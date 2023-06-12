package com.example.a5SGonher;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
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

public class ShowOnlyImageHallazgos extends AppCompatActivity{
    RequestQueue requestQueue;
    private ImageView imageview,imageview2,imageview3,imageview4;
    private String  GlobalAyudaVisual,subarea,Anterior,Pregunta,Pregunta2;
    private int NumeroImagenes=0;
    String ServerName, Planta;
    TextView tView;
    String NombrePregunta;
    AlertDialog.Builder builder;
    String Url1;
    Button btnCompartir1, btnCompartir2, btnCompartir3, btnCompartir4;

    private static final String TAG = "Touch";
    @SuppressWarnings("unused")
    private static final float MIN_ZOOM = 1f,MAX_ZOOM = 1f;

    // These matrices will be used to scale points of the image
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();

    // The 3 states (events) which the user is trying to perform
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;

    // these PointF objects are used to record the point(s) the user is touching
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;

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
        btnCompartir1 = findViewById(R.id.btnCompartir1);
        btnCompartir2 = findViewById(R.id.btnCompartir2);
        btnCompartir3 = findViewById(R.id.btnCompartir3);
        btnCompartir4 = findViewById(R.id.btnCompartir4);
        ZoomImagenes zoom = new ZoomImagenes(imageview);
        zoom.setOnTouchListener(zoom);
        /*imageview.setOnTouchListener(this);
        imageview2.setOnTouchListener(this);
        imageview3.setOnTouchListener(this);
        imageview4.setOnTouchListener(this);*/


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

    /*////////////////////////////////////////////////////INICIO ZOOM*/
   /* public boolean onTouch(View v, MotionEvent event)
    {
        ImageView view = (ImageView) v;
        view.setScaleType(ImageView.ScaleType.MATRIX);
        float scale;

        dumpEvent(event);
        // Handle touch events here...

        switch (event.getAction() & MotionEvent.ACTION_MASK)
        {
            case MotionEvent.ACTION_DOWN:   // first finger down only
                matrix.set(view.getImageMatrix());
                savedMatrix.set(matrix);
                start.set(event.getX(), event.getY());
                Log.d(TAG, "mode=DRAG"); // write to LogCat
                mode = DRAG;
                break;

            case MotionEvent.ACTION_UP: // first finger lifted

            case MotionEvent.ACTION_POINTER_UP: // second finger lifted

                mode = NONE;
                Log.d(TAG, "mode=NONE");
                break;

            case MotionEvent.ACTION_POINTER_DOWN: // first and second finger down

                oldDist = spacing(event);
                Log.d(TAG, "oldDist=" + oldDist);
                if (oldDist > 5f) {
                    savedMatrix.set(matrix);
                    midPoint(mid, event);
                    mode = ZOOM;
                    Log.d(TAG, "mode=ZOOM");
                }
                break;

            case MotionEvent.ACTION_MOVE:

                if (mode == DRAG)
                {
                    matrix.set(savedMatrix);
                    matrix.postTranslate(event.getX() - start.x, event.getY() - start.y); // create the transformation in the matrix  of points
                }
                else if (mode == ZOOM)
                {
                    // pinch zooming
                    float newDist = spacing(event);
                    Log.d(TAG, "newDist=" + newDist);
                    if (newDist > 5f)
                    {
                        matrix.set(savedMatrix);
                        scale = newDist / oldDist; // setting the scaling of the
                        // matrix...if scale > 1 means
                        // zoom in...if scale < 1 means
                        // zoom out
                        matrix.postScale(scale, scale, mid.x, mid.y);
                    }
                }
                break;
        }

        view.setImageMatrix(matrix); // display the transformation on screen

        return true; // indicate event was handled
    }



    private float spacing(MotionEvent event)
    {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }


    private void midPoint(PointF point, MotionEvent event)
    {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }


    private void dumpEvent(MotionEvent event)
    {
        String names[] = { "DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE","POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?" };
        StringBuilder sb = new StringBuilder();
        int action = event.getAction();
        int actionCode = action & MotionEvent.ACTION_MASK;
        sb.append("event ACTION_").append(names[actionCode]);

        if (actionCode == MotionEvent.ACTION_POINTER_DOWN || actionCode == MotionEvent.ACTION_POINTER_UP)
        {
            sb.append("(pid ").append(action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
            sb.append(")");
        }

        sb.append("[");
        for (int i = 0; i < event.getPointerCount(); i++)
        {
            sb.append("#").append(i);
            sb.append("(pid ").append(event.getPointerId(i));
            sb.append(")=").append((int) event.getX(i));
            sb.append(",").append((int) event.getY(i));
            if (i + 1 < event.getPointerCount())
                sb.append(";");
        }

        sb.append("]");
        Log.d("Touch Events ---------", sb.toString());
    }*/

    ///////////////////////////////////////////////////////////////////*FIN ZOOM*/

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

                    /*LinearLayout layouts = findViewById(R.id.LayoutImaganesHallazgos);// los creare desde el layout para que primero aparezcan los botones.
                    Button btnCompartir1 = new Button(ShowOnlyImageHallazgos.this);
                    btnCompartir1.setText("Compartir Hallázgo");
                    LinearLayout.LayoutParams parametos = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                    parametos.setMargins(0,5,0,5);
                    parametos.gravity = Gravity.CENTER;
                    btnCompartir1.setLayoutParams(parametos);
                    btnCompartir1.setBackgroundResource(R.drawable.boton_compartir);
                    btnCompartir1.setTextColor(Color.WHITE);
                    layouts.addView(btnCompartir1);*/

                    btnCompartir1.setVisibility(View.VISIBLE);


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

                        btnCompartir1.setVisibility(View.VISIBLE);
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

                        btnCompartir2.setVisibility(View.VISIBLE);
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
                    final Bitmap bitmap = drawable.getBitmap();

                    btnCompartir1.setVisibility(View.VISIBLE);
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
                    BitmapDrawable drawable = (BitmapDrawable) imageview2.getDrawable();

                    final Bitmap bitmap = drawable.getBitmap();
                    btnCompartir2.setVisibility(View.VISIBLE);
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

            ImageRequest imageRequest3= new ImageRequest("https://vvnorth.com/5sGhoner/FotosAuditorias/"+Anterior+"/"+GlobalAyudaVisual+"/"+Foto+"/3.jpeg", new Response.Listener<Bitmap>() {
                //ImageRequest imageRequest= new ImageRequest("https://vvnorth.com/5sGhoner/subareas/"+subarea+"/"+GlobalAyudaVisual+".jpg", new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    imageview3.setImageBitmap(response);
                    final int num= 3;
                    BitmapDrawable drawable = (BitmapDrawable) imageview3.getDrawable();

                    final Bitmap bitmap = drawable.getBitmap();
                    btnCompartir3.setVisibility(View.VISIBLE);
                    btnCompartir3.setOnClickListener(new View.OnClickListener() {
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
                    final Bitmap bitmap = drawable.getBitmap();
                    btnCompartir1.setVisibility(View.VISIBLE);
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
                    BitmapDrawable drawable = (BitmapDrawable) imageview2.getDrawable();
                    final Bitmap bitmap = drawable.getBitmap();
                    btnCompartir2.setVisibility(View.VISIBLE);
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

            ImageRequest imageRequest3= new ImageRequest("https://vvnorth.com/5sGhoner/FotosAuditorias/"+Anterior+"/"+GlobalAyudaVisual+"/"+Foto+"/3.jpeg", new Response.Listener<Bitmap>() {
                //ImageRequest imageRequest= new ImageRequest("https://vvnorth.com/5sGhoner/subareas/"+subarea+"/"+GlobalAyudaVisual+".jpg", new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    imageview3.setImageBitmap(response);
                    final int num= 3;
                    BitmapDrawable drawable = (BitmapDrawable) imageview3.getDrawable();

                    final Bitmap bitmap = drawable.getBitmap();
                    btnCompartir3.setVisibility(View.VISIBLE);
                    btnCompartir3.setOnClickListener(new View.OnClickListener() {
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
            requestQueue.add(imageRequest3);

            ImageRequest imageRequest4= new ImageRequest("https://vvnorth.com/5sGhoner/FotosAuditorias/"+Anterior+"/"+GlobalAyudaVisual+"/"+Foto+"/4.jpeg", new Response.Listener<Bitmap>() {
                //ImageRequest imageRequest= new ImageRequest("https://vvnorth.com/5sGhoner/subareas/"+subarea+"/"+GlobalAyudaVisual+".jpg", new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    imageview4.setImageBitmap(response);
                    final int num= 4;
                    BitmapDrawable drawable = (BitmapDrawable) imageview4.getDrawable();
                    final Bitmap bitmap = drawable.getBitmap();
                    btnCompartir4.setVisibility(View.VISIBLE);
                    btnCompartir4.setOnClickListener(new View.OnClickListener() {
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