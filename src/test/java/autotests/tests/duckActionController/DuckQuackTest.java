package autotests.tests.duckActionController;

import autotests.clients.DuckActionsClient;
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
            createDuck(runner, "green", 0.50, "plastic", "quack", "ACTIVE");
            saveDuckId(runner);

            runner.$(action -> {
                id.set(Integer.parseInt(action.getVariable("duckId")));
            });
        } while (id.get() % 2 != 0);
        duckQuack(runner, String.valueOf(id.get()), 2, 3, "quack-quack-quack, quack-quack-quack");
    }

    @Test (description = "Проверка того, что утки с нечетным id издают звуки")
    @CitrusTest
    public void successfulQuackWithOddId(@Optional @CitrusResource TestCaseRunner runner){

        AtomicInteger id = new AtomicInteger();
        do {
            createDuck(runner, "green", 0.50, "plastic", "quack", "ACTIVE");
            saveDuckId(runner);

            runner.$(action -> {
                id.set(Integer.parseInt(action.getVariable("duckId")));
            });
        } while (id.get() % 2 == 0);
        duckQuack(runner, String.valueOf(id.get()), 2, 3, "quack-quack-quack, quack-quack-quack");
    }
}
