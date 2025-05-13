package NavigationSystem;

import java.util.*;

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
    
    // Sesi warmup untuk JVM
    for (int warmupIndex = 0; warmupIndex < 1000; warmupIndex++) {
      for (String loc: locations) {
        bst.insert(loc);
      }
    }
    
    long bstInsertTime = 0;
    
    for (String loc: locations) {
      long start = System.nanoTime();
      bst.insert(loc);
      long end = System.nanoTime();
      bstInsertTime += end - start;
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

    // Sesi warmup untuk JVM
    for (int warmupIndex = 0; warmupIndex < 1000; warmupIndex++) {
      if (bst.search(startName)) {
        bst.search(endName);
      }
    }

    // Validasi menggunakan BST
    long bstSearchTime = System.nanoTime();
    if (!bst.search(startName) || !bst.search(endName)) {
      System.out.println("Lokasi tidak valid!");
      scanner.close();
      return;
    }
    long bstSearchTimeEnd = System.nanoTime();
    bstSearchTime = bstSearchTimeEnd - bstSearchTime;

    // Cari vertex berdasarkan nama
    Vertex start = null, end = null;
    
    for (Vertex vertex : Arrays.asList(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z)) {
      if (vertex.toString().equals(startName)) start = vertex;
      if (vertex.toString().equals(endName)) end = vertex;
    }

    // Sesi warmup untuk JVM
    for (int warmupIndex = 0; warmupIndex < 1000; warmupIndex++) {
      Dijkstra dijkstra = new Dijkstra(start);
      ArrayList<Vertex> path = dijkstra.getShortestPathTo(end);
    }

    // Jalankan Dijkstra (akan diintegrasikan dengan kode dari Anggota 2)
    long dijkstraTime = System.nanoTime();
    Dijkstra dijkstra = new Dijkstra(start);
    ArrayList<Vertex> path = dijkstra.getShortestPathTo(end);
    long dijkstraTimeEnd = System.nanoTime();
    dijkstraTime = dijkstraTimeEnd - dijkstraTime;

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