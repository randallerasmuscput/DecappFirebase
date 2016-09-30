package za.ac.cput.decappfinal;

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

/**
 * Created by User on 2016/05/12.
 */
public class SignUpActivity extends AppCompatActivity implements View.OnClickListener
{
    private EditText username;
    private EditText password;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        Button btnSignUp = (Button) findViewById(R.id.btnSignUp);
        username = (EditText)findViewById(R.id.editTx_name);
        password = (EditText)findViewById(R.id.editTxt_pass1);


        btnSignUp.setOnClickListener(this);


    }


    public void registerUser()
    {

       final String name = username.getText().toString().trim();
       final String pass = password.getText().toString().trim();


        if(TextUtils.isEmpty(name))
        {   //email is empty
            Toast.makeText(getApplicationContext(), "Please enter Correct Username", Toast.LENGTH_SHORT).show();
            //stopping the function further
            return;
        }
        if(TextUtils.isEmpty(pass))
        {
            Toast.makeText(getApplicationContext(), "Please enter Correct Password", Toast.LENGTH_SHORT).show();
            //stopping the function further
            return ;
        }
        // if it get here validataions are ok
        // we will first show a progress bar
        progressDialog.setMessage("Registering User..........");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(name,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(SignUpActivity.this,"Registration Successfully", Toast.LENGTH_SHORT).show();

                        }else  {
                            Toast.makeText(SignUpActivity.this,"Could not Register...Please try again", Toast.LENGTH_SHORT).show();
                        }if (!name.equals("")||!pass.equals(""))
                        {

                            Toast.makeText(SignUpActivity.this,"Complete all the fields....", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });

    }
    @Override
    public void onClick(View v)
    {

        if(v.getId()==R.id.btnSignUp)
        {
           registerUser();
            Intent signUpIntent = new Intent(SignUpActivity.this, MainActivity.class);
            startActivity(signUpIntent);

        }if(v.getId()==R.id.button5)
    {
        Intent backIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(backIntent);
    }

//            EditText uname = (EditText)findViewById(R.id.editTx_name);

//            EditText pass1 = (EditText)findViewById(R.id.editTxt_pass1);
//            EditText pass2 = (EditText)findViewById(R.id.editTxt_pass2);
//            EditText email = (EditText)findViewById(R.id.editTxt_authorizationNumber);
//
//            //this convert the value to string values
//        String unamestr = uname.getText().toString(); //this is how you fetch the text from editText
//        String pass1str = pass1.getText().toString();
//        String pass2str = pass2.getText().toString();
//        String emailstr = email.getText().toString();
//
//            // this is to test that the passwords match
//            if (!pass1str.equals(pass2str))
//            {
//                // this test to
//                Toast pass = Toast.makeText(SignUpActivity.this,"Passwords Dont Match",Toast.LENGTH_SHORT);
//                pass.show();
//            } else if(pass1str.equals(pass2str))
//            {
////                userRepositoryImpl.save(new User());
//                Toast pass = Toast.makeText(SignUpActivity.this,"SignUpActivity Successfull",Toast.LENGTH_SHORT);
//                pass.show();
//                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//                startActivity(intent);
//            }
        }
    }

