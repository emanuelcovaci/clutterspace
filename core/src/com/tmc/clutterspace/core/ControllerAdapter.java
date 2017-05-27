package com.tmc.clutterspace.core;

/**
 * Created by  on 5/27/2017.
 */

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;

import java.util.Locale;

/** A base implementation for {@link ControllerListener}. Subclass this if you are only interested in a few specific events.
 * @author mzechner */
public class ControllerAdapter implements ControllerListener {
    public int indexOf (Controller controller) {
        return Controllers.getControllers().indexOf(controller, true);
    }

    @Override
    public void connected (Controller controller) {
        System.out.println("connected " + controller.getName());
        int i = 0;
        for (Controller c : Controllers.getControllers()) {
            System.out.printf("#" + i++ + ": " + c.getName());
        }
    }

    @Override
    public void disconnected (Controller controller) {
        System.out.println("disconnected " + controller.getName());
        int i = 0;
        for (Controller c : Controllers.getControllers()) {
            System.out.println("#" + i++ + ": " + c.getName());
        }
        if (Controllers.getControllers().size == 0) System.out.println("No controllers attached");
    }

    @Override
    public boolean buttonDown (Controller controller, int buttonIndex) {
        System.out.println("#" + indexOf(controller) + ", button " + Buttons.findByValue(buttonIndex) + " down");
        return false;
    }

    @Override
    public boolean buttonUp (Controller controller, int buttonIndex) {
        System.out.println("#" + indexOf(controller) + ", button " + Buttons.findByValue(buttonIndex) + " up");
        return false;
    }

    @Override
    public boolean axisMoved (Controller controller, int axisIndex, float value) {
        String Valoare = String.format(Locale.ROOT, "%.2f", value);
        if(value>0.5 || value<-0.5)
            System.out.println("#" + indexOf(controller) + ", axis " + Axes.findByValue(axisIndex) + ": " + Valoare);
        return false;
    }

    @Override
    public boolean povMoved (Controller controller, int povIndex, PovDirection value) {
        System.out.println("#" + indexOf(controller) + ", pov " + povIndex + ": " + value);
        return false;
    }

    @Override
    public boolean xSliderMoved (Controller controller, int sliderIndex, boolean value) {
        System.out.println("#" + indexOf(controller) + ", x slider " + Axes.findByValue(sliderIndex) + ": " + value);
        return false;
    }

    @Override
    public boolean ySliderMoved (Controller controller, int sliderIndex, boolean value) {
        System.out.println("#" + indexOf(controller) + ", y slider " + Axes.findByValue(sliderIndex) + ": " + value);
        return false;
    }
    @Override
    public boolean accelerometerMoved (Controller controller, int accelerometerIndex, Vector3 value) {
        return false;
    }
}