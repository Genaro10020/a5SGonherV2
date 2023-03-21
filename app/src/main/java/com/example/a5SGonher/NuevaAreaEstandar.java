package com.example.a5SGonher;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NuevaAreaEstandar extends AppCompatActivity {
    ImageView tomar_foto_area;
    Bitmap bitmapf;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_area_estandar);
        TextView titulo = findViewById(R.id.titulo_toolbar);
        titulo.setText("Nueva Área");

        tomar_foto_area = findViewById(R.id.icono_foto_area);

        tomar_foto_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tomarFoto();
            }
        });
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
            tomar_foto_area.setImageBitmap(imageBitmap);
            //miniatura= ThumbnailUtils.extractThumbnail(imageBitmap, 100, 100);
            //subirImagen(miniatura);

            tomar_foto_area.setImageURI(data.getData());
            Bitmap bitmap = ((BitmapDrawable)tomar_foto_area.getDrawable()).getBitmap();

            bitmapf=bitmap;

        }
    }




}
