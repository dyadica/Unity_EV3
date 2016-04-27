package uk.co.dyadica.ev3api;

import uk.co.dyadica.ev3api.EV3Types.*;

/**
 * Created by dyadica.co.uk on 27/04/2016.
 * <p>
 * THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY
 * KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS FOR A
 * PARTICULAR PURPOSE.
 * <p/>
 */

public class EV3Button
{
    public int index;
    public String name;
    public BrickButton brickButton;

    public boolean pressed;

    public EV3Button(String name, BrickButton brickButton, int index)
    {
        this.name = name;
        this.index = index;
        this.brickButton = brickButton;
    }
}
