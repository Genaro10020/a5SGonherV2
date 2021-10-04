package com.example.a5SGonher;

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
import android.widget.Toast;

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

public class CambiarS extends AppCompatActivity implements DialogOptions2.DialogOptions1Listener{
    TextView tView;
    Button btnAgregarS;
    EditText edtRquermiento,edtDatos;
    RadioGroup radioGroup;
    RadioButton radioButton;
    RequestQueue requestQueue;
    private ImageView myImageView1,myImageView2,myImageView3,myImageView4,myImageView5;

    Bitmap bitmapf1;
    Bitmap bitmapf2;
    Bitmap bitmapf3;
    Bitmap bitmapf4;
    Bitmap bitmapf5;
    int imagenesCondition=0;
    private String currentPhotoPath;
    private static final int IMAGE_PICK_CODE=1000;
    private  static  final int PERMISSION_CODE=1001;
    private static final int Request_IMAGE_CAPTURE=101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_s);
        tView=(TextView)findViewById(R.id.textViewSS);
        edtRquermiento=(EditText)findViewById(R.id.requText);
        edtDatos=(EditText)findViewById(R.id.datosText);
        myImageView1=(ImageView) findViewById(R.id.imageView19);
        myImageView2=(ImageView) findViewById(R.id.imageView20);
        myImageView3=(ImageView) findViewById(R.id.imageView21);
        myImageView4=(ImageView) findViewById(R.id.imageView22);
        myImageView5=(ImageView) findViewById(R.id.imageView23);
        btnAgregarS=(Button) findViewById(R.id.botonSS);
        final String nombrePlanta = getIntent().getStringExtra("EXTRA_SESSION_ID");
        final String nombreArea = getIntent().getStringExtra("EXTRA_SESSION_ID2");
        final String nombreSubArea = getIntent().getStringExtra("EXTRA_SESSION_ID3");
        final String nombreS = getIntent().getStringExtra("EXTRA_SESSION_ID4");
        radioGroup=findViewById(R.id.radioGroup);
        tView.setText(nombreS);

        myImageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        myImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        myImageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        myImageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        myImageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });


        btnAgregarS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ejecutarservicio("https://vvnorth.com/cambiar_s.php",nombrePlanta,nombreArea,nombreSubArea,nombreS);
                // openActivity(nombrePlanta,nombreArea,nombreSubArea);



            }
        });
    }
    public void checkButton (View v)
    {
        int radioId=radioGroup.getCheckedRadioButtonId();
        radioButton= findViewById(radioId);

        Toast.makeText(this,radioButton.getText(),Toast.LENGTH_SHORT).show();

    }

    public void openDialog()
    {
        DialogOptions2 dialogOptions2 = new DialogOptions2();
        dialogOptions2.show(getSupportFragmentManager(),"example Dialog2");

    }


    public void openActivity(final String nombrePlanta,final String nombreArea,final String estado,final String nombreSubArea)
    {

        if(estado.equals("true"))
        {


            Intent intent =new Intent(this,AgregarS.class);
            intent.putExtra("EXTRA_SESSION_ID", nombrePlanta);
            intent.putExtra("EXTRA_SESSION_ID2", nombreArea);
            intent.putExtra("EXTRA_SESSION_ID3", nombreSubArea);

            startActivity(intent);
        }
        else{
            Dialog dialog = new Dialog();
            dialog.show(getSupportFragmentManager(),"example dialog");

        }

    }

    @Override
    public void onYesClicked() {
        String fileName="photo";
        File StorageDirectory= getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        try {
            File imageFile=File.createTempFile(fileName,".jpg",StorageDirectory);
            currentPhotoPath=imageFile.getAbsolutePath();
            Uri imageUri=  FileProvider.getUriForFile(CambiarS.this,
                    "com.example.a5sghoner.fileprovider",imageFile);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
            startActivityForResult(intent,1);

        } catch (IOException e) {
            e.printStackTrace();
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
    private void pickImageFromGallery()
    {
// int to pick image

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_CODE);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode == RESULT_OK)
        {
            if(imagenesCondition==0)
            {
                Bitmap bitmap1= BitmapFactory.decodeFile(currentPhotoPath);
                ImageView imageView =findViewById(R.id.imageView19);
                imageView.setImageBitmap(bitmap1);
                bitmapf1=bitmap1;
            }

            if(imagenesCondition==1)
            {
                Bitmap bitmap2= BitmapFactory.decodeFile(currentPhotoPath);
                ImageView imageView1 =findViewById(R.id.imageView20);
                imageView1.setImageBitmap(bitmap2);
                bitmapf2=bitmap2;
            }

            if(imagenesCondition==2)
            {
                Bitmap bitmap3= BitmapFactory.decodeFile(currentPhotoPath);
                ImageView imageView3 =findViewById(R.id.imageView21);
                imageView3.setImageBitmap(bitmap3);
                bitmapf3=bitmap3;
            }

            if(imagenesCondition==3)
            {
                Bitmap bitmap4= BitmapFactory.decodeFile(currentPhotoPath);
                ImageView imageView4 =findViewById(R.id.imageView22);
                imageView4.setImageBitmap(bitmap4);
                bitmapf4=bitmap4;
            }

            if(imagenesCondition==4)
            {
                Bitmap bitmap5= BitmapFactory.decodeFile(currentPhotoPath);
                ImageView imageView5 =findViewById(R.id.imageView23);
                imageView5.setImageBitmap(bitmap5);
                bitmapf5=bitmap5;
            }
            imagenesCondition++;
        }


        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
//set image to image view
            if(imagenesCondition==0)
            {
                myImageView1.setImageURI(data.getData());
                Bitmap bitmap1 = ((BitmapDrawable)myImageView1.getDrawable()).getBitmap();
                bitmapf1=bitmap1;

            }
            if(imagenesCondition==1)
            {
                myImageView2.setImageURI(data.getData());
                Bitmap bitmap2 = ((BitmapDrawable)myImageView2.getDrawable()).getBitmap();
                bitmapf2=bitmap2;

            }
            if(imagenesCondition==2)
            {
                myImageView3.setImageURI(data.getData());
                Bitmap bitmap3 = ((BitmapDrawable)myImageView3.getDrawable()).getBitmap();
                bitmapf3=bitmap3;

            }
            if(imagenesCondition==3)
            {
                myImageView4.setImageURI(data.getData());
                Bitmap bitmap4 = ((BitmapDrawable)myImageView4.getDrawable()).getBitmap();
                bitmapf4=bitmap4;


            }
            if(imagenesCondition==4)
            {
                myImageView5.setImageURI(data.getData());
                Bitmap bitmap5 = ((BitmapDrawable)myImageView5.getDrawable()).getBitmap();
                bitmapf5=bitmap5;

            }
            imagenesCondition++;


        }

    }


    private void buscarProducto(String URL,final String nombrePlanta,final String nombreArea,final String nombreSubArea)
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

                        openActivity(nombrePlanta,nombreArea,estado,nombreSubArea);


                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void ejecutarservicio(String URL,final String NPlanta,final String NArea,final String NSubArea,final String cambio)
    {
        StringRequest stringRequest=new  StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                buscarProducto("https://vvnorth.com/comparacion_auditorf.php",NPlanta,NArea,NSubArea);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros =new HashMap<String,String>();
                int radioId=radioGroup.getCheckedRadioButtonId();
                radioButton= findViewById(radioId);
                String strI = String.valueOf(radioButton.getText());

               // String imageData1= imageToString(bitmapf1);
                //parametros.put("image1",imageData1);

                parametros.put("ss",edtRquermiento.getText().toString());
                parametros.put("datos",edtDatos.getText().toString());
                parametros.put("NombrePlanta",NPlanta);
                parametros.put("NombreArea",NArea);
                parametros.put("NombreAreaSS",strI);
                parametros.put("NombrenombreArea",NSubArea);
                parametros.put("Cambio",cambio);


                if(imagenesCondition==1|| imagenesCondition==2 || imagenesCondition==3 ||imagenesCondition==4 ||imagenesCondition==5) {
                    String imageData= imageToString(bitmapf1);
                    parametros.put("image1",imageData);
                }

                if(imagenesCondition==2 || imagenesCondition==3 ||imagenesCondition==4 ||imagenesCondition==5) {
                    String imageData2= imageToString(bitmapf2);
                    parametros.put("image2",imageData2);
                }
                if(imagenesCondition==3 ||imagenesCondition==4 ||imagenesCondition==5) {
                    String imageData3= imageToString(bitmapf3);
                    parametros.put("image3",imageData3);
                }

                if(imagenesCondition==4 ||imagenesCondition==5 ) {
                    String imageData4= imageToString(bitmapf4);
                    parametros.put("image4",imageData4);

                }

                if(imagenesCondition==5) {
                    String imageData5= imageToString(bitmapf5);
                    parametros.put("image5",imageData5);
                }

                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
