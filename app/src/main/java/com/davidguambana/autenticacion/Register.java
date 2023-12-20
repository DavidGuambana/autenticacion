package com.davidguambana.autenticacion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {
    EditText mail, pass;
    TextView txt_login;
    Button registrar;
    FirebaseAuth autenticacion;
    ProgressBar progress;

    public void onStart(){
        super.onStart();
        FirebaseUser usuario = autenticacion.getCurrentUser();
        if (usuario != null){
            Intent i = new Intent(getApplicationContext(), main.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //parseos:
        autenticacion = FirebaseAuth.getInstance();
        mail = findViewById(R.id.txtMailReg);
        pass = findViewById(R.id.txtPassReg);
        txt_login = findViewById(R.id.txtLogin);
        registrar = findViewById(R.id.btnRegistrar);
        progress = findViewById(R.id.progressBarReg);
        //llamar al metodo register y abrirLogin:
        registrar.setOnClickListener(l-> register());
        txt_login.setOnClickListener(l-> abrirLogin());
    }

    public void register(){
        progress.setVisibility(View.VISIBLE);
        if(mail.getText().toString().isEmpty() || pass.getText().toString().isEmpty()){
            Toast.makeText(Register.this,"¡Aún hay campos por completar!",Toast.LENGTH_LONG).show();
        } else{
            autenticacion.createUserWithEmailAndPassword(mail.getText().toString(),pass.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progress.setVisibility(View.GONE);
                            if (task.isSuccessful()){
                                Toast.makeText(Register.this,"¡Registro exitoso!", Toast.LENGTH_LONG).show();
                                abrirLogin();
                            } else{
                                Toast.makeText(Register.this,"¡Registro fallido!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

        }
    }
    public void abrirLogin(){
        Intent i = new Intent(getApplicationContext(), Login.class);
        startActivity(i);
        finish();
    }
}