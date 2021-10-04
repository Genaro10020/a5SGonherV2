package com.example.a5SGonher;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class ExampleDialogData extends AppCompatDialogFragment {

    private EditText editTextUsername;
    private EditText editPassword;
    private ExampleDialogDataListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog,null);
        builder.setView(view)
                .setTitle("Comentario")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
String username= editTextUsername.getText().toString();
               // String password= editPassword.getText().toString();
listener.applyTexts(username);


            }
        });

        editTextUsername=view.findViewById(R.id.edit_username);
      //  editPassword=view.findViewById(R.id.edit_password);



        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener =(ExampleDialogDataListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement Example Dialog Lister" );
        }
    }

    public interface  ExampleDialogDataListener{

        void applyTexts(String username);


    }

}
