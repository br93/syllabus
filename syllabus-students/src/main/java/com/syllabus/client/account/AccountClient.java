package com.syllabus.client.account;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "${account.management.name}")
public interface AccountClient {

    @GetMapping(value = "${account.management.path}")
    public APIResponse getAccount();
    
}
