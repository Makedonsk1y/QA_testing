package autotests.tests.duckActionController;


import autotests.clients.DuckActionsClient;
import autotests.payloads.Duck;
import autotests.payloads.WingsState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.container.FinallySequence.Builder.doFinally;


public class DuckFlyTest extends DuckActionsClient {

    @Test (description = "Проверка полета утки с состоянием крыльев ACTIVE")
    @CitrusTest
    public void successfulFlyWithActiveWingsState(@Optional @CitrusResource TestCaseRunner runner){
        runner.variable("duckId", "222");
        runner.$(
                doFinally().actions(deleteDuckDb(runner, "${duckId}"))
        );
        Duck duck = new Duck().color("red").height(0.53).material("metal").sound("quack").wingsState(WingsState.ACTIVE);
        insertDuckDb(runner,duck.color(),String.valueOf(duck.height()), duck.material(), duck.sound(), duck.wingsState().toString());
        duckFly(runner);
        validateResponseWithResource(runner, "duckActionController/duckFly/duckFlyActiveWings.json");
    }

    @Test (description = "Проверка полета утки с состоянием крыльев FIXED")
    @CitrusTest
    public void successfulFlyWithFixedWingsState(@Optional @CitrusResource TestCaseRunner runner){
        runner.variable("duckId", "222");
        runner.$(
                doFinally().actions(deleteDuckDb(runner, "${duckId}"))
        );
        Duck duck = new Duck().color("red").height(0.53).material("metal").sound("quack").wingsState(WingsState.FIXED);
        insertDuckDb(runner,duck.color(),String.valueOf(duck.height()), duck.material(), duck.sound(), duck.wingsState().toString());
        duckFly(runner);
        validateResponseWithResource(runner, "duckActionController/duckFly/duckFlyFixedWings.json");
    }

    @Test (description = "Проверка полета утки с состоянием крыльев UNDEFINED")
    @CitrusTest
    public void successfulFlyWithUndefinedWingsState(@Optional @CitrusResource TestCaseRunner runner){
        runner.variable("duckId", "222");
        runner.$(
                doFinally().actions(deleteDuckDb(runner, "${duckId}"))
        );
        Duck duck = new Duck().color("red").height(0.53).material("metal").sound("quack").wingsState(WingsState.UNDEFINED);
        insertDuckDb(runner,duck.color(),String.valueOf(duck.height()), duck.material(), duck.sound(), duck.wingsState().toString());
        duckFly(runner);
        validateResponseWithResource(runner, "duckActionController/duckFly/duckFlyUndefinedWings.json");
    }
}
