package core;

import lombok.Getter;

public enum Language {
    RU("ru"),
    UK("uk"),
    DE("de"),
    EN("en");

    @Getter
    private final String languageCode;

    private Language(String lang) {
        this.languageCode = lang;
    }
}
