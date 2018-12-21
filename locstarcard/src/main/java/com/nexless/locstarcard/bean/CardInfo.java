package com.nexless.locstarcard.bean;

/**
 * @date: 2018/12/18
 * @author: su qinglin
 * @description: 客人卡片信息
 */
public class CardInfo {

    private String cardId; // 卡片ID
    private String roomNum; // 房间号
    private long startTime; //开始时间
    private long endTime; // 结束时间
    private boolean isLoss; // 是否挂失

    public CardInfo() {
    }

    public CardInfo(String cardId, String roomNum, long startTime, long endTime, boolean isLoss) {
        this.cardId = cardId;
        this.roomNum = roomNum;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isLoss = isLoss;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public boolean isLoss() {
        return isLoss;
    }

    public void setLoss(boolean loss) {
        isLoss = loss;
    }

    @Override
    public String toString() {
        return "CardInfo{" +
                "cardId='" + cardId + '\'' +
                ", roomNum='" + roomNum + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", isLoss=" + isLoss +
                '}';
    }
}
