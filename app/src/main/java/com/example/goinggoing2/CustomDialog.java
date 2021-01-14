package com.example.goinggoing2;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;

import java.util.Objects;

public class CustomDialog extends Dialog {

    private EditText et1, et2, et3, et4;
    private RadioGroup rg;
    private RadioButton rb1;
    private Button saveBtn, cancleBtn;
    private Context mContext;

    private CustomDialogListener customDialogListener;

    public CustomDialog(Context mContext) {
        super(mContext);
        this.mContext = mContext;
    }

    //인터페이스 설정
    interface CustomDialogListener{
        void onPositiveClicked(String name, String age, String gender, String height, String weight);
        void onNegativeClicked();
    }

    //호출할 리스너 초기화
    public void setDialogListener(CustomDialogListener customDialogListener){
        this.customDialogListener = customDialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog);

        // 다이얼로그의 배경을 투명으로 만든다.
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        et1 = findViewById(R.id.dialog_et1);
        et2 = findViewById(R.id.dialog_et2);
        et3 = findViewById(R.id.dialog_et3);
        et4 = findViewById(R.id.dialog_et4);
        rg = findViewById(R.id.dialog_rg);
        rb1 = findViewById(R.id.dialog_rb1);
        saveBtn = findViewById(R.id.dialog_btn1);
        cancleBtn = findViewById(R.id.dialog_btn2);

        rb1.setChecked(true);

        // 버튼 리스너 설정
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //각각의 변수에 EidtText에서 가져온 값을 저장
                String name = et1.getText().toString();
                String age = et2.getText().toString();
                int id = rg.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton)findViewById(id);
                String gender = rb.getText().toString();
                String height = et3.getText().toString();
                String weight = et4.getText().toString();

                //인터페이스의 함수를 호출하여 변수에 저장된 값들을 Activity로 전달
                customDialogListener.onPositiveClicked(name,age,gender,height,weight);
                dismiss();
            }
        });

        cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
    }

}
