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

public class DuckDeleteTest extends DuckBaseTest {
    @Test(description = "Проверка удаления утки")
    @CitrusTest
    public void successfulDeleteDuck(@Optional @CitrusResource TestCaseRunner runner){

        createDuckAndSaveId(runner, "red", 0.53, "rubber", "quack", "ACTIVE");
        deleteDuck(runner, "${duckId}");
        checkDuckDeleted(runner, "${duckId}");
    }

    private void createDuckAndSaveId(TestCaseRunner runner, String color, double height, String material, String sound, String wingsState) {
        createDuck(runner, color, height, material, sound, wingsState);
        saveDuckId(runner);
    }

    private void checkDuckDeleted(TestCaseRunner runner, String duckId) {
        runner.$(
                http().client("http://localhost:2222").send().get("/api/duck/action/properties").queryParam("id", duckId)
        );

        runner.$(
                http().client("http://localhost:2222").receive().response(HttpStatus.INTERNAL_SERVER_ERROR)
        );
    }
}
