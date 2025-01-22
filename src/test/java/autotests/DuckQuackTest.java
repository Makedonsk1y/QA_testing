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

public class DuckQuackTest extends TestNGCitrusSpringSupport {
    @Test (description = "Проверка того, что утки с четным и нечетным id издают звуки")
    @CitrusTest
    public void successfulQuack(@Optional @CitrusResource TestCaseRunner runner){

        createDuckAndSaveId(runner, "red", 0.53, "rubber", "quack", "ACTIVE");
        String firstDuckId = "${duckId}";

        createDuckAndSaveId(runner, "blue", 0.60, "plastic", "quack", "ACTIVE");
        String secondDuckId = "${duckId}";

        checkDuckQuack(runner, firstDuckId, 2, 3, "quack-quack-quack, quack-quack-quack");
        checkDuckQuack(runner, secondDuckId, 2, 3, "quack-quack-quack, quack-quack-quack");
    }

    private void createDuckAndSaveId(TestCaseRunner runner, String color, double height, String material, String sound, String wingsState) {
        createDuck(runner, color, height, material, sound, wingsState);
        saveDuckId(runner);
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

    private void saveDuckId(TestCaseRunner runner) {
        runner.$(
                http()
                        .client("http://localhost:2222")
                        .receive()
                        .response()
                        .message()
                        .extract(fromBody().expression("$.id", "duckId")) // Сохраняем ID в переменной duckId
        );
    }

    private void checkDuckQuack(TestCaseRunner runner, String duckId, int repetitionCount, int soundCount, String expectedSound) {
        runner.$(
                http().client("http://localhost:2222").send().get("/api/duck/action/quack")
                        .queryParam("id", duckId)
                        .queryParam("repetitionCount", String.valueOf(repetitionCount))
                        .queryParam("soundCount", String.valueOf(soundCount))
        );

        runner.$(
                http().client("http://localhost:2222").receive().response(HttpStatus.OK)
                        .message().contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body("{\n" +
                                "\"sound\": \"" + expectedSound + "\"\n" +
                                "}")
        );
    }
}
