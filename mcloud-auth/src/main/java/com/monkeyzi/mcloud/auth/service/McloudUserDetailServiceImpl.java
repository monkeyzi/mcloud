package com.monkeyzi.mcloud.auth.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author: 高yg
 * @date: 2019/4/29 23:12
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Service
@Slf4j
@AllArgsConstructor
public class McloudUserDetailServiceImpl  implements McloudUserDetailService {

    /**
     * 用户名密码登录
     * @param s
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }

    /**
     * 社交登录
     * @param code
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserBySocial(String code) throws UsernameNotFoundException {
        return null;
    }
}
