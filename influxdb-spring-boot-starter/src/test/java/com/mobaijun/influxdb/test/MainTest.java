package com.mobaijun.influxdb.test;

import com.mobaijun.influxdb.config.InfluxdbConnection;
import com.mobaijun.influxdb.test.model.TestModel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * software：IntelliJ IDEA 2022.1
 * class name: MainTest
 * class description： 启动类
 *
 * @author MoBaiJun 2022/8/24 14:00
 */
@SpringBootTest
@SpringBootApplication
public class MainTest {
    public static void main(String[] args) {
        SpringApplication.run(MainTest.class, args);
    }

    @Resource
    private InfluxdbConnection influxdbConnection;

    @Test
    public void influxdbSet() {
        for (int i = 0; i < 10; i++) {
            TestModel testModel = new TestModel();
            testModel.setData("mobai2" + i);
            testModel.setDeviceFactory(i + "dsadasdasdas3");
            testModel.setId(12L + i);
            testModel.setTime(LocalDateTime.now());
            influxdbConnection.insert(testModel);
        }
    }
}
