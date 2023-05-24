import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SeatMa extends JFrame {

    public SeatMa() {
        super("Seat Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1800, 1000);

        // Frame에 GridLayout 적용
        GridLayout gridLayout = new GridLayout(3, 8);
        gridLayout.setHgap(50); // 열 사이의 가로 간격 설정
        gridLayout.setVgap(50); // 행 사이의 세로 간격 설정
        setLayout(gridLayout);

        // 각 공간에 버튼을 추가한 JPanel 생성하여 Frame에 추가
        for (int i = 1; i < 25; i++) {
            addSeatButtons((i * 10 - 9), i * 10, 2, 100);
        }

        setVisible(true);
    }

    // 범위에 해당하는 버튼을 추가하는 메소드
    private void addSeatButtons(int start, int end, int columns, int height) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.GRAY);

        // GridLayout으로 버튼을 2열로 배치
        GridLayout gridLayout = new GridLayout(0, columns);
        panel.setLayout(gridLayout);
        gridLayout.setHgap(10); // 버튼 사이의 가로 간격 설정
        gridLayout.setVgap(10); // 버튼 사이의 세로 간격 설정

        // 버튼 추가
        for (int i = start; i <= end; i++) {
            JButton button = new JButton("" + i);
            button.setFont(button.getFont().deriveFont(Font.PLAIN, 16)); // 버튼의 폰트 크기 조절
            button.setMargin(new Insets(10, 10, 10, 10)); // 버튼의 여백 조절
            button.setBackground(Color.WHITE); // 버튼의 배경색 설정

            // 시간바로 사용할 커스텀 버튼 생성
            CustomButton customButton = new CustomButton(i, button);

            button.add(customButton); // 버튼에 시간바 추가

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (button.getBackground() != Color.YELLOW) {
                        int choice = JOptionPane.showConfirmDialog(button, "좌석을 선택하시겠습니까?", "좌석 선택",
                                JOptionPane.YES_NO_OPTION);
                        if (choice == JOptionPane.YES_OPTION) {
                            button.setBackground(Color.YELLOW); // 버튼 색상 변경
                            customButton.startTimer(); // 시간바 타이머 시작
                        }
                    } else {
                        int choice = JOptionPane.showConfirmDialog(button, "퇴실하시겠습니까?", "퇴실 확인",
                                JOptionPane.YES_NO_OPTION);
                        if (choice == JOptionPane.YES_OPTION) {
                            button.setBackground(Color.WHITE); // 버튼 색상 초기화
                            customButton.stopTimer(); // 시간바 타이머 중지
                        }
                    }
                }
            });

            panel.add(button);
        }

        // 패널의 크기 설정
        Dimension panelSize = new Dimension(panel.getPreferredSize().width, height);
        panel.setPreferredSize(panelSize);

        add(panel);
    }

    // 커스텀 버튼 클래스
    private class CustomButton extends JPanel {
        private JLabel label;
        private JProgressBar progressBar;
        private Timer timer;
        private int remainingTime;
        private JButton button;

        public CustomButton(int number, JButton button) {
            setLayout(new BorderLayout());
            this.button = button;

            label = new JLabel("" + number, SwingConstants.CENTER);
            label.setFont(label.getFont().deriveFont(Font.BOLD, 16)); // 레이블의 폰트 설정
            add(label, BorderLayout.NORTH);

            progressBar = new JProgressBar(0, 5 * 60); // 5분을 초 단위로 설정
            progressBar.setStringPainted(false); // 바에 텍스트 표시하지 않음
            progressBar.setPreferredSize(new Dimension(getWidth(), 10)); // 바의 크기 설정
            progressBar.setBackground(new Color(0, 0, 0, 0)); // 바의 배경색을 투명으로 설정
            progressBar.setForeground(Color.RED); // 바의 전경색 설정
            progressBar.setVisible(false); // 타이머를 처음에는 숨김
            add(progressBar, BorderLayout.CENTER);
        }

        public void startTimer() {
            remainingTime = 5 * 60; // 남은 시간을 5분으로 초기화
            progressBar.setValue(remainingTime); // 바 초기화
            progressBar.setVisible(true); // 바 표시

            timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    remainingTime--;

                    if (remainingTime <= 0) {
                        timer.stop(); // 타이머 중지
                        button.setBackground(Color.WHITE); // 버튼 색상 초기화
                        progressBar.setVisible(false); // 바 숨김
                        progressBar.setValue(5 * 60); // 바 초기화
                        JOptionPane.showMessageDialog(button, "퇴실되었습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        progressBar.setValue(remainingTime); // 바 업데이트
                    }
                }
            });

            timer.start(); // 타이머 시작
        }

        public void stopTimer() {
            if (timer != null && timer.isRunning()) {
                timer.stop(); // 타이머 중지
            }
            progressBar.setVisible(false); // 바 숨김
            progressBar.setValue(5 * 60); // 바 초기화
        }
    }

    // public static void main(String[] args) {
    // SwingUtilities.invokeLater(new Runnable() {
    // @Override
    // public void run() {
    // new SeatMa();
    // }
    // });
    // }
}
