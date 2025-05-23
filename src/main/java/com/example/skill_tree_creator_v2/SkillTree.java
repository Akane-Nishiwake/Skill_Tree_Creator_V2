package com.example.skill_tree_creator_v2;

import java.util.ArrayList;
import java.util.List;

/**
 * SkillTreeWrapper - Wrapper class for JSON serialization
 * Provides a container for the skill tree structure
 */
class SkillTreeWrapper
{
    private SkillTree skill_tree;

    public SkillTreeWrapper(SkillTree skillTree)
    {
        this.skill_tree = skillTree;
    }

    public SkillTree getSkill_tree()
    {
        return skill_tree;
    }

    public void setSkill_tree(SkillTree skillTree)
    {
        this.skill_tree = skillTree;
    }
}

/**
 * SkillTree - Represents a complete skill tree structure
 * Contains the tree name and collection of skill nodes
 */
public class SkillTree
{
    private String name;
    private List<SkillNode> nodes;

    /**
     * Default constructor needed by Gson
     * Initializes an empty list of nodes
     */
    public SkillTree()
    {
        this.nodes = new ArrayList<>();
    }

    /**
     * Constructor with parameters
     *
     * @param name  Name of the skill tree
     * @param nodes List of skill nodes
     */
    public SkillTree(String name, List<SkillNode> nodes)
    {
        this.name = name;
        this.nodes = nodes != null ? nodes : new ArrayList<>();
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<SkillNode> getNodes()
    {
        return nodes;
    }

    public void setNodes(List<SkillNode> nodes)
    {
        this.nodes = nodes;
    }
}

/**
 * SkillNode - Represents a single node in the skill tree
 * Contains all properties of a skill including prerequisites
 */
class SkillNode
{
    String id;
    String name;
    String description;
    int cost;
    String effect;
    List<String> prerequisites;

    /**
     * Constructor
     *
     * @param i   Node ID
     * @param nam Node name
     * @param des Node description
     * @param c   Node cost
     * @param ef  Node effect
     * @param pre List of prerequisite node IDs
     */
    public SkillNode(String i, String nam, String des, int c, String ef, List<String> pre)
    {
        id = i;
        name = nam;
        description = des;
        cost = c;
        effect = ef;
        this.prerequisites = pre != null ? pre : new ArrayList<>();
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public int getCost()
    {
        return cost;
    }

    public void setCost(int cost)
    {
        this.cost = cost;
    }

    public String getEffect()
    {
        return effect;
    }

    public void setEffect(String effect)
    {
        this.effect = effect;
    }

    public List<String> getPrerequisites()
    {
        return prerequisites;
    }

    public void setPrerequisites(List<String> prerequisites)
    {
        this.prerequisites = prerequisites;
    }
}

