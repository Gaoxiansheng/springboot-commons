package com.project.demo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.crudcommons.api.base.entity.BaseEntity;

import lombok.Data;

@Table(name = "project_demo")
@Data
public class Demo extends BaseEntity{
	
    @Column(name = "number")
    private Integer number;
    
    @Column(name = "column_time")
    private Date columnTime;
    
    @Column(name = "demo_column")
    private String demoColumn;

}
