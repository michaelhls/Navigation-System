package Map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

class VertexMap<T> extends HashTable<Vertex, T> {
  private static final int HASH_TABLE_SIZE = 25;

  public VertexMap() {
    super(HASH_TABLE_SIZE);
  }

  @Override
  protected int hashFunction(final Vertex key) {
    return Math.abs(key.toString().hashCode()) % HASH_TABLE_SIZE;
  }
}

class DistanceMap extends VertexMap<Double> {
  public DistanceMap(final Vertex source) {
    this.set(source, 0.0);
  }

  @Override
  public Double get(final Vertex key) {
    final Double distance = super.get(key);

    return distance == null ? Double.MAX_VALUE : distance;
  }
}

class Dijkstra {
  private VertexMap<Vertex> previousMap;

  public Dijkstra(final Vertex start) {
    this.previousMap = new VertexMap<>();

    final DistanceMap distanceMap = new DistanceMap(start);
    final PriorityQueue<HashTable.Entry<Vertex, Double>> queue = new PriorityQueue<>(HashTable.Entry.comparingByValue());

    queue.add(new HashTable.Entry<>(start, 0.0));

    while (!queue.isEmpty()) {
      final HashTable.Entry<Vertex, Double> source = queue.poll();
      final Vertex sourceVector = source.getKey();

      for (final Edge edge: sourceVector.getEdges()) {
        final Vertex edgeDestination = edge.getDestination();
        final double newDistance = source.getValue() + edge.getWeight();

        if (newDistance < distanceMap.get(edgeDestination)) {
          distanceMap.set(edgeDestination, newDistance);
          queue.add(new HashTable.Entry<>(edgeDestination, newDistance));
          
          this.previousMap.set(edgeDestination, sourceVector);
        }
      }
    }
  }

  public ArrayList<Vertex> getShortestPathTo(final Vertex destination) {
    final ArrayList<Vertex> result = new ArrayList<>(1);

    result.add(destination);

    Vertex current = destination;
    Vertex previous;

    while ((previous = this.previousMap.get(current)) != null) {
      result.add(previous);
      current = previous;
    }

    Collections.reverse(result);

    return result;
  }
}

public class App {
  public static void main(String[] args) {
    final Vertex a = new Vertex("A");
    final Vertex b = new Vertex("B");
    final Vertex c = new Vertex("C");
    final Vertex d = new Vertex("D");
    final Vertex e = new Vertex("E");
    final Vertex f = new Vertex("F");

    a.connect(7, b);
    a.connect(14, c);
    a.connect(9, d);
    c.connect(2, d);
    d.connect(11, e);
    b.connect(10, d);
    b.connect(15, e);
    c.connect(9, f);
    e.connect(6, f);

    final Dijkstra dijkstra = new Dijkstra(a);

    System.out.println(dijkstra.getShortestPathTo(f).toString());
  }
}