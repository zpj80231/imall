package com.imall.admin.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * url 白名单
 *
 * @author zhangpengjun
 * @date 2022/8/24
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "security.ignored")
public class IgnoreUrlsConfig {

    private List<String> urls = new ArrayList<>();

}
