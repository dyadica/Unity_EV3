// <copyright file="PollAudioUpdate.cs" company="dyadica.co.uk">
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

public class PollAudioUpdate : MonoBehaviour {

    public string MyString;
    public int MyID;

    public InputField MyField;

    void Start()
    {
        MyField.text = PlayerPrefs.GetString("Audio" + MyID.ToString(), "");
    }

    public void UpdateAudio()
    {
        GUIManager.Instance.UpdateAudio(MyID, MyField.text);
        PlayerPrefs.SetString("Audio" + MyID.ToString(), MyField.text);
    }
}
