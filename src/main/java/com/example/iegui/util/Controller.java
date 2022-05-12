package com.example.iegui.util;

/**
 * The Controller class is extended by every JavaFX controller. It is an utility class which contains the context.
 */
public class Controller {
    protected Context context;

    /**
     * After creation of a Controller, the setContext method should be called to pass the context of the parent window.
     * @param context An Object of Type Context which contains shared data
     */
    public void setContext(Context context) {
        this.context = context;
    }
}
