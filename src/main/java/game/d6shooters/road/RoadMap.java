package game.d6shooters.road;


import game.d6shooters.game.Squad;
import lombok.Getter;
import lombok.experimental.UtilityClass;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
public class RoadMap implements Serializable {
    public final Map<Road, List<RoadNode>> map = new HashMap<>();
    private final List<RoadNode> mainRoad = getRoad(80);
    private final List<RoadNode> lonrockRoad = getRoad(5);
    private final List<RoadNode> bakskinRoad = getRoad(8);
    private final static String KALIKO = "Калико";
    private final static String GOLDHILL = "Голдхилл";
    private final static String THOMPSON = "Томпсон";
    private final static String BAKSKIN = "Бакскин";
    private final static String LONROK = "Лонрок";
    private final static String RINO = "Рино";
    private final Squad squad;

    public RoadMap(Squad squad) {
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
        this.squad = squad;
    }

    public Place next(Place place, boolean mainRoad) {
        int number = place.getNumber();
        RoadNode node = this.map.get(place.getRoad()).get(number);

        Place result = switch (node.getType()) {
            case ROAD, TOWN, EVENT -> new Place(squad, place.getRoad(), ++number);
            case BRANCHEND -> new Place(squad, node.getNextRoad(), node.getNextRoadNumberNode());
            case BRANCHSTART -> {
                Place main = new Place(squad, place.getRoad(), ++number);
                Place branch = new Place(squad, node.getNextRoad(), node.getNextRoadNumberNode());
                yield mainRoad ? main : branch;
            }
            case RINO -> Place.getNew(squad); // RINO!!!
        };

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
