import java.util.Random;

// Класа за симулација на барање (request)
class Request {
    String ip;
    int port;
    String content; // содржина на пакетот
    String program; // програма што праќа request

    public Request(String ip, int port, String content, String program) {
        this.ip = ip;
        this.port = port;
        this.content = content;
        this.program = program;
    }
}

// ----------------- Network Firewall -----------------
class NetworkFirewall {
    public boolean allow(Request req) {
        // Блокирај UDP порт 123 како пример
        if (req.port == 123) {
            System.out.println("[NetworkFirewall] BLOCKED IP: " + req.ip + " Port: " + req.port);
            return false;
        }
        System.out.println("[NetworkFirewall] Passed IP: " + req.ip + " Port: " + req.port);
        return true;
    }
}

// ----------------- Application Firewall (WAF) -----------------
class ApplicationFirewall {
    public boolean allow(Request req) {
        // Блокирај барања кои содржат "malware"
        if (req.content.contains("malware")) {
            System.out.println("[WAF] BLOCKED malicious content from IP: " + req.ip);
            return false;
        }
        System.out.println("[WAF] Passed content from IP: " + req.ip);
        return true;
    }
}

// ----------------- Host-Based Firewall -----------------
class HostFirewall {
    public boolean allow(Request req) {
        // Дозволи само Chrome да праќа HTTP барања на порт 80
        if (!req.program.equals("Chrome") && req.port == 80) {
            System.out.println("[HostFirewall] BLOCKED unauthorized program " + req.program + " from IP: " + req.ip);
            return false;
        }
        System.out.println("[HostFirewall] Passed program " + req.program + " from IP: " + req.ip);
        return true;
    }
}

// ----------------- Сервер кој проверува сите firewall-и -----------------
class Server {
    NetworkFirewall nwFirewall = new NetworkFirewall();
    ApplicationFirewall appFirewall = new ApplicationFirewall();
    HostFirewall hostFirewall = new HostFirewall();

    public void handleRequest(Request req) {
        if (nwFirewall.allow(req) && appFirewall.allow(req) && hostFirewall.allow(req)) {
            System.out.println("[SERVER] Request accepted from IP: " + req.ip + "\n");
        } else {
            System.out.println("[SERVER] Request dropped from IP: " + req.ip + "\n");
        }
    }
}

// ----------------- Бот кој праќа request -----------------
class Bot extends Thread {
    Server server;
    Request req;

    public Bot(Server server, Request req) {
        this.server = server;
        this.req = req;
    }

    @Override
    public void run() {
        server.handleRequest(req);
    }
}

// ----------------- Главна класа -----------------
public class FirewallSimulation {
    public static void main(String[] args) {
        Server server = new Server();
        Random rand = new Random();

        // Генерирајме 10 случајни барања
        for (int i = 0; i < 10; i++) {
            String ip = "192.168.1." + (rand.nextInt(100) + 1);
            int port = rand.nextInt(200); // порт од 0 до 199
            String content = rand.nextBoolean() ? "normal request" : "malware";
            String program = rand.nextBoolean() ? "Chrome" : "BadApp";

            Request req = new Request(ip, port, content, program);
            new Bot(server, req).start();
        }
    }
}
