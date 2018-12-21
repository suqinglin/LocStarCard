package com.nexless.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nexless.locstarcard.CardManager;
import com.nexless.locstarcard.Utils.Constants;
import com.nexless.locstarcard.bean.CardInfo;
import com.nexless.locstarcard.bean.ReadCardResult;
import com.nexless.locstarcarddemo.R;
import com.nexless.utils.DateUtil;

/**
 * @date: 2018/12/18
 * @author: su qinglin
 * @description:
 */
public class ReadCardActivity extends Activity {

    private static final String TAG = ReadCardActivity.class.getSimpleName();
    private TextView tvTip;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_card);

        tvTip = findViewById(R.id.readCard_tv_tip);
        tvResult = findViewById(R.id.readCard_tv_result);

        ReadCardResult result = CardManager.getInstance().readCard();
        if (result.getResultCode() == Constants.STATUS_SUCC && result.getCardInfo() != null) {
            CardInfo cardInfo = result.getCardInfo();
            tvTip.append("读卡成功");
            tvResult.append("\n\n卡号：" + cardInfo.getCardId());
            tvResult.append("\n\n开始时间：" + DateUtil.longToString(cardInfo.getStartTime()));
            tvResult.append("\n\n结束时间：" + DateUtil.longToString(cardInfo.getEndTime()));
            tvResult.append("\n\n房间号：" + cardInfo.getRoomNum());
            tvResult.append("\n\n是否挂失：" + (cardInfo.isLoss() ? "是" : "否"));
            tvTip.setTextColor(Color.GREEN);
        } else {
            tvTip.append("错误代码：" + result.getResultCode());
            tvTip.setTextColor(Color.RED);
        }
    }
}
