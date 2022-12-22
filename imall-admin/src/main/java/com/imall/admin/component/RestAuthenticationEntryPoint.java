package com.imall.admin.component;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.imall.common.api.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 身份认证异常返回
 *
 * @author zhangpengjun
 * @date 2022/8/24
 */
@Slf4j
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        JSON msg = JSONUtil.parse(CommonResult.unauthorized(authException.getMessage()));
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println(msg);
        response.getWriter().flush();
        log.info("身份认证异常: {}", msg);
    }
}
