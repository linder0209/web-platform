/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopefuture.caipiao.ssq;

import java.util.List;

/**
 *
 * @author wangyanjun
 */
public class Region {

    private String[] region;
    private List<String> combination;

    /**
     * @return the region
     */
    public String[] getRegion() {
        return region;
    }

    /**
     * @param region the region to set
     */
    public void setRegion(String[] region) {
        this.region = region;
    }

    /**
     * @return the combination
     */
    public List<String> getCombination() {
        return combination;
    }

    /**
     * @param combination the combination to set
     */
    public void setCombination(List<String> combination) {
        this.combination = combination;
    }

    public String countRegion(int[] num) {
        String[] reg = this.getRegion();
        int len = reg.length;
        int numLen = num.length;
        String[] regSplit;
        String regions = "";
        int step = 0;
        for (int i = 0; i < len; i++) {
            regSplit = reg[i].split("-");
            int temp = Integer.parseInt(regSplit[1]);
            for (int j = step; j < numLen; j++) {
                if (j == numLen - 1) {
                    if (num[j] >= Integer.parseInt(regSplit[0]) && num[j] <= temp) {
                        regions += reg[i];
                        step = j + 1;
                        break;
                    }
                } else {
                    if (num[j] <= temp && num[j + 1] > temp) {
                        regions += reg[i];
                        step = j + 1;
                        break;
                    }
                }
            }
        }

        //或者
//        for (int i = 0; i < len; i++) {
//            regSplit = reg[i].split("-");
//            int temp0 = Integer.parseInt(regSplit[0]);
//            int temp1 = Integer.parseInt(regSplit[1]);
//            for (int j = step; j < numLen; j++) {
//                if (num[j] <= temp0 && num[j] >= temp1 ) {
//                    hm.put(reg[i], true);
//                    step = j + 1;
//                    break;
//                }
//            }
//        }
        
        return regions;
    }
}