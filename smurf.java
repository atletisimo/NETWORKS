import java.util.*;

public class SmurfFloodSimulation {

    static class Packet {
        String srcIP;
        String dstIP;
        String type;

        Packet(String srcIP, String dstIP, String type) {
            this.srcIP = srcIP;
            this.dstIP = dstIP;
            this.type = type;
        }

        @Override
        public String toString() {
            return "ICMP " + type + " | from " + srcIP + " -> " + dstIP;
        }
    }

    public static void main(String[] args) {
        String victimIP = "10.0.0.5";          // жртва
        String broadcastIP = "192.168.1.255";  // broadcast адреса

        // „хостови“ во мрежата
        List<String> hosts = Arrays.asList(
                "192.168.1.10",
                "192.168.1.11",
                "192.168.1.12",
                "192.168.1.13",
                "192.168.1.14"
        );

        int rounds = 3;   // број на итерации (бранови)
        int floodSize = 2; // колку spoofed барања по рунда

        Random rand = new Random();

        long totalReplies = 0;

        System.out.println("=== Smurf Flood Simulation ===\n");

        for (int r = 1; r <= rounds; r++) {
            System.out.println(">>> Round " + r + " <<<");

            for (int i = 0; i < floodSize; i++) {
                // Напаѓач праќа spoofed Echo Request кон broadcast
                System.out.println("Attacker sends spoofed ICMP Echo Request #" + (i+1));
                Packet spoofedRequest = new Packet(victimIP, broadcastIP, "ECHO_REQUEST");
                System.out.println(spoofedRequest);

                // Секој host враќа Echo Reply кон жртвата
                for (String host : hosts) {
                    Packet reply = new Packet(host, victimIP, "ECHO_REPLY");
                    System.out.println(reply);
                    totalReplies++;
                }
            }

            System.out.println("Victim " + victimIP + " has now received " + totalReplies + " ICMP Echo Replies!\n");
        }

        System.out.println("=== Simulation finished ===");
        System.out.println("Total Echo Replies received by victim: " + totalReplies);
    }
}
