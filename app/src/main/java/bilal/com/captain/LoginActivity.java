package bilal.com.captain;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.valdesekamdem.library.mdtoast.MDToast;

import bilal.com.captain.CaptainInterfaces.Initialization;
import bilal.com.captain.Util.CustomToast;
import bilal.com.captain.Util.InternetConnection;
import bilal.com.captain.Util.Util;

public class LoginActivity extends AppCompatActivity implements Initialization, View.OnClickListener {

    EditText et_email, et_password;

    LinearLayout login;

    TextView Signup;

    FirebaseAuth firebaseAuth;

    ProgressDialog waitDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialise();
    }

    @Override
    public void initialise() {

        firebaseAuth = FirebaseAuth.getInstance();

        waitDialog = new ProgressDialog(LoginActivity.this);

        waitDialog.setCancelable(false);

        waitDialog.setTitle("Captain Manager");

        waitDialog.setMessage("Please Wait");

        et_email = (EditText) findViewById(R.id.email);

        et_password = (EditText) findViewById(R.id.password);

        login = (LinearLayout) findViewById(R.id.login);

        login.setOnClickListener(LoginActivity.this);

        Signup = (TextView)findViewById(R.id.signuptextview);

        Signup.setOnClickListener(LoginActivity.this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(firebaseAuth.getCurrentUser() != null){

            finish();

            startActivity(new Intent(LoginActivity.this, MainActivity.class));

        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.login:
                if(Util.etValidate(et_email) && Util.emailValidate(et_email) && Util.etValidate(et_password)){

                    String email = et_email.getText().toString().trim();

                    String password = et_password.getText().toString().trim();

                    if(InternetConnection.internetConnectionAvailable(2000)){

                        authenticateUserWithFireBase(email,password);

                    }else {

                        CustomToast.showToast(LoginActivity.this,"Please Check Internet", MDToast.TYPE_WARNING);

                    }

                }
                break;

            case R.id.signuptextview:
                startActivity(new Intent(LoginActivity.this, bilal.com.captain.Signup.class));

        }

    }

    private void authenticateUserWithFireBase(String email, String password){

        waitDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                waitDialog.dismiss();

                if(task.isSuccessful()){

                    FirebaseDatabase.getInstance().getReference().child("Public_User").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child("isonline").setValue(true);

                    FirebaseDatabase.getInstance().getReference().child("USER").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child("user").child("isonline").setValue(true);

                    finish();

                    startActivity(new Intent(LoginActivity.this, MainActivity.class));

                }
                else {

                    CustomToast.showToast(LoginActivity.this,"Invalid Email Or Password",MDToast.TYPE_ERROR);

                }
            }
        });


    }
}
