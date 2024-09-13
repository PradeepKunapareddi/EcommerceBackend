package BackendUtil;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashGenerator {
 public static void main(String[] args) {
     BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
     String hashedPassword = passwordEncoder.encode("Nareshit@789");
     System.out.println("Hashed password: " + hashedPassword);
 }
}
