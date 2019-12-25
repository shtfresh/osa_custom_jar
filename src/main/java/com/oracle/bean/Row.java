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
public class Row {

   private String key;
   @JSONField(name ="Cell")
   private List<Cell> Cell;
   public void setKey(String key) {
        this.key = key;
    }
    public String getKey() {
        return key;
    }

   public void setCell(List<Cell> Cell) {
        this.Cell = Cell;
    }
    public List<Cell> getCell() {
        return Cell;
    }

}