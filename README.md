# Improving Automated Patch Correctness Assessment by Designing LLM-Based Oracles

> Artifact and replication package for the paper:
> *Improving Automated Patch Correctness Assessment by Designing LLM-Based Oracles*
> Accepted at *ICST 2026*

Paper: https://inyeongjang.github.io/papers/ICST-2026.pdf

Artifact DOI: (to be added after Zenodo archival)

<br>

## Badges

This artifact is submitted for the following badges:

- Artifact Available
The artifact is publicly available through a permanent archival repository.

- Artifact Reusable
The artifact includes complete documentation, scripts, and a Docker environment to facilitate reuse by other researchers.

<br>

## Purpose

Automated Program Repair (APR) tools often generate plausible but incorrect patches. Automated Patch Correctness Assessment (APCA) aims to automatically determine whether a patch is correct.

This artifact extends [FixCheck](https://github.com/facumolina/fixcheck), a framework that assesses patch correctness by generating test assertions using LLMs. We identify two major failure modes in FixCheck's assertion generation pipeline and propose six prompt engineering strategies (H1–H6) to address them without modifying FixCheck's underlying architecture.

### Failure Modes

| Failure Mode | Description |
|---|---|
| **Semantic Insufficiency** | Generated assertions fail to capture defect-triggering conditions |
| **Syntactic Invalidity** | Generated tests fail to compile due to scope violations or hallucinated APIs |

### Prompt Engineering Strategies

**Group 1: Improving Verification Depth**

| Strategy | Description |
|---|---|
| H1 | Expansion of bug-related context |
| H2 | Inducement of step-wise reasoning |
| H3 | Mitigation of copying bias |
| H4 | Explicit alignment with APCA objectives |

**Group 2: Enhancing Code Validity**

| Strategy | Description |
|---|---|
| H5 | Output format enforcement |
| H6 | Scope-aware constraints |

<br>

## Provenance

- Artifact repository: https://github.com/inyeongjang/LLM-Oracle
- Paper preprint: https://inyeongjang.github.io/papers/ICST-2026.pdf

This repository contains the full implementation and scripts required to reproduce the experimental results presented in the paper.

<br>

## Data

Experiments are conducted on a Defects4J-based dataset of APR-generated patches.

| | |
|---|---|
| Total patches | 139 |
| Correct patches | 30 |
| Incorrect patches | 109 |
| Projects | Chart, Lang, Math, Time |
| APR tools | JGenProg, JKali, Nopol, ACS, HDRepair |

<br>

## Artifact Structure

```
fixcheck/
├── src/
│   └── assertion/          # Prompt engineering implementations (H1–H6)
├── experiments/
│   ├── setup-all-defect-repairing.py
│   ├── run-all-defect-repairing.py
│   └── results/
│       ├── table2_effectiveness.py
│       ├── table3_outcomes.py
│       └── table4_precision_recall.py
└── fixcheck-output/
    └── defects-repairing/  # Experiment outputs
```

<br>

## Setup

### Hardware Requirements

- CPU: 8 cores or higher
- RAM: 16GB
- Disk: ~20GB free space
- GPU: not required

### Software Requirements

- Docker
- Java 11
- Python 3
- Defects4J

### 1. Build and Run Docker Container

```bash
git clone https://github.com/inyeongjang/LLM-Oracle.git
cd LLM-Oracle
docker build -t fixcheck .
docker run -it fixcheck
```

### 2. Set Java 11 as Default (inside the container)

```bash
update-alternatives --config java
java -version
```

### 3. Configure Defects4J

```bash
cd /home/ubuntu/defects4j
./init.sh
export PATH=$PATH:/home/ubuntu/defects4j/framework/bin
export DEFECT_REPAIRING_DATASET=/home/ubuntu/DefectRepairing
```

<br>

## LLM Configuration

### GPT-based Models

```bash
export OPENAI_API_KEY=<your-api-key>

# Select model (default: gpt-4o-mini)
export OPENAI_MODEL=gpt-4o        # for GPT-4o
export OPENAI_MODEL=gpt-4o-mini   # for GPT-4o-mini
```

### Llama 3.2 3B (via Ollama)

Install Ollama:

```bash
curl -fsSL https://ollama.com/install.sh | sh
```

Pull and run the model:

```bash
ollama pull llama3.2:3b
ollama run llama3.2:3b
```

Verify installation:

```bash
curl http://localhost:11434/api/tags
```

<br>

## Running Experiments

### 1. Prepare Patches

```bash
python3 experiments/setup-all-defect-repairing.py
```

### 2. Run FixCheck

```bash
python3 experiments/run-all-defect-repairing.py <MODEL-OPTION>
```

**Example:**

```bash
python3 experiments/run-all-defect-repairing.py gpt-h4
```

Results are stored in `fixcheck-output/defects-repairing`.

<br>

## Available Model Options

| Model | Options |
|---|---|
| GPT-based | `gpt-baseline`, `gpt-h1`, `gpt-h2a`, `gpt-h2b`, `gpt-h3a`, `gpt-h3b`, `gpt-h3c`, `gpt-h4`, `gpt-h5`, `gpt-h6` |
| Llama 3.2 3B | `llama-baseline`, `llama-h1`, `llama-h2a`, `llama-h2b`, `llama-h3a`, `llama-h3b`, `llama-h3c`, `llama-h4`, `llama-h5`, `llama-h6` |

<br>

## Reproducing Paper Results

```bash
# Table II: strategy effectiveness across LLMs
python3 experiments/results/table2_effectiveness.py <MODEL-OPTION>

# Table III: assertion outcome distribution
python3 experiments/results/table3_outcomes.py <MODEL-OPTION>

# Table IV: patch-level precision and recall
python3 experiments/results/table4_precision_recall.py <MODEL-OPTION>
```

<br>

## License

This artifact is released under the MIT License.

See the LICENSE file for details.

<br>

## Contact

For questions or issues, please open a GitHub issue.
