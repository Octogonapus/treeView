import java.util.LinkedList;
import java.util.List;
import processing.core.PVector;

/**
 * k-ary {@link TreeNode} of {@link String}.
 */
public class BasicNode extends Particle implements TreeNode<BasicNode, String> {

  private final String data;
  private List<BasicNode> children;
  private BasicNode parent;

  public BasicNode(Main main, PVector l) {
    this(main, l, "root");
  }

  public BasicNode(Main main, PVector l, String name) {
    super(main, l);
    data = name;
    children = new LinkedList<>();
  }

  @Override
  public void addChild(BasicNode child) {
    children.add(child);
    child.setParent(this);
  }

  @Override
  public void removeChild(BasicNode child) {
    children.remove(child);
  }

  @Override
  public void removeChildren() {
    children = null;
  }

  @Override
  public String getData() {
    return data;
  }

  @Override
  public List<BasicNode> getChildren() {
    return children;
  }

  public void setChildren(List<BasicNode> children) {
    this.children = children;
  }

  @Override
  public BasicNode getParent() {
    return parent;
  }

  @Override
  public void setParent(BasicNode parent) {
    this.parent = parent;
  }

}
