package com.example.tranquillo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText email, pswd, uname;
    private FirebaseAuth mAuth;
    private Button btn;
    Intent intent;
    TextView forgotTextLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        uname = findViewById(R.id.lo_username);
        email = findViewById(R.id.lo_email);
        pswd = findViewById(R.id.lo_password);

        forgotTextLink = findViewById(R.id.forgotPassword);

        //Firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        forgotTextLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText resetmail = new EditText(view.getContext());
                AlertDialog.Builder passwordReset = new AlertDialog.Builder(view.getContext());
                passwordReset.setTitle("Reset Password!");
                passwordReset.setMessage("Enter your Email address to receive the password link.");
                passwordReset.setView(resetmail);

                passwordReset.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Extract user's email and send the link
                        String mail = resetmail.getText().toString();
                        mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                              Toast toast =  Toast.makeText(Login.this, "Reset Link sent to your Email!", Toast.LENGTH_SHORT);
                                View view = toast.getView();
                                TextView text = view.findViewById(android.R.id.message);
                                view.setBackground(getDrawable(R.drawable.rounded_toast));
                                text.setTextColor(Color.parseColor("#522749"));
                                text.setTextSize(21);
                                text.setTypeface(Typeface.create("serif",Typeface.BOLD));
                                toast.show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                               Toast toast = Toast.makeText(Login.this, "Error! Link is not sent"+ e.getMessage(), Toast.LENGTH_LONG);
                                View view = toast.getView();
                                TextView text = view.findViewById(android.R.id.message);
                                view.setBackground(getDrawable(R.drawable.rounded_toast));
                                text.setTextColor(Color.parseColor("#522749"));
                                text.setTextSize(16);
                                text.setGravity(Gravity.CENTER);
                                text.setTypeface(Typeface.create("serif",Typeface.BOLD));
                                toast.show();
                            }
                        });
                    }
                });
                passwordReset.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Close the dialog box
                    }
                });

                passwordReset.create().show();
            }
        });
    }
    public void backbtn(View view)
    {
        Intent back_btn = new Intent(Login.this,WelcomePg.class);
        startActivity(back_btn);
        finish();
    }

    public void onclick_signup(View view)
    {
        Intent lsignup_btn = new Intent(Login.this,SignUp.class);
        startActivity(lsignup_btn);
        finish();
    }

    private boolean validateuname()
    {
        String flag = uname.getText().toString();
        if(flag.isEmpty())
        {
            uname.setError("Please Enter Username!");
            return false;
        }
        else
        {
            uname.setError(null);
            return true;
        }
    }

    private boolean validateemail()
    {
        String flag = email.getText().toString();
        String emailformat = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        if (flag.isEmpty())
        {
            email.setError("Please Enter Your Email Address");
            return false;
        }
        else if(!flag.matches(emailformat))
        {
            email.setError("Incorrect Email Format!");
            return false;
        }
        else
        {
            email.setError(null);
            return true;
        }
    }

    private boolean validatepswd()
    {
        String flag = pswd.getText().toString();
        if(flag.isEmpty())
        {
            pswd.setError("Field cannot be empty");
            return false;
        }
        if(flag.length()<8)
        {
            pswd.setError("Password must be atleast 8 characters");
            return false;
        }
        else
        {
            pswd.setError(null);
            return true;
        }
    }

    public void onclick_nextpage(View view)
    {

        if(!validateuname() | !validateemail() | !validatepswd())
        {
            return;
        }
        String name = uname.getText().toString();
        String mail = email.getText().toString();
        String password = pswd.getText().toString();
        /*SharedPreferences prefs = this.getSharedPreferences("com.example.tranquillo", Context.MODE_PRIVATE);
        prefs.edit().putString("NAME",name).apply();*/

        //Authenticate User
        mAuth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    Toast toast = Toast.makeText(Login.this, "Successfully Logged In!", Toast.LENGTH_LONG);
                    View view = toast.getView();
                    TextView text = view.findViewById(android.R.id.message);
                    view.setBackground(getDrawable(R.drawable.rounded_toast));
                    text.setTextColor(Color.parseColor("#522749"));
                    text.setTextSize(21);
                    text.setTypeface(Typeface.create("serif",Typeface.BOLD));
                    toast.show();
                    startActivity(new Intent(getApplicationContext(),navigation_drawer.class));
                }
                else
                {
                   Toast toast = Toast.makeText(Login.this, "Unsuccessful Login! Please Check your credentials", Toast.LENGTH_LONG);
                    View view = toast.getView();
                    TextView text = view.findViewById(android.R.id.message);
                    view.setBackground(getDrawable(R.drawable.rounded_toast));
                    text.setTextColor(Color.parseColor("#522749"));
                    text.setGravity(Gravity.CENTER);
                    text.setTextSize(15);
                    text.setTypeface(Typeface.create("serif",Typeface.BOLD));
                    toast.show();
                }
            }
        });



    }
}