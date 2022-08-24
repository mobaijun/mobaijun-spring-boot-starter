package com.mobaijun.influxdb.test.model;

import com.mobaijun.influxdb.annotation.Count;
import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

import java.time.LocalDateTime;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: TestModel
 * class description：
 *
 * @author MoBaiJun 2022/8/24 14:04
 */
@Measurement(name = "TEST_BAI")
public class TestModel {

    /**
     * 默认时间
     */
    @Column(name = "time")
    private LocalDateTime time;

    /**
     * 设备对象{json}数据类型
     */
    @Count(value = "value")
    @Column(name = "data")
    private String data;

    /**
     * id
     */
    @Column(name = "id", tag = true)
    public Long id;

    /**
     * 设备厂商
     */
    @Column(name = "device_factory", tag = true)
    private String deviceFactory;

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceFactory() {
        return deviceFactory;
    }

    public void setDeviceFactory(String deviceFactory) {
        this.deviceFactory = deviceFactory;
    }

    @Override
    public String toString() {
        return "TestModel{" +
                "time=" + time +
                ", data='" + data + '\'' +
                ", id=" + id +
                ", deviceFactory='" + deviceFactory + '\'' +
                '}';
    }
}
