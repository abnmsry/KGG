package com.kgg.back.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Demo {
    public static void main(String[] args) {
        List<Map<String,Object>> list = new ArrayList<>();

        Map<String,Object> map = new HashMap<>();
        map.put("age",25);
        map.put("className","一年级");
        map.put("mobile","123456");
        map.put("money",14.4D);
        list.add(map);

        System.out.println(list);

    }
}
