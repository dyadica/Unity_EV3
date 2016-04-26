package uk.co.dyadica.ev3api;

import android.bluetooth.BluetoothSocket;

/**
 * Created by dyadica.co.uk on 04/02/2016.
 */

public interface IConnected
{
    public void connected(BluetoothSocket socket);

    public void failed();
}
