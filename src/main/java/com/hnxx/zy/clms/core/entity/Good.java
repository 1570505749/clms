/**
 * @FileName: Good
 * @Author: code-fusheng
 * @Date: 2020/3/25 14:27
 * Description: 点赞实体类
 */
package com.hnxx.zy.clms.core.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Good implements Serializable {

    /**
     * 点赞id
     */
    private Integer goodId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 文章id
     */
    private Integer articleId;

    /**
     * 评论id
     */
    private Integer commentId;

    /**
     * 问题id
     */
    private Integer questionId;

    /**
     * 答复id
     */
    private Integer answerId;

    /**
     * 视屏id
     */
    private Integer videoId;

    /**
     * 点赞类型
     * 文章 0; 评论 1; 问题 2; 答复 3; 视频 4;
     */
    private Integer goodType;

    /**
     * 点赞时间
     */
    private String goodTime;

}
