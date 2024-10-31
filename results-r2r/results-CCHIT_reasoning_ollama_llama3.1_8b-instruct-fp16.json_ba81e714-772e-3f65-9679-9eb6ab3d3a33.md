## Configuration
```json
{
  "cache_dir" : "./cache/CCHIT",
  "gold_standard_configuration" : {
    "hasHeader" : true,
    "path" : "./datasets/req2req/CCHIT/answer.csv"
  },
  "source_artifact_provider" : {
    "name" : "text",
    "args" : {
      "artifact_type" : "requirement",
      "path" : "./datasets/req2req/CCHIT/high"
    }
  },
  "target_artifact_provider" : {
    "name" : "text",
    "args" : {
      "artifact_type" : "requirement",
      "path" : "./datasets/req2req/CCHIT/low"
    }
  },
  "source_preprocessor" : {
    "name" : "artifact",
    "args" : { }
  },
  "target_preprocessor" : {
    "name" : "artifact",
    "args" : { }
  },
  "embedding_creator" : {
    "name" : "openai",
    "args" : {
      "model" : "text-embedding-3-large"
    }
  },
  "source_store" : {
    "name" : "custom",
    "args" : { }
  },
  "target_store" : {
    "name" : "custom",
    "args" : {
      "max_results" : "4"
    }
  },
  "classifier" : {
    "name" : "reasoning_ollama",
    "args" : {
      "model" : "llama3.1:8b-instruct-fp16",
      "seed" : "133742243",
      "prompt" : "Below are two artifacts from the same software system. Is there a traceability link between (1) and (2)? Give your reasoning and then answer with 'yes' or 'no' enclosed in <trace> </trace>.\n (1) {source_type}: '''{source_content}''' \n (2) {target_type}: '''{target_content}''' ",
      "use_original_artifacts" : "false",
      "use_system_message" : "true"
    }
  },
  "result_aggregator" : {
    "name" : "any_connection",
    "args" : {
      "source_granularity" : "0",
      "target_granularity" : "0"
    }
  },
  "tracelinkid_postprocessor" : {
    "name" : "identity",
    "args" : {
      "reverse" : "false"
    }
  }
}
```

## Stats
* # TraceLinks (GS): 587
* # Source Artifacts: 116
* # Target Artifacts: 1064
## Results
* True Positives: 76
* False Positives: 199
* False Negatives: 511
* Precision: 0.27636363636363637
* Recall: 0.12947189097103917
* F1: 0.17633410672853828
