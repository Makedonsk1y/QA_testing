package autotests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;
import static com.consol.citrus.validation.DelegatingPayloadVariableExtractor.Builder.fromBody;
import static com.consol.citrus.variable.MessageHeaderVariableExtractor.Builder.fromHeaders;

public class DuckSwimTest extends DuckBaseTest {
    @Test(description = "Проверка того, что уточка поплыла")
    @CitrusTest
    public void successfulSwim(@Optional @CitrusResource TestCaseRunner runner){
        createDuck(runner, "red", 0.43, "wood", "quack", "ACTIVE");
        saveDuckId(runner);
        duckSwim(runner, "${duckId}");
        validateResponse(runner, "{\n"+" \"message\":\"I'm swimming\"\n"+"}");
        extractDataFromResponse(runner);
        duckSwim(runner, "-1");
        validateBadResponse(runner);
        extractDataFromResponse(runner);
    }

    public void duckSwim(TestCaseRunner runner, String id){
        runner.$(
                http().client("http://localhost:2222").send().get("/api/duck/action/swim").queryParam("id", id)
        );
    }

    public void validateResponse(TestCaseRunner runner, String responseMessage){
        runner.$(
                http().client("http://localhost:2222").receive().response(HttpStatus.OK).
                        message().contentType(MediaType.APPLICATION_JSON_VALUE).body(responseMessage)
        );
    }

    public void validateBadResponse(TestCaseRunner runner){
        runner.$(
                http().client("http://localhost:2222").receive().response(HttpStatus.NOT_FOUND).
                        message().contentType(MediaType.APPLICATION_JSON_VALUE)
        );
    }

    public void extractDataFromResponse(TestCaseRunner runner) {
        runner.$(
                http()
                        .client("http://localhost:2222")
                        .receive()
                        .response(HttpStatus.OK)
                        .message()
                        //.type(MessageType.JSON)
                        .extract(fromBody().expression("$.message", "errorMessage"))
                        .extract(fromHeaders().header("contentType", "type")));
    }
}
