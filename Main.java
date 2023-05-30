import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.HashMap;

public class Main extends JFrame {

    private void openSeatManagement(String ID) {
        // Open seat management logic goes here
        SeatMa seatManagement = new SeatMa(ID);
        seatManagement.setVisible(true);
    }

    public static void main(String[] args) {
        // 메인 클래스 실행
        Main main = new Main();
        CustomerManage customermanage = new CustomerManage();

        Map<String, String> userMap = new HashMap<>();
        userMap.put("사용자1", "비밀번호1");
        userMap.put("사용자2", "비밀번호2");

        // 관리자 접근 계정 추가
        String adminUsername = "admin";
        String adminPassword = "adminPassword";
        userMap.put(adminUsername, adminPassword);

        Login login = new Login(userMap);
    }
}