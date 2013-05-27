/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopefuture.caipiao.fc3d;

import com.hopefuture.caipiao.ssq.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author wangyanjun
 */
public class Fc3dAnalyse {

    public static final String filePath = "D:\\gitworkspace-nb\\web-platform\\web_hopefuture\\files\\fc3d\\";

    /**
     * 处理数据
     * @throws Exception 
     */
    public static void sourceToDestData() throws Exception {
        StringBuilder sb = new StringBuilder();
        File sourceFile = new File(filePath + "source2.txt");
        File destFile = new File(filePath + "source3.txt");
        if (!destFile.exists()) {
            destFile.createNewFile();
        }
        BufferedReader reader = new BufferedReader(new FileReader(sourceFile));
        String str;
        String[] strArray;
        String[] strArray2;
        while (reader.ready()) {
            str = reader.readLine();
            strArray = str.trim().split(":");
            strArray2 = strArray[1].split(",");
            Arrays.sort(strArray2);
            sb.append(strArray[0]);
            sb.append(":");
            str = Arrays.toString(strArray2);
            sb.append(str.substring(1,8));
            sb.append("\n");
        }
        reader.close();

        FileOutputStream fout = new FileOutputStream(destFile);
        byte[] b = sb.toString().getBytes();
        fout.write(b);
        fout.close();
    }
    
    /**
     * 统计各种情况出现的次数
     *
     * @throws Exception
     */
    public static void statFrequencyData() throws Exception {
        Map<String, Integer> hm = new HashMap<String, Integer>();
        for (int i = 0; i < 1000; i++) {
            hm.put(i < 10 ? "00" + i : i < 100 ? "0" + i : "" + i, 0);
        }

        File sourceFile = new File(filePath + "source.txt");
        BufferedReader reader = new BufferedReader(new FileReader(sourceFile));
        String str;
        while (reader.ready()) {
            str = reader.readLine().split(":")[1];
            hm.put(str, hm.get(str) + 1);
        }

        Set<Map.Entry<String, Integer>> set = hm.entrySet();
        Iterator<Map.Entry<String, Integer>> it = set.iterator();
        Map.Entry<String, Integer> entry;
        StringBuilder sb = new StringBuilder();
        while (it.hasNext()) {
            entry = it.next();
            //System.out.println(entry.getKey());
            //System.out.println(entry.getValue());
            sb.append(entry.getKey()).append(":").append(entry.getValue()).append("\n");
        }
        reader.close();

        File destFile = new File(filePath + "dest1.txt");
        FileOutputStream fout = new FileOutputStream(destFile);
        byte[] b = sb.toString().getBytes();
        fout.write(b);
        fout.close();
    }

