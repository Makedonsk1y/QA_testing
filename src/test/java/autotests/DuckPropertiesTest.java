package autotests;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import java.util.concurrent.atomic.AtomicInteger;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;


public class DuckPropertiesTest extends DuckBaseTest {
    @Test(description = "Проверка того, что выводятся свойства уточки с четным ID")
    @CitrusTest
    public void successfulPropertiesEvenId(@Optional @CitrusResource TestCaseRunner runner) {
        String duckColor = "red";
        double duckHeight = 0.53;
        String duckMaterial = "metal";
        String duckSound = "quack";
        String duckWingsState = "FIXED";

        AtomicInteger id = new AtomicInteger();
        do {
            createDuck(runner, duckColor, duckHeight, duckMaterial, duckSound, duckWingsState);
            saveDuckId(runner);

            runner.$(action -> {
                id.set(Integer.parseInt(action.getVariable("duckId")));
            });
        } while (id.get() % 2 != 0);

        getDuckProperties(runner, String.valueOf(id.get()));
        validateDuckProperties(runner, duckColor, duckHeight, duckMaterial, duckSound, duckWingsState);
    }

    @Test(description = "Проверка того, что выводятся свойства уточки с нечетным id")
    @CitrusTest
    public void successfulPropertiesOddId(@Optional @CitrusResource TestCaseRunner runner) {
        String duckColor = "green";
        double duckHeight = 0.60;
        String duckMaterial = "plastic";
        String duckSound = "squeak";
        String duckWingsState = "ACTIVE";

        AtomicInteger id = new AtomicInteger();
        do {
            createDuck(runner, duckColor, duckHeight, duckMaterial, duckSound, duckWingsState);
            saveDuckId(runner);

            runner.$(action -> {
                id.set(Integer.parseInt(action.getVariable("duckId")));
            });
        } while (id.get() % 2 == 0);

        getDuckProperties(runner, String.valueOf(id.get()));
        validateDuckProperties(runner, duckColor, duckHeight, duckMaterial, duckSound, duckWingsState);
    }

    public void getDuckProperties(TestCaseRunner runner, String id){
        runner.$(
                http().client("http://localhost:2222").send().get("/api/duck/action/properties").queryParam("id", id)
        );
    }

    public void validateDuckProperties(TestCaseRunner runner, String color, double height, String material, String sound, String wingsState){
        runner.$(
                http().client("http://localhost:2222").receive().response(HttpStatus.OK)
                        .message().contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body("{\n" +
                                "\"color\": \"" + color + "\",\n" +
                                "\"height\": " + height + ",\n" +
                                "\"material\": \"" + material + "\",\n" +
                                "\"sound\": \"" + sound + "\",\n" +
                                "\"wingsState\": \"" + wingsState + "\"\n" +
                                "}")
        );
    }
}
