package test.android.exercise.mini.calculator.app;

import android.exercise.mini.calculator.app.SimpleCalculatorImpl;

import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;

import static org.junit.Assert.*;

public class SimpleCalculatorImplTest {

  @Test
  public void when_noInputGiven_then_outputShouldBe0(){
    SimpleCalculatorImpl calculatorUnderTest = new SimpleCalculatorImpl();
    assertEquals("0", calculatorUnderTest.output());
  }

  @Test
  public void when_inputIsPlus_then_outputShouldBe0Plus(){
    SimpleCalculatorImpl calculatorUnderTest = new SimpleCalculatorImpl();
    calculatorUnderTest.insertPlus();
    assertEquals("0+", calculatorUnderTest.output());
  }


  @Test
  public void when_inputIsMinus_then_outputShouldBeCorrect(){
    SimpleCalculatorImpl calculatorUnderTest = new SimpleCalculatorImpl();
    calculatorUnderTest.insertMinus();
    String expected = "0-";
    assertEquals(expected, calculatorUnderTest.output());
  }

  @Test
  public void when_callingInsertDigitWithIllegalNumber_then_exceptionShouldBeThrown(){
    SimpleCalculatorImpl calculatorUnderTest = new SimpleCalculatorImpl();
    try {
      calculatorUnderTest.insertDigit(357);
      fail("should throw an exception and not reach this line");
    } catch (RuntimeException e) {
      // good :)
    }
  }


  @Test
  public void when_callingDeleteLast_then_lastOutputShouldBeDeleted(){
    SimpleCalculatorImpl calculatorUnderTest = new SimpleCalculatorImpl();
    calculatorUnderTest.insertDigit(3);
    calculatorUnderTest.insertMinus();
    calculatorUnderTest.deleteLast();
    String expected = "3";
    assertEquals(expected, calculatorUnderTest.output());
  }

  @Test
  public void when_callingClear_then_outputShouldBeCleared(){
    SimpleCalculatorImpl calculatorUnderTest = new SimpleCalculatorImpl();
    calculatorUnderTest.insertDigit(3);
    calculatorUnderTest.insertMinus();
    calculatorUnderTest.clear();
    String expected = "0";
    assertEquals(expected, calculatorUnderTest.output());
  }

  @Test
  public void when_savingState_should_loadThatStateCorrectly(){
    SimpleCalculatorImpl calculatorUnderTest = new SimpleCalculatorImpl();
    // give some input
    calculatorUnderTest.insertDigit(5);
    calculatorUnderTest.insertPlus();
    calculatorUnderTest.insertDigit(7);

    // save current state
    Serializable savedState = calculatorUnderTest.saveState();
    assertNotNull(savedState);

    // call `clear` and make sure calculator cleared
    calculatorUnderTest.clear();
    assertEquals("0", calculatorUnderTest.output());

    // load the saved state and make sure state was loaded correctly
    calculatorUnderTest.loadState(savedState);
    assertEquals("5+7", calculatorUnderTest.output());
  }

  @Test
  public void when_savingStateFromFirstCalculator_should_loadStateCorrectlyFromSecondCalculator(){
    SimpleCalculatorImpl firstCalculator = new SimpleCalculatorImpl();
    SimpleCalculatorImpl secondCalculator = new SimpleCalculatorImpl();

    // give some input
    firstCalculator.insertDigit(5);
    firstCalculator.insertPlus();
    firstCalculator.insertDigit(7);

    // save current state
    Serializable savedState = firstCalculator.saveState();
    assertNotNull(savedState);

    // call `clear` and make sure calculator cleared
    firstCalculator.clear();
    assertEquals("0", firstCalculator.output());

    // load the saved state in the second calculator and make sure state was loaded correctly
    secondCalculator.loadState(savedState);
    assertEquals("5+7", secondCalculator.output());
  }

  // TODO:
  //  the existing tests are not enough since they only test simple use-cases with small inputs.
  //  write at least 10 methods to test correct behavior with complicated inputs or use-cases.
  //  examples:
  //  - with 2 calculators, give them different inputs, then save state on first calculator and load the state into second calculator, make sure state loaded well
  @Test
  public void when_insertingTwoPluses_then_outputShouldOnlyHaveOne(){
    SimpleCalculatorImpl calculatorUnderTest = new SimpleCalculatorImpl();
    calculatorUnderTest.insertDigit(5);
    calculatorUnderTest.insertPlus();
    calculatorUnderTest.insertPlus();
    String expected = "5+";
    assertEquals(expected, calculatorUnderTest.output());
  }

  @Test
  public void when_insertingTwoMinuses_then_outputShouldOnlyHaveOne(){
    SimpleCalculatorImpl calculatorUnderTest = new SimpleCalculatorImpl();
    calculatorUnderTest.insertDigit(5);
    calculatorUnderTest.insertMinus();
    calculatorUnderTest.insertMinus();
    String expected = "5-";
    assertEquals(expected, calculatorUnderTest.output());
  }

  @Test
  public void when_clearingSeveralTime_then_outputOnlyLastCalculation(){
    SimpleCalculatorImpl calculatorUnderTest = new SimpleCalculatorImpl();
    calculatorUnderTest.insertDigit(9);
    calculatorUnderTest.clear();
    calculatorUnderTest.insertDigit(1);
    calculatorUnderTest.insertDigit(2);
    calculatorUnderTest.clear();
    calculatorUnderTest.insertDigit(8);
    calculatorUnderTest.insertMinus();
    calculatorUnderTest.insertDigit(7);
    calculatorUnderTest.insertEquals();
    String expected = "1";
    assertEquals(expected, calculatorUnderTest.output());
  }

