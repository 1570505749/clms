package com.hnxx.zy.clms.core.service;

import com.hnxx.zy.clms.common.utils.Page;
import com.hnxx.zy.clms.core.entity.Report;
import com.hnxx.zy.clms.core.entity.ReportMarking;
import com.hnxx.zy.clms.core.entity.ReportStatistics;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: clms
 * @description: 报告批阅Service实现类
 * @author: nile
 * @create: 2020-03-24 16:15
 **/
public interface ReportMarkingService {

    /**
     * 管理员获取所有报告批阅信息
     * @param page
     * @return
     */
    List<ReportMarking> getAllMarking(Page<ReportMarking> page);

    /**
     * 管理员根据id清空批阅数据
     */
    void deleteAdminById(Integer markingId);


    /**
     * 返回未批阅的报告
     * @param page
     * @return
     */
    List<Report> getNotMarkingReport(Page<Report> page);

    /**
     * 组长提交批阅报告
     * @param reportMarkings
     */
    void setGroupMarking(List<ReportMarking> reportMarkings);

    /**
     * 班长提交批阅报告
     * @param reportMarkings
     */
    void setClassesMarking(List<ReportMarking> reportMarkings);

    /**
     * 教师提交批阅报告
     * @param reportMarkings
     */
    void setTeacherMarking(List<ReportMarking> reportMarkings);

    /**
     * 获取批阅信息
     * @param page
     * @return
     */
    List<Report> getMarkingReport(Page<Report> page);

    /**
     ** 学生通过报告ID查询报告批阅
     * @param id
     * @return
     */
    ReportMarking getUserMarkingById(Integer id);

    /**
     * 获取所有报告批阅平均分数
     * @param page
     * @return
     */
    ReportStatistics getAvgReportScore(Page<ReportStatistics> page);

    /**
     * 获取用户报告批阅平均分数
     * @param page
     * @return
     */
    ReportStatistics getReportScore(Page<ReportStatistics> page);
}
