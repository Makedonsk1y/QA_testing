package autotests.tests.duckActionController;

import autotests.clients.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;



public class DuckSwimTest extends DuckActionsClient {
    @Test(description = "Проверка того, что уточка c корректным id поплыла")
    @CitrusTest
    public void successfulSwim(@Optional @CitrusResource TestCaseRunner runner){
        createDuck(runner, "red", 0.43, "wood", "quack", "ACTIVE");
        saveDuckId(runner);
        duckSwim(runner, "${duckId}");
        validateResponse(runner, "I'm swimming");
    }

    @Test(description = "Проверка, плавает ли уточка с несуществующим id")
    @CitrusTest
    public void swimWithInvalidId(@Optional @CitrusResource TestCaseRunner runner){
        createDuck(runner, "red", 0.43, "wood", "quack", "ACTIVE");
        saveDuckId(runner);
        deleteDuck(runner, "${duckId}");
        duckSwim(runner, "${duckId}");
        validateBadResponse(runner);
    }
}
