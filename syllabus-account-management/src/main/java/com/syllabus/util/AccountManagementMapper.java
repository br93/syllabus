package com.syllabus.util;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.syllabus.client.ClientResponse;
import com.syllabus.client.LoginResponse;
import com.syllabus.data.APIResponse;
import com.syllabus.data.AccountRequest;
import com.syllabus.data.AccountResponse;
import com.syllabus.data.model.AccountModel;

@Mapper(componentModel = "spring", uses = MappingUtil.class)
public interface AccountManagementMapper {

    public AccountResponse toAccountResponse(LoginResponse login);
    public APIResponse toAPIResponse(ClientResponse clientResponse);

    public AccountResponse toAccountResponse(AccountModel model);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "userId", ignore = true)
    public AccountModel toAccountModel(AccountRequest request);
    
}
