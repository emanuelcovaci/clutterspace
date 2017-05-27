package com.tmc.clutterspace.core;

/**
 * Created by Vlad Fara on 5/27/2017.
 */
public enum Axes {

    R_Axis_Y(5),
    R_Axis_X(2),
    L_Axis_Y(1),
    L_Axis_X(0),
    L2(3),
    R2(4),
    ;

    private final int value;

    private Axes(int value){
        this.value = value;
    }

    public static Axes findByValue(int value){
        for(Axes constants : values()){
            if(constants.value==value){
                return constants;
            }
        }
        return null;
    }

    public int getValue() {
        return value;
    }
}
