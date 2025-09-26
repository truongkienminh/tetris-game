package kienminh.tetrisgame.service;

import kienminh.tetrisgame.entity.User;
import kienminh.tetrisgame.repository.UserRepository;
import kienminh.tetrisgame.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    // đăng ký user mới
    public User register(String username, String password, String email) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password)); // MÃ HÓA PASSWORD
        user.setEmail(email);
        return userRepository.save(user);
    }

    // AuthService.java
// ...
// đăng nhập
    public String login(String username, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            // Kiểm tra xem người dùng có được xác thực không
            if (authentication.isAuthenticated()) {
                // Lấy UserDetails từ đối tượng Authentication
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                // Tạo và trả về JWT token
                return jwtUtil.generateToken(userDetails.getUsername());
            }
        } catch (BadCredentialsException e) {
            System.err.println("Đăng nhập thất bại cho người dùng: " + username + " - Thông tin không hợp lệ.");
            throw new RuntimeException("Tên người dùng hoặc mật khẩu không hợp lệ", e);
        } catch (Exception e) {
            System.err.println("Đã xảy ra lỗi không mong muốn trong quá trình đăng nhập cho người dùng: " + username);
            e.printStackTrace();
            throw e;
        }
        // Dòng này không bao giờ được gọi nếu đăng nhập thành công
        throw new RuntimeException("Đăng nhập thất bại");
    }
// ...

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
