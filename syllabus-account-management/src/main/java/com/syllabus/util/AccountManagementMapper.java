package com.syllabus.util;

import org.mapstruct.Mapper;

import com.syllabus.client.ClientResponse;
import com.syllabus.client.LoginResponse;
import com.syllabus.model.APIResponse;
import com.syllabus.model.AccountResponse;

@Mapper(componentModel = "spring", uses = MappingUtil.class)
public interface AccountManagementMapper {

    public AccountResponse toAccountResponse(LoginResponse login);
    public APIResponse toAPIResponse(ClientResponse clientResponse);
    
}
