package autotests.tests.duckController;

import autotests.clients.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;


public class DuckDeleteTest extends DuckActionsClient {
    @Test(description = "Проверка удаления утки")
    @CitrusTest
    public void successfulDeleteDuck(@Optional @CitrusResource TestCaseRunner runner){
        createDuck(runner, "red", 0.53, "rubber", "quack", "ACTIVE");
        saveDuckId(runner);
        deleteDuck(runner, "${duckId}");
        checkDuckDeleted(runner, "${duckId}");
    }
}
