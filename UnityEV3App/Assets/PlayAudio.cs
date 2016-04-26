using UnityEngine;
using System.Collections;

// Bespoke using declarations

public class PlayAudio : MonoBehaviour
{
    public string FileName = "";
    public int Volume = 100;

    public void CallPlay()
    {
        EV3Manager.Instance.PlayAudio(Volume, FileName);
    }
}
