package com.example.puicbr.fertilizerforlatex.helper;

import com.example.puicbr.fertilizerforlatex.model.Formula;

import java.util.List;

/**
 * Created by Folder on 16-Nov-16.
 */
public class FormulaHelper {

    /**
     * Get สูตรที่ต้องใช้ในการให้ปุ๋ยถัดไปโดยอิงจากอายุต้นไม้ปัจจุบัน
     * return null ถ้าไม่มีรอบการให้ปุ๋ยถัดไปแล้ว
     * @param formulaFullList
     * @param currentTreeAgeInMonth
     * @return สูตรการให้ปุ๋ยรอบถัดไป, return null ถ้าไม่มีรอบการให้ปุ๋ยถัดไปแล้ว
     */
    public static Formula getNextFertilizingFormulaByCurrentTreeAge(List<Formula> formulaFullList, int currentTreeAgeInMonth){
        Formula next = null;
        for(Formula f : formulaFullList){
            if(f.tree_age > currentTreeAgeInMonth){
                next = f;
            }
        }
        return next;
    }
}
