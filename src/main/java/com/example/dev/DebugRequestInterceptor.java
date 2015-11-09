package com.example.dev;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class DebugRequestInterceptor implements HandlerInterceptor {
    static final Logger logger = LoggerFactory.getLogger(DebugRequestInterceptor.class);

    @Autowired
    DebugManager debugManager;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        logger.debug(String.format("调试拦截器截获来自 %s 的 %s 请求：%s",
                        httpServletRequest.getRemoteHost() + ":" + httpServletRequest.getRemotePort(),
                        httpServletRequest.getMethod(),
                        httpServletRequest.getRequestURI())
        );
        DebugController.Request request = new DebugController.Request();
        request.setHost(httpServletRequest.getRemoteAddr() + ":" + httpServletRequest.getRemotePort());
        request.setMethod(httpServletRequest.getMethod());
        request.setType(httpServletRequest.getContentType());
        request.setContentLength(httpServletRequest.getContentLength());
        request.setUrl(httpServletRequest.getRequestURI());
        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            List<String> headerValues = new ArrayList<>();
            Enumeration<String> headers = httpServletRequest.getHeaders(headerName);
            while (headers.hasMoreElements()) {
                String headerValue = headers.nextElement();
                headerValues.add(headerValue);
            }
            request.getHeaders().put(headerName, headerValues);
        }
        // cannot use get reader -> IllegalStateException: STREAMED
        InputStream is = httpServletRequest.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String content = null;
        String line;
        while ((line = br.readLine()) != null) {
            if (content == null) {
                content = line;
            } else {
                content += "<br>" + line;
            }
        }
        if (content == null) {
            Map<String, String[]> params = httpServletRequest.getParameterMap();
            content = "";
            boolean first = true;
            for (Map.Entry<String, String[]> entry : params.entrySet()) {
                if (!first) content += "<br>";
                content += entry.getKey() + ": " + Arrays.stream(entry.getValue()).collect(Collectors.joining(", "));
            }
        }
        request.setContent(content);
        debugManager.requests.add(0, request);
    }
}
