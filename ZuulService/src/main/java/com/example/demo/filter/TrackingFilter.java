package com.example.demo.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

@Component
public class TrackingFilter extends ZuulFilter{

	private final static int FILTER_ORDER=1;
	private final static boolean SHOULD_FILTER=true;
	private final static Logger logger=LoggerFactory.getLogger(TrackingFilter.class);
	
	@Autowired
	private FilterUtils filterUtils;
	


	@Override
	public boolean shouldFilter() {
		return SHOULD_FILTER;
	}

	@Override
	public int filterOrder() {
		return FILTER_ORDER;
	}

	@Override
	public String filterType() {
		return FilterUtils.PRE_FILTER_TYPE;
	}
	
	private boolean isCorrelationIdPresent() {
		if(filterUtils.getCorrelationId() != null) {
			return true;
		}
		return false;
	}
	
	public String generateCorrelationId() {
		return java.util.UUID.randomUUID().toString();
	}
	
	@Override
	public Object run() {
		if(isCorrelationIdPresent()) {
			logger.debug("is correlation id found in tracking filter: {}", filterUtils.getCorrelationId());
		} else {
			filterUtils.setCorrelationId(generateCorrelationId());
			logger.debug("is correlation id generated in tracking filter:{}", filterUtils.getCorrelationId());
		}
		RequestContext ctx= RequestContext.getCurrentContext();
		logger.debug("Processing incoming request for {}", ctx.getRequest().getRequestURI());
		return null;
	}
}
