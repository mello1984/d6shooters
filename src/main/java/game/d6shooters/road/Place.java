package game.d6shooters.road;

import game.d6shooters.game.Squad;
import game.d6shooters.users.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
@ToString
@EqualsAndHashCode
public class Place {
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Squad squad;
    RoadMap.Road road;
    int number;

    public RoadNode.Type getType() {
        return squad.getRoadMap().map.get(road).get(number).getType();
    }

    public String getTownName() {
        return squad.getRoadMap().map.get(road).get(number).getTownName();
    }

    public TownShop getTownShop() {
        return squad.getRoadMap().map.get(road).get(number).getTownShop();
    }

    public static Place getNew(Squad squad) {
        return new Place(squad, RoadMap.Road.MAINROAD, 78);
    }
}