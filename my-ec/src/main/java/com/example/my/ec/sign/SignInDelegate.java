package com.example.my.ec.sign;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.my.ec.R;
import com.example.my.ec.R2;
import com.example.my.ec.main.EcBottomDelegate;
import com.example.my_core.delegates.LatteDelegate;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.OnClick;

//登录页面逻辑
public class SignInDelegate extends LatteDelegate {

    @BindView(R2.id.edit_sign_in_email)
    TextInputEditText mEmail = null;
    @BindView(R2.id.edit_sign_in_password)
    TextInputEditText mPassword = null;

    //登录
    @OnClick(R2.id.btn_sign_in)
    void onClickSignIn() {
        if(checkForm()){
            start(new EcBottomDelegate());
        }
    }

    //微信登录
    @OnClick(R2.id.icon_sign_in_wechat)
    void onClickWeChat(){

    }

    //未注册 去注册
    @OnClick(R2.id.tv_link_sign_up)
    void onClickLink() {
        start(new SignUpDelegate());
    }
    private boolean checkForm() {
        final String email = mEmail.getText().toString();
        final String password = mPassword.getText().toString();

        boolean isPass = true;

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError("错误的邮箱格式");
            isPass = false;
        } else {
            mEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            mPassword.setError("请填写至少6位数密码");
            isPass = false;
        } else {
            mPassword.setError(null);
        }

        return isPass;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_in;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
