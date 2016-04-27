package uk.co.dyadica.ev3api;

import android.os.AsyncTask;

/**
 * Created by dyadica.co.uk on 04/02/2016.
 * <p>
 * THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY
 * KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS FOR A
 * PARTICULAR PURPOSE.
 * <p/>
 */

public class ExecuteCommand extends AsyncTask<Void, Void, Void>
{
    String command;
    Brick ev3;

    int i1, i2;
    String p1, p2;

    boolean b1;

    public ExecuteCommand(Brick ev3, String cmd, int i1, int i2)
    {
        this.ev3 = ev3;
        this.command = cmd;

        this.i1 = i1;
        this.i2 = i2;

        this.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public ExecuteCommand(Brick ev3, String cmd, int i1, String p1)
    {
        this.ev3 = ev3;
        this.command = cmd;

        this.p1 = p1;
        this.i1 = i1;

        this.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public ExecuteCommand(Brick ev3, String cmd, String p1)
    {
        this.ev3 = ev3;
        this.command = cmd;
        this.p1 = p1;

        this.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public ExecuteCommand(Brick ev3, String cmd, String p1, int i1)
    {
        this.ev3 = ev3;
        this.command = cmd;
        this.p1 = p1;
        this.i1 = i1;

        this.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public ExecuteCommand(Brick ev3, String cmd, String p1, String p2)
    {
        this.ev3 = ev3;
        this.command = cmd;
        this.p1 = p1;
        this.p2 = p2;

        this.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public ExecuteCommand(Brick ev3, String cmd, String p1, String p2, int i1, int i2)
    {
        this.ev3 = ev3;
        this.command = cmd;

        this.p1 = p1;
        this.p2 = p2;

        this.i1 = i1;
        this.i2 = i2;

        this.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    protected Void doInBackground(Void... params)
    {
        System.out.println("Executing command: " + command);

        switch (command)
        {
            case "play-program": playProgram();
                break;
            case "play-audio": playAudio();
                break;
            case "set-led": setLedPattern();
                break;
            case "set-motor-power": startMotorAtPower();
                break;
            case "stop-direct-drive": stopDirectDrive();
                break;
            case "start-direct-drive": startDirectDrive();
                break;
        }

        return null;
    }

    public void playProgram()
    {
        try
        {
            System.out.println("Playing program: " + p1);
            ev3.directCommand.loadProgramFromFullPath(p1);
        }
        catch (Exception ex)
        {
            System.err.println("Failed to start program: " + ex.getMessage());
        }
    }

    public void playAudio()
    {
        try
        {
            ev3.directCommand.playAudioFile(i1, p1);
        }
        catch (Exception ex)
        {
            System.err.println("Failed to start audio: " + ex.getMessage());
        }
    }

    public void startMotorAtPower()
    {
        try
        {
            EV3Types.OutputPort port = EV3Types.OutputPort.All;

            switch (p1)
            {
                case "A": port = EV3Types.OutputPort.A;
                    break;
                case "B": port = EV3Types.OutputPort.B;
                    break;
                case "C": port = EV3Types.OutputPort.C;
                    break;
                case "D": port = EV3Types.OutputPort.D;
                    break;
                case "All": port = EV3Types.OutputPort.All;
                    break;
            }

            ev3.directCommand.startMotorAtPower(port, i1);
        }
        catch (Exception ex)
        {
            System.err.println("Failed to start audio: " + ex.getMessage());
        }
    }

    public void stopDirectDrive()
    {
        try
        {
            EV3Types.OutputPort portL = EV3Types.OutputPort.All;

            switch (p1)
            {
                case "A": portL = EV3Types.OutputPort.A;
                    break;
                case "B": portL = EV3Types.OutputPort.B;
                    break;
                case "C": portL = EV3Types.OutputPort.C;
                    break;
                case "D": portL = EV3Types.OutputPort.D;
                    break;
                case "All": portL = EV3Types.OutputPort.All;
                    break;
            }

            EV3Types.OutputPort portR = EV3Types.OutputPort.All;

            switch (p2)
            {
                case "A": portR = EV3Types.OutputPort.A;
                    break;
                case "B": portR = EV3Types.OutputPort.B;
                    break;
                case "C": portR = EV3Types.OutputPort.C;
                    break;
                case "D": portR = EV3Types.OutputPort.D;
                    break;
                case "All": portR = EV3Types.OutputPort.All;
                    break;
            }

            EV3Types.OutputPort[] ports = new EV3Types.OutputPort[]{ portL, portR };

            ev3.directCommand.stopMotor(ports, false);
        }
        catch (Exception ex)
        {
            System.err.println("Failed to start audio: " + ex.getMessage());
        }
    }

    public void startDirectDrive()
    {
        try
        {
            EV3Types.OutputPort portL = EV3Types.OutputPort.All;

            switch (p1)
            {
                case "A": portL = EV3Types.OutputPort.A;
                    break;
                case "B": portL = EV3Types.OutputPort.B;
                    break;
                case "C": portL = EV3Types.OutputPort.C;
                    break;
                case "D": portL = EV3Types.OutputPort.D;
                    break;
                case "All": portL = EV3Types.OutputPort.All;
                    break;
            }

            EV3Types.OutputPort portR = EV3Types.OutputPort.All;

            switch (p2)
            {
                case "A": portR = EV3Types.OutputPort.A;
                    break;
                case "B": portR = EV3Types.OutputPort.B;
                    break;
                case "C": portR = EV3Types.OutputPort.C;
                    break;
                case "D": portR = EV3Types.OutputPort.D;
                    break;
                case "All": portR = EV3Types.OutputPort.All;
                    break;
            }

            ev3.directCommand.startMotorAtPower(portL, i1);
            ev3.directCommand.startMotorAtPower(portR, i2);
        }
        catch (Exception ex)
        {
            System.err.println("Failed to start audio: " + ex.getMessage());
        }
    }

    public void setLedPattern()
    {
        try
        {
            EV3Types.LedPattern pattern = EV3Types.LedPattern.Black;

            switch (p1)
            {
                // LED off
                case "Black": pattern = EV3Types.LedPattern.Black;
                    break;
                case "Green": pattern = EV3Types.LedPattern.Green;
                    break;
                case "Red": pattern = EV3Types.LedPattern.Red;
                    break;
                case "Orange": pattern = EV3Types.LedPattern.Orange;
                    break;
                case "GreenFlash": pattern = EV3Types.LedPattern.GreenFlash;
                    break;
                case "RedFlash": pattern = EV3Types.LedPattern.RedFlash;
                    break;
                case "OrangeFlash": pattern = EV3Types.LedPattern.OrangeFlash;
                    break;
                case "GreenPulse": pattern = EV3Types.LedPattern.GreenPulse;
                    break;
                case "RedPulse": pattern = EV3Types.LedPattern.RedPulse;
                    break;
                case "OrangePulse": pattern = EV3Types.LedPattern.OrangePulse;
                    break;
            }

            ev3.directCommand.setLedPattern(pattern);
        }
        catch (Exception ex)
        {
            System.err.println("Failed to set pattern: " + ex.getMessage());
        }
    }
}
