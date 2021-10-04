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
import android.widget.TableLayout;
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

public class FormularioS extends AppCompatActivity implements DialogOptions2.DialogOptions1Listener{
    TextView tView;
    Button btnAgregarS;
    EditText edtRquermiento,edtDatos;
    RadioGroup radioGroup;
    RadioButton radioButton;
    RequestQueue requestQueue;
    Bitmap bitmapf;
    Bitmap bitmapf1;
    Bitmap bitmapf2;
    Bitmap bitmapf3;
    Bitmap bitmapf4;
    Bitmap bitmapf5,bitmapf6,bitmapf7,bitmapf8,bitmapf9,bitmapf10,bitmapf11,bitmapf12,bitmapf13,bitmapf14,bitmapf15,bitmapf16,bitmapf17,bitmapf18,bitmapf19,bitmapf20;
    Bitmap bitmapf21,bitmapf22,bitmapf23,bitmapf24,bitmapf25,bitmapf26,bitmapf27,bitmapf28,bitmapf29,bitmapf30;
    int imagenesCondition=0;
    private String currentPhotoPath;
    private static final int IMAGE_PICK_CODE=1000;
    private  static  final int PERMISSION_CODE=1001;
    private static final int Request_IMAGE_CAPTURE=101;
    private ImageView myImageView;
    private ImageView myImageView1;
    private ImageView myImageView2;
    private ImageView myImageView3;
    private ImageView myImageView4;
    private ImageView myImageView5,myImageView6,myImageView7,myImageView8,myImageView9,myImageView10,myImageView11,myImageView12,myImageView13,myImageView14,myImageView15,myImageView16,myImageView17;
    private ImageView myImageView18,myImageView19,myImageView20,myImageView21,myImageView22,myImageView23,myImageView24,myImageView25,myImageView26,myImageView27,myImageView28,myImageView29,myImageView30;
    private TableLayout buttonImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_s);
        tView=(TextView)findViewById(R.id.textViewSS);
        edtRquermiento=(EditText)findViewById(R.id.requText);
        edtDatos=(EditText)findViewById(R.id.datosText);
        btnAgregarS=(Button) findViewById(R.id.botonSS);
        final String nombrePlanta = getIntent().getStringExtra("EXTRA_SESSION_ID");
        final String nombreArea = getIntent().getStringExtra("EXTRA_SESSION_ID2");
        final String nombreSubArea = getIntent().getStringExtra("EXTRA_SESSION_ID3");
        radioGroup=findViewById(R.id.radioGroup);
        tView.setText(nombreSubArea);

        buttonImage=(TableLayout) findViewById(R.id.tableLayout22);
      //  myImageView=(ImageView) findViewById(R.id.imageViewSubArea4);

        myImageView1=(ImageView) findViewById(R.id.imageView19);
        myImageView2=(ImageView) findViewById(R.id.imageView20);
        myImageView3=(ImageView) findViewById(R.id.imageView21);
        myImageView4=(ImageView) findViewById(R.id.imageView22);
        myImageView5=(ImageView) findViewById(R.id.imageView23);

        myImageView6=(ImageView) findViewById(R.id.imageView06);
        myImageView7=(ImageView) findViewById(R.id.imageView07);
        myImageView8=(ImageView) findViewById(R.id.imageView08);
        myImageView9=(ImageView) findViewById(R.id.imageView09);
        myImageView10=(ImageView) findViewById(R.id.imageView010);
        myImageView11=(ImageView) findViewById(R.id.imageView011);
        myImageView12=(ImageView) findViewById(R.id.imageView012);
        myImageView13=(ImageView) findViewById(R.id.imageView013);
        myImageView14=(ImageView) findViewById(R.id.imageView014);
        myImageView15=(ImageView) findViewById(R.id.imageView015);
        myImageView16=(ImageView) findViewById(R.id.imageView016);
        myImageView17=(ImageView) findViewById(R.id.imageView017);
        myImageView18=(ImageView) findViewById(R.id.imageView018);
        myImageView19=(ImageView) findViewById(R.id.imageView019);
        myImageView20=(ImageView) findViewById(R.id.imageView020);
        myImageView21=(ImageView) findViewById(R.id.imageView021);
        myImageView22=(ImageView) findViewById(R.id.imageView022);
        myImageView23=(ImageView) findViewById(R.id.imageView023);
        myImageView24=(ImageView) findViewById(R.id.imageView024);
        myImageView25=(ImageView) findViewById(R.id.imageView025);
        myImageView26=(ImageView) findViewById(R.id.imageView026);
        myImageView27=(ImageView) findViewById(R.id.imageView027);
        myImageView28=(ImageView) findViewById(R.id.imageView028);
        myImageView29=(ImageView) findViewById(R.id.imageView029);
        myImageView30=(ImageView) findViewById(R.id.imageView030);




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
                ejecutarservicio("https://vvnorth.com/insertar_s.php",nombrePlanta,nombreArea,nombreSubArea);
               // openActivity(nombrePlanta,nombreArea,nombreSubArea);



            }
        });
    }
    public void checkButton (View v)
    {
        int radioId=radioGroup.getCheckedRadioButtonId();
        radioButton= findViewById(radioId);

       // Toast.makeText(this,radioButton.getText(),Toast.LENGTH_SHORT).show();

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
            Uri imageUri=  FileProvider.getUriForFile(FormularioS.this,
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
                 //       Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void ejecutarservicio(String URL,final String NPlanta,final String NArea,final String NSubArea)
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


                parametros.put("ss",edtRquermiento.getText().toString());
                parametros.put("datos",edtDatos.getText().toString());
                parametros.put("NombrePlanta",NPlanta);
                parametros.put("NombreArea",NArea);
                parametros.put("NombreAreaSS",strI);


                parametros.put("NombrenombreArea",NSubArea);
                if(imagenesCondition==0)
                {
                    parametros.put("NumeroImagenes","0");
                }

                if(imagenesCondition==1|| imagenesCondition==2 || imagenesCondition==3 ||imagenesCondition==4 ||imagenesCondition==5||imagenesCondition==6||imagenesCondition==7||imagenesCondition==8
                        ||imagenesCondition==9||imagenesCondition==10||imagenesCondition==11||imagenesCondition==12||imagenesCondition==13||imagenesCondition==14||imagenesCondition==15
                        ||imagenesCondition==16||imagenesCondition==17||imagenesCondition==18||imagenesCondition==19||imagenesCondition==20||imagenesCondition==21||imagenesCondition==22
                        ||imagenesCondition==23||imagenesCondition==24||imagenesCondition==25||imagenesCondition==26||imagenesCondition==27||imagenesCondition==28||imagenesCondition==29
                        ||imagenesCondition==30) {
                    String imageData= imageToString(bitmapf1);
                    parametros.put("image1",imageData);
                    parametros.put("NumeroImagenes","1");
                }

                if(imagenesCondition==2 || imagenesCondition==3 ||imagenesCondition==4 ||imagenesCondition==5||imagenesCondition==6||imagenesCondition==7||imagenesCondition==8
                        ||imagenesCondition==9||imagenesCondition==10||imagenesCondition==11||imagenesCondition==12||imagenesCondition==13||imagenesCondition==14||imagenesCondition==15
                        ||imagenesCondition==16||imagenesCondition==17||imagenesCondition==18||imagenesCondition==19||imagenesCondition==20||imagenesCondition==21||imagenesCondition==22
                        ||imagenesCondition==23||imagenesCondition==24||imagenesCondition==25||imagenesCondition==26||imagenesCondition==27||imagenesCondition==28||imagenesCondition==29
                        ||imagenesCondition==30) {
                    String imageData2= imageToString(bitmapf2);
                    parametros.put("image2",imageData2);
                    parametros.put("NumeroImagenes","2");
                 }
                if( imagenesCondition==3 ||imagenesCondition==4 ||imagenesCondition==5||imagenesCondition==6||imagenesCondition==7||imagenesCondition==8
                        ||imagenesCondition==9||imagenesCondition==10||imagenesCondition==11||imagenesCondition==12||imagenesCondition==13||imagenesCondition==14||imagenesCondition==15
                        ||imagenesCondition==16||imagenesCondition==17||imagenesCondition==18||imagenesCondition==19||imagenesCondition==20||imagenesCondition==21||imagenesCondition==22
                        ||imagenesCondition==23||imagenesCondition==24||imagenesCondition==25||imagenesCondition==26||imagenesCondition==27||imagenesCondition==28||imagenesCondition==29
                        ||imagenesCondition==30) {
                    String imageData3= imageToString(bitmapf3);
                    parametros.put("image3",imageData3);
                    parametros.put("NumeroImagenes","3");
                    }

                if( imagenesCondition==4 ||imagenesCondition==5||imagenesCondition==6||imagenesCondition==7||imagenesCondition==8
                        ||imagenesCondition==9||imagenesCondition==10||imagenesCondition==11||imagenesCondition==12||imagenesCondition==13||imagenesCondition==14||imagenesCondition==15
                        ||imagenesCondition==16||imagenesCondition==17||imagenesCondition==18||imagenesCondition==19||imagenesCondition==20||imagenesCondition==21||imagenesCondition==22
                        ||imagenesCondition==23||imagenesCondition==24||imagenesCondition==25||imagenesCondition==26||imagenesCondition==27||imagenesCondition==28||imagenesCondition==29
                        ||imagenesCondition==30) {
                    String imageData4= imageToString(bitmapf4);
                    parametros.put("image4",imageData4);
                    parametros.put("NumeroImagenes","4");

                }

                if(imagenesCondition==5||imagenesCondition==6||imagenesCondition==7||imagenesCondition==8
                        ||imagenesCondition==9||imagenesCondition==10||imagenesCondition==11||imagenesCondition==12||imagenesCondition==13||imagenesCondition==14||imagenesCondition==15
                        ||imagenesCondition==16||imagenesCondition==17||imagenesCondition==18||imagenesCondition==19||imagenesCondition==20||imagenesCondition==21||imagenesCondition==22
                        ||imagenesCondition==23||imagenesCondition==24||imagenesCondition==25||imagenesCondition==26||imagenesCondition==27||imagenesCondition==28||imagenesCondition==29
                        ||imagenesCondition==30) {
                    String imageData5= imageToString(bitmapf5);
                    parametros.put("image5",imageData5);
                    parametros.put("NumeroImagenes","5");
                   }
                if(imagenesCondition==6||imagenesCondition==7||imagenesCondition==8
                        ||imagenesCondition==9||imagenesCondition==10||imagenesCondition==11||imagenesCondition==12||imagenesCondition==13||imagenesCondition==14||imagenesCondition==15
                        ||imagenesCondition==16||imagenesCondition==17||imagenesCondition==18||imagenesCondition==19||imagenesCondition==20||imagenesCondition==21||imagenesCondition==22
                        ||imagenesCondition==23||imagenesCondition==24||imagenesCondition==25||imagenesCondition==26||imagenesCondition==27||imagenesCondition==28||imagenesCondition==29
                        ||imagenesCondition==30) {
                    String imageData6= imageToString(bitmapf6);
                    parametros.put("image6",imageData6);
                    parametros.put("NumeroImagenes","6");
                }
                if(imagenesCondition==7||imagenesCondition==8
                        ||imagenesCondition==9||imagenesCondition==10||imagenesCondition==11||imagenesCondition==12||imagenesCondition==13||imagenesCondition==14||imagenesCondition==15
                        ||imagenesCondition==16||imagenesCondition==17||imagenesCondition==18||imagenesCondition==19||imagenesCondition==20||imagenesCondition==21||imagenesCondition==22
                        ||imagenesCondition==23||imagenesCondition==24||imagenesCondition==25||imagenesCondition==26||imagenesCondition==27||imagenesCondition==28||imagenesCondition==29
                        ||imagenesCondition==30) {
                    String imageData7= imageToString(bitmapf7);
                    parametros.put("image7",imageData7);
                    parametros.put("NumeroImagenes","7");
                }
                if(imagenesCondition==8
                        ||imagenesCondition==9||imagenesCondition==10||imagenesCondition==11||imagenesCondition==12||imagenesCondition==13||imagenesCondition==14||imagenesCondition==15
                        ||imagenesCondition==16||imagenesCondition==17||imagenesCondition==18||imagenesCondition==19||imagenesCondition==20||imagenesCondition==21||imagenesCondition==22
                        ||imagenesCondition==23||imagenesCondition==24||imagenesCondition==25||imagenesCondition==26||imagenesCondition==27||imagenesCondition==28||imagenesCondition==29
                        ||imagenesCondition==30) {
                    String imageData8= imageToString(bitmapf8);
                    parametros.put("image8",imageData8);
                    parametros.put("NumeroImagenes","8");
                }
                if(imagenesCondition==9||imagenesCondition==10||imagenesCondition==11||imagenesCondition==12||imagenesCondition==13||imagenesCondition==14||imagenesCondition==15
                        ||imagenesCondition==16||imagenesCondition==17||imagenesCondition==18||imagenesCondition==19||imagenesCondition==20||imagenesCondition==21||imagenesCondition==22
                        ||imagenesCondition==23||imagenesCondition==24||imagenesCondition==25||imagenesCondition==26||imagenesCondition==27||imagenesCondition==28||imagenesCondition==29
                        ||imagenesCondition==30) {
                    String imageData9= imageToString(bitmapf9);
                    parametros.put("image9",imageData9);
                    parametros.put("NumeroImagenes","9"); }
                if(imagenesCondition==10||imagenesCondition==11||imagenesCondition==12||imagenesCondition==13||imagenesCondition==14||imagenesCondition==15
                        ||imagenesCondition==16||imagenesCondition==17||imagenesCondition==18||imagenesCondition==19||imagenesCondition==20||imagenesCondition==21||imagenesCondition==22
                        ||imagenesCondition==23||imagenesCondition==24||imagenesCondition==25||imagenesCondition==26||imagenesCondition==27||imagenesCondition==28||imagenesCondition==29
                        ||imagenesCondition==30) {
                    String imageData10= imageToString(bitmapf10);
                    parametros.put("image10",imageData10);
                    parametros.put("NumeroImagenes","10"); }
                if(imagenesCondition==11||imagenesCondition==12||imagenesCondition==13||imagenesCondition==14||imagenesCondition==15
                        ||imagenesCondition==16||imagenesCondition==17||imagenesCondition==18||imagenesCondition==19||imagenesCondition==20||imagenesCondition==21||imagenesCondition==22
                        ||imagenesCondition==23||imagenesCondition==24||imagenesCondition==25||imagenesCondition==26||imagenesCondition==27||imagenesCondition==28||imagenesCondition==29
                        ||imagenesCondition==30) {
                    String imageData11= imageToString(bitmapf11);
                    parametros.put("image11",imageData11);
                    parametros.put("NumeroImagenes","11"); }
                if(imagenesCondition==12||imagenesCondition==13||imagenesCondition==14||imagenesCondition==15
                        ||imagenesCondition==16||imagenesCondition==17||imagenesCondition==18||imagenesCondition==19||imagenesCondition==20||imagenesCondition==21||imagenesCondition==22
                        ||imagenesCondition==23||imagenesCondition==24||imagenesCondition==25||imagenesCondition==26||imagenesCondition==27||imagenesCondition==28||imagenesCondition==29
                        ||imagenesCondition==30) {
                    String imageData12= imageToString(bitmapf12);
                    parametros.put("image12",imageData12);
                    parametros.put("NumeroImagenes","12"); }
                if(imagenesCondition==13||imagenesCondition==14||imagenesCondition==15
                        ||imagenesCondition==16||imagenesCondition==17||imagenesCondition==18||imagenesCondition==19||imagenesCondition==20||imagenesCondition==21||imagenesCondition==22
                        ||imagenesCondition==23||imagenesCondition==24||imagenesCondition==25||imagenesCondition==26||imagenesCondition==27||imagenesCondition==28||imagenesCondition==29
                        ||imagenesCondition==30) {
                    String imageData13= imageToString(bitmapf13);
                    parametros.put("image13",imageData13);
                    parametros.put("NumeroImagenes","13"); }
                if(imagenesCondition==14||imagenesCondition==15
                        ||imagenesCondition==16||imagenesCondition==17||imagenesCondition==18||imagenesCondition==19||imagenesCondition==20||imagenesCondition==21||imagenesCondition==22
                        ||imagenesCondition==23||imagenesCondition==24||imagenesCondition==25||imagenesCondition==26||imagenesCondition==27||imagenesCondition==28||imagenesCondition==29
                        ||imagenesCondition==30) {
                    String imageData14= imageToString(bitmapf14);
                    parametros.put("image14",imageData14);
                    parametros.put("NumeroImagenes","14"); }
                if(imagenesCondition==15
                        ||imagenesCondition==16||imagenesCondition==17||imagenesCondition==18||imagenesCondition==19||imagenesCondition==20||imagenesCondition==21||imagenesCondition==22
                        ||imagenesCondition==23||imagenesCondition==24||imagenesCondition==25||imagenesCondition==26||imagenesCondition==27||imagenesCondition==28||imagenesCondition==29
                        ||imagenesCondition==30) {
                    String imageData15= imageToString(bitmapf15);
                    parametros.put("image15",imageData15);
                    parametros.put("NumeroImagenes","15"); }
                if(imagenesCondition==16||imagenesCondition==17||imagenesCondition==18||imagenesCondition==19||imagenesCondition==20||imagenesCondition==21||imagenesCondition==22
                        ||imagenesCondition==23||imagenesCondition==24||imagenesCondition==25||imagenesCondition==26||imagenesCondition==27||imagenesCondition==28||imagenesCondition==29
                        ||imagenesCondition==30) {
                    String imageData16= imageToString(bitmapf16);
                    parametros.put("image16",imageData16);
                    parametros.put("NumeroImagenes","16"); }
                if(imagenesCondition==17||imagenesCondition==18||imagenesCondition==19||imagenesCondition==20||imagenesCondition==21||imagenesCondition==22
                        ||imagenesCondition==23||imagenesCondition==24||imagenesCondition==25||imagenesCondition==26||imagenesCondition==27||imagenesCondition==28||imagenesCondition==29
                        ||imagenesCondition==30) {
                    String imageData17= imageToString(bitmapf17);
                    parametros.put("image17",imageData17);
                    parametros.put("NumeroImagenes","17"); }
                if(imagenesCondition==18||imagenesCondition==19||imagenesCondition==20||imagenesCondition==21||imagenesCondition==22
                        ||imagenesCondition==23||imagenesCondition==24||imagenesCondition==25||imagenesCondition==26||imagenesCondition==27||imagenesCondition==28||imagenesCondition==29
                        ||imagenesCondition==30) {
                    String imageData18= imageToString(bitmapf18);
                    parametros.put("image18",imageData18);
                    parametros.put("NumeroImagenes","18"); }
                if(imagenesCondition==19||imagenesCondition==20||imagenesCondition==21||imagenesCondition==22
                        ||imagenesCondition==23||imagenesCondition==24||imagenesCondition==25||imagenesCondition==26||imagenesCondition==27||imagenesCondition==28||imagenesCondition==29
                        ||imagenesCondition==30) {
                    String imageData19= imageToString(bitmapf19);
                    parametros.put("image19",imageData19);
                    parametros.put("NumeroImagenes","19"); }
                if(imagenesCondition==20||imagenesCondition==21||imagenesCondition==22
                        ||imagenesCondition==23||imagenesCondition==24||imagenesCondition==25||imagenesCondition==26||imagenesCondition==27||imagenesCondition==28||imagenesCondition==29
                        ||imagenesCondition==30) {
                    String imageData20= imageToString(bitmapf20);
                    parametros.put("image20",imageData20);
                    parametros.put("NumeroImagenes","20"); }
                if(imagenesCondition==21||imagenesCondition==22
                        ||imagenesCondition==23||imagenesCondition==24||imagenesCondition==25||imagenesCondition==26||imagenesCondition==27||imagenesCondition==28||imagenesCondition==29
                        ||imagenesCondition==30) {
                    String imageData21= imageToString(bitmapf21);
                    parametros.put("image21",imageData21);
                    parametros.put("NumeroImagenes","21"); }
                if(imagenesCondition==22
                        ||imagenesCondition==23||imagenesCondition==24||imagenesCondition==25||imagenesCondition==26||imagenesCondition==27||imagenesCondition==28||imagenesCondition==29
                        ||imagenesCondition==30) {
                    String imageData22= imageToString(bitmapf22);
                    parametros.put("image22",imageData22);
                    parametros.put("NumeroImagenes","22"); }
                if(imagenesCondition==23||imagenesCondition==24||imagenesCondition==25||imagenesCondition==26||imagenesCondition==27||imagenesCondition==28||imagenesCondition==29
                        ||imagenesCondition==30) {
                    String imageData23= imageToString(bitmapf23);
                    parametros.put("image23",imageData23);
                    parametros.put("NumeroImagenes","23"); }
                if(imagenesCondition==24||imagenesCondition==25||imagenesCondition==26||imagenesCondition==27||imagenesCondition==28||imagenesCondition==29
                        ||imagenesCondition==30) {
                    String imageData24= imageToString(bitmapf24);
                    parametros.put("image24",imageData24);
                    parametros.put("NumeroImagenes","24"); }
                if(imagenesCondition==25||imagenesCondition==26||imagenesCondition==27||imagenesCondition==28||imagenesCondition==29
                        ||imagenesCondition==30) {
                    String imageData25= imageToString(bitmapf25);
                    parametros.put("image25",imageData25);
                    parametros.put("NumeroImagenes","25"); }
                if(imagenesCondition==26||imagenesCondition==27||imagenesCondition==28||imagenesCondition==29
                        ||imagenesCondition==30) {
                    String imageData26= imageToString(bitmapf26);
                    parametros.put("image26",imageData26);
                    parametros.put("NumeroImagenes","26"); }
                if(imagenesCondition==27||imagenesCondition==28||imagenesCondition==29
                        ||imagenesCondition==30) {
                    String imageData27= imageToString(bitmapf27);
                    parametros.put("image27",imageData27);
                    parametros.put("NumeroImagenes","27"); }
                if(imagenesCondition==28||imagenesCondition==29
                        ||imagenesCondition==30) {
                    String imageData28= imageToString(bitmapf28);
                    parametros.put("image28",imageData28);
                    parametros.put("NumeroImagenes","28"); }
                if(imagenesCondition==29
                        ||imagenesCondition==30) {
                    String imageData29= imageToString(bitmapf29);
                    parametros.put("image29",imageData29);
                    parametros.put("NumeroImagenes","29"); }
                if(imagenesCondition==30) {
                    String imageData30= imageToString(bitmapf30);
                    parametros.put("image30",imageData30);
                    parametros.put("NumeroImagenes","30"); }



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
            { Bitmap bitmap5= BitmapFactory.decodeFile(currentPhotoPath);
                ImageView imageView5 =findViewById(R.id.imageView23);
                imageView5.setImageBitmap(bitmap5);
                bitmapf5=bitmap5; }
            ////----------------------------------------------------
            if(imagenesCondition==5)
        { Bitmap bitmap6= BitmapFactory.decodeFile(currentPhotoPath);
            ImageView imageView6 =findViewById(R.id.imageView06);
            imageView6.setImageBitmap(bitmap6);
            bitmapf6=bitmap6; }
            if(imagenesCondition==6)
            { Bitmap bitmap7= BitmapFactory.decodeFile(currentPhotoPath);
                ImageView imageView7 =findViewById(R.id.imageView07);
                imageView7.setImageBitmap(bitmap7);
                bitmapf7=bitmap7; }
            if(imagenesCondition==7)
            { Bitmap bitmap8= BitmapFactory.decodeFile(currentPhotoPath);
                ImageView imageView8 =findViewById(R.id.imageView08);
                imageView8.setImageBitmap(bitmap8);
                bitmapf8=bitmap8; }
            if(imagenesCondition==8)
            { Bitmap bitmap9= BitmapFactory.decodeFile(currentPhotoPath);
                ImageView imageView9 =findViewById(R.id.imageView09);
                imageView9.setImageBitmap(bitmap9);
                bitmapf9=bitmap9; }
            if(imagenesCondition==9)
            { Bitmap bitmap10= BitmapFactory.decodeFile(currentPhotoPath);
                ImageView imageView10 =findViewById(R.id.imageView010);
                imageView10.setImageBitmap(bitmap10);
                bitmapf10=bitmap10; }
            if(imagenesCondition==10)
            { Bitmap bitmap11= BitmapFactory.decodeFile(currentPhotoPath);
                ImageView imageView11 =findViewById(R.id.imageView011);
                imageView11.setImageBitmap(bitmap11);
                bitmapf11=bitmap11; }
            if(imagenesCondition==11)
            { Bitmap bitmap12= BitmapFactory.decodeFile(currentPhotoPath);
                ImageView imageView12 =findViewById(R.id.imageView012);
                imageView12.setImageBitmap(bitmap12);
                bitmapf12=bitmap12; }
            if(imagenesCondition==12)
            { Bitmap bitmap13= BitmapFactory.decodeFile(currentPhotoPath);
                ImageView imageView13 =findViewById(R.id.imageView013);
                imageView13.setImageBitmap(bitmap13);
                bitmapf13=bitmap13; }
            if(imagenesCondition==13)
        { Bitmap bitmap14= BitmapFactory.decodeFile(currentPhotoPath);
            ImageView imageView14 =findViewById(R.id.imageView014);
            imageView14.setImageBitmap(bitmap14);
            bitmapf14=bitmap14; }
            if(imagenesCondition==14)
            { Bitmap bitmap15= BitmapFactory.decodeFile(currentPhotoPath);
                ImageView imageView15 =findViewById(R.id.imageView015);
                imageView15.setImageBitmap(bitmap15);
                bitmapf15=bitmap15; }
            if(imagenesCondition==15)
            { Bitmap bitmap16= BitmapFactory.decodeFile(currentPhotoPath);
                ImageView imageView16 =findViewById(R.id.imageView016);
                imageView16.setImageBitmap(bitmap16);
                bitmapf16=bitmap16; }
            if(imagenesCondition==16)
            { Bitmap bitmap17= BitmapFactory.decodeFile(currentPhotoPath);
                ImageView imageView17 =findViewById(R.id.imageView017);
                imageView17.setImageBitmap(bitmap17);
                bitmapf17=bitmap17; }
            if(imagenesCondition==17)
            { Bitmap bitmap18= BitmapFactory.decodeFile(currentPhotoPath);
                ImageView imageView18 =findViewById(R.id.imageView018);
                imageView18.setImageBitmap(bitmap18);
                bitmapf18=bitmap18; }
            if(imagenesCondition==18)
            { Bitmap bitmap19= BitmapFactory.decodeFile(currentPhotoPath);
                ImageView imageView19 =findViewById(R.id.imageView019);
                imageView19.setImageBitmap(bitmap19);
                bitmapf19=bitmap19; }
            if(imagenesCondition==19)
            { Bitmap bitmap20= BitmapFactory.decodeFile(currentPhotoPath);
                ImageView imageView20 =findViewById(R.id.imageView020);
                imageView20.setImageBitmap(bitmap20);
                bitmapf20=bitmap20; }
            if(imagenesCondition==20)
            { Bitmap bitmap21= BitmapFactory.decodeFile(currentPhotoPath);
                ImageView imageView21 =findViewById(R.id.imageView021);
                imageView21.setImageBitmap(bitmap21);
                bitmapf21=bitmap21; }
            if(imagenesCondition==21)
            { Bitmap bitmap22= BitmapFactory.decodeFile(currentPhotoPath);
                ImageView imageView22 =findViewById(R.id.imageView022);
                imageView22.setImageBitmap(bitmap22);
                bitmapf22=bitmap22; }
            if(imagenesCondition==22)
            { Bitmap bitmap23= BitmapFactory.decodeFile(currentPhotoPath);
                ImageView imageView23 =findViewById(R.id.imageView023);
                imageView23.setImageBitmap(bitmap23);
                bitmapf23=bitmap23; }
            if(imagenesCondition==23)
            { Bitmap bitmap24= BitmapFactory.decodeFile(currentPhotoPath);
                ImageView imageView24 =findViewById(R.id.imageView024);
                imageView24.setImageBitmap(bitmap24);
                bitmapf24=bitmap24; }
            if(imagenesCondition==24)
            { Bitmap bitmap25= BitmapFactory.decodeFile(currentPhotoPath);
                ImageView imageView25 =findViewById(R.id.imageView025);
                imageView25.setImageBitmap(bitmap25);
                bitmapf25=bitmap25; }
            if(imagenesCondition==25)
            { Bitmap bitmap26= BitmapFactory.decodeFile(currentPhotoPath);
                ImageView imageView26 =findViewById(R.id.imageView026);
                imageView26.setImageBitmap(bitmap26);
                bitmapf26=bitmap26; }
            if(imagenesCondition==26)
            { Bitmap bitmap27= BitmapFactory.decodeFile(currentPhotoPath);
                ImageView imageView27 =findViewById(R.id.imageView027);
                imageView27.setImageBitmap(bitmap27);
                bitmapf27=bitmap27; }
            if(imagenesCondition==27)
            { Bitmap bitmap28= BitmapFactory.decodeFile(currentPhotoPath);
                ImageView imageView28 =findViewById(R.id.imageView028);
                imageView28.setImageBitmap(bitmap28);
                bitmapf28=bitmap28; }
            if(imagenesCondition==28)
        { Bitmap bitmap29= BitmapFactory.decodeFile(currentPhotoPath);
            ImageView imageView29 =findViewById(R.id.imageView029);
            imageView29.setImageBitmap(bitmap29);
            bitmapf29=bitmap29; }
            if(imagenesCondition==29)
            { Bitmap bitmap30= BitmapFactory.decodeFile(currentPhotoPath);
                ImageView imageView30 =findViewById(R.id.imageView030);
                imageView30.setImageBitmap(bitmap30);
                bitmapf30=bitmap30; }



            ///----------------------------------------------------

            imagenesCondition++;

        }


        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
//set image to image view
       //     myImageView.setImageURI(data.getData());

           // Bitmap bitmap = ((BitmapDrawable)myImageView.getDrawable()).getBitmap();



           // bitmapf=bitmap;

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
            { myImageView5.setImageURI(data.getData());
                Bitmap bitmap5 = ((BitmapDrawable)myImageView5.getDrawable()).getBitmap();
                bitmapf5=bitmap5; }
            /////-----------------------------
            if(imagenesCondition==5)
            { myImageView6.setImageURI(data.getData());
                Bitmap bitmap6 = ((BitmapDrawable)myImageView6.getDrawable()).getBitmap();
                bitmapf6=bitmap6; }
            if(imagenesCondition==6)
            {          myImageView7.setImageURI(data.getData());
                     Bitmap bitmap7 = ((BitmapDrawable)myImageView7.getDrawable()).getBitmap();
                   bitmapf7=bitmap7; }
            if(imagenesCondition==7)
            {          myImageView8.setImageURI(data.getData());
                Bitmap bitmap8 = ((BitmapDrawable)myImageView8.getDrawable()).getBitmap();
                bitmapf8=bitmap8; }
            if(imagenesCondition==8)
            {          myImageView9.setImageURI(data.getData());
                Bitmap bitmap9 = ((BitmapDrawable)myImageView9.getDrawable()).getBitmap();
                bitmapf9=bitmap9; }
            if(imagenesCondition==9)
            {          myImageView10.setImageURI(data.getData());
                Bitmap bitmap10 = ((BitmapDrawable)myImageView10.getDrawable()).getBitmap();
                bitmapf10=bitmap10; }
            if(imagenesCondition==10)
            {          myImageView11.setImageURI(data.getData());
                Bitmap bitmap11 = ((BitmapDrawable)myImageView11.getDrawable()).getBitmap();
                bitmapf11=bitmap11; }
            if(imagenesCondition==11)
            {          myImageView12.setImageURI(data.getData());
                Bitmap bitmap12 = ((BitmapDrawable)myImageView12.getDrawable()).getBitmap();
                bitmapf12=bitmap12; }
            if(imagenesCondition==12)
            {          myImageView13.setImageURI(data.getData());
                Bitmap bitmap13 = ((BitmapDrawable)myImageView13.getDrawable()).getBitmap();
                bitmapf13=bitmap13; }
            if(imagenesCondition==13)
            {          myImageView14.setImageURI(data.getData());
                Bitmap bitmap14 = ((BitmapDrawable)myImageView14.getDrawable()).getBitmap();
                bitmapf14=bitmap14; }
            if(imagenesCondition==14)
            {          myImageView15.setImageURI(data.getData());
                Bitmap bitmap15 = ((BitmapDrawable)myImageView15.getDrawable()).getBitmap();
                bitmapf15=bitmap15; }
            if(imagenesCondition==15)
            {          myImageView16.setImageURI(data.getData());
                Bitmap bitmap16 = ((BitmapDrawable)myImageView16.getDrawable()).getBitmap();
                bitmapf16=bitmap16; }
            if(imagenesCondition==16)
            {          myImageView17.setImageURI(data.getData());
                Bitmap bitmap17 = ((BitmapDrawable)myImageView17.getDrawable()).getBitmap();
                bitmapf17=bitmap17; }
            if(imagenesCondition==17)
            {          myImageView18.setImageURI(data.getData());
                Bitmap bitmap18 = ((BitmapDrawable)myImageView18.getDrawable()).getBitmap();
                bitmapf18=bitmap18; }
            if(imagenesCondition==18)
            {          myImageView19.setImageURI(data.getData());
                Bitmap bitmap19 = ((BitmapDrawable)myImageView19.getDrawable()).getBitmap();
                bitmapf19=bitmap19; }
            if(imagenesCondition==19)
            {          myImageView20.setImageURI(data.getData());
                Bitmap bitmap20 = ((BitmapDrawable)myImageView20.getDrawable()).getBitmap();
                bitmapf20=bitmap20; }
            if(imagenesCondition==20)
            {          myImageView21.setImageURI(data.getData());
                Bitmap bitmap21 = ((BitmapDrawable)myImageView21.getDrawable()).getBitmap();
                bitmapf21=bitmap21; }
            if(imagenesCondition==21)
            {          myImageView22.setImageURI(data.getData());
                Bitmap bitmap22 = ((BitmapDrawable)myImageView22.getDrawable()).getBitmap();
                bitmapf22=bitmap22; }
            if(imagenesCondition==22)
            {          myImageView23.setImageURI(data.getData());
                Bitmap bitmap23 = ((BitmapDrawable)myImageView23.getDrawable()).getBitmap();
                bitmapf23=bitmap23; }
            if(imagenesCondition==23)
            {          myImageView24.setImageURI(data.getData());
                Bitmap bitmap24 = ((BitmapDrawable)myImageView24.getDrawable()).getBitmap();
                bitmapf24=bitmap24; }
            if(imagenesCondition==24)
            {          myImageView25.setImageURI(data.getData());
                Bitmap bitmap25 = ((BitmapDrawable)myImageView25.getDrawable()).getBitmap();
                bitmapf25=bitmap25; }
            if(imagenesCondition==25)
            {          myImageView26.setImageURI(data.getData());
                Bitmap bitmap26 = ((BitmapDrawable)myImageView26.getDrawable()).getBitmap();
                bitmapf26=bitmap26; }
            if(imagenesCondition==26)
            {          myImageView27.setImageURI(data.getData());
                Bitmap bitmap27 = ((BitmapDrawable)myImageView27.getDrawable()).getBitmap();
                bitmapf27=bitmap27; }
            if(imagenesCondition==27)
            {          myImageView28.setImageURI(data.getData());
                Bitmap bitmap28 = ((BitmapDrawable)myImageView28.getDrawable()).getBitmap();
                bitmapf28=bitmap28; }
            if(imagenesCondition==28)
            {          myImageView29.setImageURI(data.getData());
                Bitmap bitmap29 = ((BitmapDrawable)myImageView29.getDrawable()).getBitmap();
                bitmapf29=bitmap29; }
            if(imagenesCondition==29)
            {          myImageView30.setImageURI(data.getData());
                Bitmap bitmap30 = ((BitmapDrawable)myImageView30.getDrawable()).getBitmap();
                bitmapf30=bitmap30; }


            /////------------------------------


            imagenesCondition++;

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    //permission was granted
                    pickImageFromGallery();
                } else {
//permision was denied

                }

            }
        }

        }
}
