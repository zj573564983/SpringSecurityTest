package com.zhangjie.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;

/***
 * @author zhangjie
 * @date 2019/4/6 9:40
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserService myUserService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //在SpringSecurity框架中实现直接登录，直接在内存中实现有一个角色
        //需要在password前加上{noop}
//        auth.inMemoryAuthentication().withUser("admin").password("{noop}123456").roles("ADMIN");
//        auth.inMemoryAuthentication().withUser("zhangsan").password("{noop}zhangsan").roles("ADMIN");
//        auth.inMemoryAuthentication().withUser("demo").password("{noop}demo").roles("USER");

        //passwordEncoder是为了实现自己的密码加密验证
        auth.userDetailsService(myUserService).passwordEncoder(new MyPasswordEncoder());

        //可以使用jdbc验证
        //auth.jdbcAuthentication().usersByUsernameQuery("").authoritiesByUsernameQuery("").passwordEncoder(new MyPasswordEncoder());


    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and()
                .logout().permitAll()
                .and()
                .formLogin();
        http.csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**","/css/**","images/**");
    }
}
