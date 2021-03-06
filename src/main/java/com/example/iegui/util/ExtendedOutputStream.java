package com.example.iegui.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.function.Function;

/**
 * This Stream replaces the standard outputstream which writes to the consol on application startup
 * It makes it possible to add Functions which can be invoked everytime a System.out operation is performed.
 * It is used in the loading-view to show the console output in a TextArea.
 */
public class ExtendedOutputStream extends OutputStream {
    private ArrayList<Function<Integer,Object>> streams = new ArrayList<>();
    private PrintStream origout;

    public ExtendedOutputStream(){
        origout = System.out;
    }

    public void addStream(Function stream){
        streams.add(stream);
    }

    public void rmStream(Function stream){
        streams.remove(stream);
    }

    public void clearStreams(){
        streams.clear();
    }
    @Override
    public void write(int b) throws IOException {
        origout.write(b);
        for (Function i : streams) {
            try {
                i.apply(b);
            } catch (Exception ignore) {}
        }
    }
}
