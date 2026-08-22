
ROOT_DIR = os.path.join(os.path.dirname(os.path.abspath(__file__)), "..", "src", "main", "resources", "assets", "shaderfixer", "models")



# ----------------------------------------------------------------------------------------------------------------------------

import os


for root, dirs, files in os.walk(ROOT_DIR, topdown=False):
    for file in files:
        os.remove(os.path.join(root, file))
    for dir in dirs:
        os.rmdir(os.path.join(root, dir))

os.rmdir(ROOT_DIR)
print("\n === D - O - N - E === ")
input("\n...")
