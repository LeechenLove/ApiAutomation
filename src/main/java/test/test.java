package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Lulu
 * @Description: TODO
 * @DateTime: 2022/8/12 21:09
 **/
public class test {
    public static void main(String[] args) {
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("test1", "name1");
        map.put("test2", "name2");
        list.add(map);
        System.out.println(list);
        String str = "tes;t";
        System.out.println(str.split(";")[1]);
    }
}
