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


public class DuckQuackTest extends DuckActionsClient {

    @Test (description = "Проверка того, что утки с четным id издают звуки")
    @CitrusTest
    public void successfulQuackWithEvenId(@Optional @CitrusResource TestCaseRunner runner){

        AtomicInteger id = new AtomicInteger();
        do {
            Duck duck = new Duck().id("@ignore@").color("green").height(0.50).material("plastic").sound("quack").wingsState(WingsState.ACTIVE);
            createDuck(runner, duck);
            saveDuckId(runner);

            runner.$(action -> {
                id.set(Integer.parseInt(action.getVariable("duckId")));
            });
        } while (id.get() % 2 != 0);
        duckQuackResources(runner, String.valueOf(id.get()), 2, 3, "duckActionController/duckQuack/duckQuack.json");
    }

    @Test (description = "Проверка того, что утки с нечетным id издают звуки")
    @CitrusTest
    public void successfulQuackWithOddId(@Optional @CitrusResource TestCaseRunner runner){

        AtomicInteger id = new AtomicInteger();
        do {
            Duck duck = new Duck().id("@ignore@").color("green").height(0.50).material("plastic").sound("quack").wingsState(WingsState.ACTIVE);
            createDuck(runner, duck);
            saveDuckId(runner);

            runner.$(action -> {
                id.set(Integer.parseInt(action.getVariable("duckId")));
            });
        } while (id.get() % 2 == 0);
        duckQuackResources(runner, String.valueOf(id.get()), 2, 3, "duckActionController/duckQuack/duckQuack.json");
    }
}
