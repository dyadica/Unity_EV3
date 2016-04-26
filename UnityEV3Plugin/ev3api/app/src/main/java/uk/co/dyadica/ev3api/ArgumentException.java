package uk.co.dyadica.ev3api;

/**
 * Created by dyadica.co.uk on 04/02/2016.
 */

public class ArgumentException extends Exception
{
    public ArgumentException(String msg, String arg)
    {
        super("Argument \"" + arg + "\" is invalid : " + msg);
    }
}