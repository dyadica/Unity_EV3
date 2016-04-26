using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

class Types
{
}

public enum LedPattern
{
    // LED off
    Black,
    // Solid green
    Green,
    // Solid red
    Red,
    // Solid orange
    Orange,
    // Flashing green
    GreenFlash,
    // Flashing red
    RedFlash,
    // Flashing orange
    OrangeFlash,
    // Pulsing green
    GreenPulse,
    // Pulsing red
    RedPulse,
    // Pulsing orange
    OrangePulse
}

public enum OutputPort
{
    A,
    B,
    C,
    D,
    All
}

public enum InputPort
{
    One,
    Two,
    Three,
    Four,
    A,
    B,
    C,
    D,
    All
}

public enum Device
{
    // Large motor
    LMotor,
    // Medium motor
    MMotor,
    // EV3 Touch sensor
    Touch,
    // EV3 Color sensor
    Color,
    // EV3 Ultrasonic sensor
    Ultrasonic,
    // EV3 Gyroscope sensor
    Gyroscope,
    // EV3 IR sensor
    Infrared,
    // other
    // Sensor is initializing
    Initializing,
    // Port is empty
    Empty,
    // Sensor is plugged into a motor port), or vice-versa
    WrongPort,
    // Unknown sensor/status
    Unknown
}

public enum Motor
{
    Left,
    Right,
    Spare
}

public enum Direction
{
    Forward,
    Backwards,
    Left,
    Right
}

public class PortState
{
    public InputPort InputPort;
    public int Mode;
    public Device Device;
    public double Raw;
    public double SI;
    public double Percent;
}
