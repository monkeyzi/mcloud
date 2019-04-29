package com.monkeyzi.mcloud.auth.config;

import com.monkeyzi.mcloud.auth.service.McloudUserDetailService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

import javax.sql.DataSource;

/**
 * @author: 高yg
 * @date: 2019/4/29 23:06
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@AllArgsConstructor
@EnableAuthorizationServer
@Configuration
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

     private  final DataSource dataSource;
     private  final McloudUserDetailService mcloudUserDetailService;
     private  final AuthenticationManager authenticationManagerBean;


}
