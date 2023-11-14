package validator;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



public class PointsStorage implements Serializable {
    private List<Point> points;
    public PointsStorage(List<Point> points){
        this.points=points;
    }
    public PointsStorage(){
        this.points = new ArrayList<>();
    }

    public PointsStorage(ArrayList<Object> objects) {
    }
    public List<Point> getPoints(){
        List<Point> newList = new ArrayList<>(points);
        return newList;
    }
    public void addPoint(Point point){
        points.add(point);
    }


}
