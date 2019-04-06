package com.zhangjie.security;

import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/***
 * 在使用时发现SpringSecurity中的Md5PasswordEncoder或者Md4都已经被淘汰了。
 * 最后我在StackOverflow上发现大家已经决定使用BCryptPassworderEncoder，经使用发现，这种方法就不需要向其中传入盐值。
 * 通过对源码的研究，发现BCryptPassworderEncoder是实现了PasswordEncoder接口，而且其中的salt盐值是由它自己控制的，极大的增加了安全性。
 * 因为一开始使用PasswordEncoder，StackOverflow上有人说使用MessageDigestPasswordEncoder,结果发现这种方法也被淘汰了。
 * 自我感觉SpringSecurity更新蛮快的。
 * @author zhangjie
 * @date 2019/4/6 11:05
 */
public class MyPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
        //PasswordEncoder encoder=new DelegatingPasswordEncoder();
        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        return passwordEncoder.encode(charSequence);
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        return passwordEncoder.matches(charSequence,s);
    }
}
