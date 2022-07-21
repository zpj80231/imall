package com.imall.thirdparty.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;

import java.io.InputStream;

/**
 * HttpInputMessage包装类
 *
 * @author zhangpengjun
 * @date 2022/7/15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HttpInputMessageWrapper implements HttpInputMessage {

    private HttpHeaders headers;
    private InputStream body;

}
