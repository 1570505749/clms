/**
 * @FileName: QuestionMapper
 * @Author: code-fusheng
 * @Date: 2020/4/11 22:35
 * Description: 问题数据访问层
 */
package com.hnxx.zy.clms.core.mapper;

import com.hnxx.zy.clms.common.utils.Page;
import com.hnxx.zy.clms.core.entity.Article;
import com.hnxx.zy.clms.core.entity.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
@Repository
public interface QuestionMapper {

    /**
     * 保存
     * @param question
     */
    @Insert("insert into cl_question(question_description, question_content, question_author) " +
            "values (#{questionDescription}, #{questionContent}, #{questionAuthor})")
    void save(Question question);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Select("select * from cl_question where is_deleted = 0 and question_id = #{id}")
    Question getById(Integer id);

    /**
     * 修改
     * @param question
     */
    @Update("<script>" +
            "        update cl_question set question_id = #{questionId}\n" +
            "        <if test=\"questionDescription!=null and questionDescription!=''\">\n" +
            "            ,question_description = #{questionDescription}\n" +
            "        </if>\n" +
            "        <if test=\"questionContent!=null and questionContent!=''\">\n" +
            "            ,question_content = #{questionContent}\n" +
            "        </if>\n" +
            "        <if test=\"questionAuthor!=null and questionAuthor!=''\">\n" +
            "            ,question_author = #{questionAuthor}\n" +
            "        </if>\n" +
            "        <if test=\"questionMark!=null\">\n" +
            "            ,question_mark = #{questionMark}\n" +
            "        </if>\n" +
            "        where question_id = #{questionId}" +
            "</script>")
    void update(Question question);

    /**
     * 根据id删除
     * @param id
     */
    @Update("update cl_question set is_deleted = 1 where question_id = #{id}")
    void deletedById(Integer id);

    /**
     * 分页查询
     * @param page
     * @return
     */
    @Select("<script>" +
            "        select question_id, question_description, question_content, question_author, question_time, update_time, question_good, answer_count, question_mark from cl_question\n" +
            "        where is_deleted = 0 \n" +
            "        <if test=\"params.questionDescription!=null and params.questionDescription!=''\">\n" +
            "            and question_description like CONCAT('%', #{params.questionDescription}, '%')\n" +
            "        </if>\n" +
            "        <if test=\"params.questionContent!=null and params.questionContent!=''\">\n" +
            "            and question_content like CONCAT('%', #{params.questionContent}, '%')\n" +
            "        </if>\n" +
            "        <if test=\"params.questionAuthor!=null and params.questionAuthor!=''\">\n" +
            "            and question_author like CONCAT('%', #{params.questionAuthor}, '%')\n" +
            "        </if>\n" +
            "        <if test=\"params.questionTime!=null\">\n" +
            "            and question_time between #{params.questionTime[0]} and #{params.questionTime[1]}\n" +
            "        </if>\n" +
            "        <if test=\"params.questionMark!=null\">\n" +
            "            and question_mark = #{params.questionMark}\n" +
            "        </if>\n" +
            "        <if test=\"sortColumn!=null and sortColumn!=''\">\n" +
            "            order by ${sortColumn} ${sortMethod}\n" +
            "        </if>\n" +
            "        limit #{index}, #{pageSize}" +
            "</script>")
    List<Question> getByPage(Page<Question> page);

    /**
     * 查询总数
     * @param page
     * @return
     */
    @Select("<script>" +
            "        select count(*) from cl_question\n" +
            "        where is_deleted = 0\n" +
            "        <if test=\"params.questionDescription!=null and params.questionDescription!=''\">\n" +
            "            and question_description like CONCAT('%', #{params.questionDescription}, '%')\n" +
            "        </if>\n" +
            "        <if test=\"params.questionContent!=null and params.questionContent!=''\">\n" +
            "            and question_content like CONCAT('%', #{params.questionContent}, '%')\n" +
            "        </if>\n" +
            "        <if test=\"params.questionAuthor!=null and params.questionAuthor!=''\">\n" +
            "            and question_author like CONCAT('%', #{params.questionAuthor}, '%')\n" +
            "        </if>\n" +
            "        <if test=\"params.questionTime!=null\">\n" +
            "            and question_time between #{params.questionTime[0]} and #{params.questionTime[1]}\n" +
            "        </if>\n" +
            "        <if test=\"params.questionMark!=null\">\n" +
            "            and question_mark = #{params.questionMark}\n" +
            "        </if>\n" +
            "</script>")
    int getCountByPage(Page<Question> page);

    /**
     * 获取对应问题的答复数
     * @param qid
     * @return
     */
    @Select("select count(*) from cl_answer where question_id = #{qid} and is_deleted = 0 ")
    int getAnswerCount(Integer qid);

    /**
     * 更新对应问题的答复数
     * @param id
     * @param aCount
     */
    @Update("update cl_question set answer_count = #{aCount} where question_id = #{id}")
    void updateAnswerCount(Integer id, Integer aCount);

    /**
     * 改变问题状态
     * @param id
     * @param mark
     */
    @Update("update cl_question set question_mark = #{mark} where question_id = #{id}")
    void changeSolve(Integer id, int mark);
}