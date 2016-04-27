package uk.co.dyadica.ev3api;

import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by dyadica.co.uk on 03/02/2016.
 * <p>
 * THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY
 * KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS FOR A
 * PARTICULAR PURPOSE.
 * <p/>
 */

public abstract class Communication
{
    // private final EventListenerList listeners = new EventListenerList();

    private List<IDataReceived> listeners = new LinkedList<>();

    abstract void open();
    abstract void close();

    abstract boolean write(byte[] data);

    /**
     * Add a new listener
     * @param listener
     */
    public void addDataReceivedListener(IDataReceived listener)
    {
        listeners.add(listener);
    }

    /**
     * Fire new data to all listeners
     * @param data
     */
    public void fireDataReceived(byte[] data)
    {
        for(IDataReceived listener : getDataListeners())
        {
            listener.dataReceived(data);
        }
    }

    public IDataReceived[] getDataListeners()
    {
        IDataReceived[] array = new IDataReceived[listeners.size()];

        for(int i=0; i<listeners.size(); i++)
        {
            IDataReceived dl = listeners.get(i);
            array[i] = dl;
        }

        return array;
    }
}
