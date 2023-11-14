package validator;

import java.io.Serializable;

public class Point implements Serializable {
    private final double x;
    private final double y;
    private final double r;
    boolean isKill;
    String scriptTime;
    String now;

    public Point(double x, double y, double r, boolean isKill, String scriptTime, String now) {
        this.x = x;
        this.y = y;
        this.now = now;
        this.r=r;
        this.isKill=isKill;
        this.scriptTime=scriptTime;
    }
    public String getTime (){
        return now;
    }

    public double getR() {
        return r;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public String getScriptTime() {
        return scriptTime;
    }
    public boolean getIsKill(){
        return isKill;
    }
}
