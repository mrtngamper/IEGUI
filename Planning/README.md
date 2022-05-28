## Image Enhance GUI Concept

### Contents

1. [Idea](#Idea)
2. [General Information](#general-information)
    - [Basics](#basics)
    - [Save Data](#save-data)
    - [Files and Folders](#files-and-folders)
    - [Language](#language)
3. [GUI](#gui)
   - [Welcome View](#welcome-view)
   - [Main View](#main-view)
   - [Setting View](#setting-view)
   - [Loading View](#loading-view)
   - [Finished View](#finished-view)


### Idea

The goal of this project is to provide a GUI for different AI supported image enhance algorithms.
We would like to include the following options:

- [SwinIr](https://github.com/JingyunLiang/SwinIR) (Image Super-Resolution, JPEG Artifact Removal, Image Denoising)
- [DPIR](https://github.com/cszn/DPIR) (Image Super-Resolution, Image Deblurring)
- [Low-light-Image-Enhancement](https://github.com/pvnieo/Low-light-Image-Enhancement)
- [Auto White-Balance Correction](https://github.com/mahmoudnafifi/mixedillWB)

### General Information
  - #### Basics
    The Context contains all Data shared between the different Controllers. It includes a List
    containing instances of ImageEnhanceMethods, which are initialised on creation of the Context.
    The different will be able to use the information to display names, descriptions, examples and setting
    menus. It also contains the *start(String input, String output)* method which will be given the path of an input and
    output image. The start method can then use the input image and use the corresponding enhance method
    to compute an output image. While it is doing all of this a [Loading View](#loading-view) will be displayed. Upon completion a [Finished View](#finished-view)
    should be displayed.
    Code explanation can be found while looking at the **Java Docs**. **Therefore all code should be described!!**
  - #### Save Data
    All settings and language data will be saved in a *yml* or *yaml* file and loaded using *snakeyaml*. We use this method
    instead of java properties, because the yml format is much more flexible.
  - #### Files and Folders
    - #### EnhanceMethod (folder)
       This directory contains a directory foreach enhance method. Usually containing a clone of the
        corresponding github repository. It contains a custom config folder, which includes variable data,
        which can be loaded when an instance of ImageEnhanceMethod is created. These files are **language dependent**.
    - #### Language (folder)
        This directory contains all language files. Files contained in this directory should
      be selectable in the Main View. All the data is saved using the yml format.
  - #### Language
    A language Class contains static *load(String filename)* method which can load language files ([Language (folder)](#language-folder))
    from the disk, as well as other utilitarian methods which may be defined and documented during development. The load method
    loads yaml properties into a HashMap with String keys and StringProperty values. If a key does already exist, the existing StringProperty will change
    its value. A *get(String key)* can be called to get specific properties. **All texts in the finished UI should use bindings to specific elements of this
    language HashMap!!**
### GUI

The GUI will be written using JAVA11 and JAVAFX.
Hereafter, are listed some concept views of the different windows.
- #### Welcome View
    The Welcome View will be displayed on startup. It lets the user explore the different
Available algorithms and contains explanations, for what each module does. By default it will appear on every startup.
This behavior can be disabled using a checkbox. In the Main View it will be possible to enable the Welcome View again.
The Main View will appear after clicking Ok.  

  
   <p align="center">
      <img src="Images/WelcomeView.png" width="80%" title="hover text">
    </p>
  

- #### Main View
    The Main View will be used to select an image and then select an Image Enhance Option.
    At the top will be a Menu Bar with the Items File, Settings and Help. With these Options.
  - File
    - Open (file dialog)
    - Exit (quit program)
  - Settings
    - Language (select language)
    - Tutorial on Startup (enable or disable Welcome View)
  - Help
    - About <br /><br />
    
  Under the Menu Bar should be a miniature version of an opened window and the file name. 
 It should be possible to drag and drop other files into this area.
The most important element of this view will be a List View containing the names and example images of 
different enhance methods. On click a Setting View should appear on the bottom. <br /><br />
   <p align="center">
      <img src="Images/MainView.png" width="80%" title="hover text">
    </p>

- #### Setting View
   The Setting View will be used to display detailed 
information of a selected image enhance method. It displays the name a description and example Images of the Method.
It should also be possible to return to the Main View. The description and examples should be contained in a List View.
At the bottom of which a method specific field for Options should be displayed. It is contained in the ImageEnhanceMethod
Class and can be accessed via the *getSettingWindow()* method. At the bottom a start button should be displayed. On click the
start method of the corresponding ImageEnhanceMethod should be called, after displaying file dialog.



   <p align="center">
      <img src="Images/SettingView.png" width="80%" title="hover text">
    </p>
  
- #### Loading View 
  The Loading View will be displayed while calculating the output image. No progressbar will be displayed
but when the time comes the displayed content will be specified further.


- #### Finished View
    This view should contain a comparison input and the output image after the Loading View.
    It should contain a button to return to the Main View.