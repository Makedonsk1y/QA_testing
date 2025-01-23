package autotests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;


public class DuckCreateTest extends DuckBaseTest {
    @Test(description = "Проверка создания утки с материалом rubber")
    @CitrusTest
    public void createDuckWithMaterialRubber(@Optional @CitrusResource TestCaseRunner runner){
        createDuck(runner, "red", 0.53, "rubber", "quack", "FIXED");
        saveDuckId(runner);
        validateDuckResponse(runner, "red", 0.53, "rubber", "quack", "FIXED");
    }

    @Test(description = "Проверка создания утки с материалом wood")
    @CitrusTest
    public void createDuckWithMaterialWood(@Optional @CitrusResource TestCaseRunner runner){
        createDuck(runner, "red", 0.53, "wood", "quack", "FIXED");
        saveDuckId(runner);
        validateDuckResponse(runner, "red", 0.53, "wood", "quack", "FIXED");
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
