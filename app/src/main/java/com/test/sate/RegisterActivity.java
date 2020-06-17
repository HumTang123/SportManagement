package com.test.sate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    EditText etUser,etPsw;
    Button btnRe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etUser = findViewById(R.id.etUser);
        etPsw = findViewById(R.id.etPsw);
        btnRe = findViewById(R.id.btnRe);
        btnRe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register() {
        String name = etUser.getText().toString().trim();
        String pasw = etPsw.getText().toString().trim();
        if (name.isEmpty()){
            showMsg("用户名不能为空");
        }else if (pasw.isEmpty()){
            showMsg("密码不能为空");
        }else {
            User user = new User();
            user.setName(name);
            user.setPsw(pasw);
            User user1 = DBUtils.getInstance().queryUser(name);
            if (user1.getName()==null){
             long flag = DBUtils.getInstance().inisertUser(user);
             if (flag == 1){
                 showMsg("注册成功");
                 finish();
             }
            }else {
                showMsg("该用户已存在");
            }

        }
    }

    private void showMsg(String msg){
        Toast.makeText(RegisterActivity.this,msg,Toast.LENGTH_SHORT).show();
    }
}
