package com.ibm.asat.admin.security;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;


import com.ibm.asat.admin.response.AccessTokenResponse;
import com.ibm.asat.admin.response.AccessTokenStatus;

public class AppIdAuthUtilityImpl{
	
@Value("${security.oauth2.client.client-id}")
private String clientId;

@Value("${security.oauth2.client.client-secret}")
private String clientSecret;

@Value("${security.oauth2.client.tenant-id}")
private String tenantId;

@Value("${security.oauth2.client.access-token-url}")
private String accessTokenUrl;

@Value("${security.oauth2.client.introspect-url}")
private String introspectUrl;
	
private static final Logger logger = LoggerFactory.getLogger(AppIdAuthUtilityImpl.class);

	@Autowired
	private RestTemplate restTemplate;
	


	public  String getaccesstoken(HttpServletRequest request) {
	
		try {
			String[] decodedvalues = null;
			final String authorization = request.getHeader("Authorization");
			
			if (authorization != null && authorization.toLowerCase().startsWith("basic")) {
			    // Authorization: Basic base64credentials
			    String decodeCredentials = authorization.substring("Basic".length()).trim();
			    byte[] credDecoded = Base64.getDecoder().decode(decodeCredentials);
			    String decodedCredentials = new String(credDecoded, StandardCharsets.UTF_8);
			    // credentials = username:password
			    decodedvalues = decodedCredentials.split(":", 2);
			    }
			
			String userName=decodedvalues[0];
			String password=decodedvalues[1];
		
			String encodedCredentials=Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes());
			HttpHeaders headers = new HttpHeaders();
			headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
			headers.set("Authorization", "Basic " + encodedCredentials);
			String requestBody="{\"grant_type\":\"password\",\"username\": \"" + userName + "\",\"password\":\"" + password + "\"}" ; 
			HttpEntity<String> entity = new HttpEntity<String>(requestBody, headers);
			ResponseEntity<AccessTokenResponse> response = restTemplate.exchange(accessTokenUrl, HttpMethod.POST, entity, AccessTokenResponse.class);

			if (response.getStatusCode() == HttpStatus.OK) {
				logger.info("Obtaining Access Token : ");
				return response.getBody().getAccessToken();
			}
			
		} catch (Exception e) {
			throw new RuntimeException("Error occured during Authentication of UserName and Password");
		}
		
		return null;
	}
		
			
			
	public boolean validateAccessToken(HttpServletRequest request, String accessToken) {
		
		
		try {
			
			String introspectUrl = this.introspectUrl;

			String encodedCredentials=Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes());
			HttpHeaders headers = new HttpHeaders();
			headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
			headers.set("Authorization", "Basic " + encodedCredentials);
			String requestBody="{\"token\": \"" + accessToken + "\"}" ; 
			HttpEntity<String> entity = new HttpEntity<String>(requestBody, headers);
			ResponseEntity<AccessTokenStatus> response = restTemplate.exchange(introspectUrl, HttpMethod.POST, entity, AccessTokenStatus.class);


			if (response.getStatusCode() == HttpStatus.OK) {
				logger.info("Getting Token Status : ");
				return response.getBody().getTokenActiveStatus();
			}
		} catch (Exception e) {
			throw new RuntimeException("Error occured during Authenticating Access token");
		}
		return false;
	}
	
	public boolean isUserAutheticated(HttpServletRequest request) throws InvalidTokenException,TokenExpiredException{
		try {
			
			
			 
			String accessToken;
			String authorizationHeader=request.getHeader("Authorization");
			if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Basic ")) {
		
				accessToken = getaccesstoken(request);
				logger.info("Access token : ", accessToken);
				if(!StringUtils.isEmpty(accessToken)) {
					logger.info("Validating Access Token for Basic");
					return validateAccessToken(request, accessToken);
				}else {
					return false;
				}
			}else if(StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")){
				
				accessToken = authorizationHeader.substring(7, authorizationHeader.length());
				logger.info("Validating Access Token for Bearer");
				return validateAccessToken(request, accessToken);
			}
		}
		
		catch (Exception e) {
				return false;
			}
		return false;
	}
	
}

