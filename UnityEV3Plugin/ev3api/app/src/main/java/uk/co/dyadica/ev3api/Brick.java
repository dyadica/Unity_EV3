package uk.co.dyadica.ev3api;

import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.util.Log;

import com.unity3d.player.UnityPlayer;

import java.util.LinkedHashMap;
import java.util.Map;

import uk.co.dyadica.ev3api.EV3Types.*;

/**
 * Created by dyadica.co.uk on 03/02/2016.
 */

public class Brick extends AsyncTask<Void,Void,Void> implements IDataReceived
{
    private EV3Manager ev3Manager;

    private Communication comm = null;
    public final DirectCommand directCommand;
    public Command batchCommand;

    public String deviceName;

    public boolean isConnected = false;

    // Create port list

    public Map<InputPort, Port> ports =
            new LinkedHashMap<>();

    public Port[] portsList;

    public  static boolean pollEnabled = true;

    public Brick(EV3Manager ev3Manager, String deviceName)
    {
        this.ev3Manager = ev3Manager;
        this.deviceName = deviceName;

        comm = new BluetoothConnection(deviceName, this);
        this.comm.addDataReceivedListener(this);

        directCommand = new DirectCommand(this);

        // populate ports

        int index = 0;

        portsList = new Port[InputPort.values().length];

        for(InputPort inputPort : InputPort.values())
        {
            Port p = new Port(inputPort.toString(), inputPort, index);
            portsList[index] = p;
            ports.put(inputPort, p);

            index++;
        }

        index = 0;

        // this.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void connect()
    {
        comm.open();
    }

    public void disconnect()
    {
        comm.close();
    }

    public void bluetoothConnected()
    {
        isConnected = true;

        ev3Manager.sendMessage("throwBluetoothConnection", "true");

        this.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    // region AsyncTask

    @Override
    protected Void doInBackground(Void... params)
    {
        while (pollEnabled)
        {
            try
            {
                PollBrick();
            }
            catch (ArgumentException e)
            {
                System.err.println("Failed to poll brick ports");
            }
        }

        // Poll for changes
        return null;
    }

    public void  PollBrick() throws ArgumentException
    {
        // Next we poll the ports. I must also rationalise this
        // down to a singular call and response.

        try
        {
            pollPorts();
        }
        catch (Exception ex)
        {
            System.err.println("Poll Error: " + ex.getMessage().toString());
        }
    }

    private void pollPorts() throws ArgumentException
    {
        for(InputPort inputPort:InputPort.values())
        {
            /*
            DeviceType dt = directCommand.getDeviceType(inputPort);

            if (dt != ports.get(inputPort).deviceType)
            {
                ports.get(inputPort).deviceType = dt;

                System.out.println("Port." + inputPort.name() + " : DeviceType." + ports.get(inputPort).deviceType.toString());
                // throw unity event

                String portDeviceUpdate = inputPort.name() + "," + dt.toString();

                ev3Manager.sendMessage("throwPortDeviceUpdate", portDeviceUpdate);
            }

            ports.get(inputPort).deviceType =
                    directCommand.getDeviceType(inputPort);

            ports.get(inputPort).mode =
                    directCommand.getDeviceMode(inputPort);

                    */

            // singular port call

            // Archive existing

            DeviceType dt = ports.get(inputPort).deviceType;

            // Update the whole port

            ports.get(inputPort).port =
                    directCommand.getPortState(ports.get(inputPort));

            // Update the port to reflect (long-winded!)

            ports.get(inputPort).deviceType =
                    ports.get(inputPort).port.deviceType;

            ports.get(inputPort).rawValue =
                    ports.get(inputPort).port.rawValue;

            ports.get(inputPort).SIValue =
                    ports.get(inputPort).port.SIValue;

            ports.get(inputPort).percentValue =
                    ports.get(inputPort).port.percentValue;

            ports.get(inputPort).mode =
                    ports.get(inputPort).port.mode;

            // Convert values to strings for sending to unity

            String di = ports.get(inputPort).deviceType.toString();
            String dm = String.valueOf(((int) ports.get(inputPort).mode));

            String dr = String.valueOf(ports.get(inputPort).rawValue);
            String ds = String.valueOf(ports.get(inputPort).SIValue);
            String dp = String.valueOf(ports.get(inputPort).percentValue);

            // Throw a port connection or device update

            if (dt != ports.get(inputPort).deviceType)
            {
                String portDeviceUpdate = inputPort.name() + "," + di;
                ev3Manager.sendMessage("throwPortDeviceUpdate", portDeviceUpdate);
            }

            // Throw an input device update event

            if(ports.get(inputPort).deviceType != DeviceType.Empty &&
                    ports.get(inputPort).deviceType != DeviceType.Unknown &&
                    ports.get(inputPort).deviceType != DeviceType.WrongPort &&
                    ports.get(inputPort).deviceType != DeviceType.Initializing &&
                    ports.get(inputPort).deviceType != DeviceType.LMotor &&
                    ports.get(inputPort).deviceType != DeviceType.MMotor)
            {
                String portSensorUpdate = inputPort.name() + ","
                        + di + ","
                        + dm + ","
                        + dr + ","
                        + ds + ","
                        + dp;

                ev3Manager.sendMessage("throwPortSensorUpdate", portSensorUpdate);
            }
        }

        /*
        ports.get(InputPort.One).port =
                directCommand.getPortState(ports.get(InputPort.One));

        System.out.println(ports.get(InputPort.One).port.deviceType);
        */
    }

    // endregion AsyncTask

    // region IDataReceived

    @Override
    public void dataReceived(byte[] data)
    {
        // Incoming bluetooth data initialised via this script!
        ResponseManager.handleResponse(data);
    }


    // endregion IDataReceived

    // region Send Commands

    /**
     * Send the specified command
     * @param c the command to send
     * @param waitResponse
     */
    public void sendCommand(Command c, boolean waitResponse)
    {
        comm.write(c.toBytes());

        if (c.commandType == CommandType.DirectReply || c.commandType == CommandType.SystemReply)
            ResponseManager.listenForResponse(c.response, waitResponse);
    }


    public void sendCommand(Command c)
    {
        sendCommand(c, true);
    }

    /**
     * Send the batchCommand : before sending this command you must instanciate the
     * attribute batchCommand with a new command and add commands
     * @param waitResponse
     */
    public void sendBatchCommand(boolean waitResponse)
    {
        if (batchCommand != null)
            sendCommand(batchCommand, waitResponse);
        else
            System.err.println("The batch command is not set");
    }

    public void sendBatchCommand()
    {
        sendBatchCommand(true);
    }

    // endregion Send Commands
}
