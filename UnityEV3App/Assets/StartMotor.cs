using UnityEngine;
using System.Collections;

using UnityEngine.EventSystems;
using System;

public class StartMotor : MonoBehaviour, IPointerDownHandler, IPointerUpHandler
{
    public bool triggered = false;

    public Direction direction;

    public void OnPointerDown(PointerEventData eventData)
    {
        if (triggered == false)
        {
            triggered = true;
            EV3Manager.Instance.StartDriveMotors(direction);
        }
    }

    public void OnPointerUp(PointerEventData eventData)
    {
        triggered = false;
        EV3Manager.Instance.StopDriveMotors();
    }

    // Use this for initialization
    void Start ()
    {
	
	}
	
	// Update is called once per frame
	void Update ()
    {
	
	}
}
