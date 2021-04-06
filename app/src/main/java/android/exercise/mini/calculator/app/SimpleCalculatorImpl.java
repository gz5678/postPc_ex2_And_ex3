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
        int parsedNumberString = Integer.parseInt(currentNumber.toString());
        // First, we do the last operation we found since now we know the 2 numbers
        switch(operator) {
          case "-":
            calc -= parsedNumberString;
            break;
          case "+":
            calc += parsedNumberString;
            break;
        }
        // We save the operator
        operator = s;
      }
    }
    // Clear history and insert the calculated expression
    history.clear();
    history.add(String.valueOf(calc));
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
    // todo: insert all data to the state, so in the future we can load from this state
    return state;
  }

  @Override
  public void loadState(Serializable prevState) {
    if (!(prevState instanceof CalculatorState)) {
      return; // ignore
    }
    CalculatorState casted = (CalculatorState) prevState;
    // todo: use the CalculatorState to load
  }

  private static class CalculatorState implements Serializable {
    /*
    TODO: add fields to this class that will store the calculator state
    all fields must only be from the types:
    - primitives (e.g. int, boolean, etc)
    - String
    - ArrayList<> where the type is a primitive or a String
    - HashMap<> where the types are primitives or a String
     */
  }
}