  @Test
  public void when_deleteLast_then_onlyOneDigitShouldBeRemoved(){
    SimpleCalculatorImpl calculatorUnderTest = new SimpleCalculatorImpl();
    calculatorUnderTest.insertDigit(5);
    calculatorUnderTest.insertPlus();
    calculatorUnderTest.insertDigit(7);
    calculatorUnderTest.insertMinus();
    calculatorUnderTest.insertDigit(1);
    calculatorUnderTest.insertDigit(3);
    calculatorUnderTest.deleteLast();
    calculatorUnderTest.insertDigit(2);
    calculatorUnderTest.insertDigit(5);
    String expected = "5+7-125";
    assertEquals(expected, calculatorUnderTest.output());
  }

  @Test
  public void when_multipleCalculations_then_resultShouldCarryOn(){
    SimpleCalculatorImpl calculatorUnderTest = new SimpleCalculatorImpl();
    calculatorUnderTest.insertDigit(8);
    calculatorUnderTest.insertMinus();
    calculatorUnderTest.insertDigit(7);
    calculatorUnderTest.insertEquals();
    calculatorUnderTest.insertPlus();
    calculatorUnderTest.insertDigit(4);
    calculatorUnderTest.insertEquals();
    calculatorUnderTest.insertMinus();
    calculatorUnderTest.insertDigit(1);
    calculatorUnderTest.insertEquals();
    String expected = "4";
    assertEquals(expected, calculatorUnderTest.output());
  }

  @Test
  public void when_bigNumbers_then_shouldWork(){
    SimpleCalculatorImpl calculatorUnderTest = new SimpleCalculatorImpl();
    calculatorUnderTest.insertDigit(9);
    calculatorUnderTest.insertDigit(9);
    calculatorUnderTest.insertDigit(9);
    calculatorUnderTest.insertMinus();
    calculatorUnderTest.insertDigit(8);
    calculatorUnderTest.insertDigit(8);
    calculatorUnderTest.insertDigit(8);
    calculatorUnderTest.insertMinus();
    calculatorUnderTest.insertDigit(2);
    calculatorUnderTest.insertDigit(2);
    calculatorUnderTest.insertDigit(2);
    calculatorUnderTest.insertEquals();
    calculatorUnderTest.insertMinus();
    calculatorUnderTest.insertDigit(3);
    calculatorUnderTest.insertDigit(3);
    calculatorUnderTest.insertDigit(3);
    String expected = "-111-333";
    assertEquals(expected, calculatorUnderTest.output());
  }

  @Test
  public void when_calculationIsNegative_then_calcShouldWork(){
    SimpleCalculatorImpl calculatorUnderTest = new SimpleCalculatorImpl();
    calculatorUnderTest.insertDigit(3);
    calculatorUnderTest.insertMinus();
    calculatorUnderTest.insertDigit(5);
    calculatorUnderTest.insertEquals();
    String expected = "-2";
    assertEquals(expected, calculatorUnderTest.output());
  }

  @Test
  public void when_multipleChangesOfSign_then_calcShouldWork(){
    SimpleCalculatorImpl calculatorUnderTest = new SimpleCalculatorImpl();
    calculatorUnderTest.insertDigit(3);
    calculatorUnderTest.insertMinus();
    calculatorUnderTest.insertDigit(5);
    calculatorUnderTest.insertEquals();
    calculatorUnderTest.insertPlus();
    calculatorUnderTest.insertDigit(3);
    calculatorUnderTest.insertEquals();
    calculatorUnderTest.insertMinus();
    calculatorUnderTest.insertDigit(5);
    calculatorUnderTest.insertEquals();
    String expected = "-4";
    assertEquals(expected, calculatorUnderTest.output());
  }

  @Test
  public void when_startingWithMinus_then_calcShouldWork(){
    SimpleCalculatorImpl calculatorUnderTest = new SimpleCalculatorImpl();
    calculatorUnderTest.insertMinus();
    calculatorUnderTest.insertDigit(5);
    calculatorUnderTest.insertEquals();
    calculatorUnderTest.insertPlus();
    calculatorUnderTest.insertDigit(3);
    calculatorUnderTest.insertEquals();
    String expected = "-2";
    assertEquals(expected, calculatorUnderTest.output());
  }

  @Test
  public void when_startingWithPlus_then_calcShouldWork(){
    SimpleCalculatorImpl calculatorUnderTest = new SimpleCalculatorImpl();
    calculatorUnderTest.insertPlus();
    calculatorUnderTest.insertDigit(5);
    calculatorUnderTest.insertEquals();
    calculatorUnderTest.insertPlus();
    calculatorUnderTest.insertDigit(3);
    calculatorUnderTest.insertEquals();
    String expected = "8";
    assertEquals(expected, calculatorUnderTest.output());
  }

  @Test
  public void when_savingStateFromFirstCalculator_should_loadStateCorrectlyFromSecondCalculatorWithData(){
    SimpleCalculatorImpl firstCalculator = new SimpleCalculatorImpl();
    SimpleCalculatorImpl secondCalculator = new SimpleCalculatorImpl();

    // give some input to first calculator
    firstCalculator.insertDigit(5);
    firstCalculator.insertPlus();
    firstCalculator.insertDigit(7);

    // give some input to second calculator
    secondCalculator.insertDigit(1);
    secondCalculator.insertPlus();
    secondCalculator.insertDigit(3);

    // save current state
    Serializable savedState = firstCalculator.saveState();
    assertNotNull(savedState);

    // call `clear` and make sure calculator cleared
    firstCalculator.clear();
    assertEquals("0", firstCalculator.output());

    // load the saved state in the second calculator and make sure state was loaded correctly
    secondCalculator.loadState(savedState);
    assertEquals("5+7", secondCalculator.output());
  }
}