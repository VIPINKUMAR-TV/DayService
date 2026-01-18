package com.expense.DayService.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class FeignAuthRequestInterceptor implements RequestInterceptor {

	private static final String HEADER = "X-User-Id";

    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes attrs =
            (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attrs == null) return; // no incoming request context (e.g., background thread)

        HttpServletRequest request = attrs.getRequest();
        if (request == null) return;

        String userId = request.getHeader(HEADER);
        if (userId != null && !userId.isBlank()) {
            // add header to outgoing request; overwrite if already present
            template.header(HEADER, userId);
        }
    }
}
