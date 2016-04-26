package uk.co.dyadica.ev3api;

/**
 * Created by dyadica.co.uk on 04/02/2016.
 */
public class EV3Types
{
    public enum ArgumentSize
    {
        Byte(0x81), // 1 byte
        Short(0x82), // 2 bytes
        Int(0x83), // 4 bytes
        String(0x84); // null-terminated string

        int val;

        private ArgumentSize(int val)
        {
            this.val = val;
        }

        public int get()
        {
            return val;
        }
    }

    public enum ReplyType
    {
        DirectReply(0x02),
        SystemReply(0x03),
        DirectReplyError(0x04),
        SystemReplyError(0x05);

        int val;

        private ReplyType(int val)
        {
            this.val = val;
        }

        public int get()
        {
            return val;
        }

        public void set(int val) {
            this.val = val;
        }

        public static boolean isMember(int val)
        {
            ReplyType[] aReplyType = ReplyType.values();

            for(ReplyType a : aReplyType) {
                if(a.val == val)
                    return true;
            }
            return false;
        }
    }

    public enum Opcode
    {
        UIRead_GetFirmware(0x810a),
        UIWrite_LED(0x821b),
        UIButton_Pressed(0x8309),
        UIDraw_Update(0x8400),
        UIDraw_Clean(0x8401),
        UIDraw_Pixel(0x8402),
        UIDraw_Line(0x8403),
        UIDraw_Circle(0x8404),
        UIDraw_Text(0x8405),
        UIDraw_FillRect(0x8409),
        UIDraw_Rect(0x840a),
        UIDraw_InverseRect(0x8410),
        UIDraw_SelectFont(0x8411),
        UIDraw_Topline(0x8412),
        UIDraw_FillWindow(0x8413),
        UIDraw_DotLine(0x8415),
        UIDraw_FillCircle(0x8418),
        UIDraw_BmpFile(0x841c),

        Sound_Break(0x9400),
        Sound_Tone(0x9401),
        Sound_Play(0x9402),
        Sound_Repeat(0x9403),
        Sound_Service(0x9404),

        InputDevice_GetTypeMode(0x9905),
        InputDevice_SetupI2C(0x9909),
        InputDevice_GetDeviceName(0x9915),
        InputDevice_GetModeName(0x9916),
        InputDevice_ReadyPct(0x991b),
        InputDevice_ReadyRaw(0x991c),
        InputDevice_ReadySI(0x991d),
        InputDevice_ClearAll(0x990a),
        InputDevice_ClearChanges(0x991a),
        InputRead(0x9a),
        InputReadExt(0x9e),
        InputReadSI(0x9d),

        OutputGetType(0xa0),
        OutputSetType(0xa1),
        OutputReset(0xa2),
        OutputStop(0xa3),
        OutputPower(0xa4),
        OutputSpeed(0xa5),
        OutputStart(0xa6),
        OutputPolarity(0xa7),
        OutputWaitReady(0xaa),
        OutputStepPower(0xac),
        OutputTimePower(0xad),
        OutputStepSpeed(0xae),
        OutputTimeSpeed(0xaf),
        OutputStepSync(0xb0),
        OutputTimeSync(0xb1),

        OutputClearCount(0xb2),
        OutputGetCount(0xb3),
        OutputPrgStop(0xb4),

        Tst(0xff),

        // Program codes

        ProgramStart(0x03),
        ProgramStop(0x02),

        File(0xc0),
        Array(0xc10),
        ArrayWrite(0xc2),
        ArrayRead(0xc3),
        ArrayAppend(0xc4),
        MemoryUsage(0xc5),
        FileName(0xc6);

        int val;

        private Opcode(int val)
        {
            this.val = val;
        }

        public int get()
        {
            return val;
        }
    }


    public enum SystemOpcode
    {
        BeginDownload(0x92),
        ContinueDownload(0x93),
        CloseFileHandle(0x98),
        CreateDirectory(0x9b),
        DeleteFile(0x9c);

        int val;

        private SystemOpcode(int val)
        {
            this.val = val;
        }

        public int get()
        {
            return val;
        }

        public void set(int val) {
            this.val = val;
        }

        public static boolean isMember(int val) {
            SystemOpcode[] aOpcode = SystemOpcode.values();
            for(SystemOpcode a : aOpcode) {
                if(a.val == val)
                    return true;
            }
            return false;
        }
    }

    public enum SystemReplyStatus
    {
        Success,
        UnknownHandle,
        HandleNotReady,
        CorruptFile,
        NoHandlesAvailable,
        NoPermission,
        IllegalPath,
        FileExists,
        EndOfFile,
        SizeError,
        UnknownError,
        IllegalFilename,
        IllegalConnection;

        int val;

        private SystemReplyStatus(){
            val = this.ordinal();
        }

        public int get() {
            return val;
        }

        public void set(int val) {
            this.val = val;
        }

