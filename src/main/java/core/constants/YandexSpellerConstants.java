package core.constants;

/**
 * Created by yulia_atlasova@epam.com on 22/06/2017.
 * Constants of YandexSpeller
 */
public class YandexSpellerConstants {

    //useful constants for API under test
    public static final String PARAM_TEXT = "text";
    public static final String PARAM_OPTIONS = "options";
    public static final String PARAM_LANG = "lang";
    public static final String WRONG_WORD_EN = "requisitee";
    public static final String RIGHT_WORD_EN = "requisite";
    public static final String WRONG_WORD_UK = "питаня";
    public static final String WORD_WITH_WRONG_CAPITAL = "moscow";
    public static final String WORD_WITH_LEADING_DIGITS = "11" + RIGHT_WORD_EN;


    public enum Languages {
        RU("ru"),
        UK("uk"),
        EN("en");
        public String languageCode;

        private Languages(String lang) {
            this.languageCode = lang;
        }
    }

    public enum ErrorCodes {
        ERROR_UNKNOWN_WORD("1"),
        ERROR_REPEAT_WORD("2"),
        ERROR_CAPITALIZATION("3"),
        ERROR_TOO_MANY_ERRORS("4");

        private String code;

        public String getCode() {
            return code;
        }

        private ErrorCodes(String code) {
            this.code = code;
        }
    }

    public enum CorrectTexts {
        RUSSIAN("Этот текст на русском языке"),
        URL_TEXT("This text contains URL http://yandex.ru");
        private String text;

        public String text() {
            return text;
        }

        private CorrectTexts(String text) {
            this.text = text;
        }
    }

}
