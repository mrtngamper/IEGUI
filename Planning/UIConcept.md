### Image Enhance GUI Concept
The goal of this project is to provide a GUI for different AI supported image enhance algorithms.
We would like to include the following options:

- [SwinIr](https://github.com/JingyunLiang/SwinIR) (Image Super-Resolution, JPEG Artifact Removal, Image Denoising)
- [DPIR](https://github.com/cszn/DPIR) (Image Super-Resolution, Image Deblurring)
- [Low-light-Image-Enhancement](https://github.com/pvnieo/Low-light-Image-Enhancement)
- [Auto White-Balance Correction](https://github.com/mahmoudnafifi/mixedillWB) 

The GUI will be written using JAVA11 and JAVAFX.
Hereafter, are listed some concept views of the different windows.
- #### Welcome View
    The Welcome View will be displayed on startup. It lets the user explore the different
Available algorithms and contains explanations, for what each module does. By default it will appear on avery startup.
This behavior can be disabled using a checkbox. In the Main View it will be possible to enable the Welcome View again.
The Main View will appear after clicking Ok.  

  
   <p align="center">
      <img src="Images/WelcomeView.png" width="350" title="hover text">
    </p>
  

- #### Main View
    The Main View will be used to select an image and then select an Image Enhance Option.
    
  
   <p align="center">
      <img src="Images/MainView.png" width="350" title="hover text">
    </p>

- #### Setting View
   

   <p align="center">
      <img src="Images/SettingView.png" width="350" title="hover text">
    </p>



