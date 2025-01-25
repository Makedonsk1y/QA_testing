package autotests.tests.duckController;

import autotests.clients.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;



public class DuckUpdateTest extends DuckActionsClient {
    @Test(description = "Проверка обновления цвета и высоты утки")
    @CitrusTest
    public void successfulUpdateColorAndHeight(@Optional @CitrusResource TestCaseRunner runner){
        createDuck(runner, "red", 0.53, "rubber", "quack", "ACTIVE");
        saveDuckId(runner);
        updateDuck(runner, "${duckId}", "blue", 0.3, "rubber", "quack", "FIXED");
        getDuckProperties(runner, "${duckId}", "blue", 0.3, "rubber", "quack", "FIXED");
    }

    @Test(description = "Проверка обновления цвета и звука утки")
    @CitrusTest
    public void successfulUpdateColorAndSound(@Optional @CitrusResource TestCaseRunner runner){
        createDuck(runner, "green", 0.60, "plastic", "quack", "ACTIVE");
        saveDuckId(runner);
        updateDuck(runner, "${duckId}", "yellow", 0.60, "plastic", "squeak", "ACTIVE");
        getDuckProperties(runner, "${duckId}", "yellow", 0.60, "plastic", "squeak", "ACTIVE");
    }
}
