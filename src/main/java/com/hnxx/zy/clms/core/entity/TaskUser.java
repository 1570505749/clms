package com.hnxx.zy.clms.core.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: chenii
 * @date: 2020/4/13 11:19
 * @version: 1.0
 * @desc:
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskUser implements Serializable {

    private static final long serialVersionUID = 2737900483046037849L;

    /**
     * ID
     */
    private Integer Id;

    /**
     * 任务ID
     */
    private Integer taskId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户姓名
     */
    private String Name;

    /**
     * 是否已完成
     */
    private Boolean isDid;

    /**
     * 完成时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date didTime;


    /**
     * 回复内容
     */
    private String replyContent;

    /**
     * 评分等级，1优秀-2良好-3及格-4未及格
     */
    private Integer Level;

    /**
     * @Description: 文件名
     * @Param:
     * @return:
     * @Author: CHENLH
     * @Date: 2020-05-19 21:26:46
     */
    private String fileName;

    /**
     * @Description: 文件地址
     * @Param:
     * @return:
     * @Author: CHENLH
     * @Date: 2020-05-19 21:26:54
     */
    private String fileUrl;
}
