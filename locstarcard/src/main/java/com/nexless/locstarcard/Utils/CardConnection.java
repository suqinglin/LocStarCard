package com.nexless.locstarcard.Utils;

import com.nexless.locstarcard.bean.ConnectInfo;
import com.nexless.locstarcard.bean.Result;
import com.speedata.r6lib.IMifareManager;

import java.util.Map;

import static com.android.hflibs.Mifare_native.AUTH_TYPEB;
import static com.nexless.locstarcard.Utils.Constants.logI;

/**
 * @date: 2018/12/20
 * @author: su qinglin
 * @description:
 */
public class CardConnection {

    private static final String TAG = CardConnection.class.getSimpleName();

    public static int writeData(IMifareManager mifareManager, int sector, byte[] id, Map<Integer, byte[]> dataMap) {

        // xor校驗
        byte[] xorData = new byte[47];
        for (int i = 0; i < 3; i++) {
            int length = i == 2 ? 15 : 16;
            byte[] data = dataMap.get(i);
            if (data == null) {
                for (int j = 0; j < length; j++) {
                    xorData[i * 16 + j] = 0x00;
                }
            } else {
                System.arraycopy(data, 0, xorData, i * 16, length);
            }
        }
        byte[] block2 = new byte[16];
        System.arraycopy(xorData, 32, block2, 0, 15);
        block2[15] = Utils.getXor(xorData);
        dataMap.put(2, block2);
        // 循環介入數據
        for (int i : dataMap.keySet()) {
            byte[] data = dataMap.get(i);
            if (data != null) {
                mifareManager.WriteBlock(sector * 4 + i, data);
                logI(TAG, "write data block" + i + " :" + Utils.bytesToHexString(data));
            }
        }
        // 激活卡片
        mifareManager.ActiveCard(id);
        return Constants.STATUS_SUCC;
    }

    public static ConnectInfo connect(IMifareManager mifareManager, int sector, byte[] keyB) {
        logI(TAG, "sector:" + sector + "    keyB:" + Utils.bytesToHexString(keyB));
        ConnectInfo connectInfo = new ConnectInfo();
        connectInfo.resultCode = Constants.STATUS_SUCC;
        // 搜索卡片
        byte[] ID = getCardId(mifareManager);
        if (ID == null) {
            logI(TAG, "No card found");
            connectInfo.resultCode = Constants.STATUS_NO_CARD_FOUND;
            return connectInfo;
        }
        connectInfo.cardId = ID;
        // 授权
        if (mifareManager.AuthenticationCardByKey(AUTH_TYPEB, ID, sector * 4, keyB) != 0) {
            logI(TAG, "Auth failure, keyB:" + Utils.bytesToHexString(keyB));
            connectInfo.resultCode = Constants.STATUS_AUTH_FAIL;
            return connectInfo;
        }
        return connectInfo;
    }

    /**
     * 获取卡号
     *
     * @param mifareManager
     * @return
     */
    public static byte[] getCardId(IMifareManager mifareManager) {
        if (mifareManager == null) {
            throw new NullPointerException("CardManager is not init");
        }
        return mifareManager.SearchCard();
    }

}
