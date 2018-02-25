package core;

import lombok.Getter;

public class YandexSpellerConstants {

    //useful constants for API under test
    public static final String YANDEX_SPELLER_API_URI = "https://speller.yandex.net/services/spellservice.json/checkText";
    public static final String PARAM_TEXT = "text";
    public static final String PARAM_OPTIONS = "options";
    public static final String PARAM_LANG = "lang";
    public static final String WRONG_WORD_EN = "requisitee";
    public static final String RIGHT_WORD_EN = "requisite";
    public static final String WRONG_WORD_UK = "питаня";
    public static final String WORD_WITH_WRONG_CAPITAL = "moscow";
    public static final String WORD_WITH_LEADING_DIGITS = "11" + RIGHT_WORD_EN;


}
