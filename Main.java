import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.HashMap;


public class Main extends JFrame{

    // login 변수 추가
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private Map<String, String> userMap;

    public Main() {

        // 기본 프레임
        setTitle("도서관 좌석 관리 시스템");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = getContentPane();
        c.setLayout(new FlowLayout());

        setSize(1000, 800);
        setLocationRelativeTo(null);
        setVisible(true);


        // signup/in 구현
        userMap = new HashMap<>();
        userMap.put("사용자1", "비밀번호1");
        userMap.put("사용자2", "비밀번호2");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("사용자명:");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("비밀번호:");
        passwordField = new JPasswordField();

        loginButton = new JButton("로그인");
        registerButton = new JButton("회원가입");

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(registerButton);

        add(panel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (userMap.containsKey(username) && userMap.get(username).equals(password)) {
                    JOptionPane.showMessageDialog(Main.this, "로그인 성공!");
                } else {
                    JOptionPane.showMessageDialog(Main.this, "잘못된 사용자명 또는 비밀번호입니다.");
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(Main.this, "사용자명과 비밀번호를 모두 입력해주세요.");
                } else if (userMap.containsKey(username)) {
                    JOptionPane.showMessageDialog(Main.this, "이미 존재하는 사용자명입니다.");
                } else {
                    userMap.put(username, password);
                    JOptionPane.showMessageDialog(Main.this, "회원가입이 완료되었습니다.");
                }
            }
        });

        setVisible(true);

    }

    

    public static void main(String[] args) {

        // 메인 클래스 실행
        Main main = new Main();

        // 좌석관리 실행
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SeatMa();
            }
        });

        // 나머지 클래스 실행 자리
    }
}