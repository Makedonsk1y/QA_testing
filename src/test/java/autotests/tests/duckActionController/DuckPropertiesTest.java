package autotests.tests.duckActionController;

import autotests.clients.DuckActionsClient;
import autotests.payloads.Duck;
import autotests.payloads.WingsState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import java.util.concurrent.atomic.AtomicInteger;



public class DuckPropertiesTest extends DuckActionsClient {
    @Test(description = "Проверка того, что выводятся свойства уточки с четным ID")
    @CitrusTest
    public void successfulPropertiesEvenId(@Optional @CitrusResource TestCaseRunner runner) {
        AtomicInteger id = new AtomicInteger();
        do {
            Duck duck = new Duck().id("@ignore@").color("red").height(0.53).material("metal").sound("quack").wingsState(WingsState.FIXED);
            createDuck(runner, duck);
            saveDuckId(runner);

            runner.$(action -> {
                id.set(Integer.parseInt(action.getVariable("duckId")));
            });
        } while (id.get() % 2 != 0);

        getDuckProperties(runner, String.valueOf(id.get()), "duckActionController/duckGetProperties/duckPropertiesEvenId.json");
    }

    @Test(description = "Проверка того, что выводятся свойства уточки с нечетным id")
    @CitrusTest
    public void successfulPropertiesOddId(@Optional @CitrusResource TestCaseRunner runner) {

        AtomicInteger id = new AtomicInteger();
        do {
            Duck duck = new Duck().id("@ignore@").color("green").height(0.60).material("plastic").sound("squeak").wingsState(WingsState.ACTIVE);
            createDuck(runner, duck);
            saveDuckId(runner);

            runner.$(action -> {
                id.set(Integer.parseInt(action.getVariable("duckId")));
            });
        } while (id.get() % 2 == 0);

        getDuckProperties(runner, String.valueOf(id.get()), "duckActionController/duckGetProperties/duckPropertiesOddId.json");
    }
}
