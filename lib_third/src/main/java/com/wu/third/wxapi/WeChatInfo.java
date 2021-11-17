package com.wu.third.wxapi;

/**
 * 作者: 吴奎庆
 * <p>
 * 时间: 2019/8/30
 * <p>
 * 简介:
 */

public class WeChatInfo {
    private int errCode;

    private String openid;
    private String accessToken;

    //普通用户性别，1为男性，2为女性
    private int sex;

    private String nickname;

    private String headimgurl;

    private String province;

    private String language;

    private String country;

    private String unionid;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getSex() {
        return "" + sex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    @Override
    public String toString() {
        return "WeChatInfo{" +
                "errCode='" + errCode + '\'' +
                ", openid='" + openid + '\'' +
                ", sex=" + sex +
                ", nickname='" + nickname + '\'' +
                ", accessToken='" + nickname + '\'' +
                ", headimgurl='" + headimgurl + '\'' +
                ", province='" + province + '\'' +
                ", language='" + language + '\'' +
                ", country='" + country + '\'' +
                ", unionid='" + unionid + '\'' +
                '}';
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
