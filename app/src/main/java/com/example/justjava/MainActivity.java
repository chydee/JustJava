package com.example.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This App display form for user to order coffee
 * */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Globals
    int quantity = 1;

    //This method is called when the Order button is clicked

    public void submitOrder(View view) {
        //figures out if the user wants Whipped Cream Topping
        CheckBox addWhippedCream =  findViewById(R.id.addWhippedCream);
        boolean hasWhippedCream = addWhippedCream.isChecked();
        //figures out if the user wants Chocolate Topping
        CheckBox addChocolate = findViewById(R.id.addChocolate);
        boolean hasChocolate = addChocolate.isChecked();
        //Gets the name of the user
        EditText nameTextInput = findViewById(R.id.name_text_input);
        String name = nameTextInput.getText().toString();
        String priceMessage = createOrderSummary(hasWhippedCream, hasChocolate, name);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, "udacityandroiddemo@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava order for "+ name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void increment(View view) {
        if (quantity == 100){
            //Shows an error as a toast
            Toast toastMessage = Toast.makeText(this,"You Cannot have more than 100 cups coffees", Toast.LENGTH_SHORT);
            toastMessage.show();
            //exits the method when theres nothing left to do
            return;
        }
         quantity = quantity + 1;
         displayQuantity(quantity);
    }

    public void decrement(View view) {
        if (quantity == 1){
            //Shows an error as a toast
            Toast toastMessage = Toast.makeText(this,"You cannot have less than 1 cup of coffee", Toast.LENGTH_SHORT);
            toastMessage.show();
            //exits the method when there's npthing left to do
            return;
        }
        quantity--;
        displayQuantity(quantity);
    }

    //This method displays the value of quantity on the screen
    public void displayQuantity(int number){
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }



    /**
     * Calculates the price of the order based on the current quantity.
     *
     * @return the price
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        //The per cup of coffee
        int basePrice = 5;
        // whippedCreamTopping Price
        int whippedCreamTopping = 1;
        // chocolateTopping Price
        int chocolateTopping = 2;
        // Adds the price for whippedCream if the user wants whipped cream
        if(hasWhippedCream){
            basePrice += whippedCreamTopping;
        }
        // adds the price for Chocolate topping if the user wants chocolate topping
        if (hasChocolate){
            basePrice += chocolateTopping;
        }
            return basePrice * quantity;
    }
/**
 * Create Summary Order
 *
 * @param hasWhippedCream
 * @param hasChocolate
 * @param name
 * @return text_summary
 */
    private String createOrderSummary(boolean hasWhippedCream, boolean hasChocolate, String name){
        String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage += "\n" + getString(R.string.order_summary_whipped_cream, hasWhippedCream);
        priceMessage += "\n" + getString(R.string.order_summary_chocolate, hasChocolate);
        priceMessage = priceMessage + "\n" + getString(R.string.order_summary_quantity, quantity);
        priceMessage = priceMessage + "\nTotal: $" + calculatePrice(hasWhippedCream, hasChocolate);
        priceMessage = priceMessage + getString(R.string.thank_you);
        return priceMessage;
    }

}
