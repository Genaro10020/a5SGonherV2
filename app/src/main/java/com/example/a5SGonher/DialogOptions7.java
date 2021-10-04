package com.example.a5SGonher;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class DialogOptions7 extends AppCompatDialogFragment {
    private DialogOptions1Listener listener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("¿Se ha realizado el requerimiento?")
                .setMessage("")
                .setNegativeButton("No Hecho", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onNoClicked2();
                    }
                })
                .setPositiveButton("Hecho", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onYesClicked2();
                    }
                });

        return builder.create();
    }

    public interface  DialogOptions1Listener
    {
        void onYesClicked2();
        void onNoClicked2();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try{
        listener = (DialogOptions1Listener) context;}
        catch(ClassCastException e){
            throw new ClassCastException(context.toString()
                    +"need example dialogListener");
        }
    }
}
