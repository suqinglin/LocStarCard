package com.nexless.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.nexless.locstarcard.CardManager;
import com.nexless.locstarcard.Utils.Constants;
import com.nexless.locstarcard.bean.Result;
import com.nexless.locstarcarddemo.R;
import com.nexless.utils.DateUtil;

import java.util.Calendar;

/**
 * @date: 2018/12/19
 * @author: su qinglin
 * @description:
 */
public class WriteAuthCardActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvStartD;
    private TextView tvStartT;
    private TextView tvEndD;
    private TextView tvEndT;
    private int mSYear = 0;
    private int mSMonth = 0;
    private int mSDay = 0;
    private int mSHour = 0;
    private int mSMinute = 0;
    private int mEYear = 0;
    private int mEMonth = 0;
    private int mEDay = 0;
    private int mEHour = 0;
    private int mEMinute = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_auth_card);

        tvStartD = findViewById(R.id.writeAuthCard_tv_startD);
        tvStartT = findViewById(R.id.writeAuthCard_tv_startT);
        tvEndD = findViewById(R.id.writeAuthCard_tv_endD);
        tvEndT = findViewById(R.id.writeAuthCard_tv_endT);
        findViewById(R.id.writeAuthCard_btn_startD).setOnClickListener(this);
        findViewById(R.id.writeAuthCard_btn_startT).setOnClickListener(this);
        findViewById(R.id.writeAuthCard_btn_endD).setOnClickListener(this);
        findViewById(R.id.writeAuthCard_btn_endT).setOnClickListener(this);
        findViewById(R.id.writeAuthCard_btn_write).setOnClickListener(this);

        initData();
    }

    private void initData() {
        Calendar calendar = Calendar.getInstance();
        mSYear = calendar.get(Calendar.YEAR);
        mSMonth = calendar.get(Calendar.MONTH) + 1;
        mSDay = calendar.get(Calendar.DAY_OF_MONTH);
        mSHour = calendar.get(Calendar.HOUR_OF_DAY);
        mSMinute = calendar.get(Calendar.MINUTE);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        mEYear = calendar.get(Calendar.YEAR);
        mEMonth = calendar.get(Calendar.MONTH) + 1;
        mEDay = calendar.get(Calendar.DAY_OF_MONTH);
        mEHour = calendar.get(Calendar.HOUR_OF_DAY);
        mEMinute = calendar.get(Calendar.MINUTE);
        setStartD();
        setStartT();
        setEndtD();
        setEndT();
    }

    private void setStartD() {
        tvStartD.setText(mSYear + "-" + mSMonth + "-" + mSDay);
    }

    private void setStartT() {
        tvStartT.setText(mSHour + ":" + mSMinute + ":00");
    }

    private void setEndtD() {
        tvEndD.setText(mEYear + "-" + mEMonth + "-" + mEDay);
    }

    private void setEndT() {
        tvEndT.setText(mEHour + ":" + mEMinute + ":00");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.writeAuthCard_btn_startD:
                new DatePickerDialog(this, 0, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mSYear = year;
                        mSMonth = month + 1;
                        mSDay = dayOfMonth;
                        setStartD();
                    }
                }, mSYear, mSMonth - 1, mSDay)
                .show();
                break;
            case R.id.writeAuthCard_btn_startT:
                new TimePickerDialog(this, 0, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mSHour = hourOfDay;
                        mSMinute = minute;
                        setStartT();
                    }
                }, mSHour, mSMinute, true)
                 .show();
                break;
            case R.id.writeAuthCard_btn_endD:
                new DatePickerDialog(this, 0, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mEYear = year;
                        mEMonth = month + 1;
                        mEDay = dayOfMonth;
                        setEndtD();
                    }
                }, mEYear, mEMonth - 1, mEDay)
                 .show();
                break;
            case R.id.writeAuthCard_btn_endT:
                new TimePickerDialog(this, 0, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mEHour = hourOfDay;
                        mEMinute = minute;
                        setEndT();
                    }
                }, mEHour, mEMinute, true)
                 .show();
                break;
            case R.id.writeAuthCard_btn_write:
                new AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("请将授权卡靠近读卡区域！")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                writeCard();
                            }
                        })
                        .show();
                break;
        }
    }

    private void writeCard() {
        String startTime = tvStartD.getText().toString() + " " + tvStartT.getText().toString();
        String endTime = tvEndD.getText().toString() + " " + tvEndT.getText().toString();
        long start = DateUtil.stringToLong(startTime);
        long end = DateUtil.stringToLong(endTime);

//        Result result = CardManager.getInstance().writeAuthCard(start, end);
//        if (result.getResultCode() == Result.STATUS_SUCC) {
//            showToast("写卡成功");
//            onBackPressed();
//        } else {
//            showToast("写卡失败，错误代码：" + result.getResultCode());
//        }
    }
}
