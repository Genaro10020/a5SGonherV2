package com.example.a5SGonher;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.hardware.Camera;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class NuevaAreaEstandar extends AppCompatActivity {
    String numeroAuditoria, numeroActual,numeroAnterior,AyudaVisual,CodigoAyudaVisual,respuesta,Planta;
    ImageView tomar_foto_area;
    Bitmap bitmapf;
    Bitmap modifiedBitmap;
    EditText edit_comentario;
    TextView leyenda;
    Button btn_contestar;
    DrawView drawView;

    int fotografiaTomada =0;
    private static final int REQUEST_IMAGE_CAPTURE = 1;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_area_estandar);
        TextView titulo = findViewById(R.id.titulo_toolbar);
        drawView = findViewById(R.id.draw_view);
        edit_comentario = findViewById(R.id.comentario_nueva_area);
        btn_contestar = findViewById(R.id.btn_contestar);
        leyenda = findViewById(R.id.leyenda);
        numeroAuditoria = getIntent().getStringExtra("NUMERO_AUDITORIA");
        numeroActual = getIntent().getStringExtra("NUMERO_ACTUAL");
        AyudaVisual = getIntent().getStringExtra("AYUDA_VISUAL");
        CodigoAyudaVisual = getIntent().getStringExtra("CODIGO_AYUDA_VISUAL");
        respuesta = getIntent().getStringExtra("COMENTARIO");

        SharedPreferences preferences=getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        Planta = preferences.getString("Planta","No existe la planta");


        int myNum2=Integer.parseInt(numeroActual);
        myNum2--;
        numeroAnterior=Integer.toString(myNum2);

        titulo.setText("Nueva Área");

        if(!respuesta.isEmpty()){
                edit_comentario.setText(respuesta);
        }
        tomar_foto_area = findViewById(R.id.icono_foto_area);
        tomar_foto_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tomarFoto();
            }
        });
        final Context context = getApplicationContext();
        recuperandoImagen();

        btn_contestar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 respuesta = edit_comentario.getText().toString();
                if (edit_comentario.getText().toString().length() == 0 || fotografiaTomada==0){

                    View mitoast = getLayoutInflater().inflate(R.layout.toast_comentario_foto_evidencia,(ViewGroup)findViewById(R.id.layout_toast_comentario));
                    TextView text = mitoast.findViewById(R.id.mensaje1);
                    text.setText("Verifique su respuesta y fotografía.");
                    Toast toast = new Toast(context);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(mitoast);
                    toast.setGravity(Gravity.CENTER,0,-400);
                    toast.show();
                }else{

                    View mitoast = getLayoutInflater().inflate(R.layout.toast_procesando,(ViewGroup)findViewById(R.id.layout_toast_procesando));
                    Toast toast = new Toast(context);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(mitoast);
                    toast.setGravity(Gravity.CENTER,0,-400);
                    toast.show();
                    guardarinformacion("https://vvnorth.com/5sGhoner/ContestarAreaEstandar.php");
                }


            }
        });
    }

    private void recuperandoImagen(){

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "https://vvnorth.com/5sGhoner/FotosAuditorias/"+numeroAuditoria+"/"+AyudaVisual+"/"+CodigoAyudaVisual+"/nueva_area_estandar/1.jpeg";
        ImageRequest request = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                leyenda.setText("");
                ViewGroup.LayoutParams params = tomar_foto_area.getLayoutParams();
                params.width = 1000;
                params.height = 1000;
                tomar_foto_area.setLayoutParams(params);
                fotografiaTomada = 2;
                tomar_foto_area.setImageBitmap(response);

                Bitmap bitmap = ((BitmapDrawable)tomar_foto_area.getDrawable()).getBitmap();

                bitmapf=bitmap;

               /* DrawView drawView = findViewById(R.id.draw_view);
                drawView.setVisibility(View.VISIBLE);
                drawView.setDrawingCacheEnabled(true);
                drawView.setBitmap(bitmap);*/

            }
        }, 0, 0, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                return;
            }
        });

        requestQueue.add(request);

    }

    public void tomarFoto(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            int newWidth = 1000;
            int newHeight = 1000;;
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, newWidth, newHeight, false);

            tomar_foto_area.setImageBitmap(resizedBitmap);
            fotografiaTomada = 1;
            leyenda.setText("");
            tomar_foto_area.setImageURI(data.getData());

            Bitmap bitmap = ((BitmapDrawable)tomar_foto_area.getDrawable()).getBitmap();
            ViewGroup.LayoutParams param = drawView.getLayoutParams();
            param.width = 1000;
            param.height = 1000;
            drawView.setLayoutParams(param);
            drawView.setVisibility(View.VISIBLE);
            drawView.setBitmap(bitmap);
            bitmapf=drawView.getBitmap();


        }
    }

    public void guardarinformacion(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                VolverANuevaAuditoria();
                Toast toast = Toast.makeText(getApplicationContext(),"Guardado con Éxito",Toast.LENGTH_SHORT);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // Toast.makeText(getApplicationContext(), error.toString()+"Error :-(", Toast.LENGTH_SHORT).show();
                VolverANuevaAuditoria();
            }
        })
        {

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String, String>();
                String imageData= imageToString(bitmapf);
                parametros.put("imagen",imageData);
                parametros.put("NumeroAuditoria",numeroAuditoria);
                parametros.put("AyudaVisual",AyudaVisual);
                parametros.put("CodigoAyudaVisual",CodigoAyudaVisual);
                parametros.put("Comentario",respuesta);
                parametros.put("Planta",Planta);

                return parametros;
            }

        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private String imageToString(Bitmap bitmapf) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmapf.compress(Bitmap.CompressFormat.JPEG,100, outputStream);
        byte[] imageBytes= outputStream.toByteArray();
        String encodeImage= Base64.encodeToString(imageBytes,Base64.DEFAULT);
        return encodeImage;
    }

    public void VolverANuevaAuditoria()
    {
        Intent intent =new Intent(this,NuevaAuditoria.class);
        intent.putExtra("EXTRA_SESSION_ID3", numeroAuditoria);
        intent.putExtra("EXTRA_SESSION_ID5", numeroAnterior);
        startActivity(intent);
    }




}
