package com.example.project162.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.project162.R;
import com.example.project162.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class SignupActivity extends BaseActivity {
    ActivitySignupBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setVariable();
    }

    private void setVariable() {
        binding.signupBtn.setOnClickListener(v -> {
            String email = binding.userEdt.getText().toString();
            String password = binding.passEdt.getText().toString();

            if (password.length() < 6) {
                Toast.makeText(SignupActivity.this, "Mật khẩu của bạn phải có 6 ký tự", Toast.LENGTH_SHORT).show();
                return;
            }
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignupActivity.this, task -> {
                if (task.isSuccessful()) {
                    Log.i(TAG, "onComplete: ");
                    startActivity(new Intent(SignupActivity.this, MainActivity.class));
                } else {
                    Log.i(TAG, "failure: " + task.getException());
                    Toast.makeText(SignupActivity.this, "Đăng kí không thành công", Toast.LENGTH_SHORT).show();
                }
            });
        });
        binding.btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });
    }

}