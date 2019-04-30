package com.monkeyzi.mcloud.auth.config;

import com.monkeyzi.mcloud.auth.handler.McloudWebResponseExceptionTranslator;
import com.monkeyzi.mcloud.auth.service.McloudClientDetailService;
import com.monkeyzi.mcloud.auth.service.McloudUser;
import com.monkeyzi.mcloud.auth.service.McloudUserDetailService;
import com.monkeyzi.mcloud.common.core.constant.SecurityConstants;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: 高yg
 * @date: 2019/4/29 23:06
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description: 认证服务器配置
 */
@AllArgsConstructor
@EnableAuthorizationServer
@Configuration
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

     private  final DataSource dataSource;
     private  final McloudUserDetailService mcloudUserDetailService;
     /**
      * 认证管理器
      */
     private  final AuthenticationManager authenticationManagerBean;
     private  final RedisConnectionFactory redisConnectionFactory;

     /**
      * client
      * @param clients
      */
     @Override
     @SneakyThrows
     public void configure(ClientDetailsServiceConfigurer clients) {
          McloudClientDetailService clientDetailService=new McloudClientDetailService(dataSource);
          clientDetailService.setSelectClientDetailsSql(SecurityConstants.DEFAULT_SELECT_STATEMENT);
          clientDetailService.setFindClientDetailsSql(SecurityConstants.DEFAULT_FIND_STATEMENT);
          clients.withClientDetails(clientDetailService);
     }

     @Override
     @SneakyThrows
     public void configure(AuthorizationServerEndpointsConfigurer endpoints){
          endpoints
                  //允许的请求方式
                  .allowedTokenEndpointRequestMethods(HttpMethod.GET,HttpMethod.POST)
                  //token存储
                  .tokenStore(tokenStore())
                  //token增强
                  .tokenEnhancer(tokenEnhancer())
                  .userDetailsService(mcloudUserDetailService)
                  .authenticationManager(authenticationManagerBean)
                  .reuseRefreshTokens(false)
                  //异常转换
                  .exceptionTranslator(new McloudWebResponseExceptionTranslator());
     }

     @Override
     public void configure(AuthorizationServerSecurityConfigurer security) {
          security.allowFormAuthenticationForClients()
                  .checkTokenAccess("isAuthenticated()");
     }

     /**
      * token 存储
      * @return
      */
     @Bean
     public TokenStore tokenStore(){
          RedisTokenStore tokenStore=new RedisTokenStore(redisConnectionFactory);
          tokenStore.setPrefix(SecurityConstants.MCLOUD_PREFIX+SecurityConstants.OAUTH_PREFIX);
          tokenStore.setAuthenticationKeyGenerator(new DefaultAuthenticationKeyGenerator() {
               @Override
               public String extractKey(OAuth2Authentication authentication) {
                    return super.extractKey(authentication);
               }
          });
          return tokenStore;
     }

     /**
      * token增强
      * @return
      */
     @Bean
     public TokenEnhancer tokenEnhancer() {
          return (accessToken, authentication) -> {
               if (SecurityConstants.CLIENT_CREDENTIALS
                       .equals(authentication.getOAuth2Request().getGrantType())) {
                    return accessToken;
               }
               final Map<String, Object> additionalInfo = new HashMap<>(8);
               McloudUser mcloudUser = (McloudUser) authentication.getUserAuthentication().getPrincipal();
               additionalInfo.put(SecurityConstants.DETAILS_USER_ID,   mcloudUser.getId());
               additionalInfo.put(SecurityConstants.DETAILS_USERNAME,  mcloudUser.getUsername());
               additionalInfo.put(SecurityConstants.DETAILS_DEPT_ID,   mcloudUser.getDeptId());
               additionalInfo.put(SecurityConstants.DETAILS_TENANT_ID, mcloudUser.getTenantId());
               ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
               return accessToken;
          };
     }
}
