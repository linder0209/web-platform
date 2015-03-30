/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopefuture.caipiao.fc3d;

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
     *
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
            sb.append(str.substring(1, 8));
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

        File sourceFile = new File(filePath + "2013.txt");
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
            if (repeat(arrayStr)) {
                continue;
            }
            Arrays.sort(arrayStr);
            str2 = Arrays.toString(arrayStr);
            if (hm.containsKey(str2)) {
                hm.put(str2, hm.get(str2) + 1);
            } else {
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
     * 比较两个数是否是一种组合
     *
     * @param num1
     * @param num2
     * @return
     */
    private static boolean judgeNum(String num1, String num2) {
        boolean b = false;
        String[] arrNum1 = num1.split(",");
        String[] arrNum2 = num2.split(",");

        Arrays.sort(arrNum1);
        Arrays.sort(arrNum2);

        String compare1 = arrNum1[0] + arrNum1[1] + arrNum1[2];
        String compare2 = arrNum2[0] + arrNum2[1] + arrNum2[2];

        if (compare1.equals(compare2)) {
            b = true;
        }
        return b;
    }

    /**
     * 生成所有排列3组合
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
            if (!list.contains(str)) {
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
     * 从所有排列3组合中去掉重号
     */
    private static void combinationClearDouble() throws Exception {
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
            if (!list.contains(str)) {
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
     * 给定范围，统计前 historyPeriods 期没有开出的结果再接下来多少期会中奖 比如 3,5,7 在前100期没有出现过，那么接下来多少期会开出，统计结果是23
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

    public static void statSucess(int historyPeriods, List<String> source, List<String[]> data, List<String> rate, int startIndex, FileOutputStream fout) throws Exception {
        //统计
        List<String> source2;//前historyPeriods期没有开出的组合
        List<String> result;
        source2 = new ArrayList<String>();
        cloneList(source, source2);
        int size = data.size();
        for (int i = startIndex; i < startIndex + historyPeriods; i++) {
            if (i >= size) {
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
            if (i >= size) {
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

    public static void cloneList(List<String> source, List<String> dest) {
        for (String str : source) {
            dest.add(str);
        }
    }

    /**
     * 判断是否有重复的值
     *
     * @param group
     * @return
     */
    private static boolean repeat(String[] group) {
        if (group[0].equals(group[1]) || group[0].equals(group[2]) || group[1].equals(group[2])) {
            return true;
        }
        return false;
    }

    /**
     * 统计本期中奖号码（只统计组六），是否有上期开出的结果 统计结果为1918，总数为4072，几率为：47%
     *
     */
    public static void statRepeatLastPeriod() throws Exception {
        File sourceFile = new File(filePath + "source2.txt");
        BufferedReader reader = new BufferedReader(new FileReader(sourceFile));
        String[] arrayStr;
        String[] priorPeriod = null;
        boolean find;
        int results = 0;
        while (reader.ready()) {
            arrayStr = reader.readLine().split(":")[1].split(",");
            if (arrayStr[0].equals(arrayStr[1]) || arrayStr[0].equals(arrayStr[2]) || arrayStr[1].equals(arrayStr[2])) {
                priorPeriod = arrayStr;
                continue;
            }
            if (priorPeriod != null) {
                for (int i = 0; i < 3; i++) {
                    find = findInArray(priorPeriod, arrayStr[i]);
                    if (find) {
                        results++;
                        break;
                    }
                }
            }
            priorPeriod = arrayStr;
        }
        reader.close();
        System.out.println("本期中奖号码在上期中出现的次数:" + results);
    }

    /**
     * 统计本期中奖号码（只统计组六），没有上期开出的结果（上期必须是组六） 统计结果为1481，总数为4072 - 1127 = 2945，几率为：50%
     *
     */
    public static void statNoRepeatLastPeriod() throws Exception {
        File sourceFile = new File(filePath + "2013.txt");
        BufferedReader reader = new BufferedReader(new FileReader(sourceFile));
        String[] arrayStr;
        String[] priorPeriod = null;
        boolean find = false;
        int results = 0;
        while (reader.ready()) {
            arrayStr = reader.readLine().split(":")[1].split(",");
            if (arrayStr[0].equals(arrayStr[1]) || arrayStr[0].equals(arrayStr[2]) || arrayStr[1].equals(arrayStr[2])) {
                priorPeriod = arrayStr;
                continue;
            }
            if (priorPeriod != null && !(priorPeriod[0].equals(priorPeriod[1]) || priorPeriod[0].equals(priorPeriod[2]) || priorPeriod[1].equals(priorPeriod[2]))) {
                for (int i = 0; i < 3; i++) {
                    find = findInArray(priorPeriod, arrayStr[i]);
                }
                if (!find) {
                    results++;
                }
            }
            priorPeriod = arrayStr;
        }
        reader.close();
        System.out.println("本期中奖号码在上期中没有出现的次数:" + results);
    }

    /**
     * 统计本期中奖号码（只统计组六），没有上期开出的结果（上期必须是组六），并且除去前n期开出的结果（例如100） 统计结果为1481，总数为4072 - 1127 = 2945，几率为：50%
     *
     */
    public static void statNoRepeatLastPeriodDeductingHistoryPeriods() throws Exception {
        File sourceFile = new File(filePath + "source3.txt");
        BufferedReader reader = new BufferedReader(new FileReader(sourceFile));
        List<String> data = new ArrayList<String>();
        while (reader.ready()) {
            data.add(reader.readLine().split(":")[1]);
        }
        reader.close();

        int invest = 0;//投资
        int retribution = 0;//回报
        int size = data.size();
        int historyPeriods = 100;
        String[] arrayStr;
        for (int i = historyPeriods; i < size - historyPeriods; i++) {
            //上一期
            String prior = data.get(historyPeriods - 1);
            arrayStr = prior.split(",");
            //如果上一期不是组选6
            if (arrayStr[0].equals(arrayStr[1]) || arrayStr[0].equals(arrayStr[2]) || arrayStr[1].equals(arrayStr[2])) {
                continue;
            }
            //计算除去上一期后的所有组合
            List<String> newCombination = combinationDeductingPriorPeriod(prior.split(","));
            for (int j = i - historyPeriods; j < i; j++) {
                String _str = data.get(j);
                if (newCombination.contains(_str)) {
                    newCombination.remove(_str);
                }
            }
            System.out.println("投入： " + newCombination.size());
            invest += newCombination.size() * 2;
            
            //是否成功
            for (String str : newCombination) {
                if(str.equals(data.get(i))){
                    retribution += 160;
                    break;
                }
            }
        }
        
        System.out.println("投入： " + invest);
        System.out.println("回报： " + retribution);

    }

    private static boolean findInArray(String[] array, String value) {
        for (int i = 0; i < 3; i++) {
            if (value.equals(array[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * 生成除去上一期开出结果的三个数字后的组合
     *
     * @return
     */
    private static List<String> combinationDeductingPriorPeriod(String[] priorPeriod) {
        List<String> list = new ArrayList<String>();
        int[] arrayInt = new int[7];
        int step = 0;
        for (int i = 0; i < 10; i++) {
            if (i == Integer.parseInt(priorPeriod[0]) || i == Integer.parseInt(priorPeriod[1]) || i == Integer.parseInt(priorPeriod[2])) {
                continue;
            }
            arrayInt[step++] = i;
        }
        for (int i = 0; i < 7; i++) {
            for (int j = i + 1; j < 7; j++) {
                for (int k = j + 1; k < 7; k++) {
                    list.add(arrayInt[i] + "," + arrayInt[j] + "," + arrayInt[k]);
                }
            }
        }
        return list;
    }

    public static void main(String... args) throws Exception {
        //combinationClearDouble();
        //statGroup6Data();
        //successPeriods();
        //statRepeatLastPeriod();
        //statGroup3Data();
        //statNoRepeatLastPeriod();
//        String[] priorPeriod = {"4", "7", "8"};
//        List<String> list = combinationDeductingPriorPeriod(priorPeriod);
//        for (String str : list) {
//            System.out.println(str);
//        }
        
        statNoRepeatLastPeriodDeductingHistoryPeriods();
    }
}
