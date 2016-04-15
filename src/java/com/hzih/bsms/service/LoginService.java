package com.hzih.bsms.service;

import com.hzih.bsms.domain.Account;

public interface LoginService {

	Account getAccountByNameAndPwd(String name, String pwd) ;

    Account getAccountByName(String name) ;
}
