package br.com.joshua.baseprojectexpense;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
            ExpenseControlTests.class,
            ExpenseControllerTest.class
})
public class RunAllTests {

}
