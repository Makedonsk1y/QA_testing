package autotests.tests.duckController;

import autotests.clients.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;



public class DuckCreateTest extends DuckActionsClient {
    @Test(description = "Проверка создания утки с материалом rubber")
    @CitrusTest
    public void createDuckWithMaterialRubber(@Optional @CitrusResource TestCaseRunner runner){
        createDuck(runner, "red", 0.53, "rubber", "quack", "FIXED");
        validateDuckResponse(runner, "red", 0.53, "rubber", "quack", "FIXED");
    }

    @Test(description = "Проверка создания утки с материалом wood")
    @CitrusTest
    public void createDuckWithMaterialWood(@Optional @CitrusResource TestCaseRunner runner){
        createDuck(runner, "red", 0.53, "wood", "quack", "FIXED");
        validateDuckResponse(runner, "red", 0.53, "wood", "quack", "FIXED");
    }
}
