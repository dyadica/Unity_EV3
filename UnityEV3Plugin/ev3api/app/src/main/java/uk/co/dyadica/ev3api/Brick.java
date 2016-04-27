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
 * <p>
 * THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY
 * KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS FOR A
 * PARTICULAR PURPOSE.
 * <p/>
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

    // buttons

    public Map<BrickButton, EV3Button> ev3Buttons =
            new LinkedHashMap<>();

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

        // Populate buttons

        index = 0;

        for(BrickButton ev3Button : BrickButton.values())
        {
            ev3Buttons.put(ev3Button,new EV3Button(ev3Button.toString(), ev3Button, index++));
        }

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
            System.err.println("Poll Ports Error: " + ex.getMessage().toString());
        }

        try
        {
            pollButtons();
        }
        catch (Exception ex)
        {
            System.err.println("Poll Buttons Error: " + ex.getMessage().toString());
        }
    }

    private void pollButtons() throws ArgumentException
    {
        // First poll all the bricks buttons

        int[] buttons = directCommand.areButtonsPressed();

        // Check against the previous state and fire off
        // events to reflect any changes. This can and will
        // be rationalised to a single call.

        // region Button Check

        if(ev3Buttons.get(BrickButton.Up).pressed != (buttons[0] == 1))
        {
            ev3Buttons.get(BrickButton.Up).pressed = (buttons[0] == 1);
            // System.out.println("Button.Up: " + (String.valueOf(ev3Buttons.get(BrickButton.Up).pressed)));
            ev3Manager.sendMessage("throwButtonUpdate", "Up," + ev3Buttons.get(BrickButton.Up).pressed);
        }

        if(ev3Buttons.get(BrickButton.Enter).pressed != (buttons[1] == 1))
        {
            ev3Buttons.get(BrickButton.Enter).pressed = (buttons[1] == 1);
            // System.out.println("Button.Enter: " + (String.valueOf(ev3Buttons.get(BrickButton.Enter).pressed)));
            ev3Manager.sendMessage("throwButtonUpdate", "Enter," + ev3Buttons.get(BrickButton.Enter).pressed);
        }

        if(ev3Buttons.get(BrickButton.Down).pressed != (buttons[2] == 1))
        {
            ev3Buttons.get(BrickButton.Down).pressed = (buttons[2] == 1);
            // System.out.println("Button.Down: " + (String.valueOf(ev3Buttons.get(BrickButton.Down).pressed)));
            ev3Manager.sendMessage("throwButtonUpdate", "Down," + ev3Buttons.get(BrickButton.Down).pressed);
        }

        if(ev3Buttons.get(BrickButton.Right).pressed != (buttons[3] == 1))
        {
            ev3Buttons.get(BrickButton.Right).pressed = (buttons[3] == 1);
            // System.out.println("Button.Right: " + (String.valueOf(ev3Buttons.get(BrickButton.Right).pressed)));
            ev3Manager.sendMessage("throwButtonUpdate", "Right," + ev3Buttons.get(BrickButton.Right).pressed);
        }

        if(ev3Buttons.get(BrickButton.Left).pressed != (buttons[4] == 1))
        {
            ev3Buttons.get(BrickButton.Left).pressed = (buttons[4] == 1);
            // System.out.println("Button.Left: " + (String.valueOf(ev3Buttons.get(BrickButton.Left).pressed)));
            ev3Manager.sendMessage("throwButtonUpdate", "Left," + ev3Buttons.get(BrickButton.Left).pressed);
        }

        if(ev3Buttons.get(BrickButton.Back).pressed != (buttons[5] == 1))
        {
            ev3Buttons.get(BrickButton.Back).pressed = (buttons[5] == 1);
            // System.out.println("Button.Back: " + (String.valueOf(ev3Buttons.get(BrickButton.Back).pressed)));
            ev3Manager.sendMessage("throwButtonUpdate", "Back," + ev3Buttons.get(BrickButton.Back).pressed);
        }

        if(ev3Buttons.get(BrickButton.Any).pressed != (buttons[6] == 1))
        {
            ev3Buttons.get(BrickButton.Any).pressed = (buttons[6] == 1);
            // System.out.println("Button.Any: " + (String.valueOf(ev3Buttons.get(BrickButton.Any).pressed)));
            ev3Manager.sendMessage("throwButtonUpdate", "Any," + ev3Buttons.get(BrickButton.Any).pressed);
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
