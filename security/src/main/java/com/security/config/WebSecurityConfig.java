package com.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import javax.sql.DataSource;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig<main> extends WebSecurityConfigurerAdapter {

    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private DataSource dataSource;

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();//BCrypt强哈希算法来加密密码
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //关闭csrf防护 跨站请求防护
        http.csrf().disable()
                //表单登录
                .formLogin()
                //登录页面
                .loginPage("/login.html")
                //登录访问路径，与页面表单提交路径一致
                .loginProcessingUrl("/login")
                //登录成功后访问路径
                .defaultSuccessUrl("/index.html").permitAll()
                //登录失败操作
                .failureUrl("/loginerror.html")
                .and()
                //认证配置
                .authorizeRequests()
                .antMatchers("/login.html", "/login").permitAll()
                //配置静态页面可以访问
                .antMatchers("/js/**", "/css/**", "/images/**", "/favicon.ico").permitAll()
                //任何请求
                .anyRequest()
                //都需要身份验证
                .authenticated();
        //配置无权限访问页面
        http.exceptionHandling().accessDeniedPage("/uanuth.html");

        //配置退出
        http.logout()
                //退出路径
                .logoutUrl("/logout")
                //退出后跳转页面
                .logoutSuccessUrl("/login.html");

    }

}
