/*
 * Copyright (C) 2022 www.mobaijun.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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