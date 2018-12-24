package com.nexless.locstarcard.Utils;

import com.nexless.locstarcard.bean.CardInfo;
import com.nexless.locstarcard.bean.ConnectInfo;
import com.nexless.locstarcard.bean.ReadCardResult;
import com.nexless.locstarcard.bean.Result;
import com.speedata.r6lib.IMifareManager;

import java.util.HashMap;
import java.util.Map;
import static com.nexless.locstarcard.Utils.Constants.STATUS_DATA_LOSE;
import static com.nexless.locstarcard.Utils.Constants.STATUS_SUCC;
import static com.nexless.locstarcard.Utils.Constants.logI;

/**
 * @date: 2018/12/18
 * @author: su qinglin
 * @description:
 */
public class CardHelper {

    private static final String TAG = CardHelper.class.getSimpleName();
    private byte[] keyA = Constants.DEFAULT_KEY_A;
    private static final byte CARD_TYPE_AUTH = 0x00;
    private static final byte CARD_TYPE_CONSUMER = 0x0C;

    public ReadCardResult readConsumerCard(IMifareManager mifareManager, int sector, byte[] keyB) {

        ConnectInfo conn = CardConnection.connect(mifareManager, sector, keyB);
        ReadCardResult readCardResult = new ReadCardResult();
        readCardResult.setResultCode(conn.resultCode);
        if (conn.resultCode != STATUS_SUCC ) {
            return readCardResult;
        }

        CardInfo card = new CardInfo();
        card.setCardId(Utils.bytesToHexString(conn.cardId));
        readCardResult.setCardInfo(card);

        // 读取数据
        byte[] data = mifareManager.ReadBlock(sector * 4 + 1);
        for (int i = 0; i < 4; i++) {
            byte[] b = mifareManager.ReadBlock(sector * 4 + i);
            logI(TAG, "read data block:" + i + "=======================" + Utils.bytesToHexString(b));
        }
        mifareManager.ActiveCard(conn.cardId);
        if (data == null || data.length < 16) {
            readCardResult.setResultCode(STATUS_DATA_LOSE);
            return readCardResult;
        }
        String strData = Utils.bytesToHexString(data);
        logI(TAG, "read data:" + strData);


        // 解析数据
        try {
            card.setLoss(Integer.parseInt(strData.substring(2, 4)) != 0);
            card.setStartTime(Long.parseLong(strData.substring(4, 14)));
            card.setEndTime(Long.parseLong(strData.substring(14, 24)));
            card.setRoomNum(Utils.fillPosition(Integer.parseInt(strData.substring(24, 26)), 2, "0")
            + Utils.fillPosition(Integer.parseInt(strData.substring(26, 28)), 2, "0")
            + Utils.fillPosition(Integer.parseInt(strData.substring(28, 30)), 2, "0")
            + Utils.fillPosition(Integer.parseInt(strData.substring(30, 32)), 2, "0"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        logI(TAG, "read data card:" + card.toString());
        return readCardResult;
    }

    /**
     * 设置授权密码A
     * @param mifareManager
     * @param sector
     * @param keyB
     * @return
     */
    public Result setKeyA(IMifareManager mifareManager, int sector, byte[] keyB) {

        Result result = new Result();
        ConnectInfo conn = CardConnection.connect(mifareManager, sector, keyB);
        result.setResultCode(conn.resultCode);
        if (conn.resultCode != STATUS_SUCC) {
            return result;
        }
        // 读取扇区的第2块
        byte[] data = mifareManager.ReadBlock(sector * 4 + 2);
        mifareManager.ActiveCard(conn.cardId);
        if (data == null || data.length < 16) {
            result.setResultCode(STATUS_DATA_LOSE);
            return result;
        }

        System.arraycopy(data, 1, keyA, 0, 6);
        logI(TAG, "read data auth keyA:" + Utils.bytesToHexString(keyA));
        return result;
    }


    /**
     * 写卡
     * @param mifareManager
     * @param sector
     * @param keyB
     * @param start
     * @param end
     * @param buildNum
     * @param floorNum
     * @param houseNum
     * @param childHouseNum
     * @return
     */
    public Result writeConsumerCard(IMifareManager mifareManager, int sector, byte[] keyB, long start, long end, int buildNum, int floorNum, int houseNum, int childHouseNum) {
        Result result = new Result();
        ConnectInfo conn = CardConnection.connect(mifareManager, sector, keyB);
        result.setResultCode(conn.resultCode);
        if (result.getResultCode() != STATUS_SUCC) {
            return result;
        }

        Map<Integer, byte[]> dataMap = new HashMap<>();
        byte[] block0 = new byte[16];
        System.arraycopy(conn.cardId, 0, block0, 0, 4);
        dataMap.put(0, block0);
        byte[] block1 = new byte[16];
        block1[0] = CARD_TYPE_CONSUMER;
        block1[1] = 0x00;
        System.arraycopy(Utils.hexStringToBytes(Utils.fillPosition(start, 10, "0")), 0, block1, 2, 5);
        System.arraycopy(Utils.hexStringToBytes(Utils.fillPosition(end, 10, "0")), 0, block1, 7, 5);
        System.arraycopy(Utils.hexStringToBytes(Utils.fillPosition(buildNum, 2, "0")), 0, block1, 12, 1);
        System.arraycopy(Utils.hexStringToBytes(Utils.fillPosition(floorNum, 2, "0")), 0, block1, 13, 1);
        System.arraycopy(Utils.hexStringToBytes(Utils.fillPosition(houseNum, 2, "0")), 0, block1, 14, 1);
        System.arraycopy(Utils.hexStringToBytes(Utils.fillPosition(childHouseNum, 2, "0")), 0, block1, 15, 1);
        dataMap.put(1, block1);
        byte[] block2 = new byte[16];
        System.arraycopy(keyA, 0, block2, 12, 3);
        dataMap.put(2, block2);
        byte[] block3 = new byte[16];
        System.arraycopy(keyA, 0, block3, 0, 6);
        System.arraycopy(Constants.DEFAULT_CONTROL_WORD, 0, block3, 6, 4);
        System.arraycopy(Constants.DEFAULT_KEY_B, 0, block3, 10, 6);
        dataMap.put(3, block3);
        result.setResultCode(CardConnection.writeData(mifareManager, sector, conn.cardId, dataMap));
        return result;
    }

    public byte[] getKeyA() {
        return keyA;
    }

    public Result cancelCard(IMifareManager mifareManager, int sector, byte[] keyB) {
        Result result = new Result();
        ConnectInfo conn = CardConnection.connect(mifareManager, sector, keyB);
        result.setResultCode(conn.resultCode);
        if (result.getResultCode() != STATUS_SUCC) {
            return result;
        }

        Map<Integer, byte[]> dataMap = new HashMap<>();
        for (int i = 0; i < 3; i++) {
            byte[] data = new byte[16];
            for (int j = 0; j < data.length; j++) {
                data[j] = (byte) 0xFF;
            }
            dataMap.put(i, data);
        }
        result.setResultCode(CardConnection.writeData(mifareManager, sector, conn.cardId, dataMap));
        return result;
    }

    public Result writeAuthCard(IMifareManager mifareManager, int sector, byte[] keyB, byte[] consumerAuthKeyA, long start, long end) {

        Result result = new Result();
        ConnectInfo conn = CardConnection.connect(mifareManager, sector, keyB);
        result.setResultCode(conn.resultCode);
        if (result.getResultCode() != STATUS_SUCC) {
            return result;
        }
        Map<Integer, byte[]> dataMap = new HashMap<>();
        byte[] block0 = new byte[16];
        System.arraycopy(conn.cardId, 0, block0, 0, 4);
        dataMap.put(0, block0);
        byte[] block1 = new byte[16];
        block1[0] = CARD_TYPE_AUTH;
        System.arraycopy(Utils.hexStringToBytes(Utils.fillPosition(start, 10, "0")), 0, block1, 2, 5);
        System.arraycopy(Utils.hexStringToBytes(Utils.fillPosition(end, 10, "0")), 0, block1, 7, 5);
        dataMap.put(1, block1);
        byte[] block2 = new byte[16];
        System.arraycopy(consumerAuthKeyA, 0, block2, 1, 6);
        System.arraycopy(consumerAuthKeyA, 0, block2, 12, 3);
        dataMap.put(2, block2);
        byte[] block3 = new byte[16];
        System.arraycopy(Constants.DEFAULT_KEY_A, 0, block3, 0, 6);
        System.arraycopy(Constants.DEFAULT_CONTROL_WORD, 0, block3, 6, 4);
        System.arraycopy(Constants.DEFAULT_KEY_B, 0, block3, 10, 6);
        dataMap.put(3, block3);
        result.setResultCode(CardConnection.writeData(mifareManager, sector, conn.cardId, dataMap));
        return result;
    }


}
