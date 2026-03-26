package com.example.travel_companion;

import android.os.Bundle;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// the above libraries were present by default
// importing required libraries
import android.widget.ArrayAdapter; // imported to connect the arrays created in string.xml to Spinner display UI
import android.widget.Button; // imported button properties
import android.widget.EditText; // to import editText(user input) properties
import android.widget.Spinner; // to import dropdown properties
import android.widget.TextView; // to import display text properties
import android.widget.AdapterView; // imported to handle spinner options selection


public class MainActivity extends AppCompatActivity {
    // Declaring all UI component IDs as I will be using these IDs to capture the values
    Spinner categoryOption, sourceOption, destOption;
    EditText enterValue;
    Button convertButton;
    TextView resultDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // assigning the variables with UI elements using their IDs to enable their actions so that we can capture them
        categoryOption = findViewById(R.id.categoryOption);
        sourceOption = findViewById(R.id.sourceOption);
        destOption = findViewById(R.id.destOption);
        enterValue = findViewById(R.id.enterValue);
        convertButton = findViewById(R.id.convertButton);
        resultDisplay = findViewById(R.id.resultDisplay);

        // now starting from 1st step, we need to change the from and to units when we select specific categories.
        // setting listener to detect when the categories are selected
        categoryOption.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            // this method runs when user selects a category
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {

                // get selected category as string
                String selectedCategory = categoryOption.getSelectedItem().toString();

                // update from and to spinner options based on passed category
                updateUnitSpinners(selectedCategory); // custom function call to change the spinner options
            }

            // required method for onItemSelected. Currently we don't have any actions for the below so it is empty.
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });
        // setting click listener for convert button
        convertButton.setOnClickListener(v -> {

            // get selected category
            String category = categoryOption.getSelectedItem().toString();

            // get selected source unit
            String sourceUnit = sourceOption.getSelectedItem().toString();

            // get selected destination unit
            String destUnit = destOption.getSelectedItem().toString();

            // get input value as string
            String inputText = enterValue.getText().toString();

            // convert string input to double
            double inputValue = Double.parseDouble(inputText);

            // call conversion function
            double result = convertValue(category, sourceUnit, destUnit, inputValue);

            // display result (formatted to 2 decimal places)
            resultDisplay.setText(String.format("%.2f", result));
        });
    }
    // function definition to update unit dropdowns based on selected category
    public void updateUnitSpinners(String category) {

        // variable to store array ID
        int arrayId;

        // using switch case to select correct array based on category
        switch (category) {
            case "Currency":
                arrayId = R.array.currency_units;
                break;
            case "Fuel Efficiency":
                arrayId = R.array.fuel_units;
                break;
            case "Temperature":
                arrayId = R.array.temperature_units;
                break;
            case "Distance":
                arrayId = R.array.distance_units;
                break;
            // put a default case for when the app starts
            default:
                arrayId = R.array.currency_units;
                break;
        }
        // create adapter using selected arrayID
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                arrayId,
                android.R.layout.simple_spinner_item
        );

        // setting dropdown layout style used in xml file
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // applying adapter to both spinners
        sourceOption.setAdapter(adapter);
        destOption.setAdapter(adapter);
    }
    // main function to handle conversion logic
    public double convertValue(String category, String sourceUnit, String destUnit, double value) {

        // check which category is selected
        switch (category) {

            // currency conversion
            case "Currency":
                return convertCurrency(sourceUnit, destUnit, value);

            // fuel conversion
            case "Fuel Efficiency":
                return convertFuel(sourceUnit, destUnit, value);

            // temperature conversion
            case "Temperature":
                return convertTemperature(sourceUnit, destUnit, value);

            // distance conversion
            case "Distance":
                return convertDistance(sourceUnit, destUnit, value);

            // default case (should not happen)
            default:
                return value;
        }
    }

    // currency conversion using USD as base
    public double convertCurrency(String sourceUnit, String destUnit, double value) {

        // convert source value to USD first
        double valueInUSD;

        switch (sourceUnit) {
            case "USD":
                valueInUSD = value;
                break;
            case "AUD":
                valueInUSD = value / 1.55;
                break;
            case "EUR":
                valueInUSD = value / 0.92;
                break;
            case "JPY":
                valueInUSD = value / 148.50;
                break;
            case "GBP":
                valueInUSD = value / 0.78;
                break;
            default:
                valueInUSD = value;
                break;
        }

        // convert USD to destination currency
        switch (destUnit) {
            case "USD":
                return valueInUSD;
            case "AUD":
                return valueInUSD * 1.55;
            case "EUR":
                return valueInUSD * 0.92;
            case "JPY":
                return valueInUSD * 148.50;
            case "GBP":
                return valueInUSD * 0.78;
            default:
                return valueInUSD;
        }
    }

    // function for Fuel efficiency conversion
    public double convertFuel(String sourceUnit, String destUnit, double value) {

        if (sourceUnit.equals("MPG (US)") && destUnit.equals("km/L")) {
            return value * 0.425;
        } else {
            return value / 0.425;
        }
    }

    // function for temperature conversion
    public double convertTemperature(String sourceUnit, String destUnit, double value) {

        if (sourceUnit.equals("Celsius") && destUnit.equals("Fahrenheit")) {
            return (value * 1.8) + 32;
        } else if (sourceUnit.equals("Fahrenheit") && destUnit.equals("Celsius")) {
            return (value - 32) / 1.8;
        } else if (sourceUnit.equals("Celsius") && destUnit.equals("Kelvin")) {
            return value + 273.15;
        } else if (sourceUnit.equals("Kelvin") && destUnit.equals("Celsius")) {
            return value - 273.15;
        } else if (sourceUnit.equals("Fahrenheit") && destUnit.equals("Kelvin")) {
            return ((value - 32) / 1.8) + 273.15;
        } else {
            return ((value - 273.15) * 1.8) + 32;
        }
    }

    // function for distance conversion
    public double convertDistance(String sourceUnit, String destUnit, double value) {

        if (sourceUnit.equals("Nautical Miles") && destUnit.equals("Kilometers")) {
            return value * 1.852;
        } else {
            return value / 1.852;
        }
    }

}