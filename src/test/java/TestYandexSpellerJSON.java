import beans.YandexSpellerAnswer;
import core.YandexSpellerCheckTextApi;
import core.constants.IncorrectTexts;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsEqual;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static core.YandexSpellerCheckTextApi.successResponse;
import static core.constants.Options.*;
import static core.constants.YandexSpellerConstants.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.core.IsCollectionContaining.hasItem;


/**
 * Created by yulia-atlasova@epam.com.
 */
public class TestYandexSpellerJSON {

    // simple usage of RestAssured library: direct request call and response validations in test.
    @Test
    public void simpleSpellerApiCall() {
        RestAssured
                .given()
                .queryParam(PARAM_TEXT, WRONG_WORD_EN)
                .params(PARAM_LANG, Languages.EN,
                        "CustomParameter", "valueOfParam")
                .accept(ContentType.JSON)
                .auth().basic("abcName", "abcPassword")
                .header("custom header1", "header1.value")
                .and()
                .body("some body payroll")
                .log().everything()
                .get(YandexSpellerCheckTextApi.YANDEX_SPELLER_API_URI)
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body(Matchers.allOf(
                        Matchers.stringContainsInOrder(Arrays.asList(WRONG_WORD_EN, RIGHT_WORD_EN)),
                        Matchers.containsString("\"code\":1")))
                .contentType(ContentType.JSON)
                .time(lessThan(20000L)); // Milliseconds
    }

    // different http methods calls
    @Test
    public void spellerApiCallsWithDifferentMethods() {
        //GET //POST //HEAD //OPTIONS
        RestAssured
                .given()
                .param(PARAM_TEXT, WRONG_WORD_EN)
                .log().everything()
                .patch (YandexSpellerCheckTextApi.YANDEX_SPELLER_API_URI)
                .prettyPeek();

        //DELETE  //PUT  //PATCH
        RestAssured
                .given()
                .param(PARAM_TEXT, WRONG_WORD_EN)
                .log()
                .everything()
                .delete(YandexSpellerCheckTextApi.YANDEX_SPELLER_API_URI).prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED)
                .statusLine("HTTP/1.1 405 Method not allowed");
    }


    // use base request and response specifications to form request and validate response.
    @Test
    public void useBaseRequestAndResponseSpecifications() {
        RestAssured
                .given(YandexSpellerCheckTextApi.baseRequestConfiguration())
                .param(PARAM_TEXT, WRONG_WORD_EN)
                .get().prettyPeek()
                .then().specification(successResponse());
    }

    @Test
    public void reachBuilderUsage(){
        YandexSpellerCheckTextApi.with()
                .language(Languages.UK)
                .options(computeOptions(IGNORE_DIGITS, IGNORE_URLS, IGNORE_CAPITALIZATION, FIND_REPEAT_WORDS))
                .text(WRONG_WORD_UK)
                .callApi()
                .then().specification(successResponse());
    }


    //validate an object we've got in API response
    @Test
    public void validateSpellerAnswerAsAnObject() {
        List<YandexSpellerAnswer> answers =
                YandexSpellerCheckTextApi.getYandexSpellerAnswers(
                        YandexSpellerCheckTextApi.with().text("motherr fatherr," + WRONG_WORD_EN).callApi());
        assertThat("expected number of answers is wrong.", answers.size(), equalTo(3));
        assertThat(answers.get(0).word, equalTo("motherr"));
        assertThat(answers.get(1).word, equalTo("fatherr"));
        assertThat(answers.get(0).s.get(0), equalTo("mother"));
        assertThat(answers.get(1).s.get(0), equalTo("father"));
        assertThat(answers.get(2).s.get(0), equalTo(RIGHT_WORD_EN));
    }


    @Test
    public void optionsValueIgnoreDigits(){
        List<YandexSpellerAnswer> answers =
                YandexSpellerCheckTextApi.getYandexSpellerAnswers(
                        YandexSpellerCheckTextApi.with().
                                text(WORD_WITH_LEADING_DIGITS)
                                .options(IGNORE_DIGITS.getCode())
                                .callApi());
        assertThat("expected number of answers is wrong.", answers.size(), equalTo(0));
    }

    @Test
    public void optionsIgnoreWrongCapital(){
        List<YandexSpellerAnswer> answers =
                YandexSpellerCheckTextApi.getYandexSpellerAnswers(
                        YandexSpellerCheckTextApi.with().
                                text(WORD_WITH_WRONG_CAPITAL)
                                .options("512")
                                .callApi());
        assertThat("expected number of answers is wrong.", answers.size(), equalTo(0));
    }

    @Test//(description = "Check Russian text with English letters in it")
    public void checkRusTextWithEngLetters() {
        List<YandexSpellerAnswer> answers =
                YandexSpellerCheckTextApi.getYandexSpellerAnswers(
                        YandexSpellerCheckTextApi.with()
                .text(IncorrectTexts.RUSSIAN_ENG_LETTER.text())
                .language(Languages.RU)
                .callApi()
                                .then()
                                .specification(successResponse())
                                .extract().response());

        // Check that answers size is 2
        assertThat(answers.size(), IsEqual.equalTo(2));
        assertThat(answers.get(0).toString(), isEmptyOrNullString());

    }
}
