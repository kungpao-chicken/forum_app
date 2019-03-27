package com.example.wangzhixiang.librarytest.entity;

import java.io.Serializable;

public class EvaluationReply implements Serializable {
    private String replyId;                        //评论id
    private String replyContent;                   //评论内容
    private String attachmentCardId;               //回复的帖子id
    private String attachmentCEvaluationId;        //回复的评论id
    private String replyUserName;                  //回复人姓名
    private Avatar replyUserAvatar;                //回复人头像
    private String replyTime;                      //回复时间
    private int classify;                     //是否匿名
    private int starNum;                           //点赞量

    public int getStarNum() {
        return starNum;
    }

    public void setStarNum(int starNum) {
        this.starNum = starNum;
    }

    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public String getAttachmentCardId() {
        return attachmentCardId;
    }

    public void setAttachmentCardId(String attachmentCardId) {
        this.attachmentCardId = attachmentCardId;
    }

    public String getAttachmentCEvaluationId() {
        return attachmentCEvaluationId;
    }

    public void setAttachmentCEvaluationId(String attachmentCEvaluationId) {
        this.attachmentCEvaluationId = attachmentCEvaluationId;
    }

    public String getReplyUserName() {
        return replyUserName;
    }

    public void setReplyUserName(String replyUserName) {
        this.replyUserName = replyUserName;
    }

    public Avatar getReplyUserAvatar() {
        return replyUserAvatar;
    }

    public void setReplyUserAvatar(Avatar replyUserAvatar) {
        this.replyUserAvatar = replyUserAvatar;
    }

    public String getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(String replyTime) {
        this.replyTime = replyTime;
    }

    public int getClassify() {
        return classify;
    }

    public void setClassify(int classify) {
        this.classify = classify;
    }
}
