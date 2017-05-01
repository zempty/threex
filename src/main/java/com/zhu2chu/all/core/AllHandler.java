package com.zhu2chu.all.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.handler.Handler;
import com.jfinal.kit.HandlerKit;

public class AllHandler extends Handler {

	@Override
	public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {

		String uri = request.getRequestURI();
		if (uri.endsWith(".html")) {
			HandlerKit.renderError404(request, response, isHandled);
			return;
		} else if (uri.contains("console")) {
		    isHandled[0] = false;
		    return;
		}

		next.handle(target, request, response, isHandled);
	}

}
