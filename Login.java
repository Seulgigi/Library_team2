import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class Login extends JFrame {
    private JTextField memberIdField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private Map<String, String> userMap;

    public Login(Map<String, String> userMap) {
        this.userMap = userMap;

        setTitle("로그인");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = getContentPane();
        c.setLayout(new FlowLayout());

        setSize(300, 200);
        setLocationRelativeTo(null);
        setVisible(true);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel memberIdLabel = new JLabel("학번:");
        memberIdField = new JTextField();
        JLabel passwordLabel = new JLabel("비밀번호:");
        passwordField = new JPasswordField();

        loginButton = new JButton("로그인");
        registerButton = new JButton("회원가입");

        panel.add(memberIdLabel);
        panel.add(memberIdField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(registerButton);

        add(panel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String memberId = memberIdField.getText();
                String password = new String(passwordField.getPassword());

                if (userMap.containsKey(memberId) && userMap.get(memberId).equals(password)) {
                    if (memberId.equals("admin")) {
                        JOptionPane.showMessageDialog(Login.this, "관리자 계정 로그인 성공!");
                        dispose();
                        openCustomerManage();
                    } else {
                        JOptionPane.showMessageDialog(Login.this, "로그인 성공!");
                        dispose();
                        openSeatManagement(memberId); // Open seat management after successful login
                    }
                } else {
                    JOptionPane.showMessageDialog(Login.this, "잘못된 사용자명 또는 비밀번호입니다.");
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String memberId = memberIdField.getText();
                String password = new String(passwordField.getPassword());

                if (memberId.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(Login.this, "사용자명과 비밀번호를 모두 입력해주세요.");
                } else if (userMap.containsKey(memberId)) {
                    JOptionPane.showMessageDialog(Login.this, "이미 존재하는 사용자명입니다.");
                } else {
                    userMap.put(memberId, password);
                    JOptionPane.showMessageDialog(Login.this, "회원가입이 완료되었습니다.");
                }
            }
        });

        setVisible(true);
    }

    private void openCustomerManage() {
        CustomerManage customermanage = new CustomerManage();
        customermanage.setVisible(true);
    }

    private void openSeatManagement(String ID) {
        // Open seat management logic goes here
        SeatMa seatManagement = new SeatMa(ID);
        seatManagement.setVisible(true);
    }
}