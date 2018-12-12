package com.example.richardsenyange.breedlocator;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import java.util.concurrent.TimeUnit;
public class phoneverification extends AppCompatActivity {

private String mVerificationId;
private EditText editTextCode;
private FirebaseAuth mAuth;
Intent intent=new Intent();
//getting country code
    String code_cntry=intent.getStringExtra("code");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phoneverification);
         mAuth=FirebaseAuth.getInstance();
         editTextCode=findViewById(R.id.editTextCode);
       // getting mobile number
        String mobile = intent.getStringExtra("mobile");
        sendVerificationCode(mobile);
findViewById(R.id.buttonSignIn).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String code=editTextCode.getText().toString().trim();
        if(code.isEmpty()||code.length()<6){
            editTextCode.setError("Enter valid code");
            editTextCode.requestFocus();

        }
    }
});
    }
    //sending verification code
    private  void sendVerificationCode(String  mobile){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+"+code_cntry+mobile,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks
        );
    }
    //the callback to detect the verification status
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks()
    {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential
                                                    phoneAuthCredential) {

            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
                editTextCode.setText(code);
                //verifying the code
                verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(phoneverification.this, e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s,
                               PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            //storing the verification id that is sent to the user
            mVerificationId = s;
        }
    };


    private void verifyVerificationCode(String code) {
        //creating the credential
        PhoneAuthCredential credential =
                PhoneAuthProvider.getCredential(mVerificationId, code);

        //signing the user
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential
                                                       credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(phoneverification.this, new
                        OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull
                                                           Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    //verification successful we will start theprofile activity
                                    Intent intent = new
                                            Intent(phoneverification.this,Home.class);

                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                                            Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);

                                } else {

                                    //verification unsuccessful.. display anerror message

                                    String message = "Something is wrong, we will fix it soon...";

                                    if (task.getException() instanceof
                                            FirebaseAuthInvalidCredentialsException) {
                                        message = "Invalid code entered...";
                                    }

                                    Snackbar snackbar = Snackbar.make(findViewById(R.id.parent), message, Snackbar.LENGTH_LONG);
                                    snackbar.setAction("Dismiss", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                }
                                            });
                                    snackbar.show();
                                }
                            }
                        });
    }

}
