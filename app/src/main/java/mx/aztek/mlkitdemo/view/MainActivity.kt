package mx.aztek.mlkitdemo.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.vision.barcode.Barcode
import mx.aztek.mlkitdemo.R
import mx.aztek.mlkitdemo.databinding.ActivityBarcodeScannerBinding
import mx.aztek.mlkitdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val extras: Bundle? = result.data?.extras
                if (extras?.get("SCANNED_STRING") != null) {

                    binding.resultTextView.setText(extras?.get("SCANNED_STRING").toString())
                }
            }
        }

    fun startScanner(view: View) {

        var listSelectedCodes = ArrayList<Int>()

        if (binding.FORMATAZTEC.isChecked) listSelectedCodes.add(Barcode.FORMAT_AZTEC)
        if (binding.FORMATCODABAR.isChecked) listSelectedCodes.add(Barcode.FORMAT_CODABAR)
        if (binding.FORMATCODE128.isChecked) listSelectedCodes.add(Barcode.FORMAT_CODE_128)
        if (binding.FORMATCODE39.isChecked) listSelectedCodes.add(Barcode.FORMAT_CODE_39)
        if (binding.FORMATCODE93.isChecked) listSelectedCodes.add(Barcode.FORMAT_CODE_93)
        if (binding.FORMATDATAMATRIX.isChecked) listSelectedCodes.add(Barcode.FORMAT_DATA_MATRIX)
        if (binding.FORMATEAN13.isChecked) listSelectedCodes.add(Barcode.FORMAT_EAN_13)
        if (binding.FORMATEAN8.isChecked) listSelectedCodes.add(Barcode.FORMAT_EAN_8)
        if (binding.FORMATITF.isChecked) listSelectedCodes.add(Barcode.FORMAT_ITF)
        if (binding.FORMATPDF417.isChecked) listSelectedCodes.add(Barcode.FORMAT_PDF417)
        if (binding.FORMATQRCODE.isChecked) listSelectedCodes.add(Barcode.FORMAT_QR_CODE)
        if (binding.FORMATUPCA.isChecked) listSelectedCodes.add(Barcode.FORMAT_UPC_A)
        if (binding.FORMATUPCE.isChecked) listSelectedCodes.add(Barcode.FORMAT_UPC_E)

        var selectedCodes = when (listSelectedCodes.size) {
            0 -> IntArray(1) {Barcode.FORMAT_ALL_FORMATS}
            13 -> IntArray(1) {Barcode.FORMAT_ALL_FORMATS}
            else -> listSelectedCodes.toIntArray()
        }

        val intentScan = Intent(this, BarcodeScannerActivity::class.java)
        intentScan.putExtra("SELECTED_CODES", selectedCodes)

        resultLauncher.launch(intentScan)
    }
}