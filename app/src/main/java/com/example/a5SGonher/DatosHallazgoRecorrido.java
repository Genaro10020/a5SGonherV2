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
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DatosHallazgoRecorrido extends AppCompatActivity {
    ImageView escuchar_voz, fotografia,iconoInterrogacion;
    EditText descripcion;
    String ServerName,Planta;
    TextView textoCamara,mensaje_lienzo,textoiconoInterrogacion,espereguardando,textResponsable;
    DrawView drawView;
    Button BotonTerminar,btn_lienzo,limpiar;
    ImageView icono_liezo;
    Bitmap bitmapf;
    ArrayList<String> ResponsablesList;
    Spinner spinnerResponsables;
    int fotografiaTomada=0;
    RequestQueue requestQueue;
    private String currentPhotoPath;
    private static final int REQUEST_CODE_SPEECH_INPUT = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_hallazgo_recorrido);
        TextView titulo_toolbar = (TextView)findViewById(R.id.titulo_toolbar);
        SharedPreferences preferences=getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        Planta = preferences.getString("Planta","No existe Planta");
        GlobalClass globalClass = (GlobalClass)getApplicationContext();
        ServerName=globalClass.getName();
        titulo_toolbar.setText("Datos de Hallázgo");
        textResponsable = findViewById(R.id.textResponsable);
        spinnerResponsables= findViewById(R.id.spinnerResponsable);
        escuchar_voz =findViewById(R.id.img_record);
        descripcion=findViewById(R.id.descripcion);
        limpiar = (Button)findViewById(R.id.btn_limpiar);
        fotografia = findViewById(R.id.imageView1P);
        textoCamara = findViewById(R.id.textoCamara);
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
        ResponsablesList = new ArrayList<>();

        limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                descripcion.setText("");
            }
        });
        fotografia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TakePhoto();
            }
        } );

        btn_lienzo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawView.setVisibility(View.GONE);
                btn_lienzo.setVisibility(View.GONE);
                icono_liezo.setVisibility(View.GONE);
                mensaje_lienzo.setVisibility(View.GONE);
                descripcion.setVisibility(View.VISIBLE);
                escuchar_voz.setVisibility(View.VISIBLE);
                limpiar.setVisibility(View.VISIBLE);
                textoCamara.setVisibility(View.VISIBLE);
                fotografia.setVisibility(View.VISIBLE);
                BotonTerminar.setVisibility(View.VISIBLE);
                textoiconoInterrogacion.setVisibility(View.VISIBLE);
                iconoInterrogacion.setVisibility(View.VISIBLE);
                spinnerResponsables.setVisibility(View.VISIBLE);
                textResponsable.setVisibility(View.VISIBLE);
            }
        });
        consultandoResponsables(ServerName+"5sGhoner/consultarAuditores.php");
        BotonTerminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fotografiaTomada == 1){
                    espereguardando.setVisibility(View.VISIBLE);
                    BotonTerminar.setVisibility(View.GONE);
                    //guardarHallazgo("https://vvnorth.com/5sGhoner/ContestarErrores.php");
                }else{
                    View toastfoto = getLayoutInflater().inflate(R.layout.toast_verificando_foto_evidencia,(ViewGroup)findViewById(R.id.layout_toast_fotografia));
                    Toast toast2 = new Toast(getApplicationContext());
                    toast2.setDuration(Toast.LENGTH_SHORT);
                    toast2.setView(toastfoto);
                    toast2.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
                    toast2.show();
                }
            }
        });

    }


    public void consultandoResponsables(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("RESPUESTA",response);
                        JSONObject jsonObject = null;
                        String auditor="";
                        try {
                            JSONArray respuestArreglo = new JSONArray(response);
                            for (int i = 0; i < respuestArreglo.length(); i++) {
                                jsonObject = respuestArreglo.getJSONObject(i);
                                auditor = jsonObject.getString("NombreAuditor");
                                ResponsablesList.add(auditor);

                                Log.e("","\n"+auditor);

                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(DatosHallazgoRecorrido.this, android.R.layout.simple_spinner_item, ResponsablesList);
                            spinnerResponsables.setAdapter(adapter);


                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Problemas al conectar intente nuevamente.", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("planta", Planta);
                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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

    public void onYesClicked() {
        String fileName="photo";
        File StorageDirectory= getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        try {
            File imageFile=File.createTempFile(fileName,".jpg",StorageDirectory);
            currentPhotoPath=imageFile.getAbsolutePath();
            Uri imageUri=  FileProvider.getUriForFile(DatosHallazgoRecorrido.this,
                    "com.example.a5SGonher.fileprovider",imageFile);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
            startActivityForResult(intent,1);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void TakePhoto()
    {
        String fileName="photo";
        File StorageDirectory= getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        try {
            File imageFile=File.createTempFile(fileName,".jpg",StorageDirectory);
            currentPhotoPath=imageFile.getAbsolutePath();
            Uri imageUri=  FileProvider.getUriForFile(DatosHallazgoRecorrido.this,
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
            fotografiaTomada = 1;

            Bitmap bitmap= BitmapFactory.decodeFile(currentPhotoPath);
            // Create a matrix for the rotation
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);


            int newWidth = 1000;
            int newHeight = 1000;;
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(rotatedBitmap, newWidth, newHeight, false);
            textoCamara.setText("Tomada");
            fotografia.setImageBitmap(resizedBitmap);
            ///////

            Bitmap bitmapt = ((BitmapDrawable)fotografia.getDrawable()).getBitmap();
            ViewGroup.LayoutParams param = drawView.getLayoutParams();
            param.width = 1000;
            param.height = 1000;

            drawView.setVisibility(View.VISIBLE);
            drawView.setBitmap(bitmapt);
            bitmapf = drawView.getBitmap();
            ViewGroup.LayoutParams params = fotografia.getLayoutParams();
            //fotografia.setPadding(50,50,50,50);
            params.width = 1000;
            params.height = 1000;
            //fotografia.setLayoutParams(params);
            fotografia.setImageBitmap(bitmapf);



            icono_liezo.setVisibility(View.VISIBLE);
            BotonTerminar.setVisibility(View.GONE);
            mensaje_lienzo.setVisibility(View.VISIBLE);
            btn_lienzo.setVisibility(View.VISIBLE);
            descripcion.setVisibility(View.GONE);
            escuchar_voz.setVisibility(View.GONE);
            limpiar.setVisibility(View.GONE);
            textoCamara.setVisibility(View.GONE);
            fotografia.setVisibility(View.GONE);
            textoiconoInterrogacion.setVisibility(View.GONE);
            iconoInterrogacion.setVisibility(View.GONE);
            spinnerResponsables.setVisibility(View.GONE);
            textResponsable.setVisibility(View.GONE);


        }
    }


}