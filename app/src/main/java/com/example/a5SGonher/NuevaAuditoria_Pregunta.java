package com.example.a5SGonher;

import static com.example.a5SGonher.ShowOnlyImageHallazgos.removeLastChars;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
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
import java.util.HashMap;
import java.util.Map;

public class NuevaAuditoria_Pregunta extends AppCompatActivity implements DialogOptions2.DialogOptions1Listener{
    String [] guardandorespuestas  = new String[6];
    String nombrePregunta,numeroAuditoria,nombreAyuda,numeroPregunta,numeroActual,cantidadRealPreguntas,numeroAnteriorAuditoria,textoHallazgo, cantidaddeimagenes;
    Button BotonTerminar;
    TextView Pregunta;
    EditText Razon;
    Bitmap bitmapf,bitmapf2,bitmapf3,bitmapf4,bitmapf5,bitmapf6;
    int numberPhoto=0,fotografiaTomada=0,fotografiasVisualizadas=0,cantidadimg=0,fotonumero=0,clickenfotoUno=0,clickenfotoDos=0,clickenfotoTres=0,
            tomadafotoUno=0,tomadafotoDos=0,tomadafotoTres=0, fotoVista1=0, fotoVista2=0, fotoVista3=0;
    private ImageView imageView1,imageView2,imageView3,imageView4,imageView5,imageView6;
    private String currentPhotoPath;
    private static final int IMAGE_PICK_CODE=1000;
    private  static  final int PERMISSION_CODE=1001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_auditoria__pregunta);
        TextView titulobarra = (TextView)findViewById(R.id.titulo_toolbar);
        final TextView espereguardando = (TextView)findViewById(R.id.espereguardando);
        titulobarra.setText("Evidencia");
        nombrePregunta = getIntent().getStringExtra("EXTRA_SESSION_ID");
        numeroAuditoria = getIntent().getStringExtra("EXTRA_SESSION_ID2");
        nombreAyuda = getIntent().getStringExtra("EXTRA_SESSION_ID3");
        numeroPregunta = getIntent().getStringExtra("EXTRA_SESSION_ID4");
        numeroActual = getIntent().getStringExtra("EXTRA_SESSION_ID5");
        numeroAnteriorAuditoria = getIntent().getStringExtra("EXTRA_SESSION_ID6");
        textoHallazgo = getIntent().getStringExtra("EXTRA_SESSION_ID7");
        cantidaddeimagenes = getIntent().getStringExtra("CANTIDAD_IMAGENES");
        Log.e("","cain------"+cantidaddeimagenes);
        BotonTerminar=(Button) findViewById(R.id.Button_Contestar);
        Pregunta=(TextView) findViewById(R.id.textView_Pregunta);
        Razon=(EditText) findViewById(R.id.editTextTextMultiLine);
        imageView1=(ImageView)findViewById(R.id.imageView1P);
         imageView2=(ImageView)findViewById(R.id.imageView1P2);
         imageView3=(ImageView)findViewById(R.id.imageView1P3);
         imageView4=(ImageView)findViewById(R.id.imageView1P4);
         imageView5=(ImageView)findViewById(R.id.imageView1P5);
         imageView6=(ImageView)findViewById(R.id.imageView1P6);

       // Toast.makeText(getApplicationContext(), nombreAyuda, Toast.LENGTH_SHORT).show();

        Pregunta.setText(nombrePregunta);
        Razon.setText(textoHallazgo);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickenfotoUno=1;
                TakePhoto();
            }
        } );
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickenfotoDos=1;
                TakePhoto();
            }
        });
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickenfotoTres=1;
                TakePhoto();
            }
        });
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TakePhoto();
            }
        });
        imageView5.setOnClickListener(new View.OnClickListener() {
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
        });



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
        if(cantidaddeimagenes.equals(" ") || cantidaddeimagenes.equals(null) || cantidaddeimagenes.equals("")){
            Log.e("VACIOCANTIDADDEIMAGENES",";"+cantidadimg);
        }else{
            cantidadimg=Integer.parseInt(cantidaddeimagenes);
            Log.e("Cantidad Imagenes",";"+cantidadimg);
            for (int j=1;j<=cantidadimg;j++)
            {
                if (j==1){
                    BuscandoImagenesTomadas1();
                }
                if (j==2){
                    BuscandoImagenesTomadas2();
                }
                if (j==3){
                    BuscandoImagenesTomadas3();
                }
            }
        }
    }

    public static String removeLastChar(String str) {
        return removeLastChars(str, 1);
    }

    private void BuscandoImagenesTomadas1() {
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        fotoVista1=1;
        String noLastChar= removeLastChar(nombrePregunta);
        String Foto=noLastChar+"%3f";
        Log.e("urlbuscando: ","https://vvnorth.com/5sGhoner/FotosAuditorias/"+numeroAuditoria+"/"+nombreAyuda+"/"+Foto+"/"+1+".jpeg");
        ImageRequest imageRequest= new ImageRequest("https://vvnorth.com/5sGhoner/FotosAuditorias/"+numeroAuditoria+"/"+nombreAyuda+"/"+Foto+"/"+1+".jpeg", new Response.Listener<Bitmap>() {
            //  ImageRequest imageRequest= new ImageRequest("https://vvnorth.com/5sGhoner/subareas/"+subarea+"/"+GlobalAyudaVisual+".jpg", new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                Log.e("fotonumero",":"+fotonumero);
                    imageView1.setImageBitmap(response);
                    if (response == null){ Log.e("NOO","VaciaImagen1");  }else{ Log.e("SII","SIHAYImagen1"); fotografiasVisualizadas=1;}
                //imageview.setPadding(260,0,0,0);
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
    private void BuscandoImagenesTomadas2() {
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        fotoVista2=1;
        String noLastChar= removeLastChar(nombrePregunta);
        String Foto=noLastChar+"%3f";
        Log.e("urlbuscando: ","https://vvnorth.com/5sGhoner/FotosAuditorias/"+numeroAuditoria+"/"+nombreAyuda+"/"+Foto+"/"+2+".jpeg");
        ImageRequest imageRequest= new ImageRequest("https://vvnorth.com/5sGhoner/FotosAuditorias/"+numeroAuditoria+"/"+nombreAyuda+"/"+Foto+"/"+2+".jpeg", new Response.Listener<Bitmap>() {
            //  ImageRequest imageRequest= new ImageRequest("https://vvnorth.com/5sGhoner/subareas/"+subarea+"/"+GlobalAyudaVisual+".jpg", new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                Log.e("fotonumero",":"+fotonumero);
                imageView2.setImageBitmap(response);
                if (response == null){ Log.e("NOO","VaciaImagen1");  }else{ Log.e("SII","SIHAYImagen1"); fotografiasVisualizadas=1;}
                //imageview.setPadding(260,0,0,0);
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
    private void BuscandoImagenesTomadas3() {
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        fotoVista3=1;
        String noLastChar= removeLastChar(nombrePregunta);
        String Foto=noLastChar+"%3f";
        Log.e("urlbuscando: ","https://vvnorth.com/5sGhoner/FotosAuditorias/"+numeroAuditoria+"/"+nombreAyuda+"/"+Foto+"/"+3+".jpeg");
        ImageRequest imageRequest= new ImageRequest("https://vvnorth.com/5sGhoner/FotosAuditorias/"+numeroAuditoria+"/"+nombreAyuda+"/"+Foto+"/"+3+".jpeg", new Response.Listener<Bitmap>() {
            //  ImageRequest imageRequest= new ImageRequest("https://vvnorth.com/5sGhoner/subareas/"+subarea+"/"+GlobalAyudaVisual+".jpg", new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                Log.e("fotonumero",":"+fotonumero);
                imageView3.setImageBitmap(response);
                if (response == null){ Log.e("NOO","VaciaImagen1");  }else{ Log.e("SII","SIHAYImagen1"); fotografiasVisualizadas=1;}
                //imageview.setPadding(260,0,0,0);
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

    public void openDialog()
    {
        DialogOptions2 dialogOptions2 = new DialogOptions2();
        dialogOptions2.show(getSupportFragmentManager(),"example Dialog2");

    }


    @Override
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

    }

    @Override
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
    }
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

    private void pickImageFromGallery()
    {
// int to pick image
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case  PERMISSION_CODE:{
                if(grantResults.length >0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED){
                    //permission was granted
                    pickImageFromGallery();
                }
                else{
//permision was denied

                }

            }
        }
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
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                VolverANuevaAuditoria();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros =new HashMap<String,String>();

                numberPhoto=tomadafotoUno+tomadafotoDos+tomadafotoTres+fotoVista1+fotoVista2+fotoVista3;
                String sNumberPhoto= String.valueOf(numberPhoto);
                Log.e("sNumberPhoto",": "+sNumberPhoto);
                parametros.put("AyudaVisual",nombreAyuda);
                parametros.put("nombrePregunta",nombrePregunta);
                parametros.put("NumeroAuditoria",numeroAuditoria);
                parametros.put("numeroPregunta",numeroPregunta);
                parametros.put("Error",Razon.getText().toString());
                parametros.put("numerPhotos",sNumberPhoto);
                Log.e("numerodefotos","sNumberPhoto"+sNumberPhoto);
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

                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode == RESULT_OK)
        {
            Bitmap bitmap= BitmapFactory.decodeFile(currentPhotoPath);

           /* String sNumberPhoto= String.valueOf(numberPhoto);
            if(sNumberPhoto.equals("3")) {
                fotografiaTomada=1;
            }
            else{  numberPhoto++; }
             sNumberPhoto= String.valueOf(numberPhoto);*/

            if(clickenfotoUno==1) {
                Log.e("ENTRE","click1");
                fotografiaTomada=1;//comprobando que si exita minimo una imagen tomada
                //fotoUno=1;
                ImageView imageView = findViewById(R.id.imageView1P);
                imageView.setImageBitmap(bitmap);
                bitmapf = bitmap;
                clickenfotoUno=0;
                tomadafotoUno=1;
            }

            if(clickenfotoDos==1) {
                Log.e("ENTRE","click2");
                fotografiaTomada=1;
                //fotoDos=1;
                ImageView imageView = findViewById(R.id.imageView1P2);
                imageView.setImageBitmap(bitmap);
                bitmapf2 = bitmap;
                clickenfotoDos=0;
                tomadafotoDos=1;
            }

            if(clickenfotoTres==1) {
                Log.e("ENTRE","click3");
                fotografiaTomada=1;
                //fotoTres=1;
                ImageView imageView = findViewById(R.id.imageView1P3);
                imageView.setImageBitmap(bitmap);
                bitmapf3 = bitmap;
                clickenfotoTres=0;
                tomadafotoTres=1;
            }

           /* if(sNumberPhoto.equals("4")) {
                fotografiaTomada=1;
                ImageView imageView = findViewById(R.id.imageView1P4);
                imageView.setImageBitmap(bitmap);
                bitmapf4 = bitmap;
            }
            if(sNumberPhoto.equals("5")) {
                fotografiaTomada=1;
                ImageView imageView = findViewById(R.id.imageView1P5);
                imageView.setImageBitmap(bitmap);
                bitmapf5 = bitmap;
            }

            if(sNumberPhoto.equals("6")) {
                fotografiaTomada=1;
                ImageView imageView = findViewById(R.id.imageView1P6);
                imageView.setImageBitmap(bitmap);
                bitmapf6 = bitmap;
            }*/

        }


        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
//set image to image view
            imageView1.setImageURI(data.getData());
            Bitmap bitmap = ((BitmapDrawable)imageView1.getDrawable()).getBitmap();

            bitmapf=bitmap;


        }


    }

    private String imageToString(Bitmap bitmap)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,20, outputStream);
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