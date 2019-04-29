package com.monkeyzi.mcloud.auth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author: 高yg
 * @date: 2019/4/29 23:09
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
public interface McloudUserDetailService  extends UserDetailsService {
   UserDetails  loadUserBySocial(String code) throws UsernameNotFoundException;
}
