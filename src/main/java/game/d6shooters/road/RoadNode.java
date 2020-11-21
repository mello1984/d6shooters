package game.d6shooters.road;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoadNode {
    Type type;
    RoadMap.Road nextRoad;
    int nextRoadNumberNode;
    String townName;

     TownShop townShop;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    List<RoadNode> roadNodeList;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    RoadNode mergeNode;

    public RoadNode(Type type) {
        this.type = type;
    }

    public enum Type {
        ROAD, BRANCHSTART, BRANCHEND, TOWN, EVENT, RINO
    }
}
