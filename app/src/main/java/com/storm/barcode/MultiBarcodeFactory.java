package com.storm.barcode;

import android.os.Handler;

import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.barcode.Barcode;


public class MultiBarcodeFactory implements MultiProcessor.Factory<Barcode> {

    private Handler mHandler;

    public MultiBarcodeFactory(Handler handler) {
        mHandler = handler;
    }

    @Override
    public Tracker<Barcode> create(Barcode barcode) {

        return new TrackerBarcode(mHandler);
    }
}
