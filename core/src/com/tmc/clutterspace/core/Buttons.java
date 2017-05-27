package com.tmc.clutterspace.core;

/**
 * Created by Vlad Fara on 5/27/2017.
 */
public enum Buttons {

    SQUARE_KEY(0),
    X_KEY(1),
    CIRCLE_KEY(2),
    TRIANGLE_KEY(3),
    L1_KEY(4),
    R1_KEY(5),
    L2_KEY(6),
    R2_KEY(7),
    SHARE_KEY(8),
    OPTIONS_KEY(9),
    L_JOY_KEY(10),
    R_JOY_KEY(11),
    PS_KEY(12),
    TOUCH_KEY(13);

    private final int value;

    private Buttons(int value){
        this.value = value;
    }

    public static Buttons findByValue(int value){
        for(Buttons constants : values()){
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
