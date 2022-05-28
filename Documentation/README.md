**# Documentation for Image Enhance GUI (IEGUI)

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

| Task              | Language          |
|:------------------|:------------------|
| GUI               | JavaFX            |  
| Backend           | Java              |   
| Image enhancement | Preferably python |
| Tests             | JUnit             |

### Development process
As a development process the team chose to use the __*waterfall model*__. Since it was given very little
time a linear approach seems appropriate.

#### Waterfall model
The basic waterfall model breaks a development process down into five phases with respective results.

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
