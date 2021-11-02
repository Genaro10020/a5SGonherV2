package com.example.a5SGonher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class NuevaAuditoria extends AppCompatActivity {
    TextView tView,tCodigo;
    String ServerName;
    RequestQueue requestQueue;
    Button ButtonNext,ButtonBefore;
    String numeroAuditoria;
    String numeroActual,numeroAnterior;
    String NumeroAyuda,subarea;
    String cantidadPreguntas;
    int masterCount;

    String GlobalNumeroAuditoria,GlobalAyudaVisual;
    private ImageView imageview;
    LinearLayout layoutList;
    int radios[]= new int[100];
    TextView tv[]=new TextView[100];
    TextView prueba1,prueba2,titulo;
    RadioGroup radioGroup2[]=new RadioGroup[100];
    Button buttonArray[]=new Button[100];
    Button buttonValores[]=new Button[100];
    private ImageView imageviewHallazgo[]=new ImageView[100];
    RadioGroup radioSolo;
    int numeroPreguntas=0;
    String[] errorRespondido = new String[100];
    String Anterior;
    String[] numerosRadios = new String[100];



    int ciclo=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_auditoria);
        layoutList = findViewById(R.id.layoutSPlanta);
        tView=(TextView)findViewById(R.id.NombreAyuda);
        tCodigo=(TextView)findViewById(R.id.Codigo);
        imageview= findViewById(R.id.imageViewAyudaV);
        ButtonNext=(Button) findViewById(R.id.NextQuestion);
        ButtonBefore=(Button) findViewById(R.id.beforeQuestion);
        GlobalClass globalClass =(GlobalClass)getApplicationContext();
        ServerName=globalClass.getName();
          numeroAuditoria = getIntent().getStringExtra("EXTRA_SESSION_ID3");
        NumeroAyuda = getIntent().getStringExtra("EXTRA_SESSION_ID5");
        GlobalNumeroAuditoria=numeroAuditoria;
        int myNum = Integer.parseInt(NumeroAyuda);
        myNum++;
        numeroActual = Integer.toString(myNum);
        int myNum2=Integer.parseInt(NumeroAyuda);
        myNum2--;
        numeroAnterior=Integer.toString(myNum2);
        ButtonNext.setEnabled(false);

        TextView titulo = (TextView)findViewById(R.id.titulo_toolbar);
        titulo.setText("Auditando");

        if(NumeroAyuda.equals("1")) { ButtonBefore.setText("Salir");}
        else{ButtonBefore.setEnabled(true);}

        /*LinearLayout ll = new LinearLayout(this);
        TextView sacandonumeroauditoria = new TextView(this);
        sacandonumeroauditoria.setText(numeroAuditoria);
        ll.addView(sacandonumeroauditoria);
        setContentView(ll);*/
        //Toast.makeText(getApplicationContext(),"Número de Preguntas"+numeroPreguntas, Toast.LENGTH_LONG).show();
        buscarProducto(ServerName+"/5sGhoner/buscar_preguntas.php?NumeroAuditoria="+numeroAuditoria +"&NumeroAyuda="+ NumeroAyuda+"");
        String ciclov = Integer.toString(ciclo);

        //PreguntasContestadas(); //comente Genaro


        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowImage();
            }
        });

        ButtonBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(NumeroAyuda.equals("1")) {  TerminarAuditoria();}
                else{AnteriorPregunta();}


            }
        });




        ButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   Toast.makeText(getApplicationContext(),cantidadPreguntas,Toast.LENGTH_SHORT).show();
                Log.i("NEXT","numero de preguntas"+numeroPreguntas);
            for(int i=0;i<numeroPreguntas;i++)
            {

               // String numeroAct = Integer.toString(numeroPreguntas);
              //  Toast.makeText(getApplicationContext(),numeroAct,Toast.LENGTH_SHORT).show();

                    int radioId = radioGroup2[i].getCheckedRadioButtonId();
                    RadioButton radioButton = findViewById(radioId);
                    tv[i].setText(radioButton.getText());

                numerosRadios[i]=radioButton.getText().toString();

                String numerPregunta = Integer.toString(i+1);

                ejecutarservicio("https://vvnorth.com/5sGhoner/ContestarPreguntas.php",radioButton.getText().toString(),numerPregunta);

            }





             //   String radioN = radioButton.getText().toString();

