package constraintsatisfying;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


enum MapColor {
    RED,
    YELLOW,
    BLUE,
    ORANGE,
}

enum JingMenDistrict {
    DONGBAO,
    DUODAO,
    SHAYANG,
    ZHONGXIANG,
    JINGSHAN
}

public class MapColoringConstraint extends Constraint<JingMenDistrict, MapColor> {

    private JingMenDistrict place1, place2;

    public MapColoringConstraint(JingMenDistrict place1, JingMenDistrict place2) {
        super(List.of(place1, place2));
        this.place1 = place1;
        this.place2 = place2;
    }

    @Override
    public boolean satisfied(Map<JingMenDistrict, MapColor> assignment) {
        if (!assignment.containsKey(place1) || !assignment.containsKey(place2)) {
            return true;
        }

        return !assignment.get(place1).equals(assignment.get(place2));
    }

    public static void main(String[] args) {
        Map<JingMenDistrict, List<MapColor>> domains = new HashMap<>();

        for (JingMenDistrict district : JingMenDistrict.values()) {
            domains.put(district, List.of(MapColor.YELLOW, MapColor.RED, MapColor.BLUE));
        }

        CSP<JingMenDistrict, MapColor> csp = new CSP<>(domains);
        csp.addConstraint(new MapColoringConstraint(JingMenDistrict.DONGBAO, JingMenDistrict.ZHONGXIANG));
        csp.addConstraint(new MapColoringConstraint(JingMenDistrict.DONGBAO, JingMenDistrict.DUODAO));
        csp.addConstraint(new MapColoringConstraint(JingMenDistrict.DUODAO, JingMenDistrict.SHAYANG));
        csp.addConstraint(new MapColoringConstraint(JingMenDistrict.DUODAO, JingMenDistrict.ZHONGXIANG));
        csp.addConstraint(new MapColoringConstraint(JingMenDistrict.ZHONGXIANG, JingMenDistrict.SHAYANG));
        csp.addConstraint(new MapColoringConstraint(JingMenDistrict.ZHONGXIANG, JingMenDistrict.JINGSHAN));

        Map<JingMenDistrict, MapColor> solution = csp.backtracking(new HashMap<>());
        if (solution == null) {
            System.out.println("No solution found!");
        } else {
            System.out.println(solution);
        }
    }
}
