// <copyright file="SetMotor.cs" company="dyadica.co.uk">
// Copyright (c) 2010, 2016 All Right Reserved, http://www.dyadica.co.uk

// This source is subject to the dyadica.co.uk Permissive License.
// Please see the http://www.dyadica.co.uk/permissive-license file for more information.
// All other rights reserved.

// THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY 
// KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS FOR A
// PARTICULAR PURPOSE.
//
// </copyright>

// <author>SJB</author>
// <email>info@dyadica.co.uk</email>
// <date>16.01.2016</date>

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
