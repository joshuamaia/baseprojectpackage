package br.com.joshua.baseproject;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
            PersonTests.class,
            ExpenseControlTests.class,
            PersonControllerTest.class,
            ExpenseControllerTest.class
})
public class RunAllTests {

}
