import javax.swing.*;

public class Logout {
    public static void logout() {
        int option = JOptionPane.showConfirmDialog(null, "로그아웃 하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            System.exit(0); // 시스템 종료
        }
    }
}
