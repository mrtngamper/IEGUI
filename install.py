import argparse
import io
import os

from pathlib import Path

import shutil
import zipfile

import requests
import yaml

release_tag = "v0.1"


def update_progress(progress):
    print("\r [{0}] {1}%".format(str('#' * (int(progress * 100) // 2)).ljust(50), "{:.2f}".format(progress * 100)),
          end='')


def download_models():
    models = ["mixedillWB2_models.zip", "GPEN_models.zip", "LLFlow_models.zip", "NAFNet_models.zip",
              "SwinIR_models.zip"]

    for model in models:
        url = "https://github.com/mrtngamper/IEGUI/releases/download/" + release_tag + "/" + model

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

        with open(model[:-4] + ".installing", "rb") as f:
            z = zipfile.ZipFile(io.BytesIO(f.read()))
            os.makedirs(directory, exist_ok=True)
            z.extractall(directory)

        os.remove(model[:-4] + ".installing")

        print("\n")


def normalize(raw_path):
    return Path(raw_path).expanduser().resolve().__str__()


parser = argparse.ArgumentParser()


parser.add_argument("--zip", type=str, default=None)

parser.add_argument("--installation", type=str, default=".\\temp")
parser.add_argument("--model", type=str, default="model")
parser.add_argument("--source", type=str, default="./")

args = parser.parse_args()

directory = normalize(args.model)
source = normalize(args.source)

tempdir = source + "/tempInstallationDir"


def main():
    global tempdir
    if args.installation is not None:
        tempdir = normalize(args.installation)

    if args.zip is not None or args.installation is not None:

        os.system("mvn install")
        try:
            os.makedirs(tempdir)
        except:
            print("Temp dir exists")
        print("copying jar")
        shutil.copyfile(source+"/target/IEGUI-0.1-shaded.jar",tempdir+"/iegui.jar")
        print("copying EnhanceMethod")
        shutil.copytree(source+"/EnhanceMethod/", tempdir+"/EnhanceMethod/", dirs_exist_ok=True, ignore=shutil.ignore_patterns('*.pth'))
        print("copying Settings")
        shutil.copytree(source+"/Settings/", tempdir+"/Settings/", dirs_exist_ok=True, ignore=shutil.ignore_patterns('*.pth'))
        print("copying Environments")
        files = os.listdir(Path(source + "/Environments/"))
        try:
            os.makedirs(Path(tempdir + "/Environments"))
        except:
            print("Environments dir exists")
        for fname in files:
            if not os.path.isdir(Path(source + "/Environments/" + fname)):
                shutil.copy2(os.path.join(Path(source + "/Environments/", fname)), Path(tempdir + "/Environments/" + fname))
        print("copying models")
        copyModels(tempdir)

        if args.zip is not None:
            print("creating zip archive")
            shutil.make_archive(os.path.splitext(args.zip)[0], 'zip', tempdir)
            if args.installation is not None:
                shutil.rmtree(tempdir)

    else:
        copyModels(source)

def getTotalSizeOfModels():
    total_size = 0
    if os.path.isdir(directory):
        for dirpath, dirnames, filenames in os.walk(directory):
            for f in filenames:
                fp = os.path.join(dirpath, f)
                total_size += os.path.getsize(fp)

    return total_size


def copyModels(destination):
    if getTotalSizeOfModels() < 7174020431:
        # TODO download only models that doesn't exist in the directory
        download_models()
        
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


main()
# download_models()
