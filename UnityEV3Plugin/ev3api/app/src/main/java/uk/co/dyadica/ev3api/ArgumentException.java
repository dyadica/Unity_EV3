package uk.co.dyadica.ev3api;

/**
 * Created by dyadica.co.uk on 04/02/2016.
 * <p>
 * THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY
 * KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS FOR A
 * PARTICULAR PURPOSE.
 * <p/>
 */

public class ArgumentException extends Exception
{
    public ArgumentException(String msg, String arg)
    {
        super("Argument \"" + arg + "\" is invalid : " + msg);
    }
}