import javax.swing.JFrame;
import java.awt.*;

import javax.swing.SwingUtilities;
// import java.awt.event.*;

public class Main extends JFrame {
    // 클래스 선언 자리

    // 기본 프레임
    public Main() {
        setTitle("도서관 좌석 관리 시스템");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = getContentPane();
        c.setLayout(new FlowLayout());

        setSize(1000, 800);
        setLocationRelativeTo(null);
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