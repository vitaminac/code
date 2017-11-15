package oop.xml;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class XMLNode {
    private final ArrayList<XMLNode> children;
    private String nodeName;
    private String value;

    public XMLNode(String nodeName) {
        this(nodeName, "");
    }

    public XMLNode(final String nodeName, final String value) {
        this(nodeName, value, new XMLNode[0]);
    }

    public XMLNode(String nodeName, String value, XMLNode... children) {
        this.setNodeName(nodeName);
        this.setValue(value);
        this.children = new ArrayList<XMLNode>(Arrays.asList(children));
    }

    public String getNodeName() {
        return this.nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void appendChild(XMLNode child) {
        this.getChildren().add(child);
    }

    public ArrayList<XMLNode> getChildren() {
        return this.children;
    }

    @Override
    public String toString() {
        ArrayList<String> children = new ArrayList<String>();
        for (XMLNode node : this.getChildren()) {
            children.add(node.toString());
        }
        if (this.getChildren().size() > 0) {
            children.add("");
        }
        return MessageFormat.format("<{0}>\n{1}\n{2}</{0}>", new String[]{this.getNodeName(), this.getValue(), String.join("\n", children)});
    }
}
