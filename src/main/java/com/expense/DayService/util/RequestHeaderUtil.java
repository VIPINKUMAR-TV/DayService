package com.expense.DayService.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpServletRequest;

public class RequestHeaderUtil {
	public static Long extractUserId(HttpServletRequest request) {
	        String id = request.getHeader("X-User-Id");
	        if (id == null || id.isBlank()) {
	            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing X-User-Id header (Gateway did not forward auth)");
	        }
	        try {
	            return Long.parseLong(id);
	        } catch (NumberFormatException ex) {
	            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid X-User-Id header");
	        }
	    }
}
