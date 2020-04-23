package its.me.Vladik.control;

import java.io.Serializable;

public class Point implements Serializable {
    private static final long serialVersionUID = 1L;

    public double x;
    public double y;

    public Point() { }
    public Point(double cx, double cy) {
        x = cx;
        y = cy;
    }
}
