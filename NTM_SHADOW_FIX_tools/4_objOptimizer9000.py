
ROOT_DIR = os.path.join(os.path.dirname(os.path.abspath(__file__)), "..", "src", "main", "resources", "assets", "shaderfixer", "models")
PREC = 4
REM_NORMALS = True



# ----------------------------------------------------------------------------------------------------------------------------

import os
import re

def procNum(num):
    try:
        if '.' in num:
            decP = num.split('.')[1]
            if len(decP) >= PREC:
                r = f"{round(float(num), PREC):.{PREC}f}"
                t = r.rstrip('0').rstrip('.')
                return t if t else "0"
        return num
    except ValueError:
        return num

def optObj(path):
    with open(path, 'r', encoding='utf-8', errors='ignore') as f:
        lines = f.readlines()
    nl = []
    for l in lines:
        if l.startswith('# '):
            continue
        if REM_NORMALS and l.startswith('vn '):
            continue
        m = re.match(r'^(v|vt|vn)\s+(.*)', l)
        if m:
            prefix = m.group(1)
            parts = m.group(2).split()
            optParts = [procNum(p) for p in parts]
            nl.append(f"{prefix} {' '.join(optParts)}\n")
            continue
        if REM_NORMALS and l.startswith('f '):
            parts = l.split()
            f = parts[0]
            ne = []
            for e in parts[1:]:
                sp = e.split('/')
                if len(sp) == 3:
                    sp = sp[:2]
                net = '/'.join(sp).rstrip('/')
                ne.append(net)
            nl.append(f"{f} {' '.join(ne)}\n")
        else:
            nl.append(l)
    with open(path, 'w', encoding='utf-8') as f:
        f.writelines(nl)

for root, dirs, files in os.walk(ROOT_DIR):
    for file in files:
        if file.lower().endswith('.obj'):
            path = os.path.join(root, file)
            optObj(path)
print("\n === D - O - N - E === ")
input("\n...")
