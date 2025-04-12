import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            return true;
        } else {
            return false;
        }
    }
}

class ATMGUI extends JFrame implements ActionListener {
    private BankAccount account;
    private JTextField balanceField;
    private JTextField amountField;
    private JTextArea outputArea;

    private JButton depositButton;
    private JButton withdrawButton;

    public ATMGUI(BankAccount account) {
        this.account = account;

        setTitle("ATM Machine");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        JLabel balanceLabel = new JLabel("Balance:");
        balanceField = new JTextField(String.format("%.2f", account.getBalance()));
        balanceField.setEditable(false);

        JLabel amountLabel = new JLabel("Amount:");
        amountField = new JTextField();

        depositButton = new JButton("Deposit");
        withdrawButton = new JButton("Withdraw");

        depositButton.addActionListener(this);
        withdrawButton.addActionListener(this);

        outputArea = new JTextArea(3, 20);
        outputArea.setEditable(false);

        panel.add(balanceLabel);
        panel.add(balanceField);
        panel.add(amountLabel);
        panel.add(amountField);
        panel.add(depositButton);
        panel.add(withdrawButton);
        panel.add(new JLabel("Output:"));
        panel.add(new JScrollPane(outputArea));

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (command.equals("Deposit")) {
                account.deposit(amount);
                outputArea.setText("Deposited: $" + amount);
            } else if (command.equals("Withdraw")) {
                boolean success = account.withdraw(amount);
                if (success) {
                    outputArea.setText("Withdrew: $" + amount);
                } else {
                    outputArea.setText("Insufficient balance!");
                }
            }
            balanceField.setText(String.format("%.2f", account.getBalance()));
        } catch (NumberFormatException ex) {
            outputArea.setText("Please enter a valid amount.");
        }
    }

    public static void main(String[] args) {
        BankAccount account = new BankAccount(1000.00);
        new ATMGUI(account);
    }
}