//////////////////////////////////
                if(cantidadPreguntas.equals(NumeroAyuda))
                {
                    TerminarAuditoria();
                }
                else{

                    SiguientePregunta();
               }
      //////////////////////////////////

            }
        });

    }

    void PreguntasContestadas()
    {
        Log.d("myTag", "ESTE ES MI MENSAJE Y NUMERO DE PREGUNTAS SON"+numeroPreguntas);

        int numeroDeCincos=0;
        //Toast.makeText(getApplicationContext(),"Numero de preguntas"+numeroPreguntas,Toast.LENGTH_SHORT).show();
        for(int i=0;i<numeroPreguntas;i++)
        {
            int radioId = radioGroup2[i].getCheckedRadioButtonId();
            RadioButton radioButton = findViewById(radioId);
           // tv[i].setText(radioButton.getText());
        //    Toast.makeText(getApplicationContext(), radioButton.getText(), Toast.LENGTH_SHORT).show();

            numerosRadios[i]=radioButton.getText().toString();

            if(radioButton.getText().equals("5")) {
                buttonArray[i].setEnabled(false);  buttonArray[i].setVisibility(View.GONE);}
            else{
                buttonArray[i].setEnabled(true); buttonArray[i].setVisibility(View.VISIBLE); }

          //  Toast.makeText(getApplicationContext(), errorRespondido[0], Toast.LENGTH_SHORT).show();

            if(numerosRadios[i].equals("5")|| errorRespondido[i].equals("true")) {numeroDeCincos++;}
           // else{ButtonNext.setEnabled(false);}

           String sNumeroDeCincos= String.valueOf(numeroDeCincos);
            String sNumeroPreguntas= String.valueOf(numeroPreguntas);
           // Toast.makeText(getApplicationContext(), sNumeroPreguntas, Toast.LENGTH_SHORT).show();

            if(sNumeroDeCincos.equals(sNumeroPreguntas))
            {
                    ButtonNext.setEnabled(true);
            }
            else
            {
                ButtonNext.setEnabled(false);
            }

            }

    }

    private void requestImage(final String nombresNS) {

        RequestQueue requestQueue= Volley.newRequestQueue(this);

        ImageRequest imageRequest= new ImageRequest("https://vvnorth.com/5sGhoner/subareas/"+subarea+"/"+nombresNS+".jpg", new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {

                imageview.setImageBitmap(response);

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
    private void ejecutarservicio(String URL,final String Calificacion,final String NumeroPregunta)
    {
        Log.i("Datos a Guardar","Calificar"+Calificacion+"NumeroAuditoria"+GlobalNumeroAuditoria+"NumeroPregunta"+NumeroPregunta+"AyudaVisual"+GlobalAyudaVisual);
        StringRequest stringRequest=new  StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // buscarProducto("https://vvnorth.com/comparacion_auditorf.php",NPlanta);
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
                //  parametros.put("NombreArea",edtArea.getText().toString());
                parametros.put("Calificacion",Calificacion);
                parametros.put("NumeroAuditoria",GlobalNumeroAuditoria);
                parametros.put("NumeroPregunta",NumeroPregunta);
                parametros.put("AyudaVisual",GlobalAyudaVisual);
                //  parametros.put("Cambio",cambio);

                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



    private void addView1(final String name,final int i,String calificacion, final String nombreAudaVisual,final int j,final String descripcionDelError,final String AyudaVisual,final String SubArea2, final String Anterior, final  String CodigoAyudaVisual23) {
        View layoutView=getLayoutInflater().inflate(R.layout.preguntas,null,false);
        int cero=1;
       // TextView tv = (TextView).findViewById(R.id.Pregunta);
        tv[i]=(TextView)layoutView.findViewById((R.id.Pregunta));
        radioSolo =findViewById(R.id.radioGroup);
        radioGroup2[i] =(RadioGroup)layoutView.findViewById(R.id.radioGroup);
        imageviewHallazgo[i] =(ImageView) layoutView.findViewById(R.id.imageView_Hallazgo);
        buttonArray[i]=(Button)layoutView.findViewById((R.id.Button_Cuestionario));
        buttonValores[i]=(Button)layoutView.findViewById((R.id.Button_Valores));
        tv[i].setText(name);
        radios[i]=2;
        buttonArray[i].setText("Pruebas");

        //buttonArray[i].setEnabled(false); Comentado Genaro
        buttonArray[i].setVisibility(View.GONE);



        if(nombreAudaVisual.equals("Hallazgo")) {}else{
            imageviewHallazgo[i].setVisibility(View.GONE);}


        if(descripcionDelError.equals("")) {}
        else{ buttonArray[i].setBackgroundColor(Color.GREEN);
        }

        radioGroup2[j].setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {


                int radioId = radioGroup2[j].getCheckedRadioButtonId();
                String stringNumber = Integer.toString(radioId);

                PreguntasContestadas();

            }
        });


        //Actualiza como fue respondido anteriormente

        if(calificacion.equals("1")) {
           RadioButton RadioButton1 = (RadioButton) layoutView.findViewById(R.id.radioButton1);
            RadioButton1.setChecked(true);
            buttonArray[i].setEnabled(true);
          // buttonArray[i].setVisibility(View.VISIBLE);
        }

        if(calificacion.equals("2")) {
           RadioButton RadioButton2 = (RadioButton) layoutView.findViewById(R.id.radioButton2);
            RadioButton2.setChecked(true);
            buttonArray[i].setEnabled(true);
           // buttonArray[i].setVisibility(View.VISIBLE);
        }

        if(calificacion.equals("3")) {
            RadioButton RadioButton3 = (RadioButton) layoutView.findViewById(R.id.radioButton3);
            RadioButton3.setChecked(true);
            buttonArray[i].setEnabled(true);
         //   buttonArray[i].setVisibility(View.VISIBLE);
        }

        if(calificacion.equals("4")) {
            RadioButton RadioButton4 = (RadioButton) layoutView.findViewById(R.id.radioButton4);
            RadioButton4.setChecked(true);
            buttonArray[i].setEnabled(true);
           // buttonArray[i].setVisibility(View.VISIBLE);
        }

        if(calificacion.equals("5")) {
            RadioButton RadioButton5 = (RadioButton) layoutView.findViewById(R.id.radioButton5);
            RadioButton5.setChecked(true);


        }


        buttonArray[i].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i=0;i<numeroPreguntas;i++)
                {

                    // String numeroAct = Integer.toString(numeroPreguntas);
                    //  Toast.makeText(getApplicationContext(),numeroAct,Toast.LENGTH_SHORT).show();

                    int radioId = radioGroup2[i].getCheckedRadioButtonId();
                    RadioButton radioButton = findViewById(radioId);
                    tv[i].setText(radioButton.getText());

                    numerosRadios[i]=radioButton.getText().toString();

                    String numerPregunta = Integer.toString(i+1);
                    ejecutarservicio("https://vvnorth.com/5sGhoner/ContestarPreguntas.php",radioButton.getText().toString(),numerPregunta);

                }

                ContestarPregunta(name,i,nombreAudaVisual);
            }
        });



        buttonValores[i].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog9 dialog9 = new Dialog9();
                dialog9.show(getSupportFragmentManager(), "example dialog");

            }
        });

        imageviewHallazgo[i].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ShowImage2(AyudaVisual,SubArea2,Anterior,name);

            }
        });



       layoutList.addView(layoutView);

    }

    public void ContestarPregunta(String nombrePregunta,int numeroPregunta,String nombreAyudaVisual)
    {

        int actualNumber=numeroPregunta;
        actualNumber--;
        String SActualPregunta = Integer.toString(actualNumber);
        String sNumeroPregunta = Integer.toString(numeroPregunta);
        Intent intent =new Intent(this,NuevaAuditoria_Pregunta.class);
        intent.putExtra("EXTRA_SESSION_ID", nombrePregunta);
        intent.putExtra("EXTRA_SESSION_ID2", numeroAuditoria);
        intent.putExtra("EXTRA_SESSION_ID3", nombreAyudaVisual);
        intent.putExtra("EXTRA_SESSION_ID4", sNumeroPregunta);
        intent.putExtra("EXTRA_SESSION_ID5", NumeroAyuda);
        Toast.makeText(getApplicationContext(),sNumeroPregunta,Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }


    public void SiguientePregunta()
    {
        Intent intent =new Intent(this,NuevaAuditoria.class);
        intent.putExtra("EXTRA_SESSION_ID3", numeroAuditoria);
        intent.putExtra("EXTRA_SESSION_ID5", numeroActual);
        startActivity(intent);
    }

    public void AnteriorPregunta()
    {
        Intent intent =new Intent(this,NuevaAuditoria.class);
        intent.putExtra("EXTRA_SESSION_ID3", numeroAuditoria);
        intent.putExtra("EXTRA_SESSION_ID5", numeroAnterior);
        startActivity(intent);
    }

    public void ShowImage()
    {
        Intent intent =new Intent(this,ShowOnlyImage.class);
        intent.putExtra("EXTRA_SESSION_ID", GlobalAyudaVisual);
        intent.putExtra("EXTRA_SESSION_ID2", subarea);
        startActivity(intent);
    }


    public void ShowImage2(String AyudaVisual,String Subarea,String Anterior,String NombrePregunta)
    {
        Intent intent =new Intent(this,ShowOnlyImageHallazgos.class);
          intent.putExtra("EXTRA_SESSION_ID", AyudaVisual);
         intent.putExtra("EXTRA_SESSION_ID2", Subarea);
        intent.putExtra("EXTRA_SESSION_ID3", Anterior);
        intent.putExtra("EXTRA_SESSION_ID4", NombrePregunta);
        ;

      //  intent.putExtra("EXTRA_SESSION_ID", GlobalAyudaVisual);
       // intent.putExtra("EXTRA_SESSION_ID2", subarea);
        //intent.putExtra("EXTRA_SESSION_ID3", Anterior);
      //  intent.putExtra("EXTRA_SESSION_ID4", NombrePreguntra);
       // intent.putExtra("EXTRA_SESSION_ID5", name);
        startActivity(intent);
    }

    public void TerminarAuditoria()
    {
        Intent intent =new Intent(this,MainMenu.class);
        startActivity(intent);
    }




    private void buscarProducto(String URL)
    {

        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
               // Log.d("CONSULTADO BD","Respuesta de auditoria"+response.length());

              for (int i = 0; i < response.length(); i++) {
                    try {
                       String nombre,nombrePregunta,CodigoAyudaVisual,descripcionDelError;
                        jsonObject = response.getJSONObject(i);
                        // editT.setText(jsonObject.getString("Planta"));
                        nombre=jsonObject.getString("AyudaVisual");
                        nombrePregunta=jsonObject.getString("NombrePregunta");
                        CodigoAyudaVisual=jsonObject.getString("CodigoAyudaVisual");
                        descripcionDelError=jsonObject.getString("DescripcionError");
                        cantidadPreguntas=jsonObject.getString("CantidadPreguntas");
                       subarea=jsonObject.getString("SubArea");
                       String Calificacion=jsonObject.getString("Calificacion");
                       Anterior=jsonObject.getString("Anterior");
                        requestImage(nombre);
                       GlobalAyudaVisual=nombre;
                        String ayudavisual=jsonObject.getString("AyudaAnterior");
                        String SubArea=jsonObject.getString("SubArea");
                       String  AyudaVisu=jsonObject.getString("AyudaVisual");

                        if(descripcionDelError.equals(""))
                        {
                        errorRespondido[i]="false";}
                        else {
                        errorRespondido[i]="true";}

                        tView.setText(nombre);
                       tCodigo.setText(CodigoAyudaVisual);
                        addView1(nombrePregunta,i,Calificacion,nombre,i,descripcionDelError,ayudavisual,SubArea,Anterior,AyudaVisu);
                        numeroPreguntas++;
                      //  boton(nombre,i);
                      //  Toast.makeText(getApplicationContext(),AyudaVisu,Toast.LENGTH_SHORT).show();
                      //PreguntasContestadas(); //Comentado Genaro

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"ERROR DE CONEXION",Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }


    public void boton(final String nombreBoton, int numeroEmpresa)
    {
        Button myButton3 = new Button(this);

        myButton3.setText(nombreBoton);

        LinearLayout ll3 = (LinearLayout)findViewById(R.id.layoutSPlanta);
        LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll3.addView(myButton3, lp3);

        /////////////////////////
        myButton3.setBackgroundColor(Color.rgb(150, 0, 0));
        myButton3.setTextColor(Color.rgb(179, 179, 179));
        lp3.setMargins(0, 0, 0, 10);
        myButton3.setLayoutParams( lp3);
        /////////////////////////////////////////
        myButton3.setOnClickListener(new View.OnClickListener()  {
            // String NomPlanta =nFinal;
            public void onClick(View view) {

            //   agregarArea(nombreBoton);

            }
        });
    }
}