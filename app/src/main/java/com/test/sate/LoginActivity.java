package com.test.sate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText etUser,etPsw;
    Button btnLogin;
    TextView tvRe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUser = findViewById(R.id.etUser);
        etPsw = findViewById(R.id.etPsw);
        btnLogin = findViewById(R.id.btnLogin);
        tvRe = findViewById(R.id.tvRe);
        tvRe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        String name = etUser.getText().toString().trim();
        String pasw = etPsw.getText().toString().trim();
        if (name.isEmpty()){
            showMsg("用户名不能为空");
        }else if (pasw.isEmpty()){
            showMsg("密码不能为空");
        }else {
            User user = DBUtils.getInstance().queryUser(name);
            if (user.getName()==null){
                showMsg("该用户还未注册");
            }else {
                boolean flag = DBUtils.getInstance().ifLogin(name,pasw);
                if (flag){
                    showMsg("登录成功");
                    SpUtil.saveSingleStr("USERNAME","username",name);
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    finish();
                }else {
                    showMsg("用户或密码不正确");
                }
            }
        }
    }

    private void showMsg(String msg){
        Toast.makeText(LoginActivity.this,msg,Toast.LENGTH_SHORT).show();
    }
}
