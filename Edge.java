package Map;

public class Edge {
  private final double weight;
  private final Vertex source;
  private final Vertex destination;

  public Edge(final double weight, final Vertex source, final Vertex destination) {
    this.weight = weight;
    this.source = source;
    this.destination = destination;
  }

  public double getWeight() {
    return this.weight;
  }

  public Vertex getSource() {
    return this.source;
  }

  public Vertex getDestination() {
    return this.destination;
  }
}