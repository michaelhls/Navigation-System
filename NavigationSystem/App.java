package NavigationSystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.*;

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
    this.previousMap = new VertexMap<Vertex>();;

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
    boolean reachable = false;

    while ((previous = this.previousMap.get(current)) != null) {
      result.add(previous);
      current = previous;
      reachable = true;
    }

    Collections.reverse(result);

    if (!reachable && result.size() == 1 && result.get(0) != destination) {
      return new ArrayList<>(); // Jalur tidak valid
    }

    return result;
  }
}

public class App {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    BST bst = new BST();

    // Inisialisasi graf
    Vertex a = new Vertex("Gedung A");
    Vertex b = new Vertex("Gedung B");
    Vertex c = new Vertex("Gedung C");
    Vertex d = new Vertex("Gedung D");
    Vertex e = new Vertex("Gedung E");
    Vertex f = new Vertex("Gedung F");
    Vertex g = new Vertex("Gedung G");
    Vertex h = new Vertex("Gedung H");
    Vertex i = new Vertex("Gedung I");
    Vertex j = new Vertex("Gedung J");
    Vertex k = new Vertex("Gedung K");
    Vertex l = new Vertex("Gedung L");
    Vertex m = new Vertex("Gedung M");
    Vertex n = new Vertex("Gedung N");
    Vertex o = new Vertex("Gedung O");
    Vertex p = new Vertex("Gedung P");
    Vertex q = new Vertex("Gedung Q");
    Vertex r = new Vertex("Gedung R");
    Vertex s = new Vertex("Gedung S");
    Vertex t = new Vertex("Gedung T");
    Vertex u = new Vertex("Gedung U");
    Vertex v = new Vertex("Gedung V");
    Vertex w = new Vertex("Gedung W");
    Vertex x = new Vertex("Gedung X");
    Vertex y = new Vertex("Gedung Y");
    Vertex z = new Vertex("Gedung Z");

    // Tambahkan ke BST
    String[] locations = {"Gedung A", "Gedung B", "Gedung C", "Gedung D", "Gedung E", "Gedung F",
            "Gedung G", "Gedung H", "Gedung I", "Gedung J", "Gedung K", "Gedung L",
            "Gedung M", "Gedung N", "Gedung O", "Gedung P", "Gedung Q", "Gedung R",
            "Gedung S", "Gedung T", "Gedung U", "Gedung V", "Gedung W", "Gedung X",
            "Gedung Y", "Gedung Z"};
    long bstInsertTime = 0;
    for (String loc : locations) {
      long start = System.nanoTime();
      bst.insert(loc);
      bstInsertTime += System.nanoTime() - start;
    }

    // Tambahkan sisi antara semua simpul A-Z
    List<Vertex> allVertices = Arrays.asList(
            a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z
    );

    for (int index = 0; index < allVertices.size() - 1; index++) {
      Vertex current = allVertices.get(index);
      Vertex next = allVertices.get(index + 1);
      current.connect(1, next); // Bobot 1 m
    }

    // Input pengguna
    System.out.print("Masukkan lokasi awal: ");
    String startName = scanner.nextLine();
    System.out.print("Masukkan lokasi tujuan: ");
    String endName = scanner.nextLine();

    // Validasi menggunakan BST
    long bstSearchTime = System.nanoTime();
    if (!bst.search(startName) || !bst.search(endName)) {
      System.out.println("Lokasi tidak valid!");
      scanner.close();
      return;
    }
    bstSearchTime = System.nanoTime() - bstSearchTime;

    // Cari vertex berdasarkan nama
    Vertex start = null, end = null;
    for (Vertex vertex : Arrays.asList(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z)) {
      if (vertex.toString().equals(startName)) start = vertex;
      if (vertex.toString().equals(endName)) end = vertex;
    }

    // Jalankan Dijkstra (akan diintegrasikan dengan kode dari Anggota 2)
    long dijkstraTime = System.nanoTime();
    Dijkstra dijkstra = new Dijkstra(start);
    ArrayList<Vertex> path = dijkstra.getShortestPathTo(end);
    dijkstraTime = System.nanoTime() - dijkstraTime;

    // Hitung jarak total
    double totalDistance = 0;
    for (int index = 0; index < path.size() - 1; index++) {
      Vertex curr = path.get(index);
      Vertex next = path.get(index + 1);
      for (Edge edge : curr.getEdges()) {
        if (edge.getDestination() == next) {
          totalDistance += edge.getWeight();
          break;
        }
      }
    }

    // Tampilkan hasil
    if (!path.isEmpty() && path.get(0) == start && path.get(path.size() - 1) == end) {
      System.out.println("Jalur terpendek: " + path);
      System.out.println("Jarak total: " + totalDistance + " meter");
      System.out.println("Waktu BST Insert (ms): " + bstInsertTime / 1_000_000.0);
      System.out.println("Waktu BST Search (ms): " + bstSearchTime / 1_000_000.0);
      System.out.println("Waktu Dijkstra (ms): " + dijkstraTime / 1_000_000.0);

      // Tampilkan traversal BST
      System.out.println("Daftar lokasi (inorder):");
      bst.inorder();
    } else {
      System.out.println("Tidak ada jalur yang ditemukan!");
    }

    scanner.close();
  }
}