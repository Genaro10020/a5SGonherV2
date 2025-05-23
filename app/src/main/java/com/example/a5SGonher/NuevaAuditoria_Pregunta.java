package com.example.a5SGonher;

import static com.example.a5SGonher.ShowOnlyImageHallazgos.removeLastChars;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class NuevaAuditoria_Pregunta extends AppCompatActivity{
    private static final int REQUEST_CODE_SPEECH_INPUT = 100;
    RequestQueue requestQueue;
    String [] guardandorespuestas  = new String[6];
    String Planta,nombrePregunta,numeroAuditoria,nombreAyuda,numeroPregunta,numeroActual,cantidadRealPreguntas,numeroAnteriorAuditoria,textoHallazgo, cantidaddeimagenes;
    Button BotonTerminar,btn_lienzo,limpiar;
    TextView Pregunta,textofotouno,textofotodos,textofototres,textofotocuatro,mensaje_lienzo;
    EditText Razon;
    Bitmap bitmapf,bitmapf2,bitmapf3,bitmapf4,bitmapf5,bitmapf6;
    DrawView drawView;
    ImageView icono_liezo, escuchar_voz;
    TextView espereguardando;
    int numberPhoto=0,fotografiaTomada=0,fotografiasVisualizadas=0,cantidadimg=0,fotonumero=0,clickenfotoUno=0,clickenfotoDos=0,clickenfotoTres=0,clickenfotoCuatro=0,
            tomadafotoUno=0,tomadafotoDos=0,tomadafotoTres=0,tomadafotoCuatro=0, fotoVista1=0, fotoVista2=0, fotoVista3=0,fotoVista4=0;
    private ImageView imageView1,imageView2,imageView3,imageView4,imageView5,imageView6;
    private String currentPhotoPath;

    private static final int IMAGE_PICK_CODE=1000;
    private  static  final int PERMISSION_CODE=1001;
    private static final int REQUEST_IMAGE_GALLERY = 10002;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //fotografiaTomada: Me sirve para saber que se tomo por lo menos una fotografia si no para que me arroje mensaje
        //fotografiasVisualizadas: me sirve para si no existen fotografias tomadas debe de existir alguna visualizada si no pasa y mensaje.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_auditoria__pregunta);
        TextView titulobarra = (TextView)findViewById(R.id.titulo_toolbar);
        espereguardando = (TextView)findViewById(R.id.espereguardando);

        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        Planta = preferences.getString("Planta","No existe Número de Planta");

        titulobarra.setText("Evidencia");
        drawView = (DrawView)findViewById(R.id.EspacioCanva);
        nombrePregunta = getIntent().getStringExtra("EXTRA_SESSION_ID");
        numeroAuditoria = getIntent().getStringExtra("EXTRA_SESSION_ID2");
        nombreAyuda = getIntent().getStringExtra("EXTRA_SESSION_ID3");
        numeroPregunta = getIntent().getStringExtra("EXTRA_SESSION_ID4");
        numeroActual = getIntent().getStringExtra("EXTRA_SESSION_ID5");
        numeroAnteriorAuditoria = getIntent().getStringExtra("EXTRA_SESSION_ID6");
        textoHallazgo = getIntent().getStringExtra("EXTRA_SESSION_ID7");
        cantidaddeimagenes = getIntent().getStringExtra("CANTIDAD_IMAGENES");
        escuchar_voz = (ImageView)findViewById(R.id.img_record);
        BotonTerminar=(Button) findViewById(R.id.Button_Contestar);
        Pregunta=(TextView) findViewById(R.id.textView_Pregunta);
        Razon=(EditText) findViewById(R.id.editTextTextMultiLine);
        mensaje_lienzo = (TextView)findViewById(R.id.mensaje_lienzo);
        imageView1=(ImageView)findViewById(R.id.imageView1P);
         imageView2=(ImageView)findViewById(R.id.imageView1P2);
         imageView3=(ImageView)findViewById(R.id.imageView1P3);
         imageView4=(ImageView)findViewById(R.id.imageView1P4);
         //imageView5=(ImageView)findViewById(R.id.imageView1P5);
         //imageView6=(ImageView)findViewById(R.id.imageView1P6);
        textofotouno =(TextView) findViewById(R.id.textophoto1);
        textofotodos =(TextView)findViewById(R.id.textophoto2);
        textofototres=(TextView)findViewById(R.id.textophoto3);
        textofotocuatro=(TextView)findViewById(R.id.textophoto4);
        drawView = findViewById(R.id.EspacioCanva);
        btn_lienzo = (Button)findViewById(R.id.btn_lienzo);
        icono_liezo = (ImageView)findViewById(R.id.icono_lienzo);
        limpiar = (Button)findViewById(R.id.btn_limpiar);

        limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Razon.setText("");
            }
        });

        escuchar_voz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSpeechToText();
            }
        });

       // Toast.makeText(getApplicationContext(), nombreAyuda, Toast.LENGTH_SHORT).show();
           Log.e("","CANTIDAD DE IMAGENES DESDE BD:"+cantidaddeimagenes) ;



        Pregunta.setText(nombrePregunta);
        Razon.setText(textoHallazgo);

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickenfotoUno=1;
                eligeFotoGaleria();
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickenfotoDos=1;
                eligeFotoGaleria();
            }
        });
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickenfotoTres=1;
                eligeFotoGaleria();
            }
        });
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickenfotoCuatro=1;
                eligeFotoGaleria();
            }
        });
        /*imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TakePhoto();
            }
        });
        imageView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TakePhoto();
            }
        });*/



        final Context context = getApplicationContext();
        BotonTerminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("fotografiasVisualizadas",":"+fotografiasVisualizadas);

                BotonTerminar.setEnabled(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        BotonTerminar.setEnabled(true);
                    }
                },2000);

                if (Razon.getText().toString().length() == 0){

                    View mitoast = getLayoutInflater().inflate(R.layout.toast_comentario_foto_evidencia,(ViewGroup)findViewById(R.id.layout_toast_comentario));
                    Toast toast = new Toast(context);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(mitoast);
                    toast.setGravity(Gravity.CENTER,0,-400);
                    toast.show();
                }else{

                    if (fotografiaTomada==0 && fotografiasVisualizadas==0){
                        View toastfoto = getLayoutInflater().inflate(R.layout.toast_verificando_foto_evidencia,(ViewGroup)findViewById(R.id.layout_toast_fotografia));
                        Toast toast2 = new Toast(context);
                        toast2.setDuration(Toast.LENGTH_SHORT);
                        toast2.setView(toastfoto);
                        toast2.setGravity(Gravity.CENTER,0,-400);
                        toast2.show();
                    }

                }

                if (fotografiaTomada==1 && Razon.getText().toString().length() > 0 || fotografiasVisualizadas==1){
                    View procesando = getLayoutInflater().inflate(R.layout.toast_procesando,(ViewGroup)findViewById(R.id.layout_toast_procesando));
                    Toast toasprocesando =  new Toast(context);
                    toasprocesando.setDuration(Toast.LENGTH_SHORT);
                    toasprocesando.setView(procesando);
                    toasprocesando.setGravity(Gravity.CENTER,0,0);
                    toasprocesando.show();
                    Log.e("numero","numeroPregunta"+numeroPregunta);
                    BotonTerminar.setVisibility(View.GONE);
                    espereguardando.setVisibility(View.VISIBLE);
                    ejecutarservicio("https://vvnorth.com/5sGhoner/ContestarErrores.php");
                }
            }
        });



        if(cantidaddeimagenes.equals(" ") || cantidaddeimagenes.equals(null) || cantidaddeimagenes.equals("") || cantidaddeimagenes.equals("0")){
            Log.e("VACIOCANTIDADDEIMAGENES",";"+cantidadimg);

                textofotodos.setVisibility(View.INVISIBLE);
                textofototres.setVisibility(View.INVISIBLE);
                textofotocuatro.setVisibility(View.INVISIBLE);
                //imageView2.setImageResource(R.drawable.ic_camara_evidencia_deshabilitado);
                //imageView3.setImageResource(R.drawable.ic_camara_evidencia_deshabilitado);
                //imageView4.setImageResource(R.drawable.ic_camara_evidencia_deshabilitado);
                imageView2.setEnabled(false);
                imageView3.setEnabled(false);
                imageView4.setEnabled(false);

        }else{

            if(cantidaddeimagenes.equals("1")){
                textofototres.setVisibility(View.INVISIBLE);
                textofotocuatro.setVisibility(View.INVISIBLE);
                //imageView3.setImageResource(R.drawable.ic_camara_evidencia_deshabilitado);
                //imageView4.setImageResource(R.drawable.ic_camara_evidencia_deshabilitado);
                imageView3.setEnabled(false);
                imageView4.setEnabled(false);
            }

            if(cantidaddeimagenes.equals("2")){
                textofotocuatro.setVisibility(View.INVISIBLE);
                //imageView4.setImageResource(R.drawable.ic_camara_evidencia_deshabilitado);
                imageView4.setEnabled(false);
            }

            cantidadimg=Integer.parseInt(cantidaddeimagenes);
            Log.e("Cantidad Imagenes",";"+cantidadimg);

           /* BuscandoImagenesTomadas1();
            BuscandoImagenesTomadas2();
            BuscandoImagenesTomadas3();
            BuscandoImagenesTomadas4();*/


           for (int j=1;j<=cantidadimg;j++)
            {
                Log.e("Cantidad Imagenes:  "+cantidadimg," Vueltas: "+j);
                if (j==1){
                    BuscandoImagenesTomadas1();
                }
                if (j==2){
                    BuscandoImagenesTomadas2();
                }
                if (j==3){
                    BuscandoImagenesTomadas3();
                }
                if (j==4){
                    BuscandoImagenesTomadas4();
                }
            }
        }
    }


    public void eligeFotoGaleria(){
        final CharSequence[] options = {"Take a photo", "Select from gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(NuevaAuditoria_Pregunta.this);
        builder.setTitle("Elija Una Opción:");
        builder.setItems(new CharSequence[]{"Tomar foto", "Elegir de la galería"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        TakePhoto();
                        break;
                    case 1:
                        desdeGaleria();
                        break;
                }
            }
        });
        builder.show();
    }

    private void startSpeechToText() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Habla ahora...");

        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        } catch (Exception e) {
            Toast toas = Toast.makeText(getApplicationContext(),"Dispositivo no soporta esta opcion",Toast.LENGTH_SHORT);
            toas.show();
        }
    }


    public static String removeLastChar(String str) {
        return removeLastChars(str, 1);
    }

    private void BuscandoImagenesTomadas1() {
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        String noLastChar= removeLastChar(nombrePregunta);
        String Foto=noLastChar+"%3f";
        textofotouno.setText("Cargando imagen...");
        Log.e("urlbuscando: ","https://vvnorth.com/5sGhoner/FotosAuditorias/"+numeroAuditoria+"/"+nombreAyuda+"/"+Foto+"/"+1+".jpeg");

        ImageRequest imageRequest= new ImageRequest("https://vvnorth.com/5sGhoner/FotosAuditorias/"+numeroAuditoria+"/"+nombreAyuda+"/"+Foto+"/"+1+".jpeg", new Response.Listener<Bitmap>() {
            //  ImageRequest imageRequest= new ImageRequest("https://vvnorth.com/5sGhoner/subareas/"+subarea+"/"+GlobalAyudaVisual+".jpg", new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                fotoVista1=1;
                    imageView1.setImageBitmap(response);
                    if (response == null){ Log.e("NOO","VaciaImagen1"); }else{ Log.e("SII","SIHAYImagen1"); fotografiasVisualizadas=1; textofotouno.setText("");}
                //imageview.setPadding(260,0,0,0);
            }
        }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textofotouno.setText("Sin fotografía.");
                Log.e("NO EXITE LA IMAGEN",":"+1);
                //BuscandoImagenesTomadas2();// si existe 1 imagen y no la encuentra entonces buscame si esta en la imagen 2.
                //  requestImage(subareaTemportal,nombresNS,"1"  );
                //  numeroFoto=1;
                return;

            }
        });
        requestQueue.add(imageRequest);
    }
    private void BuscandoImagenesTomadas2() {
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        String noLastChar= removeLastChar(nombrePregunta);
        String Foto=noLastChar+"%3f";
        textofotodos.setText("Cargando imagen...");
        Log.e("urlbuscando: ","https://vvnorth.com/5sGhoner/FotosAuditorias/"+numeroAuditoria+"/"+nombreAyuda+"/"+Foto+"/"+2+".jpeg");
        ImageRequest imageRequest= new ImageRequest("https://vvnorth.com/5sGhoner/FotosAuditorias/"+numeroAuditoria+"/"+nombreAyuda+"/"+Foto+"/"+2+".jpeg", new Response.Listener<Bitmap>() {
            //  ImageRequest imageRequest= new ImageRequest("https://vvnorth.com/5sGhoner/subareas/"+subarea+"/"+GlobalAyudaVisual+".jpg", new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                fotoVista2=1;
                imageView2.setImageBitmap(response);
                if (response == null){ Log.e("NOO","VaciaImagen1");  }else{ Log.e("SII","SIHAYImagen2"); fotografiasVisualizadas=1; textofotodos.setText("");}
                //imageview.setPadding(260,0,0,0);
            }
        }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textofotodos.setText("Sin fotografía.");
                Log.e("NO EXITE LA IMAGEN",":"+2);
                //BuscandoImagenesTomadas3();//si hay 2 imagen y no laencuentra por que dio erro entonces busca en la imagen 3.
                //  requestImage(subareaTemportal,nombresNS,"1"  );
                //  numeroFoto=1;
                return;

            }
        });
        requestQueue.add(imageRequest);
    }
    private void BuscandoImagenesTomadas3() {
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        String noLastChar= removeLastChar(nombrePregunta);
        String Foto=noLastChar+"%3f";
        textofototres.setText("Cargando imagen...");
        Log.e("urlbuscando: ","https://vvnorth.com/5sGhoner/FotosAuditorias/"+numeroAuditoria+"/"+nombreAyuda+"/"+Foto+"/"+3+".jpeg");
        ImageRequest imageRequest= new ImageRequest("https://vvnorth.com/5sGhoner/FotosAuditorias/"+numeroAuditoria+"/"+nombreAyuda+"/"+Foto+"/"+3+".jpeg", new Response.Listener<Bitmap>() {
            //  ImageRequest imageRequest= new ImageRequest("https://vvnorth.com/5sGhoner/subareas/"+subarea+"/"+GlobalAyudaVisual+".jpg", new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                fotoVista3=1;
                imageView3.setImageBitmap(response);
                if (response == null){ Log.e("NOO","VaciaImagen1");  }else{ Log.e("SII","SIHAYImagen3"); fotografiasVisualizadas=1; textofototres.setText("");}
                //imageview.setPadding(260,0,0,0);
            }
        }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textofototres.setText("Sin fotografía.");
                Log.e("NO EXITE LA IMAGEN",":"+3);
                //BuscandoImagenesTomadas4();//si hay 3 imagenes y no laencuentra por que dio erro entonces busca en la imagen 4.
                //  requestImage(subareaTemportal,nombresNS,"1"  );
                //  numeroFoto=1;
                return;

            }
        });
        requestQueue.add(imageRequest);
    }

    private void BuscandoImagenesTomadas4() {
        RequestQueue requestQueue= Volley.newRequestQueue(this);

        String noLastChar= removeLastChar(nombrePregunta);
        String Foto=noLastChar+"%3f";
        textofotocuatro.setText("Cargando imagen...");
        Log.e("urlbuscando: ","https://vvnorth.com/5sGhoner/FotosAuditorias/"+numeroAuditoria+"/"+nombreAyuda+"/"+Foto+"/"+4+".jpeg");
        ImageRequest imageRequest= new ImageRequest("https://vvnorth.com/5sGhoner/FotosAuditorias/"+numeroAuditoria+"/"+nombreAyuda+"/"+Foto+"/"+4+".jpeg", new Response.Listener<Bitmap>() {
            //  ImageRequest imageRequest= new ImageRequest("https://vvnorth.com/5sGhoner/subareas/"+subarea+"/"+GlobalAyudaVisual+".jpg", new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                fotoVista4=1;
                imageView4.setImageBitmap(response);
                if (response == null){ Log.e("NOO","VaciaImagen1");  }else{ Log.e("SII","SIHAYImagen4"); fotografiasVisualizadas=1; textofotocuatro.setText("");}
                //imageview.setPadding(260,0,0,0);
            }
        }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textofotocuatro.setText("Sin fotografía.");
                Log.e("NO EXITE LA IMAGEN",":"+4);
                //  requestImage(subareaTemportal,nombresNS,"1"  );
                //  numeroFoto=1;
                return;

            }
        });
        requestQueue.add(imageRequest);
    }



    public void openDialog()
    {
        DialogOptions2 dialogOptions2 = new DialogOptions2();
        dialogOptions2.show(getSupportFragmentManager(),"example Dialog2");

    }


    /*@Override
    public void onYesClicked() {
        String fileName="photo";
        File StorageDirectory= getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        try {
            File imageFile=File.createTempFile(fileName,".jpg",StorageDirectory);
            currentPhotoPath=imageFile.getAbsolutePath();
            Uri imageUri=  FileProvider.getUriForFile(NuevaAuditoria_Pregunta.this,
                    "com.example.a5SGonher.fileprovider",imageFile);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
            startActivityForResult(intent,1);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }*/

    /*@Override
    public void onNoClicked() {

        //check runtime permission
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
        {if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED){
            //permission not granted, request it

            String[] permissions={Manifest.permission.READ_EXTERNAL_STORAGE};
            //show pop up for runtume
            requestPermissions(permissions,PERMISSION_CODE);

        }else {
            // permision already granted
            pickImageFromGallery();
        }


        }else{
            ///system os is less than marshmalllow
            pickImageFromGallery();
        }
    }*/

   public void TakePhoto()
    {
        String fileName="photo";
        File StorageDirectory= getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        try {
            File imageFile=File.createTempFile(fileName,".jpg",StorageDirectory);
            currentPhotoPath=imageFile.getAbsolutePath();
            Uri imageUri=  FileProvider.getUriForFile(NuevaAuditoria_Pregunta.this,
                    "com.example.a5SGonher.fileprovider",imageFile);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
            startActivityForResult(intent,1);



        } catch (IOException e) {
            e.printStackTrace();
        }


    }



    public void desdeGaleria(){
        // El usuario eligió elegir de la galería
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, REQUEST_IMAGE_GALLERY);
    }

    private void ejecutarservicio(String URL)
    {
        StringRequest stringRequest=new  StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                VolverANuevaAuditoria();
                // buscarProducto("https://vvnorth.com/comparacion_auditorf.php",NPlanta);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                BotonTerminar.setVisibility(View.VISIBLE);
                espereguardando.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Error de conexión, espere reconexión e intente nuevamente", Toast.LENGTH_SHORT).show();
               // Toast.makeText(getApplicationContext(), "Error de conexión, espere reconexión e intente nuevamente:"+error.toString(), Toast.LENGTH_SHORT).show();
                //VolverANuevaAuditoria(); //reporte de error
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros =new HashMap<String,String>();

                numberPhoto=tomadafotoUno+tomadafotoDos+tomadafotoTres+tomadafotoCuatro+fotoVista1+fotoVista2+fotoVista3+fotoVista4;
                String sNumberPhoto= String.valueOf(numberPhoto);
                Log.e("sNumberPhoto",": "+sNumberPhoto);
                parametros.put("Planta",Planta);
                parametros.put("AyudaVisual",nombreAyuda);
                parametros.put("nombrePregunta",nombrePregunta);
                parametros.put("NumeroAuditoria",numeroAuditoria);
                parametros.put("numeroPregunta",numeroPregunta);
                parametros.put("Error",Razon.getText().toString());
                parametros.put("numerPhotos",sNumberPhoto);
                Log.e("numerodefotos","sNumberPhoto="+sNumberPhoto);
                /*if(sNumberPhoto.equals("1")||sNumberPhoto.equals("2")||sNumberPhoto.equals("3")||sNumberPhoto.equals("4")||sNumberPhoto.equals("5") ||sNumberPhoto.equals("6"))
                { String imageData= imageToString(bitmapf);
                   parametros.put("image",imageData);}
                if(sNumberPhoto.equals("2")||sNumberPhoto.equals("3")||sNumberPhoto.equals("4")||sNumberPhoto.equals("5") ||sNumberPhoto.equals("6"))
                { String imageData2= imageToString(bitmapf);
                  parametros.put("image2",imageData2);}
                if(sNumberPhoto.equals("3")||sNumberPhoto.equals("4")||sNumberPhoto.equals("5") ||sNumberPhoto.equals("6"))
                { String imageData3= imageToString(bitmapf);parametros.put("image3",imageData3);}
                if(sNumberPhoto.equals("4")||sNumberPhoto.equals("5") ||sNumberPhoto.equals("6"))
                { String imageData4= imageToString(bitmapf);parametros.put("image4",imageData4);}
                if(sNumberPhoto.equals("5") ||sNumberPhoto.equals("6"))
                { String imageData5= imageToString(bitmapf);parametros.put("image5",imageData5);}
                if(sNumberPhoto.equals("6"))
                { String imageData6= imageToString(bitmapf);parametros.put("image6",imageData6);}*/
                if(tomadafotoUno==1)
                { String imageData= imageToString(bitmapf);parametros.put("image",imageData);}
                if(tomadafotoDos==1)
                { String imageData2= imageToString(bitmapf2);parametros.put("image2",imageData2);}
                if(tomadafotoTres==1)
                { String imageData3= imageToString(bitmapf3);parametros.put("image3",imageData3);}
                if(tomadafotoCuatro==1)
                { String imageData4= imageToString(bitmapf4);parametros.put("image4",imageData4);}

                return parametros;
            }
        };
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SPEECH_INPUT && resultCode == RESULT_OK && data != null) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (result != null && !result.isEmpty()) {
                String spokenText = result.get(0);
                Razon.setText(spokenText);
            }
        }

       if(requestCode == REQUEST_IMAGE_GALLERY && data != null && clickenfotoUno==1) {


           if (fotoVista2!=1){
               //imageView2.setImageResource(R.drawable.ic_camara_evidencia);
               imageView2.setEnabled(true);
           }
           textofotouno.setText("Capturada.");
           textofotodos.setVisibility(View.VISIBLE);
           if (fotoVista2!=1){
               //imageView2.setImageResource(R.drawable.ic_camara_evidencia);
               imageView2.setEnabled(true);
           }
           fotografiaTomada=1;//comprobando que si exita minimo una imagen tomada
            // La imagen seleccionada de la galería
            Uri imageUri = data.getData();
            imageView1.setImageURI(imageUri);


            Bitmap bitmap = ((BitmapDrawable)imageView1.getDrawable()).getBitmap();
            bitmapf = bitmap;
            imageView1.setImageBitmap(bitmapf);
            //bitmapf2 = bitmap;
            clickenfotoUno=0;
            fotoVista1=0;
            tomadafotoUno=1;

        }

        if(requestCode == REQUEST_IMAGE_GALLERY && data != null && clickenfotoDos==1) {
            if (fotoVista3!=1){
                //imageView3.setImageResource(R.drawable.ic_camara_evidencia);
                imageView3.setEnabled(true);
            }
            textofotodos.setText("Capturada.");
            textofototres.setVisibility(View.VISIBLE);
            fotografiaTomada=1;//comprobando que si exita minimo una imagen tomada

            // La imagen seleccionada de la galería
            Uri imageUri = data.getData();
            imageView2.setImageURI(imageUri);

            Bitmap bitmap = ((BitmapDrawable)imageView2.getDrawable()).getBitmap();
            bitmapf2 = bitmap;
            imageView2.setImageBitmap(bitmapf2);
            //bitmapf2 = bitmap;
            clickenfotoDos=0;
            fotoVista2=0;
            tomadafotoDos=1;

        }

        if(requestCode == REQUEST_IMAGE_GALLERY && data != null && clickenfotoTres==1) {
            if (fotoVista4!=1){
                //imageView4.setImageResource(R.drawable.ic_camara_evidencia);
                imageView4.setEnabled(true);
            }
            textofototres.setText("Capturada.");
            textofotocuatro.setVisibility(View.VISIBLE);
            fotografiaTomada=1;//comprobando que si exita minimo una imagen tomada

            // La imagen seleccionada de la galería
            Uri imageUri = data.getData();
            imageView3.setImageURI(imageUri);

            Bitmap bitmap = ((BitmapDrawable)imageView3.getDrawable()).getBitmap();
            bitmapf3 = bitmap;
            imageView3.setImageBitmap(bitmapf3);
            //bitmapf2 = bitmap;
            clickenfotoTres=0;
            fotoVista3=0;
            tomadafotoTres=1;
            imageView4.setEnabled(true);
        }

        if(requestCode == REQUEST_IMAGE_GALLERY && data != null && clickenfotoCuatro==1) {
            textofotocuatro.setText("Capturada.");
            fotografiaTomada=1;
            // La imagen seleccionada de la galería
            Uri imageUri = data.getData();
            imageView4.setImageURI(imageUri);

            Bitmap bitmap = ((BitmapDrawable)imageView4.getDrawable()).getBitmap();
            bitmapf4 = bitmap;
            imageView4.setImageBitmap(bitmapf4);
            //bitmapf2 = bitmap;
            clickenfotoCuatro=0;
            fotoVista4=0;
            tomadafotoCuatro=1;

        }

        if (requestCode == 1 && resultCode == RESULT_OK)
        {

          //  Bundle extras = data.getExtras();
          //  Bitmap bitmap = (Bitmap) extras.get("data");

            Bitmap bitmap= BitmapFactory.decodeFile(currentPhotoPath);//LINEA ANTERIOR

            // Create a matrix for the rotation
            Matrix matrix = new Matrix();
            matrix.postRotate(90);

// Rotate the bitmap
            Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);


            int newWidth = 1000;
            int newHeight = 1000;;
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(rotatedBitmap, newWidth, newHeight, false);

           /* String sNumberPhoto= String.valueOf(numberPhoto);
            if(sNumberPhoto.equals("3")) {
                fotografiaTomada=1;
            }
            else{  numberPhoto++; }
             sNumberPhoto= String.valueOf(numberPhoto);*/



            if(clickenfotoUno==1) {
                textofotouno.setText("Capturada.");
                textofotodos.setVisibility(View.VISIBLE);



                Log.e("fotovista2",":"+fotoVista2);
                if (fotoVista2!=1){
                    //imageView2.setImageResource(R.drawable.ic_camara_evidencia);
                    imageView2.setEnabled(true);
                }
                Log.e("ENTRE","click1");
                fotografiaTomada=1;//comprobando que si exita minimo una imagen tomada
                //ImageView imageView = findViewById(R.id.imageView1P);
                imageView1.setImageBitmap(resizedBitmap);
                ///////

                        Bitmap bitmapt = ((BitmapDrawable)imageView1.getDrawable()).getBitmap();
                        ViewGroup.LayoutParams param = drawView.getLayoutParams();
                        param.width = 1000;
                        param.height = 1000;
                        drawView.setVisibility(View.VISIBLE);
                        drawView.setBitmap(bitmapt);
                        bitmapf = drawView.getBitmap();
                        imageView1.setImageBitmap(bitmapf);

                //bitmapf = bitmap; cuando no existia el lienzo
                clickenfotoUno=0;
                fotoVista1=0;
                tomadafotoUno=1;

            }

            if(clickenfotoDos==1) {
                textofotodos.setText("Capturada.");
                textofototres.setVisibility(View.VISIBLE);
                if (fotoVista3!=1){
                    //imageView3.setImageResource(R.drawable.ic_camara_evidencia);
                    imageView3.setEnabled(true);
                }
                Log.e("ENTRE","click2");
                fotografiaTomada=1;
                //imageView2 = findViewById(R.id.imageView1P2);
                imageView2.setImageBitmap(resizedBitmap);
                Bitmap bitmapt = ((BitmapDrawable)imageView2.getDrawable()).getBitmap();
                ViewGroup.LayoutParams param = drawView.getLayoutParams();
                param.width = 1000;
                param.height = 1000;
                drawView.setVisibility(View.VISIBLE);
                drawView.setBitmap(bitmapt);
                bitmapf2 = drawView.getBitmap();
                imageView2.setImageBitmap(bitmapf2);
                //bitmapf2 = bitmap;
                clickenfotoDos=0;
                fotoVista2=0;
                tomadafotoDos=1;

            }

            if(clickenfotoTres==1) {
                textofototres.setText("Capturada.");
                textofotocuatro.setVisibility(View.VISIBLE);
                if (fotoVista4!=1){
                    //imageView4.setImageResource(R.drawable.ic_camara_evidencia);
                    imageView4.setEnabled(true);
                }
                Log.e("ENTRE","click3");
                fotografiaTomada=1;
                //fotoTres=1;
                //imageView3 = findViewById(R.id.imageView1P3);
                imageView3.setImageBitmap(resizedBitmap);

                Bitmap bitmapt = ((BitmapDrawable)imageView3.getDrawable()).getBitmap();
                ViewGroup.LayoutParams param = drawView.getLayoutParams();
                param.width = 1000;
                param.height = 1000;
                drawView.setVisibility(View.VISIBLE);
                drawView.setBitmap(bitmapt);
                bitmapf3 = drawView.getBitmap();
                imageView3.setImageBitmap(bitmapf3);
                /////


                //bitmapf3 = bitmap;
                clickenfotoTres=0;
                fotoVista3=0;
                tomadafotoTres=1;
                imageView4.setEnabled(true);
            }

            if(clickenfotoCuatro==1) {
                textofotocuatro.setText("Capturada.");
                Log.e("ENTRE","click4");
                fotografiaTomada=1;
                //fotoTres=1;
                //imageView4 = findViewById(R.id.imageView1P4);
                imageView4.setImageBitmap(resizedBitmap);

                Bitmap bitmapt = ((BitmapDrawable)imageView4.getDrawable()).getBitmap();
                ViewGroup.LayoutParams param = drawView.getLayoutParams();
                param.width = 1000;
                param.height = 1000;
                drawView.setVisibility(View.VISIBLE);
                drawView.setBitmap(bitmapt);
                bitmapf4 = drawView.getBitmap();
                imageView4.setImageBitmap(bitmapf4);
                /////

                //bitmapf4 = bitmap;
                clickenfotoCuatro=0;
                fotoVista4=0;
                tomadafotoCuatro=1;
            }

            icono_liezo.setVisibility(View.VISIBLE);
            BotonTerminar.setVisibility(View.GONE);
            mensaje_lienzo.setVisibility(View.VISIBLE);
            btn_lienzo.setVisibility(View.VISIBLE);
            Pregunta.setVisibility(View.GONE);
            Razon.setVisibility(View.GONE);
            escuchar_voz.setVisibility(View.GONE);
            limpiar.setVisibility(View.GONE);
            textofotouno.setVisibility(View.GONE);
            textofotodos.setVisibility(View.GONE);
            textofototres.setVisibility(View.GONE);
            textofotocuatro.setVisibility(View.GONE);
            imageView1.setVisibility(View.GONE);
            imageView2.setVisibility(View.GONE);
            imageView3.setVisibility(View.GONE);
            imageView4.setVisibility(View.GONE);

            btn_lienzo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    drawView.setVisibility(View.GONE);
                    btn_lienzo.setVisibility(View.GONE);
                    icono_liezo.setVisibility(View.GONE);
                    mensaje_lienzo.setVisibility(View.GONE);
                    Pregunta.setVisibility(View.VISIBLE);
                    Razon.setVisibility(View.VISIBLE);
                    escuchar_voz.setVisibility(View.VISIBLE);
                    limpiar.setVisibility(View.VISIBLE);
                    textofotouno.setVisibility(View.VISIBLE);
                    textofotodos.setVisibility(View.VISIBLE);
                    textofototres.setVisibility(View.VISIBLE);
                    textofotocuatro.setVisibility(View.VISIBLE);
                    imageView1.setVisibility(View.VISIBLE);
                    imageView2.setVisibility(View.VISIBLE);
                    imageView3.setVisibility(View.VISIBLE);
                    imageView4.setVisibility(View.VISIBLE);
                    BotonTerminar.setVisibility(View.VISIBLE);
                }
            });


        }


    }

    private String imageToString(Bitmap bitmap)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,20, outputStream);//peso
        byte[] imageBytes= outputStream.toByteArray();
        String encodeImage= Base64.encodeToString(imageBytes,Base64.DEFAULT);
        return encodeImage;
    }

    public void VolverANuevaAuditoria()
    {
        Intent intent =new Intent(this,NuevaAuditoria.class);
        intent.putExtra("EXTRA_SESSION_ID3", numeroAuditoria);
        intent.putExtra("EXTRA_SESSION_ID5", numeroActual);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {

        Intent intent =new Intent(this,NuevaAuditoria.class);
        intent.putExtra("EXTRA_SESSION_ID3", numeroAuditoria);
        intent.putExtra("EXTRA_SESSION_ID5", numeroActual);
        startActivity(intent);
    }

}