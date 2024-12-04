package com.example.jingle;

import java.lang.reflect.Method;
import android.Manifest;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.hardware.display.DisplayManager;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.nfc.NfcManager;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.jingle.ui.MyFirstPageTV;
import com.example.jingle.ui.MyPresentation;
import com.example.jingle.ui.MySplashTV;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import android.nfc.NfcAdapter;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class FirstPage extends AppCompatActivity {
    private static final int REQUEST_CODE_BLUETOOTH_PERMISSIONS = 1;
    private static final int REQUEST_CODE_WIFI_PERMISSIONS = 2;


    private String wifiSSID = "JingleWiFi";
    private String wifiPassword = "12345678";
    private String accountToken = "user_token";


    public Bitmap dsc;

    @Override
    protected void onPause() {
        super.onPause();
        // Останавливаем обнаружение NFC, когда активность приостанавливается
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this);
        }
    }

    private NfcAdapter nfcAdapter;

    @Override
    protected void onResume() {
        super.onResume();

        // Получаем доступ к NFC
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        // Проверяем, поддерживает ли устройство NFC
        if (nfcAdapter == null) {
            // NFC не поддерживается на устройстве
            Log.e("NFC", "NFC не поддерживается на этом устройстве");
            return; // Выход из метода, если NFC не поддерживается
        }

        // Проверяем, включен ли NFC
        if (!nfcAdapter.isEnabled()) {
            // NFC выключен
            Log.e("NFC", "NFC выключен. Пожалуйста, включите NFC в настройках.");
            return; // Выход из метода, если NFC выключен
        }

        // Включаем NFC для получения данных через приложение
        enableForegroundDispatch();
    }

    private void enableForegroundDispatch() {
        // Проверяем, не null ли nfcAdapter
        if (nfcAdapter != null) {
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                    new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), PendingIntent.FLAG_IMMUTABLE);
            IntentFilter[] intentFiltersArray = new IntentFilter[]{
                    new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED)
            };
            String[][] techListsArray = new String[][]{
                    new String[]{android.nfc.tech.NfcA.class.getName()}
            };

            // Включаем распознавание NFC
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, techListsArray);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (tag == null) {
            Toast.makeText(this, "NFC-тег не обнаружен", Toast.LENGTH_SHORT).show();
            return;
        }

        // Обработка данных с NFC-тега
        Ndef ndef = Ndef.get(tag);
        if (ndef != null) {
            try {
                NdefMessage ndefMessage = ndef.getCachedNdefMessage();
                if (ndefMessage != null) {
                    byte[] payload = ndefMessage.getRecords()[0].getPayload();
                    String message = new String(payload, "UTF-8");
                    Toast.makeText(this, "Считано сообщение: " + message, Toast.LENGTH_SHORT).show();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }




    private void showPresentation(Display display) {
        Log.i("vfdsfv","vcc");
        presentation = new MyFirstPageTV(this, display, this);
        presentation.show();
    }
    private DisplayManager displayManager;
    private MyFirstPageTV presentation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstpage);
        getSupportActionBar().hide();

        checkAndRequestPermissions();
        enableWiFiHotspot();
        Log.i("fes","fgbd");
        displayManager = (DisplayManager) getSystemService(DISPLAY_SERVICE);
        if (displayManager != null) {
            Toast.makeText(this, "sdfsdf", Toast.LENGTH_SHORT).show();
            Display[] dssda = displayManager.getDisplays();
            Log.i("sdfdsa",String.valueOf((dssda[1].getDisplayId())));
            Display display = displayManager.getDisplay(dssda[1].getDisplayId());
            showPresentation(display);
            displayManager.registerDisplayListener(new DisplayManager.DisplayListener() {
                @Override
                public void onDisplayAdded(int displayId) {
                    Log.d("MainActivity", "Display added: " + displayId);
                    Display display = displayManager.getDisplay(1);
                    if (display != null && display.getDisplayId() != Display.DEFAULT_DISPLAY) {
                        showPresentation(display);
                    }
                }

                @Override
                public void onDisplayChanged(int displayId) {
                    Log.d("MainActivity", "Display changed: " + displayId);
                }

                @Override
                public void onDisplayRemoved(int displayId) {
                    Log.d("MainActivity", "Display removed: " + displayId);
                    if (presentation != null && presentation.getDisplay().getDisplayId() == displayId) {
                        presentation.dismiss();
                        presentation = null;
                    }
                }
            }, null);
        }






        NfcManager nfcManager = (NfcManager) getSystemService(NFC_SERVICE);
        nfcAdapter = nfcManager.getDefaultAdapter();

        if (nfcAdapter == null) {
            Toast.makeText(this, "NFC не поддерживается на этом устройстве", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!nfcAdapter.isEnabled()) {
            Toast.makeText(this, "Включите NFC в настройках устройства", Toast.LENGTH_SHORT).show();
        }

    }

    private void checkAndRequestPermissions() {
        String[] permissions = {
                Manifest.permission.BLUETOOTH_SCAN,
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.ACCESS_WIFI_STATE
        };

        boolean permissionsNeeded = false;
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsNeeded = true;
                break;
            }
        }

        if (permissionsNeeded) {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_WIFI_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_WIFI_PERMISSIONS) {
            boolean allPermissionsGranted = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false;
                    break;
                }
            }

            if (allPermissionsGranted) {
                Log.i("PermissionCheck", "All permissions granted");
            } else {
                Log.i("PermissionCheck", "Permissions are required for this app");
            }
        }
    }

    private void startBluetoothOperations() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter != null) {
            BluetoothDevice device = bluetoothAdapter.getRemoteDevice(getBluetoothMacAddress());
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            String deviceInfo = device.getName() + "\n" + device.getAddress();
            Log.i("BluetoothInfo", deviceInfo);
        }
    }

    private void connectToWiFi() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }

        WifiConfiguration wifiConfig = new WifiConfiguration();
        wifiConfig.SSID = "\"" + wifiSSID + "\"";
        wifiConfig.preSharedKey = "\"" + wifiPassword + "\"";

        int netId = wifiManager.addNetwork(wifiConfig);
        if (netId != -1) {
            wifiManager.disconnect();
            wifiManager.enableNetwork(netId, true);
            wifiManager.reconnect();
            Log.i("WiFiConnection", "Connected to Wi-Fi: " + wifiSSID);
        } else {
            Log.i("WiFiConnection", "Failed to add Wi-Fi network");
        }
    }

    private Bitmap generateWiFiQRCode(String ssid, String password) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            // Формируем строку с данными Wi-Fi
            String wifiContent = "WIFI:T:WPA;S:" + ssid + ";P:" + password + ";;";

            int width = 500;
            int height = 500;
            com.google.zxing.common.BitMatrix bitMatrix = qrCodeWriter.encode(wifiContent, BarcodeFormat.QR_CODE, width, height);
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }


    public void enableWiFiHotspot() {
        Log.i("hi","yr");
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null) {
            Log.i("go","yr");
            WifiConfiguration wifiConfig = new WifiConfiguration();
            wifiConfig.SSID = "MyHotspot"; // Имя сети
            wifiConfig.preSharedKey = "password"; // Пароль сети
            wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA2_PSK);

            try {
                // Получаем метод через Reflection
                Method method = wifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
                // Включаем точку доступа
                method.invoke(wifiManager, wifiConfig, true);
                Toast.makeText(this, "Точка доступа включена", Toast.LENGTH_SHORT).show();
                ImageView myImage = (ImageView) findViewById(R.id.imageVie4);
                myImage.setImageBitmap(generateWiFiQRCode(wifiConfig.SSID, wifiConfig.preSharedKey));
                Log.i("Точка доступа включена","yr");
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("Ошибка при включении точки доступа","yr");
                Toast.makeText(this, "Ошибка при включении точки доступа", Toast.LENGTH_SHORT).show();
            }
        }
    }






    private Bitmap generateQRCode(String content) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            int width = 500;
            int height = 500;
            com.google.zxing.common.BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height);
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getBluetoothMacAddress() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter != null) {
            return bluetoothAdapter.getAddress();
        }
        return null;
    }

}
