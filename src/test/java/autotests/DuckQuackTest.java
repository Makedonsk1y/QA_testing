package autotests;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.testng.annotations.Test;

public class DuckQuackTest extends TestNGCitrusSpringSupport {
    @Test (description = "Проверка того, что утка издает звук")
    @CitrusTest
    public void successfulQuack(){

    }
}
