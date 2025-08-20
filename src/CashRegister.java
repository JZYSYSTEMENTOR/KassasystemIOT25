import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.TextArea;
import java.awt.Desktop.Action;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.event.*;


public class CashRegister implements ActionListener {    
    JFrame frame;
    JTextArea receipt;
    JButton kaffeButton;
    JButton nalleButton;
    JButton muggButton;
    JButton chipsButton;
    JButton vaniljYoghurtButton;
    JButton daimButton;
    JTextArea inputProductName;
    JTextArea inputCount;
    JButton addToReceiptButton;
    JButton payButton;

    // 28 min sista
    ArrayList<Product> allProducts = new ArrayList<>();
    
    // exempel på kaffe och daim 
    Product kaffe;
    Product daim;

    // När man klickar på en produktknapp
    // om klickat på kaffeButton, då lastClickProduct = kaffe
    // om klickat på kaffeButton, då lastClickProduct = daim
    // sätter inputProductName.setText till lastClickProduct.getName()
    Product lastClickProduct = null; // Denna kommer alltid att innehålla senaste klickade produkten
    double totalSumma = 0.0;

    int receiptNo = 0;

    // int receiptTotal = 0;
    private void addProducts(){
        kaffe = new Product();
        kaffe.setName("Kaffe");
        kaffe.setPrice(100);
        allProducts.add(kaffe);

        daim = new Product();
        daim.setName(("Daim"));
        daim.setPrice(5);
        allProducts.add(daim);
    }

    public CashRegister(){
        frame = new JFrame("IOT25 POS");

        createReceiptArea();
        createQuickButtonsArea();
        createAddArea();
        addProducts();

        frame.getContentPane().setBackground(Color.BLACK);
        frame.setSize(1000,800); 

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLayout(null);
    
        frame.setVisible(true);        
            

    }

    private void createAddArea(){

        inputProductName = new JTextArea();
        inputProductName.setBounds(20,650,300,30);
        inputProductName.setFont(new Font("Arial Black", Font.BOLD, 18));
        frame.add(inputProductName);

        JLabel label = new JLabel("Antal");
        label.setBounds(340,625,300,30);
        label.setForeground(Color.WHITE);
        frame.add(label);

        inputCount = new JTextArea();
        inputCount.setBounds(330,650,50,30);
        inputCount.setFont(new Font("Arial Black", Font.BOLD, 18));
        frame.add(inputCount);


        addToReceiptButton = new JButton("Add");
        addToReceiptButton.setBounds(400,640,70,50);
        addToReceiptButton.setFont(new Font("Arial Black", Font.PLAIN, 14));
        frame.add(addToReceiptButton);

        // om man kilckar på "Add" button
        addToReceiptButton.addActionListener(event -> {
            // om man inte har valt någon produkt, då händer det ingenting
            if(lastClickProduct == null) {
                return;
            }

            String count = inputCount.getText();
            Double total = (double)lastClickProduct.getPrice() * Double.parseDouble(count);
            totalSumma = totalSumma + total;
            if(count.isEmpty()) {
                return;
            }
            //annars visar man nedanstående text
            //receipt.append("Nu klickade man på add\n");
            receipt.append(lastClickProduct.getName() 
                    + "                   " + count + " *     "
                    + lastClickProduct.getPrice()
                    + " =   " + total +"  \n");

            inputProductName.setText("");
            lastClickProduct = null;
        });

        payButton = new JButton("Pay");
        payButton.setBounds(480,640,70,50);
        payButton.setFont(new Font("Arial Black", Font.PLAIN, 14));
        payButton.addActionListener(event -> {
            receipt.append("Total                                        ------\n");
            receipt.append("                                             " + totalSumma +"\n");
            receipt.append("TACK FÖR DITT KÖP\n");
            startNewReceipt();
        });
        frame.add(payButton);


    }

    private void createQuickButtonsArea() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEADING, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.setBackground(Color.green);
        panel.setPreferredSize(new Dimension(600, 600));

        kaffeButton = new JButton("Kaffe");
        panel.add(kaffeButton);
        // // alternativ lösning 1 
        // kaffeButton.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {

        //     }
        // });

        // alternativ lösning 2 - rekommenderad
        // Denna typ av lösning kommer vi även utnyttja under C++ kursen - lambda
        // När någon klickar på kaffeButton -> skickas till swing -> kör denna kod!
        // kaffeButton.addActionListener(event -> {
        //     lastClickProduct = kaffe;
        //     inputProductName.setText(lastClickProduct.getName());
        // });
        kaffeButton.addActionListener(event -> {
            System.out.println("Button clicked!");
            System.out.println("kaffe = " + kaffe);
            System.out.println("inputProductName = " + inputProductName);

            lastClickProduct = kaffe;
            inputProductName.setText(lastClickProduct.getName());
        });


        
        daimButton = new JButton("Daim");
        panel.add(daimButton);
        daimButton.addActionListener(event -> {
            lastClickProduct = daim;
            inputProductName.setText(lastClickProduct.getName());
        });

        nalleButton = new JButton("Nalle");
        panel.add(nalleButton);

        muggButton = new JButton("Mugg");
        panel.add(muggButton);

        chipsButton = new JButton("Chips");
        panel.add(chipsButton);

        vaniljYoghurtButton = new JButton("Yoghurt");
        panel.add(vaniljYoghurtButton);
        
        panel.setBounds(0, 0, 600, 600);

        frame.add(panel);
    }

    private void createReceiptArea() {
        receipt = new JTextArea();
        receipt.setSize(400,400); 
        receipt.setLineWrap(true);
        receipt.setEditable(false);
        receipt.setVisible(true);
        receipt.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        JScrollPane scroll = new JScrollPane (receipt);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setBounds(600, 0, 400, 1000);

        frame.add(scroll);    
    }
    // Kvitto är enbart en text area
    public void startNewReceipt() {
        totalSumma = 0;
        lastClickProduct = null;
        receiptNo++;
        // förslag på lösning till att rensar fönstret, ni får komma på andra sätt också!
        // alternativ 1 - TIMER
        // alternativ 2 - Message Box https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html
        JOptionPane.showMessageDialog(frame, "Klicka för att starta nästa kvitto!");
        // alternativ 3 - SLEEP 3 sekunder 
        // alternativ 4 - lägg till en knapp

        receipt.setText("");

        receipt.append("                     STEFANS SUPERSHOP\n");
        receipt.append("----------------------------------------------------\n");
        receipt.append("\n");
        receipt.append("Kvittonummer: " + receiptNo +"        Datum: 2024-09-01 13:00:21\n");
        // receipt.append("----------------------------------------------------\n");
        // receipt.append("Kaffe Gevalia           5 *     51.00    =   255.00  \n");
        // receipt.append("Nallebjörn              1 *     110.00   =   110.00  \n");
        // receipt.append("Total                                        ------\n");
        // receipt.append("                                             306.00\n");
        // receipt.append("TACK FÖR DITT KÖP\n");

    }            

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
}
