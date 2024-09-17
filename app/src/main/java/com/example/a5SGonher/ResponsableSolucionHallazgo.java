package com.example.a5SGonher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ResponsableSolucionHallazgo extends AppCompatActivity {
    LinearLayout layoutComentario, layoutFotos;
    ImageView escuchar_voz,fotografia1,fotografia2,fotografia3,iconoInterrogacion,icono_liezo;
    EditText descripcion;
    TextView textoCamara1,textoCamara2,textoCamara3,textoiconoInterrogacion,mensaje_lienzo,espereguardando;
    DrawView drawView;
    Bitmap bitmapf1,bitmapf2,bitmapf3;
    Button limpiar,BotonTerminar,btn_lienzo;
    String Planta,ServerName,numeroNomina,ID_hallazgo,Comentario_Solucion;
    int clickFotoUno=0,clickFotoDos=0,clickFotoTres=0,tomadafotoUno=0,tomadafotoDos=0,tomadafotoTres=0;
    int fotoTomadaUno=0,FotoTomadaDos=0,FotoTomadaTres=0;
    private String currentPhotoPath;
    private static final int REQUEST_CODE_SPEECH_INPUT = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_responsable_solucion_hallazgo);
        TextView titulo = (TextView)findViewById(R.id.titulo_toolbar);
        layoutComentario = (LinearLayout)findViewById(R.id.linearLayoutComentario);
        layoutFotos = (LinearLayout)findViewById(R.id.linearLayoutFotos);
        titulo.setText("Solución Hallazgo");
        GlobalClass globalClass =(GlobalClass)getApplicationContext();
        ServerName=globalClass.getName();

        Intent intent = getIntent();
        ID_hallazgo=intent.getStringExtra("ID_HALLAZGO");
        Comentario_Solucion=intent.getStringExtra("COMENTARIO_SOLUCION");

        //Recupero Informacion
        SharedPreferences preferences=getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String user= preferences.getString("User","No existe Usuario");
        String Rol= preferences.getString("Rol","No existe Usuario");
        String nombreAuditor= preferences.getString("NombreAuditor","No existe Usuario");
        Planta= preferences.getString("Planta","No existe Planta");
        numeroNomina= preferences.getString("NumeroNomina","Sin Número de Nómina");


        escuchar_voz=findViewById(R.id.img_record);
        descripcion=findViewById(R.id.descripcion);
        descripcion.setText(Comentario_Solucion);
        limpiar = (Button)findViewById(R.id.btn_limpiar);
        fotografia1 = findViewById(R.id.imageView1P);
        fotografia2 = findViewById(R.id.imageView2P);
        fotografia3 = findViewById(R.id.imageView3P);
        textoCamara1 = findViewById(R.id.textoCamara1);
        textoCamara2 = findViewById(R.id.textoCamara2);
        textoCamara3 = findViewById(R.id.textoCamara3);
        BotonTerminar=(Button) findViewById(R.id.Button_Contestar);
        iconoInterrogacion = (ImageView)findViewById(R.id.imageView18);
        textoiconoInterrogacion = (TextView)findViewById(R.id.textView33);
        mensaje_lienzo = (TextView)findViewById(R.id.mensaje_lienzo);
        drawView = findViewById(R.id.EspacioCanva);
        btn_lienzo = (Button)findViewById(R.id.btn_lienzo);
        icono_liezo = (ImageView)findViewById(R.id.icono_lienzo);
        limpiar = (Button)findViewById(R.id.btn_limpiar);
        espereguardando = (TextView)findViewById(R.id.espereguardando);
        escuchar_voz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSpeechToText();
            }
        });


        String urlImagen1 = ServerName+"portal5s/evidencias_recorrido/"+numeroNomina+"/"+ID_hallazgo+"/1.jpeg?random="+Math.random();
        String urlImagen2 = ServerName+"portal5s/evidencias_recorrido/"+numeroNomina+"/"+ID_hallazgo+"/2.jpeg?random="+Math.random();
        String urlImagen3 = ServerName+"portal5s/evidencias_recorrido/"+numeroNomina+"/"+ID_hallazgo+"/3.jpeg?random="+Math.random();

        loadImage(urlImagen1, fotografia1, R.drawable.ic_camara_evidencia);//Si existe imagen colocala si no el icono.
        loadImage(urlImagen2, fotografia2, R.drawable.ic_camara_evidencia);
        loadImage(urlImagen3, fotografia3, R.drawable.ic_camara_evidencia);

        limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                descripcion.setText("");
            }
        });
        fotografia1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Presi","tomarFoto1");
                clickFotoUno =1;
                clickFotoDos =0;
                clickFotoTres =0;
                TakePhoto();
            }
        } );
        fotografia2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Presi","tomarFoto2");
                clickFotoUno =0;
                clickFotoDos =1;
                clickFotoTres =0;
                TakePhoto();
            }
        } );
        fotografia3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Presi","tomarFoto3");
                clickFotoUno =0;
                clickFotoDos =0;
                clickFotoTres =1;
                TakePhoto();
            }
        } );

        BotonTerminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    espereguardando.setVisibility(View.VISIBLE);
                    BotonTerminar.setVisibility(View.GONE);
                    guardarImagenesDeSolucion(ServerName+"5sGhoner/guardarSolucionHallazgo.php");
            }
        });
    }

    private void loadImage(String url, ImageView imageView, int defaultImage) {
        Picasso.get().load(url).error(defaultImage).into(imageView);
    }

    private void guardarImagenesDeSolucion(String URL)
    {
        StringRequest stringRequest=new  StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("true")){
                    View procesando = getLayoutInflater().inflate(R.layout.toast_procesando,(ViewGroup)findViewById(R.id.layout_toast_procesando));
                    Toast toasprocesando =  new Toast(ResponsableSolucionHallazgo.this);
                    TextView textoTitulo =procesando.findViewById(R.id.textView35);
                    TextView mensaje =procesando.findViewById(R.id.mensaje1);
                    textoTitulo.setText("EVIDENCIA GUARDADA!!");
                    mensaje.setText("Se guardado correctamente");

                    toasprocesando.setDuration(Toast.LENGTH_SHORT);
                    toasprocesando.setView(procesando);
                    toasprocesando.setGravity(Gravity.CENTER,0,0);
                    toasprocesando.show();
                    itentLista();
                }else{
                    Toast.makeText(getApplicationContext(),"Intento Nuevamente",Toast.LENGTH_LONG).show();
                    espereguardando.setVisibility(View.GONE);
                    BotonTerminar.setVisibility(View.VISIBLE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                BotonTerminar.setVisibility(View.VISIBLE);
                espereguardando.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Error de conexión, espere reconexión e intente nuevamente", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros =new HashMap<String,String>();

                parametros.put("Planta",Planta);
                parametros.put("id_hallazgo",ID_hallazgo);
                parametros.put("nomina_responsable",numeroNomina);
                parametros.put("comentario_solucion",descripcion.getText().toString());
                if(tomadafotoUno==1)
                { String imageData= imageToString(bitmapf1);
                parametros.put("image1",imageData);}
                if(tomadafotoDos==1)
                { String imageData2= imageToString(bitmapf2);
                parametros.put("image2",imageData2);}
                if(tomadafotoTres==1)
                { String imageData3= imageToString(bitmapf3);
                parametros.put("image3",imageData3);}

                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private String imageToString(Bitmap bitmap)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,20, outputStream);//peso
        byte[] imageBytes= outputStream.toByteArray();
        String encodeImage= Base64.encodeToString(imageBytes,Base64.DEFAULT);
        return encodeImage;
    }


    private void startSpeechToText() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Habla ahora...");
        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);

        } catch (Exception e) {
            Toast toas = Toast.makeText(getApplicationContext(),"Dispositivo no soporta esta opción",Toast.LENGTH_SHORT);
            toas.show();
        }
    }

    public void TakePhoto()
    {
        String fileName="photo";
        File StorageDirectory= getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        try {
            File imageFile=File.createTempFile(fileName,".jpg",StorageDirectory);
            currentPhotoPath=imageFile.getAbsolutePath();
            Uri imageUri=  FileProvider.getUriForFile(this,
                    "com.example.a5SGonher.fileprovider",imageFile);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
            startActivityForResult(intent,1);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SPEECH_INPUT && resultCode == RESULT_OK && data != null) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (result != null && !result.isEmpty()) {
                String spokenText = result.get(0);
                descripcion.setText(spokenText);
            }
        }

        if (requestCode == 1 && resultCode == RESULT_OK)
        {
            Bitmap bitmap= BitmapFactory.decodeFile(currentPhotoPath);
            // Create a matrix for the rotation
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);


            int newWidth = 1000;
            int newHeight = 1000;;
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(rotatedBitmap, newWidth, newHeight, false);
            if(clickFotoUno==1){
                textoCamara1.setText("Tomada");
                fotografia1.setImageBitmap(resizedBitmap);

                Bitmap bitmapt = ((BitmapDrawable)fotografia1.getDrawable()).getBitmap();
                ViewGroup.LayoutParams param = drawView.getLayoutParams();
                param.width = 1000;
                param.height = 1000;

                //drawView.setVisibility(View.VISIBLE);
                drawView.setBitmap(bitmapt);
                bitmapf1 = drawView.getBitmap();
                ViewGroup.LayoutParams params = fotografia1.getLayoutParams();

                params.width = 300;
                params.height = 300;
                fotografia1.setImageBitmap(bitmapf1);
                tomadafotoUno =1;
            }
            if(clickFotoDos==1){
                textoCamara2.setText("Tomada");
                fotografia2.setImageBitmap(resizedBitmap);

                Bitmap bitmapt = ((BitmapDrawable)fotografia2.getDrawable()).getBitmap();
                ViewGroup.LayoutParams param = drawView.getLayoutParams();
                param.width = 1000;
                param.height = 1000;

                //drawView.setVisibility(View.VISIBLE);
                drawView.setBitmap(bitmapt);
                bitmapf2 = drawView.getBitmap();
                ViewGroup.LayoutParams params = fotografia2.getLayoutParams();

                params.width = 300;
                params.height = 300;
                fotografia2.setImageBitmap(bitmapf2);
                tomadafotoDos =1;
            }
            if(clickFotoTres==1){
                textoCamara3.setText("Tomada");
                fotografia3.setImageBitmap(resizedBitmap);

                Bitmap bitmapt = ((BitmapDrawable)fotografia3.getDrawable()).getBitmap();
                ViewGroup.LayoutParams param = drawView.getLayoutParams();
                param.width = 1000;
                param.height = 1000;

                //drawView.setVisibility(View.VISIBLE);
                drawView.setBitmap(bitmapt);
                bitmapf3 = drawView.getBitmap();
                ViewGroup.LayoutParams params = fotografia3.getLayoutParams();

                params.width = 300;
                params.height = 300;
                fotografia3.setImageBitmap(bitmapf3);
                tomadafotoTres =1;
            }

           /* icono_liezo.setVisibility(View.VISIBLE);
            BotonTerminar.setVisibility(View.GONE);
            mensaje_lienzo.setVisibility(View.VISIBLE);
            btn_lienzo.setVisibility(View.VISIBLE);
            layoutComentario.setVisibility(View.GONE);
            textoCamara1.setVisibility(View.GONE);
            textoiconoInterrogacion.setVisibility(View.GONE);
            iconoInterrogacion.setVisibility(View.GONE);
            layoutFotos.setVisibility(View.GONE);*/

            btn_lienzo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    drawView.setVisibility(View.GONE);
                    btn_lienzo.setVisibility(View.GONE);
                    icono_liezo.setVisibility(View.GONE);
                    mensaje_lienzo.setVisibility(View.GONE);
                    layoutComentario.setVisibility(View.GONE);
                    textoiconoInterrogacion.setVisibility(View.VISIBLE);
                    iconoInterrogacion.setVisibility(View.VISIBLE);
                    layoutComentario.setVisibility(View.VISIBLE);
                    textoCamara1.setVisibility(View.VISIBLE);
                    BotonTerminar.setVisibility(View.VISIBLE);
                    layoutFotos.setVisibility(View.VISIBLE);
                }
            });



        }
    }

    public void itentLista(){
        Intent intent = new Intent(this,ResponsableListaHallazgos.class);
        intent.putExtra("tipoHallazgo","");
        startActivity(intent);
        finish();
    }

}