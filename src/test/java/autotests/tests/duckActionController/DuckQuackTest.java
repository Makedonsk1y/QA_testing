package autotests.tests.duckActionController;

import autotests.clients.DuckActionsClient;
import autotests.payloads.Duck;
import autotests.payloads.Sound;
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
            Duck duck = new Duck().color("green").height(0.50).material("plastic").sound("quack").wingsState(WingsState.ACTIVE);
            createDuck(runner, duck);
            saveDuckId(runner);

            runner.$(action -> {
                id.set(Integer.parseInt(action.getVariable("duckId")));
            });
        } while (id.get() % 2 != 0);
        duckQuack(runner, 2, 3);
        validateResponseWithResource(runner,"duckActionController/duckQuack/duckQuack.json");
    }

    @Test (description = "Проверка того, что утки с нечетным id издают звуки")
    @CitrusTest
    public void successfulQuackWithOddId(@Optional @CitrusResource TestCaseRunner runner){

        AtomicInteger id = new AtomicInteger();
        do {
            Duck duck = new Duck().color("green").height(0.50).material("plastic").sound("quack").wingsState(WingsState.ACTIVE);
            createDuck(runner, duck);
            saveDuckId(runner);

            runner.$(action -> {
                id.set(Integer.parseInt(action.getVariable("duckId")));
            });
        } while (id.get() % 2 == 0);
        Sound sound = new Sound().sound("quack-quack-quack, quack-quack-quack");
        duckQuack(runner, 2, 3);
        validateResponse(runner, sound);
    }
}
