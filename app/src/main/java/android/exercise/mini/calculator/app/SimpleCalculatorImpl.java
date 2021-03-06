package android.exercise.mini.calculator.app;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SimpleCalculatorImpl implements SimpleCalculator {

  private ArrayList<String> history = new ArrayList<>(Collections.singletonList("0"));
  private final List<String> ORDERS = Arrays.asList("+", "-");

  @Override
  public String output() {
    StringBuilder sb = new StringBuilder();
    for(String s: history) {
      sb.append(s);
    }
    return sb.toString();
  }

  @Override
  public void insertDigit(int digit) {
    if(digit < 0 || digit > 9) {
      throw new IllegalArgumentException("Digit needs to be from 0-9");
    }
    else if(history.size() == 1 && history.get(0).equals("0")) {
      history.remove(0);
    }
    history.add(String.valueOf(digit));
  }

  @Override
  public void insertPlus() {
    if(!history.isEmpty() && !ORDERS.contains(history.get(history.size() - 1))) {
      history.add("+");
    }
  }

  @Override
  public void insertMinus() {
    if(!history.isEmpty() && !ORDERS.contains(history.get(history.size() - 1))) {
      history.add("-");
    }
  }

  @Override
  public void insertEquals() {
    StringBuilder currentNumber = new StringBuilder();
    int calc = 0;
    String operator = "+";
    for(String s: history) {
      // Build the number until an order is found
      if(!ORDERS.contains(s)) {
        currentNumber.append(s);
      }
      else {
        calc = this.newCalc(operator, calc, currentNumber);
        // We save the operator
        operator = s;
        currentNumber.setLength(0); // Clears the string builder
      }
    }
    // There might still be a number in currentNumber we haven't taken care of since the last
    // iteration won't go into the else clause.
    if(currentNumber.length() != 0) {
      calc = this.newCalc(operator, calc, currentNumber);
    }
    // Clear history and insert the calculated expression
    history.clear();
    history.add(String.valueOf(calc));
  }

  private int newCalc(String operator, int oldCalc, StringBuilder numberString) {
    int parsedNumberString = Integer.parseInt(numberString.toString());
    // We do the last operation we found since now we know the 2 numbers
    int calc = 0;
    switch(operator) {
      case "-":
        calc = oldCalc - parsedNumberString;
        break;
      case "+":
        calc = oldCalc + parsedNumberString;
        break;
    }
    return calc;
  }

  @Override
  public void deleteLast() {
    if(!history.isEmpty()) {
      history.remove(history.size() - 1);
      if (history.isEmpty()) {
        history.add("0");
      }
    }
  }

  @Override
  public void clear() {
    history.clear();
    history.add("0");
  }

  @Override
  public Serializable saveState() {
    CalculatorState state = new CalculatorState();
    state.setHistory(this.history);
    return state;
  }

  @Override
  public void loadState(Serializable prevState) {
    if (!(prevState instanceof CalculatorState)) {
      return; // ignore
    }
    CalculatorState casted = (CalculatorState) prevState;
    this.history = casted.getHistory();
  }

  private static class CalculatorState implements Serializable {

    private ArrayList<String> history;

    public void setHistory(ArrayList<String> history) {
      this.history = new ArrayList<>(history);
    }

    public ArrayList<String> getHistory() {
      return this.history;
    }
  }
}
