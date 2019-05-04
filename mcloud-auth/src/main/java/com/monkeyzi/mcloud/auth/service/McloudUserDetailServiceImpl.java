package com.monkeyzi.mcloud.auth.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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
        String password="$2a$10$PX.0ntZY0ChfkR2KdkXG/OeR.HGvEAZTixiFteJhc.k8ULLAPzPva";
        String BCRYPT = "{bcrypt}";
        return new McloudUser(0,002,002,"guoguo",BCRYPT+password,true,
                true,true,true,new ArrayList<>());
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
