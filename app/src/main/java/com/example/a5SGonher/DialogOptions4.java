package com.example.a5SGonher;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class DialogOptions4 extends AppCompatDialogFragment {
    private DialogOptions1Listener listener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Aviso")
                .setMessage("El auditor se a agregado Corecctamente")

                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
listener.onYesClicked();
                    }
                });

        return builder.create();
    }

    public interface  DialogOptions1Listener
    {
        void onYesClicked();


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
