package com.example.a5SGonher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.clans.fab.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AuditoriaLlenado extends AppCompatActivity implements DialogOptions2.DialogOptions1Listener,  ExampleDialogData.ExampleDialogDataListener{
    RadioGroup radioGroup;
    RadioButton radioButton;
    String radioN2;
    TextView textView;
    TextView textViewNs;
    TextView textViewDatos;
    int imageNumber=0;
    EditText multiText;
    Bitmap bitmapf;
    Bitmap bitmapf2;
    Bitmap bitmapf3;
    Bitmap bitmapf4;
    Bitmap bitmapf5;
    String urlUpload="https://vvnorth.com/upload.php";
    String nombrePlanta2;
    String nombreArea2 ;
    String numeroAuditoria2;
    private String currentPhotoPath;
    TextView text1,text2,text3,text4,text5,tex6,textViewPruebas;
    Integer cantidadFotos=0;
    RequestQueue requestQueue;
    String nextNombrePlanta;
    String nextNombreArea;
    String nextNumeroAuditoria;
    String nextNombrePregunta;
    String nextDatos;
    String nextNumeroS;
    String nextSubarea;
    String currentId;
    String ServerName;
    String nextCondition;
    String numeroSGlobal;
    private static final int IMAGE_PICK_CODE=1000;
    private  static  final int PERMISSION_CODE=1001;



    FloatingActionButton fabFoto,fabGfoto;
    private ImageView mimageView,mimageView2,mimageView3,mimageView4,mimageView5,mimageView6,mimageView7,imageViewQquestionMark;
    private static final int Request_IMAGE_CAPTURE=101;



    //--------------------------------------------------
    String imgTemporal="iVBORw0KGgoAAAANSUhEUgAAAQgAAAEWCAYAAACaKgkUAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAMUSURBVHhe7dSBAAAADASh+Uv/CM6gILoBBEEASRBAEgSQBAEkQQBJEEASBJAEASRBAEkQQBIEkAQBJEEASRBAEgSQBAEkQQBJEEASBJAEASRBAEkQQBIEkAQBJEEASRBAEgSQBAEkQQBJEEASBJAEASRBAEkQQBIEkAQBJEEASRBAEgSQBAEkQQBJEEASBJAEASRBAEkQQBIEkAQBJEEASRBAEgSQBAEkQQBJEEASBJAEASRBAEkQQBIEkAQBJEEASRBAEgSQBAEkQQBJEEASBJAEASRBAEkQQBIEkAQBJEEASRBAEgSQBAEkQQBJEEASBJAEASRBAEkQQBIEkAQBJEEASRBAEgSQBAEkQQBJEEASBJAEASRBAEkQQBIEkAQBJEEASRBAEgSQBAEkQQBJEEASBJAEASRBAEkQQBIEkAQBJEEASRBAEgSQBAEkQQBJEEASBJAEASRBAEkQQBIEkAQBJEEASRBAEgSQBAEkQQBJEEASBJAEASRBAEkQQBIEkAQBJEEASRBAEgSQBAEkQQBJEEASBJAEASRBAEkQQBIEkAQBJEEASRBAEgSQBAEkQQBJEEASBJAEASRBAEkQQBIEkAQBJEEASRBAEgSQBAEkQQBJEEASBJAEASRBAEkQQBIEkAQBJEEASRBAEgSQBAEkQQBJEEASBJAEASRBAEkQQBIEkAQBJEEASRBAEgSQBAEkQQBJEEASBJAEASRBAEkQQBIEkAQBJEEASRBAEgSQBAEkQQBJEEASBJAEASRBAEkQQBIEkAQBJEEASRBAEgSQBAEkQQBJEEASBJAEASRBAEkQQBIEkAQBJEEASRBAEgSQBAEkQQBJEEASBJAEASRBAEkQQBIEkAQBJEEASRBAEgSQBAEkQQBJEEASBJAEASRBAEkQQBIEkAQBJEEASRBAEgSQBAEkQQBJEEASBJAEASRBAEkQQBIEkAQBJEEASRBAEgSQBAEkQQBJEEASBJAEASRBAEkQQBIEkAQBJEEASRBAEgSQBAEkQQBJEEASBJAEASRBAEkQQBIEkAQBJEEAYXtgm4grnb8i+AAAAABJRU5ErkJggg==";

    //------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //  openDialog2();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auditoria_llenado);
        final String nombrePlanta = getIntent().getStringExtra("EXTRA_SESSION_ID");
        final String nombreArea = getIntent().getStringExtra("EXTRA_SESSION_ID2");
        final String numeroAuditoria = getIntent().getStringExtra("EXTRA_SESSION_ID3");////////////////////////////////
        final String nombrePregunta = getIntent().getStringExtra("EXTRA_SESSION_ID4");//////////////////////////////////////////
        final String datos = getIntent().getStringExtra("EXTRA_SESSION_ID5");
        final String numeroS = getIntent().getStringExtra("EXTRA_SESSION_ID6");////////////////////////////////////////////////////////
        final String subArea = getIntent().getStringExtra("EXTRA_SESSION_ID7");////////////////////////////////////////////////////////
        GlobalClass globalClass =(GlobalClass)getApplicationContext();
        ServerName=globalClass.getName();
        nombrePlanta2=nombrePlanta;
        nombreArea2 =nombreArea;
        numeroAuditoria2=numeroAuditoria;
        numeroSGlobal=numeroS;

        text1=findViewById(R.id.textView);/////////////////////////////////////////////////////////////////////////////si algo falla es esto
        text2=findViewById(R.id.textView2);
        text3=findViewById(R.id.textView3);
        text4=findViewById(R.id.textView4);
        text5=findViewById(R.id.textView5);
        textViewPruebas=findViewById(R.id.textView20);

        mimageView=findViewById(R.id.imageView19);
        mimageView2=findViewById(R.id.imageView20);
        mimageView3=findViewById(R.id.imageView21);
        mimageView4=findViewById(R.id.imageView22);
        mimageView5=findViewById(R.id.imageView23);
        imageViewQquestionMark=findViewById(R.id.imageViewQuestionMark);

        radioGroup=findViewById(R.id.radioGroup);
        textView=findViewById(R.id.textViewNs);///////////////////////////////////////////////////////////////////////////////////// si algo falla es esto
        textViewNs=findViewById(R.id.textViewNs);
        textViewDatos=findViewById(R.id.textViewDatos);
        fabFoto=findViewById(R.id.fabSalir);
        fabGfoto=findViewById(R.id.fabGfoto);
        multiText=(EditText)findViewById(R.id.multiTexto1);


        textViewNs.setText("S"+numeroS+"-"+nombrePregunta);
        textViewDatos.setText(datos);
        buscarProducto(ServerName+"/Siguiente_Pregunta.php?NumeroAuditoria="+numeroAuditoria2 +"&NombrePregunta="+nombrePregunta+"");
        buscarProducto2(ServerName+"/Condicion_Pregunta.php?NumeroAuditoria="+ numeroAuditoria2 +"&NombrePregunta="+ nombrePregunta+"&numeroS="+ numeroS+"");


      //  Toast.makeText(getApplicationContext(),numeroS,Toast.LENGTH_SHORT).show();




        Button buttonApply = findViewById(R.id.applyButton);
////////////////////////////////////////////////////////////////////////////////////Clicks para Agregar Imagen
        mimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        mimageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        mimageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        mimageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        mimageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        imageViewQquestionMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog9 dialog9 = new Dialog9();
                dialog9.show(getSupportFragmentManager(), "example dialog");
            }
        });

        buttonApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioId);
                textView.setText(radioButton.getText());


                String radioN = radioButton.getText().toString();

                radioN2=radioN;


                if(!multiText.getText().toString().equals("")) {


                    //  Toast.makeText(getApplicationContext(),"Holaaaa",Toast.LENGTH_SHORT).show();

                    ejecutarservicio("https://vvnorth.com/insertar_AudiFinal.php", nombrePlanta, nombreArea, numeroAuditoria, nombrePregunta, nombrePlanta, radioN, numeroS);

                    FinalConditions(nombrePlanta,nombreArea,numeroAuditoria);

                  //  agregarArea(nombrePlanta, nombreArea, numeroAuditoria, "null");

                  //  nextQuestion();//

                    // nextCondition;
                }
                else if(radioButton.getText().toString().equals("5")){

                    ejecutarservicio("https://vvnorth.com/insertar_AudiFinal.php", nombrePlanta, nombreArea, numeroAuditoria, nombrePregunta, nombrePlanta, radioN, numeroS);

                   // agregarArea(nombrePlanta, nombreArea, numeroAuditoria, "null");
                  //  buscarProductoCondicion(ServerName+"/Condicion_Pregunta.php?NumeroAuditoria="+"174247410" +"&NombreId="+"638"+"");

                   FinalConditions(nombrePlanta,nombreArea,numeroAuditoria);



                }else{

                    Dialog4 dialog4 = new Dialog4();
                    dialog4.show(getSupportFragmentManager(), "example dialog");

                }
            }
        });




        fabFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();

            }
        });

        fabGfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                seeImage(nombrePregunta,subArea);

            }
        });
    }

