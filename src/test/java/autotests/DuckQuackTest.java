package autotests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import java.util.concurrent.atomic.AtomicInteger;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;


public class DuckQuackTest extends DuckBaseTest {

    @Test (description = "Проверка того, что утки с четным id издают звуки")
    @CitrusTest
    public void successfulQuackWithEvenId(@Optional @CitrusResource TestCaseRunner runner){

        AtomicInteger id = new AtomicInteger();
        do {
            createDuck(runner, "green", 0.50, "plastic", "quack", "ACTIVE");
            saveDuckId(runner);

            runner.$(action -> {
                id.set(Integer.parseInt(action.getVariable("duckId")));
            });
        } while (id.get() % 2 != 0);
        checkDuckQuack(runner, String.valueOf(id.get()), 2, 3, "quack-quack-quack, quack-quack-quack");
    }

    @Test (description = "Проверка того, что утки с нечетным id издают звуки")
    @CitrusTest
    public void successfulQuackWithOddId(@Optional @CitrusResource TestCaseRunner runner){

        AtomicInteger id = new AtomicInteger();
        do {
            createDuck(runner, "green", 0.50, "plastic", "quack", "ACTIVE");
            saveDuckId(runner);

            runner.$(action -> {
                id.set(Integer.parseInt(action.getVariable("duckId")));
            });
        } while (id.get() % 2 == 0);
        checkDuckQuack(runner, String.valueOf(id.get()), 2, 3, "quack-quack-quack, quack-quack-quack");
    }

    private void checkDuckQuack(TestCaseRunner runner, String duckId, int repetitionCount, int soundCount, String expectedSound) {
        runner.$(
                http().client("http://localhost:2222").send().get("/api/duck/action/quack")
                        .queryParam("id", duckId)
                        .queryParam("repetitionCount", String.valueOf(repetitionCount))
                        .queryParam("soundCount", String.valueOf(soundCount))
        );

        runner.$(
                http().client("http://localhost:2222").receive().response(HttpStatus.OK)
                        .message().contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body("{\n" +
                                "\"sound\": \"" + expectedSound + "\"\n" +
                                "}")
        );
    }
}
