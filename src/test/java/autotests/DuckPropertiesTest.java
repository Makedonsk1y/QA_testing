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

public class DuckPropertiesTest extends TestNGCitrusSpringSupport {
    @Test(description = "Проверка того, что выводятся свойства уточки")
    @CitrusTest
    public void successfulProperties(@Optional @CitrusResource TestCaseRunner runner){
        String duckColor = "red";
        double duckHeight = 0.53;
        String duckMaterial = "metal";
        String duckSound = "quack";
        String duckWingsState = "FIXED";

        createDuck(runner, duckColor, duckHeight, duckMaterial, duckSound, duckWingsState);
        saveDuckId(runner);
        getDuckProperties(runner, "${duckId}");
        validateResponse(runner, duckColor, duckHeight, duckMaterial, duckSound, duckWingsState);
    }

    public void getDuckProperties(TestCaseRunner runner, String id){
        runner.$(
                http().client("http://localhost:2222").send().get("/api/duck/action/properties").queryParam("id", id)
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

    public void validateResponse(TestCaseRunner runner, String color, double height, String material, String sound, String wingsState){
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
