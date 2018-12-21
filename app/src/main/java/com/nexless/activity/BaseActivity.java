package com.nexless.activity;

import android.app.Activity;
import android.widget.Toast;

/**
 * @date: 2018/12/20
 * @author: su qinglin
 * @description:
 */
public class BaseActivity extends Activity {

    void showToast(String msg) {

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
