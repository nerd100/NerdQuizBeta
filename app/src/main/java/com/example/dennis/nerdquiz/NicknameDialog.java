package com.example.dennis.nerdquiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Lui on 25.09.2016.
 */
public class NicknameDialog extends DialogFragment {
    private View v;
    SharedPreferences shared_preferences;
    SharedPreferences.Editor shared_preferences_editor;
    EditText usernameedit;



    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        shared_preferences = getActivity().getSharedPreferences("shared_preferences_test",
                Context.MODE_PRIVATE);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        v = inflater.inflate(R.layout.usernamedialog, null);
        usernameedit = (EditText)v.findViewById(R.id.usernameedit);
        usernameedit.addTextChangedListener(new TextWatcher() {
            boolean ignoreChange = false;
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.length() > 10) {
                    if(!ignoreChange) {
                        ignoreChange=true;
                        new AlertDialog.Builder(getActivity()).setTitle("Zeichenlimit erreicht").setMessage("Wähle ein Username mit weniger als " + 12 + " Buchstaben.").setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                               // usernameedit.setText("");
                                ignoreChange=false;
                            }
                        }).show();
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());

        builder.setView(v)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //shared_preferences.edit().remove("username");
                     //   if(usernameedit.getText().toString().length()>2 &&usernameedit.getText().toString().length()<12) {
                            shared_preferences_editor = shared_preferences.edit();
                            shared_preferences_editor.putString("username", usernameedit.getText().toString()).apply();
                            shared_preferences_editor.putBoolean("userset", true).apply();
                            Button button = (Button) getActivity().findViewById(R.id.username);
                            button.setText(usernameedit.getText().toString());
                            button.setTextColor(Color.WHITE);
                            button.setEnabled(false);
                   //     }
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert);

        return builder.create();
    }


}
