package org.yzh.web.database.influx;

import java.io.Serializable;

public class CodeInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer status;//状态位
    private Integer latitude;//纬度
    private Integer longitude;//经度
    private Integer altitude;//海拔

    private String time;
    private String tagCode;
    private String tagName;

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
}
