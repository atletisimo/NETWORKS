import java.util.Random;

class VulnerableApp {
    private boolean patched = false; // прво нема закрпа
    private String secretData = "User Passwords: 1234, admin, qwerty";

    // метод за примена на patch
    public void patchSystem() {
        patched = true;
        System.out.println("[SYSTEM] Patch applied. Vulnerability closed.");
    }

    // ранлив метод - симулира баг
    public void vulnerableMethod() {
        if (!patched) {
            System.out.println("[WARNING] Unknown vulnerability triggered!");
            System.out.println("[ATTACKER] Exfiltrated data: " + secretData);
            // автоматски се применува patch
            patchSystem();
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

public class AutoPatchSimulation {
    public static void main(String[] args) {
        VulnerableApp app = new VulnerableApp();
        Attacker hacker = new Attacker();

        System.out.println("=== First attack attempt ===");
        hacker.exploit(app);  // прв напад → експлоатира ранливост и се активира patch

        System.out.println("\n=== Second attack attempt ===");
        hacker.exploit(app);  // втор напад → веќе е закрпен, нападот не успева
    }
}
