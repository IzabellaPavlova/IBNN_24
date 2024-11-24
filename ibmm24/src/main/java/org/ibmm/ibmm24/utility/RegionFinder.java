package org.ibmm.ibmm24.utility;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.ibmm.ibmm24.dto.RegionNode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RegionFinder {

    public static RegionNode buildTreeFromJson(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        // Read JSON and map it to the RegionNode class
        return objectMapper.readValue(new File(filePath), RegionNode.class);
    }

    // Find node by ID in the tree
    public static RegionNode findNodeById(RegionNode root, int id) {
        if (root.getId() == id) {
            return root;
        }
        if (root.getSubregions() != null) {
            for (RegionNode subregion : root.getSubregions()) {
                RegionNode found = findNodeById(subregion, id);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }

    // Collect all leaf nodes in the subtree
    public static void collectLeaves(RegionNode node, List<RegionNode> leaves) {
        if (node.getSubregions() == null || node.getSubregions().isEmpty()) {
            leaves.add(node);
        } else {
            for (RegionNode subregion : node.getSubregions()) {
                collectLeaves(subregion, leaves);
            }
        }
    }

    // Main method to find all leaves given an ID
    public static List<Integer> findLeavesById(RegionNode root, int id) {
        RegionNode targetNode = findNodeById(root, id);
        List<RegionNode> leaves = new ArrayList<>();
        if (targetNode != null) {
            collectLeaves(targetNode, leaves);
        }
        return leaves.stream().map(RegionNode::getId).toList();
    }


}