package com.aulas.whatsalae;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aulas.whatsalae.config.ConfigFirebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button bt = findViewById(R.id.btLogar);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtEmail = ((EditText) findViewById(R.id.editLoginEmail)).getText().toString();
                String txtSenha = ((EditText) findViewById(R.id.editLoginSenha)).getText().toString();

                // Validar se esta vazio

                logarUsuario(txtEmail,txtSenha);

            }
        });
    }

    private void logarUsuario(String txtEmail, String txtSenha) {
        autenticacao = ConfigFirebase.getFirebaseAuthentic();

        autenticacao.signInWithEmailAndPassword(txtEmail,txtSenha)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Intent intent =new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            String msg = "Usuario / senha inválidos";
                            try{
                                throw task.getException();
                            } catch ( FirebaseAuthInvalidUserException e){
                                msg= "Usuario não cadastro";
                            } catch ( FirebaseAuthInvalidCredentialsException e){
                                msg= " Voce errou a senha..!!!";
                            } catch ( Exception e){
                                System.out.println(e.getMessage());
                            }
                            Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public void abrirCadastro(View view) {
        Intent intent = new Intent(this, CadastroActivity.class);
        startActivity(intent);
    }

    // Abrir a tela principal caso ja tenha login ativo


    @Override
    protected void onStart() {
        super.onStart();
        autenticacao = ConfigFirebase.getFirebaseAuthentic();

        FirebaseUser usuarioAtual = autenticacao.getCurrentUser();

        if (usuarioAtual !=null){
            Intent intent =new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }


    }
}