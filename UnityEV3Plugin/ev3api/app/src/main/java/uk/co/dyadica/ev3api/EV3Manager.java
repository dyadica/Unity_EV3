package uk.co.dyadica.ev3api;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.unity3d.player.UnityPlayer;

public class EV3Manager
{
    private static EV3Manager ev3Manager;

    public static String scriptLocation = "EV3Manager";
    public static String deviceName = "EV3";

    private Context context;
    private Activity activity;
    private UnityPlayer unityPlayer;

    // Bluetooth properties

    BluetoothAdapter bluetoothAdapter;

    private static boolean autoEnableBT = false;

    public Brick ev3;

    // region Constructor Methods

    public EV3Manager()
    {
        ev3Manager = this;
    }

    public static EV3Manager ev3Manager()
    {
        if(ev3Manager == null) {
            ev3Manager = new EV3Manager();
        }

        return ev3Manager;
    }

    // endregion Constructor Methods

    // region Multi-Plugin Methods

    ///////////////////////////////

    public void setContext(Context context)
    {
        this.context = context;
        activity = (Activity)context;
    }

    public void setUnityPlayer(UnityPlayer player)
    {
        this.unityPlayer = player;
    }

    public void setActivity(Activity activity)
    {
        this.activity = activity;
    }

    ///////////////////////////////

    // endregion Multi-Plugin Methods

    // region Initialisation & Bluetooth

    public void initialisePlugin()
    {
        UnityPlayer.UnitySendMessage(scriptLocation, "throwAlertUpdate", "Initialising EV3 Plugin!");

        // Perform initial checks for bluetooth

        performInitialChecks();

        // Connect to the defined device

        performDeviceConnection();
    }

    public void performInitialChecks()
    {
        // Simple Bluetooth test

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // If we have no bluetooth the return

        if (bluetoothAdapter == null)
        {
            performCleanup();
            showToast("This application requires a bluetooth capable device");
            return;
        }

        // showToast("Bluetooth Adapter Found!");

        if (!bluetoothAdapter.isEnabled())
        {
            UnityPlayer.UnitySendMessage(scriptLocation, "throwBluetoothAlert", "false");

            if(autoEnableBT)
            {
                requestBluetoothEnabled();

                while (!bluetoothAdapter.isEnabled())
                {
                    // Perform timeout here!
                }
            }
            else
            {
                performCleanup();
            }
        }
    }

    public void  performDeviceConnection()
    {
        if(!bluetoothAdapter.isEnabled())
        {
            showToast("Please enable bluetooth and try again!");
            return;
        }
        else
        {
            UnityPlayer.UnitySendMessage(scriptLocation, "throwBluetoothAlert", "true");
        }

        // All checks are passed so lets begin the connection!

        showToast("Initialising bluetooth connection!");

        // Get the bluetooth devices which are paired
        // with the system.

        ev3 = new Brick(this, deviceName);
        ev3.connect();
    }

    private void getPairedDevices()
    {
        // Removed for simplified (stealth) implementation!
    }

    public void performCleanup()
    {

    }

    public void requestBluetoothEnabled()
    {
        Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        activity.startActivityForResult(turnOn, 0);

        UnityPlayer.UnitySendMessage(scriptLocation, "throwBluetoothAlert", "true");
    }

    // endregion Initialisation & Bluetooth

    // region Bridging Methods

    public void playProgram(String fileName)
    {
        if(ev3 == null)
            return;

        System.out.println("Calling Play program: " + fileName);

        new ExecuteCommand(ev3, "play-program", fileName);
    }

    public void playAudio(int volume, String fileName)
    {
        if(ev3 == null)
            return;

        new ExecuteCommand(ev3, "play-audio", volume, fileName);
    }

    public void setMotorPower(String port, int power)
    {
        if(ev3 == null)
            return;

        new ExecuteCommand(ev3, "set-motor-power", port, power);
    }

    public void  startDriveMotors(String portL, String portR, int s1, int s2)
    {
        System.out.println("Motors: " + String.valueOf(s1) + "," + String.valueOf(s2) );

        if(ev3 == null)
            return;

        new ExecuteCommand(ev3, "start-direct-drive", portL, portR, s1, s2);
    }

    public void stopDriveMotors(String portL, String portR)
    {
        if(ev3 == null)
            return;

        new ExecuteCommand(ev3, "stop-direct-drive", portL, portR);
    }

    public void setLedPattern(String pattern)
    {
        if(ev3 == null)
            return;

        new ExecuteCommand(ev3, "set-led", pattern);
    }

    // endregion Bridging Methods

    // region Tools & Scripts

    public void sendMessage(String event, String message)
    {
        UnityPlayer.UnitySendMessage(scriptLocation, event, message);
    }

    public void showToast(final String message)
    {
        try
        {
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception ex)
        {
            UnityPlayer.UnitySendMessage(scriptLocation, "throwAlertUpdate", "Error: " + ex.getMessage());
        }
    }

    // endregion Tools & Scripts
}
