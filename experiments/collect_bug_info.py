import os
import subprocess
import re

PROJECTS = ["Chart", "Lang", "Math", "Time"]
OUTPUT_DIR = "/root/defects4j_bug_info"
os.makedirs(OUTPUT_DIR, exist_ok=True)

for project in PROJECTS:
    output_file = os.path.join(OUTPUT_DIR, f"{project.lower()}_bug_info.txt")
    print(f"Collecting bug info for {project}...")

    result = subprocess.run(
        ["defects4j", "bids", "-p", project],
        capture_output=True, text=True
    )
    bug_ids = result.stdout.strip().split("\n")

    with open(output_file, "w") as f:
        for bug_id in bug_ids:
            bug_id = bug_id.strip()
            if not bug_id:
                continue

            info_result = subprocess.run(
                ["defects4j", "info", "-p", project, "-b", bug_id],
                capture_output=True, text=True
            )
            output = info_result.stdout

            # Extract root cause block
            rc_match = re.search(
                r"Root cause in triggering tests:\n([\s\S]*?)(?=^-{5,})",
                output, re.MULTILINE
            )
            root_cause = rc_match.group(1).strip() if rc_match else ""

            # Extract modified sources block
            ms_match = re.search(
                r"List of modified sources:\n([\s\S]*?)(?=^-{5,}|\Z)",
                output, re.MULTILINE
            )
            modified_sources = ms_match.group(1).strip() if ms_match else ""

            f.write(f"Summary for Bug: {project}-{bug_id}\n")
            f.write("-" * 80 + "\n")
            f.write("Root cause in triggering tests:\n")
            for line in root_cause.splitlines():
                f.write(f"{line}\n")
            f.write("-" * 80 + "\n")
            f.write("List of modified sources:\n")
            for line in modified_sources.splitlines():
                f.write(f"{line}\n")
            f.write("\n")

    print(f"Saved: {output_file}")

print("Done.")