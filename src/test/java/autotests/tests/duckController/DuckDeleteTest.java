package autotests.tests.duckController;

import autotests.clients.DuckActionsClient;
import autotests.payloads.Duck;
import autotests.payloads.Message;
import autotests.payloads.WingsState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

@Epic("Duck Controller")
@Feature("Duck Delete /api/duck/delete")
public class DuckDeleteTest extends DuckActionsClient {
    @Test(description = "Проверка удаления утки")
    @CitrusTest
    public void successfulDeleteDuck(@Optional @CitrusResource TestCaseRunner runner){
        runner.variable("duckId", "1234567");
        Duck duck = new Duck().color("red").height(0.53).material("rubber").sound("quack").wingsState(WingsState.ACTIVE);
        insertDuckDb(runner, duck.color(),String.valueOf(duck.height()), duck.material(), duck.sound(), duck.wingsState().toString());
        Message message = new Message().message("Duck is deleted");
        deleteDuck(runner);
        validateResponse(runner, message);
        checkDuckDeleted(runner);
    }
}
