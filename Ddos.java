import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class Server {
    private BlockingQueue<String> requests;
    private int dropped = 0;
    private int capacity;

    public Server(int capacity) {
        this.capacity = capacity;
        this.requests = new LinkedBlockingQueue<>(capacity);
    }

    public synchronized void receiveRequest(int botId) {
        if (!requests.offer("Барање од BOT-" + botId)) {
            dropped++;
        }
    }

    public void processRequests() {
        new Thread(() -> {
            while (true) {
                try {
                    String req = requests.take();
                    System.out.println("[SERVER] Обработувам " + req);
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void monitor() {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    synchronized (this) {
                        System.out.println("[MONITOR] Опашка: " + requests.size() +
                                " | Одбиени: " + dropped);
                        dropped = 0;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

class Bot extends Thread {
    private int id;
    private Server server;

    public Bot(int id, Server server) {
        this.id = id;
        this.server = server;
    }

    @Override
    public void run() {
        while (true) {
            server.receiveRequest(id);
            try {
                Thread.sleep((long) (50 + Math.random() * 150));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class DDoSSimulation {
    public static void main(String[] args) {
        Server server = new Server(20);
        server.processRequests();
        server.monitor();

        // стартувај 50 ботови
        for (int i = 0; i < 50; i++) {
            new Bot(i, server).start();
        }
    }
}
