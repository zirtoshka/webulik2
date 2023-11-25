package validator;

import exceptions.WrongDataException;

import java.math.BigDecimal;

public class DataChecker {

    public DataChecker() {
    }

    final static BigDecimal xMin = BigDecimal.valueOf(-5);
    final static BigDecimal xMax = BigDecimal.valueOf(3);
    final static BigDecimal yMin = BigDecimal.valueOf(-5);
    final static BigDecimal yMax = BigDecimal.valueOf(3);
    final static BigDecimal rMin = BigDecimal.valueOf(1);
    final static BigDecimal rMax = BigDecimal.valueOf(5);
    final static BigDecimal graphBoundary = BigDecimal.valueOf(5.5);

    public boolean checkXYR(BigDecimal x, BigDecimal y, BigDecimal r) throws WrongDataException {
        if (checkX(x)) {
            throw new WrongDataException("x = " + x + " is invalid value");
        }
        if (checkY(y)) throw new WrongDataException("y = " + y + " is invalid value");
        if (checkR(r)) throw new WrongDataException("r = " + r + " is invalid value");
        return true;

    }

    private boolean checkX(BigDecimal x) {
        return x.compareTo(xMax) > 0 || x.compareTo(xMin) < 0;
//                x > xMax || x < xMin;
    }

    private boolean checkY(BigDecimal y) {
        return y.compareTo(yMax)>=0 || y.compareTo(yMin)<=0;
//                y >= yMax || y <= yMin;
    }

    private boolean checkR(BigDecimal r) {
        return r.compareTo(rMax) > 0 || r.compareTo(rMin) < 0;
//                r > rMax || r < rMin;
    }

    public boolean checkKill(BigDecimal x, BigDecimal y, BigDecimal r) {
        return (x.compareTo(BigDecimal.ZERO) <=0 && x.compareTo(r.negate()) >=0 &&
                y.compareTo(BigDecimal.ZERO) >=0  && y.compareTo(r.divide(BigDecimal.valueOf(2))) <=0 ) ||
                (x.compareTo(BigDecimal.ZERO) <=0 && x.compareTo(r.negate().divide(BigDecimal.valueOf(2))) >=0 &&
                        y.compareTo(BigDecimal.ZERO) <=0 && y.compareTo(r.negate().divide(BigDecimal.valueOf(2))) >=0 &&
                        y.compareTo(x.negate().add(r.negate().divide(BigDecimal.valueOf(2))))<=0)||
                (x.compareTo(BigDecimal.ZERO) >=0 && y.compareTo(BigDecimal.ZERO) <=0 &&
                        x.pow(2).add(y.pow(2)).compareTo(r.pow(2).divide(BigDecimal.valueOf(4)))<=0);
//todo cringe
//                ((x <= 0) && (x >= -r) && (y >= 0) && (y <= r / 2))
//                || ((x <= 0) && (x >= -r / 2) && (y <= 0) && (y >= -r / 2) && (y <= -x - r / 2))
//                || ((x >= 0) && (y <= 0) && ((x * x + y * y) <= (r * r / 4)));
    }

    public boolean checkAreaForGraph(BigDecimal x, BigDecimal y, BigDecimal r) {
        return graphBoundary.negate().compareTo(x)<=0 &&  x.compareTo(graphBoundary)<=0 &&
                graphBoundary.negate().compareTo(y)<=0 && y.compareTo(graphBoundary)<=0 &&
                rMin.compareTo(r)<=0 && r.compareTo(rMax)<=0;

//                -1 * graphBoundary <= x && x <= graphBoundary && -1 * graphBoundary <= y && y <= graphBoundary && rMin <= r && r <= rMax;
    }
}
