package org.springframework.clinicaetsii.configuration;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.springframework.web.filter.GenericFilterBean;

public class EncodingFilter extends GenericFilterBean {

	private String encoding = "UTF-8";

	@Override
	public void doFilter(final ServletRequest request,
			final ServletResponse response,
			final FilterChain next) throws IOException, ServletException {
		// Respect the client-specified character encoding
		// (see HTTP specification section 3.4.1)
		if (null == request.getCharacterEncoding()) {
			request.setCharacterEncoding(this.encoding);
		}

		// Set the default response content type and encoding
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		next.doFilter(request, response);
	}

}
