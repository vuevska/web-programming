/**
package mk.finki.ukim.mk.lab.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter
public class SelectedSizeFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String path = request.getServletPath();
        String size = (String) request.getSession().getAttribute("size");
        String sizeErrorText = "Please select a balloon size!";
        if (!path.equals("") && !path.equals("/selectBalloon") && size == null && !path.equals("/selectBalloonSize.css")) {
            request.getSession().setAttribute("sizeError", true);
            request.getSession().setAttribute("sizeErrorText", sizeErrorText);
            response.sendRedirect("/selectBalloon");
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
*/
