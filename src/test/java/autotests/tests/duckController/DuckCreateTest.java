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
        Duck duck = new Duck().id("@ignore@").color("red").height(0.53).material("rubber").sound("quack").wingsState(WingsState.FIXED);
        createDuck(runner, duck);
        validateDuckResponse(runner, duck);
    }

    @Test(description = "Проверка создания утки с материалом wood")
    @CitrusTest
    public void createDuckWithMaterialWood(@Optional @CitrusResource TestCaseRunner runner){
        Duck duck = new Duck().id("@ignore@").color("red").height(0.53).material("wood").sound("quack").wingsState(WingsState.FIXED);
        createDuck(runner, duck);
        validateDuckResponse(runner, "duckController/duckCreate/duckCreateWood.json");
    }
}
