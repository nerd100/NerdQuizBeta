package com.example.dennis.nerdquiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Lui on 25.09.2016.
 */
public class NicknameDialog extends DialogFragment {
    private View v;
    SharedPreferences shared_preferences;
    SharedPreferences.Editor shared_preferences_editor;
    EditText usernameedit;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        shared_preferences = getActivity().getSharedPreferences("username",0);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        v = inflater.inflate(R.layout.usernamedialog, null);
        usernameedit = (EditText)v.findViewById(R.id.usernameedit);
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());

        builder.setView(v)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        //shared_preferences.edit().remove("username");
                        shared_preferences_editor = shared_preferences.edit();
                        shared_preferences_editor.putString("username",usernameedit.getText().toString());
                        shared_preferences_editor.apply();
                        Button test=(Button)getActivity().findViewById(R.id.username);
                        test.setText(shared_preferences.getString("username", "Default"));

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert);

        return builder.create();
    }

}
