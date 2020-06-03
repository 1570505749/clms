/**
 * @FileName: CommentServiceImpl
 * @Author: code-fusheng
 * @Date: 2020/3/24 14:44
 * Description: 评论业务逻辑接口实现类
 */
package com.hnxx.zy.clms.core.service.impl;

import com.hnxx.zy.clms.common.enums.StateEnum;
import com.hnxx.zy.clms.common.exception.ClmsException;
import com.hnxx.zy.clms.common.utils.Page;
import com.hnxx.zy.clms.core.entity.Comment;
import com.hnxx.zy.clms.core.entity.Good;
import com.hnxx.zy.clms.core.entity.User;
import com.hnxx.zy.clms.core.entity.Xxx;
import com.hnxx.zy.clms.core.mapper.CommentMapper;
import com.hnxx.zy.clms.core.service.CommentService;
import com.hnxx.zy.clms.core.service.GoodService;
import com.hnxx.zy.clms.core.service.UserService;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private GoodService goodService;

    /**
     * 保存,添加
     *
     * @param comment
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Comment comment) {
        // 评论类型为0 文章评论 父类id为空
        commentMapper.save(comment);
        // 根据评论类型， 文章评论 commentType = 0, 评论的评论 commentType = 1
        if(comment.getCommentType() == 0){
            int aid = comment.getCommentArticle();
            int cCount = commentMapper.getCountByAid(aid);
            // 更新文章评论数
            commentMapper.updateACommentCount(cCount, aid);
        } else if(comment.getCommentType() == 1) {
            // 判断二级评论的回复对象
            // int parentCommentId = comment.getPid();
            // 获取父级评论的评论人
            // String parentCommentUser = commentMapper.getById(parentCommentId).getCommentUser();
            // 获取当前评论的评论人
            // String nowCommentUser = comment.getCommentUser();
            // 判断是否与当前评论人相同
            // if(nowCommentUser == parentCommentUser){
            //     comment.setParentCommentUser(null);
            // } else {
            //     comment.setParentCommentUser(parentCommentUser);
            // }
            int cid = comment.getPid();
            int cCount = commentMapper.getCountByCid(cid);
            // 更新评论评论数
            commentMapper.updateCCommentCount(cCount, cid);
        }

    }

    /**
     * 根据id查询单条评论
     * @param id
     * @return
     */
    @Override
    public Comment getById(Integer id) {
        return commentMapper.getById(id);
    }

    /**
     * 查询所有,层级结构
     *
     * @return
     */
    @Override
    public List<Comment> getAll(){
        List<Comment> commentList = commentMapper.getAll();
        // 遍历 getAll 返回的文章评论实体
        for (Comment comment : commentList) {
            // 拿到文章评论实体的id值
            int id = comment.getCommentId();
            comment.setCommentCount(getCountByCid(id));
            List<Comment> comments = commentMapper.getCommentByPid(id);
            for (Comment comment1 : comments) {
                comment.getCommentList().add(comment1);
            }
        }
        return commentList;
    }

    /**
     * 根据id删除评论 []
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Integer id) {
        // 先查询校验 在删除
        Comment comment = commentMapper.getById(id);
        // 获取评论的类型
        int type = comment.getCommentType();
        // 如果类型为文章评论, 则删除评论下的评论
        if(type == StateEnum.ARTICLE_COMMENT.getCode()){
            // 获取文章评论的id
            int pid = comment.getCommentId();
            // 先根据文章评论id删除 评论的评论
            commentMapper.deleteByPid(pid);
        }
        commentMapper.deleteById(id);

    }

    /**
     * 根据id查询文章评论列表
     * @param id
     * @return
     */
    @Override
    public List<Comment> getListById(Integer id) {
        List<Comment> commentList = commentMapper.getCommentByAid(id);
        for(Comment comment : commentList){
            int pid = comment.getCommentId();
            comment.setCommentCount(getCountByCid(pid));
        }
        return commentList;
    }

    @Override
    public Page<Comment> getCommentList(Page<Comment> page) {
        // 查询数据
        List<Comment> commentList = commentMapper.getCommentList(page);
        User user = userService.selectByName(SecurityContextHolder.getContext().getAuthentication().getName());
        int uid = user.getUserId();
        commentList.forEach( comment -> {
            int count = goodService.getGoodCountForComment(uid, comment.getCommentId());
            if(count == 0){
                comment.setGoodCommentFlag(false);
            }else {
                comment.setGoodCommentFlag(true);
            }
        } );
        page.setList(commentList);
        // 查询总数
        int totalCount = commentMapper.getCountByPage(page);
        page.setTotalCount(totalCount);
        return page;
    }

    @Override
    public Page<Comment> getByPage(Page<Comment> page) {
        // 查询数据
        List<Comment> commentList = commentMapper.getByPage(page);
        // 查询点赞状态
        User user = userService.selectByName(SecurityContextHolder.getContext().getAuthentication().getName());
        int uid = user.getUserId();
        commentList.forEach( comment -> {
            int count = goodService.getGoodCountForComment(uid, comment.getCommentId());
            if(count == 0){
                comment.setGoodCommentFlag(false);
            }else {
                comment.setGoodCommentFlag(true);
            }
        } );
        page.setList(commentList);
        // 查询总数
        int totalCount = commentMapper.getChildCountByPage(page);
        page.setTotalCount(totalCount);
        return page;
    }


    /**
     * 根据文章id获取文章评论的总量
     * @param aid
     * @return
     */
    int getCountByAid(int aid){
        int comment1 = commentMapper.getCountByAid(aid);
        return comment1;
    }

    /**
     * 通过评论id获取评论的评论量
     * @param pid
     * @return
     */
    int getCountByCid(int pid){
        int comment2 = commentMapper.getCountByCid(pid);
        return comment2;
    }


}
