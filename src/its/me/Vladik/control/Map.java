package its.me.Vladik.control;

import java.util.ArrayList;

public class Map {
    public String figureName;
    public ArrayList<Attribute> attributes;
    public int line;

    public Map() {
        attributes = new ArrayList<Attribute>();
        figureName = new String();
    }
}
