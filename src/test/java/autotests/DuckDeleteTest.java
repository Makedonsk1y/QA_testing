package autotests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckDeleteTest extends DuckBaseTest {
    @Test(description = "Проверка удаления утки")
    @CitrusTest
    public void successfulDeleteDuck(@Optional @CitrusResource TestCaseRunner runner){
        createDuck(runner, "red", 0.53, "rubber", "quack", "ACTIVE");
        saveDuckId(runner);
        deleteDuck(runner, "${duckId}");
        checkDuckDeleted(runner, "${duckId}");
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
