import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

public class CustomerManage extends JFrame {

    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField memberIdField;
    private JTextField seatNumberField;
    private JTextField registrationTimeField;

    public CustomerManage() {
        setTitle("Customer Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);

        // 테이블 모델 생성
        tableModel = new DefaultTableModel();
        tableModel.addColumn("학번");
        tableModel.addColumn("좌석번호");
        tableModel.addColumn("등록시간");

        // 테이블 생성
        table = new JTable(tableModel);

        // 입력 필드 생성
        memberIdField = new JTextField(10);
        seatNumberField = new JTextField(10);
        registrationTimeField = new JTextField(10);

        // 버튼 생성
        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String memberId = memberIdField.getText();
                String seatNumber = seatNumberField.getText();

                // 현재 시간을 등록시간으로 설정
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String registrationTime = sdf.format(new Date());

                Vector<String> row = new Vector<String>();
                row.add(memberId);
                row.add(seatNumber);
                row.add(registrationTime);

                tableModel.addRow(row);

                saveDataToFile(); // 데이터 파일에 저장

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
                        case 2: // 등록시간
                            updatedValue = registrationTimeField.getText();
                            break;
                        default:
                            break;
                    }

                    // 업데이트된 값으로 선택된 셀을 수정합니다.
                    tableModel.setValueAt(updatedValue, selectedRow, selectedColumn);

                    saveDataToFile(); // 데이터 파일에 저장

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
                    saveDataToFile(); // 데이터 파일에 저장
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
        inputPanel.add(addButton);
        inputPanel.add(updateButton);
        inputPanel.add(deleteButton);
        inputPanel.add(findButton);

        panel.add(inputPanel, BorderLayout.SOUTH);
        setContentPane(panel);

        // 데이터 로드
        loadDataFromFile();
    }

    private void clearFields() {
        memberIdField.setText("");
        seatNumberField.setText("");
        registrationTimeField.setText("");
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

    private void saveDataToFile() {
        try {
            FileWriter writer = new FileWriter("data.txt");
            BufferedWriter bufferWriter = new BufferedWriter(writer);

            int rowCount = tableModel.getRowCount();
            int columnCount = tableModel.getColumnCount();

            for (int row = 0; row < rowCount; row++) {
                for (int column = 0; column < columnCount; column++) {
                    String cellValue = (String) tableModel.getValueAt(row, column);
                    bufferWriter.write(cellValue);
                    bufferWriter.write(",");
                }
                bufferWriter.newLine();
            }

            bufferWriter.close();
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void loadDataFromFile() {
        try {
            FileReader reader = new FileReader("data.txt");
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    Vector<String> row = new Vector<String>();
                    row.add(data[0]);
                    row.add(data[1]);
                    row.add(data[2]);
                    tableModel.addRow(row);
                }
            }

            bufferedReader.close();
            reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
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