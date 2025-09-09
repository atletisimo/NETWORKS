import java.util.*;

public class AttackSimulation {

    static class Attack {
        String category;
        String type;
        String description;

        Attack(String category, String type, String description) {
            this.category = category;
            this.type = type;
            this.description = description;
        }

        void simulate() {
            System.out.println("[" + category + "] " + type + " -> " + description);
            switch (category) {
                case "Network":
                    simulateNetwork();
                    break;
                case "Application":
                    simulateApplication();
                    break;
                case "Social Engineering":
                    simulateSocialEngineering();
                    break;
                case "Malware":
                    simulateMalware();
                    break;
                case "Physical":
                    simulatePhysical();
                    break;
            }
        }

        private void simulateNetwork() {
            System.out.println("Simulating network packets...");
            Random rand = new Random();
            int packets = rand.nextInt(5) + 1;
            for (int i = 0; i < packets; i++) {
                System.out.println("Packet #" + (i + 1) + " sent with spoofed IP: 192.168." + rand.nextInt(255) + "." + rand.nextInt(255));
            }
            System.out.println("Network simulation complete.\n");
        }

        private void simulateApplication() {
            System.out.println("Simulating application attack...");
            Random rand = new Random();
            String[] fakeInputs = {"<script>alert('XSS')</script>", "' OR '1'='1", "../../etc/passwd"};
            for (String input : fakeInputs) {
                System.out.println("Injected input: " + input);
            }
            System.out.println("Application simulation complete.\n");
        }

        private void simulateSocialEngineering() {
            System.out.println("Simulating social engineering...");
            String[] messages = {"Phishing email to user@example.com", "Spear phishing targeting CEO", "SMS scam to 555-1234"};
            for (String msg : messages) {
                System.out.println(msg);
            }
            System.out.println("Social engineering simulation complete.\n");
        }

        private void simulateMalware() {
            System.out.println("Simulating malware...");
            String[] actions = {"Install keylogger", "Encrypt files (ransomware)", "Start crypto miner"};
            for (String action : actions) {
                System.out.println("Action: " + action);
            }
            System.out.println("Malware simulation complete.\n");
        }

        private void simulatePhysical() {
            System.out.println("Simulating physical attack...");
            String[] actions = {"Inspect RAM (cold boot)", "Read keyboard strokes", "Use BadUSB device"};
            for (String action : actions) {
                System.out.println("Action: " + action);
            }
            System.out.println("Physical attack simulation complete.\n");
        }
    }

    public static void main(String[] args) {
        List<Attack> attacks = Arrays.asList(
                new Attack("Network", "SYN Flood", "Simulate TCP SYN flood attack"),
                new Attack("Network", "Ping of Death", "Simulate oversized ICMP packet flood"),
                new Attack("Application", "SQL Injection", "Inject malicious SQL queries"),
                new Attack("Application", "XSS", "Inject JavaScript code into forms"),
                new Attack("Social Engineering", "Phishing", "Send fake emails to users"),
                new Attack("Social Engineering", "Vishing", "Simulate phone scam"),
                new Attack("Malware", "Ransomware", "Encrypt files and demand ransom"),
                new Attack("Malware", "Keylogger", "Record user keystrokes"),
                new Attack("Physical", "Cold Boot", "Extract data from RAM after shutdown"),
                new Attack("Physical", "BadUSB", "Simulate USB injection attack")
        );

        System.out.println("=== Attack Simulation Start ===\n");

        for (Attack attack : attacks) {
            attack.simulate();
        }

        System.out.println("=== Attack Simulation Complete ===");
    }
}
