package bilal.com.captain;

import android.app.ProgressDialog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.valdesekamdem.library.mdtoast.MDToast;

import bilal.com.captain.Util.CustomToast;
import bilal.com.captain.Util.InternetConnection;
import bilal.com.captain.Util.Util;


public class Signup extends AppCompatActivity {

    EditText inputUsername;
    EditText inputEmail;
    EditText inputPassword;
    Button buttonsignup;
    FirebaseAuth auth;
    ProgressDialog waitDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();

        waitDialog = new ProgressDialog(Signup.this);

        waitDialog.setCancelable(false);

        waitDialog.setTitle("Captain Manager");

        waitDialog.setMessage("Signing up");

        inputUsername = (EditText)findViewById(R.id.editText2);
        inputEmail = (EditText)findViewById(R.id.editText3);
        inputPassword = (EditText)findViewById(R.id.editText4);

        buttonsignup = (Button)findViewById(R.id.button2);


        buttonsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(InternetConnection.internetConnectionAvailable(2000)){
                    if((Util.etValidate(inputUsername)) && (Util.etValidate(inputEmail)) && (Util.etValidate(inputPassword))){
                        final String email = inputEmail.getText().toString();
                        final String password = inputPassword.getText().toString();
                        final String username = inputUsername.getText().toString();

                        waitDialog.show();


                        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                waitDialog.dismiss();

                                if(task.isSuccessful()){
                                    String id = auth.getCurrentUser().getUid();

                                    Firebase firebase = new Firebase(id,email,password,username,true);

                                    FirebaseDatabase.getInstance().getReference().child("USER").child(auth.getCurrentUser().getUid()).child("user").setValue(firebase);

                                    FirebaseDatabase.getInstance().getReference().child("Public_User").child(auth.getCurrentUser().getUid()).setValue(firebase);

                                    startActivity(new Intent(Signup.this,LoginActivity.class));
                                    finish();
//                                    auth.signOut();
                                }

                                else{

                                }
                            }
                        });

                    }

                }

                else{
                    CustomToast.showToast(Signup.this,"Please Check Internet", MDToast.TYPE_WARNING);
                }
            }
        });







    }

}
