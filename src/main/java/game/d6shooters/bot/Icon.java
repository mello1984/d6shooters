package game.d6shooters.bot;

import com.vdurmont.emoji.EmojiParser;

public enum Icon {

    DICE1(":one:"),
    DICE2(":two:"),
    DICE3(":three:"),
    DICE4(":four:"),
    DICE5(":five:"),
    DICE6(":six:"),
    GUNFIGHTER(":tophat:"),//?
    REDSQUARE(":red_circle:"),
    WHITESQUARE(":white_circle:"),
    FOOD(":poultry_leg:"),
    AMMO(":firecracker:"),
    MONEYBAG(":moneybag:"),
    BUFFALO(":water_buffalo:"),
    FOOTPRINTS(":footprints:"),
    CLOCK(":clock1:"),
    COMPASS(":compass:"),
    MAP(":world_map:"),
    PILL(":pill:"),
    BOMB(":bomb:"),
    HUNTER(":dog2:"),
    BINOCULAR(":telescope:"),
    POKER(":clubs:"),

    ;

    private String value;

    Icon(String value) {
        this.value = value;
    }

    public String get() {
        return EmojiParser.parseToUnicode(value);
    }
}

