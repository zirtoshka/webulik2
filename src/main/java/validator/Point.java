package validator;

import java.io.Serializable;
import java.math.BigDecimal;

public class Point implements Serializable {
    private final BigDecimal x;
    private final BigDecimal y;
    private final BigDecimal r;
    boolean isKill;
    String scriptTime;
    String now;

    public Point(BigDecimal x, BigDecimal y, BigDecimal r, boolean isKill, String scriptTime, String now) {
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

    public BigDecimal getR() {
        return r;
    }

    public BigDecimal getX() {
        return x;
    }

    public BigDecimal getY() {
        return y;
    }

    public String getScriptTime() {
        return scriptTime;
    }
    public boolean getIsKill(){
        return isKill;
    }
}
