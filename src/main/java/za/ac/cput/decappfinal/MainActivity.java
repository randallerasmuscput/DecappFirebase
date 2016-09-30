package za.ac.cput.decappfinal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText username;
    private EditText password;
    private EditText authNumber;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressDialog mprogressDialog;

    private Firebase mRootref;

    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    private GoogleApiClient mGoogleApiClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();

        Firebase.setAndroidContext(this);
         mRootref = new Firebase("https://decappfirebasebackend.firebaseio.com/");

        mprogressDialog = new ProgressDialog(this);

       //type casting to buttons for Actionlisteners
        username = (EditText)findViewById(R.id.editTextUsername);
        password = (EditText)findViewById(R.id.editTextPassword);
        authNumber = (EditText)findViewById(R.id.editTextAuthNr);

        Button btnSignUp = (Button) findViewById(R.id.btnSignUp);
        Button btnCancelMain = (Button) findViewById(R.id.btnCancel);
        Button btnLogin = (Button) findViewById(R.id.btnLogin);


        progressDialog = new ProgressDialog(this);

        //this also works like a charm attached
        btnSignUp.setOnClickListener(this);
        btnCancelMain.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        // [START config_signin]
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser()!=null)
                {// profile activity
                    //finish();
                    startActivity(new Intent(MainActivity.this,DecappActivity.class));
                }
            }
        } ;

    }


    @Override
    protected void onStart() {
        //this checks that the user is already logged in
        super.onStart();
//       firebaseAuth.addAuthStateListener(mAuthListener);
    }

    private void userlogin()
    {
        final String name = username.getText().toString().trim();
        final String pass = password.getText().toString().trim();
        final String auth = authNumber.getText().toString().trim();



        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(pass))
        {   //email is empty
            Toast.makeText(getApplicationContext(), "Please enter all the fields", Toast.LENGTH_SHORT).show();
            //stopping the function further
            return;

        }else if(name==(""))
        {
            Toast.makeText(getApplicationContext(), "Please enter Valid Email", Toast.LENGTH_SHORT).show();
        }
        // if it get here validataions are ok
        // we will first show a progress bar
        progressDialog.setMessage("Registering User..........");
        progressDialog.show();
        progressDialog.dismiss();

        firebaseAuth.signInWithEmailAndPassword(name,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {



                        if(task.isSuccessful())
                        {
                            mprogressDialog.setMessage("Logging In........");
                            mprogressDialog.show();
                            Toast.makeText(getApplicationContext(), "Registration Successfull", Toast.LENGTH_SHORT).show();
                            String value = authNumber.getText().toString();
                            Firebase childref = mRootref.child("AuthNumber");
                            mRootref.push().setValue(value);
                            Intent intent = new Intent(getApplicationContext(),DecappActivity.class);
                            startActivity(intent);
                            mprogressDialog.dismiss();
                        }else if(!task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(), "Your Details does not exist.. Please Signup", Toast.LENGTH_SHORT).show();
                            return;
                        }


                    }

                });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignUp:
                Intent signUpIntent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(signUpIntent);
                break;
            case R.id.btnCancel:
                System.exit(1);
                FirebaseAuth.getInstance().signOut();
                break;
            case R.id.btnLogin:
                userlogin();

                break;
        }
    }

}
