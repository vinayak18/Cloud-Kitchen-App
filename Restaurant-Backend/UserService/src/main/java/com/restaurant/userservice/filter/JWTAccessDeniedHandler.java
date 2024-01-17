package com.restaurant.userservice.filter;
import java.io.IOException;
import java.io.OutputStream;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.userservice.model.CustomErrorResponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
    	CustomErrorResponse httpResponse=new CustomErrorResponse(HttpStatus.FORBIDDEN.value(),HttpStatus.FORBIDDEN,HttpStatus.FORBIDDEN.getReasonPhrase(),"You are not allowed to access that resource");
    	response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    	response.setStatus(HttpStatus.FORBIDDEN.value());
    	OutputStream outputStream=response.getOutputStream();
    	ObjectMapper mapper=new ObjectMapper();
    	mapper.writeValue(outputStream, httpResponse);
    	outputStream.flush();
     }
}