package autotests.tests.duckActionController;

import autotests.clients.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import java.util.concurrent.atomic.AtomicInteger;



public class DuckPropertiesTest extends DuckActionsClient {
    @Test(description = "Проверка того, что выводятся свойства уточки с четным ID")
    @CitrusTest
    public void successfulPropertiesEvenId(@Optional @CitrusResource TestCaseRunner runner) {
        String duckColor = "red";
        double duckHeight = 0.53;
        String duckMaterial = "metal";
        String duckSound = "quack";
        String duckWingsState = "FIXED";

        AtomicInteger id = new AtomicInteger();
        do {
            createDuck(runner, duckColor, duckHeight, duckMaterial, duckSound, duckWingsState);
            saveDuckId(runner);

            runner.$(action -> {
                id.set(Integer.parseInt(action.getVariable("duckId")));
            });
        } while (id.get() % 2 != 0);

        getDuckProperties(runner, String.valueOf(id.get()), duckColor, duckHeight, duckMaterial, duckSound, duckWingsState);
    }

    @Test(description = "Проверка того, что выводятся свойства уточки с нечетным id")
    @CitrusTest
    public void successfulPropertiesOddId(@Optional @CitrusResource TestCaseRunner runner) {
        String duckColor = "green";
        double duckHeight = 0.60;
        String duckMaterial = "plastic";
        String duckSound = "squeak";
        String duckWingsState = "ACTIVE";

        AtomicInteger id = new AtomicInteger();
        do {
            createDuck(runner, duckColor, duckHeight, duckMaterial, duckSound, duckWingsState);
            saveDuckId(runner);

            runner.$(action -> {
                id.set(Integer.parseInt(action.getVariable("duckId")));
            });
        } while (id.get() % 2 == 0);

        getDuckProperties(runner, String.valueOf(id.get()), duckColor, duckHeight, duckMaterial, duckSound, duckWingsState);
    }
}
