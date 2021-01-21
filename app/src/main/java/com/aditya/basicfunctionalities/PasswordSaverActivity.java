package com.aditya.basicfunctionalities;

//This Activty code is for saving password in mobile with the help of Shared Preferences

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PasswordSaverActivity extends AppCompatActivity {

    private EditText mailEdiText,passwordEdtText;
    private Button saveBtn;
    private CheckBox checkBox;
    SharedPreferences sharedPreferences;
    public static final String sharpref_key = "password";
    public static final String sharpref_Mail = "Email_id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_saver);

        mailEdiText = (EditText) findViewById(R.id.mailId);
        passwordEdtText = (EditText) findViewById(R.id.password);
        saveBtn = (Button) findViewById(R.id.save);
        checkBox = (CheckBox) findViewById(R.id.checkBox);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    passwordEdtText.setTransformationMethod(null);
                }
                else {
                    passwordEdtText.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });



        sharedPreferences = this.getPreferences(MODE_PRIVATE);
        if(sharedPreferences.getString(sharpref_key,null)==null){
            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PasswordSaverActivity.this);
                    builder.setTitle("Save This Pasword");
                    builder.setMessage("Save your Password,\nso you don't need to enter it again");
                    builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(sharpref_key,passwordEdtText.getText().toString());
                            editor.putString(sharpref_Mail,mailEdiText.getText().toString());
                            editor.apply();
                            Toast.makeText(PasswordSaverActivity.this, "Password Saved", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(PasswordSaverActivity.this,MainActivity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("Don't Save", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(PasswordSaverActivity.this,MainActivity.class);
                            startActivity(intent);
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
        }
        else {
            final String smail = sharedPreferences.getString(sharpref_Mail,null);
            final String spass = sharedPreferences.getString(sharpref_key,null);

            mailEdiText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(mailEdiText.getText().toString().equals(smail)){
                        passwordEdtText.setText(spass);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mailEdiText.getText().toString().equals(smail) && passwordEdtText.getText().toString().equals(spass)){
                        //Intent
                        Intent intent = new Intent(PasswordSaverActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                    else {
                        //Toast.makeText(PasswordSaverActivity.this, "Incorrect Mail or Password", Toast.LENGTH_SHORT).show();
                        //Custom Toast

                        LayoutInflater inflater =getLayoutInflater();
                        View layout = inflater.inflate(R.layout.custom_toast_layout,(ViewGroup) findViewById(R.id.container));
                        TextView text = layout.findViewById(R.id.text);
                        text.setText("Incorrect Mail or Password");
                        Toast toast = new Toast(getApplicationContext());
                        toast.setGravity(Gravity.BOTTOM,0,200);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(layout);
                        toast.show();
                    }
                }
            });
        }
    }


}
