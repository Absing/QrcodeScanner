package com.storm.constant;


/**
 * Constants related to the {@link Scan#ACTION} Intent.
 */
public  final class Scan {
    /**
     * Send this intent to open the Barcodes app in scanning mode, find a barcode, and return
     * the results.
     */
    public static final String ACTION = "com.google.zxing.client.android.SCAN";

    /**
     * By default, sending this will decode all barcodes that we understand. However it
     * may be useful to limit scanning to certain formats. Use
     * {@link android.content.Intent#putExtra(String, String)} with one of the values below.
     * <p>
     * Setting this is effectively shorthand for setting explicit formats with {@link #FORMATS}.
     * It is overridden by that setting.
     */
    public static final String MODE = "SCAN_MODE";

    /**
     * Decode only UPC and EAN barcodes. This is the right choice for shopping apps which get
     * prices, reviews, etc. for products.
     */
    public static final String PRODUCT_MODE = "PRODUCT_MODE";

    /**
     * Decode only 1D barcodes.
     */
    public static final String ONE_D_MODE = "ONE_D_MODE";

    /**
     * Decode only QR codes.
     */
    public static final String QR_CODE_MODE = "QR_CODE_MODE";

    /**
     * Decode only Data Matrix codes.
     */
    public static final String DATA_MATRIX_MODE = "DATA_MATRIX_MODE";

    /**
     * Decode only Aztec.
     */
    public static final String AZTEC_MODE = "AZTEC_MODE";

    /**
     * Decode only PDF417.
     */
    public static final String PDF417_MODE = "PDF417_MODE";

    /**
     * Example: "EAN_13,EAN_8,QR_CODE". This overrides {@link #MODE}.
     */
    public static final String FORMATS = "SCAN_FORMATS";

    /**
     * Optional parameter to specify the id of the camera from which to recognize barcodes.
     * Overrides the default camera that would otherwise would have been selected.
     * If provided, should be an int.
     */
    public static final String CAMERA_ID = "SCAN_CAMERA_ID";

    /**
     */
    public static final String CHARACTER_SET = "CHARACTER_SET";

    /**
     * Optional parameters to specify the width and height of the scanning rectangle in pixels.
     * The app will try to honor these, but will clamp them to the size of the preview frame.
     * You should specify both or neither, and pass the size as an int.
     */
    public static final String WIDTH = "SCAN_WIDTH";
    public static final String HEIGHT = "SCAN_HEIGHT";

    /**
     * Desired duration in milliseconds for which to pause after a successful scan before
     * returning to the calling intent. Specified as a long, not an integer!
     * For example: 1000L, not 1000.
     */
    public static final String RESULT_DISPLAY_DURATION_MS = "RESULT_DISPLAY_DURATION_MS";

    /**
     * Prompt to show on-screen when scanning by intent. Specified as a {@link String}.
     */
    public static final String PROMPT_MESSAGE = "PROMPT_MESSAGE";

    /**
     * If a barcode is found, Barcodes returns {@link android.app.Activity#RESULT_OK} to
     * {@link android.app.Activity#onActivityResult(int, int, android.content.Intent)}
     * of the app which requested the scan via
     * {@link android.app.Activity#startActivityForResult(android.content.Intent, int)}
     * The barcodes contents can be retrieved with
     * {@link android.content.Intent#getStringExtra(String)}.
     * If the user presses Back, the result code will be {@link android.app.Activity#RESULT_CANCELED}.
     */
    public static final String RESULT = "SCAN_RESULT";

    /**
     * Call {@link android.content.Intent#getStringExtra(String)} with {@link #RESULT_FORMAT}
     * to determine which barcode format was found.
     */
    public static final String RESULT_FORMAT = "SCAN_RESULT_FORMAT";

    /**
     */
    public static final String RESULT_UPC_EAN_EXTENSION = "SCAN_RESULT_UPC_EAN_EXTENSION";

    /**
     * Call {@link android.content.Intent#getByteArrayExtra(String)} with {@link #RESULT_BYTES}
     * to get a {@code byte[]} of raw bytes in the barcode, if available.
     */
    public static final String RESULT_BYTES = "SCAN_RESULT_BYTES";

    /**
     */
    public static final String RESULT_ORIENTATION = "SCAN_RESULT_ORIENTATION";

    /**
     */
    public static final String RESULT_ERROR_CORRECTION_LEVEL = "SCAN_RESULT_ERROR_CORRECTION_LEVEL";

    /**
     * if available. The actual values will be set under a series of keys formed by adding 0, 1, 2, ...
     * to this prefix. So the first byte segment is under key "SCAN_RESULT_BYTE_SEGMENTS_0" for example.
     * Call {@link android.content.Intent#getByteArrayExtra(String)} with these keys.
     */
    public static final String RESULT_BYTE_SEGMENTS_PREFIX = "SCAN_RESULT_BYTE_SEGMENTS_";

    /**
     * Setting this to false will not save scanned codes in the history. Specified as a {@code boolean}.
     */
    public static final String SAVE_HISTORY = "SAVE_HISTORY";

    private Scan() {
    }

}