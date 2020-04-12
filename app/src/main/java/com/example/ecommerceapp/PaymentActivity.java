package com.example.ecommerceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class PaymentActivity extends AppCompatActivity implements PaymentResultListener {
    private static final String TAG = "mytag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Checkout.preload(getApplicationContext());
        startPayment();
    }

    public void startPayment() {
        Checkout checkout = new Checkout();
        checkout.setKeyID("<YOUR_KEY_ID>");

        checkout.setImage(R.drawable.arrow_one);

        final Activity activity = this;


        try {
            JSONObject options = new JSONObject();


            options.put("name", "Merchant Name");


            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("order_id", "order_9A33XWu170gUtm");
            options.put("currency", "INR");


            options.put("amount", "500");

            checkout.open(activity, options);
        } catch (Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        Log.d("mytag", razorpayPaymentID);
    }

    @Override
    public void onPaymentError(int code, String response) {
        Log.d("mytag", response);
    }
}
