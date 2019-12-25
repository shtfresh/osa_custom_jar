package com.oracle.bean;

/**
 * Copyright 2019 bejson.com 
 */

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
* Auto-generated: 2019-12-03 18:4:14
*
* @author bejson.com (i@bejson.com)
* @website http://www.bejson.com/java2pojo/
*/
public class JsonRootBean {

	@JSONField(name ="Row")
   private List<Row> Row;
   public void setRow(List<Row> Row) {
        this.Row = Row;
    }
    public List<Row> getRow() {
        return Row;
    }

}