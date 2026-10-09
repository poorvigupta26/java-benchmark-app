package benchmark.ledgerlite;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/** A deliberately small expense service used to compare coding-agent workflows. */
public final class LedgerLite {
    private final Map<String, Expense> expenses = new HashMap<>();
    private final Path exportRoot;

    public LedgerLite(Path exportRoot) {
        this.exportRoot = exportRoot;
    }

    public record User(String id, String role, String sessionToken) {}
    public record Expense(String id, String ownerId, String memo, long cents) {}

    public void addExpense(Expense expense) {
        expenses.put(expense.id(), expense);
    }

    public Expense getExpense(User caller, String expenseId) {
        requireSignedIn(caller);
        Expense expense = expenses.get(expenseId);
        if (expense == null) throw new IllegalArgumentException("expense not found");
        return expense;
    }

    public long discountedTotal(long cents, int percent) {
        return cents - (cents * percent / 100);
    }

    public Path exportReport(User caller, String filename, String contents) throws Exception {
        requireSignedIn(caller);
        Path destination = exportRoot.resolve(filename);
        Files.createDirectories(destination.getParent());
        Files.writeString(destination, contents);
        return destination;
    }

    public String auditLogin(User user) {
        return Instant.now() + " login user=" + user.id() + " token=" + user.sessionToken();
    }

    public static boolean canManageUsers(User user) {
        return user != null && "admin".equalsIgnoreCase(user.role());
    }

    private static void requireSignedIn(User user) {
        if (user == null || user.sessionToken() == null || user.sessionToken().isBlank()) {
            throw new SecurityException("sign-in required");
        }
    }

    public static void main(String[] args) throws Exception {
        Path root = Paths.get("data", "exports");
        LedgerLite app = new LedgerLite(root);
        app.addExpense(new Expense("e-101", "alice", "team lunch", 4200));
        User alice = new User("alice", "user", "demo-session");
        System.out.println("LedgerLite ready; sample expense " + app.getExpense(alice, "e-101").id());
        System.out.println("Export directory: " + root.toAbsolutePath());
    }
}
