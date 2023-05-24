import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

public class CustomerManage extends JFrame {

    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField memberIdField;
    private JTextField seatNumberField;
    private JTextField nameField;
    private JTextField phoneNumberField;
    private JTextField registrationTimeField;
    private JTextField expirationTimeField;

    public CustomerManage() {
        setTitle("Customer Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        // 테이블 모델 생성
        tableModel = new DefaultTableModel();
        tableModel.addColumn("학번");
        tableModel.addColumn("좌석번호");
        tableModel.addColumn("이름");
        tableModel.addColumn("전화번호");
        tableModel.addColumn("등록시간");
        tableModel.addColumn("만료시간");

        // 테이블 생성
        table = new JTable(tableModel);

        // 테이블 셀 렌더러 설정
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
                        column);

                String expirationTime = (String) tableModel.getValueAt(row, 5);

                if (expirationTime != null) {
                    long currentTime = System.currentTimeMillis();
                    long expiration = parseTime(expirationTime);

                    if (expiration - currentTime <= 10 * 60 * 1000) { // 10분 = 10 * 60 * 1000 밀리초
                        component.setBackground(Color.RED);
                    } else {
                        component.setBackground(table.getBackground());
                    }
                } else {
                    component.setBackground(table.getBackground());
                }

                return component;
            }
        };

        // 테이블 셀 렌더러 적용
        for (int i = 0; i < tableModel.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }

        // 입력 필드 생성
        memberIdField = new JTextField(10);
        seatNumberField = new JTextField(10);
        nameField = new JTextField(10);
        phoneNumberField = new JTextField(10);
        registrationTimeField = new JTextField(10);
        expirationTimeField = new JTextField(10);

        // 버튼 생성
        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String memberId = memberIdField.getText();
                String seatNumber = seatNumberField.getText();
                String name = nameField.getText();
                String phoneNumber = phoneNumberField.getText();

                // 현재 시간을 등록시간으로 설정
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String registrationTime = sdf.format(new Date());

                // 등록시간에 4시간 추가하여 만료시간 계산
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date(parseTime(registrationTime)));
                calendar.add(Calendar.HOUR_OF_DAY, 4);
                String expirationTime = sdf.format(calendar.getTime());

                Vector<String> row = new Vector<String>();
                row.add(memberId);
                row.add(seatNumber);
                row.add(name);
                row.add(phoneNumber);
                row.add(registrationTime);
                row.add(expirationTime);

                tableModel.addRow(row);

                clearFields();
            }
        });

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                int selectedColumn = table.getSelectedColumn();

                if (selectedRow >= 0 && selectedColumn >= 0) {
                    // 선택된 행에서 기존 데이터를 가져옵니다.
                    String currentValue = (String) tableModel.getValueAt(selectedRow, selectedColumn);

                    // 업데이트된 데이터를 가져옵니다.
                    String updatedValue = "";

                    // 선택된 열에 따라 업데이트할 필드를 확인합니다.
                    switch (selectedColumn) {
                        case 0: // 학번
                            updatedValue = memberIdField.getText();
                            break;
                        case 1: // 좌석번호
                            updatedValue = seatNumberField.getText();
                            break;
                        case 2: // 이름
                            updatedValue = nameField.getText();
                            break;
                        case 3: // 전화번호
                            updatedValue = phoneNumberField.getText();
                            break;
                        case 4: // 등록시간
                            updatedValue = registrationTimeField.getText();
                            break;
                        case 5: // 만료시간
                            updatedValue = expirationTimeField.getText();
                            break;
                        default:
                            break;
                    }

                    // 업데이트된 값으로 선택된 셀을 수정합니다.
                    tableModel.setValueAt(updatedValue, selectedRow, selectedColumn);

                    clearFields();
                }
            }
        });

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    tableModel.removeRow(selectedRow);
                }
            }
        });

        JButton findButton = new JButton("Find");
        findButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String searchValue = JOptionPane.showInputDialog("찾을 값을 입력하세요:");

                if (searchValue != null && !searchValue.isEmpty()) {
                    int columnCount = table.getColumnCount();
                    int rowCount = table.getRowCount();
                    boolean found = false;

                    // 기존에 선택되어 있던 셀 초기화
                    table.clearSelection();

                    for (int row = 0; row < rowCount; row++) {
                        for (int column = 0; column < columnCount; column++) {
                            String cellValue = (String) table.getValueAt(row, column);
                            if (cellValue != null && cellValue.equalsIgnoreCase(searchValue)) {
                                // 해당 셀 선택
                                table.changeSelection(row, column, false, false);
                                // 선택한 셀의 배경색 변경
                                table.getCellRenderer(row, column)
                                        .getTableCellRendererComponent(table, null, false, false, row, column)
                                        .setBackground(Color.BLUE);
                                found = true;
                            }
                        }
                    }

                    if (!found) {
                        JOptionPane.showMessageDialog(null, "찾을 값이 없습니다.");
                    }
                }
            }
        });

        // 레이아웃 설정
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(0, 2));
        inputPanel.add(new JLabel("학번:"));
        inputPanel.add(memberIdField);
        inputPanel.add(new JLabel("좌석번호:"));
        inputPanel.add(seatNumberField);
        inputPanel.add(new JLabel("이름:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("전화번호:"));
        inputPanel.add(phoneNumberField);
        inputPanel.add(addButton);
        inputPanel.add(updateButton);
        inputPanel.add(deleteButton);
        inputPanel.add(findButton);

        panel.add(inputPanel, BorderLayout.SOUTH);
        setContentPane(panel);
    }

    private void clearFields() {
        memberIdField.setText("");
        seatNumberField.setText("");
        nameField.setText("");
        phoneNumberField.setText("");
        registrationTimeField.setText("");
        expirationTimeField.setText("");
    }

    private long parseTime(String timeString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(timeString);
            return date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                CustomerManage app = new CustomerManage();
                app.setVisible(true);
            }
        });
    }
}