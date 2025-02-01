package autotests.clients;

import autotests.BaseTest;
import autotests.EndpointConfig;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.actions.ExecuteSQLAction;
import io.qameta.allure.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.context.ContextConfiguration;

import static com.consol.citrus.actions.ExecuteSQLAction.Builder.sql;
import static com.consol.citrus.actions.ExecuteSQLQueryAction.Builder.query;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;
import static com.consol.citrus.validation.DelegatingPayloadVariableExtractor.Builder.fromBody;


@ContextConfiguration(classes = {EndpointConfig.class})
public class DuckActionsClient extends BaseTest {

    //DB methods

    @Step("Delete duck from DB")
    public ExecuteSQLAction deleteDuckDb(TestCaseRunner runner, String id){
        return runner.$(
                sql(testDb).statement("DELETE FROM DUCK WHERE ID=" + id)
        );
    }

    @Step("Validate duck DB")
    public void validateDuckDb(TestCaseRunner runner, String id, String color, String height, String material, String sound, String wingsState) {
        runner.$(query(testDb).statement("SELECT * FROM DUCK WHERE ID=" + id)
                .validate("COLOR",color)
                .validate("HEIGHT",height)
                .validate("MATERIAL",material)
                .validate("SOUND",sound)
                .validate("WINGS_STATE",wingsState));
    }

    @Step("Insert new duck DB")
    public void insertDuckDb(TestCaseRunner runner, String color, String height, String material, String sound, String wingsState) {
        String query = "insert into DUCK (id, color, height, material, sound, wings_state) " +
                "values (${duckId}, '" + color + "', " + height + ", '" + material + "', '" + sound + "', '" + wingsState + "');";
        databaseUpdate(runner, query);
    }

    //CRUD

    @Step("Create duck endpoint")
    public void createDuck(TestCaseRunner runner, Object body){
        sendPostRequest(runner, duckService, "/api/duck/create", body);
    }

    @Step("Update duck endpoint")
    public void updateDuck(TestCaseRunner runner, String color, double height, String material, String sound, String wingsState) {
        sendPutRequest(runner, duckService, "/api/duck/update?color=" + color +
                "&height=" + height + "&material=" + material + "&sound="
                + sound + "&wingsState=" + wingsState + "&id=${duckId}");
    }

    @Step("Delete duck endpoint")
    public void deleteDuck(TestCaseRunner runner) {
        sendDeleteRequest(runner,duckService,"/api/duck/delete?id=${duckId}");
    }

    //Actions
    @Step("Duck swim endpoint")
    public void duckSwim(TestCaseRunner runner){
        sendGetRequest(runner, duckService, "/api/duck/action/swim?id=${duckId}");
    }

    @Step("Duck sound endpoint")
    public void duckQuack(TestCaseRunner runner, int repetitionCount, int soundCount){
        sendGetRequest(runner, duckService, "/api/duck/action/quack?id=${duckId}" + "&repetitionCount=" + repetitionCount
                + "&soundCount=" + soundCount);
    }

    @Step("Duck get properties endpoint")
    public void getDuckProperties(TestCaseRunner runner) {
        sendGetRequest(runner, duckService, "/api/duck/action/properties?id=${duckId}");
    }

    @Step("Duck fly endpoint")
    public void duckFly(TestCaseRunner runner){
        sendGetRequest(runner, duckService, "/api/duck/action/fly?id=${duckId}");
    }

    //Validation

    //Payloads folder
    @Step("Validate duck response")
    public void validateResponse(TestCaseRunner runner, Object body){
        validateDuckResponseBase(runner, body, duckService);
    }

    //Resources folder
    @Step("Validate duck response")
    public void validateResponseWithResource(TestCaseRunner runner, String expectedPayload){
        validateDuckResponseBase(runner, expectedPayload, duckService);
    }

    public void validateDuckWithGetId(TestCaseRunner runner, String expectedPayload){
        validateDuckWithGetIdBase(runner, expectedPayload, duckService);
    }

    @Step("Validate bad response")
    public void validateBadResponse(TestCaseRunner runner){
        runner.$(
                http().client(duckService).receive().response(HttpStatus.NOT_FOUND).
                        message().contentType(MediaType.APPLICATION_JSON_VALUE)
        );
    }

    //Additional methods
    @Step("Save duck ID")
    public void saveDuckId(TestCaseRunner runner) {
        runner.$(
                http()
                        .client(duckService)
                        .receive()
                        .response(HttpStatus.OK)
                        .message()
                        .extract(fromBody().expression("$.id", "duckId"))
        );
    }

    @Step("Check duck deleted")
    public void checkDuckDeleted(TestCaseRunner runner) {
        runner.$(
                http().client("http://localhost:2222").send().get("/api/duck/action/properties").queryParam("id", "${duckId}")
        );

        runner.$(
                http().client("http://localhost:2222").receive().response(HttpStatus.INTERNAL_SERVER_ERROR)
        );
    }

}
