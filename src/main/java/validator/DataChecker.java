package validator;

import exceptions.WrongDataException;

public class DataChecker {

    public DataChecker() {
    }

    final static double xMin = -5;
    final static double xMax = 3;
    final static double yMin = -5;
    final static double yMax = 3;
    final static double rMin = 1;
    final static double rMax = 5;
    final static double graphBoundary = 5.5;

    public boolean checkXYR(double x, double y, double r) throws WrongDataException {
        if (checkX(x)) {
            throw new WrongDataException("x = " + x + " is invalid value");
        }
        if (checkY(y)) throw new WrongDataException("y = " + y + " is invalid value");
        if (checkR(r)) throw new WrongDataException("r = " + r + " is invalid value");
        return true;

    }

    private boolean checkX(double x) {
        return x > xMax || x < xMin;
    }

    private boolean checkY(double y) {
        return y >= yMax || y <= yMin;
    }

    private boolean checkR(double r) {
        return r > rMax || r < rMin;
    }

    public boolean checkKill(double x, double y, double r) {
        return ((x <= 0) && (x >= -r) && (y >= 0) && (y <= r / 2))
                || ((x <= 0) && (x >= -r / 2) && (y <= 0) && (y >= -r / 2) && (y <= -x - r / 2))
                || ((x >= 0) && (y <= 0) && ((x * x + y * y) <= (r * r / 4)));
    }

    public boolean checkAreaForGraph(Double x, Double y, Double r) {
        return -1*graphBoundary <= x && x <= graphBoundary && -1*graphBoundary <= y && y <= graphBoundary && rMin <= r && r <= rMax;
    }
}
