package cn.loverot.basic.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: 铭飞开源团队--huise
 * @Date: 2019/10/17 1:24
 */
@Configuration
@EnableConfigurationProperties(BasicProperties.class)
@ConfigurationProperties(prefix = BasicProperties.BASIC_PREFIX)
public class BasicProperties {
    public static final String BASIC_PREFIX="hs";
    private UploadConfig upload;

    public UploadConfig getUpload() {
        return upload;
    }

    public void setUpload(UploadConfig upload) {
        this.upload = upload;
    }

}
