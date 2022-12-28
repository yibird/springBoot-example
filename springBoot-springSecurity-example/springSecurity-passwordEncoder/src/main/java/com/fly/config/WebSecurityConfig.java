package com.fly.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 该密码由 {bcrypt} 和 new BCryptPasswordEncoder(16).encode("zxp123123")
     * 拼接组成,用户注册成功将该密码存储至用户表的password
     */
    private static final String BCRYPT_PASSWORD="{bcrypt}$2a$16$wbPMW1db2DjZ.i2Eqdn9qOz5xSLpUsNfFDxnhflsjk0ILSoXftXre";

    /**
     * 该密码由 {pbkdf2} 和 new Argon2PasswordEncoder().encode("user")
     * 拼接组成,用户注册成功将该密码存储至用户表的password
     */
    private static final String ARGON2_PASSWORD="{argon2}$argon2id$v=19$m=4096,t=3,p=1$rYwSVJAGeSmZ9yJ6NSCxhg$S7oJ0k93Vui7L0A9nEdPp6nEH0As7LMFhWEU6jRJSEY";

    /**
     * 该密码由 {pbkdf2} 和 new Pbkdf2PasswordEncoder().encode("list")
     * 拼接组成,用户注册成功将该密码存储至用户表的password
     */
    private static final String PBKDF2_PASSWORD="{pbkdf2}69a43bf9fd383b248e469b9b02d91a566ec4dc698b46b984688185df5dbf6fa846e98864a713a899";

    /**
     * 该密码由 {scrypt} 和 new SCryptPasswordEncoder().encode("root")
     * 拼接组成,用户注册成功将该密码存储至用户表的password
     */
    private static final String SCRYPT_PASSWORD="{scrypt}$e0801$FwM8mv4myFie1qvydR2iy1xOErW2JByfXHRoxHgIxsHLLnjQ3E0PuajDW5i/UHnyrnTCC3Bz51pPSkGcpkBtjQ==$ru4I0wfKmC3OvtCMfHXrzFS8gGDkl1J7p8jDSh2MTX4=";
    /**
     * 该密码由 {sha256} 和 new StandardPasswordEncoder().encode("hash")
     * 拼接组成,用户注册成功将该密码存储至用户表的password
     */
    private static final String SHA256_PASSWORD="{sha256}fb805e4d606590d15be0fd6a35fdb59c6cb5262633720a765f352bb7c6094aab5f05f5ab85357247";

    /**
     * 自定义用户详情服务,基于内存方式提供用户信息
     * @return InMemoryUserDetailsManager
     */
    @Bean
    public UserDetailsService userDetails(){
        InMemoryUserDetailsManager manager=new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("admin").password(BCRYPT_PASSWORD).roles("ADMIN","USER").build());
        manager.createUser(User.withUsername("user").password(ARGON2_PASSWORD).roles("USER").build());
        manager.createUser(User.withUsername("list").password(PBKDF2_PASSWORD).roles("USER").build());
        manager.createUser(User.withUsername("root").password(SCRYPT_PASSWORD).roles("USER").build());
        manager.createUser(User.withUsername("hash").password(SHA256_PASSWORD).roles("USER").build());
        return manager;
    }

    /**
     * 向SpringIOC容器注入PasswordEncoder,使用BCryptPasswordEncoder
     * 作为默认的密码编码器。
     * @return PasswordEncoder实现
     */
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .and()
                .csrf()
                .disable();
    }
}
