package com.mobaijun.captcha.validator.model;

import com.mobaijun.captcha.enums.TrackType;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: Track
 * class description：验证码轨迹
 *
 * @author MoBaiJun 2022/10/27 10:29
 */
public class Track {

    /**
     * x.
     */
    private Integer x;

    /**
     * y.
     */
    private Integer y;

    /**
     * 时间.
     */
    private Integer t;

    /**
     * 类型.
     */
    private String type = TrackType.MOVE.name();

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getT() {
        return t;
    }

    public void setT(Integer t) {
        this.t = t;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Track{" +
                "x=" + x +
                ", y=" + y +
                ", t=" + t +
                ", type='" + type + '\'' +
                '}';
    }
}
