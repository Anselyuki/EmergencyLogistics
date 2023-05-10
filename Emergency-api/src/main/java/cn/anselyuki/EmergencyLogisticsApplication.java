package cn.anselyuki;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author AnselYuki
 * @date 2022/10/15 19:05
 **/
@SpringBootApplication
@EnableTransactionManagement
@ConfigurationPropertiesScan("cn.anselyuki")
@MapperScan("cn.anselyuki.*.mapper")
public class EmergencyLogisticsApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmergencyLogisticsApplication.class, args);
    }
}
