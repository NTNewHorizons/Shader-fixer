
ROOT_DIR = os.path.join(os.path.dirname(os.path.abspath(__file__)), "..", "src", "main", "resources", "assets", "shaderfixer", "models")

aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa = (
    "737.obj",
    "turbofan_blades.obj",
    "b29.obj",
    "turbofan_derp.obj",
    "sat_foeq_burning.obj",
    "dyson_swarm_satellite.obj",
    "dornier.obj",
    "uvwave.obj",
    "railgun_main.obj",
    "nikonium.obj",
    "plane.obj",
    "liquidator.obj",
    ".obj", # When the zero is pl(s)us!😳
)



# ----------------------------------------------------------------------------------------------------------------------------

import os

delc = 0
for root, dirs, files in os.walk(ROOT_DIR):
    for file in files:
        if file.lower() in (target.lower() for target in aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa):
            file_path = os.path.join(root, file)
            try:
                os.remove(file_path)
                print(f"ELIMINATED : {file_path}")
                delc += 1
            except Exception as e:
                print(f"Failed to eliminate {file_path}: {e}")

print(f"\n KILL COUNT: {delc}")
print("\n === D - O - N - E === ")
input("\n...")
