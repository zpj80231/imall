package com.imall.thirdparty.modules.pojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @TableName cdr_20220711
 */
@TableName(value = "cdr")
@Data
public class Cdr implements Serializable {
    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     *
     */
    @TableField(value = "project_id")
    private String projectId;

    /**
     *
     */
    @TableField(value = "calldate")
    private Date calldate;

    /**
     *
     */
    @TableField(value = "clid")
    private String clid;

    /**
     *
     */
    @TableField(value = "src")
    private String src;

    /**
     *
     */
    @TableField(value = "dnid")
    private String dnid;

    /**
     *
     */
    @TableField(value = "dst")
    private String dst;

    /**
     *
     */
    @TableField(value = "sipcallid")
    private String sipcallid;

    /**
     *
     */
    @TableField(value = "dcontext")
    private String dcontext;

    /**
     *
     */
    @TableField(value = "channel")
    private String channel;

    /**
     *
     */
    @TableField(value = "dstchannel")
    private String dstchannel;

    /**
     *
     */
    @TableField(value = "callerip")
    private String callerip;

    /**
     *
     */
    @TableField(value = "calledip")
    private String calledip;

    /**
     *
     */
    @TableField(value = "lastapp")
    private String lastapp;

    /**
     *
     */
    @TableField(value = "lastdata")
    private String lastdata;

    /**
     *
     */
    @TableField(value = "start")
    private Date start;

    /**
     *
     */
    @TableField(value = "answer")
    private Date answer;

    /**
     *
     */
    @TableField(value = "end")
    private Date end;

    /**
     * 通话时长
     */
    @TableField(value = "duration")
    private Integer duration;

    /**
     * 通话计费时长
     */
    @TableField(value = "billsec")
    private Integer billsec;

    /**
     *
     */
    @TableField(value = "disposition")
    private String disposition;

    /**
     *
     */
    @TableField(value = "amaflags")
    private Integer amaflags;

    /**
     *
     */
    @TableField(value = "accountcode")
    private String accountcode;

    /**
     *
     */
    @TableField(value = "peeraccount")
    private String peeraccount;

    /**
     *
     */
    @TableField(value = "uniqueid")
    private String uniqueid;

    /**
     *
     */
    @TableField(value = "linkedid")
    private String linkedid;

    /**
     *
     */
    @TableField(value = "userfield")
    private String userfield;

    /**
     *
     */
    @TableField(value = "ivrdigit")
    private String ivrdigit;

    /**
     *
     */
    @TableField(value = "ivrflow")
    private String ivrflow;

    /**
     *
     */
    @TableField(value = "sequence")
    private String sequence;

    /**
     *
     */
    @TableField(value = "billingid")
    private String billingid;

    /**
     *
     */
    @TableField(value = "area_code")
    private String areaCode;

    /**
     *
     */
    @TableField(value = "city")
    private String city;

    /**
     *
     */
    @TableField(value = "succ")
    private Integer succ;

    /**
     *
     */
    @TableField(value = "tela")
    private String tela;

    /**
     *
     */
    @TableField(value = "telx")
    private String telx;

    /**
     *
     */
    @TableField(value = "telb")
    private String telb;

    /**
     * 是否已计费
     */
    @TableField(value = "billed")
    private Integer billed;

    /**
     * 客户账号
     */
    @TableField(value = "account")
    private String account;

    /**
     * 客户费用
     */
    @TableField(value = "fee")
    private BigDecimal fee;

    /**
     * 供应商账号
     */
    @TableField(value = "agentaccount")
    private String agentaccount;

    /**
     * 供应商成本
     */
    @TableField(value = "cost")
    private BigDecimal cost;

    /**
     * 客户费率组
     */
    @TableField(value = "feerate_gid")
    private Integer feerateGid;

    /**
     * 供应商费率组
     */
    @TableField(value = "agentfeerate_gid")
    private Integer agentfeerateGid;

    /**
     * 等候音乐
     */
    @TableField(value = "musictype")
    private Integer musictype;

    /**
     * 满意度
     */
    @TableField(value = "servicelevel")
    private Integer servicelevel;

    /**
     * 工号
     */
    @TableField(value = "agentid")
    private String agentid;

    /**
     * 录音文件名
     */
    @TableField(value = "recording")
    private String recording;

    /**
     * 通话方向in呼入,out呼出
     */
    @TableField(value = "`option`")
    private String option;

    /**
     * 系统并发呼叫数
     */
    @TableField(value = "numcalls")
    private Integer numcalls;

    /**
     * CPU负载
     */
    @TableField(value = "loadavg")
    private Double loadavg;

    /**
     * 挂机通道
     */
    @TableField(value = "hangupsource")
    private String hangupsource;

    /**
     * 挂机原因
     */
    @TableField(value = "hangupcause")
    private String hangupcause;

    /**
     *
     */
    @TableField(value = "create_time")
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
