import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class Server {
    private BlockingQueue<String> requests = new LinkedBlockingQueue<>();
    private int requestCount = 0;

    public synchronized void receiveRequest(int botId, String message) {
        System.out.println("[SERVER] Примено од BOT-" + botId + ": " + message);
        requests.add("BOT-" + botId + " -> " + message);
        requestCount++;
    }

    public void processRequests() {
        new Thread(() -> {
            while (true) {
                try {
                    String req = requests.take();
                    System.out.println("[SERVER] Обработувам: " + req);
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void monitorLoad() {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    synchronized (this) {
                        System.out.println("[MONITOR] RPS: " + requestCount);
                        requestCount = 0;
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
        for (int i = 1; i <= 5; i++) {
            try {
                Thread.sleep((long) (200 + Math.random() * 800));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            server.receiveRequest(id, "Барање-" + i);
        }
    }
}

public class BotnetSimulation {
    public static void main(String[] args) {
        Server server = new Server();
        server.processRequests();
        server.monitorLoad();

        for (int i = 0; i < 5; i++) {
            new Bot(i, server).start();
        }
    }
}
