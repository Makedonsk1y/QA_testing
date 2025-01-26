package autotests.tests.duckActionController;


import autotests.clients.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;



public class DuckFlyTest extends DuckActionsClient {

    @Test (description = "Проверка полета утки с состоянием крыльев ACTIVE")
    @CitrusTest
    public void successfulFlyWithActiveWingsState(@Optional @CitrusResource TestCaseRunner runner){
        createDuck(runner, "red", 0.53, "metal", "quack", "ACTIVE");
        saveDuckId(runner);
        duckFly(runner, "${duckId}");
        validateResponse(runner, "I'm flying");
    }

    @Test (description = "Проверка полета утки с состоянием крыльев FIXED")
    @CitrusTest
    public void successfulFlyWithFixedWingsState(@Optional @CitrusResource TestCaseRunner runner){
        createDuck(runner, "red", 0.53, "metal", "quack", "FIXED");
        saveDuckId(runner);
        duckFly(runner, "${duckId}");
        validateResponse(runner, "I can't fly");
    }

    @Test (description = "Проверка полета утки с состоянием крыльев UNDEFINED")
    @CitrusTest
    public void successfulFlyWithUndefinedWingsState(@Optional @CitrusResource TestCaseRunner runner){
        createDuck(runner, "red", 0.53, "metal", "quack", "FIXED");
        saveDuckId(runner);
        duckFly(runner, "${duckId}");
        validateResponse(runner, "Wings are not detected");
    }
}
