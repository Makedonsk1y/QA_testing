package autotests.tests.duckController;

import autotests.clients.DuckActionsClient;
import autotests.payloads.Duck;
import autotests.payloads.WingsState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;



public class DuckCreateTest extends DuckActionsClient {
    @Test(description = "Проверка создания утки с материалом rubber")
    @CitrusTest
    public void createDuckWithMaterialRubber(@Optional @CitrusResource TestCaseRunner runner){
        Duck duck = new Duck().color("red").height(0.53).material("rubber").sound("quack").wingsState(WingsState.FIXED);
        createDuck(runner, duck);
        validateDuckResponse(runner, duck);
    }

    @Test(description = "Проверка создания утки с материалом rubber через БД")
    @CitrusTest
    public void createDuckWithMaterialRubberDb(@Optional @CitrusResource TestCaseRunner runner){
        Duck duck = new Duck().color("red").height(0.53).material("rubber").sound("quack").wingsState(WingsState.FIXED);
        createDuck(runner, duck);
        saveDuckId(runner);
        validateDuckDb(runner,"${duckId}",duck.color(), String.valueOf(duck.height()), duck.material(), duck.sound(), duck.wingsState().toString());
        deleteDuckDb(runner, "${duckId}");
    }

    @Test(description = "Проверка создания утки с материалом wood")
    @CitrusTest
    public void createDuckWithMaterialWood(@Optional @CitrusResource TestCaseRunner runner){
        Duck duck = new Duck().color("red").height(0.53).material("wood").sound("quack").wingsState(WingsState.FIXED);
        createDuck(runner, duck);
        validateDuckResponse(runner, "duckController/duckCreate/duckCreateWood.json");
    }

    @Test(description = "Проверка создания утки с материалом wood через БД")
    @CitrusTest
    public void createDuckWithMaterialWoodDb(@Optional @CitrusResource TestCaseRunner runner){
        Duck duck = new Duck().color("red").height(0.53).material("wood").sound("quack").wingsState(WingsState.FIXED);
        createDuck(runner, duck);
        saveDuckId(runner);
        validateDuckDb(runner,"${duckId}",duck.color(), String.valueOf(duck.height()), duck.material(), duck.sound(), duck.wingsState().toString());
        deleteDuckDb(runner, "${duckId}");
    }
}
