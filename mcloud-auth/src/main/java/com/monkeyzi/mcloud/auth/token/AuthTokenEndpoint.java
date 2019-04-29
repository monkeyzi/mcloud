package com.monkeyzi.mcloud.auth.token;

import com.monkeyzi.mcloud.common.result.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class AuthTokenEndpoint {

     @GetMapping(value = "/test1")
     public R  Test(){
         return R.okMsg("ok");
     }
}
