package com.hopefuture.caipiao.ssq;

import java.util.ArrayList;
import java.util.List;

/**
 * 组合算法
 *
 * @author wangyanjun
 */
public class Combination {
    
    private List<String> results = new ArrayList<String> ();
    public static void main(String[] args) {
        Combination combination = new Combination();
        String[] data = {"03","05","14","19","20","26","30"};
        combination.select(6,data);
        
        List<String> test = combination.getResults();
        for (String string : test) {
            System.out.println(string);
        }
    }

    public void select(int selectNum, String[] data) {
        String[] result = new String[selectNum];
        this.subselect(0, 1, result, selectNum, data);
    }

    private void subselect(int head, int index, String[] result, int selectNum, String[] data) {
        for (int i = head; i < data.length + index - selectNum; i++) {
            if (index < selectNum) {
                result[index - 1] = data[i];
                this.subselect(i + 1, index + 1, result, selectNum, data);
            } else if (index == selectNum) {
                result[index - 1] = data[i];
                String temp = "";
                for(int j = 0;j<result.length;j++){
                    temp += result[j] + ",";
                }
                this.putResults(temp.substring(0,temp.length() - 1));
                this.subselect(i + 1, index + 1, result, selectNum,data);
            } else {
                return;
            }

        }
    }

    /**
     * @return the results
     */
    public List<String> getResults() {
        return results;
    }

    /**
     * @param results the results to set
     */
    public void setResults(List<String> results) {
        this.results = results;
    }
    
    public void putResults(String str){
        this.results.add(str);
    }
    
    
}