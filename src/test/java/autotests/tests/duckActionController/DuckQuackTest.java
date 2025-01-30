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

import static com.consol.citrus.container.FinallySequence.Builder.doFinally;


public class DuckQuackTest extends DuckActionsClient {

    @Test (description = "Проверка того, что утки с четным id издают звуки")
    @CitrusTest
    public void successfulQuackWithEvenId(@Optional @CitrusResource TestCaseRunner runner){
        runner.variable("duckId", "222");
        runner.$(
                doFinally().actions(deleteDuckDb(runner, "${duckId}"))
        );
        Duck duck = new Duck().color("green").height(0.50).material("plastic").sound("quack").wingsState(WingsState.ACTIVE);
        insertDuckDb(runner,duck.color(),String.valueOf(duck.height()), duck.material(), duck.sound(), duck.wingsState().toString());
        duckQuack(runner, 2, 3);
        validateResponseWithResource(runner,"duckActionController/duckQuack/duckQuack.json");
    }

    @Test (description = "Проверка того, что утки с нечетным id издают звуки")
    @CitrusTest
    public void successfulQuackWithOddId(@Optional @CitrusResource TestCaseRunner runner){
        runner.variable("duckId", "221");
        runner.$(
                doFinally().actions(deleteDuckDb(runner, "${duckId}"))
        );
        Duck duck = new Duck().color("green").height(0.50).material("plastic").sound("quack").wingsState(WingsState.ACTIVE);
        insertDuckDb(runner,duck.color(),String.valueOf(duck.height()), duck.material(), duck.sound(), duck.wingsState().toString());
        Sound sound = new Sound().sound("quack-quack-quack, quack-quack-quack");
        duckQuack(runner, 2, 3);
        validateResponse(runner, sound);
    }
}
