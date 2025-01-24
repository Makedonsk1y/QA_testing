package autotests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.DefaultTestActionBuilder.action;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;


public class DuckSwimTest extends DuckBaseTest {
    @Test(description = "Проверка того, что уточка c корректным id поплыла")
    @CitrusTest
    public void successfulSwim(@Optional @CitrusResource TestCaseRunner runner){
        createDuck(runner, "red", 0.43, "wood", "quack", "ACTIVE");
        saveDuckId(runner);
        duckSwim(runner, "${duckId}");
        validateResponse(runner, "I'm swimming");
    }

    @Test(description = "Проверка того, что уточка c несуществующим id не плавает")
    @CitrusTest
    public void swimWithInvalidId(@Optional @CitrusResource TestCaseRunner runner){
       // int[] existingIds = getAllDuckIds(runner);
       // int invalidId = generateInvalidId(existingIds);
        createDuck(runner, "red", 0.43, "wood", "quack", "ACTIVE");
        saveDuckId(runner);
        deleteDuck(runner, "${duckId}");
        duckSwim(runner, "${duckId}");
        validateBadResponse(runner);
    }

    public void duckSwim(TestCaseRunner runner, String id){
        runner.$(
                http().client("http://localhost:2222").send().get("/api/duck/action/swim").queryParam("id", id)
        );
    }

    public void validateBadResponse(TestCaseRunner runner){
        runner.$(
                http().client("http://localhost:2222").receive().response(HttpStatus.NOT_FOUND).
                        message().contentType(MediaType.APPLICATION_JSON_VALUE)
        );
    }

    public int[] getAllDuckIds(TestCaseRunner runner) {
        // Отправляем запрос на получение всех ID уток
        runner.$(
                http().client("http://localhost:2222").send().get("/api/duck/getAllIds")
        );

        // Создаем переменную для хранения ответа
        final String[] response = new String[1];

        // Извлекаем переменную response из контекста
        runner.$(action(testContext ->  {
            response[0] = testContext.getVariable("response"); // Извлекаем ответ
        }));

        // Обрабатываем ответ и извлекаем ID
        String[] idStrings = response[0].replaceAll("[\\[\\]]", "").split(",");
        int[] ids = new int[idStrings.length];

        for (int i = 0; i < idStrings.length; i++) {
            ids[i] = Integer.parseInt(idStrings[i].trim()); // Преобразуем строки в целые числа
        }

        return ids; // Возвращаем массив ID
    }

    private int generateInvalidId(int[] existingIds) {
        int maxId = 0;
        for (int id : existingIds) {
            if (id > maxId) {
                maxId = id;
            }
        }
        return maxId + 1;
    }
}
