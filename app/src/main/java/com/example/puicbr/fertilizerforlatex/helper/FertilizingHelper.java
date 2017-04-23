package com.example.puicbr.fertilizerforlatex.helper;

/**
 * Created by PuiCBR on 4/23/2017.
 */

public class FertilizingHelper {
    public static int calTreeAmountFromArea(int rai)
    {
        if (rai<0){
            return 0;
        }
      int treeAmt = rai*76;

        return treeAmt;
    }
}
