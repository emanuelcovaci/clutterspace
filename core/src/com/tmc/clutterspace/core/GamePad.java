package com.tmc.clutterspace.core;


import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;


public class GamePad {
    private static Controller controllerut;
    protected static Controller test(){
        for( Controller controller: Controllers.getControllers()) {
            System.out.println(controller.getName());
            controllerut = controller;
        }
        return controllerut;
    }
}