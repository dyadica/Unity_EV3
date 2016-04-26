using UnityEngine;
using System.Collections;

using UnityEngine.UI;

public class SetMotor : MonoBehaviour
{
    public Motor Motor;

    public OutputPort Port;

    public Text MyText;

	void Start ()
    {
        SetUpMotor();

        MyText.text = Port.ToString();
    }

	void Update ()
    {
	    
	}

    public void SetUpMotor()
    {
        string port = PlayerPrefs.GetString(Motor.ToString(), "A");

        switch (port)
        {
            case "A":
                Port = OutputPort.A;
                break;
            case "B":
                Port = OutputPort.B;
                break;
            case "C":
                Port = OutputPort.C;
                break;
            case"D":
                Port = OutputPort.D;
                break;
            case "All":
                Port = OutputPort.All;
                break;
        }
    }

    public void UpdateMotor()
    {
        switch(Port)
        {
            case OutputPort.A: Port = OutputPort.B;
                break;
            case OutputPort.B: Port = OutputPort.C;
                break;
            case OutputPort.C: Port = OutputPort.D;
                break;
            case OutputPort.D: Port = OutputPort.All;
                break;
            case OutputPort.All: Port = OutputPort.A;
                break;
        }

        MyText.text = Port.ToString();

        switch(Motor)
        {
            case Motor.Left: EV3Manager.Instance.LMotorPort = Port;
                break;
            case Motor.Right: EV3Manager.Instance.RMotorPort = Port;
                break;
            case Motor.Spare:
                EV3Manager.Instance.SMotorPort = Port;
                break;
        }

        PlayerPrefs.SetString(Motor.ToString(), Port.ToString());
    }
}
