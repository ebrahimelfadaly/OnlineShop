package com.example.onlineshop.ui.Payment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import com.example.onlineshop.R





import kotlinx.android.synthetic.main.activity_pay_pal.*


class PayTapsActivity : AppCompatActivity() {
    /*
    private var token: String? = null
    private var transRef: String? = null
    var version = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay_taps)
       // version = "Version: " + BuildConfig.VERSION_NAME
        pay.setOnClickListener {
            val configData = generatePaytabsConfigurationDetails()
            startCardPayment(this, configData, this)
        }
    }
    private fun generatePaytabsConfigurationDetails(): PaymentSdkConfigurationDetails {

        val billingData = PaymentSdkBillingDetails(
            "cairo", "AF",
            "123@gmail.com", "billing_name",
            "010055005", "state_billing",
            "street_billing", "00000"
        )
        val shippingData = PaymentSdkShippingDetails(
            "cairo","AF",
            "123@gmail.com", "billing_name",
            "010055005", "state_billing",
            "street_billing", "00000"
        )
        val configData = PaymentSdkConfigBuilder("profileId", "serverKey", "clientKey", 20.0,"USD")
            .setCartDescription("cartDesc")
            //.setLanguageCode("locale")
            .setBillingData(billingData)
            .setMerchantCountryCode("AF")
            .setShippingData(shippingData)
            .setCartId("1")
            .showBillingInfo(false)
            .showShippingInfo(false)



        return configData.build()
    }


    override fun onError(error: PaymentSdkError) {
        Toast.makeText(this, "${error.msg}+***********", Toast.LENGTH_SHORT).show()
    }

    override fun onPaymentCancel() {
        Toast.makeText(this, "onPaymentCancel", Toast.LENGTH_SHORT).show()

    }

    override fun onPaymentFinish(paymentSdkTransactionDetails: PaymentSdkTransactionDetails) {
        token = paymentSdkTransactionDetails.token
        transRef = paymentSdkTransactionDetails.transactionReference
        Toast.makeText(
            this,
            "${paymentSdkTransactionDetails.paymentResult?.responseMessage ?: "PaymentFinish"}",
            Toast.LENGTH_SHORT
        ).show()
    }

     */
}