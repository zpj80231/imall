package com.imall.notice.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

/**
 * @author zhangpengjun
 * @date 2022/9/20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PushInfoDTO {

    /**
     * 推送配置信息(包括url等)
     */
    private Object pushInfoEntity;
    /**
     * 推送字段
     */
    private Set<String> pushFields;

}
