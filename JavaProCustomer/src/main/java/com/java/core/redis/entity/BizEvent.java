package com.java.core.redis.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 事件类，存储事件详细信息
 *
 * @author wwlong
 * @version 2018年6月2日
 */
@Data
public class BizEvent implements Serializable {

    private Long id;
    private Date createTime;

    /** 事件名称 **/
    private String name;

    /** 副标题 (2018-06-01后已无效) **/
    private String title;

    /** 事件类型 1-SITU,2-Marketing,3-Training,4-Position,5-Experience (2018-06-01后已无效) **/
    /** 事件类型 1-app 2-sdk 2018-11-08重新使用 **/
    private Integer type;

    /** 创作团队 **/
    private String team;

    /** 事件图片 **/
    private String imageObj;

    /** 封面图 **/
    private String coverObj;

    /** 屏幕方向 **/
    private Integer direction;

    /** 事件描述 **/
    private String description;

    /** 用于事件人工排序 **/
    private Integer sequence;

    /**
     * 见枚举 ：EventCategory.java
     * 事件类别 0-基本事件，1-事件分组，2-专题分组，3-Banner分组，4-Sticker分组, 5-二级页面 <br>
     * (V2.3.0后版本此字段无效，改用其它方式实现，后续考虑删除此字段)
     **/
    private Integer category;

    /** 作者信息 (json串) **/
    private String authorInfo = "";

    /** 跳转的url (H5会用到) **/
    private String url;

    /** 黑白名单状态 0-没有黑白名单，1-有黑名单，2-有白名单 **/
    private Integer listStatus;

    /** 创作团队搜索条件 0不被搜索 1可以被搜索**/
    private Integer instSearchStatus;
    /** 创作团队id **/
    private Long instId;

    /** 事件有android 版本 **/
    private Integer hasAndroid;
    /** 事件有iOS版本 **/
    private Integer hasIOS;
    /** 事件支持最小android 版本 **/
    private String androidVersion;
    /** 事件支持iOS版本(最小) **/
    private String iosVersion;
    /** 平台类型 1-app 2-SDK**/
    private Integer platformType;

    /** 1-图片 2-陀螺仪 **/
    private Integer coverType;
    /** 封面图 资源列表 **/
    private String coverResourceList;
    /** covorResourceList 解析后的数据 **/
    private Long[] coverResourceIdArr;

    /** 详情图资源列表 **/
    private String detailResourceList;
    /** detailResourceList 解析后的数据 **/
    private Long[] detailResourceIdArr;

}
