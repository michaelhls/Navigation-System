package NavigationSystem;

import java.util.*;

public class App {
  // Shuffle array
  private static void shuffle(String[] array) {
    final Random rand = new Random();

    for (int i = array.length - 1; i > 0; i--) {
      final int randomIndex = rand.nextInt(i + 1);
      final String temp = array[randomIndex];

      array[randomIndex] = array[i];
      array[i] = temp;
    }
  }

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

    // Koneksi antar satu vertex dengan lain dengan weight acak untuk membentuk graf yang ideal
    i.connect(71, a);
    i.connect(102, g);
    i.connect(106, k);
    i.connect(91, j);
    a.connect(62, f);
    a.connect(85, e);
    a.connect(72, j);
    h.connect(43, m);
    h.connect(94, k);
    a.connect(23, l);
    b.connect(48, g);
    m.connect(43, j);
    d.connect(84, e);
    j.connect(89, k);
    g.connect(78, f);
    b.connect(54, l);
    d.connect(69, h);
    d.connect(72, c);
    c.connect(56, e);
    m.connect(63, l);
    b.connect(24, m);

    // Tambahkan ke BST
    String[] locations = {"Gedung A", "Gedung B", "Gedung C", "Gedung D", "Gedung E", "Gedung F",
            "Gedung G", "Gedung H", "Gedung I", "Gedung J", "Gedung K", "Gedung L",
            "Gedung M"};
    
    // Sesi warmup untuk JVM
    for (int warmupIndex = 0; warmupIndex < 1000; warmupIndex++) {
      shuffle(locations);

      for (String loc: locations) {
        bst.insert(loc);
      }

      bst.clear();
    }

    shuffle(locations);
    
    long bstInsertTime = 0;
    
    for (String loc: locations) {
      long start = System.nanoTime();
      bst.insert(loc);
      long end = System.nanoTime();
      bstInsertTime += end - start;
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
    
    for (Vertex vertex : Arrays.asList(a, b, c, d, e, f, g, h, i, j, k, l, m)) {
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
      System.out.println("Daftar lokasi (inorder/depth-first search):");
      bst.inorder();

      System.out.println("Daftar lokasi (breadth-first search):");
      bst.breadth();
    } else {
      System.out.println("Tidak ada jalur yang ditemukan!");
    }

    scanner.close();
  }
}