package autotests.tests.duckController;

import autotests.clients.DuckActionsClient;
import autotests.payloads.Duck;
import autotests.payloads.WingsState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.container.FinallySequence.Builder.doFinally;


public class DuckUpdateTest extends DuckActionsClient {
    @Test(description = "Проверка обновления цвета и высоты утки")
    @CitrusTest
    public void successfulUpdateColorAndHeight(@Optional @CitrusResource TestCaseRunner runner){
        runner.variable("duckId", "222");
        runner.$(
                doFinally().actions(deleteDuckDb(runner, "${duckId}"))
        );
        Duck duck = new Duck().color("red").height(0.53).material("rubber").sound("quack").wingsState(WingsState.ACTIVE);
        insertDuckDb(runner,duck.color(),String.valueOf(duck.height()), duck.material(), duck.sound(), duck.wingsState().toString());
        updateDuck(runner,"blue", 0.30, "rubber", "quack", "FIXED");
        getDuckProperties(runner);
        validateDuckResponse(runner, "duckController/duckUpdate/checkDuckUpdateColorHeight.json");
    }

    @Test(description = "Проверка обновления цвета и высоты утки через БД")
    @CitrusTest
    public void successfulUpdateColorAndHeightDb(@Optional @CitrusResource TestCaseRunner runner){
        runner.variable("duckId", "222");
        runner.$(
                doFinally().actions(deleteDuckDb(runner, "${duckId}"))
        );
        Duck duck = new Duck().color("red").height(0.53).material("rubber").sound("quack").wingsState(WingsState.ACTIVE);
        insertDuckDb(runner,duck.color(),String.valueOf(duck.height()), duck.material(), duck.sound(), duck.wingsState().toString());
        updateDuck(runner,"blue", 0.30, "rubber", "quack", "FIXED");
        validateDuckDb(runner, "${duckId}", "blue", String.valueOf(0.30), "rubber", "quack", "FIXED");
    }

    @Test(description = "Проверка обновления цвета и звука утки")
    @CitrusTest
    public void successfulUpdateColorAndSound(@Optional @CitrusResource TestCaseRunner runner){
        runner.variable("duckId", "222");
        runner.$(
                doFinally().actions(deleteDuckDb(runner, "${duckId}"))
        );
        Duck duck = new Duck().color("green").height(0.60).material("plastic").sound("quack").wingsState(WingsState.ACTIVE);
        insertDuckDb(runner,duck.color(),String.valueOf(duck.height()), duck.material(), duck.sound(), duck.wingsState().toString());
        updateDuck(runner,"yellow", 0.60, "plastic", "squeak", "ACTIVE");
        getDuckProperties(runner);
        validateDuckResponse(runner, "duckController/duckUpdate/checkDuckUpdateColorSound.json");
    }

    @Test(description = "Проверка обновления цвета и звука утки через БД")
    @CitrusTest
    public void successfulUpdateColorAndSoundDb(@Optional @CitrusResource TestCaseRunner runner){
        runner.variable("duckId", "222");
        runner.$(
                doFinally().actions(deleteDuckDb(runner, "${duckId}"))
        );
        Duck duck = new Duck().color("green").height(0.60).material("plastic").sound("quack").wingsState(WingsState.ACTIVE);
        insertDuckDb(runner,duck.color(),String.valueOf(duck.height()), duck.material(), duck.sound(), duck.wingsState().toString());
        updateDuck(runner,"yellow", 0.60, "plastic", "squeak", "ACTIVE");
        validateDuckDb(runner, "${duckId}", "yellow", String.valueOf(0.60), "plastic", "squeak", "ACTIVE");
    }
}
