package com.nexless.locstarcard.bean;

/**
 * @date: 2018/12/18
 * @author: su qinglin
 * @description:
 */
public class ReadCardResult extends Result {

    private CardInfo cardInfo;

    /**
     * 获取卡片信息
     * @return 卡片信息对象
     */
    public CardInfo getCardInfo() {
        return cardInfo;
    }

    public void setCardInfo(CardInfo cardInfo) {
        this.cardInfo = cardInfo;
    }
}
