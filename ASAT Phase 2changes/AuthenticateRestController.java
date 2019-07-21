/*package com.ibm.asat.admin.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.asat.admin.request.UserLoginRequest;
import com.ibm.asat.admin.response.BaseResponse;
import com.ibm.asat.admin.response.Error;
import com.ibm.asat.admin.response.ErrorType;
import com.ibm.asat.admin.response.ResponseStatus;
import com.ibm.asat.admin.util.AdminConstants;

@CrossOrigin( origins = "*", maxAge = 3600 )
@RestController
@RequestMapping( "/admin" )
public class AuthenticateRestController
{

    private static final Logger logger = LoggerFactory.getLogger( AuthenticateRestController.class );

    *//**
     * The authenticate service provides a means to authenticate a user for access to other APIs and functions of the Admin Tool.
     * This operation is normally replaced by a client's security mechanism.
     * we need a simple authenticate service which only verifies a password .
     * Request Body ex: - { "username": "name", "password": "asat" } 
     * @param UserLoginRequest userLoginRequest
     * @return {@link BaseResponse}
     **//*
    @PostMapping( "/authenticate" )
    public BaseResponse authenticate( HttpServletRequest request,@RequestBody UserLoginRequest userLoginRequest )
    {
        logger.info( AdminConstants.STR_INSIDE + AdminConstants.AUTHENTICATE_USER );
        try
        {
            if( userLoginRequest.getPassword().toString().equals( getLoginPassword() ) )
            {
                return new BaseResponse( ResponseStatus.SUCCESS );
            }
            return new BaseResponse( ResponseStatus.ERROR, AdminConstants.RESPONSE_ERROR_INVALID_USER_CREDENTIALS );
        }
        catch( Exception lExcp )
        {
            logger.error( lExcp.getMessage(), lExcp );
            return new BaseResponse( new Error( ErrorType.ERROR,
                                                lExcp.getMessage(),
                                                String.valueOf( HttpStatus.INTERNAL_SERVER_ERROR.value() ) ) );
        }
    }

    *//**
     * To read pwd from the properties file
     * @return pass
     * @throws IOException
     *//*
    private String getLoginPassword() throws IOException
    {
        Properties properties = new Properties();
        File file = ResourceUtils.getFile( "classpath:admin-login.properties" );
        InputStream in = new FileInputStream( file );
        properties.load( in );
        String password = properties.getProperty( "pass" ).toString();
        in.close();
        return password;
    }

}
*/