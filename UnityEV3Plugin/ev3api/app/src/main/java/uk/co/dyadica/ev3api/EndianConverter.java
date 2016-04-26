package uk.co.dyadica.ev3api;

/**
 * Created by dyadica.co.uk on 04/02/2016.
 */

public class EndianConverter
{
    /**
     * Convert the int number into its opposite endian representation
     * @param number number to convert
     * @return the int converted number
     */
    public static int swapToInt(int number)
    {
        return ((number >> 24) & 0xff)
                + (((number >> 16) & 0xff) << 8)
                + (((number >> 8) & 0xff) << 16)
                + (((number) & 0xff) << 24);
    }

    /**
     * Convert the int number into its opposite endian representation
     * @param number number to convert
     * @return the short converted number
     */
    public static short swapToShort(short number)
    {
        return (short) (((number >> 8) & 0xff) + (((number) & 0xff) << 8));
    }

    /**
     * Convert the four first bytes into their opposite int endian representation
     * @param bytes Byte which composed the number bytes[0] is the MSB
     * @return the int converted number
     */
    public static int swapToInt(byte[] bytes)
    {
        if(bytes.length < 4)
            return -1;

        return (bytes[0] & 0xff)
                + ((bytes[1] & 0xff) << 8)
                + ((bytes[2] & 0xff) << 16)
                + ((bytes[3] & 0xff) << 24);
    }

    /**
     * Convert the two first bytes into their opposite short endian representation
     * @param bytes Byte which composed the number bytes[0] is the MSB
     * @return the short converted number
     */
    public static short swapToShort(byte[] bytes)
    {
        if(bytes.length < 2)
            return -1;

        return (short)((bytes[0] & 0xff) + ((bytes[1] & 0xff) << 8));
    }

    /**
     * Get the current short representation of two bytes or more
     * @param bytes
     * @return
     */
    public static short getShort(byte[] bytes)
    {
        if(bytes.length < 2)
            return -1;

        return (short)((bytes[1] & 0xff) + ((bytes[0] & 0xff) << 8));
    }

    /**
     * Get the current int representation of the four bytes
     * @param bytes
     * @return
     */
    public static int getInt(byte[] bytes)
    {
        if(bytes.length < 2)
            return -1;

        return (bytes[3] & 0xff)
                + ((bytes[2] & 0xff) << 8)
                + ((bytes[1] & 0xff) << 16)
                + ((bytes[0] & 0xff) << 24);
    }
}