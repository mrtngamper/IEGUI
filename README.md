

# Image Enhance GUI (IEGUI)

By [Kilian Kier](https://github.com/kilian-kier), [Martin Gamper](https://github.com/mrtngamper), [Manuel Mitterrutzner](https://github.com/TubaComic), [Maximilian Perathoner](https://github.com/Maxnboy), [Michael Volgger](https://github.com/michaelV04)

TFO Fallmerayer



### Contents
1. [About IEGUI](#about-iegui)
    - [Methods](#list-of-integrated-methods)
2. [Requirements](#requirements)
3. [Installation](#installation)
4. [Build](#build)
5. [Documentation](#documentation)


## About IEGUI

IEGUI is a JAVA GUI which allows the use of several artificial intelligences
to enhance images.
### List of integrated methods
These are the available algorithms that can be used.
<div class="heatMap">

| Name                                               | Tasks (usable in IEGUI)                                                      | GitHub                                                                       | License                                                                                          |
|:---------------------------------------------------|:-----------------------------------------------------------------------------|:-----------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------|
| SwinIR                                             | - super-resolution<br/> - denoising                                          | [link](https://github.com/JingyunLiang/SwinIR)                               | [Apache](./EnhanceMethod/SwinIR/LICENSE)                                                         |  
| NAFNet                                             | - denoising<br/> - deblurring                                                | [link](https://github.com/megvii-research/NAFNet)                            | [MIT](./EnhanceMethod/NAFNet/LICENSE)                                                            |   
| mixedillWB                                         | - white balance                                                              | [link](https://github.com/yanxiang-wang/mixedillWB)                          | [Attribution-NonCommercial-ShareAlike 4.0 International](./EnhanceMethod/mixedillWB2/LICENSE.md) |  
| Low-light-Image-Enhancement                        | - low light enhancement                                                      | [link](https://github.com/pvnieo/Low-light-Image-Enhancement)                | [MIT](./EnhanceMethod/Low-light-Image-Enhancement/LICENSE)                                       | 
| GAN Prior Embedded Network                         | - face enhancement <br/> - face colorization <br/> - remove face inpainting  | [link](https://github.com/yangxy/GPEN)                                       | © Alibaba, 2021. For academic and non-commercial use only.                                       |
| Low-Light Image Enhancement with Normalizing Flow  | - low light enhancement                                                      | [link](https://github.com/wyf0912/LLFlow)                                    | [Attribution-NonCommercial-ShareAlike 4.0 International](./EnhanceMethod/LLFlow/LICENSE)         |

</div>
All of these are copies from their respective GitHub 
repositories and may have the following changes:

- The arguments __*input*__ and __*output*__ folders may have been added to the parser.
- PyTorch may have been configured to use __*cpu*__ or __*cuda*__ depending on which of these is available.
- A few lines of code may have been added at the beginning of some files to check for missing dependencies.
- Test images may have been removed to save storage space.


## Requirements

### Execution

#### Python3
```
pyyaml
requests
argparse
shutil
```

#### Java
```
Java JRE 17
```

### Build

```
Java JDK 17
Maven
```

## Installation
To install IEGUI
python3 installation 
and a java runtime environment are required.


Download install.py from the [release](https://github.com/mrtngamper/IEGUI/releases/tag/v0.1). And then run the following
it in a command line.

```
python3 install.py --installation [location] --dl_cache downloads  
```

This script will now download the executable and all necessary resources.

## Build
:warning: This process requires a java jre and maven

To build IEGUI you can clone the repository, and then excute the install.py script.

```
git clone https://github.com/mrtngamper/IEGUI
cd IEGUI
python3 install.py --installation [location] --dl_cache downloads --source
```

If you only want to build the jar file you can run

```
mvn install
```


in the source directory.
This will create an executable jar in the target directory, which is called IEGUI...shaded.jar
this file can then be copied into the source directory, but it may need additional resources.



## Documentation
[Documentation](Documentation/README.md)

