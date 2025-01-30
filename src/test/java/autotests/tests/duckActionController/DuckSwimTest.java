package autotests.tests.duckActionController;

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

import static com.consol.citrus.container.FinallySequence.Builder.doFinally;

@Epic("Duck Action Controller")
@Feature("Duck Swim /api/duck/action/swim")
public class DuckSwimTest extends DuckActionsClient {
    @Test(description = "Проверка того, что уточка c корректным id поплыла")
    @CitrusTest
    public void successfulSwim(@Optional @CitrusResource TestCaseRunner runner){
        runner.variable("duckId", "222");
        runner.$(
                doFinally().actions(deleteDuckDb(runner, "${duckId}"))
        );
        Duck duck = new Duck().color("red").height(0.43).material("wood").sound("quack").wingsState(WingsState.ACTIVE);
        insertDuckDb(runner,duck.color(),String.valueOf(duck.height()), duck.material(), duck.sound(), duck.wingsState().toString());
        duckSwim(runner);
        Message message = new Message().message("I'm swimming");
        validateResponse(runner, message);
    }

    @Test(description = "Проверка, плавает ли уточка с несуществующим id")
    @CitrusTest
    public void swimWithInvalidId(@Optional @CitrusResource TestCaseRunner runner){
        runner.variable("duckId", "222");
        runner.$(
                doFinally().actions(deleteDuckDb(runner, "${duckId}"))
        );
        Duck duck = new Duck().color("red").height(0.43).material("wood").sound("quack").wingsState(WingsState.ACTIVE);
        insertDuckDb(runner,duck.color(),String.valueOf(duck.height()), duck.material(), duck.sound(), duck.wingsState().toString());
        deleteDuck(runner);
        duckSwim(runner);
        validateBadResponse(runner);
    }
}
