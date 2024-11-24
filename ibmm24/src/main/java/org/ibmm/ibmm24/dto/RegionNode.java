package org.ibmm.ibmm24.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegionNode {
    int id;
    String name;
    List<RegionNode> subregions;
}
