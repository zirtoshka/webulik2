package validator;

public class Point {
    //todo chto za ebola
    double x;
    double y;
    double r;
    boolean isKill;
    String scriptTime;
    String currentTime;

    public Point(double x, double y, double r, boolean isKill, String scriptTime, String currentTime) {
        this.x = x;
        this.y = y;
        this.currentTime=currentTime;
        this.r=r;
        this.isKill=isKill;
        this.scriptTime=scriptTime;
    }

}
