package com.example.todo;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;


public class RegisterActivity extends AppCompatActivity {
    private Toolbar toolbar;

    private EditText RegPhone, RegPwd, RegName;
    private Button RegBtn;
    private TextView RegnQn;
    private FirebaseAuth mAuth;

    private ProgressDialog loader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        changeStatusBarColor();

//        toolbar = findViewById(R.id.RegistrationToolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Registration");

        mAuth = FirebaseAuth.getInstance();
        loader = new ProgressDialog(this);


        RegPhone = findViewById(R.id.RegistrationPhone);
        RegPwd = findViewById(R.id.RegistrationPassword);
        RegName = findViewById(R.id.RegistrationName);
        RegBtn = findViewById(R.id.RegistrationButton);
        RegnQn = findViewById(R.id.RegistrationPageQuestion);
        RegnQn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        RegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = RegPhone.getText().toString().trim();
                String password = RegPwd.getText().toString().trim();
                String name = RegName.getText().toString().trim();

                if (TextUtils.isEmpty(phone)) {
                    RegPhone.setError("phone is required");
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    RegName.setError("name is required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    RegPwd.setError("Password required");
                    return;
                } else {
                    loader.setMessage("Registration in progress");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();
                    ValidatephoneNumber(name, phone, password);

                }
                
            }
        });
    }

    private void ValidatephoneNumber(String name, String phone, String password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.child("users").child(phone).exists())) {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("phone", phone);
                    userdataMap.put("password", password);
                    userdataMap.put("name", name);

                    RootRef.child("users").child(phone).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "Congratulations,your account has been created", Toast.LENGTH_SHORT).show();
                                        loader.dismiss();
                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "Network Error:Please try again after some time...", Toast.LENGTH_SHORT).show();
                                        loader.dismiss();
                                    }
                                }
                            });

                } else {
                    Toast.makeText(RegisterActivity.this, "This" + phone + "already exists", Toast.LENGTH_SHORT).show();
                    loader.dismiss();
                    Toast.makeText(RegisterActivity.this, "Please try again using another phone number", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }



        });
    }
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }
    }

    public void onLoginClick(View view) {
        startActivity(new Intent(this, LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);

    }
}
