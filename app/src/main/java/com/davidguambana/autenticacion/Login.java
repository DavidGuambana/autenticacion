package com.davidguambana.autenticacion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    EditText mail, pass;
    TextView txt_register;
    Button loguear;
    FirebaseAuth autenticacion;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //parseos:
        autenticacion = FirebaseAuth.getInstance();
        mail = findViewById(R.id.txtMailLog);
        pass = findViewById(R.id.txtPassLog);
        txt_register = findViewById(R.id.txtRegister);
        loguear = findViewById(R.id.btnIngresar);
        progress = findViewById(R.id.progressBarLog);
        //llamar al metodo register y abrirRegister:
        loguear.setOnClickListener(l-> login());
        txt_register.setOnClickListener(l-> abrirRegister());
    }

    @Override
    public void onStart(){
        super.onStart();
        FirebaseUser usuario = autenticacion.getCurrentUser();
        if (usuario != null){
            Intent i = new Intent(getApplicationContext(), main.class);
            startActivity(i);
            finish();
        }
    }
    public void login(){
        progress.setVisibility(View.VISIBLE);
        if(mail.getText().toString().isEmpty() || pass.getText().toString().isEmpty()){
            Toast.makeText(Login.this,"¡Aún hay campos por completar!",Toast.LENGTH_LONG).show();
        } else{
            autenticacion.signInWithEmailAndPassword(mail.getText().toString(),pass.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progress.setVisibility(View.GONE);
                            if (task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"¡Login exitoso!", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(getApplicationContext(), main.class);
                                startActivity(i);
                                finish();
                            } else{
                                Toast.makeText(Login.this,"¡Usuario no valido!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

        }
    }
    public void abrirRegister(){
        Intent i = new Intent(getApplicationContext(), Register.class);
        startActivity(i);
        finish();
    }
}