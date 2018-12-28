package com.nexless.locstarcard.bean;

/**
 * @date: 2018/12/19
 * @author: su qinglin
 * @description:
 */
public class GetCardIdResult extends Result {

    private String cardId;

    /**
     * 获取卡号
     * @return 卡号
     */
    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
}
