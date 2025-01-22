package autotests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;
import static com.consol.citrus.validation.DelegatingPayloadVariableExtractor.Builder.fromBody;

public class GetAllDucksIdsTest extends DuckBaseTest {
    private List<String> createdDuckIds = new ArrayList<>();
    @Test(description = "Проверка получения всех ID уток")
    @CitrusTest
    public void successfulGetAllIds(@Optional @CitrusResource TestCaseRunner runner){
        // Создаем несколько уток
        createDuckAndSaveId(runner, "red", 0.53, "metal", "quack", "ACTIVE");
        createDuckAndSaveId(runner, "blue", 0.60, "plastic", "quack", "FIXED");
        createDuckAndSaveId(runner, "green", 0.55, "wood", "quack", "UNDEFINED");

        getAllDuckIds(runner);
        validateGetAllIdsResponse(runner, createdDuckIds);
    }

    private void createDuckAndSaveId(TestCaseRunner runner, String color, double height, String material, String sound, String wingsState) {
        createDuck(runner, color, height, material, sound, wingsState);
        saveDuckId(runner);
    }

    public void getAllDuckIds(TestCaseRunner runner){
        runner.$(
                http().client("http://localhost:2222").send().get("/api/duck/getAllIds")
        );
    }

    public void validateGetAllIdsResponse(TestCaseRunner runner, List<String> expectedIds){
        runner.$(
                http().client("http://localhost:2222").receive().response(HttpStatus.OK)
                        .message().contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(expectedIds.toString().replaceAll("[\\[\\] ]", "") )
        );
    }
}
