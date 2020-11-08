package game.d6shooters.road;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoadMapTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void next() {
        Place actual1 = RoadMap.next(Place.getNew(), true);
        Place expected1 = new Place(RoadMap.Road.MAINROAD, 1);
        Place actual2 = RoadMap.next(new Place(RoadMap.Road.MAINROAD, 1), true);
        Place expected2 = new Place(RoadMap.Road.MAINROAD, 2);


        Place actual3 = RoadMap.next(new Place(RoadMap.Road.LONROK, 4), true);
        Place expected3 = new Place(RoadMap.Road.MAINROAD, 46);
        Place actual4 = RoadMap.next(new Place(RoadMap.Road.BAKSKIN, 7), true);
        Place expected4 = new Place(RoadMap.Road.MAINROAD, 72);
        Place actual5 = RoadMap.next(new Place(RoadMap.Road.LONROK, 1), true);
        Place expected5 = new Place(RoadMap.Road.LONROK, 2);

        Place actual6 = RoadMap.next(new Place(RoadMap.Road.MAINROAD, 43), false);
        Place expected6 = new Place(RoadMap.Road.LONROK, 0);
        Place actual7 = RoadMap.next(new Place(RoadMap.Road.MAINROAD, 67), false);
        Place expected7 = new Place(RoadMap.Road.BAKSKIN, 0);


        assertAll("Test RoadMap.next method",
                () -> assertEquals(expected1, actual1),
                () -> assertEquals(expected2, actual2),
                () -> assertEquals(expected3, actual3),
                () -> assertEquals(expected4, actual4),
                () -> assertEquals(expected5, actual5),
                () -> assertEquals(expected6, actual6),
                () -> assertEquals(expected7, actual7)
        );
    }
}