package com.leodevel.loginandroid;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    private TextView lbNome, lbEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        lbNome = (TextView) findViewById(R.id.lbNome);
        lbEmail = (TextView) findViewById(R.id.lbEmail);

        FirebaseAuth auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null){

            lbNome.setText(auth.getCurrentUser().getDisplayName());
            lbEmail.setText(auth.getCurrentUser().getEmail());

        }else{

            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

    }


    public void sair(View v) {

        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(HomeActivity.this, MainActivity.class));
                        finish();
                    }
                });

    }



}
