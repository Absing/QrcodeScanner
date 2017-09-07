package com.storm.barcode;

import android.util.Log;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.barcode.Barcode;
import com.storm.adapter.BarCodeAdapter;

public class TrackerBarcode extends Tracker<Barcode> {

    private static final String TAG = "TrackerBarcode";

    @Override
    public void onUpdate(Detector.Detections<Barcode> detections, Barcode barcode) {
        BarCodeAdapter.getInstance().add(barcode.rawValue);
        Log.e(TAG, "onUpdate: " + barcode.rawValue);
    }

}
