package com.imall.notice.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MqMsgVo {
    private Integer no;
    private String name;
    private String phone;
    private Date createTime;
}
