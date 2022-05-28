# Documentation for Image Enhance GUI (IEGUI)

By [Kilian Kier](https://github.com/kilian-kier), [Martin Gamper](https://github.com/mrtngamper), [Manuel Mitterrutzner](https://github.com/TubaComic), [Maximilian Perathoner](https://github.com/Maxnboy), [Michael Volgger](https://github.com/michaelV04)

TFO Fallmerayer 2022

---

### Contents
1. [Participants](#participants)
2. [Requirements](#requirements)
   - [Enhance methods](#enhance-methods)
   - [Programming languages and frameworks](#programming-languages-and-frameworks)
   - [Development process](#development-process)
      - [Waterfall model](#waterfall-model)
3. [Design](#design)
4. [Daily Documentation](#daily-documentation)


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
document daily changes, use git as a version control system and use a test environment. The development was set to three weeks.

The team decided to create a GUI  which allows the user to open
image files, select an artificial-intelligence-supported enhance-algorithm 
and store the output into a new file.
These algorithms are mostly state-of-the-art algorithms in their respective field and released on GitHub.

### Enhance methods
The team chose to implement the following options

| Name                                               | Tasks (usable in IEGUI)             | GitHub                                                                       | License                                                                                          |
|:---------------------------------------------------|:------------------------------------|:-----------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------|
| SwinIR                                             | - super-resolution<br/> - denoising | [link](https://github.com/JingyunLiang/SwinIR)                               | [Apache](./EnhanceMethod/SwinIR/LICENSE)                                                         |  
| NAFNet                                             | - denoising<br/> - deblurring       | [link](https://github.com/megvii-research/NAFNet)                            | [MIT](./EnhanceMethod/NAFNet/LICENSE)                                                            |   
| mixedillWB                                         | - white balance                     | [link](https://github.com/yanxiang-wang/mixedillWB)                          | [Attribution-NonCommercial-ShareAlike 4.0 International](./EnhanceMethod/mixedillWB2/LICENSE.md) |  
| Low-light-Image-Enhancement                        | - low light enhancement             | [link](https://github.com/pvnieo/Low-light-Image-Enhancement)                | [MIT](./EnhanceMethod/Low-light-Image-Enhancement/LICENSE)                                       | 
| GAN Prior Embedded Network                         | - face enhancement                  | [link](https://github.com/yangxy/GPEN)                                       | © Alibaba, 2021. For academic and non-commercial use only.                                       |
| Low-Light Image Enhancement with Normalizing Flow  | - low light enhancement             | [link](https://github.com/wyf0912/LLFlow)                                    | [Attribution-NonCommercial-ShareAlike 4.0 International](./EnhanceMethod/LLFlow/LICENSE)         |

### Programming languages and frameworks

The team also chose to use these programming languages and frameworks. 

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

### Development process
As a development process the team chose to use the __*waterfall model*__. Since it was given very little
time a linear approach seemed appropriate.

#### Waterfall model
The basic waterfall model breaks down a development process into five phases with their respective results.

- System and software requirements ->  requirements document
- Desing -> software architecture
- Coding -> development, proving, and integration of software
- Testing -> discovery and debugging of defects
- Operations -> installation, migration and maintenance

The downside of this process is the lack of flexibility. As the requirements were very clear the team decided that an iterative
model would not be necessary.
Furthermore, the model was mainly used as a reference. The team decided to allow minimal changes of the design during the
development process if the whole team was informed.

## Design




## Daily Documentation


#### 09-05-22
- __Up to Commit: Update README.md__ <br> 
A GitHub repository was created and a basic README was uploaded. Collaborators
were added to the repository and each collaborator was prepared a branch.

#### 10-05-22
- Requirements were established and a basic Use Case Diagram was created.

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

#### 13-05-22
- __Commit: Concept__ <br>
The UIConcept.md was extended with detailed descriptions for the different views as well as the basic folder and class structure.

#### 17-05-22

#### 18-05-22

#### 19-05-22

#### 20-05-22

#### 24-05-22

#### 26-05-22





## Resume