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

public class DuckCreateTest extends TestNGCitrusSpringSupport {
    @Test(description = "Проверка создания утки")
    @CitrusTest
    public void successfulCreateDucks(@Optional @CitrusResource TestCaseRunner runner){
        // Создаем утку с material = rubber
        createDuckAndValidate(runner, "red", 0.53, "rubber", "quack", "ACTIVE");

        // Создаем утку с material = wood
        createDuckAndValidate(runner, "blue", 0.60, "wood", "quack", "FIXED");
    }

    private void createDuckAndValidate(TestCaseRunner runner, String color, double height, String material, String sound, String wingsState) {
        // Создаем утку
        createDuck(runner, color, height, material, sound, wingsState);

        // Сохраняем ID утки
        saveDuckId(runner);

        // Проверяем ответ
        validateDuckResponse(runner, color, height, material, sound, wingsState);
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

    private void validateDuckResponse(TestCaseRunner runner, String color, double height, String material, String sound, String wingsState) {
        runner.$(
                http().client("http://localhost:2222").receive().response(HttpStatus.OK)
                        .message().contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body("{\n" +
                                "\"id\": ${duckId},\n" +
                                "\"color\": \"" + color + "\",\n" +
                                "\"height\": " + height + ",\n" +
                                "\"material\": \"" + material + "\",\n" +
                                "\"sound\": \"" + sound + "\",\n" +
                                "\"wingsState\": \"" + wingsState + "\"\n" +
                                "}")
        );
    }
}
