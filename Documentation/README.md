# Documentation for Image Enhance GUI (IEGUI)

By [Kilian Kier](https://github.com/kilian-kier), [Martin Gamper](https://github.com/mrtngamper), [Manuel Mitterrutzner](https://github.com/TubaComic), [Maximilian Perathoner](https://github.com/Maxnboy), [Michael Volgger](https://github.com/michaelV04)

TFO Fallmerayer 2022

---

### Contents
1. [Participants](#participants)
2. [Requirements](#requirements)
   - [Enhance algorithms](#enhance-algorithms)
   - [Programming languages and frameworks](#programming-languages-and-frameworks)
   - [Development process](#development-process)
      - [Waterfall model](#waterfall-model)
   - [About testing](#about-testing)
   - [Code documentation](#code-documentation)
3. [Design](#design)
    - [Use Case](#use-case)
    - [UI Concept](#ui-concept)
      - [Welcome View](#welcome-view)
      - [Main View](#main-view)
      - [Setting View](#setting-view)
      - [Loading View](#loading-view)
      - [Finished View](#finished-view)
    - [Sequence](#sequence)
    - [Enhancement process](#enhancement-process) 
    - [Classes](#classes)
    - [File structure](#file-structure)
    - [Task distribution](#task-distribution)
4. [Daily documentation](#daily-documentation)
    - [Design phase](#design-phase)
    - [Code phase](#end-of-design-phase-and-start-of-coding-phase)
5. [Temporary resume](#temporary-resume)
    - [What worked](#what-worked)
    - [What did not](#what-did-not)
    - [Prospect](#prospect)
6. [Resume](#resume)


## Participants
This project was initiated by the information technology teachers of the TFO J. Ph. Fallmerayer which teach
the class 4BT.

| Tasks       | Team                                                                                    |
|:------------|:----------------------------------------------------------------------------------------|
| Development | Maximilian Perathoner, Michael Volgger, Manuel Mitterutzner, Martin Gamper, Kilian Kier |
| Stakeholder | IT teachers - TFO J. Ph. Fallmerayer                                                    |**


## Requirements
The team was given the task to develop a program using an object-oriented programming language
wich has a graphical user interface. The team should provide documentation, select a software development process, 
document daily changes, use git as a version control system and use a testing environment. The development was set to three weeks.

The team decided to create a GUI  which allows the user to open
image files, select an artificial-intelligence supported enhance-algorithm 
and store the output into a new file.
The selected algorithms are mostly state-of-the-art algorithms in their respective field and released on GitHub.
The team also chose to enable the program to switch language during runtime and include a tutorial.

### Enhance algorithms
The team chose to implement the following options

| Name                                               | Tasks (usable in IEGUI)             | GitHub                                                                       | License                                                                                           |
|:---------------------------------------------------|:------------------------------------|:-----------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------|
| SwinIR                                             | - super-resolution<br/> - denoising | [link](https://github.com/JingyunLiang/SwinIR)                               | [Apache](../EnhanceMethod/SwinIR/LICENSE)                                                         |  
| NAFNet                                             | - denoising<br/> - deblurring       | [link](https://github.com/megvii-research/NAFNet)                            | [MIT](../EnhanceMethod/NAFNet/LICENSE)                                                            |   
| mixedillWB                                         | - white balance                     | [link](https://github.com/yanxiang-wang/mixedillWB)                          | [Attribution-NonCommercial-ShareAlike 4.0 International](../EnhanceMethod/mixedillWB2/LICENSE.md) |  
| Low-light-Image-Enhancement                        | - low light enhancement             | [link](https://github.com/pvnieo/Low-light-Image-Enhancement)                | [MIT](../EnhanceMethod/Low-light-Image-Enhancement/LICENSE)                                       | 
| GAN Prior Embedded Network                         | - face enhancement                  | [link](https://github.com/yangxy/GPEN)                                       | © Alibaba, 2021. For academic and non-commercial use only.                                        |
| Low-Light Image Enhancement with Normalizing Flow  | - low light enhancement             | [link](https://github.com/wyf0912/LLFlow)                                    | [Attribution-NonCommercial-ShareAlike 4.0 International](../EnhanceMethod/LLFlow/LICENSE)         |

### Programming languages and frameworks

These programming languages and frameworks were chosen.

| Task              | Language/Frameworks/Tools |
|:------------------|:--------------------------|
| GUI               | JavaFX                    |  
| Backend           | Java                      |   
| Image enhancement | Preferably python         |
| Tests             | JUnit                     |
| Documentation     | Markdown + Java Docs      |
| Collaboration     | WhatsApp                  |
| Diagram creation  | StarUML                   |
| VCS               | Git on GitHub             |
| Save files        | SnakeYAML                 |

### Development process
For development, the team chose to use the __*waterfall model*__. Since it was given very little
time a linear approach seemed appropriate.

#### Waterfall model
The basic waterfall model breaks down a development process into five phases with their respective results.

- System and software requirements ->  requirements document
- Design -> software architecture
- Coding -> development, proving, and integration of software
- Testing -> discovery and debugging of defects
- Operations -> installation, migration and maintenance

The downside of this process is the lack of flexibility. As the requirements were very clear the team decided that an iterative
model would not be necessary.
Furthermore, the model was mainly used as a reference. The team decided to allow minimal changes of the design during the
development process if the whole team was informed.

### About testing
Test cases may be implemented using JUnit for critical processes, as needed.
As the team's development process is mostly linear, testing may also be
implemented to simplify later modifications to the project after the deadline.

### Code documentation
The team decided to document the code using Java Docs. A render of this documentation will
be added after finishing the project to help future developers.

## Design


### Use Case
During the design phase the team defined a basic use case.
 <p align="center">
      <img src="Images/useCase.png" width="80%" title="hover text">
    </p>
A user should be able to do the following actions:

- Enable the help on startup
- Close the program
- Change the language
- Select an image file, or drag and drop a file into the program
- Select an enhance method
- Start the enhancement

The UI, on the other hand should enable these interactions.

A logic thread should only be active once, during the enhancement process.

### UI Concept
In addition to the use case, an initial design layout of the different views, the user interface 
should provide, was proposed.

- #### Welcome View
  The Welcome View should be displayed on startup. It should provide a tutorial on how to use
 the program. Additionally, there should be a checkmark to disable the startup behaviour. <br /><br />

   <p align="center">
      <img src="Images/WelcomeView.png" width="50%" title="hover text">
    </p>


- #### Main View
  The Main View should provide the option to select an image and then select an image-enhance-option.
  At the top a Menu Bar should be displayed, containing these options:
    - File
        - Open (file dialog)
        - Exit (quit program)
    - Settings
        - Language (select language)
        - Tutorial on Startup (enable or disable Welcome View)
    - Help
        - About <br /><br />

  Under the Menu Bar a miniature version of an opened image should be displayed beside its file name.
  It should be possible to drag and drop other files into this area.
  The most important element of this view will be a List View containing the names and example images of
  different enhance methods. On-click a Setting View should replace the List View. <br /><br />
   <p align="center">
      <img src="Images/MainView.png" width="80%" title="hover text">
    </p>
  
- #### Setting View
  The Setting View should provide detailed
  information about a selected image-enhance method and the option to return to the Main View. 
  The methods description and examples should be contained in a List View.
  At the bottom or the top of this List View a method specific field for options should be displayed.
  At the bottom of the whole View a button should be provided to start the enhancement process.<br /><br />
   <p align="center">
      <img src="Images/SettingView.png" width="80%" title="hover text">
    </p>

- #### Loading View
  The Loading View should be displayed while calculating the output image. It should first and foremost provide
a graphical indication, that the enhancement process is running. The implementation is free to the responsible
team member.

- #### Finished View
  This view should contain a comparison between the input and the output image after the enhancement process has been finished
  It should contain a button to return to the Main View.

These are guidelines which may help team members responsible for a specific View during the implementation process, but
they are not obligatory and the design can be changed, as long as the same functionality is provided.


### Sequence
The interactions between the different Views should be as follows.
  <p align="center">
      <img src="Images/sequence.png" width="80%" title="hover text">
    </p>

### Enhancement process
To enhance an image after a user selected a method, java will need to interact with different python projects.
Because of that, an environment has to be set up before execution.
 <p align="center">
      <img src="Images/activity.png" width="80%" title="hover text">
    </p>

### Classes
The following classes and packages were defined. The main package should be the util package. This package should contain
the language implementation as well as the Context, which is accessible to every JavaFX controller
and contains all the data, which can be stored and loaded into the settings.yml file.

<p align="center">
      <img src="Images/util.png" width="80%" title="hover text">
    </p>

The AI package should contain the abstract class ImageEnhanceMethod which loads data from config files. 
Every enhancement-method should have its own class which extends ImageEnhanceMethod and implements the getCMD() method.

<p align="center">
      <img src="Images/ai.png" width="80%" title="hover text">
    </p>

Additionally, some classes may be added to the CustomNodes or Exceptions package when needed by an implementation.
<p align="center">
      <img src="Images/extra.png" width="80%" title="hover text">
    </p>

### File structure
Also, a basic file structure was specified. The enhancement-methods should be in its corresponding folder and contain
a config file. This file should contain, the values of the attributes of ImageEnhanceMethod, including
the environment in which this method should be executed. The environment requirements should be 
contained in the Environments folder. The environments should be created in the same folder.
Other important folders are the Settings folder and the Language folder. Both should contain a yml
file which contain a map of the stored data. The packages found in the iegui subdirectory of main have already been explained 
above. JavaFX fxml files, as well as css files should be in the iegui subdirectory of resources.
```
├── Documentation
│   ├── Images
│   └── README.md
├── EnhanceMethod
│   └── ExampleMethod
│       └── Config
│           └── en.yml
├── Environments
│   └── environment_file.txt
├── Language
│   └── en.yml
├── README.md
├── Settings
│   └── settings.yml
└── src
    ├── java
    └── src
        └── main
            ├── java
            │   └── com
            │       └── example
            │           └── iegui
            │               ├── AI
            │               ├── controller
            │               ├── CustomNodes
            │               ├── Exceptions
            │               └── util
            └── resources
                └── com
                    └── example
                        └── iegui
                            └── views.css
```

### Task distribution
After the design phase the tasks each team member should work on were specified.

| Task/Implementation      | Team member                                 |
|:-------------------------|:--------------------------------------------|
| Main View                | Maximilian Perathoner, Manuel Mitterrutzner |  
| Setting View             | Maximilian Perathoner, Manuel Mitterrutzner |   
| Loading View             | Martin Gamper                               |
| Finished View            | Michael Volgger                             |
| Welcome View             | Michael Volgger                             |
| Java- Python integration | Martin Gamper                               |
| AI research / Marketing  | Kilian Kier                                 |
| Installer                | Kilian Kier                                 |
| Styling                  | Michael Volgger                             |


## Daily documentation

### Design phase
#### 09-05-22
- __Up to Commit: Update README.md__ <br> 
A GitHub repository was created and a basic README was uploaded. Collaborators
were added to the repository and every collaborator was given a branch.

#### 10-05-22
- The planning phase began, the requirements were established and a basic use-case-diagram was created.

#### 11-05-22
- __Commit: JavaFX Setup__ <br>
A basic JavaFX project folder was uploaded to the repository

#### 12-05-22
- Basic classes were defined and a basic class diagram was created

- __Up to Commit: Further Preparation and Experimentation__ <br>
An updated JavaFX project folder was uploaded with a basic class and
folder design including JavaDoc documentation. The SwinIR project folder was embedded into
the IEGUI folder structure. A settings file was added to the config folder in SwinIR as well as an implementation of
a reader for the settings as a proof of concept to test the yaml file format.

- __Commit: Concept__ <br>
The UIConcept.md file was added to the repository. It contained basic information about the UI design with reference images.


### End of design phase and start of coding phase

#### 13-05-22
- During that day the coding phase began after the basic inner workings of IEGUI were defined. Beforehand the tasks for each developer were specified.
- __Commit: Concept__ <br>
The UIConcept.md was extended with detailed descriptions of the different views as well as a basic folder and class structure.
- __Commit: Language__ <br>
The language backend was defined in the UIConcept.md file
- __Commit: Language backend__ <br>
The language backend was implemented.
- __Commit: Welcome view added__ <br>
The fxml files and controllers were added and updated.

#### 17-05-22
- __Commit: Settings__ <br>
A Settings implementation was made. It provided the option to store and load data contained
in an instance of context.
- __Commit: WelcomeView + MainView merged__ <br>
The two views and their affiliated controllers have been merged. The main application has also been adapted.
- Commit: WelcomeView updated__ <br>
A pagination and a button were added and implemented. 

#### 18-05-22
- __Commit: Fix WelcomeView__ <br>
Fixed bug which resulted in a NullPointerException

#### 19-05-22
- __Commit: MainView upload picture feature__ <br>
A button to choose a picture to process with an additional drag and drop option and picture display was implemented.

#### 20-05-22
- __Commit: Create and setup python environment__ <br>
Code to set-up an environment for SwinIR was added to ImageEnhanceMethod.java as well as SwinIR.java
- __Commit: Fix: Drag and Drop__ <br>
Changed the feature that it behaved exactly like the button.

#### 24-05-22
- __Commits by mrtngmaper up to Low-light enhancement, SwinIR, Auto-white-balance working__ <br>
Additional enhance-methods were added to the project, a standard implementation of start() was moved
from SwinIR to ImageEnhanceMethod, as the other enhance-methods could use the same backend, while using
different execution commands, which could be gathered via the getCMD() method, every ImageEnhanceMethod must implement.
- __WelcomeView Visual Update__ <br>
WelcomeView got a visual update 
- __FinishedView added__ <br>
Finished View was added and implemented.

#### 25-05-22
- __Commit: Method display__ <br>
First version of the display of all available methods with title and short description was added.

#### 26-05-22
- __Commit: Visual Update__ <br>
.css file was added to all fxml files.


#### 27-05-22
- __Commit: Two additional methods__ <br>
  Two additional enhance-methods were added to the project (LLFlow, NAFNet). All model files were removed from the project and added to Google Drive,
  because GitHub only allows file sizes up to 100mb.

#### 28-05-22
- This documentation was added to the project, the Concept.md became obsolete.



## Temporary resume 
#### What worked
Up until June 28th, one week from the deadline, the development team was able
to create basic implementations of the different views wich were previously specified.
The WelcomeView was implemented, it was possible to select or drag & drop images 
into the MainView. It was also possible to run enhance images using a simple interface in java.

#### What did not
It was still not possible to select an image-enhance-method from the GUI and descriptions
for the different algorithms were still missing, as well as settings nodes for the methods.
The overall design of the different views could still be improved.
Languages other than english were still not available.

#### Prospect
It seemed to be possible to finish the project during the following week, even though
there might not be left much time. The right development process was chosen, as only very few
design specifications had to be changed, the most sever being the need for an installer, 
because of the size of some PyTorch models and the GitHub file size limitation.

## Resume
This section will be added in after release :warning:
