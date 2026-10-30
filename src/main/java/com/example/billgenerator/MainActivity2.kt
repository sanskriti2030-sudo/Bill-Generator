package com.example.billgenerator

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.billgenerator.databinding.ActivityMain2Binding
import java.text.NumberFormat
import java.util.Locale

class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)


        val productName = intent.getStringExtra("ProductName")
        val productPriceString = intent.getStringExtra("ProductPrice")
        val productQuantityString = intent.getStringExtra("ProductQuantity")

        val productPrice = productPriceString?.toDoubleOrNull()
        val productQuantity = productQuantityString?.toIntOrNull()

        if (
            productName.isNullOrBlank() ||
            productPrice == null ||
            productQuantity == null ||
            productPrice < 0 ||
            productQuantity <= 0
        ) {
            Toast.makeText(
                this,
                "Invalid product details. Please try again.",
                Toast.LENGTH_LONG
            ).show()

            finish()
            return
        }

        generateBill(
            productName,
            productPrice,
            productQuantity
        )
    }

    private fun generateBill(
        productName: String,
        productPrice: Double,
        productQuantity: Int
    ) {

        val subTotal = calculateTotal(
            productPrice,
            productQuantity
        )

        val discount = calculateDiscount(subTotal)

        val amountAfterDiscount = subTotal - discount

        val gst = calculateGST(amountAfterDiscount)

        val finalAmount = amountAfterDiscount + gst

        binding.billTv.text = """
            -------- PRODUCT BILL --------

            Product Name: $productName

            Product Price: ₹${formatMoney(productPrice)}

            Quantity: $productQuantity

            Subtotal: ₹${formatMoney(subTotal)}

            Discount (10%): ₹${formatMoney(discount)}

            Amount After Discount: ₹${formatMoney(amountAfterDiscount)}

            GST (18%): ₹${formatMoney(gst)}

            ------------------------------

            Final Amount: ₹${formatMoney(finalAmount)}
        """.trimIndent()
    }

    private fun calculateTotal(
        price: Double,
        quantity: Int
    ): Double {
        return price * quantity
    }

    private fun calculateDiscount(
        total: Double
    ): Double {
        return total * 10.0 / 100.0
    }

    private fun calculateGST(
        amount: Double
    ): Double {
        return amount * 18.0 / 100.0
    }

    private fun formatMoney(
        amount: Double
    ): String {
        val formatter = NumberFormat.getNumberInstance(
            Locale("en", "IN")
        )

        formatter.minimumFractionDigits = 2
        formatter.maximumFractionDigits = 2

        return formatter.format(amount)
    }
}