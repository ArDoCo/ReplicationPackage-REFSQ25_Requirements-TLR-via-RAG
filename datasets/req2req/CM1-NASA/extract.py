import os
import xml.etree.ElementTree as ET

def extract(in_file, output_dir):
    # <?xml version="1.0" encoding="utf-8"?>
    # <artifacts_collection>
    #   <collection_info>
    #     <id>cm1-high</id>
    #     <name>CM 1 Source Artifacts</name>
    #     <version>1.32</version>
    #     <description>Collection of Source Artifacts for the CM1 dataset (high)</description>
    #   </collection_info>
    #   <artifacts>
    #     <artifact>
    #       <id>SRS5.12.2.1</id>
    #       <content>  The DPU-CCM shall implement a mechanism whereby large memory loads and dumps can be accomplished incrementally.
    # </content>
    #       <parent_id />
    #     </artifact>
    #     <artifact>
    #       <id>SRS5.12.2.2</id>
    #       <content>  The DPU-CCM shall process real-time non-deferred commands within B ms of receipt from the ICU or the SCU.
    # </content>

    # Parse XML File
    tree = ET.parse(in_file)
    root = tree.getroot()

    # Create output directory
    if not os.path.exists(output_dir):
        os.makedirs(output_dir)

    # Extract artifacts
    for artifact in root.findall('artifacts/artifact'):
        id = artifact.find('id').text
        content = artifact.find('content').text.strip()
        with open(os.path.join(output_dir, id), 'w') as f:
            f.write(content)
            f.close()

def extract_gold(in_file:str):
    # <?xml version="1.0" encoding="utf-8"?>
    # <answer_set>
    #   <answer_info>
    #     <source_artifacts_collection>cm1-high</source_artifacts_collection>
    #     <target_artifacts_collection>cm1-low</target_artifacts_collection>
    #   </answer_info>
    #   <links>
    #     <link>
    #       <source_artifact_id>SRS5.12.2.1</source_artifact_id>
    #       <target_artifact_id>DPUSDS5.12.1.2.4</target_artifact_id>
    #       <confidence_score>1</confidence_score>
    #     </link>

    # Parse XML File
    tree = ET.parse(in_file)
    root = tree.getroot()
    out_file = "answer.csv"
    with open(out_file, 'w') as f:
        f.write('high,low\n')
        for link in root.findall('links/link'):
            source_id = link.find('source_artifact_id').text.strip()
            target_id = link.find('target_artifact_id').text.strip()
            f.write(f'{source_id},{target_id}\n')
        f.close()


if __name__ == '__main__':
    extract('CM1-sourceArtifacts.xml', 'high')
    extract('CM1-targetArtifacts.xml', 'low')
    extract_gold('CM1-answerSet.xml')



