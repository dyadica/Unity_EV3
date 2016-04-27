// <copyright file="PollIRSensor.cs" company="dyadica.co.uk & isrg.org.uk">
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
// <email>info@dyadica.co.uk & info@isrg.org.uk</email>
// <date>27.04.2016</date>

using UnityEngine;
using System.Collections;

using UnityEngine.UI;
using System.Collections.Generic;

public class PollIRSensor : MonoBehaviour
{
    public Text IRText;

    void Start()
    {
        EV3Manager.InfraredSensorUpdateEvent +=
            EV3Manager_InfraredSensorUpdateEvent;
    }

    private void EV3Manager_InfraredSensorUpdateEvent(PortState state)
    {
        if (IRText != null)
            IRText.text = state.Raw.ToString();
    }

    void OnDestroy()
    {
        EV3Manager.InfraredSensorUpdateEvent -=
            EV3Manager_InfraredSensorUpdateEvent;
    }
}