# Improving Automated Patch Correctness Assessment by Designing LLM-Based Oracles

> Artifact and replication package for the paper:
> *Improving Automated Patch Correctness Assessment by Designing LLM-Based Oracles*
> Accepted at *ICST 2026*

<br>

## Purpose

Automated Program Repair (APR) tools often generate plausible but incorrect patches. Automated Patch Correctness Assessment (APCA) aims to automatically determine whether a patch is correct.

This artifact extends [FixCheck](https://github.com/facumolina/fixcheck), a framework that assesses patch correctness by generating test assertions using LLMs. We identify two major failure modes in FixCheck's assertion generation pipeline and propose six prompt engineering strategies (H1–H6) to address them without modifying FixCheck's underlying architecture.

| Failure Mode | Description |
|---|---|
| **Semantic Insufficiency** | Generated assertions fail to capture defect-triggering conditions |
| **Syntactic Invalidity** | Generated tests fail to compile due to scope violations or hallucinated APIs |

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

This artifact is submitted for the following badges:

- **Artifact Available**: the artifact is archived on Zenodo with a permanent DOI, ensuring long-term public accessibility.
- **Artifact Reviewed**: the artifact is documented, consistent with the paper, complete with all implementation and analysis scripts, and exercisable via a provided Docker environment.

<br>

## Provenance

- Artifact repository: https://github.com/inyeongjang/LLM-Oracle
- Artifact DOI: https://doi.org/10.5281/zenodo.18900115
- Paper preprint: https://inyeongjang.github.io/papers/ICST-2026.pdf

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

The dataset is derived from the publicly available Defects4J benchmark. No personal data is involved. The dataset is freely available for research purposes. Storage requirement: approximately 10GB for the full dataset and experiment outputs.

<br>

## Artifact Structure

```
fixcheck/
├── src/
│   └── assertion/          # Prompt engineering implementations (H1–H6)
├── experiments/
│   ├── setup-all-defect-repairing.py
│   ├── run-all-defect-repairing.py
│   ├── collect_bug_info.py         # Collect Defects4J bug metadata for H1 context
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

- RAM: 16GB
- Disk: ~20GB free space
- GPU: not required

### Software Requirements

- Docker

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

```bash
curl -fsSL https://ollama.com/install.sh | sh
ollama pull llama3.2:3b
ollama serve
```

Verify installation:

```bash
curl http://localhost:11434/api/tags
```

<br>


## Usage

Run once before experiments to extract root cause and modified source info from Defects4J:
```bash
cd /home/ubuntu/fixcheck
python3 experiments/collect_bug_info.py
```

Output is saved to `/root/defects4j_bug_info/`. This step is only required when using H1-based model options (`gpt-h1`, `gpt-h2b`, `gpt-h3c`, `llama-h1`, `llama-h2b`, `llama-h3c`).

### Quick Test

To confirm the artifact is correctly set up:

```bash
python3 experiments/setup-defect-repairing.py Patch1
python3 experiments/run-fixcheck-defect-repairing.py Patch1 llama-baseline
```

Expected output:
```
Running FixCheck for subject: Patch1
assertion generation: llama-baseline
...
Moving all outputs to dir: fixcheck-output/defects-repairing/Patch1/llama-baseline
```

### Full Experiment

```bash
python3 experiments/setup-all-defect-repairing.py
python3 experiments/run-all-defect-repairing.py <MODEL-OPTION>
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

This artifact is released under the MIT License. See the LICENSE file for details.

<br>


## Contact

For questions or issues, please open a GitHub issue.
