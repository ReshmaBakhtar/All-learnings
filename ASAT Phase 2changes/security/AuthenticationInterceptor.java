package com.ibm.asat.admin.security;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;


import com.ibm.asat.admin.security.AppIdAuthUtilityImpl;
import  com.ibm.asat.admin.security.TokenExpiredException;



public class AuthenticationInterceptor implements HandlerInterceptor {
	
	 private static final Logger logger = LoggerFactory.getLogger( AuthenticationInterceptor.class );
	
	@Autowired
	AppIdAuthUtilityImpl appIDAuthUtilityImpl;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		try {
			if(appIDAuthUtilityImpl.isUserAutheticated(request)){
				return true;
			}
			
				else {
					
					logger.error("Session has expird ! please Login again");
				}
					
			
		}
		
		
		catch (TokenExpiredException tokenExpiredException) {
			  logger.error("Token expired. Please login again");
			  logger.info("Token expired");
		       
		        SecurityContextHolder.clearContext();
		        response.sendError(HttpServletResponse.SC_REQUEST_TIMEOUT, tokenExpiredException.getMessage());
		}
		
		return true;
	

	
	}
	
	
	
	
}
