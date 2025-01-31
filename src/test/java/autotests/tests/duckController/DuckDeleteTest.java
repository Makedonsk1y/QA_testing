package autotests.tests.duckController;

import autotests.clients.DuckActionsClient;
import autotests.payloads.Duck;
import autotests.payloads.Message;
import autotests.payloads.WingsState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;


public class DuckDeleteTest extends DuckActionsClient {
    @Test(description = "Проверка удаления утки")
    @CitrusTest
    public void successfulDeleteDuck(@Optional @CitrusResource TestCaseRunner runner){
        Duck duck = new Duck().color("red").height(0.53).material("rubber").sound("quack").wingsState(WingsState.ACTIVE);
        createDuck(runner, duck);
        saveDuckId(runner);
        Message message = new Message().message("Duck is deleted");
        deleteDuck(runner);
        validateResponse(runner, message);
        checkDuckDeleted(runner);
    }
}
