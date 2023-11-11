package validator;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


public class PointsStorage {
    private List<Point> points;
    public PointsStorage(List<Point> points){
        this.points=points;
    }
    //todo: it is ebola
    public PointsStorage(){
        this.points = new ArrayList<>();
    }

    public PointsStorage(ArrayList<Object> objects) {
    }
    public List<Point> getPoints(){
        return points;
    }


}
