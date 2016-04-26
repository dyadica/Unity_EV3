using UnityEngine;
using System.Collections;

public class PlayLed : MonoBehaviour
{
    public LedPattern Pattern;

    public void CallLedPattern()
    {
        EV3Manager.Instance.CallLedPattern(Pattern);
    }
}
