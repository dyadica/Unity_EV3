using UnityEngine;
using System.Collections;

using UnityEngine.UI;

public class PollNameUpdate : MonoBehaviour
{
    public InputField InputField;

    public void Start()
    {
        InputField.text = EV3Manager.Instance.EV3Name;
    }

    public void UpdateRobotName()
    {
        EV3Manager.Instance.EV3Name = InputField.text;
    }
}
