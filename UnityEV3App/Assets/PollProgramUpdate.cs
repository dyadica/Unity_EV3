using UnityEngine;
using UnityEngine.UI;

using System.Collections;

public class PollProgramUpdate : MonoBehaviour
{
    public string MyString;
    public int MyID;

    public InputField MyField;

    void Start()
    {
        MyField.text = PlayerPrefs.GetString("Prog" + MyID.ToString(), "");
    }

    public void UpdateProgram()
    {
        GUIManager.Instance.UpdateProgram(MyID, MyField.text);
        PlayerPrefs.SetString("Prog" + MyID.ToString(), MyField.text);
    }
    
}
