package com.nexless.locstarcard;

import com.nexless.locstarcard.Utils.CardConnection;
import com.nexless.locstarcard.Utils.CardHelper;
import com.nexless.locstarcard.Utils.Constants;
import com.nexless.locstarcard.Utils.Utils;
import com.nexless.locstarcard.bean.GetCardIdResult;
import com.nexless.locstarcard.bean.ReadCardResult;
import com.nexless.locstarcard.bean.Result;
import com.speedata.r6lib.IMifareManager;
import com.speedata.r6lib.R6Manager;

import static com.speedata.r6lib.R6Manager.CardType.MIFARE;

/**
 * @date: 2018/12/18
 * @author: su qinglin
 * @description:
 */
public class CardManager {

    private static CardManager instance;
    private IMifareManager mifareManager;
    private int sectorIndex = 11;
    private CardHelper helper;

    private CardManager() {
        helper = new CardHelper();
    }

    public static CardManager getInstance() {

        if (instance == null) {
            instance = new CardManager();
        }
        return instance;
    }

    /**
     * 初始化IMifareManager
     */
    public void init() {
        mifareManager = R6Manager.getMifareInstance(MIFARE);
        mifareManager.InitDev();
    }

    public void setDebug(boolean debug) {
        Constants.DEBUG = debug;
    }

    /**
     * 设置扇区
     * @param sectorIndex 扇区编号0~15
     */
    public void setSectorIndex(int sectorIndex) {
        this.sectorIndex = sectorIndex;
    }

    /**
     * 读卡
     * @return 卡片信息
     */
    public ReadCardResult readCard() {

        return helper.readConsumerCard(mifareManager, sectorIndex, helper.getKeyA());
    }

    /**
     * 获取授权
     * @return
     */
    public Result getAuth() {

        return helper.setKeyA(mifareManager, sectorIndex, Constants.DEFAULT_KEY_B);
    }

    /**
     * 获取卡号
     * @return
     */
    public GetCardIdResult getCardId() {

        GetCardIdResult result = new GetCardIdResult();
        byte[] id = CardConnection.getCardId(mifareManager);
        if (id == null) {
            result.setResultCode(Result.STATUS_NO_CARD_FOUND);
        } else {
            mifareManager.ActiveCard(id);
            result.setResultCode(Result.STATUS_SUCC);
            result.setCardId(Utils.bytesToHexString(id));
        }
        return result;
    }

    /**
     * 写卡
     * @param start
     * @param end
     * @param buildNum
     * @param floorNum
     * @param houseNum
     * @param childHouseNum
     * @return
     */
    public Result writeCard(long start, long end, int buildNum, int floorNum, int houseNum, int childHouseNum) {

        return helper.writeConsumerCard(mifareManager, sectorIndex, Constants.DEFAULT_KEY_B, start, end, buildNum, floorNum, houseNum, childHouseNum);
    }

    public Result cancelCard() {
        return helper.cancelCard(mifareManager, sectorIndex, Constants.DEFAULT_KEY_B);
    }
}
