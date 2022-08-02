package com.imall.thirdparty.config;

import feign.Client;
import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import static feign.FeignException.errorStatus;
import static java.lang.String.format;

/**
 * FeignConfiguration
 *
 * @author zhangpengjun
 * @date 2022/7/26
 */
@Slf4j
@Configuration
public class FeignConfiguration {

    @Bean
    public Decoder decoder() {
        return (response, type) -> {
            if (response.status() == 404 || response.status() == 204)
                return Util.emptyValueOf(type);
            if (response.body() == null)
                return null;
            if (byte[].class.equals(type)) {
                return Util.toByteArray(response.body().asInputStream());
            }
            Response.Body body = response.body();
            if (body == null) {
                return null;
            }
            if (String.class.equals(type)) {
                return Util.toString(body.asReader(Util.UTF_8));
            }
            throw new DecodeException(response.status(),
                    format("%s is not a type supported by this decoder.", type), response.request());
        };
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return (methodKey, response) -> {
            FeignException exception = errorStatus(methodKey, response);
            // 获取原始的返回内容
            String json = null;
            try {
                json = Util.toString(response.body().asReader(Util.UTF_8));
                log.error("fegin调用异常处理 请求{} 返回:{}", response.request().toString(), json);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return exception;
        };
    }

    /**
     * feign配置忽略ssl
     */
    @Bean
    @ConditionalOnMissingBean
    public Client feiClient() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext ctx = SSLContext.getInstance("SSL");
        X509TrustManager tm = new X509TrustManager() {
            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        ctx.init(null, new TrustManager[]{tm}, null);
        return new Client.Default(
                ctx.getSocketFactory(), new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession sslSession) {
                return true;
            }
        }
        );
    }

}
