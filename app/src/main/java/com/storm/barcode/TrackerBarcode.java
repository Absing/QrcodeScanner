package com.storm.barcode;

import android.os.Handler;
import android.os.Message;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.barcode.Barcode;

public class TrackerBarcode extends Tracker<Barcode> {

    private static final String TAG = "TrackerBarcode";
    private Handler mHandler;

    public TrackerBarcode(Handler handler) {
        mHandler = handler;
    }

    @Override
    public void onUpdate(Detector.Detections<Barcode> detections, Barcode barcode) {
        Message msg = Message.obtain();
        msg.what = 1;
        msg.obj = barcode.rawValue;
        mHandler.sendMessage(msg);
    }

}
