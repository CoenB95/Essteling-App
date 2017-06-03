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
    /**
     * @param inRange an map with all the input strings and an boolean if they are in range
     */
    void bluetoothChecked(Map<String, Boolean> inRange);

    /**
     * @param deviceName the name of the found device(this device name was in the input list)
     */
    void bluetoothDeviceFoundandWasCheckedFor(String deviceName);
}
