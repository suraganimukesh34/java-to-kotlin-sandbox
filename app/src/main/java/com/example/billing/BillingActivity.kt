package com.example.billing

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class BillingActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_billing)
        val statusView = findViewById<TextView>(R.id.status)
        val loadButton = findViewById<Button>(R.id.load)
        val viewModel = InvoiceViewModel(InvoiceRepository.withSample())
        loadButton.setOnClickListener {
            viewModel.loadInvoice("inv-1")
            statusView.text = viewModel.statusText
        }
    }
}
