package com.example.a5SGonher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
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
    //String NumeroImagenes[]= new String[100];
    int masterCount;

    String GlobalNumeroAuditoria,GlobalAyudaVisual;
    private ImageView imageview;
    LinearLayout layoutList;
    int radios[]= new int[1000];
    TextView tv[]=new TextView[1000];
    TextView respuestas[]=new TextView[1000];
    TextView nouno[]=new TextView[10000];
    TextView nodos[]=new TextView[10000];
    TextView notres[]=new TextView[10000];
    TextView nocuatro[]=new TextView[10000];
    TextView sicinco[]=new TextView[10000];
    TextView prueba1,prueba2,titulo;
    RadioGroup radioGroup2[]=new RadioGroup[1000];
    Button botonEvidencia[]=new Button[1000];
    Button buttonValores[]=new Button[1000];
    RadioButton radioButton1[]= new RadioButton[1000];
    RadioButton radioButton2[]= new RadioButton[1000];
    RadioButton radioButton3[]= new RadioButton[1000];
    RadioButton radioButton4[]= new RadioButton[1000];
    RadioButton radioButton5[]= new RadioButton[1000];
    private ImageView imageviewHallazgo[]=new ImageView[1000];
    RadioGroup radioSolo;
    int numeroPreguntas=0;
    int cantidadHallazgos=0;
    int cantidaddeno=0;
    String[] errorRespondido = new String[1000];
    String Anterior;
    String[] numerosRadios = new String[1000];
    int cantidadcincos =0;
    int pruebasvacias=0;

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

        //ButtonNext.setEnabled(false); Comentado por Genaro
        final Context context = getApplicationContext();

        TextView titulo = (TextView)findViewById(R.id.titulo_toolbar);
        titulo.setText("Auditando");

        if(NumeroAyuda.equals("1")) { ButtonBefore.setText("Salir");}
        else{ButtonBefore.setEnabled(true);}

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
            public void onClick(View view) {//ATRAS
                if(NumeroAyuda.equals("1")){
                    TerminarAuditoria();
                }
                else{
                    AnteriorPregunta();
                }
            }
        });



         ButtonNext.setOnClickListener(new View.OnClickListener() {//NEXT
            @Override
            public void onClick(View view){

                //System.out.println("cantidadcincos: "+cantidadcincos+"NumeroAnterior: "+numeroAnterior+"NumeroAyuda:"+NumeroAyuda+"NumeroPreguntas"+numeroPreguntas+"cantidaPreguntas:"+cantidadPreguntas);
                int cantidadRealPreguntas=numeroPreguntas-cantidadHallazgos;
                Log.i("a ver","cantidadrealpreguntas"+cantidadRealPreguntas+"cantidad de cincos"+cantidadcincos);
                if(cantidadcincos>=cantidadRealPreguntas)
                {

                            for(int i=0;i<cantidadHallazgos;i++)
                            {
                                int radioId = radioGroup2[i].getCheckedRadioButtonId();
                                RadioButton radioButton = findViewById(radioId);
                                String stringI= String.valueOf(i);
                                String numerPregunta="0"+stringI;
                                Log.e("Insertando","radio: "+radioButton.getText().toString()+" NumeroPregunta:"+numerPregunta);
                                 ejecutarservicio("https://vvnorth.com/5sGhoner/ContestarPreguntas.php",radioButton.getText().toString(),numerPregunta);
                            }

                        for(int i=0+cantidadHallazgos;i<numeroPreguntas;i++)
                                {
                                        int radioId = radioGroup2[i].getCheckedRadioButtonId();
                                        RadioButton radioButton = findViewById(radioId);
                                        String numerPregunta = Integer.toString(i+1-cantidadHallazgos);
                                        Log.e("Insertando","radio: "+radioButton.getText().toString()+" NumeroPregunta:"+numerPregunta);
                                        ejecutarservicio("https://vvnorth.com/5sGhoner/ContestarPreguntas.php",radioButton.getText().toString(),numerPregunta);
                                }

                            if(cantidadPreguntas.equals(NumeroAyuda))
                            {
                                TerminarAuditoria();
                            }
                            else{
                                SiguientePregunta();
                           }
                }else{
                    View viewToast = getLayoutInflater().inflate(R.layout.toast_siguiente_pruebas,(ViewGroup)findViewById(R.id.layout_toast_prueba));
                    Toast toast = new Toast(context);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(viewToast);
                    toast.setGravity(Gravity.CENTER_HORIZONTAL, 300, 500);
                    toast.show();
                        ButtonNext.setEnabled(false);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ButtonNext.setEnabled(true);
                            }
                        },2000);
                }

            }
        });


    }




   void PreguntasContestadas(String calificacion)
    {


        int numeroDeCincos=0;
        System.out.println("Numero de preguntas"+numeroPreguntas);
        System.out.println("cantidad de hallazgos"+cantidadHallazgos);
         for(int i=0+cantidadHallazgos;i<numeroPreguntas;i++)
            {

                int radioId = radioGroup2[i].getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(radioId);
               // Log.i(" radioGroup2","contiene"+radioGroup2[i]);
               // Log.i("radiobuton","contiene"+radioButton.getText());


                numerosRadios[i]=radioButton.getText().toString();
                // tv[i].setText(radioButton.getText());
                //    Toast.makeText(getApplicationContext(), radioButton.getText(), Toast.LENGTH_SHORT).show();



                if(radioButton.getText().equals("5") || radioButton.getText().equals("SI")) {// Ocultar botones pruebas
                    botonEvidencia[i].setEnabled(false);  botonEvidencia[i].setVisibility(View.GONE);}
                else{
                    //System.out.println("activando botones"+i);// Muestra botones pruebas
                    botonEvidencia[i].setEnabled(true); botonEvidencia[i].setVisibility(View.VISIBLE); }

                System.out.println("RespondidoHallazgo"+errorRespondido[i]);

                if(numerosRadios[i].equals("5") || errorRespondido[i].equals("true") || numerosRadios[i].equals("SI") || numerosRadios[i].equals("NO")) {numeroDeCincos++;}//contestados satisfactorio
                // else{ButtonNext.setEnabled(false);}

                cantidadcincos=numeroDeCincos;
                System.out.println("numerodecincos"+numeroDeCincos);
                String Numero= String.valueOf(numeroDeCincos);
                String sNumeroPreguntas= String.valueOf(numeroPreguntas);
                // Toast.makeText(getApplicationContext(), sNumeroPreguntas, Toast.LENGTH_SHORT).show();


                            if(NumeroAyuda.equals(cantidadPreguntas)){
                                ButtonNext.setText("Finalizar");
                            }

            }


    }

    public void Siguiente(){

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
        //Log.i("Datos a Guardar","Calificar"+Calificacion+"NumeroAuditoria"+GlobalNumeroAuditoria+"NumeroPregunta"+NumeroPregunta+"AyudaVisual"+GlobalAyudaVisual);
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



    private void addView1(final String name, final int i, final String calificacion, final String nombreAudaVisual, String hallazgo,final String respuestaanterior, final int j, final String descripcionDelError, final String AyudaVisual, final String SubArea2, final String Anterior, final String  numeroImageness, final  String CodigoAyudaVisual23) {
        View layoutView=getLayoutInflater().inflate(R.layout.preguntas,null,false);
        int cero=1;

       // TextView tv = (TextView).findViewById(R.id.Pregunta);
        tv[i]=(TextView)layoutView.findViewById((R.id.Pregunta));
        respuestas[i]=(TextView)layoutView.findViewById((R.id.respuestahallazgo));
        nouno[i]=(TextView)layoutView.findViewById((R.id.nouno));
        nodos[i]=(TextView)layoutView.findViewById((R.id.nodos));
        notres[i]=(TextView)layoutView.findViewById((R.id.notres));
        nocuatro[i]=(TextView)layoutView.findViewById((R.id.nocuatro));
        sicinco[i]=(TextView)layoutView.findViewById((R.id.sicinco));

        //NumeroImagenes[i]=numeroImagenes;
        radioSolo =findViewById(R.id.radioGroup);
        radioGroup2[i] =(RadioGroup)layoutView.findViewById(R.id.radioGroup);
        imageviewHallazgo[i] =(ImageView) layoutView.findViewById(R.id.imageView_Hallazgo);
        botonEvidencia[i]=(Button)layoutView.findViewById((R.id.Button_Cuestionario));
        buttonValores[i]=(Button)layoutView.findViewById((R.id.Button_Valores));
        //respuestas[i]=findViewById(R.id.respuestahallazgo);
        tv[i].setText(name);//Asignando Pregunta
        radios[i]=2;
        botonEvidencia[i].setText("Hallazgo");

        //Log.i("respuestas arreglo",":  "+respuestas[i]);
        //botonEvidencia[i].setEnabled(false); Comentado Genaro
        //System.out.println("Respuesta Anterior"+respuestaanterior);
        //System.out.println("Hallazgo"+hallazgo);
        //botonEvidencia[i].setVisibility(View.GONE);

        if(hallazgo.equals("si")) {
            respuestas[i].setText(respuestaanterior);//Asignando Respuesta Anterior
           nouno[i].setVisibility(View.VISIBLE);
           sicinco[i].setVisibility(View.VISIBLE);


        }else{
            imageviewHallazgo[i].setVisibility(View.GONE);
            respuestas[i].setVisibility(View.GONE);

        }

       if (calificacion.equals("1") || calificacion.equals("2") || calificacion.equals("3") || calificacion.equals("4"))
       {
           if(descripcionDelError.equals("")) { pruebasvacias++;}else{
               //imageviewHallazgo[i].setVisibility(View.VISIBLE);//lo oculto pero para
               botonEvidencia[i].setBackgroundResource(R.drawable.boton_verde);
               cantidadcincos++;}//si se sube hallazgo incrementa cantidadcincos.
       }else{botonEvidencia[i].setVisibility(View.GONE); cantidadcincos++;
           /*if(hallazgo.equals("si")){
               imageviewHallazgo[i].setVisibility(View.GONE);
           }*/
       }// si es 5 incrementa cantidadcincos






        //Actualiza como fue respondido anteriormente
        RadioButton RadioButton1 = (RadioButton) layoutView.findViewById(R.id.radioButton1);
        RadioButton RadioButton2 = (RadioButton) layoutView.findViewById(R.id.radioButton2);
        RadioButton RadioButton3 = (RadioButton) layoutView.findViewById(R.id.radioButton3);
        RadioButton RadioButton4 = (RadioButton) layoutView.findViewById(R.id.radioButton4);
        RadioButton RadioButton5 = (RadioButton) layoutView.findViewById(R.id.radioButton5);
        Log.i("hallazgo","hay"+hallazgo);
        if(calificacion.equals("1")) {
            RadioButton1.setChecked(true);
            botonEvidencia[i].setEnabled(true);

            if(hallazgo.equals("si")){
                RadioButton1.setTextColor(Color.rgb(255,255,255));
                RadioButton5.setTextColor(Color.rgb(255,255,255));
                RadioButton1.setWidth(73);
                RadioButton5.setWidth(73);
                botonEvidencia[i].setVisibility(View.GONE);
                buttonValores[i].setVisibility(View.GONE);
                RadioButton2.setVisibility(View.GONE);
                RadioButton3.setVisibility(View.GONE);
                RadioButton4.setVisibility(View.GONE);
            }
            // botonEvidencia[i].setVisibility(View.VISIBLE);
        }

        if(calificacion.equals("2")) {
            RadioButton2.setChecked(true);
            botonEvidencia[i].setEnabled(true);
            if(hallazgo.equals("si")){
                RadioButton2.setTextColor(Color.rgb(255,255,255));
                RadioButton5.setTextColor(Color.rgb(255,255,255));
                RadioButton2.setWidth(73);
                RadioButton5.setWidth(73);
                botonEvidencia[i].setVisibility(View.GONE);
                buttonValores[i].setVisibility(View.GONE);
                RadioButton1.setVisibility(View.GONE);
                RadioButton3.setVisibility(View.GONE);
                RadioButton4.setVisibility(View.GONE);
            }
            // botonEvidencia[i].setVisibility(View.VISIBLE);
        }

        if(calificacion.equals("3")) {
            RadioButton3.setChecked(true);
            botonEvidencia[i].setEnabled(true);
            if(hallazgo.equals("si")){
                RadioButton3.setTextColor(Color.rgb(255,255,255));
                RadioButton5.setTextColor(Color.rgb(255,255,255));
                RadioButton3.setWidth(73);
                RadioButton5.setWidth(73);
                botonEvidencia[i].setVisibility(View.GONE);
                buttonValores[i].setVisibility(View.GONE);
                RadioButton1.setVisibility(View.GONE);
                RadioButton2.setVisibility(View.GONE);
                RadioButton4.setVisibility(View.GONE);
            }
            //   botonEvidencia[i].setVisibility(View.VISIBLE);
        }

        if(calificacion.equals("4")) {
            RadioButton4.setChecked(true);
            botonEvidencia[i].setEnabled(true);
            if(hallazgo.equals("si")){
                RadioButton4.setTextColor(Color.rgb(255,255,255));
                RadioButton5.setTextColor(Color.rgb(255,255,255));
                RadioButton4.setWidth(73);
                RadioButton5.setWidth(73);
                botonEvidencia[i].setVisibility(View.GONE);
                buttonValores[i].setVisibility(View.GONE);
                RadioButton1.setVisibility(View.GONE);
                RadioButton2.setVisibility(View.GONE);
                RadioButton3.setVisibility(View.GONE);
            }
            // botonEvidencia[i].setVisibility(View.VISIBLE);
        }

        if(calificacion.equals("5")) {

            RadioButton5.setChecked(true);
            if(hallazgo.equals("si")){
                RadioButton1.setTextColor(Color.rgb(255,255,255));
                RadioButton5.setTextColor(Color.rgb(255,255,255));
                RadioButton1.setWidth(73);
                RadioButton5.setWidth(73);
                botonEvidencia[i].setVisibility(View.GONE);
                buttonValores[i].setVisibility(View.GONE);
                RadioButton2.setVisibility(View.GONE);
                RadioButton3.setVisibility(View.GONE);
                RadioButton4.setVisibility(View.GONE);
            }

        }

        //Log.e("next","NumeroAyuda"+NumeroAyuda+"cantidadPreguntas"+cantidadPreguntas);
        if(NumeroAyuda.equals(cantidadPreguntas)){
            ButtonNext.setText("Finalizar");
        }

        radioGroup2[j].setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                PreguntasContestadas(calificacion);
            }

        });


        botonEvidencia[i].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*for(int i=0;i<numeroPreguntas;i++)
                {
                    // String numeroAct = Integer.toString(numeroPreguntas);
                    //  Toast.makeText(getApplicationContext(),numeroAct,Toast.LENGTH_SHORT).show();
                    int radioId = radioGroup2[i].getCheckedRadioButtonId();
                    RadioButton radioButton = findViewById(radioId);
                   // tv[i].setText(radioButton.getText());Comentada por Genaro
                    numerosRadios[i]=radioButton.getText().toString();
                    String numerPregunta = Integer.toString(i+1);
                    //ejecutarservicio("https://vvnorth.com/5sGhoner/ContestarPreguntas.php",radioButton.getText().toString(),numerPregunta);
                }*/

                //int cantidadRealPreguntas=numeroPreguntas-cantidadHallazgos;
                //Log.i("a ver","cantidadrealpreguntas"+cantidadRealPreguntas+"cantidad de cincos"+cantidadcincos);


                    for (int i = 0; i < cantidadHallazgos; i++) {
                        int radioId = radioGroup2[i].getCheckedRadioButtonId();
                        RadioButton radioButton = findViewById(radioId);
                        String stringI = String.valueOf(i);
                        String numerPregunta = "0" + stringI;
                        //Log.e("Insertando", "radio: " + radioButton.getText().toString() + " NumeroPregunta:" + numerPregunta);
                        ejecutarservicio("https://vvnorth.com/5sGhoner/ContestarPreguntas.php", radioButton.getText().toString(), numerPregunta);
                    }

                    for (int i = 0 + cantidadHallazgos; i < numeroPreguntas; i++) {
                        int radioId = radioGroup2[i].getCheckedRadioButtonId();
                        RadioButton radioButton = findViewById(radioId);
                        String numerPregunta = Integer.toString(i + 1 - cantidadHallazgos);
                        //Log.e("Insertando", "radio: " + radioButton.getText().toString() + " NumeroPregunta:" + numerPregunta);
                        ejecutarservicio("https://vvnorth.com/5sGhoner/ContestarPreguntas.php", radioButton.getText().toString(), numerPregunta);
                    }


                    //Log.e("verificando",":"+"name"+name+"i"+i+"nombreAudaVisual"+nombreAudaVisual);


                            int numeropregunta= (i+1)-cantidadHallazgos;
                ContestarPregunta(name,numeropregunta,nombreAudaVisual,descripcionDelError,numeroImageness);//AQUI



            }
        });



        buttonValores[i].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog9 dialog9 = new Dialog9();
                dialog9.show(getSupportFragmentManager(), "example dialog");

            }
        });

        imageviewHallazgo[i].setOnClickListener(new View.OnClickListener() {//Boton ver Hallazgo.
            @Override
            public void onClick(View v) {


                if(Anterior.equals("")){ //si no hay Numero de auditoria anterio entonces busca en ella misma
                    //Log.e("entre","ACTUAL");
                    ShowImage2(GlobalAyudaVisual,SubArea2,numeroAuditoria,name);
                }else{//Busca el hallazgo encontrada en la auditoria anterior
                    //Log.e("entre","ANTERIOR"+"AydaAnterior: "+AyudaVisual+"SubArea2: "+SubArea2+"Anterior: "+Anterior+"Name oPregunta: "+name);

                    ShowImage2(AyudaVisual,SubArea2,Anterior,name);
                }


            }
        });



       layoutList.addView(layoutView);

    }

    public void ContestarPregunta(String nombrePregunta,int numeroPregunta,String nombreAyudaVisual,String Descripcionerror, String numeroImagenes)
    {


        //actualNumber--;
        String numeropregunta = Integer.toString(numeroPregunta);
        //Log.e("num","PARSEANDO A ESTRING"+numeropregunta);
        Intent intent =new Intent(this,NuevaAuditoria_Pregunta.class);
        intent.putExtra("EXTRA_SESSION_ID", nombrePregunta);
        intent.putExtra("EXTRA_SESSION_ID2", numeroAuditoria);
        intent.putExtra("EXTRA_SESSION_ID3", nombreAyudaVisual);
        intent.putExtra("EXTRA_SESSION_ID4", numeropregunta);
        intent.putExtra("EXTRA_SESSION_ID5", NumeroAyuda);
        intent.putExtra("EXTRA_SESSION_ID6", Anterior);
        intent.putExtra("EXTRA_SESSION_ID7", Descripcionerror);
        intent.putExtra("CANTIDAD_IMAGENES", numeroImagenes);


        //Log.e("que","NumeroImagenes"+numeroImagenes);
        //Toast.makeText(getApplicationContext(),sNumeroPregunta,Toast.LENGTH_SHORT).show();
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
                Log.d("CONSULTADO BD","Respuesta de auditoria"+response.length());
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
                       String hallazgo=jsonObject.getString("Hallazgo");
                        String numeroImagenes =jsonObject.getString("NumeroImagenes");
                        String respuestaanterior=jsonObject.getString("respuestaanterior");
                       subarea=jsonObject.getString("SubArea");
                       String Calificacion=jsonObject.getString("Calificacion");
                       Anterior=jsonObject.getString("Anterior");
                        requestImage(nombre);
                       GlobalAyudaVisual=nombre;
                        String ayudavisual=jsonObject.getString("AyudaAnterior");
                        String SubArea=jsonObject.getString("SubArea");
                        String  AyudaVisu=jsonObject.getString("AyudaVisual");
                       TextView nombresubArea = (TextView)findViewById(R.id.subareaauditando);
                      // Log.e("NumeroImagenes---",""+NumeroImagenes);

                       nombresubArea.setText(SubArea);
                        if(descripcionDelError.equals(""))
                        {
                        errorRespondido[i]="false";}
                        else {
                        errorRespondido[i]="true";}

                        tView.setText(nombre);
                        tCodigo.setText(CodigoAyudaVisual);
                        if(hallazgo.equals("si")){
                            cantidadHallazgos++;
                        }
                        numeroPreguntas++;
                        addView1(nombrePregunta,i,Calificacion,nombre,hallazgo,respuestaanterior,i,descripcionDelError,ayudavisual,SubArea,Anterior,numeroImagenes,AyudaVisu);




                        //PreguntasContestadas();

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