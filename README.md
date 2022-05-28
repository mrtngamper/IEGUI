

# Image Enhance GUI (IEGUI)

By [Kilian Kier](https://github.com/kilian-kier), [Martin Gamper](https://github.com/mrtngamper), [Manuel Mitterrutzner](https://github.com/TubaComic), [Maximilian Perathoner](https://github.com/Maxnboy), [Michael Volgger](https://github.com/michaelV04)

TFO Fallmerayer

---
*Still under developement*! :warning:

---


### Contents
1. [About IEGUI](#about-iegui)
    - [Methods](#list-of-integrated-methods)
2. [Installation](#installation)
3. [Usage](#usage)
4. [Documentation](#documentation)

## About IEGUI

IEGUI is a JAVA GUI which allows the use of several artificial intelligences
to enhance images.
### List of integrated methods
These are the available algorithms that can be used.
<div class="heatMap">

| Name                                               | Tasks (usable in IEGUI)             | GitHub                                                                       | License                                                                                          |
|:---------------------------------------------------|:------------------------------------|:-----------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------|
| SwinIR                                             | - super-resolution<br/> - denoising | [link](https://github.com/JingyunLiang/SwinIR)                               | [Apache](./EnhanceMethod/SwinIR/LICENSE)                                                         |  
| NAFNet                                             | - denoising<br/> - deblurring       | [link](https://github.com/megvii-research/NAFNet)                            | [MIT](./EnhanceMethod/NAFNet/LICENSE)                                                            |   
| mixedillWB                                         | - white balance                     | [link](https://github.com/yanxiang-wang/mixedillWB)                          | [Attribution-NonCommercial-ShareAlike 4.0 International](./EnhanceMethod/mixedillWB2/LICENSE.md) |  
| Low-light-Image-Enhancement                        | - low light enhancement             | [link](https://github.com/pvnieo/Low-light-Image-Enhancement)                | [MIT](./EnhanceMethod/Low-light-Image-Enhancement/LICENSE)                                       | 
| GAN Prior Embedded Network                         | - face enhancement                  | [link](https://github.com/yangxy/GPEN)                                       | © Alibaba, 2021. For academic and non-commercial use only.                                       |
| Low-Light Image Enhancement with Normalizing Flow  | - low light enhancement             | [link](https://github.com/wyf0912/LLFlow)                                    | [Attribution-NonCommercial-ShareAlike 4.0 International](./EnhanceMethod/LLFlow/LICENSE)         |

</div>
All of these are copies from there respective GitHub 
repositories and may have the following changes:

- The arguments __*input*__ and __*output*__ folders may have been added to the parser.
- PyTorch may have been configured to use __*cpu*__ or __*cuda*__ depending on which of these is available.
- A few lines of code may have been added at the beginning of some files to check for missing dependencies.
- Test images may have been removed to save storage space.


## Installation

[//]: # (TODO Description)
TODO when finished


## Usage

[//]: # (TODO Description)
TODO when finished

## Documentation
[Documentation](Documentation/README.md)

