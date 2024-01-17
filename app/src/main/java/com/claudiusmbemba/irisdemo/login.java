package com.claudiusmbemba.irisdemo;

        import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

        import static com.claudiusmbemba.irisdemo.signup.isConnectingToInternet;

public class login extends AppCompatActivity implements View.OnClickListener {
    private Button buttonSignIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button textViewSignUp;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(isConnectingToInternet(login.this))
        {
            //  Toast.makeText(getApplicationContext(),"internet is available",Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getApplicationContext(),"internet is not  available",Toast.LENGTH_LONG).show();
        }

        firebaseAuth=FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()!=null)
        {
            //profile Activity Here
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));

        }
        progressDialog = new ProgressDialog(this);

        editTextEmail=(EditText)findViewById(R.id.editTextEmail);
        editTextPassword=(EditText)findViewById(R.id.editTextPassword);
        buttonSignIn=(Button) findViewById(R.id.buttonSignIn);
        textViewSignUp=(Button) findViewById(R.id.textViewSignUp);

        buttonSignIn.setOnClickListener(this);
        textViewSignUp.setOnClickListener(this);
    }
    private void UserLogin()
    {
        String email=editTextEmail.getText().toString().trim();
        String password=editTextPassword.getText().toString().trim();
        if(TextUtils.isEmpty(email))
        {
            //email is empty
            Toast.makeText(this,"please enter email",Toast.LENGTH_SHORT).show();
            //stopping the function and execution further
            return;
        }
        if(TextUtils.isEmpty(password))
        {
            //password is empty
            Toast.makeText(this,"please enter password",Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Loging User.....");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if(task.isSuccessful())
                        {
                            progressDialog.dismiss();
                            //we can start profile activity here
                            finish();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else
                        {
                            progressDialog.dismiss();
                            Toast.makeText(login.this, "wrong credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if (view == buttonSignIn) {
            UserLogin();
        }
        if (view == textViewSignUp) {
            startActivity(new Intent(this, signup.class));
        }
    }
}
