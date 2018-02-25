package core;

import lombok.Getter;

public enum Option {
    IGNORE_DIGITS(2),
    IGNORE_URLS(4),
    WRONG_OPTION_5(5),
    FIND_REPEAT_WORDS(8),
    WRONG_OPTION_16(16),
    IGNORE_CAPITALIZATION(512);

    @Getter
    private final int code;

    Option(int code) {
        this.code = code;
    }
}