        public static boolean isMember(int val)
        {
            SystemReplyStatus[] aReplyStatus = SystemReplyStatus.values();
            for(SystemReplyStatus a : aReplyStatus) {
                if(a.val == val)
                    return true;
            }
            return false;
        }
    }

    // The type of command being sent to the brick
    public enum CommandType
    {
        // Direct command with a reply expected
        DirectReply(0x00),
        // Direct command with no reply
        DirectNoReply(0x80),
        //  System command with a reply expected
        SystemReply(0x01),
        // System command with no reply
        SystemNoReply(0x81);

        int val;

        private CommandType(int val)
        {
            this.val = val;
        }

        public int get()
        {
            return val;
        }
    }

    // Format for sensor data.
    public enum Format
    {
        // Percentage
        Percent(0x10),
        // Raw
        Raw(0x11),
        // International System of Units
        SI(0x12);

        int val;

        private Format(int val)
        {
            this.val = val;
        }

        public int get()
        {
            return val;
        }
    }

    // Polarity/direction to turn the motor
    public enum Polarity
    {
        Backward(-1),
        Opposite(0),
        Forward(1);

        int val;

        private Polarity(int val)
        {
            this.val = val;
        }

        public int get()
        {
            return val;
        }
    }

    // Ports which can receive input data
    public enum InputPort
    {
        One(0x00),
        Two(0x01),
        Three(0x02),
        Four(0x03),
        A(0x10),
        B(0x11),
        C(0x12),
        D(0x13);

        int val;
        byte b;

        InputPort(int val)
        {
            this.val = val;
        }

        public int get()
        {
            return val;
        }

        public byte getByte(){
            return b;
        }

        public static InputPort getByByte(byte b)
        {
            for(InputPort ip:InputPort.values())
            {
                if(ip.getByte()==b)
                    return ip;
            }

            return null;
        }

    }

    // Ports which can send output
    public enum OutputPort
    {
        A(0x01),
        B(0x02),
        C(0x04),
        D(0x08),
        All(0x0f);

        int val;

        private OutputPort(int val)
        {
            this.val = val;
        }

        public int get()
        {
            return val;
        }

    }

    // List of devices which can be recognized as input or output devices
    public enum DeviceType
    {
        // NXT Touch sensor
        NxtTouch(1),
        // NXT Light sensor
        NxtLight(2),
        // NXT Sound sensor
        NxtSound(3),
        // NXT Color sensor
        NxtColor(4),
        // NXT Ultrasonic sensor
        NxtUltrasonic(5),
        //  NXT Temperature sensor
        NxtTemperature(6),
        // 2 motors

        // Large motor
        LMotor(7),
        // Medium motor
        MMotor(8),
        // Ev3 devices

        // EV3 Touch sensor
        Touch(16),
        // EV3 Color sensor
        Color(29),
        // EV3 Ultrasonic sensor
        Ultrasonic(30),
        // EV3 Gyroscope sensor
        Gyroscope(32),
        // EV3 IR sensor
        Infrared(33),
        // other

        // Sensor is initializing
        Initializing(0x7d),
        // Port is empty
        Empty(0x7e),
        // Sensor is plugged into a motor port), or vice-versa
        WrongPort(0x7f),
        // Unknown sensor/status
        Unknown(0xff);

        int val;

        private DeviceType(int val)
        {
            this.val = val;
        }

        public int get()
        {
            return val;
        }

    }

    // Buttons on the face of the EV3 brick
    public enum BrickButton
    {
        // No button
        None,
        // Up button
        Up,
        // Enter button
        Enter,
        // Down button
        Down,
        // Right button
        Right,
        // Left button
        Left,
        // Back button
        Back,
        // Any button
        Any;

        int val;

        private BrickButton(){
            val = this.ordinal();
        }

        public int get() {
            return val;
        }

        public void set(int val) {
            this.val = val;
        }
    }

    // Pattern to light up the EV3 brick's LED
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
        OrangePulse;

        int val;

        private LedPattern(){
            val = this.ordinal();
        }

        public int get() {
            return val;
        }

