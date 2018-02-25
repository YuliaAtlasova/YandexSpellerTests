import beans.YandexSpellerAnswer;
import core.Option;
import org.junit.Test;

import java.util.List;

import static core.Error.ERROR_CAPITALIZATION;
import static core.Error.ERROR_REPEAT_WORD;
import static core.Error.ERROR_UNKNOWN_WORD;
import static core.Language.*;
import static core.Option.IGNORE_URLS;
import static core.YandexSpellerApi.getYandexSpellerAnswers;
import static core.YandexSpellerApi.with;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

/**
 * @author Olga Odintsova
 */
public class TestYandexSpellerRESTHW {

    @Test
    public void unknownRussianWord() {
        List<YandexSpellerAnswer> answers = getYandexSpellerAnswers(with().text("прив" + /* english letter*/ "e" + "т")
                                                                          .language(RU)
                                                                          .callGetApi());

        assertThat("An answer isn't received!", answers.size(), equalTo(1));

        YandexSpellerAnswer answer = answers.get(0);
        assertThat("Expected error code is wrong!", answer.getError(), equalTo(ERROR_UNKNOWN_WORD));
        assertThat("Similar word doesn't recognized!", answer.getSimilar().contains("привет"));
    }

    @Test
    public void unsupportedLanguage() {
        List<YandexSpellerAnswer> answers = getYandexSpellerAnswers(with().text("fehler")
                                                                          .language(DE)
                                                                          .callGetApi());

        assertThat("Expected refuse to process unsupported language!", answers, empty());
    }

    @Test
    public void incorrectRussianWordInEnglishDictionary() {
        List<YandexSpellerAnswer> answers = getYandexSpellerAnswers(with().text("халадильник")
                                                                          .language(EN)
                                                                          .callGetApi());

        assertThat("An answer isn't received!", answers.size(), equalTo(1));
        assertThat("Expected error code is wrong!", answers.get(0).getError(), equalTo(ERROR_UNKNOWN_WORD));
    }

    @Test
    public void correctRussianWordInEnglishDictionary() {
        List<YandexSpellerAnswer> answers = getYandexSpellerAnswers(with().text("холодильник")
                                                                          .language(EN)
                                                                          .callGetApi());

        assertThat("An answer isn't received!", answers.size(), equalTo(1));
        assertThat("Expected error code is wrong!", answers.get(0).getError(), equalTo(ERROR_UNKNOWN_WORD));
    }

    @Test
    public void correctRussianWordWithDigits() {
        List<YandexSpellerAnswer> answers = getYandexSpellerAnswers(with().text("холодильник1")
                                                                          .language(RU)
                                                                          .options(Option.IGNORE_DIGITS)
                                                                          .callGetApi());

        assertThat("Word was recognized as wrong!", answers, empty());
    }

    @Test
    public void correctRussianWordWithUnsupportedOption() {
        List<YandexSpellerAnswer> answers = getYandexSpellerAnswers(with().text("человек")
                                                                          .language(RU)
                                                                          .options(Option.WRONG_OPTION_16)
                                                                          .callGetApi());

        assertThat("Unsupported option failed operation!", answers, empty());
    }

    @Test
    public void twiceRepeatedRussianWords() {
        List<YandexSpellerAnswer> answers = getYandexSpellerAnswers(with().text("сел в в машину")
                                                                          .language(RU)
                                                                          .options(Option.FIND_REPEAT_WORDS)
                                                                          .callGetApi());

        assertThat("An answer isn't received!", answers, not(empty()));
        assertThat("Not found repeated word!", answers.get(0).getCode(), equalTo(ERROR_REPEAT_WORD));
    }

    @Test
    public void incorrectUpperCaseInTheRegularRussianWorld() {
        List<YandexSpellerAnswer> answers = getYandexSpellerAnswers(with().text("почему я СеЛ в машину")
                                                                          .language(RU)
                                                                          .callGetApi());

        assertThat("An answer isn't received!", answers, not(empty()));
        assertThat("Not found wrong capitalization in word!", answers.get(0).getCode(), equalTo(ERROR_CAPITALIZATION));
    }

    @Test
    public void missingUpperCaseInTheProperNamesInEnglish() {
        List<YandexSpellerAnswer> answers = getYandexSpellerAnswers(with().text("london is the capital of great britain")
                                                                          .language(EN)
                                                                          .callGetApi());

        assertThat("An answer isn't received!", answers, not(empty()));
        assertThat("Not found wrong capitalization in word!", answers.get(0).getCode(), equalTo(ERROR_CAPITALIZATION));
    }

    @Test
    public void skipUrlsInSentence() {
        List<YandexSpellerAnswer> answers = getYandexSpellerAnswers(with().text("Мой аддрес сегодня такой: www.леннинград.ru")
                                                                          .language(RU)
                                                                          .options(IGNORE_URLS)
                                                                          .callGetApi());

        assertThat("Found error in URL-address!", answers.size(), equalTo(1));
    }
}
