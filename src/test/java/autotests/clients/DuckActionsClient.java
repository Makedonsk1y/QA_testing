package autotests.clients;

import autotests.EndpointConfig;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;
import static com.consol.citrus.validation.DelegatingPayloadVariableExtractor.Builder.fromBody;
import static com.consol.citrus.validation.json.JsonMessageValidationContext.Builder.json;
import static com.consol.citrus.validation.json.JsonPathMessageValidationContext.Builder.jsonPath;

@ContextConfiguration(classes = {EndpointConfig.class})
public class DuckActionsClient extends TestNGCitrusSpringSupport {

    @Autowired
    protected HttpClient duckService;

    //CRUD
    //String
    public void createDuck(TestCaseRunner runner, String color, double height, String material, String sound, String wingsState) {
        runner.$(
                http()
                        .client(duckService)
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

    //Payloads folder
    public void createDuck(TestCaseRunner runner, Object body){
        runner.$(
                http()
                        .client(duckService)
                        .send()
                        .post("/api/duck/create")
                        .message()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(new ObjectMappingPayloadBuilder(body, new ObjectMapper())));
    }

    //Resources folder
    public void createDuck(TestCaseRunner runner, String expectedPayload){
        runner.$(
                http()
                        .client(duckService)
                        .send()
                        .post("/api/duck/create")
                        .message()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(new ClassPathResource(expectedPayload))
        );
    }

    public void updateDuck(TestCaseRunner runner, String color, double height, String material, String sound, String wingsState) {
        runner.$(
                http().client(duckService).send().put("/api/duck/update")
                        .queryParam("color", color)
                        .queryParam("height", String.valueOf(height))
                        .queryParam("id", "${duckId}")
                        .queryParam("material", material)
                        .queryParam("sound", sound)
                        .queryParam("wingsState", wingsState)
        );
    }

    public void deleteDuck(TestCaseRunner runner) {
        runner.$(
                http().client(duckService).send().delete("/api/duck/delete").queryParam("id", "${duckId}")
        );
    }


    //Actions
    public void duckSwim(TestCaseRunner runner){
        runner.$(
                http().client(duckService).send().get("/api/duck/action/swim").queryParam("id", "${duckId}")
        );
    }

    public void duckQuack(TestCaseRunner runner, int repetitionCount, int soundCount){
        runner.$(
                http().client(duckService).send().get("/api/duck/action/quack")
                        .queryParam("id", "${duckId}")
                        .queryParam("repetitionCount", String.valueOf(repetitionCount))
                        .queryParam("soundCount", String.valueOf(soundCount))
        );
    }

    public void getDuckProperties(TestCaseRunner runner) {
        runner.$(
                http().client(duckService).send().get("/api/duck/action/properties").queryParam("id", "${duckId}")
        );
    }

    public void duckFly(TestCaseRunner runner){
        runner.$(
                http().client(duckService).send().get("/api/duck/action/fly").queryParam("id", "${duckId}")
        );
    }

    //Validation
    //String
    public void validateDuckResponse(TestCaseRunner runner, String color, double height, String material, String sound, String wingsState) {
        runner.$(
                http()
                        .client(duckService)
                        .receive()
                        .response(HttpStatus.OK)
                        .message()
                        .type(MessageType.JSON)
                        .validate(jsonPath()
                                .expression("$.color", color)
                                .expression("$.height", height)
                                .expression("$.material", material)
                                .expression("$.sound", sound)
                                .expression("$.wingsState", wingsState)
                        )
        );
    }

    //Payloads folder
    public void validateDuckResponse(TestCaseRunner runner, Object body){
        runner.$(
                http()
                        .client(duckService)
                        .receive()
                        .response(HttpStatus.OK)
                        .message()
                        .type(MessageType.JSON)
                        .validate(json().ignore("$.id"))
                        .body(new ObjectMappingPayloadBuilder(body, new ObjectMapper()))
        );
    }

    //Resources folder
    public void validateDuckResponse(TestCaseRunner runner, String expectedPayload){
        runner.$(
                http()
                        .client(duckService)
                        .receive()
                        .response(HttpStatus.OK)
                        .message()
                        .type(MessageType.JSON)
                        .body(new ClassPathResource(expectedPayload))
        );
    }

    //String
    public void validateResponse(TestCaseRunner runner, String expectedMessage){
        runner.$(
                http().client(duckService).receive().response(HttpStatus.OK)
                        .message().contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body("{\n" +
                                "\"message\": \"" + expectedMessage + "\"\n" +
                                "}")
        );
    }

    //Payloads folder
    public void validateResponse(TestCaseRunner runner, Object body){
        runner.$(
                http().client(duckService).receive().response(HttpStatus.OK)
                        .message().contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(new ObjectMappingPayloadBuilder(body, new ObjectMapper()))
        );
    }

    //Resources folder
    public void validateResponseWithResource(TestCaseRunner runner, String expectedPayload){
        runner.$(
                http().client(duckService).receive().response(HttpStatus.OK)
                        .message().contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(new ClassPathResource(expectedPayload))
        );
    }

    public void validateBadResponse(TestCaseRunner runner){
        runner.$(
                http().client(duckService).receive().response(HttpStatus.NOT_FOUND).
                        message().contentType(MediaType.APPLICATION_JSON_VALUE)
        );
    }

    //Additional methods
    public void saveDuckId(TestCaseRunner runner) {
        runner.$(
                http()
                        .client(duckService)
                        .receive()
                        .response(HttpStatus.OK)
                        .message()
                        .extract(fromBody().expression("$.id", "duckId"))
        );
    }

    public void checkDuckDeleted(TestCaseRunner runner) {
        runner.$(
                http().client("http://localhost:2222").send().get("/api/duck/action/properties").queryParam("id", "${duckId}")
        );

        runner.$(
                http().client("http://localhost:2222").receive().response(HttpStatus.INTERNAL_SERVER_ERROR)
        );
    }

}
