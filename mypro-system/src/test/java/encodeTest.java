import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class encodeTest {
    public static void main(String[] args) {
        String admin123 = new BCryptPasswordEncoder().encode("admin123");
        System.out.println(admin123);
    }
}
