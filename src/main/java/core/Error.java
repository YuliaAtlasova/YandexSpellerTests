package core;

import java.util.Arrays;

public enum Error {

    ERROR_UNKNOWN_WORD(1),
    ERROR_REPEAT_WORD(2),
    ERROR_CAPITALIZATION(3),
    ERROR_TOO_MANY_ERRORS(4);

    private final int code;

    Error(int code) {
        this.code = code;
    }

    public static Error getByCode(int code) {
        return Arrays.stream(values())
                     .filter(error -> code == error.code)
                     .findFirst()
                     .orElseThrow(IllegalArgumentException::new);
    }
}
