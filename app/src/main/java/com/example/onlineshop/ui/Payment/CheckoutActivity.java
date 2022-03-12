package com.example.onlineshop.ui.Payment;

<<<<<<< Updated upstream
public class CheckoutActivity {
=======
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.onlineshop.MainActivity.MainActivity;
import com.example.onlineshop.R;
import com.example.onlineshop.ViewModelFactory;
import com.example.onlineshop.data.entity.orderGet.GetOrders;
import com.example.onlineshop.data.remoteDataSource.RemoteDataSourceImpl;
import com.example.onlineshop.data.roomData.RoomDataSourceImpl;
import com.example.onlineshop.data.roomData.RoomService;
import com.example.onlineshop.repository.RepositoryImpl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentIntentResult;
import com.stripe.android.Stripe;
import com.stripe.android.model.ConfirmPaymentIntentParams;
import com.stripe.android.model.PaymentIntent;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.view.CardInputWidget;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CheckoutActivity extends AppCompatActivity {

    private static final String BACKEND_URL = "https://strip-payment-backend.herokuapp.com/";
    // private static final String BACKEND_URL = "https://192.168.1.6:4242/";
    String amount;
    private static Context mContext;
    public static GetOrders.Order order;
    public static RepositoryImpl repositoryImpl;
    private OkHttpClient httpClient = new OkHttpClient();
    private String paymentIntentClientSecret;
    private Stripe stripe;
    public static PaymentViewModel paymentViewModel;
    ImageButton img1;
    ImageButton img2;
    CircularProgressButton payButton;
    CardInputWidget cardInputWidget;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        img1 = findViewById(R.id.btn);
        mContext = this;
        img2 = findViewById(R.id.btn_hide);
        payButton = findViewById(R.id.payButton);
        cardInputWidget = findViewById(R.id.cardInputWidget);

        img1.setOnClickListener(v -> {
            img1.setVisibility(View.GONE);
            img2.setVisibility(View.VISIBLE);
            payButton.setVisibility(View.VISIBLE);
            cardInputWidget.setVisibility(View.VISIBLE);

        });

        img2.setOnClickListener(v -> {
            img1.setVisibility(View.VISIBLE);
            img2.setVisibility(View.GONE);
            payButton.setVisibility(View.GONE);
            cardInputWidget.setVisibility(View.GONE);
        });

        repositoryImpl = new RepositoryImpl(new RemoteDataSourceImpl(), new RoomDataSourceImpl(RoomService.Companion.getInstance(getApplication())));

        ViewModelFactory viewModelFactory = new ViewModelFactory(repositoryImpl, getApplication());
        paymentViewModel = new ViewModelProvider(this, viewModelFactory).get(PaymentViewModel.class);
        amount = getIntent().getStringExtra("amount");
        order = (GetOrders.Order) getIntent().getSerializableExtra("order");
        stripe = new Stripe(
                getApplicationContext(),
                "pk_test_51KbNTKFT6LXwIKdy2Iun127gSgCpEHFRo4xGM9epnsCrLtdaxctABTmybFjN4mJIhSsVdLzxayi65SA758MT7I3300s7EaXTO0",
                "acct_1KbNTKFT6LXwIKdy"
        );

        payButton.setOnClickListener(v -> {
            payButton.startAnimation();
            startCheckout();
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    private void startCheckout() {
        {
            MediaType mediaType = MediaType.get("application/json; charset=utf-8");
            double amountt = Double.parseDouble(amount) * 100;
            Map<String, Object> payMap = new HashMap<>();
            Map<String, Object> itemMap = new HashMap<>();
            List<Map<String, Object>> itemList = new ArrayList<>();
            payMap.put("currency", "INR");
            itemMap.put("id", "photo_subscription");
            itemMap.put("amount", amountt);
            itemList.add(itemMap);
            payMap.put("items", itemList);
            String json = new Gson().toJson(payMap);
            RequestBody body = RequestBody.create(json, mediaType);
            Request request = new Request.Builder()
                    .url(BACKEND_URL + "create-payment-intent")
                    .post(body)
                    .build();
            httpClient.newCall(request)
                    .enqueue(new PayCallback(this));

        }
    }

    private void displayAlert(@NonNull String title,
                              @Nullable String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message);

        builder.setPositiveButton("Ok", null);
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Handle the result of stripe.confirmPayment
        stripe.onPaymentResult(requestCode, data, new PaymentResultCallback(this));
    }

    private void onPaymentSuccess(@NonNull final Response response) throws IOException {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>() {
        }.getType();
        Map<String, String> responseMap = gson.fromJson(
                Objects.requireNonNull(response.body()).string(),
                type
        );

        paymentIntentClientSecret = responseMap.get("clientSecret");

        assert paymentIntentClientSecret != null;
        paymentIntentClientSecret = paymentIntentClientSecret.replaceAll("^\"|\"$", "");

        //once you get the payment client secret start transaction
        //get card detail
        PaymentMethodCreateParams params = cardInputWidget.getPaymentMethodCreateParams();
        if (params != null) {
            //now use paymentIntentClientSecret to start transaction
            ConfirmPaymentIntentParams confirmParams = ConfirmPaymentIntentParams
                    .createWithPaymentMethodCreateParams(params, paymentIntentClientSecret);
            //start payment
            stripe.confirmPayment(this, confirmParams);
        }
        Log.i("TAG", "onPaymentSuccess: " + paymentIntentClientSecret);
    }

    private static final class PayCallback implements Callback {
        @NonNull
        private final WeakReference<CheckoutActivity> activityRef;

        PayCallback(@NonNull CheckoutActivity activity) {
            activityRef = new WeakReference<>(activity);
        }

        @Override
        public void onFailure(@NonNull Call call, @NonNull IOException e) {
            final CheckoutActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }

            activity.runOnUiThread(() ->
                    Toast.makeText(
                            activity, "Some Error Happened, Please try again...", Toast.LENGTH_LONG
                    ).show()
            );
        }

        @Override
        public void onResponse(@NonNull Call call, @NonNull final Response response)
                throws IOException {
            final CheckoutActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }

            if (!response.isSuccessful()) {
                activity.runOnUiThread(() ->
                        Toast.makeText(
                                activity, "Some Error Happened, Please try again...", Toast.LENGTH_LONG
                        ).show()
                );
            } else {
                activity.onPaymentSuccess(response);
            }
        }
    }

