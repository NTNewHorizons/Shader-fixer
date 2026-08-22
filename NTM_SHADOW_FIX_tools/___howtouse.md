
Automated scripts for various model files conversions (NTM_SHADOW_FIX)

RUN STRICTLY IN SEQUENCE

Some scripts contain variables that need to be changed:
    - BLENDER_PATH: path to the installed Blender

# 0_clean.py
Deletes models folder (src/main/resources/assets/shaderfixer/models)

# 1_model_fetch.py
Downloads and extracts models (.../hbm/models -> .../shaderfixer/models)

# 2_sanitizer.py
Deletes broken and not needed for this purpose files

# 3_tessellator3000UltraProMaxv3.py
Finds edges > EDGE_THRESHOLD (default 1.5) and subdivides them
(if no such edges are found, the file is deleted)

# 3_tessellator3000UltraProMaxv3_COLLADA.py
Same as tessellator3000UltraProMaxv3, but for collada

# 4_objOptimizer9000.py
Optimizes OBJ file's size: removes trailing zeros and reduces precision (default 4 decimals), also removes normals and comments

