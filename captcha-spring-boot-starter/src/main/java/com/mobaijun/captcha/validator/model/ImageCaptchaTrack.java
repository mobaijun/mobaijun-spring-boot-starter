package com.mobaijun.captcha.validator.model;

import java.time.LocalDateTime;
import java.util.List;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: ImageCaptchaTrack
 * class description： 图片验证码滑动轨迹
 *
 * @author MoBaiJun 2022/10/27 10:28
 */
public class ImageCaptchaTrack {
    /**
     * 背景图片宽度.
     */
    private Integer bgImageWidth;

    /**
     * 背景图片高度.
     */
    private Integer bgImageHeight;

    /**
     * 滑块图片宽度.
     */
    private Integer sliderImageWidth;

    /**
     * 滑块图片高度.
     */
    private Integer sliderImageHeight;

    /**
     * 滑动开始时间.
     */
    private LocalDateTime startSlidingTime;

    /**
     * 滑动结束时间.
     */
    private LocalDateTime endSlidingTime;

    /**
     * 滑动的轨迹.
     */
    private List<Track> trackList;

    /**
     * 扩展数据，用户传输加密数据等.
     */
    private Object data;

    public Integer getBgImageWidth() {
        return bgImageWidth;
    }

    public void setBgImageWidth(Integer bgImageWidth) {
        this.bgImageWidth = bgImageWidth;
    }

    public Integer getBgImageHeight() {
        return bgImageHeight;
    }

    public void setBgImageHeight(Integer bgImageHeight) {
        this.bgImageHeight = bgImageHeight;
    }

    public Integer getSliderImageWidth() {
        return sliderImageWidth;
    }

    public void setSliderImageWidth(Integer sliderImageWidth) {
        this.sliderImageWidth = sliderImageWidth;
    }

    public Integer getSliderImageHeight() {
        return sliderImageHeight;
    }

    public void setSliderImageHeight(Integer sliderImageHeight) {
        this.sliderImageHeight = sliderImageHeight;
    }

    public LocalDateTime getStartSlidingTime() {
        return startSlidingTime;
    }

    public void setStartSlidingTime(LocalDateTime startSlidingTime) {
        this.startSlidingTime = startSlidingTime;
    }

    public LocalDateTime getEndSlidingTime() {
        return endSlidingTime;
    }

    public void setEndSlidingTime(LocalDateTime endSlidingTime) {
        this.endSlidingTime = endSlidingTime;
    }

    public List<Track> getTrackList() {
        return trackList;
    }

    public void setTrackList(List<Track> trackList) {
        this.trackList = trackList;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ImageCaptchaTrack{" +
                "bgImageWidth=" + bgImageWidth +
                ", bgImageHeight=" + bgImageHeight +
                ", sliderImageWidth=" + sliderImageWidth +
                ", sliderImageHeight=" + sliderImageHeight +
                ", startSlidingTime=" + startSlidingTime +
                ", endSlidingTime=" + endSlidingTime +
                ", trackList=" + trackList +
                ", data=" + data +
                '}';
    }
}
