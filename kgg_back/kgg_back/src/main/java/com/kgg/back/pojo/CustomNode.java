package com.kgg.back.pojo;

import java.util.List;
import java.util.Map;

public class CustomNode {
    private String id;
    private List<Map<String,Object>> prop;
    private int group;

    public CustomNode(String id, List<Map<String, Object>> prop, int group) {
        this.id = id;
        this.prop = prop;
        this.group = group;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public List<Map<String, Object>> getProp() {
        return prop;
    }

    public void setProp(List<Map<String, Object>> prop) {
        this.prop = prop;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }
}
