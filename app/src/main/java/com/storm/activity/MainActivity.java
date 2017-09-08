/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.storm.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.storm.R;
import com.storm.adapter.HomeAdapter;
import com.storm.barcode.MultiBarcodeFactory;
import com.storm.bean.Result;
import com.storm.camera.CameraSource;
import com.storm.camera.CameraSourcePreview;
import com.storm.constant.ResultType;
import com.storm.constant.Scan;

import java.io.IOException;
import java.util.ArrayList;

public final class MainActivity extends AppCompatActivity {

    private static final int RC_HANDLE_GMS = 9001;

    private static final int RC_HANDLE_CAMERA_PERM = 2;

    public static final String AutoFocus = "AutoFocus";
    public static final String UseFlash = "UseFlash";
    public static final String BarcodeObject = "Barcode";

    private CameraSource mCameraSource;
    private CameraSourcePreview mPreview;
    private ArrayList<Result> results;
    private HomeAdapter mHomeAdapter;
    private String mIntentAction;

    private Handler mHandler = new Handler(message -> {
        if (message.what == 1) {
            String scanResult = message.obj.toString();
            if (Scan.ACTION.equals(mIntentAction)) {
                Intent intent = new Intent();
                intent.putExtra(Scan.RESULT, scanResult);
                setResult(RESULT_OK, intent);
                finish();
                return false;
            }
            Result result = new Result(scanResult, ResultType.NORMAL);
            if (!results.contains(result)) {
                results.add(result);
                if (mHomeAdapter != null) {
                    mHomeAdapter.notifyDataSetChanged();
                }
            }
        }
        return false;
    });

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_main);
        initVars();
        createCamera();
        mIntentAction = getIntent().getAction();
        Log.e("------", "onCreate: " + mIntentAction);
    }

    private void createCamera() {
        int rc = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (rc == PackageManager.PERMISSION_GRANTED) {
            createCameraSource(false);
        } else {
            requestCameraPermission();
        }
    }

    private void initVars() {
        results = new ArrayList<>();
        mHomeAdapter = new HomeAdapter(R.layout.item_result, results);
        mPreview = findViewById(R.id.preview);
        RecyclerView scanResultShowView = findViewById(R.id.scan_result);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        scanResultShowView.setLayoutManager(layoutManager);
        scanResultShowView.setAdapter(mHomeAdapter);
        mHomeAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            ClipboardManager clipboard = (ClipboardManager)
                    getSystemService(Context.CLIPBOARD_SERVICE);
            String content = results.get(position).content;
            ClipData clipData = ClipData.newPlainText("com.storm", content);
            if (clipboard != null) {
                clipboard.setPrimaryClip(clipData);
                Toast.makeText(MainActivity.this, "复制成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void requestCameraPermission() {
        final String[] permissions = new String[]{Manifest.permission.CAMERA};
        if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(this, permissions, RC_HANDLE_CAMERA_PERM);
            return;
        }
        final Activity thisActivity = this;
        View.OnClickListener listener = view -> ActivityCompat.requestPermissions(thisActivity, permissions,
                RC_HANDLE_CAMERA_PERM);
        findViewById(R.id.topLayout).setOnClickListener(listener);
    }

    @SuppressLint("InlinedApi")
    private void createCameraSource(boolean useFlash) {
        Context context = getApplicationContext();

        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(context).build();

        MultiBarcodeFactory objectMultiBarcodeFactory = new MultiBarcodeFactory(mHandler);
        barcodeDetector.setProcessor(new MultiProcessor.Builder<>(objectMultiBarcodeFactory).build());

        if (!barcodeDetector.isOperational()) {
            IntentFilter lowStorageFilter = new IntentFilter(Intent.ACTION_DEVICE_STORAGE_LOW);
            boolean hasLowStorage = registerReceiver(null, lowStorageFilter) != null;

            if (hasLowStorage) {
                Toast.makeText(this, R.string.low_storage_error, Toast.LENGTH_LONG).show();
            }
        }

        CameraSource.Builder builder = new CameraSource.Builder(getApplicationContext(), barcodeDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(2624, 1968)
                .setRequestedFps(24.0f);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            builder = builder.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        }

        mCameraSource = builder
                .setFlashMode(useFlash ? Camera.Parameters.FLASH_MODE_TORCH : null)
                .build();
    }

    /**
     * Restarts the camera.
     */
    @Override
    protected void onResume() {
        super.onResume();
        startCameraSource();
    }

    /**
     * Stops the camera.
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (mPreview != null) {
            mPreview.stop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPreview != null) {
            mPreview.release();
        }
    }

    /**
     * Callback for the result from requesting permissions. This method
     * is invoked for every call on {@link #requestPermissions(String[], int)}.
     * <p>
     * <strong>Note:</strong> It is possible that the permissions request interaction
     * with the user is interrupted. In this case you will receive empty permissions
     * and results arrays which should be treated as a cancellation.
     * </p>
     *
     * @param requestCode  The request code passed in {@link #requestPermissions(String[], int)}.
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *                     which is either {@link PackageManager#PERMISSION_GRANTED}
     *                     or {@link PackageManager#PERMISSION_DENIED}. Never null.
     * @see #requestPermissions(String[], int)
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != RC_HANDLE_CAMERA_PERM) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // we have permission, so create the camerasource
//            boolean autoFocus = getIntent().getBooleanExtra(AutoFocus, false);
            boolean useFlash = getIntent().getBooleanExtra(UseFlash, false);
            createCameraSource(useFlash);
        }
    }

    /**
     * Starts or restarts the camera source, if it exists.  If the camera source doesn't exist yet
     * (e.g., because onResume was called before the camera source was created), this will be called
     * again when the camera source is created.
     */
    private void startCameraSource() throws SecurityException {
        // check that the device has play services available.
        int code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(
                getApplicationContext());
        if (code != ConnectionResult.SUCCESS) {
            Dialog dlg =
                    GoogleApiAvailability.getInstance().getErrorDialog(this, code, RC_HANDLE_GMS);
            dlg.show();
        }

        if (mCameraSource != null) {
            try {
                mPreview.start(mCameraSource);
            } catch (IOException e) {
                mCameraSource.release();
                mCameraSource = null;
            }
        }
    }
}
