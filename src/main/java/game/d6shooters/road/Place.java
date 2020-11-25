package game.d6shooters.road;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
@ToString
@EqualsAndHashCode
public class Place {
    RoadMap.Road road;
    int number;

    public RoadNode.Type getType() {
        return RoadMap.map.get(road).get(number).getType();
    }

    public String getTownName() {
        return RoadMap.map.get(road).get(number).getTownName();
    }

    public TownShop getTownShop() {
        return RoadMap.map.get(road).get(number).getTownShop();
    }

    public static Place getNew() {
        return new Place(RoadMap.Road.MAINROAD, 13);
    }
}