    private static final class PaymentResultCallback
            implements ApiResultCallback<PaymentIntentResult> {
        @NonNull
        private final WeakReference<CheckoutActivity> activityRef;

        PaymentResultCallback(@NonNull CheckoutActivity activity) {
            activityRef = new WeakReference<>(activity);
        }

        @Override
        public void onSuccess(@NonNull PaymentIntentResult result) {

            final CheckoutActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }

            PaymentIntent paymentIntent = result.getIntent();
            PaymentIntent.Status status = paymentIntent.getStatus();
            if (status == PaymentIntent.Status.Succeeded) {

                Toast.makeText(activity, "Ordered Successfully", Toast.LENGTH_LONG).show();
                try {
                    Thread.sleep(1500);
                    // Toast.makeText(activity,"Open Home Activity",Toast.LENGTH_LONG).show();
                    paymentViewModel.cancelOrder(order.getId());
                    paymentViewModel.createOrderInPayment(order);
                    Intent intent = new Intent(mContext, MainActivity.class);
                    mContext.startActivity(intent);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (status == PaymentIntent.Status.RequiresPaymentMethod) {
                // Payment failed – allow retrying using a different payment method
                activity.displayAlert(
                        "Payment failed",
                        Objects.requireNonNull(paymentIntent.getLastPaymentError()).getMessage()
                );
            }
        }

        @Override
        public void onError(@NonNull Exception e) {
            final CheckoutActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }

            // Payment request failed – allow retrying using the same payment method
            activity.displayAlert("Error", e.toString());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        payButton.dispose();
    }

>>>>>>> Stashed changes
}
