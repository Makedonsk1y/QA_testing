package autotests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;


public class DuckUpdateTest extends DuckBaseTest {
    @Test(description = "Проверка обновления цвета и высоты утки")
    @CitrusTest
    public void successfulUpdateColorAndHeight(@Optional @CitrusResource TestCaseRunner runner){
        createDuck(runner, "red", 0.53, "rubber", "quack", "ACTIVE");
        saveDuckId(runner);
        updateDuck(runner, "${duckId}", "blue", 0.3, "rubber", "quack", "FIXED");
        checkDuckProperties(runner, "${duckId}", "blue", 0.3, "rubber", "quack", "FIXED");
    }

    @Test(description = "Проверка обновления цвета и звука утки")
    @CitrusTest
    public void successfulUpdateColorAndSound(@Optional @CitrusResource TestCaseRunner runner){

        createDuck(runner, "green", 0.60, "plastic", "quack", "ACTIVE");
        saveDuckId(runner);
        updateDuck(runner, "${duckId}", "yellow", 0.60, "plastic", "squeak", "ACTIVE");
        checkDuckProperties(runner, "${duckId}", "yellow", 0.60, "plastic", "squeak", "ACTIVE");
    }

    private void updateDuck(TestCaseRunner runner, String duckId, String color, double height, String material, String sound, String wingsState) {
        runner.$(
                http().client("http://localhost:2222").send().put("/api/duck/update")
                        .queryParam("color", color)
                        .queryParam("height", String.valueOf(height))
                        .queryParam("id", duckId)
                        .queryParam("material", material)
                        .queryParam("sound", sound)
                        .queryParam("wingsState", wingsState)
        );
    }

    private void checkDuckProperties(TestCaseRunner runner, String duckId, String expectedColor, double expectedHeight, String expectedMaterial, String expectedSound, String expectedWingsState) {
        runner.$(
                http().client("http://localhost:2222").send().get("/api/duck/action/properties").queryParam("id", duckId)
        );

        runner.$(
                http().client("http://localhost:2222").receive().response(HttpStatus.OK)
                        .message().contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body("{\n" +
                                "\"color\": \"" + expectedColor + "\",\n" +
                                "\"height\": " + expectedHeight + ",\n" +
                                "\"material\": \"" + expectedMaterial + "\",\n" +
                                "\"sound\": \"" + expectedSound + "\",\n" +
                                "\"wingsState\": \"" + expectedWingsState + "\"\n" +
                                "}")
        );
    }
}
