package com.example.billing;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BillingActivity extends Activity {
    private TextView statusView;
    private Button loadButton;
    private InvoiceViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing);
        statusView = findViewById(R.id.status);
        loadButton = findViewById(R.id.load);
        viewModel = new InvoiceViewModel(InvoiceRepository.withSample());
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.loadInvoice("inv-1");
                statusView.setText(viewModel.getStatusText());
            }
        });
    }
}
