package com.example.richardsenyange.breedlocator;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class phonesignup extends AppCompatActivity {
    private Toast toast;
    private EditText editTextMobile;
    private int field_switcher;
    private Intent intent;
    private FirebaseAuth mAuth;
    private ProgressDialog googleBar;
    private TextView country_codes;
    String code;
    private CallbackManager mCallbackManager;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallbacks;
    private String names[]={"Phone","Facebook","Google"};
    private static final int RC_SIGN_IN = 9001;
    GoogleApiClient mGoogleSignInClient;
    FirebaseAuth.AuthStateListener mAuthListener;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alt_signup);
        country_codes=(TextView) findViewById(R.id.country_code);
        intent = getIntent();
        mAuth = FirebaseAuth.getInstance();


        field_switcher = intent.getIntExtra("value", 0);
        editTextMobile = findViewById(R.id.editTextMobile);
          mAuthListener=new FirebaseAuth.AuthStateListener() {
              @Override
              public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                  if(firebaseAuth.getCurrentUser()!=null){
                      startActivity(new Intent(getApplicationContext(),Home.class));
                  }
              }
          };
          country_codes.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  PopupMenu popup = new PopupMenu(getApplicationContext(), v);
                  try {
                      Field[] fields = popup.getClass().getDeclaredFields();
                      for (Field field : fields) {
                          if ("mPopup".equals(field.getName())) {
                              field.setAccessible(true);
                              Object menuPopupHelper = field.get(popup);
                              Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                              Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                              setForceIcons.invoke(menuPopupHelper, true);
                              break;
                          }
                      }
                  } catch (Exception e) {
                      e.printStackTrace();
                  }
                  popup.getMenuInflater().inflate(R.menu.country_codes_menu, popup.getMenu());
                  popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                      public boolean onMenuItemClick(MenuItem item) {
                          country_codes.setText(item.getTitle());
                          code=item.getTitle().toString();
                          //Toast.makeText(getApplicationContext(), "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                          return true;
                      }
                  });
                  popup.show();

              }
          });

        //switch password views
        switch (field_switcher) {
            case 0:

                findViewById(R.id.buttonFacebookLogin).setVisibility(View.INVISIBLE);
                findViewById(R.id.sign_in_button).setVisibility(View.INVISIBLE);
                findViewById(R.id.buttonContinue).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String mobile = editTextMobile.getText().toString().trim();

                        if (mobile.isEmpty() || mobile.length() < 10) {
                            editTextMobile.setError("Enter a valid mobile");
                            editTextMobile.requestFocus();
                            return;
                        }

                        Intent intent = new Intent(phonesignup.this, phoneverification.class);
                        intent.putExtra("mobile", mobile);
                        intent.putExtra("code",String.valueOf(Country_code_mapper(code)));
                        startActivity(intent);
                    }
                });


                break;
            case 1:
//facebook login
                //setphone number details to invisible
                editTextMobile.setVisibility(View.INVISIBLE);
findViewById(R.id.phn_pass).setVisibility(View.INVISIBLE);
                findViewById(R.id.country_code).setVisibility(View.INVISIBLE);
                findViewById(R.id.buttonContinue).setVisibility(View.INVISIBLE);
                findViewById(R.id.imageView).setVisibility(View.INVISIBLE);
                findViewById(R.id.sign_in_button).setVisibility(View.INVISIBLE);

                toast = Toast.makeText(getApplicationContext(), "Tap button phonesignup with " + names[1], Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                toast.show();
                break;
            case 2:
                //setphone number details to invisible
                findViewById(R.id.phn_pass).setVisibility(View.INVISIBLE);
                findViewById(R.id.country_code).setVisibility(View.INVISIBLE);
                editTextMobile.setVisibility(View.INVISIBLE);
                findViewById(R.id.buttonContinue).setVisibility(View.INVISIBLE);
                findViewById(R.id.imageView).setVisibility(View.INVISIBLE);
                findViewById(R.id.buttonFacebookLogin).setVisibility(View.INVISIBLE);

                //google login
                findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        signIn();

                    }
                });



                toast = Toast.makeText(getApplicationContext(), "Tap button phonesignup with " + names[2], Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                toast.show();

                break;
            default:
                break;
        }

        // Initialize Facebook phonesignup button
        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = findViewById(R.id.buttonFacebookLogin);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("Fb ON success", "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("Canceled", "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("Fb Error", "facebook:onError", error);
                // ...
            }
        });


        mcallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

            }
        };
        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast t=Toast.makeText(getApplicationContext(),"Connection failed check your internet connection",Toast.LENGTH_LONG);
                        t.setGravity(Gravity.BOTTOM,0,0);
                        t.show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
    }

   @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

        /*
        // Check if user is signed in (non-null) and update UI accordingly.
        mCurrentUser = mAuth.getCurrentUser();
        // updateUI(currentUser);
        if(mCurrentUser!=null){
         updateUI();

        }*/
        }
  // ...
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode, resultCode, data);

      // Pass the activity result back to the Facebook SDK
      mCallbackManager.onActivityResult(requestCode, resultCode, data);
      // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
      if (requestCode == RC_SIGN_IN) {
          GoogleSignInResult task =Auth.GoogleSignInApi.getSignInResultFromIntent(data);
          if(task.isSuccess()){
              GoogleSignInAccount account=task.getSignInAccount();
              firebaseAuthWithGoogle(account);
          }else {
              Toast t=Toast.makeText(getApplicationContext(),"Google sign in failed",Toast.LENGTH_LONG);
              t.setGravity(Gravity.BOTTOM,0,0);
              t.show();
          }
      }
  }
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("FB TOKEN", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Lgg success", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            // If sign in fails, display a message to the user.
                            Log.w("Faliure", "signInWithCredential:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                           // updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if(user!=null){
            startActivity(new Intent(getApplicationContext(),Home.class));
        }
        if(user==null){
            startActivity(new Intent(getApplicationContext(),Welocome.class));
        }

    }
    // Configure Google Sign In
   // GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
         //   .requestIdToken(getString(R.string.default_web_client_id))
         //   .requestEmail()
         //   .build();

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleSignInClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

}




//google auth
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("FirebaseAuthwithgoogle", "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("sign successfull", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Credentials failed", "signInWithCredential:failure", task.getException());
                            Snackbar.make(findViewById(R.id.parent), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

private int Country_code_mapper(String country_name){
        int i;
        if(country_name=="Uganda"){
            i=256;
            return  i;

        }else if(country_name=="Kenya"){
            i=254;
            return i;
        }else if(country_name=="Tanzania"){
            i=255;
            return  i;
        }else {
            i=259;
            return i;
        }
}

}

