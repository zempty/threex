package com.zhu2chu.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.handler.Handler;
import com.jfinal.kit.HandlerKit;

public class ThreeXHandler extends Handler {

	@Override
	public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {

		String uri = request.getRequestURI();
		if (uri.endsWith(".html")) {
			HandlerKit.renderError404(request, response, isHandled);
			return;
		}

		next.handle(target, request, response, isHandled);
	}

}
