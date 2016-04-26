package uk.co.dyadica.ev3api;

import android.bluetooth.BluetoothSocket;

/**
 * Created by dyadica.co.uk on 03/02/2016.
 */

public interface IDataReceived
{
    public void dataReceived(byte[] data);
}
