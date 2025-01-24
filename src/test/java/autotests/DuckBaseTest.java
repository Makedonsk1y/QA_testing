package autotests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;
import static com.consol.citrus.validation.DelegatingPayloadVariableExtractor.Builder.fromBody;

public class DuckBaseTest extends TestNGCitrusSpringSupport {
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

    public void saveDuckId(TestCaseRunner runner) {
        runner.$(
                http()
                        .client("http://localhost:2222")
                        .receive()
                        .response(HttpStatus.OK)
                        .message()
                        .extract(fromBody().expression("$.id", "duckId"))
        );
    }

    public void deleteDuck(TestCaseRunner runner, String duckId) {
        runner.$(
                http().client("http://localhost:2222").send().delete("/api/duck/delete").queryParam("id", duckId)
        );

        runner.$(
                http().client("http://localhost:2222").receive().response(HttpStatus.OK)
                        .message()
                        .body("{\"message\": \"Duck is deleted\"}")
        );
    }

    public void validateResponse(TestCaseRunner runner, String expectedMessage){
        runner.$(
                http().client("http://localhost:2222").receive().response(HttpStatus.OK)
                        .message().contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body("{\n" +
                                "\"message\": \"" + expectedMessage + "\"\n" +
                                "}")
        );
    }

}
