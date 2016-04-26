using UnityEngine;
using System.Collections;

// Bespoke using declarations

public class PlayProgram : MonoBehaviour
{
    public string FileName = "../prjs/BrkProg_SAVE/Demo.rpf";

    public void CallPlay()
    {
        EV3Manager.Instance.PlayProgram(FileName);
    }
}
