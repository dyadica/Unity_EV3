package uk.co.dyadica.ev3api;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.Set;

/**
 * Created by dyadica.co.uk on 03/02/2016.
 */
public class BluetoothConnection extends Communication implements IConnected
{
    public BluetoothAdapter bluetoothAdapter;
    public Set<BluetoothDevice> pairedDevices;

    public BluetoothSocket bluetoothSocket;
    public BluetoothDevice bluetoothDevice;

    private String deviceName;

    public BluetoothClient client;

    private IDataReceived callback;

    private Brick brick;

    public BluetoothConnection(String deviceName, Brick brick)
    {
        this.deviceName = deviceName;
        this.brick = brick;

        System.out.println("Initialising Bluetooth Communication!");
    }

    // region Communication

    @Override
    void open()
    {
        try
        {
            new openConnection().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, this);
        }
        catch (Exception ex)
        {
            System.err.println("Failed to open bluetooth connection!");
        }
    }

    @Override
    void close()
    {
        if (bluetoothSocket != null && bluetoothSocket.isConnected())
        {
            try
            {
                bluetoothSocket.close();

                System.out.println("Socket closed: " + bluetoothSocket.isConnected());
                System.out.println("Wait complete end of connection!");

                try
                {
                    Thread.sleep(4000);
                }
                catch (InterruptedException ex)
                {
                    System.err.println("Interrupted during disconnection : " + ex.getMessage());
                }
            }
            catch (Exception e)
            {
                System.err.println("Error during disconnection : " + e.getMessage());
            }
        }
    }

    @Override
    boolean write(byte[] data)
    {
        try
        {
            return client.writeData(data);
        }
        catch (Exception e)
        {
            System.err.println("Error to write bytes : " + e.getMessage());
            return false;
        }
    }

    @Override
    public void connected(BluetoothSocket socket)
    {
        // We have connected so initialise the client!

        client = new BluetoothClient(brick, socket, new IDataReceived()
        {
            @Override
            public void dataReceived(byte[] data)
            {
                fireDataReceived(data);
            }
        });

        // Set the client going and begin listening for data

        client.execute();

        // Inform the brick that we are setup and ready to go!

        brick.bluetoothConnected();
    }

    @Override
    public void failed()
    {
        // We have a timeout or null response so fail the connection!

        bluetoothSocket = null;
    }

    // endregion Communication

    // region BluetoothConnection Task

    public class openConnection extends AsyncTask<IConnected, String, BluetoothSocket>
    {
        IConnected callback;

        @Override
        protected BluetoothSocket doInBackground(IConnected... params)
        {
            callback = params[0];

            System.out.println("Opening Bluetooth Connection!");

            // Simple Bluetooth test

            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            // If we have no bluetooth the return false

            if (bluetoothAdapter == null)
                return null;

            // Check for paired devices

            System.out.println("Checking for paired devices!");

            pairedDevices = bluetoothAdapter.getBondedDevices();

            // If we have no paired devices then return false

            if (pairedDevices.size() <= 0)
                return null;

            // Check to see if the named device exists

            System.out.println("Checking to see if the named device exists!");

            for (BluetoothDevice device : pairedDevices)
            {
                if(device.getName().equals(deviceName))
                {
                    bluetoothDevice = device;
                }
            }

            // If the device has not been found then return false

            if(bluetoothDevice == null)
                return null;

            System.out.println("The named device exists!");

            // Initialise the connection
            // Set the socket etc here!

            String uuid = "00001101-0000-1000-8000-00805f9b34fb";

            BluetoothSocket tmpSocket;

            try
            {
                // Use the Serial Port Profile
                tmpSocket = bluetoothDevice.createRfcommSocketToServiceRecord(java.util.UUID.fromString(uuid));
            }
            catch (IOException e)
            {
                System.err.println("Failed to create RfcommSocketToServiceRecord socket");
                return null;
            }

            bluetoothSocket = tmpSocket;

            try
            {
                bluetoothSocket.connect();
            }
            catch (Exception ex)
            {
                System.err.println("Failed to connect to socket!");
                return null;
            }

            System.out.println("Connected to device: " + deviceName);

            return bluetoothSocket;
        }

        @Override
        protected void onPostExecute(BluetoothSocket socket)
        {
            if(socket == null)
            {
                callback.failed();
            }
            else
            {
                callback.connected(socket);
            }
        }
    }

    // endregion BluetoothConnection Task

    @Override
    public void finalize() throws Throwable
    {
        super.finalize();
        close();
    }
}
