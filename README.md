# Improving Automated Patch Correctness Assessment by Designing LLM-Based Oracles

Replication package for the paper:

> **Improving Automated Patch Correctness Assessment by Designing LLM-Based Oracles**
> Accepted at ICST 2026

---

## Overview

This repository extends [FixCheck](https://github.com/facumolina/fixcheck) to study the impact of prompt engineering on LLM-based Automated Patch Correctness Assessment (APCA).

We identify two common failure modes in FixCheck's assertion generation pipeline and propose six prompt engineering strategies (H1–H6) to address them, without modifying FixCheck's underlying architecture.

| Failure Mode | Description |
|---|---|
| **Semantic Insufficiency** | Generated assertions fail to capture defect-triggering conditions |
| **Syntactic Invalidity** | Generated tests fail to compile due to scope violations or hallucinated APIs |

---

## Prompt Engineering Strategies

### Group 1: Improving Verification Depth

| Strategy | Description |
|---|---|
| H1 | Expansion of bug-related context |
| H2 | Inducement of step-wise reasoning |
| H3 | Mitigation of copying bias |
| H4 | Explicit alignment with APCA objectives |

### Group 2: Enhancing Code Validity

| Strategy | Description |
|---|---|
| H5 | Output format enforcement |
| H6 | Scope-aware constraints |

---

## Key Results

- For **GPT-based models**, role alignment with APCA objectives (H4) improves incorrect patch detection by up to **+7.5%**
- For **Llama 3.2 3B**, output format enforcement (H5) improves detection by up to **+19.9%**
- Prompt effectiveness is **model-dependent**, highlighting the importance of model-aware prompt design

---

## Setup

### 1. Build and Run Docker Container

```bash
git clone <this-repo>
cd <this-repo>
docker build -t fixcheck .
docker run -it fixcheck
```

### 2. Set Java 11 as Default (inside the container)

```bash
update-alternatives --config java
java -version
```

### 3. Set Up Defects4J

```bash
cd /home/ubuntu/defects4j
./init.sh
export PATH=$PATH:/home/ubuntu/defects4j/framework/bin
export DEFECT_REPAIRING_DATASET=/home/ubuntu/DefectRepairing
```

---

## Dataset

Experiments are conducted on a Defects4J-based dataset of APR-generated patches.

| | |
|---|---|
| Total patches | 139 |
| Correct | 30 |
| Incorrect | 109 |
| Projects | Chart, Lang, Math, Time |
| APR tools | JGenProg, JKali, Nopol, ACS, HDRepair |

---

## Running Experiments

### 1. Setup a Patch

```bash
python3 experiments/setup-all-defect-repairing.py 
```

### 2. Run FixCheck

```bash
python3 experiments/run-all-defect-repairing.py <MODEL-OPTION>
```

Results are stored in `fixcheck-output/defects-repairing`.

---

## Available Model Options

| Model | Options |
|---|---|
| GPT-based | `gpt-baseline`, `gpt-h1`, `gpt-h2a`, `gpt-h2b`, `gpt-h3a`, `gpt-h3b`, `gpt-h3c`, `gpt-h4`, `gpt-h5`, `gpt-h6` |
| Llama 3.2 3B | `llama-baseline`, `llama-h1`, `llama-h2a`, `llama-h2b`, `llama-h3a`, `llama-h3b`, `llama-h3c`, `llama-h4`, `llama-h5`, `llama-h6` |

---

## Result Analysis

```bash
# Reproduce Table II: strategy effectiveness
python3 experiments/results/table2_effectiveness.py <MODEL-OPTION>

# Reproduce Table III: assertion outcome distribution
python3 experiments/results/table3_outcomes.py <MODEL-OPTION>

# Reproduce Table IV: patch-level precision and recall
python3 experiments/results/table4_precision_recall.py <MODEL-OPTION>
```

---

## Contact

For questions or issues, please open a GitHub issue.