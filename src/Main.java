import java.util.Random;
import processing.core.PApplet;
import processing.core.PVector;

public class Main extends PApplet {

  private static final int WINDOW_X = 1000;
  private static final int WINDOW_Y = 1000;

  private final Random random;
  private final KTree<BasicNode> tree;

  public Main() {
    random = new Random();
    tree = new KTree<>(new BasicNode(this, new PVector(WINDOW_X / 2, WINDOW_Y / 2)));
    BasicNode node1 = newRandomNode();
    node1.addChild(newRandomNode());
    tree.getRoot().addChild(node1);
    tree.getRoot().addChild(newRandomNode());
    tree.getRoot().addChild(newRandomNode());
  }

  public static void main(String[] args) {
    PApplet.main("Main");
  }

  private BasicNode newRandomNode() {
    return new BasicNode(this, new PVector(
        random.nextFloat() * WINDOW_X,
        random.nextFloat() * WINDOW_Y));
  }

  @Override
  public void settings() {
    size(WINDOW_X, WINDOW_Y);
  }

  @Override
  public void draw() {
    background(0);
    tree.getRoot().position.set(mouseX, mouseY);
    pushChildrenToParents(tree.getRoot(), 50.0f);
    stepTreeNodePositions(tree.getRoot());
    drawLinesBetweenAllNodes(tree.getRoot());
  }

  private void pushChildrenToParents(BasicNode root, float offset) {
    root.getChildren().forEach(child -> {
      child.pushTo(root.position, offset);
      root.getChildren().forEach(otherChild -> {
        if (otherChild != child) {
          child.pushFrom(otherChild.position, 30.0f);
        }
      });
    });
    root.getChildren().forEach(child -> child.goToY(root.position.y + 40.0f));
    root.getChildren().forEach(child -> pushChildrenToParents(child, offset));
  }

  private void stepTreeNodePositions(BasicNode root) {
    root.run();
    root.getChildren().forEach(this::stepTreeNodePositions);
  }

  private void drawLinesBetweenAllNodes(BasicNode root) {
    stroke(255);
    root.getChildren().forEach(child -> line(
        root.position.x, root.position.y,
        child.position.x, child.position.y));
    root.getChildren().forEach(this::drawLinesBetweenAllNodes);
  }

}
