package autotests.tests.duckActionController;

import autotests.clients.DuckActionsClient;
import autotests.payloads.Duck;
import autotests.payloads.Message;
import autotests.payloads.WingsState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;



public class DuckSwimTest extends DuckActionsClient {
    @Test(description = "Проверка того, что уточка c корректным id поплыла")
    @CitrusTest
    public void successfulSwim(@Optional @CitrusResource TestCaseRunner runner){
        Duck duck = new Duck().color("red").height(0.43).material("wood").sound("quack").wingsState(WingsState.ACTIVE);
        createDuck(runner, duck);
        saveDuckId(runner);
        duckSwim(runner);
        Message message = new Message().message("I'm swimming");
        validateResponse(runner, message);
    }

    @Test(description = "Проверка, плавает ли уточка с несуществующим id")
    @CitrusTest
    public void swimWithInvalidId(@Optional @CitrusResource TestCaseRunner runner){
        Duck duck = new Duck().color("red").height(0.43).material("wood").sound("quack").wingsState(WingsState.ACTIVE);
        createDuck(runner, duck);
        saveDuckId(runner);
        deleteDuck(runner);
        duckSwim(runner);
        validateBadResponse(runner);
    }
}
