package autotests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.context.ContextConfiguration;

import static com.consol.citrus.actions.ExecuteSQLAction.Builder.sql;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;
import static com.consol.citrus.validation.DelegatingPayloadVariableExtractor.Builder.fromBody;
import static com.consol.citrus.validation.json.JsonMessageValidationContext.Builder.json;


@ContextConfiguration(classes = {EndpointConfig.class})
public class BaseTest extends TestNGCitrusSpringSupport {

    @Autowired
    protected HttpClient duckService;

    @Autowired
    protected SingleConnectionDataSource testDb;

    protected void databaseUpdate(TestCaseRunner runner, String sql){
        runner.$(
                sql(testDb).statement(sql)
        );
    }

    protected void sendGetRequest(TestCaseRunner runner, HttpClient url, String path){
        runner.$(
                http().client(url).send().get(path)
        );
    }

    protected void sendPostRequest(TestCaseRunner runner, HttpClient url, String path, Object body){
        runner.$(
                http().client(url).send().post(path).message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ObjectMappingPayloadBuilder(body, new ObjectMapper()))
        );
    }

    protected void sendPostRequest(TestCaseRunner runner, HttpClient url, String path, String expectedPayload){
        runner.$(
                http().client(url).send().post(path).message()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(new ClassPathResource(expectedPayload))
        );
    }

    protected void sendPutRequest(TestCaseRunner runner, HttpClient url, String path){
        runner.$(
                http().client(url).send().put(path)
        );
    }

    protected void sendDeleteRequest(TestCaseRunner runner, HttpClient url, String path){
        runner.$(
                http().client(url).send().delete(path)
        );
    }

    protected void validateDuckResponseBase(TestCaseRunner runner, Object body, HttpClient url){
        runner.$(
                http()
                        .client(url)
                        .receive()
                        .response(HttpStatus.OK)
                        .message()
                        .type(MessageType.JSON)
                        //.validate(json().ignore("$.id"))
                        .body(new ObjectMappingPayloadBuilder(body, new ObjectMapper()))
        );
    }

    protected void validateDuckResponseBase(TestCaseRunner runner, String expectedPayload, HttpClient url){
        runner.$(
                http()
                        .client(url)
                        .receive()
                        .response(HttpStatus.OK)
                        .message()
                        .type(MessageType.JSON)
                        .body(new ClassPathResource(expectedPayload))
        );
    }

    protected void validateDuckWithGetIdBase(TestCaseRunner runner, String expectedPayload, HttpClient url){
        runner.$(
                http()
                        .client(url)
                        .receive()
                        .response(HttpStatus.OK)
                        .message()
                        .type(MessageType.JSON)
                        .validate(json().ignore("$.id"))
                        .body(new ClassPathResource(expectedPayload))
                        .extract(fromBody().expression("$.id", "duckId"))
        );
    }
}
