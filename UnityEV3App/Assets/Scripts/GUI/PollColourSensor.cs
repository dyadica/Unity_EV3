// <copyright file="GUIManager.cs" company="dyadica.co.uk & isrg.org.uk">
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

public class PollColourSensor : MonoBehaviour
{

    public Text ColourText;

    void Start()
    {
        EV3Manager.ColourSensorUpdateEvent +=
            EV3Manager_ColourSensorUpdateEvent;
    }

    private void EV3Manager_ColourSensorUpdateEvent(PortState state)
    {
        if (ColourText != null)
            ColourText.text = state.Raw.ToString();
    }

    void OnDestroy()
    {
        EV3Manager.ColourSensorUpdateEvent -=
            EV3Manager_ColourSensorUpdateEvent;
    }
}
