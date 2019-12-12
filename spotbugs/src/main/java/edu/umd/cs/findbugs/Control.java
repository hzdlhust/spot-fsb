package edu.umd.cs.findbugs;

public class Control{
    private static Control control = new Control();
    private boolean pause = false;
    private boolean exit = false;
    private Control(){

    }

    public static Control getInstance(){
        return control;
    }

    public boolean isPaused(){
        return pause;
    }
    public void pause(){
        pause = true;
    }
    public void conti(){
        pause = false;
        notifyAll();
    }

    public boolean isToExit(){
        return exit;
    }

    public void toExit(){
        exit = true;
        notifyAll();
    }
}
