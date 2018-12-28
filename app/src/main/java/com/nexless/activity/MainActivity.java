package com.nexless.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.nexless.locstarcard.CardManager;
import com.nexless.locstarcard.Utils.Constants;
import com.nexless.locstarcard.bean.GetCardIdResult;
import com.nexless.locstarcard.bean.Result;
import com.nexless.locstarcarddemo.R;

import static com.nexless.utils.DataUtil.getSectorArray;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvSector;
    private TextView tvCardId;
    private int sectorIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvSector = findViewById(R.id.main_tv_sector);
        tvCardId = findViewById(R.id.main_tv_gardId);
        findViewById(R.id.main_rl_getCardId).setOnClickListener(this);
        findViewById(R.id.main_rl_getAuth).setOnClickListener(this);
        findViewById(R.id.main_rl_setSector).setOnClickListener(this);
        findViewById(R.id.main_rl_read_card).setOnClickListener(this);
        findViewById(R.id.main_rl_write_card).setOnClickListener(this);
        findViewById(R.id.main_rl_cancel_card).setOnClickListener(this);
        findViewById(R.id.main_rl_setAuthCard).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_rl_setSector:
                Log.i("TAG", "main_rl_setSector");
                new AlertDialog.Builder(this)
                        .setTitle("请选择扇区")
                        .setSingleChoiceItems(getSectorArray(), Integer.valueOf(tvSector.getText().toString()), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sectorIndex = which;
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (sectorIndex > 0) {
                                    tvSector.setText(String.valueOf(sectorIndex));
                                    CardManager.getInstance().setSectorIndex(sectorIndex);
                                }
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
            case R.id.main_rl_read_card:
                new AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("请将客人卡靠近读卡区域！")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(MainActivity.this, ReadCardActivity.class));
                            }
                        })
                        .show();
                break;
            case R.id.main_rl_write_card:
                startActivity(new Intent(MainActivity.this, WriteCardActivity.class));
                break;
            case R.id.main_rl_getCardId:
                new AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("请将客人卡靠近读卡区域！")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                GetCardIdResult result = CardManager.getInstance().getCardId();
                                if (result.getResultCode() != Result.STATUS_SUCC || result.getCardId() == null) {
                                    showToast("获取卡号失败，错误代码：" + result.getResultCode());
                                    return;
                                }
                                tvCardId.setText(result.getCardId());
                            }
                        })
                        .show();
                break;
            case R.id.main_rl_getAuth:
                new AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("请将授权卡靠近读卡区域！")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Result result = CardManager.getInstance().getAuth();
                                if (result.getResultCode() != Result.STATUS_SUCC ) {
                                    showToast("获取授权失败，错误代码：" + result.getResultCode());
                                } else {
                                    showToast("获取授权成功");
                                }
                            }
                        })
                        .show();
                break;
            case R.id.main_rl_cancel_card:
                new AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("请将客人卡靠近读卡区域！")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Result result = CardManager.getInstance().cancelCard();
                                if (result.getResultCode() != Result.STATUS_SUCC ) {
                                    showToast("注销失败，错误代码：" + result.getResultCode());
                                } else {
                                    showToast("注销成功");
                                }
                            }
                        })
                        .show();
                break;
            case R.id.main_rl_setAuthCard:
                startActivity(new Intent(MainActivity.this, WriteAuthCardActivity.class));
                break;
        }
    }


}
