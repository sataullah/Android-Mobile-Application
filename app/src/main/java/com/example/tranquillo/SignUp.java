package com.example.tranquillo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    private Button btn;
    EditText name, email, pswd, confirmpswd;
    String userid;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        name = findViewById(R.id.su_username);
        email = findViewById(R.id.su_email);
        pswd = findViewById(R.id.su_password);
        confirmpswd = findViewById(R.id.su_confirmpswd);
    }

    private boolean validateuname()
    {
        String flag = name.getText().toString();
        if (flag.isEmpty())
        {
            name.setError("Please Enter your Name! This field cannot be empty!");
            return false;
        }
        else
        {
            name.setError(null);
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
        String confirmpswdinput = confirmpswd.getText().toString();
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
        if(!flag.equals(confirmpswdinput))
        {
            confirmpswd.setError("Password does not match! Please Try Again!");
            return false;
        }
        else
        {
            confirmpswd.setError(null);
            return true;
        }
    }

    public void backbtn_s(View view)
    {
        Intent back_btn = new Intent(SignUp.this,WelcomePg.class);
        startActivity(back_btn);
        finish();
    }

    public void onclick_slogin(View view)
    {
        Intent slogin_btn = new Intent(SignUp.this,Login.class);
        startActivity(slogin_btn);
        finish();
    }

    public void onclick_registeruser(View view)
    {
        if (!validateuname() | !validateemail() | !validatepswd())
        {
            return;
        }

        //Variable Fields
        String mail = email.getText().toString().trim();
        String username = name.getText().toString().trim();
        String password = pswd.getText().toString().trim();


        //Firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        //Create object of dataholder
        final dataholder db_dataholder = new dataholder(username,mail,password);

        //Create object of Firebase Database
        FirebaseDatabase db_firebase = FirebaseDatabase.getInstance();

        //Create Object of Data reference
        final DatabaseReference db_ref = db_firebase.getReference("User");

        mAuth.createUserWithEmailAndPassword(mail, password)
                .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            userid = mAuth.getCurrentUser().getUid();
                            db_ref.child(userid).setValue(db_dataholder);

                            email.setText("");
                            name.setText("");
                            pswd.setText("");
                            confirmpswd.setText("");
                           Toast toast = Toast.makeText(SignUp.this, "User Sign Up is Successful", Toast.LENGTH_LONG);
                            View view = toast.getView();
                            TextView text = view.findViewById(android.R.id.message);
                            view.setBackground(getDrawable(R.drawable.rounded_toast));
                            text.setTextColor(Color.parseColor("#522749"));
                            text.setTextSize(21);
                            text.setTypeface(Typeface.create("serif",Typeface.BOLD));
                            toast.show();
                            startActivity(new Intent(getApplicationContext(),Login.class));

                        }
                        else {
                            Toast toast = Toast.makeText(SignUp.this, "Error - Unsuccessful Sign Up! ", Toast.LENGTH_SHORT);
                            View view = toast.getView();
                            TextView text = view.findViewById(android.R.id.message);
                            view.setBackground(getDrawable(R.drawable.rounded_toast));
                            text.setTextColor(Color.parseColor("#522749"));
                            text.setTextSize(21);
                            text.setTypeface(Typeface.create("serif",Typeface.BOLD));
                            toast.show();
                        }
                    }
                });
    }
}