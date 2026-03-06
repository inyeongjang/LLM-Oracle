# Improving Automated Patch Correctness Assessment by Designing LLM-Based Oracles
> Artifact and replication package for the paper:
<br> *Improving Automated Patch Correctness Assessment by Designing LLM-Based Oracles* (Accepted at *ICST 2026*)

<br>

### Overview
Automated Program Repair (APR) tools often generate plausible patches. Automated Patch Correctness Assessment (APCA) aims to automatically determine whether a patch is correct.

This repository extends [FixCheck](https://github.com/facumolina/fixcheck), which assesses patch correctness by generating test assertions using LLMs. We identify two major failure modes in FixCheck's assertion generation pipeline and propose six prompt engineering strategies (H1–H6) to address them, without modifying FixCheck's underlying architecture.

<br>

### Failure Mode
| Failure Mode | Description |
|---|---|
| **Semantic Insufficiency** | Generated assertions fail to capture defect-triggering conditions |
| **Syntactic Invalidity** | Generated tests fail to compile due to scope violations or hallucinated APIs |

<br>

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

### Key Findings

- For **GPT-based models**, aligning prompts with APCA objectives (H4) improves incorrect patch detection by up to **+7.5%**
- For **Llama 3.2 3B**, output format enforcement (H5) improves detection by up to **+19.9%**
- Prompt effectiveness is **model-dependent**, highlighting the need for model-aware prompt design
<br>

### Artifact Structure

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

### Setup

**1. Build and Run Docker Container**

```bash
git clone https://github.com/inyeongjang/LLM-Oracle.git
cd LLM-Oracle
docker build -t fixcheck .
docker run -it fixcheck
```

**2. Set Up Defects4J**

```bash
cd /home/ubuntu/defects4j
./init.sh
export PATH=$PATH:/home/ubuntu/defects4j/framework/bin
export DEFECT_REPAIRING_DATASET=/home/ubuntu/DefectRepairing
```

*Set Java 11 as Default (inside the container)*

```bash
update-alternatives --config java
java -version
```
<br>

### LLM Configuration

**GPT-based Models**

```bash
export OPENAI_API_KEY=<your-api-key>

# Select model (default: gpt-4o-mini)
export OPENAI_MODEL=gpt-4o        # for GPT-4o
export OPENAI_MODEL=gpt-4o-mini   # for GPT-4o-mini
```

**Llama 3.2 3B (via Ollama)**

```bash
# Install Ollama
curl -fsSL https://ollama.com/install.sh | sh

# Pull and run the model
ollama pull llama3.2:3b
ollama run llama3.2:3b
```
<br>

### Running Experiments

```bash
# Prepare Patches
python3 experiments/setup-all-defect-repairing.py

# Run FixCheck
python3 experiments/run-all-defect-repairing.py <MODEL-OPTION>
```

<br>

### Available Model Options

| Model | Options |
|---|---|
| GPT-based | `gpt-baseline`, `gpt-h1`, `gpt-h2a`, `gpt-h2b`, `gpt-h3a`, `gpt-h3b`, `gpt-h3c`, `gpt-h4`, `gpt-h5`, `gpt-h6` |
| Llama 3.2 3B | `llama-baseline`, `llama-h1`, `llama-h2a`, `llama-h2b`, `llama-h3a`, `llama-h3b`, `llama-h3c`, `llama-h4`, `llama-h5`, `llama-h6` |

<br>

### Reproducing Paper Results

```bash
# Table II: strategy effectiveness across LLMs
python3 experiments/results/table2_effectiveness.py <MODEL-OPTION>

# Table III: assertion outcome distribution
python3 experiments/results/table3_outcomes.py <MODEL-OPTION>

# Table IV: patch-level precision and recall
python3 experiments/results/table4_precision_recall.py <MODEL-OPTION>
```
<br>

### Contact

For questions or issues, please open a GitHub issue.