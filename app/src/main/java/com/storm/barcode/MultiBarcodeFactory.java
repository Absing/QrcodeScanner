package com.storm.barcode;

import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.barcode.Barcode;



public class MultiBarcodeFactory implements MultiProcessor.Factory<Barcode> {

    @Override
    public Tracker<Barcode> create(Barcode barcode) {

        return new TrackerBarcode();
    }
}
