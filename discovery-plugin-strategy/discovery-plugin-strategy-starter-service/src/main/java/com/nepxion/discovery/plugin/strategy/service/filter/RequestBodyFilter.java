package com.nepxion.discovery.plugin.strategy.service.filter;

import com.alibaba.fastjson.JSON;
import com.nepxion.discovery.plugin.strategy.service.context.RequestBodyContext;
import com.nepxion.discovery.plugin.strategy.service.wrapper.ServletRequestWrapper;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 *
 * @author zlliu
 * @date 2020/10/22 18:16
 */
public class RequestBodyFilter extends OncePerRequestFilter {
	public static final String FILTER_NAME = "requestBodyFilter";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		ServletRequestWrapper requestWrapper = null;
		if(request instanceof HttpServletRequest) {
			requestWrapper = new ServletRequestWrapper(request);
		}
		try {
			if(requestWrapper == null) {
				filterChain.doFilter(request, response);
			} else {
				Map<String, Object> map = null;
				try {
					map = JSON.parseObject(new String(requestWrapper.getRequestBody()), Map.class);
					RequestBodyContext.getContext().set(map);
				} catch (Exception e) {
				}
				filterChain.doFilter(requestWrapper, response);
			}
		} finally {
			RequestBodyContext.clear();
		}
	}

	@Override
	public void destroy() {}

}