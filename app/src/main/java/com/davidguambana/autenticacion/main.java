package com.davidguambana.autenticacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class main extends AppCompatActivity {

    FirebaseAuth autenticacion;
    FirebaseUser user;
    Button salir;
    TextView txtMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        autenticacion = FirebaseAuth.getInstance();
        salir = findViewById(R.id.btnSalir);
        txtMail = findViewById(R.id.txtMail);
        user = autenticacion.getCurrentUser();
        if (user == null){
            Intent i = new Intent(getApplicationContext(), Login.class);
            startActivity(i);
            finish();
        } else{
            txtMail.setText(user.getEmail());
        }
        salir.setOnClickListener(l-> abrirLogin());
    }
    public void abrirLogin(){
        //cerrar sesión
        FirebaseAuth.getInstance().signOut();
        //abrir login
        Intent i = new Intent(getApplicationContext(), Login.class);
        startActivity(i);
        finish();
    }
}