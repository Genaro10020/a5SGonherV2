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
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FormularioSubarea extends AppCompatActivity implements DialogOptions2.DialogOptions1Listener{
    private static final int IMAGE_PICK_CODE=1000;
    private  static  final int PERMISSION_CODE=1001;
    TextView tView;
    Button btnAgregarS;
    EditText edtRquermiento,edtDatos;
    RequestQueue requestQueue;
    private ImageView myImageView;
    Bitmap bitmapf;
    private String currentPhotoPath;
    String urlUpload="https://vvnorth.com/uploadSubarea.php";
    private static final int Request_IMAGE_CAPTURE=101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_subarea);
      //  tView=(TextView)findViewById(R.id.textViewSS);
        edtRquermiento=(EditText)findViewById(R.id.requText);
        edtDatos=(EditText)findViewById(R.id.datosText);
        btnAgregarS=(Button) findViewById(R.id.botonSS);
        final String nombrePlanta = getIntent().getStringExtra("EXTRA_SESSION_ID");
        final String nombreArea = getIntent().getStringExtra("EXTRA_SESSION_ID2");


        myImageView=(ImageView) findViewById(R.id.imageViewSubArea);

        myImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });


       // tView.setText(nombreArea);
        btnAgregarS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ejecutarservicio("https://vvnorth.com/insertar_subarea.php",nombrePlanta,nombreArea);
                StringRequest stringRequest= new StringRequest(Request.Method.POST, urlUpload, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                 //       Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                   //     Toast.makeText(getApplicationContext(),"error:" + error.toString(),Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params= new HashMap<>();

                        String imageData= imageToString(bitmapf);

                        params.put("image",imageData);
                        params.put("Nombreimage",edtRquermiento.getText().toString());

                        return params;
                    }
                };
                RequestQueue requestQueue= Volley.newRequestQueue(FormularioSubarea.this);
                requestQueue.add(stringRequest);
              //  openActivity(nombrePlanta,nombreArea);
            }
        });
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
            Uri imageUri=  FileProvider.getUriForFile(FormularioSubarea.this,
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
    private String imageToString(Bitmap bitmap)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50, outputStream);
        byte[] imageBytes= outputStream.toByteArray();
        String encodeImage= Base64.encodeToString(imageBytes,Base64.DEFAULT);
        return encodeImage;
    }
    public void openActivity(final String nombrePlanta,final String nombreArea,final String estado)
    {

        if(estado.equals("true"))
        {


            Intent intent =new Intent(this,AgregarSubarea.class);
            intent.putExtra("EXTRA_SESSION_ID", nombrePlanta);
            intent.putExtra("EXTRA_SESSION_ID2", nombreArea);
            startActivity(intent);
        }
        else{
            Dialog dialog = new Dialog();
            dialog.show(getSupportFragmentManager(),"example dialog");

        }

    }

    private void buscarProducto(String URL,final String nombrePlanta,final String nombreArea)
    {

        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        String estado;
                        jsonObject = response.getJSONObject(i);
                        // editT.setText(jsonObject.getString("Planta"));
                        estado=jsonObject.getString("estado");

                        openActivity(nombrePlanta,nombreArea,estado);


                    } catch (JSONException e) {
                    //
                        //
                        //    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Toast.makeText(getApplicationContext(),"ERRO DE CONEXION",Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode == RESULT_OK)
        {
            Bitmap bitmap= BitmapFactory.decodeFile(currentPhotoPath);

            ImageView imageView =findViewById(R.id.imageViewSubArea);
            imageView.setImageBitmap(bitmap);
            bitmapf=bitmap;
        }


        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
//set image to image view
            myImageView.setImageURI(data.getData());

            Bitmap bitmap = ((BitmapDrawable)myImageView.getDrawable()).getBitmap();

            bitmapf=bitmap;


        }

    }

    private void ejecutarservicio(String URL,final String NPlanta,final String NArea)
    {
        StringRequest stringRequest=new  StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                buscarProducto("https://vvnorth.com/comparacion_auditorf.php",NPlanta,NArea);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
             //   Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros =new HashMap<String,String>();
                String imageData= imageToString(bitmapf);
                parametros.put("image",imageData);
                parametros.put("ss",edtRquermiento.getText().toString());
                parametros.put("NombrePlanta",NPlanta);
                parametros.put("NombreArea",NArea);

                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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
    //handle result of pick of image



}