    /**
     * 组选3统计，有两个号一样，或3个号都一样的
     *
     * @throws Exception
     */
    public static void statGroup3Data() throws Exception {
        Map<String, Integer> hm = new HashMap<String, Integer>();
        for (int i = 0; i < 10; i++) {
            hm.put("" + i, 0);
        }

        File sourceFile = new File(filePath + "source2.txt");
        BufferedReader reader = new BufferedReader(new FileReader(sourceFile));
        String str;
        String[] arrayStr;
        int counts1 = 0;
        int counts2 = 0;
        while (reader.ready()) {
            str = reader.readLine().split(":")[1];
            arrayStr = str.split(",");
            if (arrayStr[0].equals(arrayStr[1]) || arrayStr[0].equals(arrayStr[2]) || arrayStr[1].equals(arrayStr[2])) {
                counts2++;
                if (arrayStr[0].equals(arrayStr[1])) {
                    hm.put(arrayStr[0], hm.get(arrayStr[0]) + 1);
                } else if (arrayStr[0].equals(arrayStr[2])) {
                    hm.put(arrayStr[0], hm.get(arrayStr[0]) + 1);
                } else if (arrayStr[1].equals(arrayStr[2])) {
                    hm.put(arrayStr[1], hm.get(arrayStr[1]) + 1);
                }
            }
            counts1++;
        }
        System.out.println("总数" + counts1);
        System.out.println("组选总数" + counts2);

        Set<Map.Entry<String, Integer>> set = hm.entrySet();
        Iterator<Map.Entry<String, Integer>> it = set.iterator();
        Map.Entry<String, Integer> entry;
        StringBuilder sb = new StringBuilder();
        while (it.hasNext()) {
            entry = it.next();
            //System.out.println(entry.getKey());
            //System.out.println(entry.getValue());
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
     * 统计组六出现的次数
     *
     * @throws Exception
     */
    public static void statGroup6Data() throws Exception {
        Map<String, Integer> hm = new HashMap<String, Integer>();
        
        File sourceFile = new File(filePath + "source2.txt");
        BufferedReader reader = new BufferedReader(new FileReader(sourceFile));
        String str;
        String[] arrayStr;
        String str2;
        while (reader.ready()) {
            str = reader.readLine().split(":")[1];
            arrayStr = str.split(",");
            Arrays.sort(arrayStr);
            str2 = Arrays.toString(arrayStr);
            if(hm.containsKey(str2)){
                hm.put(str2, hm.get(str2) + 1);
            }else{
                hm.put(str2, 1);
            }
        }

        Set<Map.Entry<String, Integer>> set = hm.entrySet();
        Iterator<Map.Entry<String, Integer>> it = set.iterator();
        Map.Entry<String, Integer> entry;
        StringBuilder sb = new StringBuilder();
        while (it.hasNext()) {
            entry = it.next();
            //System.out.println(entry.getKey());
            //System.out.println(entry.getValue());
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
     * 计算给定数据查看历史数据中是否出现过
     *
     * @throws Exception
     */
    public static void countRepetitive2Data() throws Exception {
        File sourceFile = new File(filePath + "source.txt");
        BufferedReader reader = new BufferedReader(new FileReader(sourceFile));
        String str;
        while (reader.ready()) {
            str = reader.readLine().substring(6, 23);
            Combination combination = new Combination();
            String[] data = {"3", "5", "14", "19", "20", "26", "30"};
            combination.select(6, data);
            List<String> results = combination.getResults();
            for (String string : results) {
                if (str.equals(string)) {
                    System.out.println(str);
                }
            }
        }
    }

    /**
     * 比较两个数是否是一种组合
     * @param num1
     * @param num2
     * @return 
     */
    private static boolean judgeNum(String num1,String num2){
        boolean b = false;
        String[] arrNum1 = num1.split(",");
        String[] arrNum2 = num2.split(",");
        
        Arrays.sort(arrNum1);
        Arrays.sort(arrNum2);

        String compare1 = arrNum1[0] + arrNum1[1] + arrNum1[2];
        String compare2 = arrNum2[0] + arrNum2[1] + arrNum2[2];
        
        if (compare1.equals(compare2)){
            b = true;
        }
        return b;
    }
    
    /**
     * 组合
     */
    private static void combination() throws Exception {
        Map<String, Integer> hm = new HashMap<String, Integer>();
        List<String> list = new ArrayList<String>();
        String str;
        String[] arrayStr;
        for (int i = 0; i < 1000; i++) {
            str = i < 10 ? "00" + i : i < 100 ? "0" + i : "" + i;
            str = str.charAt(0) + "," + str.charAt(1) + "," + str.charAt(2);
            arrayStr = str.split(",");
            Arrays.sort(arrayStr);
            str = Arrays.toString(arrayStr);
            if(!list.contains(str)){
                list.add(str);
            }
        }
        
        StringBuilder sb = new StringBuilder();
        for (String string : list) {
            sb.append(string).append("\n");
        }
        
        File destFile = new File(filePath + "dest4.txt");
        FileOutputStream fout = new FileOutputStream(destFile);
        byte[] b = sb.toString().getBytes();
        fout.write(b);
        fout.close();
    }
    
    /**
     * 组合，去掉重号
     */
    private static void combinationClearDouble() throws Exception {
        Map<String, Integer> hm = new HashMap<String, Integer>();
        List<String> list = new ArrayList<String>();
        String str;
        String[] arrayStr;
        for (int i = 0; i < 1000; i++) {
            str = i < 10 ? "00" + i : i < 100 ? "0" + i : "" + i;
            str = str.charAt(0) + "," + str.charAt(1) + "," + str.charAt(2);
            arrayStr = str.split(",");
            if (arrayStr[0].equals(arrayStr[1]) || arrayStr[0].equals(arrayStr[2]) || arrayStr[1].equals(arrayStr[2])) {
                continue;
            }
            Arrays.sort(arrayStr);
            str = Arrays.toString(arrayStr).replaceAll(" ", "");
            int len = str.length();
            str = str.substring(1, len - 1);
            if(!list.contains(str)){
                list.add(str);
            }
        }
        
        StringBuilder sb = new StringBuilder();
        for (String string : list) {
            sb.append(string).append("\n");
        }
        
        File destFile = new File(filePath + "dest5.txt");
        FileOutputStream fout = new FileOutputStream(destFile);
        byte[] b = sb.toString().getBytes();
        fout.write(b);
        fout.close();
        
    }
    
    /**
     * 给定范围，统计前 historyPeriods 期没有开出的结果再接下来多少期会中奖
     */
    public static void successPeriods() throws Exception {
        List<String> source = new ArrayList<String>();
        File sourceFile = new File(filePath + "dest5.txt");
        BufferedReader reader = new BufferedReader(new FileReader(sourceFile));
        while (reader.ready()) {
            source.add(reader.readLine());
        }
        reader.close();

        sourceFile = new File(filePath + "source3.txt");
        reader = new BufferedReader(new FileReader(sourceFile));
        String[] arrayStr;
        String[] issue;
        List<String[]> data = new ArrayList<String[]>();
        while (reader.ready()) {
            arrayStr = reader.readLine().split(":");
            issue = new String[2];
            issue[0] = arrayStr[0];
            issue[1] = arrayStr[1].replace(" ", "");
            data.add(issue);
        }
        reader.close();
        
        //频率最高的组合
        sourceFile = new File(filePath + "rate.txt");
        reader = new BufferedReader(new FileReader(sourceFile));
        List<String> rate = new ArrayList<String>();
        while (reader.ready()) {
            rate.add(reader.readLine());
        }
        reader.close();
        
        
        int size = data.size();
        File stat1 = new File(filePath + "stat1.txt");
        FileOutputStream fout = new FileOutputStream(stat1);
        int historyPeriods = 120;
        for (int i = 0; i < size - historyPeriods; i++) {
            statSucess(historyPeriods, source, data, rate, i, fout);
        }
        fout.close();
    }
    
    public static void statSucess(int historyPeriods, List<String> source, List<String[]> data, List<String> rate, int startIndex, FileOutputStream fout)  throws Exception {
        //统计
        List<String> source2;//前historyPeriods期没有开出的组合
        List<String> result;
        source2 = new ArrayList<String>();
        cloneList(source, source2);
        int size = data.size();
        for (int i = startIndex; i < startIndex + historyPeriods; i++) {
            if(i >= size){
                break;
            }
            source2.remove(data.get(i)[1]);
        }

        //从没有开出的组合中取频率最大的统计，也可换成最小的
        result = new ArrayList<String>();
        for (String str : rate) {
            if (result.size() >= 5) {//投注数
                break;
            }
            if (source2.contains(str)) {
                result.add(str);
            }
        }
        //计算前historyPeriods期之后那期会开出
        int step = 0;
        for (int i = startIndex + historyPeriods; i < startIndex + historyPeriods + 5000; i++) {
            if(i >= size){
                break;
            }
            step++;
            if (result.contains(data.get(i)[1])) {
                byte[] b = (data.get(i)[0] + ":" + data.get(i)[1] + "        " + step + "\n").toString().getBytes();
                fout.write(b);
                //System.out.println("sucess,投注期数:" + step);
                break;
            }
        }    
    }
    
    
    public static void cloneList(List<String> source, List<String> dest){
        for (String str : source) {
            dest.add(str);
        }
    }
    
    public static void main(String... args) throws Exception {

        //combinationClearDouble();
        
        successPeriods();
    }
}
