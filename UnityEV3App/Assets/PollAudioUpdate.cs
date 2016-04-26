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
