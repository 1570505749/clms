package com.hnxx.zy.clms.core.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class College implements Serializable {

    private Integer collegeId;

    @Excel(name = "学院名称", orderNum = "1")
    private String collegeName;

    private Integer collegeStates;

}
