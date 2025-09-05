import java.util.Random;

class VulnerableApp {
    private boolean patched = false; // прво нема закрпа
    private String secretData = "User Passwords: 1234, admin, qwerty";

    public void patchSystem() {
        patched = true;
        System.out.println("[SYSTEM] Patch applied. Vulnerability closed.");
    }

    // ранлив метод - симулира баг кој напаѓач може да го искористи
    public void vulnerableMethod() {
        if (!patched) {
            System.out.println("[WARNING] Unknown vulnerability triggered!");
            System.out.println("[ATTACKER] Exfiltrated data: " + secretData);
        } else {
            System.out.println("[SYSTEM] Attempt blocked. System is patched.");
        }
    }
}

class Attacker {
    public void exploit(VulnerableApp app) {
        System.out.println("[ATTACKER] Trying to exploit zero-day...");
        app.vulnerableMethod();
    }
}

public class ZeroDaySimulation {
    public static void main(String[] args) {
        VulnerableApp app = new VulnerableApp();
        Attacker hacker = new Attacker();

        // Симулираме напад пред да има закрпа
        hacker.exploit(app);

        // Подоцна се издава закрпа
        app.patchSystem();

        // Напаѓачот повторно пробува
        hacker.exploit(app);
    }
}
