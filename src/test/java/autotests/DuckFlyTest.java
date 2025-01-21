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

public class DuckFlyTest extends TestNGCitrusSpringSupport {

    @Test (description = "Проверка того, что утка летает")
    @CitrusTest
    public void successfulFly(@Optional @CitrusResource TestCaseRunner runner){
        // Тест для состояния крыльев ACTIVE
        testFlyWithWingsState(runner, "ACTIVE", "I am flying :)"); // оставил пока что так для проверки работают ли тесты
        // Тест для состояния крыльев FIXED
        testFlyWithWingsState(runner, "FIXED", "I can not fly :C");
        // Тест для неопределенного состояния крыльев UNDEFINED
        testFlyWithWingsState(runner, "UNDEFINED", "Wings are not detected :(");
    }

    private void testFlyWithWingsState(TestCaseRunner runner, String wingsState, String expectedMessage) {
        // Создаем утку с заданным состоянием крыльев
        createDuck(runner, "red", 0.53, "metal", "quack", wingsState);
        saveDuckId(runner);
        duckFly(runner, "${duckId}");
        validateFlyResponse(runner, expectedMessage);
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

    public void duckFly(TestCaseRunner runner, String id){
        runner.$(
                http().client("http://localhost:2222").send().get("/api/duck/action/fly").queryParam("id", id)
        );
    }

    public void validateFlyResponse(TestCaseRunner runner, String expectedMessage){
        runner.$(
                http().client("http://localhost:2222").receive().response(HttpStatus.OK)
                        .message().contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body("{\n" +
                                "\"message\": \"" + expectedMessage + "\"\n" +
                                "}")
        );
    }
}