        public void set(int val) {
            this.val = val;
        }
    }

    // UI colors
    public enum Color
    {
        // Color of the background
        Background,
        // Color of the foreground
        Foreground;

        int val;

        private Color(){
            val = this.ordinal();
        }

        public int get() {
            return val;
        }

        public void set(int val) {
            this.val = val;
        }
    }

    // Font types for drawing text to the screen
    public enum FontType
    {
        // Small font
        Small,
        // Medium font
        Medium,
        // Large font
        Large;

        int val;

        private FontType(){
            val = this.ordinal();
        }

        public int get() {
            return val;
        }

        public void set(int val) {
            this.val = val;
        }
    }

    // NXT and EV3 Touch Sensor mode
    public enum TouchMode
    {
        // On when pressed, off when released
        Touch,
        // Running counter of number of presses
        Bumps;

        int val;

        private TouchMode(){
            val = this.ordinal();
        }

        public int get() {
            return val;
        }

        public void set(int val) {
            this.val = val;
        }
    }

    // NXT Light Sensor mode
    public enum NxtLightMode
    {
        // Amount of reflected light
        Reflect,
        // Amoutn of ambient light
        Ambient;

        int val;

        private NxtLightMode(){
            val = this.ordinal();
        }

        public int get() {
            return val;
        }

        public void set(int val) {
            this.val = val;
        }
    }

    // NXT Sound Sensor mode
    public enum NxtSoundMode
    {

        // Decibels
        Decibels,
        // Adjusted Decibels
        AdjustedDecibels;

        int val;

        private NxtSoundMode(){
            val = this.ordinal();
        }

        public int get() {
            return val;
        }

        public void set(int val) {
            this.val = val;
        }
    }

    // NXT Color Sensor mode
    public enum NxtColorMode
    {
        // Reflected color
        Reflective,
        // Ambient color
        Ambient,
        // Specific color
        Color,
        // Amount of green
        Green,
        // Amount of blue
        Blue,
        // Raw sensor value
        Raw;

        int val;

        private NxtColorMode(){
            val = this.ordinal();
        }

        public int get() {
            return val;
        }

        public void set(int val) {
            this.val = val;
        }
    }

    // NXT Ultrasonic Sensor mode
    public enum NxtUltrasonicMode
    {
        // Values in centimeter units
        Centimeters,
        // Values in inch units
        Inches;

        int val;

        private NxtUltrasonicMode(){
            val = this.ordinal();
        }

        public int get() {
            return val;
        }

        public void set(int val) {
            this.val = val;
        }
    }

    // NXT Temperature Sensor mode
    public enum NxtTemperatureMode
    {
        // Values in Celsius units
        Celsius,
        // Values in Fahrenheit units
        Fahrenheit;

        int val;

        private NxtTemperatureMode(){
            val = this.ordinal();
        }

        public int get() {
            return val;
        }

        public void set(int val) {
            this.val = val;
        }
    }

    // Motor mode
    public enum MotorMode
    {
        // Values in degrees
        Degrees,
        // Values in rotations
        Rotations,
        // Values in percentage
        Percent;

        int val;

        private MotorMode(){
            val = this.ordinal();
        }

        public int get() {
            return val;
        }

        public void set(int val) {
            this.val = val;
        }
    }

    // EV3 Color Sensor mode
    public enum ColorMode
    {
        // Reflected color
        Reflective,
        // Ambient color
        Ambient,
        // Specific color
        Color,
        // Reflected color raw value
        ReflectiveRaw,
        // Reflected color RGB value
        ReflectiveRgb,
        // Calibration
        Calibration; // TODO: ??

        int val;

        private ColorMode(){
            val = this.ordinal();
        }

        public int get() {
            return val;
        }

        public void set(int val) {
            this.val = val;
        }
    }

    // EV3 Ultrasonic Sensor mode
    public enum UltrasonicMode
    {
        // Values in centimeter units
        Centimeters,
        // Values in inch units
        Inches,
        // Listen mode
        Listen,
        // Unknown
        SiCentimeters,
        // Unknown
        SiInches,
        // Unknown
        DcCentimeters, // TODO: DC?

        // Unknown
        DcInches; // TODO: DC?

        int val;

        private UltrasonicMode(){
            val = this.ordinal();
        }

        public int get() {
            return val;
        }

        public void set(int val) {
            this.val = val;
        }
    }

    // EV3 Gyroscope Sensor mode
    public enum GyroscopeMode
    {
        // Angle
        Angle,
        // Rate of movement
        Rate,
        // Unknown
        Fas, // TOOD: ??
        // Unknown
        GandA, // TODO: ??
        // Calibrate
        Calibrate;

        int val;

        private GyroscopeMode(){
            val = this.ordinal();
        }

        public int get() {
            return val;
        }

        public void set(int val) {
            this.val = val;
        }
    }

    // EV3 Infrared Sensor mode
    public enum InfraredMode
    {
        // Proximity
        Proximity,
        // Seek
        Seek,
        // EV3 remote control
        Remote,
        // Unknown
        RemoteA, // TODO: ??

        // Unknown
        SAlt, // TODO: ??

        //  Calibrate
        Calibrate;

        int val;

        private InfraredMode(){
            val = this.ordinal();
        }

        public int get() {
            return val;
        }

        public void set(int val) {
            this.val = val;
        }
    }

    // Values returned by the color sensor
    public enum ColorSensorColor
    {
        Transparent,
        Black,
        Blue,
        Green,
        Yellow,
        Red,
        White,
        Brown;

        int val;

        private ColorSensorColor(){
            val = this.ordinal();
        }

        public int get() {
            return val;
        }

        public void set(int val) {
            this.val = val;
        }
    }
}
