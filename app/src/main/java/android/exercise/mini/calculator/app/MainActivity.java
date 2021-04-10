package android.exercise.mini.calculator.app;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

  @VisibleForTesting
  public SimpleCalculator calculator;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    if (calculator == null) {
      calculator = new SimpleCalculatorImpl();
    }


    // Find all views
    TextView button0 = findViewById(R.id.button0);
    TextView button1 = findViewById(R.id.button1);
    TextView button2 = findViewById(R.id.button2);
    TextView button3 = findViewById(R.id.button3);
    TextView button4 = findViewById(R.id.button4);
    TextView button5 = findViewById(R.id.button5);
    TextView button6 = findViewById(R.id.button6);
    TextView button7 = findViewById(R.id.button7);
    TextView button8 = findViewById(R.id.button8);
    TextView button9 = findViewById(R.id.button9);
    TextView calcOutput = findViewById(R.id.textViewCalculatorOutput);
    View backspace = findViewById(R.id.buttonBackSpace);
    TextView plusButton = findViewById(R.id.buttonPlus);
    TextView minusButton = findViewById(R.id.buttonMinus);
    TextView equalButton = findViewById(R.id.buttonEquals);
    TextView clearButton = findViewById(R.id.buttonClear);

    // Initial update to calculators output
    calcOutput.setText(calculator.output());

    // Set onclick listeners to number buttons
    TextView[] buttonsArray = new TextView[]{button0, button1, button2, button3, button4, button5,
            button6, button7, button8, button9};
    int index = 0;
    for(TextView button: buttonsArray) {
      final int finalIndex = index;
      button.setOnClickListener(v -> {
        calculator.insertDigit(finalIndex);
        calcOutput.setText(calculator.output());
      });
      index++;
    }

    // Set onclick listener for order buttons
    plusButton.setOnClickListener( v -> {
      calculator.insertPlus();
      calcOutput.setText(calculator.output());
    });

    minusButton.setOnClickListener( v -> {
      calculator.insertMinus();
      calcOutput.setText(calculator.output());
    });

    equalButton.setOnClickListener( v -> {
      calculator.insertEquals();
      calcOutput.setText(calculator.output());
    });

    clearButton.setOnClickListener( v -> {
      calculator.clear();
      calcOutput.setText(calculator.output());
    });

    backspace.setOnClickListener( v -> {
      calculator.deleteLast();
      calcOutput.setText(calculator.output());
    });
  }

  @Override
  protected void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putSerializable("key_saved_calc_state", calculator.saveState());
  }

  @Override
  protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    Serializable state = savedInstanceState.getSerializable("key_saved_calc_state");
    calculator.loadState(state);
    TextView calcOutput = findViewById(R.id.textViewCalculatorOutput);
    calcOutput.setText(calculator.output());
  }
}