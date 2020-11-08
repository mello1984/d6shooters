package game.d6shooters.road;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RoadMap {
    public final static Map<Road, List<RoadNode>> map = new HashMap<>();
    private final static List<RoadNode> mainRoad = getRoad(80);
    private final static List<RoadNode> lonrockRoad = getRoad(5);
    private final static List<RoadNode> bakskinRoad = getRoad(8);
    private final static String KALIKO = "Калико";
    private final static String GOLDHILL = "Голдхилл";
    private final static String THOMPSON = "Томпсон";
    private final static String BAKSKIN = "Бакскин";
    private final static String LONROK = "Лонрок";
    private final static String RINO = "Рино";


    static {
        IntStream.of(9, 19, 29, 39, 49, 59, 69, 76).forEach(i -> mainRoad.get(i).setType(RoadNode.Type.EVENT));
        IntStream.of(14, 34, 54).forEach(i -> mainRoad.get(i).setType(RoadNode.Type.TOWN));
        mainRoad.get(79).setType(RoadNode.Type.RINO);
        mainRoad.get(79).setTownName(RINO);
        mainRoad.get(14).setTownName(KALIKO);
        mainRoad.get(34).setTownName(GOLDHILL);
        mainRoad.get(54).setTownName(THOMPSON);
        bakskinRoad.get(6).setType(RoadNode.Type.EVENT);
        bakskinRoad.get(3).setType(RoadNode.Type.TOWN);
        bakskinRoad.get(3).setTownName(BAKSKIN);
        lonrockRoad.get(2).setType(RoadNode.Type.TOWN);
        lonrockRoad.get(2).setTownName(LONROK);

        setBranchStart(mainRoad.get(43), Road.LONROK);
        setBranchEnd(lonrockRoad.get(4), 46);
        setBranchStart(mainRoad.get(67), Road.BAKSKIN);
        setBranchEnd(bakskinRoad.get(7), 72);

        map.put(Road.MAINROAD, mainRoad);
        map.put(Road.LONROK, lonrockRoad);
        map.put(Road.BAKSKIN, bakskinRoad);

    }

    public static Place next(Place place) {
        List<RoadNode> road = map.get(place.getRoad());
        int number = place.getNumber();
        RoadNode.Type type = road.get(number).getType();
        RoadNode node = road.get(number);
        Place result;
        if (type != RoadNode.Type.BRANCHSTART && type != RoadNode.Type.BRANCHEND) {
            result = new Place(place.getRoad(), ++number);
        } else if (type == RoadNode.Type.BRANCHEND) {
            result = new Place(node.getNextRoad(), node.getNextRoadNumberNode());
        } else {
            result = new Place(place.getRoad(), ++number); // mainroad
            result = new Place(node.getNextRoad(), node.getNextRoadNumberNode()); // branch
        }

        return result;
    }

    private static List<RoadNode> getRoad(int size) {
        return IntStream.generate(() -> 0).limit(size).mapToObj(i -> new RoadNode(RoadNode.Type.ROAD)).collect(Collectors.toList());
    }

    private static void setBranchStart(RoadNode mainNode, Road road) {
        mainNode.setType(RoadNode.Type.BRANCHSTART);
        mainNode.setNextRoad(road);
        mainNode.setNextRoadNumberNode(0);
    }

    private static void setBranchEnd(RoadNode branchNode, int mainNodeNumber) {
        branchNode.setType(RoadNode.Type.BRANCHEND);
        branchNode.setNextRoad(Road.MAINROAD);
        branchNode.setNextRoadNumberNode(mainNodeNumber);
    }


    public enum Road {
        MAINROAD, LONROK, BAKSKIN
    }
}
