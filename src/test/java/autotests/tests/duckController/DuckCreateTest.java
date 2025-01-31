package autotests.tests.duckController;

import autotests.clients.DuckActionsClient;
import autotests.payloads.Duck;
import autotests.payloads.WingsState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.DataProvider;
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


    Duck duck1 = new Duck().color("red").height(0.53).material("wood").sound("quack").wingsState(WingsState.FIXED);
    Duck duck2 = new Duck().color("yellow").height(0.53).material("wood").sound("quack").wingsState(WingsState.FIXED);
    Duck duck3 = new Duck().color("blue").height(0.40).material("wood").sound("quack").wingsState(WingsState.ACTIVE);
    Duck duck4 = new Duck().color("pink").height(0.70).material("plastic").sound("quack").wingsState(WingsState.ACTIVE);
    Duck duck5 = new Duck().color("black").height(0.99).material("metal").sound("squeak").wingsState(WingsState.ACTIVE);

    @DataProvider(name = "duckList")
    public Object[][] DuckProvider(){
        return new Object[][]{
                {duck1, "duckController/duckCreate/duckCreate1.json", null},
                {duck2, "duckController/duckCreate/duckCreate2.json", null},
                {duck3, "duckController/duckCreate/duckCreate3.json", null},
                {duck4, "duckController/duckCreate/duckCreate4.json", null},
                {duck5, "duckController/duckCreate/duckCreate5.json", null}
        };
    }
    @Test(description = "Проверка создания 5 уток", dataProvider = "duckList")
    @CitrusTest
    @CitrusParameters({"payload", "response", "runner"})
    public void createFiveDucks(Object body, String response, @Optional @CitrusResource TestCaseRunner runner){
        createDuck(runner, body);
        validateDuckWithGetId(runner, response);
        runner.$(
                doFinally().actions(deleteDuckDb(runner, "${duckId}"))
        );
    }
}
