package org.yzh.web.database.influx;

import java.io.Serializable;

public class CodeInfo implements Serializable {
    public static final String 上线 = "上线";
    public static final String 在线 = "在线";
    public static final String 下线 = "下线";

    private static final long serialVersionUID = 1L;

    private Integer status;//状态位
    private Integer latitude;//纬度
    private Integer longitude;//经度
    private Integer altitude;//海拔

    private String time;
    private String tagCode;//状态码
    private String tagName;//终端号

    public CodeInfo()
    {

    }

    public CodeInfo(String code,String name)
    {
        this.tagName = name;
        this.tagCode = code;
        this.status = 0;
        this.latitude = 0;
        this.longitude = 0;
        this.altitude = 0;
        this.status = 0;
    }

    public Integer getAltitude() {
        return altitude;
    }

    public Integer getLatitude() {
        return latitude;
    }

    public Integer getLongitude() {
        return longitude;
    }

    public Integer getStatus() {
        return status;
    }

    public String getTagCode() {
        return tagCode;
    }

    public String getTagName() {
        return tagName;
    }

    public String getTime() {
        return time;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setAltitude(Integer altitude) {
        this.altitude = altitude;
    }

    public void setLatitude(Integer latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Integer longitude) {
        this.longitude = longitude;
    }

    public void setTagCode(String tagCode) {
        this.tagCode = tagCode;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
