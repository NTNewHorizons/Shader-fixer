
ROOT_DIR = os.path.join(os.path.dirname(os.path.abspath(__file__)), "..", "src", "main", "resources", "assets", "shaderfixer", "models")
REPO = os.path.join(ROOT_DIR, "repo.zip")
URL = f"https://github.com/JameH2/Hbm-s-Nuclear-Tech-GIT/archive/refs/heads/space-travel-twopointfive.zip"
EXTRACT = r"src/main/resources/assets/hbm/models"



# ----------------------------------------------------------------------------------------------------------------------------

import os
import zipfile
import urllib.request

try:
    os.makedirs(ROOT_DIR, exist_ok=True)
    print("0")
    urllib.request.urlretrieve(URL, REPO)
    print("1")
    with zipfile.ZipFile(REPO, "r") as zip:
         root = zip.namelist()[0].split("/")[0]
         fpath = f"{root}/{EXTRACT}/"
         toExtr = [f for f in zip.namelist() if f.startswith(fpath)]
         if not toExtr:
             print(f"NO '{EXTRACT}'")
         else:
            for file in toExtr:
                 if file.endswith('/'):
                     continue
                 rpath = file[len(fpath):]
                 tpath = os.path.join(ROOT_DIR, rpath)
                 os.makedirs(os.path.dirname(tpath), exist_ok=True)
                 with zip.open(file) as source, open(tpath, "wb") as t:
                     t.write(source.read())
    os.remove(REPO)
    print("\n === D - O - N - E === ")
    input("\n...")
except Exception as e:
    os.remove(REPO)
    print(f"FAIL {e}")
    input("\n...")
