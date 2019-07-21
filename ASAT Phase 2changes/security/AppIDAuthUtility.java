package com.ibm.asat.admin.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;

public interface AppIDAuthUtility {

	
public String getaccesstoken();
public boolean isUserAutheticated(HttpServletRequest request) throws InvalidTokenException,TokenExpiredException;
public boolean validateAccessToken(HttpServletRequest request, String accessToken) ;

}