public void FinalConditions(final String nombrePlanta,final String nombreArea,final String numeroAuditoria)
{
    //textViewPruebas.setText(nextCondition);
    String nextCondition2=nextCondition;
    if(nextCondition2.equals("Vacio")) {
          agregarArea(nombrePlanta, nombreArea, numeroAuditoria, "null");
      // Toast.makeText(getApplicationContext(),"No tiene siguiente",Toast.LENGTH_SHORT).show();
    }
    else{
        nextQuestion();
    //   Toast.makeText(getApplicationContext(),"Si tiene siguiente",Toast.LENGTH_SHORT).show();
    }

}
    public void seeImage(final String nombrePregunta,final String subArea)
    {

        Intent intent =new Intent(this,DownloadImage.class);
        intent.putExtra("EXTRA_SESSION_ID", nombrePregunta);
        intent.putExtra("EXTRA_SESSION_ID2", subArea);

        startActivity(intent);
    }

    public void openDialog()
    {
        DialogOptions2 dialogOptions2 = new DialogOptions2();
        dialogOptions2.show(getSupportFragmentManager(),"example Dialog2");

    }

    public void openDialog2()
    {
        ExampleDialogData exampleDialogData= new ExampleDialogData();
        exampleDialogData.show(getSupportFragmentManager(),"example dialog");

    }


    @Override
    public void onYesClicked() {
        String fileName="photo";
        File StorageDirectory= getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        try {
            File imageFile=File.createTempFile(fileName,".jpg",StorageDirectory);
            currentPhotoPath=imageFile.getAbsolutePath();
            Uri imageUri=  FileProvider.getUriForFile(AuditoriaLlenado.this,
                    "com.example.a5sghoner.fileprovider",imageFile);

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
    public void agregarArea(String nombrePlanta,String nombreArea,String nombreSubarea,String nombreAuditor)
    {
        Intent intent =new Intent(this,AreasAuditoria.class);
        intent.putExtra("EXTRA_SESSION_ID", nombrePlanta);
        intent.putExtra("EXTRA_SESSION_ID2", nombreArea);
        intent.putExtra("EXTRA_SESSION_ID3", nombreSubarea);
        intent.putExtra("EXTRA_SESSION_ID4", nombreAuditor);
        intent.putExtra("INICIO", numeroSGlobal);
        startActivity(intent);
    }
    public void takePicture(View view) {

        Intent imageTakeIntent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(imageTakeIntent.resolveActivity(getPackageManager())!=null)
        {
            startActivityForResult(imageTakeIntent,Request_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);




        if(requestCode==1 && resultCode == RESULT_OK) {

            imageNumber++;

            String numberAsString = String.valueOf(imageNumber);



            if (imageNumber == 1) {

                Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
                ImageView imageView = findViewById(R.id.imageView19);
                imageView.setImageBitmap(bitmap);
                bitmapf = bitmap;
                cantidadFotos=1;

            }
            if (imageNumber == 2) {
                Bitmap bitmap2 = BitmapFactory.decodeFile(currentPhotoPath);
                ImageView imageView2 = findViewById(R.id.imageView20);
                imageView2.setImageBitmap(bitmap2);
                bitmapf2 = bitmap2;
                cantidadFotos=2;
            }
            if (imageNumber == 3) {
                Bitmap bitmap3 = BitmapFactory.decodeFile(currentPhotoPath);
                ImageView imageView3 = findViewById(R.id.imageView21);
                imageView3.setImageBitmap(bitmap3);
                bitmapf3 = bitmap3;
                cantidadFotos=3;
                ;
            }
            if (imageNumber == 4) {
                Bitmap bitmap4 = BitmapFactory.decodeFile(currentPhotoPath);
                ImageView imageView4 = findViewById(R.id.imageView22);
                imageView4.setImageBitmap(bitmap4);
                bitmapf4 = bitmap4;
                cantidadFotos=4;
            }

            if (imageNumber == 5) {
                Bitmap bitmap5 = BitmapFactory.decodeFile(currentPhotoPath);
                ImageView imageView5 = findViewById(R.id.imageView23);
                imageView5.setImageBitmap(bitmap5);
                bitmapf5 = bitmap5;
                cantidadFotos=5;
            }


        }

        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {

            imageNumber++;
            if(imageNumber ==1){
                //    mimageView.setImageURI(data.getData());
                mimageView.setImageURI(data.getData());

                Bitmap bitmap = ((BitmapDrawable)mimageView.getDrawable()).getBitmap();

                bitmapf=bitmap;
                cantidadFotos=1;
            }
            if(imageNumber ==2) {
                mimageView2.setImageURI(data.getData());

                Bitmap bitmap = ((BitmapDrawable) mimageView2.getDrawable()).getBitmap();

                bitmapf2 = bitmap;
                cantidadFotos=2;
            }
            if(imageNumber ==3){
                mimageView3.setImageURI(data.getData());

                Bitmap bitmap = ((BitmapDrawable)mimageView3.getDrawable()).getBitmap();

                bitmapf3=bitmap;
                cantidadFotos=3;}
            if(imageNumber ==4){
                mimageView4.setImageURI(data.getData());

                Bitmap bitmap = ((BitmapDrawable)mimageView4.getDrawable()).getBitmap();

                bitmapf4=bitmap;
                cantidadFotos=4;}
            if(imageNumber ==5){
                mimageView5.setImageURI(data.getData());

                Bitmap bitmap = ((BitmapDrawable)mimageView5.getDrawable()).getBitmap();

                bitmapf5=bitmap;
                cantidadFotos=5;}
        }
        openDialog2();

    }

    private void ejecutarservicio(String URL,final String NPlanta,final String NArea,final String NSubArea,final String NS,final String nombreS,final String radioN,final String numeroS)
    {
        StringRequest stringRequest=new  StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //     Toast.makeText(getApplicationContext(), "OPERACION EXITOSA", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros =new HashMap<String,String>();
                // int radioId=radioGroup.getCheckedRadioButtonId();
                //  radioButton= findViewById(radioId);
                // String strI = String.valueOf(radioButton.getText());


                parametros.put("Comentario",multiText.getText().toString());
                parametros.put("AuditoriaNumero",NSubArea);
                parametros.put("NombrePregunta",NS);
                parametros.put("5s",numeroS);
                parametros.put("NCalificacion",radioN);
                parametros.put("NombreImagen","Nombre Imagen");

                parametros.put("ComentarioFoto1",text1.getText().toString());
                parametros.put("ComentarioFoto2",text2.getText().toString());
                parametros.put("ComentarioFoto3",text3.getText().toString());
                parametros.put("ComentarioFoto4",text4.getText().toString());
                parametros.put("ComentarioFoto5",text5.getText().toString());







                if(cantidadFotos==1) {
                    String imageData = imageToString(bitmapf);
                    parametros.put("image1", imageData);
                    parametros.put("numeroImagenes","1");
                }
                if(cantidadFotos==2) {
                    String imageData = imageToString(bitmapf);
                    parametros.put("image1", imageData);
                    String imageData2 = imageToString(bitmapf2);
                    parametros.put("image2", imageData2);
                    parametros.put("numeroImagenes","2");
                }

                if(cantidadFotos==3) {
                    String imageData = imageToString(bitmapf);
                    parametros.put("image1", imageData);
                    String imageData2 = imageToString(bitmapf2);
                    parametros.put("image2", imageData2);
                    String imageData3 = imageToString(bitmapf3);
                    parametros.put("image3", imageData3);
                    parametros.put("numeroImagenes","3");
                }

                if(cantidadFotos==4) {
                    String imageData = imageToString(bitmapf);
                    parametros.put("image1", imageData);
                    String imageData2 = imageToString(bitmapf2);
                    parametros.put("image2", imageData2);
                    String imageData3 = imageToString(bitmapf3);
                    parametros.put("image3", imageData3);
                    String imageData4 = imageToString(bitmapf4);
                    parametros.put("image4", imageData4);
                    parametros.put("numeroImagenes","4");

                }

                if(cantidadFotos==5) {
                    String imageData = imageToString(bitmapf);
                    parametros.put("image1", imageData);
                    String imageData2 = imageToString(bitmapf2);
                    parametros.put("image2", imageData2);
                    String imageData3 = imageToString(bitmapf3);
                    parametros.put("image3", imageData3);
                    String imageData4 = imageToString(bitmapf4);
                    parametros.put("image4", imageData4);
                    String imageData5 = imageToString(bitmapf5);
                    parametros.put("image5", imageData5);
                    parametros.put("numeroImagenes","5");

                }




                //    parametros.put("ss",NS); ////////////Nombre de la pregunta

//             //   parametros.put("NombrePlanta",NPlanta);
                //   parametros.put("NombreArea",NArea);
                //    parametros.put("NumeroSS",numeroS);
                //    parametros.put("NombreAreaSS","aaaaaaaaaaaaaaaaaaaaaa");             //    Nombre de la imagen

                //   parametros.put("Comentarios",multiText.getText().toString());

                //  parametros.put("NombreUsiarios","Martin");


                //  parametros.put("NombrenombreArea",NSubArea);

                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        Intent intent =new Intent(this,AreasAuditoria.class);
        intent.putExtra("EXTRA_SESSION_ID", nombrePlanta2);
        intent.putExtra("EXTRA_SESSION_ID2", nombreArea2);
        intent.putExtra("EXTRA_SESSION_ID3", numeroAuditoria2);
        intent.putExtra("INICIO", numeroSGlobal);
        startActivity(intent);



    }
    public void nextQuestion()
    {
        Intent intent =new Intent(this,AuditoriaLlenado.class);

        intent.putExtra("EXTRA_SESSION_ID", nextNombrePlanta);
        intent.putExtra("EXTRA_SESSION_ID2", nextNombreArea);
        intent.putExtra("EXTRA_SESSION_ID3", nextNumeroAuditoria);
        intent.putExtra("EXTRA_SESSION_ID4", nextNombrePregunta);
        intent.putExtra("EXTRA_SESSION_ID5", nextDatos);
        intent.putExtra("EXTRA_SESSION_ID6", nextNumeroS);
        intent.putExtra("EXTRA_SESSION_ID7", nextSubarea);


        startActivity(intent);
    }
    private void buscarProducto(String URL)
    {

        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {



                        jsonObject = response.getJSONObject(i);
                        // editT.setText(jsonObject.getString("Planta"));

                        nextNombrePlanta=jsonObject.getString("Planta");
                        nextNombreArea=jsonObject.getString("Area");
                        nextNumeroAuditoria=jsonObject.getString("AuditoriaNumero");
                        nextNombrePregunta=jsonObject.getString("NombrePregunta");
                        nextDatos=jsonObject.getString("Descripcion");
                        nextNumeroS=jsonObject.getString("5s");
                        nextSubarea=jsonObject.getString("SubArea");
                        currentId=jsonObject.getString("Id");

                       // buscarProducto2(ServerName+"/Condicion_Pregunta.php");

                       // textViewPruebas.setText("hola");
                        // boton(nombre,i,nombrePlanta);
                       //  textViewPruebas.setText(currentId);

                    } catch (JSONException e) {
                      //  agregarArea(nombrePlanta, nombreArea, numeroAuditoria, "null");
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                 //    Toast.makeText(getApplicationContext(),"ERRO DE CONEXION de la 1",Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void buscarProducto2(String URL)
    {

        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {



                        jsonObject = response.getJSONObject(i);
                        // editT.setText(jsonObject.getString("Planta"));

                        nextCondition=jsonObject.getString("id");
                  //    Toast.makeText(getApplicationContext(),nextCondition,Toast.LENGTH_SHORT).show();

                    //    Toast.makeText(getApplicationContext(),nextCondition,Toast.LENGTH_SHORT).show();

                        // boton(nombre,i,nombrePlanta);
                    //   textViewPruebas.setText(nextCondition);

                    } catch (JSONException e) {
                        //  agregarArea(nombrePlanta, nombreArea, numeroAuditoria, "null");
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              //     Toast.makeText(getApplicationContext(),"ERRO DE CONEXION",Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }




    public void checkButton (View v)
    {
        int radioId=radioGroup.getCheckedRadioButtonId();
        radioButton= findViewById(radioId);

        //  Toast.makeText(this,radioButton.getText(),Toast.LENGTH_SHORT).show();

    }

    private String imageToString(Bitmap bitmap)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50, outputStream);
        byte[] imageBytes= outputStream.toByteArray();
        String encodeImage= Base64.encodeToString(imageBytes,Base64.DEFAULT);
        return encodeImage;
    }
    private void pickImageFromGallery()
    {
// int to pick image

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_CODE);

    }
    // handel Request of runtime permission

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

    @Override
    public void applyTexts(String username) {

        if (imageNumber == 1) {
            text1.setText(username);
        }
        if (imageNumber == 2) {
            text2.setText(username);
        }
        if (imageNumber == 3) {
            text3.setText(username);
        }
        if (imageNumber == 4) {
            text4.setText(username);
        }
        if (imageNumber == 5) {
            text5.setText(username);
        }
    }
}
