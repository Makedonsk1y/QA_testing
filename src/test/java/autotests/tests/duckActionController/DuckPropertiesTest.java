package autotests.tests.duckActionController;

import autotests.clients.DuckActionsClient;
import autotests.payloads.Duck;
import autotests.payloads.Properties;
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
@Feature("Duck Get Properties /api/duck/action/properties")
public class DuckPropertiesTest extends DuckActionsClient {
    @Test(description = "Проверка того, что выводятся свойства уточки с четным ID")
    @CitrusTest
    public void successfulPropertiesEvenId(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("duckId", "222");
        runner.$(
                doFinally().actions(deleteDuckDb(runner, "${duckId}"))
        );
        Duck duck = new Duck().color("red").height(0.53).material("metal").sound("quack").wingsState(WingsState.FIXED);
        insertDuckDb(runner,duck.color(),String.valueOf(duck.height()), duck.material(), duck.sound(), duck.wingsState().toString());
        getDuckProperties(runner);
        validateResponseWithResource(runner, "duckActionController/duckGetProperties/duckPropertiesEvenId.json");
    }

    @Test(description = "Проверка того, что выводятся свойства уточки с нечетным id")
    @CitrusTest
    public void successfulPropertiesOddId(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("duckId", "221");
        runner.$(
                doFinally().actions(deleteDuckDb(runner, "${duckId}"))
        );
        Duck duck = new Duck().color("green").height(0.60).material("plastic").sound("squeak").wingsState(WingsState.ACTIVE);
        insertDuckDb(runner,duck.color(),String.valueOf(duck.height()), duck.material(), duck.sound(), duck.wingsState().toString());
        Properties properties = new Properties().color("green").height(0.60).material("plastic").sound("squeak").wingsState(WingsState.ACTIVE);
        getDuckProperties(runner);
        validateResponse(runner, properties);
    }
}
