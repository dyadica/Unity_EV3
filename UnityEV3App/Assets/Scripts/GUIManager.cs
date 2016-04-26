using UnityEngine;
using System.Collections;

// Bespoke using declarations

using UnityEngine.UI;
using System.Collections.Generic;

public class GUIManager : MonoBehaviour
{
    #region Properties

    public static GUIManager Instance;

    public bool Debug = true;

    public Text DebugWindow;

    public PlayProgram[] ProgramList;
    public PlayAudio[] AudioList;

    public Toggle InvertMotors;

    public List<GameObject> Panels;

    public GameObject SplashPanel;
    public GameObject ConnectionPanel;
    public GameObject GameplayPanel;
    public GameObject SettingsPanel;
    public GameObject MenuPanel;
    public GameObject MotorsPanel;

    public 

    #endregion Properties

    #region Unity Loop

    void Awake()
    {
        Instance = this;
    }

	void Start ()
    {
        if (InvertMotors != null)
            InvertMotors.isOn = EV3Manager.Instance.InvertMotors;

        LoadConnectionScreen();
    }
	
	void Update () {
	
	}

    #endregion Unity Loop

    #region Program Updates

    public void UpdateProgram(int program, string fileName)
    {
        if (ProgramList == null || ProgramList[program] == null)
            return;

        ProgramList[program].FileName = fileName;
    }

    #endregion Program Updates

    #region Audio Updates

    public void UpdateAudio(int audio, int volume, string fileName)
    {
        if (AudioList == null || AudioList[audio] == null)
            return;

        AudioList[audio].Volume = volume;
        AudioList[audio].FileName = fileName;
    }

    public void UpdateAudio(int audio, string fileName)
    {
        if (AudioList == null || AudioList[audio] == null)
            return;

        AudioList[audio].FileName = fileName;
    }

    public void UpdateAudio(int audio, int volume)
    {
        if (AudioList == null || AudioList[audio] == null)
            return;

        AudioList[audio].Volume = volume;
    }

    #endregion Audio Updates

    public void UpdateRobotName(string name)
    {
        EV3Manager.Instance.EV3Name = name;
    }

    public void UpdateInvertMotors()
    {
        if (InvertMotors != null)
            EV3Manager.Instance.InvertMotors = InvertMotors.isOn;
    }

    public void LoadConnectionScreen()
    {
        StartCoroutine(loopAndLoad());
    }

    IEnumerator loopAndLoad()
    {
        yield return new WaitForSeconds(3);

        SplashPanel.SetActive(false);

        ConnectionPanel.SetActive(true);
    }

    public void LoadGameplayScreen()
    {
        foreach(GameObject panel in Panels)
        {
            panel.SetActive(false);
        }

        GameplayPanel.SetActive(true);
    }

    public void LoadMenuScreen()
    {
        foreach (GameObject panel in Panels)
        {
            panel.SetActive(false);
        }

        MenuPanel.SetActive(true);
    }

    public void LoadSettingsScreen()
    {
        foreach (GameObject panel in Panels)
        {
            panel.SetActive(false);
        }

        SettingsPanel.SetActive(true);
    }

    public void LoadMotorsScreen()
    {
        foreach (GameObject panel in Panels)
        {
            panel.SetActive(false);
        }

        MotorsPanel.SetActive(true);
    }

    public void QuitApp()
    {
        Application.Quit();
    }

}
