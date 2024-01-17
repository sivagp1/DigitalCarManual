package com.claudiusmbemba.irisdemo;


        import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class signup extends AppCompatActivity implements View.OnClickListener{

    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button google;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    //private SignInButton mGoogleBtn;

    private static final int RC_SIGN_IN= 1;
    private GoogleApiClient mGoogleApiClient;
    private static final String TAG="signup";
    public int val;
// ...


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        val = (int)getIntent().getIntExtra(MainActivity.FOOD,-1);
        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref1", 0); // 0 - for private mode
        final SharedPreferences.Editor editor1 = pref1.edit();
        //if(pref1.getInt("key_name1",0)==1){
          //  editor1.putInt("key_name1",0);
            //editor1.commit();
            //Auth.GoogleSignInApi.signOut(mGoogleApiClient);
            //Toast.makeText(this," google logging out...",Toast.LENGTH_SHORT).show();
        //}
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient=new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(signup.this,"you got mistake",Toast.LENGTH_LONG).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();




        if(isConnectingToInternet(signup.this))
        {
            //  Toast.makeText(getApplicationContext(),"internet is available",Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getApplicationContext(),"internet is not  available",Toast.LENGTH_LONG).show();
        }


        firebaseAuth = FirebaseAuth.getInstance();
        mAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()!=null)
        {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
        progressDialog = new ProgressDialog(this);

        buttonRegister=(Button)findViewById(R.id.buttonRegister);
        editTextEmail=(EditText)findViewById(R.id.username);
        editTextPassword=(EditText)findViewById(R.id.pass);
        google = (Button)findViewById(R.id.google) ;
        //TextViewSignIn=(TextView)findViewById(R.id.TextViewSignIn);

        buttonRegister.setOnClickListener(this);
        google.setOnClickListener(this);

    }
    public void setmGoogleApiClient(){

    }
    public GoogleApiClient getmGoogleApiClient(){
        return this.mGoogleApiClient;
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }





    public static boolean isConnectingToInternet(Context context)
    {
        ConnectivityManager connectivity =
                (ConnectivityManager) context.getSystemService(
                        Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
        }
        return false;
    }
    private void registerUser() {
        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            //email is empty
            Toast.makeText(this, "please enter email", Toast.LENGTH_SHORT).show();
            //stopping the function and execution further
            return;

        }

        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";



        if (email.matches(emailPattern))
        {
            //         Toast.makeText(getApplicationContext(),"valid email address",Toast.LENGTH_SHORT).show();
            // or
            //.setText("valid email");
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Invalid email address",Toast.LENGTH_SHORT).show();
            //or
            //  textView.setText("invalid email");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            //password is empty
            Toast.makeText(this, "please enter password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (editTextPassword.getText().toString().length() < 8 ){

            Toast.makeText(this, "it should be 8 character password", Toast.LENGTH_SHORT).show();
            return;
        }
        final String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";

        if (password.matches(passwordPattern))
        {
            //Toast.makeText(getApplicationContext(),"valid password",Toast.LENGTH_SHORT).show();
            // or
            //.setText("valid email");
        }
        else
        {
            Toast.makeText(getApplicationContext(),"atleast one number & character required",Toast.LENGTH_SHORT).show();
            //or
            //  textView.setText("invalid email");
            return;
        }

        progressDialog.setMessage("Registering User.....");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            //profile activity here
                            Toast.makeText(signup.this,"registered successfully",Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                        else
                        {
                            Toast.makeText(signup.this,"registration error....",Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
    }



        private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
            Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
            progressDialog.setMessage("Logging User.....");
            progressDialog.show();
            AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithCredential:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                //Toast.makeText(signup.this, "google signup error....", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                finish();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithCredential:failure", task.getException());
                                //Snackbar.make(findViewById(R.id.signup_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                                Toast.makeText(signup.this, "signup error....", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }

                            // ...
                        }
                    });

        }



    @Override
    public void onClick(View view) {
        if(view == buttonRegister)
        {
            registerUser();
        }

        if(view == google)
        {
            //will do nothingi(view == textViewSignUp) {f
            signIn();
        }
    }

}
