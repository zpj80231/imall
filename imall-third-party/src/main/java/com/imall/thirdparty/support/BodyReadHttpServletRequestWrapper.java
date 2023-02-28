package com.imall.thirdparty.support;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * 请求体参数包装类，用于解决多次读取请求体body数据的问题。
 * 由于流对象只能读取一次，如果在过滤器或拦截器中进行读取验证，那么之后在Controller中的参数封装就会报错。
 *
 * @author zhangpengjun
 * @date 2023/2/27
 */
public class BodyReadHttpServletRequestWrapper extends HttpServletRequestWrapper {

    /**
     * url请求参数
     */
    private final Map<String, String[]> params = new HashMap<>();
    /**
     * 可重复读取的请求体
     */
    private byte[] body;

    public BodyReadHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        this.params.putAll(request.getParameterMap());
        this.body = StreamUtils.copyToByteArray(request.getInputStream());
        emptyBodyHandling(request);
    }

    private void emptyBodyHandling(HttpServletRequest request) {
        String method = request.getMethod();
        String contentType = request.getContentType();
        if (ObjectUtils.isEmpty(body) && (HttpMethod.GET.matches(method) || contentType.contains(MediaType.APPLICATION_JSON_VALUE))) {
            body = "{}".getBytes(StandardCharsets.UTF_8);
        }
    }

    @Override
    public ServletInputStream getInputStream() {
        // 读取数据时，每次模拟构建一个新的流对象，读取请求体中的数据
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
                // default implementation ignored
            }

            @Override
            public int read() {
                return byteArrayInputStream.read();
            }
        };
    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    /**
     * 在获取所有的参数名,必须重写此方法，否则对象中参数值映射不上
     *
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public Enumeration<String> getParameterNames() {
        return new Vector(params.keySet()).elements();
    }

    /**
     * 重写getParameter方法
     *
     * @param name 参数名
     * @return 返回参数值
     * @author zhangpengjun
     */
    @Override
    public String getParameter(String name) {
        String[] values = params.get(name);
        if (values == null || values.length == 0) {
            return null;
        }
        return values[0];
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = params.get(name);
        if (values == null || values.length == 0) {
            return new String[0];
        }
        return values;
    }

    /**
     * 增加多个参数
     *
     * @param otherParams 增加的多个参数
     */
    public void addAllParameters(Map<String, Object> otherParams) {
        for (Map.Entry<String, Object> entry : otherParams.entrySet()) {
            addParameter(entry.getKey(), entry.getValue());
        }
    }

    /**
     * 增加参数
     * getParameterMap()中的类型是<String,String[]>类型的，所以这里要将其value转为String[]类型
     *
     * @param name  参数名
     * @param value 参数值
     */
    public void addParameter(String name, Object value) {
        if (value != null) {
            if (value instanceof String[]) {
                params.put(name, (String[]) value);
            } else if (value instanceof String) {
                params.put(name, new String[]{(String) value});
            } else {
                params.put(name, new String[]{String.valueOf(value)});
            }
        }
    }

    /**
     * description: 提供获取requestBody的方法
     *
     * @return byte[]
     * @version v1.0
     * @author w
     * @date 2021年4月20日 下午4:02:29
     */
    public byte[] getRequestBody() {
        return body;
    }

    /**
     * 支持修改body内容
     *
     * @param requestBody
     * @author zhangpengjun
     * @date 2022/09/11
     */
    public void setRequestBody(byte[] requestBody) {
        this.body = requestBody;
    }
}
