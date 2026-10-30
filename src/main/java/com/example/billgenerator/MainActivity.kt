package com.example.billgenerator

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.billgenerator.databinding.ActivityMainBinding
import kotlinx.serialization.BinaryFormat
import java.text.NumberFormat
import java.util.Locale
import kotlin.toString

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.genrateBillBtn.setOnClickListener {



            val intent = Intent(this, MainActivity2::class.java)

            intent.putExtra(
                "ProductName",
                binding.productNameEd.text.toString()
            )

            intent.putExtra(
                "ProductPrice",
                binding.productPriceEd.text.toString()
            )

            intent.putExtra(
                "ProductQuantity",
                binding.productQuantityEd.text.toString()
            )

            startActivity(intent)

        }

    }

    fun generateBill() {

        val productName = binding.productNameEd.text.toString()
        val productPrice = binding.productPriceEd.text.toString()
        val productQuantity = binding.productQuantityEd.text.toString()

        if (productName.isEmpty() || productPrice.isEmpty() || productQuantity.isEmpty()) {

            Toast.makeText(this, "Please fill all the blanks", Toast.LENGTH_SHORT).show()

        } else {

            val price = productPrice.toDouble()
            val quantity = productQuantity.toInt()
            val total = calculateTotal(price, quantity)
            val discount = calculateDiscount(total)
            val afterDiscountAmount = total - discount
            val gst = calculateGST(afterDiscountAmount)
            val finalAmount = afterDiscountAmount + gst
            val formatFinalAmount = formatMoney(finalAmount)
            val formatSubFinalAmount = formatMoney(afterDiscountAmount)

//            binding.billTv.text =
//                "Product name-$productName \nProduct price - ₹$price \nProduct quantity - $quantity \nSub-total price - ₹$formatSubFinalAmount \nDiscount given - 10% \nGST applied - 18%\nTotal amount - ₹$formatFinalAmount"

            binding.billTv.text =
                "____BILL ____\n\n"+
                        "Prouduct:$productName \n"+
                        "Price: ₹$price \n"+
                        "Quantity: $quantity \n"+
                        "Sub-total price: $formatSubFinalAmount \n"+
                        "Discount: 10% \n"+
                        "GST: 20% \n"+
                        "Total price: ₹$formatFinalAmount"

        }

    }

    fun calculateTotal(price: Double, quantity: Int): Double {

        return price * quantity

    }

    fun calculateDiscount(total: Double): Double {

        return total * 10 / 100

    }

    fun calculateGST(totalAmount: Double): Double {

        return totalAmount * 18 / 100

    }

    fun formatMoney(amountTotal: Double): String{

        val formatAmount = NumberFormat.getNumberInstance(Locale("en","IN"))
        return formatAmount.format(amountTotal)


    }
}