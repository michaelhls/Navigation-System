package NavigationSystem;

import java.util.ArrayList;

public class Vertex {
  private String name;
  private ArrayList<Edge> edges;

  public Vertex(String name) {
    this.name = name;
    this.edges = new ArrayList<>();
  }

  public ArrayList<Edge> getEdges() {
    return this.edges;
  }

  private void insertEdge(final Edge newEdge) {
    final Vertex newEdgeSource = newEdge.getSource();
    final Vertex newEdgeDestination = newEdge.getDestination();

    for (final Edge edge: this.edges) {
      final Vertex edgeSource = edge.getSource();
      final Vertex edgeDestination = edge.getDestination();

      // Agar tidak ada duplikat edge dengan arah yang sama
      if ((newEdgeSource == edgeSource && newEdgeDestination == edgeDestination) || (newEdgeSource == edgeDestination && newEdgeDestination == edgeSource)) {
        return;
      }
    }

    this.edges.add(newEdge);
  }
  
  public void connect(final double weight, final Vertex other) {
    this.insertEdge(new Edge(weight, this, other));
    other.insertEdge(new Edge(weight, other, this));
  }

  @Override
  public String toString() {
    return this.name;
  }
}