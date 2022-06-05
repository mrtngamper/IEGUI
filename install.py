import yaml
import shutil
import os
import argparse
import pathlib
import os
import zipfile

def normalize(raw_path):
    return pathlib.Path(raw_path).expanduser().resolve().__str__()


parser = argparse.ArgumentParser()

parser.add_argument("--zip", type=str, default=None)
parser.add_argument("--installation", type=str, default=None)
parser.add_argument("--model", type=str, default="model")
parser.add_argument("--source",type=str, default ="./")

args= parser.parse_args()

directory = normalize(args.model)
source = normalize(args.source)



tempdir = source+"/tempInstallationDir"
def main():
    global tempdir
    if(args.installation!=None):
        tempdir=args.installation

    if(args.zip != None or args.installation !=None ):

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
        files=os.listdir(source+"/Environments/")
        try:
            os.makedirs(tempdir+"/Environments")
        except:
            print("Environments dir exists")
        for fname in files:
            if(not os.path.isdir(source+"/Environments/"+fname)):
                shutil.copy2(os.path.join(source+"/Environments/",fname), tempdir+"/Environments/"+fname)
        print("copying models")
        copyModels(tempdir)

        if(args.zip!=None):
            print("creating zip archive")
            shutil.make_archive(os.path.splitext(args.zip)[0], 'zip', tempdir)
            if(args.installation!=None):
                shutil.rmtree(tempdir)

    else:
        copyModels(source)



def copyModels(destination):
    if (not os.path.isdir(directory)):
        print("Model directory not found: " + directory)
        exit(-1)

    try:
        with open(directory+'/location.yml', 'r') as file:
            yml = yaml.safe_load(file)
            for i in yml:
                try:
                    print("copying " + i + " to " + destination+"/"+yml[i]+"/")
                    shutil.copy(directory+"/"+i,destination+"/"+yml[i]+"/")
                except Exception as f:
                    print("copying: "+i)
                    print(i + ", " + yml[i]+": Could not be copied")
                    print(f)
    except Exception as e:
        print(e)


main()


