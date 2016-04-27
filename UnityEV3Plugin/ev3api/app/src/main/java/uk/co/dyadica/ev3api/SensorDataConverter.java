package uk.co.dyadica.ev3api;

import java.util.Arrays;

import uk.co.dyadica.ev3api.EV3Types.*;

/**
 * Created by dyadica.co.uk on 04/02/2016.
 * <p>
 * THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY
 * KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS FOR A
 * PARTICULAR PURPOSE.
 * <p/>
 */

public class SensorDataConverter
{
    /**
     * Return the int value of the n bytes between firstByteIndex and lastByteIndex (inclusive)
     * These bytes must be in little endian representation
     * @param data
     * @param firstByteIndex
     * @param lastByteIndex
     * @return
     */
    public static int toInt(byte[] data, int firstByteIndex, int lastByteIndex)
    {
        if(data == null)
            return -1;

        lastByteIndex++;

        byte[] bytes = Arrays.copyOfRange(data, firstByteIndex, lastByteIndex);

        if(bytes.length == 1)
            return bytes[0] & 0xff;
        if(bytes.length == 2)
            return EndianConverter.swapToShort(bytes);
        else
            return EndianConverter.swapToInt(bytes);
    }

    public static int[] areButtonsPressed(byte[] data)
    {
        // debug(data);

        int[] dummyData = new int[]{0,0,0,0,0,0,0};

        if(data == null || data.length != 7)
            return dummyData;

        return new int[]{data[0],data[1],data[2],data[3],data[4],data[5],data[6]};
    }

    public static Port getPortState(byte[] data, int index, InputPort ip)
    {
        Port p = new Port();

        p.index = index;
        p.type = data[0];
        p.mode = data[1];
        p.deviceType = getDeviceType(data, 0);
        p.rawValue = toInt(data, 2, 3);
        p.SIValue = toInt(data, 6, 7);
        p.percentValue = data[10];
        p.inputPort = ip;
        p.name = ip.name();

        return p;
    }

    public static boolean isSingleButtonPressed(byte[] data)
    {
        if(data == null)
            return false;

        if(((int) data[0]) == 1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public static int[] getTypeMode(byte[] data, int firstByteIndex)
    {
        int[] tm = new int[2];

        tm[0] = data[firstByteIndex];
        tm[1] = data[firstByteIndex+1];

        return tm;
    }

    public static byte getDeviceMode(byte[] data, int firstByteIndex)
    {
        int type =  data[firstByteIndex];
        int modeI = data[firstByteIndex+1];

        byte modeB = data[firstByteIndex+1];

        return modeB;
    }

    public static DeviceType getDeviceType(byte[] data, int firstByteIndex)
    {
        if(data == null)
            return DeviceType.Unknown;

        int type =  data[firstByteIndex];

        // Sensors

        if(type == DeviceType.Infrared.get())
            return DeviceType.Infrared;

        if(type == DeviceType.Touch.get())
            return DeviceType.Touch;

        if(type == DeviceType.Color.get())
            return DeviceType.Color;

        if(type == DeviceType.Ultrasonic.get())
            return DeviceType.Ultrasonic;

        if(type == DeviceType.Gyroscope.get())
            return DeviceType.Gyroscope;

        // Motors

        if(type == DeviceType.MMotor.get())
            return DeviceType.MMotor;

        if(type == DeviceType.LMotor.get())
            return DeviceType.LMotor;

        // States

        if(type == DeviceType.Empty.get())
            return DeviceType.Empty;

        if(type == DeviceType.WrongPort.get())
            return DeviceType.WrongPort;

        if(type == DeviceType.Unknown.get())
            return DeviceType.Unknown;

        // Return Unknown as default

        return DeviceType.Unknown;

    }

    /**
     * Get the right value depending on the type of the sensor.
     * The first and second byte must contain the type and the mode given by the command getTypeMode
     *
     * @param data
     * @param firstByteIndex
     * @param lastByteIndex
     * @return
     */
    public static double getValue(byte[] data, int firstByteIndex, int lastByteIndex)
    {
        if(data == null)
            return -1;

        int type = data[firstByteIndex];
        int mode = data[firstByteIndex+1];

        firstByteIndex += 2;

        int value = toInt(data, firstByteIndex, lastByteIndex);

        if(type == DeviceType.Touch.get())
        {
            return value;
        }
        else if(type == DeviceType.Infrared.get())
        {
            return value;
        }
        else if(type == DeviceType.Color.get())
        {
            return value;
        }
        else if(type == DeviceType.Ultrasonic.get())
        {
            return value;
        }
        else if(type == DeviceType.Gyroscope.get())
        {
            return value;
        }
        else if(type == DeviceType.Empty.get())
        {
            return -1;
        }
        else if(type == DeviceType.WrongPort.get())
        {
            return -1;
        }
        else if(type == DeviceType.Unknown.get())
        {
            return -1;
        }
        else
        {
            return value;
        }
    }

    private static void debug(byte[] data)
    {
        if(data == null)
            return;

        System.out.print("Button Data" + " : ");
        for (byte b : data)
            System.out.print((b & 0xff) + " ");
        System.out.println();
    }
}
