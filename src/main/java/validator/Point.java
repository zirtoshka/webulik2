package validator;

public class Point {
    //todo chto za ebola
    double x;
    double y;
    double r;
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

}
