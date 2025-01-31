# Install the required packages (if needed)
# install.packages("PMCMRplus")
# install.packages("rstatix")
library("PMCMRplus")
library("rstatix")

# Load results and prepare additional columns
results <- read.csv("approach-results.csv")
results$Approach_Model <- paste(results$Approach, results$Model, sep="_")
results$Approach_Dataset <- paste(results$Approach, results$Dataset, sep="_")
results$Dataset_Model <- paste(results$Dataset, results$Model, sep="_")

# Create a subset of the results: Only LLMs without IR
results_llm <- subset(results, Approach != "IR")
# Create a subset of the results: Only LLMs without Codellama (Outlier)
results_llm_without_codellama <- subset(results_llm, Model != "ollama_codellama_13b")

# Show the tables (optional)
# View(results)
# View(results_llm)

# Perform the Friedman test, effect size, and Nemenyi post-hoc test

## RQ1: F1 grouped by Prompt (Approach) and Model ##
friedman.test(results$F1, results$Approach_Model, results$Dataset)
friedman_effsize(results, F1 ~ Approach_Model | Dataset, ci=TRUE)
frdAllPairsNemenyiTest(results$F1, results$Approach_Model, results$Dataset)

## RQ1: F2 grouped by Prompt (Approach) and Model ##
friedman.test(results$F2, results$Approach_Model, results$Dataset)
friedman_effsize(results, F2 ~ Approach_Model | Dataset, ci=TRUE)
frdAllPairsNemenyiTest(results$F2, results$Approach_Model, results$Dataset)

## RQ2: F1 (Only for LLMs) grouped by Prompt (Approach) ##
friedman.test(results_llm$F1, results_llm$Approach, results_llm$Dataset_Model)
friedman_effsize(results_llm, F1 ~ Approach | Dataset_Model, ci=TRUE)
frdAllPairsNemenyiTest(results_llm$F1, results_llm$Approach, results_llm$Dataset_Model)

## RQ2: F2 (Only for LLMs) grouped by Prompt (Approach) ##
friedman.test(results_llm$F2, results_llm$Approach, results_llm$Dataset_Model)
friedman_effsize(results_llm, F2 ~ Approach | Dataset_Model, ci=TRUE)
frdAllPairsNemenyiTest(results_llm$F2, results_llm$Approach, results_llm$Dataset_Model)

## F1 (Only for LLMs without Codellama (Outlier)) grouped by Prompt (Approach) ##
friedman.test(results_llm_without_codellama$F1, results_llm_without_codellama$Approach, results_llm_without_codellama$Dataset_Model)
friedman_effsize(results_llm_without_codellama, F1 ~ Approach | Dataset_Model, ci=TRUE)
frdAllPairsNemenyiTest(results_llm_without_codellama$F1, results_llm_without_codellama$Approach, results_llm_without_codellama$Dataset_Model)

## F2 (Only for LLMs without Codellama (Outlier)) grouped by Prompt (Approach) ##
friedman.test(results_llm_without_codellama$F2, results_llm_without_codellama$Approach, results_llm_without_codellama$Dataset_Model)
friedman_effsize(results_llm_without_codellama, F2 ~ Approach | Dataset_Model, ci=TRUE)
frdAllPairsNemenyiTest(results_llm_without_codellama$F2, results_llm_without_codellama$Approach, results_llm_without_codellama$Dataset_Model)

## RQ3: F1 (Only for LLMs) grouped by Model ##
friedman.test(results_llm$F1, results_llm$Model, results_llm$Approach_Dataset)
friedman_effsize(results_llm, F1 ~ Model | Approach_Dataset, ci=TRUE)
frdAllPairsNemenyiTest(results_llm$F1, results_llm$Model, results_llm$Approach_Dataset)

## RQ3: F2 (Only for LLMs) grouped by Model ##
friedman.test(results_llm$F2, results_llm$Model, results_llm$Approach_Dataset)
friedman_effsize(results_llm, F2 ~ Model | Approach_Dataset, ci=TRUE)
frdAllPairsNemenyiTest(results_llm$F2, results_llm$Model, results_llm$Approach_Dataset)
