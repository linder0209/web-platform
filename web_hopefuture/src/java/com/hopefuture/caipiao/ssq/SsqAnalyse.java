/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopefuture.caipiao.ssq;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.jasper.tagplugins.jstl.core.ForEach;

/**
 *
 * @author wangyanjun
 */
public class SsqAnalyse {

    public static final String filePath = "D:\\gitworkspace-nb\\web-platform\\web_hopefuture\\files\\";
    
    /**
     * 处理数据
     * @throws Exception 
     */
    public static void sourceToDestData() throws Exception {
        StringBuilder sb = new StringBuilder();
        File sourceFile = new File(filePath + "source.txt");
        File destFile = new File(filePath + "dest.txt");
        if (!destFile.exists()) {
            destFile.createNewFile();
        }
        BufferedReader reader = new BufferedReader(new FileReader(sourceFile));
        String str;
        String[] strArray;
        while (reader.ready()) {
            str = reader.readLine();
            strArray = str.trim().split(" ");
            str = "";
            for (int i = 0, len = strArray.length; i < len; i++) {
                str += strArray[i] + ",";
            }
            str = str.substring(0, str.length() - 1);
            sb.append(str);
            sb.append("\n");
        }
        reader.close();

        FileOutputStream fout = new FileOutputStream(destFile);
        byte[] b = sb.toString().getBytes();
        fout.write(b);
        fout.close();
    }

    /**
     * 统计红球
     * @throws Exception 
     */
    public static void statRedData() throws Exception {
        Map<String, Integer> hm = new HashMap<String, Integer>();
        for (int i = 1; i <= 33; i++) {
            hm.put(i < 10 ? "0" + i : "" + i, 0);
        }

        File sourceFile = new File(filePath + "source.txt");
        BufferedReader reader = new BufferedReader(new FileReader(sourceFile));
        String str;
        String[] strArray;
        while (reader.ready()) {
            str = reader.readLine().substring(6, 23);
            strArray = str.trim().split(",");
            for (int i = 0, len = strArray.length; i < len; i++) {
                hm.put(strArray[i], hm.get(strArray[i]) + 1);
            }
        }
        
        Set<Map.Entry<String, Integer>> set = hm.entrySet();
        Iterator<Map.Entry<String, Integer>> it =  set.iterator();
        Map.Entry<String, Integer> entry;
        StringBuilder sb = new StringBuilder();
        while(it.hasNext()){
            entry = it.next();
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
            sb.append(entry.getKey()).append(":").append(entry.getValue()).append("\n");
        }
        reader.close();

        File destFile = new File(filePath + "dest2.txt");
        FileOutputStream fout = new FileOutputStream(destFile);
        byte[] b = sb.toString().getBytes();
        fout.write(b);
        fout.close();
    }
    
    /**
     * 统计蓝球
     * @throws Exception 
     */
    public static void statBlueData() throws Exception {
        Map<String, Integer> hm = new HashMap<String, Integer>();
        for (int i = 1; i <= 16; i++) {
            hm.put("" + i, 0);
        }

        File sourceFile = new File(filePath + "source.txt");
        BufferedReader reader = new BufferedReader(new FileReader(sourceFile));
        String str;
        String[] strArray;
        while (reader.ready()) {
            str = reader.readLine().split(";")[1];
            hm.put(str, hm.get(str) + 1);
        }
        
        Set<Map.Entry<String, Integer>> set = hm.entrySet();
        Iterator<Map.Entry<String, Integer>> it =  set.iterator();
        Map.Entry<String, Integer> entry;
        StringBuilder sb = new StringBuilder();
        while(it.hasNext()){
            entry = it.next();
            System.out.print(entry.getKey() + " : ");
            System.out.println(entry.getValue());
            sb.append(entry.getKey()).append(":").append(entry.getValue()).append("\n");
        }
        reader.close();

        File destFile = new File(filePath + "destBlue.txt");
        FileOutputStream fout = new FileOutputStream(destFile);
        byte[] b = sb.toString().getBytes();
        fout.write(b);
        fout.close();
    }

    /**
     * 统计区间
     * @throws Exception 
     */
    public static void statRegionData() throws Exception {
        File sourceFile = new File(filePath + "source.txt");
        BufferedReader reader = new BufferedReader(new FileReader(sourceFile));
        String str;
        String[] strArray;
        Map<String, Integer> hm = new HashMap<String, Integer>();
        
        Region region = new Region();
        //region.setRegion(new String[]{"1-11","12-22","23-33"});
        //region.setRegion(new String[]{"1-10","11-33"});
        region.setRegion(new String[]{"1-2","3-30","31-33"});
        int[] num = new int[6];
        String _temp;
        
        while (reader.ready()) {
            str = reader.readLine().substring(6, 23);
            strArray = str.trim().split(",");
            for(int i = 0;i<6;i++){
               num[i] = Integer.parseInt(strArray[i]);
            }
            _temp = region.countRegion(num);
            if(hm.containsKey(_temp)){
                hm.put(_temp, hm.get(_temp) + 1);
            }else{
                hm.put(_temp, 1);
            }
        }
        
        Set<Map.Entry<String, Integer>> set = hm.entrySet();
        Iterator<Map.Entry<String, Integer>> it =  set.iterator();
        Map.Entry<String, Integer> entry;
        StringBuilder sb = new StringBuilder();
        while(it.hasNext()){
            entry = it.next();
            sb.append(entry.getKey()).append(":").append(entry.getValue()).append("\n");
        }
        reader.close();

        File destFile = new File(filePath + "dest3.txt");
        FileOutputStream fout = new FileOutputStream(destFile);
        byte[] b = sb.toString().getBytes();
        fout.write(b);
        fout.close();
    }
    
