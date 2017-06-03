package nl.l15vdef.essteling.bluetooth;

import java.util.Map;

/**
 * Created by B3 Maarten on 22/05/2017.
 */

public /**
 * Interface for the listener for bluetoothInRageDetector this method gets called in
 * BluetoothInRageDetector when refreshes the check for bluetooth devices.
 */
interface BluetoothInRangeChanged {
    void bluetoothChecked(Map<String, Boolean> inRange);
    void bluetoothDeviceFoundandWasCheckedFor(String deviceName);
}
