import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import com.consol.citrus.validation.json.JsonPathMessageValidationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;


import static com.consol.citrus.validation.DelegatingPayloadVariableExtractor.Builder.fromBody;
import static com.consol.citrus.variable.MessageHeaderVariableExtractor.Builder.fromHeaders;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckActionsTest extends TestNGCitrusSpringSupport {
    @Test (description = "Проверка того, что уточка поплыла")
    @CitrusTest
    public void successfulSwim(@Optional @CitrusResource TestCaseRunner runner){
        createDuck(runner, "red", 0.43, "wood", "quack", "ACTIVE");
        saveDuckId(runner);
        duckSwim(runner, "${duckId}");
        validateResponse(runner, "{\n"+" \"message\":\"I'm swimming\"\n"+"}");
        extractDataFromResponse(runner);
    }

    private void saveDuckId(TestCaseRunner runner){
        runner.$(
                http()
                        .client("http://localhost:2222")
                        .receive()
                        .response()
                        .message()
                        .extract(fromBody().expression("$.id","duckId"))
        );
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

    public void createDuck(TestCaseRunner runner, String color, double height, String material, String sound, String wingsState) {
        runner.$(
                http()
                        .client("http://localhost:2222")
                        .send()
                        .post("/api/duck/create")
                        .message()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body( "{\n" +
                                "\"color\": \"" + color + "\",\n" +
                                "\"height\": " + height + ",\n" +
                                "\"material\": \"" + material + "\",\n" +
                                "\"sound\": \"" + sound + "\",\n" +
                                "\"wingsState\": \"" + wingsState + "\"\n" + "}"));
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