    /**
     * 连号统计 65%有连号
     * @throws Exception 
     */
    public static void statConsecutiveData() throws Exception {
        File sourceFile = new File(filePath + "source.txt");
        BufferedReader reader = new BufferedReader(new FileReader(sourceFile));
        String str;
        String[] strArray;
        int count = 0;
        while (reader.ready()) {
            str = reader.readLine().substring(6, 23);
            strArray = str.trim().split(",");
            for (int i = 0; i < 5; i++) {
                if (Integer.parseInt(strArray[i]) + 1 == Integer.parseInt(strArray[i + 1])) {
                    count++;
                    break;
                }
            }
        }
        System.out.println(count);
    }
    
    /**
     * 计算两连号最多的组合 统计结果前三位是：7和8、19,20和 26,27
     * 计算隔一个号最多的组合前四名  03,05   18,20 12,14 30,32
       计算隔两个个号最多的组合前四名  07,10  13,16  01,04  03,06

     * @throws Exception 
     */
    public static void statConsecutive2Data() throws Exception {
        File sourceFile = new File(filePath + "source.txt");
        BufferedReader reader = new BufferedReader(new FileReader(sourceFile));
        String str;
        String[] strArray;
        Map<String, Integer> hm = new HashMap<String, Integer>();
        int count = 0;
        String key;
        while (reader.ready()) {
            str = reader.readLine().substring(6, 23);
            strArray = str.trim().split(",");
            for (int i = 0; i < 5; i++) {
                if (Integer.parseInt(strArray[i]) + 3 == Integer.parseInt(strArray[i + 1])) {
                    key = strArray[i] + "," + strArray[i + 1];
                    if(hm.containsKey(key)){
                        hm.put(key, hm.get(key) + 1);
                    }else{
                        hm.put(key, 1);
                    }
                }
            }
        }
        Set<Map.Entry<String, Integer>> set = hm.entrySet();
        Iterator<Map.Entry<String, Integer>> it =  set.iterator();
        Map.Entry<String, Integer> entry;
        StringBuilder sb = new StringBuilder();
        while(it.hasNext()){
            entry = it.next();
            sb.append(entry.getKey()).append(":").append(entry.getValue()).append("\n");
        }
        reader.close();

        File destFile = new File(filePath + "dest4.txt");
        FileOutputStream fout = new FileOutputStream(destFile);
        byte[] b = sb.toString().getBytes();
        fout.write(b);
        fout.close();
    }
    
    /**
     * 计算历史数据是否有重复开出一样的号
     * @throws Exception 
     */
    public static void countRepetitiveData() throws Exception {
        File sourceFile = new File(filePath + "source.txt");
        BufferedReader reader = new BufferedReader(new FileReader(sourceFile));
        String str;
        Map<String, Boolean> hm = new HashMap<String, Boolean>();
        while (reader.ready()) {
            str = reader.readLine().substring(6, 23);
            if (hm.containsKey(str)) {
                System.out.println(str);
            } else {
                hm.put(str, true);
            }
        }
    }
    
    /**
     * 计算给定数据查看历史数据中是否出现过
     * @throws Exception 
     */
     public static void countRepetitive2Data() throws Exception {
        File sourceFile = new File(filePath + "source.txt");
        BufferedReader reader = new BufferedReader(new FileReader(sourceFile));
        String str;
        while (reader.ready()) {
            str = reader.readLine().substring(6, 23);
            Combination combination = new Combination();
            String[] data = {"3","5","14","19","20","26","30"};
            combination.select(6,data);
            List<String> results = combination.getResults();
            for (String string : results) {
                if (str.equals(string)) {
                    System.out.println(str);
                }
            }
        }
    }
     
    public static void main(String... args) throws Exception {
//        try {
//            sourceToDestData();
//        } catch (Exception ex) {
//            Logger.getLogger(SsqAnalyse.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        //statRedData();
        statBlueData();
        //statRegionData();
        //statConsecutiveData();
        //statConsecutive2Data();
        
        //countRepetitiveData();
        
        //countRepetitive2Data();
    }
    
}
