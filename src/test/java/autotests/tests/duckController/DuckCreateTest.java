package autotests.tests.duckController;

import autotests.clients.DuckActionsClient;
import autotests.payloads.Duck;
import autotests.payloads.WingsState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.container.FinallySequence.Builder.doFinally;


@Epic("Duck Controller")
@Feature("Duck create /api/duck/create")
public class DuckCreateTest extends DuckActionsClient {
    @Test(description = "Проверка создания утки с материалом rubber")
    @CitrusTest
    public void createDuckWithMaterialRubber(@Optional @CitrusResource TestCaseRunner runner){
        Duck duck = new Duck().color("red").height(0.53).material("rubber").sound("quack").wingsState(WingsState.FIXED);
        createDuck(runner, duck);
        validateDuckWithGetId(runner, "duckController/duckCreate/duckCreateRubber.json");
        validateDuckDb(runner, "${duckId}", duck.color(), String.valueOf(duck.height()), duck.material(), duck.sound(), duck.wingsState().toString());
        runner.$(
                doFinally().actions(deleteDuckDb(runner, "${duckId}"))
        );
    }

    @Test(description = "Проверка создания утки с материалом wood")
    @CitrusTest
    public void createDuckWithMaterialWood(@Optional @CitrusResource TestCaseRunner runner){
        Duck duck = new Duck().color("red").height(0.53).material("wood").sound("quack").wingsState(WingsState.FIXED);
        createDuck(runner, duck);
        validateDuckWithGetId(runner, "duckController/duckCreate/duckCreateWood.json");
        validateDuckDb(runner, "${duckId}", duck.color(), String.valueOf(duck.height()), duck.material(), duck.sound(), duck.wingsState().toString());
        runner.$(
                doFinally().actions(deleteDuckDb(runner, "${duckId}"))
        );
    }
}
