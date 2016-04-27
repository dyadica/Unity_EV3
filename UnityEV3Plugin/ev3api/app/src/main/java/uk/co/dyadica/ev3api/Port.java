package uk.co.dyadica.ev3api;

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

public class Port
{
    public int index;
    public InputPort inputPort;
    public String name;
    public byte mode;
    public int type;

    public DeviceType deviceType;

    // Value properties

    public double SIValue;
    public double rawValue;
    public double percentValue;

    public Port port;

    // Port constructor

    public Port()
    {
        // Dummy debug constructor!
    }

    protected Port(String name, InputPort inputPort, int index)
    {
        this.name = name;
        this.inputPort = inputPort;
        this.index = index;

        port = this;
    }
}
