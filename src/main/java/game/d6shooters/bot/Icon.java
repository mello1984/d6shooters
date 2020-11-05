package game.d6shooters.bot;

public enum Icon {
    DICE1("\u2680"),
    DICE2("\u2681"),
    DICE3("\u2682"),
    DICE4("\u2683"),
    DICE5("\u2684"),
    DICE6("\u2685"),
    HORSERACING("\uD83C\uDFC7"),//?
    GUNFIGHTER("\uD83E\uDD20"),//?
    REDSQUARE("\uD83D\uDFE5"),
    WHITESQUARE("\u25FB"),
    FOOD("\uD83C\uDF57"),
    AMMO("\uD83D\uDCA3"),
    MONEYBAG("\uD83D\uDCB0"),
    BUFFALO("\uD83D\uDC03"),
    FOOTPRINTS("\uD83D\uDC63"),
    CLOCK("\u23F0"),
    ;

    private String name;


    Icon(String name) {
        this.name = name;
    }

    public String get() {
        return name;
    }
}
