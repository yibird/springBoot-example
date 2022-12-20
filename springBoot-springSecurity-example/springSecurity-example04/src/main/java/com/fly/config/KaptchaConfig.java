package com.fly.config;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * Kaptcha配置类,用于生成验证码
 */
@Configuration
public class KaptchaConfig {
    @Bean
    public Producer producer() {
        Properties properties = new Properties();
        // 验证码图片宽度
        properties.setProperty("kaptcha.image.width", "150");
        // 验证码图片高度
        properties.setProperty("kaptcha.image.height", "50");
        // 验证码字符内容
        properties.setProperty("kaptcha.textproducer.char.string", "0123456789");
        // 验证码字符长度
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
