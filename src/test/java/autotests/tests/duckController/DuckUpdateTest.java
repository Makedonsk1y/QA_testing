package autotests.tests.duckController;

import autotests.clients.DuckActionsClient;
import autotests.payloads.Duck;
import autotests.payloads.WingsState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;



public class DuckUpdateTest extends DuckActionsClient {
    @Test(description = "Проверка обновления цвета и высоты утки")
    @CitrusTest
    public void successfulUpdateColorAndHeight(@Optional @CitrusResource TestCaseRunner runner){
        Duck duck = new Duck().color("red").height(0.53).material("rubber").sound("quack").wingsState(WingsState.ACTIVE);
        createDuck(runner, duck);
        saveDuckId(runner);
        updateDuck(runner,"blue", 0.30, "rubber", "quack", "FIXED");
        getDuckProperties(runner);
        validateDuckResponse(runner, "duckController/duckUpdate/checkDuckUpdateColorHeight.json");
    }

    @Test(description = "Проверка обновления цвета и звука утки")
    @CitrusTest
    public void successfulUpdateColorAndSound(@Optional @CitrusResource TestCaseRunner runner){
        Duck duck = new Duck().color("green").height(0.60).material("plastic").sound("quack").wingsState(WingsState.ACTIVE);
        createDuck(runner, duck);
        saveDuckId(runner);
        updateDuck(runner,"yellow", 0.60, "plastic", "squeak", "ACTIVE");
        getDuckProperties(runner);
        validateDuckResponse(runner, "duckController/duckUpdate/checkDuckUpdateColorSound.json");
    }
}
