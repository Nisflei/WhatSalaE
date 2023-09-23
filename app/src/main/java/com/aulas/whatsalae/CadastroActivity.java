package com.aulas.whatsalae;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aulas.whatsalae.config.ConfigFirebase;
import com.aulas.whatsalae.model.Usuario;
import com.aulas.whatsalae.tools.Base64custom;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CadastroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        Button bt = findViewById(R.id.btCadastro);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtNome = ((EditText) findViewById(R.id.editNome)).getText().toString();
                String txtEmail = ((EditText) findViewById(R.id.editEmail)).getText().toString();
                String txtSenha = ((EditText) findViewById(R.id.editSenha)).getText().toString();

                // Validar se as informações foram preenchidas

                Usuario usuario = new Usuario(txtNome,txtEmail,txtSenha );
                salvarUsuario(usuario);
            }
        });
    }

    private void salvarUsuario(Usuario usuario) {

        // Criar login
        FirebaseAuth autenticacao = ConfigFirebase.getFirebaseAuthentic();

        autenticacao.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(CadastroActivity.this, "Usuario Cadastrado", Toast.LENGTH_SHORT).show();

                            String id = Base64custom.condificar(usuario.getEmail());
                            usuario.setId(id);
                            usuario.salvarDB();


                        } else {
                            Toast.makeText(CadastroActivity.this, "Erro ao cadastrar.. verifique os dados", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        finish();

        // Abrir database

        // salvar usuario
    }
}