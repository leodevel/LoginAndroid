# Login com o Facebook e Google usando o Firebase-Auth

## Referências 

Este exemplo foi desenvolvido seguindo o tutorial do firebase, através do endereço https://github.com/firebase/FirebaseUI-Android/tree/master/auth.

Importante ressaltar que é necessário criar um projeto android do facebook através do endereço https://developers.facebook.com/ e um projeto android do firebase através do endereço https://console.firebase.google.com/.

## Login

```
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseAuth auth = FirebaseAuth.getInstance();

        // Verifica se o usuario esta logado
        if (auth.getCurrentUser() != null) {
            
            // abre a tela home
            startActivity(new Intent(this, HomeActivity.class));
            finish();

        } else {
            
            // abre a tela de login
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setProviders(
                                    AuthUI.EMAIL_PROVIDER,
                                    AuthUI.GOOGLE_PROVIDER,
                                    AuthUI.FACEBOOK_PROVIDER)
                            .build(),
                    RC_SIGN_IN);

        }

}

```
O código acima é executado durante a criação da tela principal. Ele verifica se o usuário encontra-se logado na plataforma. Caso esteja logado, a aplicação redireciona o usuário para a home, caso contrário, abre a tela de login. 

Quando o usuário efetua o login usando sua conta do facebook ou do google,a tela é encerrada e código da tela principal é executado:

```
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {

            if (resultCode == RESULT_OK) {

                startActivity(new Intent(this, HomeActivity.class));
                finish();

            }

        }
}
    
```
Ele verifica se a ação foi concluída, e então, redireciona para a tela inicial.

## Logout

O código abaixo encerra a sessão do usuário, quando clicado no botão sair.

```
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

```

## Capturar informações do usuário conectado

O código abaixo é executado durante a criação da tela home. Ele verifica se o usuário continua conectado. Caso esteja, o nome do usuário e o email é exibido na tela, caso contrário, o usuário é redirecionado para a tela principal, forçando que faça o login.

```
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

```
