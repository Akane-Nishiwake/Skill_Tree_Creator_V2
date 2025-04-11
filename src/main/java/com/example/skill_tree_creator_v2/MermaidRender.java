package com.example.skill_tree_creator_v2;

public class MermaidRender
{
    private final SkillTree skillTree;

    public MermaidRender(SkillTree skillTree)
    {
        this.skillTree = skillTree;
    }

    /**
     * Render diagram
     * Creates a WebView to display the Mermaid diagram and adds it to the JFXPanel
     */
    public String diagramRender()
    {
        String mermaidDef = buildMermaidDiagram();
        return generateHtml(mermaidDef);
    }

    /**
     * Build Mermaid diagram
     *
     * @return String containing the Mermaid diagram definition
     * Constructs diagram by iterating through skill tree nodes and their prerequisites
     */
    private String buildMermaidDiagram()
    {
        StringBuilder sb = new StringBuilder("graph TD\n");
        for (SkillNode node : skillTree.getNodes())
        {
            sb.append(node.getId()).append("[\"").append(node.getName()).append("\"]\n");
            for (String prereq : node.getPrerequisites())
            {
                sb.append(prereq).append(" --> ").append(node.getId()).append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * Generate HTML for Mermaid diagram
     *
     * @param mermaidDefinition The Mermaid diagram definition
     * @return String containing the HTML content
     */
    private String generateHtml(String mermaidDefinition)
    {
        return "<html>" + "<head>" +
               "<script type=\"text/javascript\" src=\"https://cdn.jsdelivr.net/npm/mermaid/dist/mermaid.min" +
               ".js\"></script>" + "<script type=\"text/javascript\">" + "mermaid.initialize({startOnLoad:true});" +
               "</script>" + "</head>" + "<body>" + "<div class=\"mermaid\">" + mermaidDefinition + "</div>" +
               "</body>" + "</html>";
    }
}
