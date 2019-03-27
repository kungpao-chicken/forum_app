package com.example.wangzhixiang.librarytest.entity;

import java.io.Serializable;
import java.util.List;

public class CardItem implements Serializable {
    public String evaluationId;                // 帖子id
    public int classify;                   // 分类
    public List<CardPic> attachments; // 帖子图片列表
    public Avatar avatar;                   //用户头像
    public String content;                  // 内容信息
    public String creatTime;                // 时间
    public String userName;                 //用户名
    public int visitNum;                    //浏览量
    public int replyNum;                    //评论量
    public int starNum;                     //点赞量
    public List<EvaluationReply> evaluatereplys;//回复列表内容

    public String getEvaluationId() {
        return evaluationId;
    }

    public void setEvaluationId(String evaluationId) {
        this.evaluationId = evaluationId;
    }

    public int getClassify() {
        return classify;
    }

    public void setClassify(int classify) {
        this.classify = classify;
    }

    public List<CardPic> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<CardPic> attachments) {
        this.attachments = attachments;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getVisitNum() {
        return visitNum;
    }

    public void setVisitNum(int visitNum) {
        this.visitNum = visitNum;
    }

    public int getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(int replyNum) {
        this.replyNum = replyNum;
    }

    public int getStarNum() {
        return starNum;
    }

    public void setStarNum(int starNum) {
        this.starNum = starNum;
    }

    public List<EvaluationReply> getEvaluatereplys() {
        return evaluatereplys;
    }

    public void setEvaluatereplys(List<EvaluationReply> evaluatereplys) {
        this.evaluatereplys = evaluatereplys;
    }
}
