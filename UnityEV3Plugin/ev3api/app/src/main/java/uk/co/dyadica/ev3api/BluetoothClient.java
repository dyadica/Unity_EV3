package uk.co.dyadica.ev3api;

import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by dyadica.co.uk on 03/02/2016.
 */
public class BluetoothClient extends AsyncTask<Void, String, Void>
{
    IDataReceived callback;

    private final BluetoothSocket btSocket;

    private final InputStream btInStream;
    private final OutputStream btOutStream;

    private Brick brick;

    public BluetoothClient(Brick brick, BluetoothSocket socket, IDataReceived listener)
    {
        this.brick = brick;

        System.out.println("Initialising Bluetooth Client!");

        // Set the final callback ref
        callback = listener;

        // Set the final socket ref
        btSocket = socket;

        // Set up temp input and output streams
        InputStream tmpIn = null;
        OutputStream tmpOut = null;

        // Get the input and output streams, using
        // temp objects because member streams are
        // final.

        try
        {
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        }
        catch (IOException e)
        {
            System.err.println("Failed to create streams!");
        }

        btInStream = tmpIn;
        btOutStream = tmpOut;

        System.out.println("Bluetooth Client Initialised!");
    }

    // region AsyncTask

    public boolean writeData(byte[] data)
    {
        try
        {
            btOutStream.write(data);
            return true;
        }
        catch (IOException e)
        {
            return false;
        }
    }

    @Override
    protected Void doInBackground(Void... params)
    {
        loopReceiver();
        return null;
    }

    private void loopReceiver()
    {
        System.out.println("Initialising Bluetooth Receiver!");

        while (btSocket.isConnected())
        {
            // System.out.println("Looping Receiver!");

            try
            {
                int bytesAvailable = btInStream.available();

                if (bytesAvailable > 0) {

                    byte[] packetBytes = new byte[bytesAvailable];

                    btInStream.read(packetBytes);

                    byte[] data = new byte[2];
                    data[0] = packetBytes[0];
                    data[1] = packetBytes[1];

                    short msgLength = EndianConverter.swapToShort(data);

                    // System.out.println("Message Length: " + msgLength);

                    byte[] byteData = new byte[msgLength - 2];

                    for(int i = 2; i<msgLength; i++)
                    {
                        byteData[i-2] = packetBytes[i];
                    }

                    callback.dataReceived(byteData);
                }
            }
            catch (Exception ex)
            {
                System.err.println("Failed to read data: " + ex.getMessage());
            }
        }
    }

    // endregion AsyncTask
}
