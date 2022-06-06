import argparse
import io
import os

from pathlib import Path

import shutil
import zipfile
from sys import platform

import requests
import yaml

release_tag = "v0.1"

jarname = "iegui_win.jar"
if platform == "linux" or platform == "linux2":
    jarname= "iegui_lin.jar"
elif platform == "darwin":
    jarname= "iegui_mac.jar"
elif platform == "win32":
    jarname= "iegui_win.jar"

def update_progress(progress):
    print("\r [{0}] {1}%".format(str('#' * (int(progress * 100) // 2)).ljust(50), "{:.2f}".format(progress * 100)),
          end='')


def download():
    models = [ "EnhanceMethod.zip","Environments.zip","mixedillWB2_models.zip", "GPEN_models.zip", "LLFlow_models.zip", "NAFNet_models.zip",
              "SwinIR_models.zip"]


    if(source==False):
        download_and_extract_zip(jarname,False)

    for model in models:
            download_and_extract_zip(model ,True)




def download_and_extract_zip(model, unzip):
    url = "https://github.com/mrtngamper/IEGUI/releases/download/" + release_tag + "/" + model

    if(os.path.isdir(os.path.join(directory,model[:-4]))):
        print("Directory: "+model+" already exists")
        return
    if(os.path.isfile(os.path.join(directory,model))):
        print("File: "+model+" already exists")
        return

    print("Installing " + model[:-4] + " from " + url + "...")

    info = requests.head(url, allow_redirects=True)
    size = int(info.headers.get('content-length', 0))
    print('{}: {:.2f} MB'.format('FILE SIZE', int(size) / float(1 << 20)))

    with requests.get(url, stream=True) as r:
        r.raise_for_status()
        with open(model[:-4] + ".installing", "wb") as f:
            for chunk in r.iter_content(chunk_size=8192):
                f.write(chunk)
                update_progress(f.tell() / size)

    if(unzip):
        with open(model[:-4] + ".installing", "rb") as f:
            z = zipfile.ZipFile(io.BytesIO(f.read()))
            os.makedirs(directory, exist_ok=True)
            z.extractall(directory)
    else:
        os.makedirs(directory, exist_ok=True)
        shutil.copyfile(model[:-4]+".installing",os.path.join(directory,model))

    os.remove(model[:-4] + ".installing")

    print("\n")


def normalize(raw_path):
    return Path(raw_path).expanduser().resolve().__str__()


parser = argparse.ArgumentParser()


parser.add_argument("--zip", type=str, default=None, help = "Create zip after installation (Experimental)")
parser.add_argument("--installation", type=str, default=normalize("./temp"), help="Set installation directory")
parser.add_argument("--dl_cache", type=str, default="dl_cache", help = "Set download cache directory")
parser.add_argument("--source", action='store_true', help="Select if you want to compile from source. You need to be in the source directory")
parser.add_argument("--noclean",action='store_true', help="Select if you dont want to clean the cache (Debugging)")
parser.add_argument("--fromcache",action='store_true', help="Select if you want to install from cache directory (Experimental)")

args = parser.parse_args()

directory = normalize(args.dl_cache)
source = args.source

tempdir = normalize("/tempInstallationDir")


def main():
    global tempdir
    if args.installation is not None:
        tempdir = normalize(args.installation)

    if args.zip is not None or args.installation is not None:
        os.makedirs(tempdir, exist_ok=True)
        if(args.source==True):
            os.system("mvn install")
            try:
                os.makedirs(tempdir)
            except:
                print("Temp dir exists")

            print("copying jar")
            shutil.copyfile(normalize("./target/IEGUI-0.1-shaded.jar"),normalize(tempdir+"/IEGUI.jar"))
        else:
            print("copying jar")
            shutil.copyfile((normalize(directory+"/"+jarname)), normalize(tempdir + "/IEGUI.jar"))

        print("copying EnhanceMethod")
        shutil.copytree(directory + "/EnhanceMethod/", tempdir + "/EnhanceMethod/", dirs_exist_ok=True,
                        ignore=shutil.ignore_patterns('*.pth'))
        print("copying Environments")
        shutil.copytree(directory + "/Environments/", tempdir + "/Environments/", dirs_exist_ok=True,
                        ignore=shutil.ignore_patterns('*.pth'))
        print("copying models")
        copyModels(tempdir)

        if args.zip is not None:
            print("creating zip archive")
            shutil.make_archive(os.path.splitext(args.zip)[0], 'zip', tempdir)
            if args.installation is not None:
                shutil.rmtree(tempdir)
        if not args.noclean:
            shutil.rmtree(directory, ignore_errors = False)
    else:
        copyModels(normalize("./"))

def getTotalSizeOfModels():
    total_size = 0
    if os.path.isdir(directory):
        for dirpath, dirnames, filenames in os.walk(directory):
            for f in filenames:
                fp = os.path.join(dirpath, f)
                total_size += os.path.getsize(fp)

    return total_size


def copyModels(destination):
   # if getTotalSizeOfModels() < 7174020431:
         # TODO download only models that doesn't exist in the directory

    if not os.path.isdir(directory):
        print("Model directory not found: " + directory)
        exit(-1)

    r = requests.get("https://raw.githubusercontent.com/mrtngamper/IEGUI/main/location.yml")

    with open(directory + '/location.yml', 'wb') as file:
        file.write(r.content)

    try:
        with open(directory + '/location.yml', 'r') as file:
            yml = yaml.safe_load(file)
            for i in yml:
                try:
                    _i = i.split("/")
                    src = os.path.join(directory, *_i)
                    _yml_i = yml[i].split("/")
                    dst = os.path.join(destination, *_yml_i)
                    print("copying " + src + " to " + dst)
                    os.makedirs(str(dst), exist_ok=True)
                    shutil.copy(str(src), str(dst))
                except Exception as f:
                    print(i + ", " + Path(yml[i]) + ": Could not be copied")
                    print(f)
    except Exception as e:
        print(e)

if(not args.fromcache):
    download()
main()
