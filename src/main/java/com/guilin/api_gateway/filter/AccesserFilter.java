package com.guilin.api_gateway.filter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

@Component
public class AccesserFilter extends ZuulFilter{

	private static Logger log = LoggerFactory.getLogger(AccesserFilter.class);
	
	@Override
	public Object run() {
		RequestContext rtx = RequestContext.getCurrentContext();
		HttpServletRequest request = rtx.getRequest();
		
		log.info("send {} request to {}", request.getMethod(), request
				.getRequestURL().toString());
		Object accessToken = request.getParameter("accessToken");
		if (accessToken == null) {
			log.warn("accesstoken is empty");
			rtx.setSendZuulResponse(false);
			rtx.setResponseStatusCode(401);
			return null;
		}
		log.info("accesstoken is ok");
		return null;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public String filterType() {
		return "pre";
	}

}
