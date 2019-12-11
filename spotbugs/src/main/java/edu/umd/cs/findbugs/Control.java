package edu.umd.cs.findbugs;


import java.util.Timer;
import java.util.TimerTask;

public class Control{
    private static volatile boolean exit = false;

    public static void exitProgram(){
        exit = true;
    }

    public void check(){
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if(exit) System.exit(0);
            }
        }, 1000,2000);
    }

}
