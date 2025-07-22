/**
 * 
 *                   CTCC-0513
 *      DATA STRUCTURES AND ALGORITHM ANALYSIS
 * 
 * 
 *              GROUP 4 | CASE STUDY
 * 
 * 
 *  PRODUX: AN ADT-DRIVEN INVENTORY MANAGEMENT SYSTEM
 *       (Stack - Queue - LinkedList - HashMap)
 * 
 * 
 *  LEADER: 
 *      Barata, Nicko James E.
 * 
 *  MEMBERS:
 *      Amposta, Reinald Luis
 *      Bagtas, Miguel Grant V.
 *      Tranate, Jheris V.* 
 * 
 * 
 * 
 *  DESCRIPTION:
 *  Produx is an inventory management system built on fundamental
 *  abstract data types (ADTs) like stacks, queues, linked lists, 
 *  and hash maps. It is designed to handle inventory  operations 
 *  efficiently, offering features such as:
 * 
 *      - Inventory Tracking | LinkedList
 *      - Undo/Redo System | Stack
 *      - Real-Time Profit Monitoring | Queue
 *      - Dynamic Dashboard Data | HashMap
 * 
 *  This project integrates Abstract Data Type structures concepts 
 *  into real-world functionalities, demonstrating how algorithm 
 *  analysis and design can solve practical problems effectively.
 * 
 */



import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Arc2D;
import java.awt.geom.RoundRectangle2D;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;









/*
 * DashboardHashMap.java
 *
 * This class implements a custom HashMap to store and manage key-value pairs for the dashboard data. 
 * It holds crucial information such as "Products Sold", "Revenue", and "In-Stock" data. The values 
 * are dynamically updated based on user actions in the application, ensuring real-time reflection of 
 * changes in the dashboard panel.
 */
class DashboardHashMap {
    private Entry[] table;
    private int capacity = 6; // Default capacity
    private int size = 0;

    public DashboardHashMap() {
        table = new Entry[capacity];
    }

    private static class Entry {
        // entry class will store the key-value pair
        String key;
        Object value;
        Entry next; // For handling collisions using chaining

        Entry(String key, Object value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    private int getIndex(String key) {
        // hash function to get the index
        System.out.println(key + ": " + key.hashCode() % capacity);
        return Math.abs(key.hashCode()) % capacity;
    }
    
    public void put(String key, Object value) {
        // custom put method
        int index = getIndex(key);
        Entry newEntry = new Entry(key, value);

        // if the index is empty, directly place the new entry
        if (table[index] == null) {
            table[index] = newEntry;
        } else {
            // handle collision using chaining
            Entry current = table[index];
            while (current != null) {
                if (current.key.equals(key)) {
                    // if the key already exists, update the value
                    current.value = value;
                    return;
                }
                current = current.next;
            }
            // if key does not exist, add the new entry to the chain
            newEntry.next = table[index];
            table[index] = newEntry;
        }
        size++;
    }

    public Object get(String key) {
        // custom get method
        int index = getIndex(key);
        Entry current = table[index];
        
        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return null; // Key not found
    }

    public int size() {
        return size;
    }

    public void printMap() {
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                Entry current = table[i];
                while (current != null) {
                    System.out.println(current.key + ": " + current.value);
                    current = current.next;
                }
            }
        }
    }
}




public class Main implements ActionListener {
    // frame and panel components
    JFrame frame;
    
    // declare login components
    private JPanel panelLogin;
    private JPanel panelBackground;
    private JLabel labelProdux;
    private JLabel labelCompanyID;
    private JLabel labelPassword;
    private JLabel labelMessage;
    private JTextField fieldCompanyID;
    private JPasswordField fieldPassword;
    private JButton buttonLogIn;
    private JButton buttonRegister;
    private JCheckBox showPassword;
    
    // declare navigation components
    private JPanel panelNavigation;
    private JLabel appNameLabel;
    private JPanel panelHeader;
    private JLabel companyName;
    private JButton dashboardNavButton; 
    private JButton inventoryNavButton; 
    private JButton historyNavButton;
    private JButton accountNavButton; 
    private JButton aboutNavButton;
    
    // declare dashboard components
    private JPanel panelDashboard;
    private JPanel panelProductSold;
    private JPanel panelRevenue;
    private JPanel panelInStock;
    private JPanel panelProfitChart;
    private JPanel panelPieChart;
    private JLabel labelProductSold;
    private JLabel labelRevenue;
    private JLabel labelInStock;
    private JLabel labelProductSoldValue;
    private JLabel labelRevenueValue;
    private JLabel labelInStockValue;    
    private BarGraphPanel profitGraph;
    private PieChartPanel pieGraph;
    
    // declare inventory components
    private JPanel panelInventory;
    private JPanel panelInventoryTable;
    private JPanel panelDetails;
    private JLabel labelHeaderTable; 
    private JLabel labelDetails;
    private JLabel labelDetailProduct; 
    private JLabel labelDetailCategory;
    private JLabel labelDetailCost; 
    private JLabel labelDetailPrice;
    private JLabel labelDetailQuantity;
    private JTextField fieldDetailProduct; 
    private JTextField fieldDetailCategory;
    private JTextField fieldDetailCost; 
    private JTextField fieldDetailPrice; 
    private JTextField fieldDetailQuantity;
    private JButton buttonAdd;
    private JButton buttonSold;
    private JButton buttonUpdate;
    private JButton buttonDelete;
    private JButton buttonUndo;
    private JButton buttonRedo;
    private JTable inventoryTable;
    private DefaultTableModel inventoryTableModel;
    
    // declare history components
    private JPanel panelHistory;
    private JPanel panelHistoryTable;
    private JTable historyTable;
    private DefaultTableModel historyTableModel;
    
    // declare account panel
    private JPanel panelAccount;
    private JLabel labelCompanyName;
    private JLabel labelCompanyAddress;
    private JLabel labelCompanyContacts;
    private JLabel labelCompanyEmail;
    private JButton buttonLogOut;
    
    // declare about panel
    private JPanel panelAbout;
    
    // STACK: history, undo and redo
    Stack historyStack = new Stack();
    Stack undoStack = new Stack();
    Stack redoStack = new Stack();   
    
    // QUEUE: Profit Data
    ProfitQueue profits = new ProfitQueue(7);
    
    // LINKED LIST: Product List
    ProductLinkedList productList = new ProductLinkedList();
    
    // HASHMAP: dashboard data
    DashboardHashMap dashboardData = new DashboardHashMap();
    
    // data: accounts
    HashMap<String, String> accounts = new HashMap<String, String>();
    ArrayList<String> existingAccounts = new ArrayList<String>();
    
    // data: current user
    String companyID = "";
    String password = "";
    
    // color objects
    private final Color backgroundColor = new Color(209, 222, 222);
    private final Color navigationBgColor = new Color(71, 93, 105);
    private final Color defaultButtonColor = new Color(44, 54, 59);
    private final Color selectedButtonColor = new Color(102, 150, 134);
    private final Color highlightColor = new Color(200, 200, 200); // Lighter color for highlight
    private final Color loginPanelBgColor = new Color(54, 69, 77);
    private final Color textColor = new Color(228, 239, 240);
    private final Color cardColor = new Color(250, 255, 253);

    // other properties
    private final int WIDTH = 900;
    private final int HEIGHT = 600;    
    private int hoveredRow = -1; 
    String headerText;
    
    
    
    
    public Main() {
        // initialize data and components
        initializeBackgroundPanel();
        initializeNavigationPanel();
        initializeHeaderPanel();
        initializeDashboardPanel();
        initializeInventoryPanel();
        initializeHistoryPanel();
        initializeAboutPanel();
        initializeAccountPanel();
        initializeLoginPanel();
        initializeFrame();
        
        // bypass login feature for direct demonstration of ADTs
        initializeDemoMode();
    }
    
    
    
    
    public void initializeFrame() {
        // frame properties
        frame = new JFrame("Produx: A Business Inventory Management System");
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocation(500, 175);
        frame.setLocationRelativeTo(null);
        frame.setUndecorated(true);
        frame.setShape(new RoundRectangle2D.Double(0, 0, WIDTH, HEIGHT, 25, 25));
        frame.setVisible(true);
        frame.setLayout(null);
        frame.setResizable(false);
        
        frame.setBackground(new Color(0, 0, 0));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // add panels to frame
        frame.add(panelLogin);
        frame.add(panelBackground);
        frame.add(panelNavigation);
        frame.add(panelHeader);
        frame.add(panelDashboard);
        frame.add(panelInventory);
        frame.add(panelHistory);
        frame.add(panelAccount);
        frame.add(panelAbout);
        
        // set initial visibility of panels
        panelBackground.setVisible(false);
        panelLogin.setVisible(false);
        panelNavigation.setVisible(true);
        panelHeader.setVisible(true);
        panelDashboard.setVisible(true);
        panelInventory.setVisible(false);
        panelHistory.setVisible(false);
    }
    
    
    
    
    public void initializeLoginPanel() {
        // PANEL: login
        panelLogin = new JPanel();
        panelLogin.setLayout(null);
        panelLogin.setBounds(WIDTH / 3, HEIGHT / 4 - 60, WIDTH / 3, HEIGHT / 2 + 50);
        panelLogin.setBackground(loginPanelBgColor);

        // LABEL: login
        labelProdux = new JLabel("LOG IN");
        labelProdux.setFont(new Font("Poppins", Font.BOLD, 34));
        labelProdux.setForeground(new Color(146, 222, 192));
        labelProdux.setHorizontalAlignment(SwingConstants.CENTER);
        labelProdux.setBounds(75, 15, 150, 50);
        panelLogin.add(labelProdux);

        // LABEL: company id
        labelCompanyID = new JLabel("Company ID:");
        labelCompanyID.setFont(new Font("Poppins", Font.BOLD, 14));
        labelCompanyID.setForeground(textColor);
        labelCompanyID.setBounds(47, 75, 100, 30);
        panelLogin.add(labelCompanyID);

        // FIELD: company id
        fieldCompanyID = new JTextField();
        fieldCompanyID.setFont(new Font("Lato", Font.PLAIN, 12));
        fieldCompanyID.setForeground(Color.BLACK);
        fieldCompanyID.setBackground(new Color(242, 245, 255));
        fieldCompanyID.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        fieldCompanyID.setBounds(47, 75 + 35, 200, 30);
        panelLogin.add(fieldCompanyID);

        // LABEL: password
        labelPassword = new JLabel("Password:");
        labelPassword.setFont(new Font("Poppins", Font.BOLD, 14));
        labelPassword.setForeground(textColor);
        labelPassword.setBounds(47, 75 + 80, 100, 30);
        panelLogin.add(labelPassword);

        // FIELD: password
        fieldPassword = new JPasswordField();
        fieldPassword.setFont(new Font("Lato", Font.PLAIN, 12));
        fieldPassword.setForeground(Color.BLACK);
        fieldPassword.setBackground(new Color(242, 245, 255));
        fieldPassword.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        fieldPassword.setBounds(47, 189, 200, 30);
        panelLogin.add(fieldPassword);

        // CHECKBOX: Show Password
        showPassword = new JCheckBox("Show Password");
        showPassword.setFont(new Font("Poppins", Font.PLAIN, 12));
        showPassword.setForeground(textColor);
        showPassword.setBackground(loginPanelBgColor);
        showPassword.setFocusPainted(false);
        showPassword.setBorderPainted(false);
        showPassword.setBounds(47, 222, 200, 30);
        showPassword.addActionListener(this);
        panelLogin.add(showPassword);

        // LABEL: login message
        labelMessage = new JLabel("");
        labelMessage.setFont(new Font("Poppins", Font.BOLD, 12));
        labelMessage.setForeground(textColor);
        labelMessage.setHorizontalAlignment(SwingConstants.CENTER);
        labelMessage.setBounds(0, 315, 300, 30);
        panelLogin.add(labelMessage);

        // BUTTON: log in
        buttonLogIn = new JButton("Log In");
        buttonLogIn.setFont(new Font("Lato", Font.BOLD, 16));
        buttonLogIn.setForeground(textColor);
        buttonLogIn.setBackground(selectedButtonColor);
        buttonLogIn.setFocusPainted(false);
        buttonLogIn.setBorderPainted(false);
        buttonLogIn.setBounds(47, 270, 95, 40);
        buttonLogIn.addActionListener(this);
        panelLogin.add(buttonLogIn);

        // BUTTON: register
        buttonRegister = new JButton("Register");
        buttonRegister.setFont(new Font("Lato", Font.PLAIN, 14));
        buttonRegister.setForeground(textColor);
        buttonRegister.setBackground(defaultButtonColor);
        buttonRegister.setFocusPainted(false);
        buttonRegister.setBorderPainted(true);
        buttonRegister.setBounds(152, 270, 95, 40);
        buttonRegister.addActionListener(this);
        panelLogin.add(buttonRegister);
    }
    
    
    public void initializeBackgroundPanel() {
        // PANEL: background
        panelBackground = new JPanel();
        panelBackground.setBounds(0, 0, WIDTH, HEIGHT);
        
        try {
            // Get the direct URL for the image on Google Drive
            // URL imageUrl = new URL("https://drive.google.com/uc?id=1QAW7vZKYc7QH6Gf-9TtkaJjpy7EeEGVb");
            // ImageIcon bgImageFile = new ImageIcon(imageUrl);
            // Image bgImage = bgImageFile.getImage();
            // Image scaledImage = bgImage.getScaledInstance(900, 600, Image.SCALE_SMOOTH);
            // ImageIcon background = new ImageIcon(scaledImage);
            // JLabel bgHandle = new JLabel(background);
            // bgHandle.setBounds(0, 0, 900, 600);
            // panelBackground.add(bgHandle);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
    
    public void initializeNavigationPanel() {
        // PANEL: navigation
        panelNavigation = new JPanel();
        panelNavigation.setLayout(null);
        panelNavigation.setBounds(0, 0, WIDTH / 5, HEIGHT);
        panelNavigation.setBackground(navigationBgColor);

        // LABEL: app name
        appNameLabel = new JLabel("PRODUX");
        appNameLabel.setFont(new Font("Poppins", Font.BOLD, 26));
        appNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        appNameLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        appNameLabel.setForeground(new Color(146, 222, 192));
        appNameLabel.setBounds(33, 0, 180, 80);
        panelNavigation.add(appNameLabel);

        // BUTTONS: navigation buttons (Using your createNavButton method)
        dashboardNavButton = createNavButton("Dashboard", 80, 2);
        inventoryNavButton = createNavButton("Inventory", 120, 14);
        historyNavButton = createNavButton("History", 160, 32);
        accountNavButton = createNavButton("Account", 200, 23);
        aboutNavButton = createNavButton("About", 240, 39);
    }
    
    
    private JButton createNavButton(String text, int yPosition, int rightMargin) {
        // set button properties
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFont(new Font("Poppins", Font.BOLD, 17)); 
        button.setAlignmentX(Component.CENTER_ALIGNMENT); 
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        button.setForeground(textColor); 

        // position the button based on y-position and right margin
        button.setBounds(0, yPosition, 180, 40); // yPosition dynamically set
        button.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, rightMargin));
        
        // add to navigation panel and action listener then return
        button.addActionListener(this);
        panelNavigation.add(button);
        return button;
    }
    
    
    private void setSelectedNavButton(JButton selectedButton) {
        JButton[] buttons = {dashboardNavButton, inventoryNavButton, historyNavButton, accountNavButton, aboutNavButton};
        for (JButton button : buttons) {
            if (button == selectedButton) {
                button.setForeground(loginPanelBgColor);
                button.setOpaque(true); 
                button.setBackground(new Color(137, 179, 167)); 
            } else {
                button.setForeground(textColor);
                button.setOpaque(false);
            }
        }
    }


    
    

    public void initializeHeaderPanel() {
        // PANEL: header
        panelHeader = new JPanel();
        panelHeader.setLayout(null);
        panelHeader.setBackground(new Color(230, 230, 230));
        panelHeader.setBounds(WIDTH / 5, 0, WIDTH - (WIDTH / 5), 50);
        
        // LABEL: company name
        companyName = new JLabel("Global Marketing Retail Company Ltd.");
        companyName.setFont(new Font("Poppins", Font.PLAIN, 18));
        companyName.setForeground(Color.GRAY);
        companyName.setHorizontalAlignment(SwingConstants.CENTER);
        companyName.setBounds(0, 0, 720, 50);
        panelHeader.add(companyName);
    }
    
    
    
    
    
    public void initializeDashboardPanel() {
        // PANEL: dashboard
        panelDashboard = new JPanel();
        panelDashboard.setLayout(null);
        panelDashboard.setBackground(backgroundColor);
        panelDashboard.setBounds(WIDTH / 5, 50, WIDTH - (WIDTH / 5), HEIGHT);
        panelDashboard.setVisible(false);
        
        // PANEL: product sold
        panelProductSold = new JPanel();
        panelProductSold.setLayout(null);
        panelProductSold.setBackground(cardColor);
        panelProductSold.setBackground(new Color(168, 96, 219, 200));
        panelProductSold.setBounds(65, 75, 150, 100);
        panelDashboard.add(panelProductSold);
        
        // LABEL: product sold
        labelProductSold = new JLabel("Product Sold");
        labelProductSold.setFont(new Font("Poppins", Font.BOLD, 20));        
        labelProductSold.setHorizontalAlignment(SwingConstants.CENTER);
        labelProductSold.setForeground(textColor);
        labelProductSold.setBackground(new Color(168, 96, 219, 250));
        labelProductSold.setOpaque(true);
        labelProductSold.setBounds(0, 0, 150, 40);
        panelProductSold.add(labelProductSold);
        
        // LABEL: products sold (Value)
        labelProductSoldValue = new JLabel(String.valueOf(dashboardData.get("Products Sold")));
        labelProductSoldValue.setFont(new Font("Poppins", Font.BOLD, 32));
        labelProductSoldValue.setHorizontalAlignment(SwingConstants.CENTER);
        labelProductSoldValue.setForeground(textColor);
        labelProductSoldValue.setBounds(0, 47, 150, 40);
        panelProductSold.add(labelProductSoldValue);
        
        
        // PANEL: revenue
        panelRevenue = new JPanel();
        panelRevenue.setLayout(null);
        panelRevenue.setBackground(cardColor);
        panelRevenue.setBackground(new Color(96, 219, 116, 200));
        panelRevenue.setBounds(275, 75, 150, 100);
        panelDashboard.add(panelRevenue);
        
        // LABEL: revenue
        labelRevenue = new JLabel("Revenue");
        labelRevenue.setFont(new Font("Poppins", Font.BOLD, 20));
        labelRevenue.setHorizontalAlignment(SwingConstants.CENTER);
        labelRevenue.setForeground(textColor);
        labelRevenue.setBackground(new Color(96, 219, 116, 250));
        labelRevenue.setOpaque(true);
        labelRevenue.setBounds(0, 0, 150, 40);
        panelRevenue.add(labelRevenue);
        
        // LABEL: revenue (value)
        labelRevenueValue = new JLabel("$"+String.valueOf(dashboardData.get("Revenue")));
        labelRevenueValue.setFont(new Font("Poppins", Font.BOLD, 28));
        labelRevenueValue.setHorizontalAlignment(SwingConstants.CENTER);
        labelRevenueValue.setForeground(textColor);
        labelRevenueValue.setBounds(0, 47, 150, 40);
        panelRevenue.add(labelRevenueValue);
        
        
        // PANEL: in-stock
        panelInStock = new JPanel();
        panelInStock.setLayout(null);
        panelInStock.setBackground(cardColor);
        panelInStock.setBackground(new Color(90, 166, 242, 200));
        panelInStock.setBounds(485, 75, 150, 100);
        panelDashboard.add(panelInStock);
        
        // LABEL: in-stock
        labelInStock = new JLabel("In-Stock");
        labelInStock.setFont(new Font("Poppins", Font.BOLD, 20));
        labelInStock.setHorizontalAlignment(SwingConstants.CENTER);
        labelInStock.setForeground(textColor);
        labelInStock.setBackground(new Color(90, 166, 242, 250));
        labelInStock.setOpaque(true);
        labelInStock.setBounds(0, 0, 150, 40);        
        panelInStock.add(labelInStock);
        
        // LABEL: in-stock (value)
        labelInStockValue = new JLabel(String.valueOf(dashboardData.get("In-Stock")));
        labelInStockValue.setForeground(textColor);
        labelInStockValue.setFont(new Font("Poppins", Font.BOLD, 32));
        labelInStockValue.setHorizontalAlignment(SwingConstants.CENTER);
        labelInStockValue.setBounds(0, 47, 150, 40);
        panelInStock.add(labelInStockValue);
        
        
        // PANEL: profit chart
        panelProfitChart = new JPanel();
        panelProfitChart.setBackground(new Color(101, 133, 135));
        panelProfitChart.setBounds(65, 225, 250, 200);
        panelDashboard.add(panelProfitChart);
        
        // GRAPHICS: profit chart
        profitGraph = new BarGraphPanel();
        profitGraph.setBounds(0, 0, 250, 200);
        panelProfitChart.add(profitGraph);

        
        // PANEL: pie chart
        panelPieChart = new JPanel();
        panelPieChart.setBackground(new Color(101, 133, 135));
        panelPieChart.setBounds(385, 225, 250, 200);
        panelDashboard.add(panelPieChart);
        
        // GRAPHICS: pie chart
        pieGraph = new PieChartPanel();
        pieGraph.setBounds(0, 0, 250, 200);        
        panelPieChart.add(pieGraph);        
    }
    
    
    
    
    
    public void initializeInventoryPanel() {
        // PANEL: inventory
        panelInventory = new JPanel();
        panelInventory.setLayout(null);
        panelInventory.setBackground(backgroundColor);
        panelInventory.setBounds(WIDTH / 5, 50, WIDTH - (WIDTH / 5), HEIGHT);
        panelInventory.setVisible(false);
        
        // HEADER: product inventory
        labelHeaderTable = new JLabel("PRODUCT INVENTORY");
        labelHeaderTable.setForeground(new Color(60, 60, 60));
        labelHeaderTable.setFont(new Font("Segeo UI", Font.BOLD, 22));
        labelHeaderTable.setHorizontalAlignment(SwingConstants.LEFT);
        labelHeaderTable.setBounds(40, 20, 300, 50);
        panelInventory.add(labelHeaderTable);
        
        
        // PANEL: product details
        panelDetails = new JPanel();
        panelDetails.setBorder(new LineBorder(new Color(151, 183, 185), 2));
        panelDetails.setBounds(450, 125, 225, 275);
        panelDetails.setLayout(null);
        panelInventory.add(panelDetails);
        
        // LABEL: details
        labelDetails = new JLabel("Details");
        labelDetails.setForeground(new Color(40, 40, 40));
        labelDetails.setFont(new Font("Segoe UI", Font.BOLD, 20));
        labelDetails.setHorizontalAlignment(SwingConstants.CENTER);
        labelDetails.setBounds(0, 5, 225, 40);
        panelDetails.add(labelDetails);
        
        
        // LABEL-FIELD: product
        labelDetailProduct = new JLabel("Product:");
        labelDetailProduct.setFont(new Font("Poppins", Font.BOLD, 14));
        labelDetailProduct.setForeground(new Color(71, 71, 71));
        labelDetailProduct.setBounds(25, 55, 75, 20);

        fieldDetailProduct = new JTextField();
        fieldDetailProduct.setBounds(100, 55, 100, 20);
        panelDetails.add(labelDetailProduct);
        panelDetails.add(fieldDetailProduct);

        
        // LABEL-FIELD: category
        labelDetailCategory = new JLabel("Category:");
        labelDetailCategory.setFont(new Font("Poppins", Font.BOLD, 14));
        labelDetailCategory.setForeground(new Color(71, 71, 71));
        labelDetailCategory.setBounds(25, 85, 75, 20);

        fieldDetailCategory = new JTextField();
        fieldDetailCategory.setBounds(100, 85, 100, 20);
        panelDetails.add(labelDetailCategory);
        panelDetails.add(fieldDetailCategory);
        

        // LABEL-FIELD: cost
        labelDetailCost = new JLabel("Cost:");
        labelDetailCost.setFont(new Font("Poppins", Font.BOLD, 14));
        labelDetailCost.setForeground(new Color(71, 71, 71));
        labelDetailCost.setBounds(25, 115, 75, 20);

        fieldDetailCost = new JTextField();
        fieldDetailCost.setBounds(100, 115, 100, 20);
        panelDetails.add(labelDetailCost);
        panelDetails.add(fieldDetailCost);
        

        // LABEL-FIELD: price
        labelDetailPrice = new JLabel("Price:");
        labelDetailPrice.setFont(new Font("Poppins", Font.BOLD, 14));
        labelDetailPrice.setForeground(new Color(71, 71, 71));
        labelDetailPrice.setBounds(25, 145, 75, 20);

        fieldDetailPrice = new JTextField();
        fieldDetailPrice.setBounds(100, 145, 100, 20);
        panelDetails.add(labelDetailPrice);
        panelDetails.add(fieldDetailPrice);
        

        // LABEL-FIELD: quantity
        labelDetailQuantity = new JLabel("Quantity:");
        labelDetailQuantity.setFont(new Font("Poppins", Font.BOLD, 14));
        labelDetailQuantity.setForeground(new Color(71, 71, 71));
        labelDetailQuantity.setBounds(25, 175, 75, 20);

        fieldDetailQuantity = new JTextField();
        fieldDetailQuantity.setBounds(100, 175, 100, 20);
        panelDetails.add(labelDetailQuantity);
        panelDetails.add(fieldDetailQuantity);
        
        
        // BUTTON: add
        buttonAdd = new JButton("ADD");
        buttonAdd.setFont(new Font("Lato", Font.BOLD, 14));
        buttonAdd.setForeground(textColor);
        buttonAdd.setBackground(new Color(72, 171, 121));
        buttonAdd.setFocusPainted(false);
        buttonAdd.setBorderPainted(false);
        buttonAdd.setBounds(460, 415, 90, 40);
        buttonAdd.addActionListener(this);
        panelInventory.add( buttonAdd);
        
        // BUTTON: sold
        buttonSold = new JButton("SOLD");
        buttonSold.setFont(new Font("Lato", Font.BOLD, 14));
        buttonSold.setForeground(textColor);
        buttonSold.setBackground(defaultButtonColor);
        buttonSold.setFocusPainted(false);
        buttonSold.setBorderPainted(false);    
        buttonSold.setBounds(575, 415, 90, 40);
        buttonSold.addActionListener(this);
        panelInventory.add( buttonSold);

        // BUTTON: update
        buttonUpdate = new JButton("UPDATE");
        buttonUpdate.setFont(new Font("Poppins", Font.BOLD, 11));
        buttonUpdate.setForeground(textColor);
        buttonUpdate.setBackground(new Color(187, 191, 92));
        buttonUpdate.setFocusPainted(false);
        buttonUpdate.setBorderPainted(false);
        buttonUpdate.setBounds(25, 205, 80, 30);
        buttonUpdate.addActionListener(this);
        panelDetails.add(buttonUpdate);
        
        // BUTTON: delete
        buttonDelete = new JButton("DELETE");
        buttonDelete.setFont(new Font("Poppins", Font.BOLD, 11));
        buttonDelete.setForeground(textColor);
        buttonDelete.setBackground(new Color(190, 92, 107));
        buttonDelete.setFocusPainted(false);
        buttonDelete.setBorderPainted(false); 
        buttonDelete.setBounds(120, 205, 80, 30);
        buttonDelete.addActionListener(this);
        panelDetails.add(buttonDelete);
        
        // BUTTON: undo
        buttonUndo = new JButton("UNDO");
        buttonUndo.setFont(new Font("Lato", Font.BOLD, 10));
        buttonUndo.setForeground(new Color(70, 70, 70));
        buttonUndo.setBackground(new Color(170, 180, 180));
        buttonUndo.setBorder(BorderFactory.createLineBorder(new Color(150, 160, 160), 1));
        buttonUndo.setFocusPainted(false);    
        buttonUndo.setBounds(555, 30, 50, 30);
        buttonUndo.addActionListener(this);
        panelInventory.add( buttonUndo);
        
        // BUTTON: redo
        buttonRedo = new JButton("REDO");
        buttonRedo.setFont(new Font("Lato", Font.BOLD, 10));
        buttonRedo.setForeground(new Color(70, 70, 70));
        buttonRedo.setBackground(new Color(170, 180, 180));
        buttonRedo.setBorder(BorderFactory.createLineBorder(new Color(150, 160, 160), 1));
        buttonRedo.setFocusPainted(false);   
        buttonRedo.setBounds(615, 30, 50, 30);
        buttonRedo.addActionListener(this);
        panelInventory.add( buttonRedo);
        
        
        
        // PANEL: inventory table
        panelInventoryTable = new JPanel();
        panelInventoryTable.setBackground(new Color(101, 133, 135));
        panelInventoryTable.setBorder(new LineBorder(new Color(101, 133, 135), 2));
        panelInventoryTable.setBounds(40, 80, 385, 410);
        panelInventory.add(panelInventoryTable);
       
        createInventoryTable();   
    }
    


    public void createInventoryTable() {
        // TABLE: inventory
        String[] headers = {"Products", "Category", "Cost", "Price", "Quantity"};
        inventoryTableModel = new DefaultTableModel(headers, 0);
        inventoryTable = new JTable(inventoryTableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Disable cell editing
            }

            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);

                // Apply hover effect
                if (row == hoveredRow) {
                    c.setBackground(new Color(220, 240, 230)); // Hover color
                } else {
                    c.setBackground(getBackground()); // Default background color
                }
                return c;
            }
        };

        // set the row height to 22
        inventoryTable.setRowHeight(22);

        // mouse motion listener for hover effect
        inventoryTable.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int row = inventoryTable.rowAtPoint(e.getPoint());
                if (row != hoveredRow) {
                    hoveredRow = row;
                    inventoryTable.repaint(); // Repaint to reflect the hover effect
                }
            }
        });

        // mouse listener for click event (row selection)
        inventoryTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = inventoryTable.rowAtPoint(e.getPoint());
                if (row >= 0) {
                    inventoryTable.setRowSelectionInterval(row, row); // Select the clicked row
                }
            }
        });

        // ListSelectionListener to retrieve selected row data
        inventoryTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = inventoryTable.getSelectedRow();
                if (selectedRow != -1) {
                    // Retrieve data from the selected row
                    String productName = inventoryTable.getValueAt(selectedRow, 0).toString();
                    String category = inventoryTable.getValueAt(selectedRow, 1).toString();
                    String cost = inventoryTable.getValueAt(selectedRow, 2).toString();
                    String price = inventoryTable.getValueAt(selectedRow, 3).toString();
                    String quantity = inventoryTable.getValueAt(selectedRow, 4).toString();

                    // Set the retrieved data into your text fields
                    fieldDetailProduct.setText(productName);
                    fieldDetailCategory.setText(category);
                    fieldDetailCost.setText(cost);
                    fieldDetailPrice.setText(price);
                    fieldDetailQuantity.setText(quantity);
                }
            }
        });

        // Set column widths
        inventoryTable.getColumnModel().getColumn(0).setPreferredWidth(105); // Products
        inventoryTable.getColumnModel().getColumn(1).setPreferredWidth(95);  // Category
        inventoryTable.getColumnModel().getColumn(2).setPreferredWidth(64);  // Cost Price
        inventoryTable.getColumnModel().getColumn(3).setPreferredWidth(64);  // Selling Price
        inventoryTable.getColumnModel().getColumn(4).setPreferredWidth(64);  // Quantity

        // custom header 
        inventoryTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12)); // Bold text
        inventoryTable.getTableHeader().setBackground(new Color(200, 220, 210)); // Darker shade
        inventoryTable.getTableHeader().setForeground(new Color(40, 40, 40)); // Optional: Black text
        inventoryTable.getTableHeader().setResizingAllowed(false);
        inventoryTable.getTableHeader().setReorderingAllowed(false);

        // Add table to JScrollPane
        JScrollPane scrollPane = new JScrollPane(inventoryTable);
        scrollPane.setBackground(backgroundColor);
        scrollPane.setPreferredSize(new Dimension(385, 415));

        // Assuming panelInventoryTable is your container panel, adding the scroll pane to it
        panelInventoryTable.add(scrollPane);

        // Ensure that the panel revalidates and repaints itself
        panelInventoryTable.revalidate();
        panelInventoryTable.repaint();
    }


    public void refreshInventoryTable() {
        inventoryTableModel.setRowCount(0); 

        // Traverse through the linked list and add each product to the table
        ProductNode current = productList.head;
        while (current != null) {
            inventoryTableModel.addRow(new Object[]{
                current.getName(),
                current.getCategory(),
                current.getCost(),
                current.getPrice(),
                current.getQuantity()
            });
            current = current.next;
        }
    }
    
    
    
    
    
    public void initializeHistoryPanel() {
        // PANEL: history
        panelHistory = new JPanel();
        panelHistory.setLayout(null);
        panelHistory.setBackground(backgroundColor);
        panelHistory.setBounds(WIDTH / 5, 50, WIDTH - (WIDTH / 5), HEIGHT);  
        
        // PANEL: hstory table
        panelHistoryTable = new JPanel();
        panelHistoryTable.setBounds(50, 50, 620, 400);
        panelHistory.add(panelHistoryTable);
        
        createHistoryTable();
    }
    
    
    public void createHistoryTable() {
        // TABLE: history
        
        // history table properties
        String[] headers = {"Action", "Products", "Category", "Cost", "Price", "Quantity"};
        historyTableModel = new DefaultTableModel(headers, 0);
        historyTable = new JTable(historyTableModel);
        
        // history table costumization, not selectable and editable
        historyTable.getTableHeader().setReorderingAllowed(false);
        historyTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        historyTable.setDefaultEditor(Object.class, null);
        historyTable.setCellSelectionEnabled(false);
        historyTable.setRowSelectionAllowed(false);
        historyTable.setColumnSelectionAllowed(false);
        historyTable.setRowHeight(25);
        
        // set width for each column
        historyTable.getColumnModel().getColumn(0).setPreferredWidth(150); // Action
        historyTable.getColumnModel().getColumn(1).setPreferredWidth(120); // Product
        historyTable.getColumnModel().getColumn(2).setPreferredWidth(120);  // Category
        historyTable.getColumnModel().getColumn(3).setPreferredWidth(80);  // Cost
        historyTable.getColumnModel().getColumn(4).setPreferredWidth(80);  // Price
        historyTable.getColumnModel().getColumn(5).setPreferredWidth(70);  // Quantity

        // history table dimensions
        JScrollPane scrollPane = new JScrollPane(historyTable);
        scrollPane.setBounds(50, 50, 620, 40);
        scrollPane.setPreferredSize(new Dimension(620, 400));
        panelHistoryTable.add(scrollPane);
        panelHistoryTable.setBackground(new Color(101, 133, 135));
        panelHistoryTable.setBorder(new LineBorder(new Color(101, 133, 135), 2));
    }
    
        
    public void addActionToHistory(Action action) {
        // Push action to the stack
        historyStack.push(action);
        undoStack.push(action);

        // Add the action to the history table
        historyTableModel.insertRow(0, new Object[] {
            action.getType(),
            action.getProductName(),
            action.getCategory(),
            action.getCost(),
            action.getPrice(),
            action.getQuantity()
        });

        // Set the new row color based on the action type
        historyTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Center the action type (first column)
                if (column == 0) {
                    ((JLabel) c).setHorizontalAlignment(SwingConstants.CENTER);  // Center align the first column (Action Type)
                }

                // Color based on action type
                String actionType = (String) table.getValueAt(row, 0); // Get action type from the row

                if (actionType != null) {
                    switch (actionType) {
                        case "ADD":
                            c.setBackground(new Color(204, 255, 204)); // Pastel Green for "ADD"
                            break;
                        case "UPDATE":
                            c.setBackground(new Color(255, 255, 178)); // Pastel Yellow for "UPDATE"
                            break;
                        case "DELETE":
                            c.setBackground(new Color(255, 204, 204)); // Pastel Red for "DELETE"
                            break;
                        case "SOLD":
                            c.setBackground(new Color(173, 216, 230)); // Pastel Blue for "SOLD"
                            break;
                        default:
                            c.setBackground(table.getBackground()); // Default background for unhandled cases
                            break;
                    }
                }

                return c;
            }
        });

        // Refresh the table to apply the new renderer
        historyTable.repaint();
    }




    
    
    public void initializeAccountPanel() {
        // PANEL: account
        panelAccount = new JPanel();
        panelAccount.setBounds(WIDTH / 5, 50, WIDTH - (WIDTH / 5), HEIGHT);
        panelAccount.setLayout(null);
        
        // LABEL: company name
        labelCompanyName = new JLabel("Global Marketing Retail Company Ltd.");
        labelCompanyName.setBounds(185, 130, 350, 60);
        labelCompanyName.setFont(new Font("Poppins", Font.BOLD, 18));
        labelCompanyName.setForeground(new Color(71, 71, 71));
        labelCompanyName.setHorizontalAlignment(SwingConstants.CENTER);
        panelAccount.add(labelCompanyName);
        
        // LABEL: company address
        labelCompanyAddress = new JLabel("123, J.P. Rizal St., Abucay, Bataan");
        labelCompanyAddress.setBounds(200, 170, 300, 60);
        labelCompanyAddress.setFont(new Font("Poppins", Font.PLAIN, 18));
        labelCompanyAddress.setForeground(new Color(71, 71, 71));
        labelCompanyAddress.setHorizontalAlignment(SwingConstants.CENTER);
        panelAccount.add(labelCompanyAddress);
        
        // LABEL: company contacts
        labelCompanyContacts = new JLabel("0909 123 4567");
        labelCompanyContacts.setBounds(200, 210, 300, 60);
        labelCompanyContacts.setFont(new Font("Poppins", Font.PLAIN, 18));
        labelCompanyContacts.setForeground(new Color(71, 71, 71));
        labelCompanyContacts.setHorizontalAlignment(SwingConstants.CENTER);
        panelAccount.add(labelCompanyContacts);
        
        // BUTTON: log out
        buttonLogOut = new JButton("Log Out");
        buttonLogOut.setFont(new Font("Arial", Font.BOLD, 14));
        buttonLogOut.setForeground(textColor);
        buttonLogOut.setBackground(new Color(190, 92, 107));
        buttonLogOut.setFocusPainted(false);
        buttonLogOut.setBorderPainted(false);
        buttonLogOut.setBounds(305, 320, 100, 40);
        buttonLogOut.addActionListener(this);
        buttonLogOut.setVisible(false);
        panelAccount.add(buttonLogOut);
        
        // LABEL: company email
        labelCompanyEmail = new JLabel("globalmarketing@company.com");
        labelCompanyEmail.setBounds(200, 250, 300, 60);
        labelCompanyEmail.setFont(new Font("Poppins", Font.PLAIN, 18));
        labelCompanyEmail.setForeground(new Color(71, 71, 71));
        labelCompanyEmail.setHorizontalAlignment(SwingConstants.CENTER);
        panelAccount.add(labelCompanyEmail);     
        
        try {
            // ICON: Load profile image from the cloud (URL)
            URL profImageUrl = new URL("https://drive.google.com/uc?id=1olDyVA3p88BMqh5qjVgaX9la9k1KFrw5");  // replace with your image URL
            ImageIcon profImageIcon = new ImageIcon(profImageUrl);
            Image profImage = profImageIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            JLabel profHandle = new JLabel(new ImageIcon(profImage));
            profHandle.setBounds(330, 50, 60, 60);
            panelAccount.add(profHandle);

            // IMAGE: Load background image from the cloud (URL)
            URL bgImageUrl = new URL("https://drive.google.com/uc?id=1bc4LiACzgXDU3XqXz_S0ohqzbq1nzPbj");  // replace with your image URL
            ImageIcon bgImageIcon = new ImageIcon(bgImageUrl);
            Image bgImage = bgImageIcon.getImage().getScaledInstance(720, 550, Image.SCALE_SMOOTH);
            JLabel bgHandle = new JLabel(new ImageIcon(bgImage));
            bgHandle.setBounds(0, 0, 720, 550);
            panelAccount.add(bgHandle);

        } catch (Exception e) {
            e.printStackTrace();

            // Fallback for the profile image: Light gray circle
            JLabel fallbackIcon = new JLabel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillOval(0, 0, 60, 60);
                }
            };
            fallbackIcon.setBounds(330, 50, 60, 60);
            panelAccount.add(fallbackIcon);

            // Fallback for the background: Solid background color
            panelAccount.setBackground(new Color(240, 240, 240));  // Light gray background
        }
    }
    
    
    
    public void initializeAboutPanel() {
        // PANEL: about
        panelAbout = new JPanel();
        panelAbout.add(new JLabel("About Content"));
    }
    
    
    
    
    
    
    
    
    // METHODS FOR LOGIN AND REGISTER
    
    public void authenticateLogin() {
        // companyID = fieldCompanyID.getText();
        // password = new String(fieldPassword.getPassword());
        
        // TEMP: Pre-Determined Values/Function For Demo (skip login)
        companyID = "Bagtas1024";
        password = "bagtascompany123";
        
        if (accounts.containsKey(companyID)) {
            if (accounts.get(companyID).equals(password)) {                
                // login success
                // loadCompanyData(companyID);
                // TEMP: Pre-Determined Values/Function For Demo (skip login)
                setSelectedNavButton(dashboardNavButton);
                labelMessage.setText("");
                panelBackground.setVisible(false);
                panelLogin.setVisible(false);
                panelNavigation.setVisible(true);
                panelHeader.setVisible(true);
                panelDashboard.setVisible(true);
                
                labelProductSoldValue.setText(String.valueOf(dashboardData.get("Products Sold")));
                labelRevenueValue.setText("$" + String.valueOf(dashboardData.get("Revenue")));
                labelInStockValue.setText(String.valueOf(dashboardData.get("In-Stock")));
            } else {
                // incorrect password
                labelMessage.setText("Incorrect Password. Please Try Again.");
                labelMessage.setForeground(new Color(219, 53, 53));
                labelMessage.setVisible(true);
            }
        } else {
            // companyID not in database
            labelMessage.setText("CompanyID is not yet registered.");
            labelMessage.setForeground(new Color(219, 53, 53));
            labelMessage.setVisible(true);
        }
        
    }
    
    
    
    public void registerPressed() {
        companyID = fieldCompanyID.getText();
        password = new String(fieldPassword.getPassword());
        
        if (!accounts.containsKey(companyID)) {
            if (password.length() >= 16) {
                // register success
                labelMessage.setText("Account registered successfully!");
                labelMessage.setForeground(new Color(53, 219, 92));
                labelMessage.setVisible(true);
                fieldCompanyID.setText("");
                fieldPassword.setText("");
                accounts.put(companyID, password);
                saveAccounts();
            } else {
                // incorrect password
                labelMessage.setText("Password must contain at least 16 characters.");
                labelMessage.setForeground(new Color(219, 53, 53));
                labelMessage.setVisible(true);
            }
        } else {
            // companyID not in database
            labelMessage.setText("CompanyID is already registered.");
            labelMessage.setForeground(new Color(219, 53, 53));
            labelMessage.setVisible(true);
        }
    }
    
    
    
    
    
    // METHODS FOR HANDLING, SAVING AND LOADING DATA
   
    public void initializeData() {
        // loadAccounts();
    }
    
    public void loadAccounts() {
        // retrieve accounts credentials from the account.txt
        try (BufferedReader reader = new BufferedReader(new FileReader("database/accounts.txt"))) {
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                String[] data = line.split(",");
                accounts.put(data[0].trim(), data[1].trim());
                existingAccounts.add(data[0].trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public void saveAccounts() {
        // save registered accounts to accounts.txt
        String filePath = "database/accounts.txt";
        try {
            // ensure the directory exists, create if it doesn't
            Path directoryPath = Paths.get(filePath).getParent();
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            // write data to file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
                for (Map.Entry<String, String> entry : accounts.entrySet()) {
                    if (!existingAccounts.contains(entry.getKey())) {
                        writer.write("\n" + entry.getKey() + "," + entry.getValue());
                    } else {
                        labelMessage.setText("CompanyID already exists.");
                    }
                }
                System.out.println("HashMap has been saved to " + filePath);
                writer.close();
            } catch (IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Error creating directories: " + e.getMessage());
        }
    }
    
    
    public void loadCompanyData(String compID) {
        // load company data for the current logged in companyID
        String companyDataFilePath = "database/"+compID.toLowerCase()+"database.txt";
       
        try {
            // ensure the directory exists, create if it doesn't
            Path directoryPath = Paths.get(companyDataFilePath).getParent();
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }
            
            try (BufferedReader reader = new BufferedReader(new FileReader(companyDataFilePath))) {
                // retrieve company name
                String line = reader.readLine();
                headerText = line;
                companyName.setText(headerText);
                
                // retrieve dashboard data 
                String[] dashboardFileData = reader.readLine().split(",");
                dashboardData.put("Products Sold", Integer.parseInt(dashboardFileData[0].trim()));
                dashboardData.put("Revenue", Double.parseDouble(dashboardFileData[1].trim()));
                
                // retrieve daily profit data
                String[] profitData = reader.readLine().split(",");
                for (String profit : profitData) {
                    double value = Double.parseDouble(profit.trim());
                }
                reader.readLine(); // skip header
                
                // retrieve product data
                int tempStock = 0;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    String name = data[0].trim();
                    String category = data[1].trim();
                    double costPrice = Double.parseDouble(data[2].trim());
                    double sellingPrice = Double.parseDouble(data[3].trim());
                    int quantity = Integer.parseInt(data[4].trim());

                    // instantiate product object
                    ProductNode product = new ProductNode(name, category, costPrice, sellingPrice, quantity);
                    productList.addProduct(product);
                    tempStock += quantity;
                }
                
                dashboardData.put("In-Stock", tempStock);
                
                // display each product node to inventory table
                refreshInventoryTable();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public void initializeDemoMode() {
        // Set company name
        headerText = "Global Marketing Retail Company Ltd.";
        companyName.setText(headerText);

        // Set dashboard data
        dashboardData.put("Products Sold", 352);
        dashboardData.put("Revenue", 7046.29);

        // Load product data
        Object[][] products = {
            {"Smartwatch", "Electronics", 6500, 7800, 18},
            {"Milk", "Food", 45, 55, 320},
            {"Headphones", "Accessories", 1800, 2300, 95},
            {"Chicken", "Food", 140, 170, 85},
            {"Printer", "Office Supplies", 5500, 6800, 37},
            {"Apple", "Food", 8, 12, 720},
            {"Laptop", "Electronics", 28000, 33000, 22},
            {"Tablet", "Electronics", 7500, 9500, 28},
            {"Eggs", "Food", 4, 6, 1100},
            {"Monitor", "Electronics", 11000, 14000, 20},
            {"Bread", "Food", 12, 18, 480},
            {"Camera", "Electronics", 22000, 27000, 13},
            {"Keyboard", "Accessories", 1200, 1600, 65},
            {"Rice", "Food", 35, 45, 900},
            {"Smartphone", "Electronics", 13000, 16000, 45}
        };

        int totalStock = 0;

        for (Object[] productData : products) {
            String name = (String) productData[0];
            String category = (String) productData[1];
            double costPrice = Double.parseDouble(productData[2].toString());
            double sellingPrice = Double.parseDouble(productData[3].toString());
            int quantity = Integer.parseInt(productData[4].toString());

            // Create product node
            ProductNode product = new ProductNode(name, category, costPrice, sellingPrice, quantity);
            productList.addProduct(product);

            // Add to total stock
            totalStock += quantity;
        }

        dashboardData.put("In-Stock", totalStock);

        // Refresh inventory table
        refreshInventoryTable();
        
        setSelectedNavButton(dashboardNavButton);
        labelMessage.setText("");
        panelBackground.setVisible(false);
        panelLogin.setVisible(false);
        panelNavigation.setVisible(true);
        panelHeader.setVisible(true);
        panelDashboard.setVisible(true);

        labelProductSoldValue.setText(String.valueOf(dashboardData.get("Products Sold")));
        labelRevenueValue.setText("$" + String.valueOf(dashboardData.get("Revenue")));
        labelInStockValue.setText(String.valueOf(dashboardData.get("In-Stock")));
        
        buttonLogOut.setVisible(false);
    }

   
    

    
    // METHODS FOR ACTIONLISTENER 
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        
        if (e.getSource() == buttonLogIn) {
            authenticateLogin();
        } else if (e.getSource() == buttonRegister) {  
            registerPressed();
        } else if (e.getSource() == showPassword) {
            if (showPassword.isSelected()) {
                fieldPassword.setEchoChar((char) 0);
            } else {
                fieldPassword.setEchoChar('\u2022');
            }
        } else if (e.getSource() == dashboardNavButton) {
            panelDashboard.setVisible(true);
            panelInventory.setVisible(false);
            panelHistory.setVisible(false);
            panelAbout.setVisible(false);
            panelAccount.setVisible(false);
            buttonLogOut.setVisible(false);
            setSelectedNavButton(dashboardNavButton);
        } else if (e.getSource() == inventoryNavButton) {
            panelDashboard.setVisible(false);
            panelInventory.setVisible(true);
            panelHistory.setVisible(false);
            panelAbout.setVisible(false);
            panelAccount.setVisible(false);
            buttonLogOut.setVisible(false);
            setSelectedNavButton(inventoryNavButton);
        } else if (e.getSource() == historyNavButton) {
            panelDashboard.setVisible(false);
            panelInventory.setVisible(false);
            panelHistory.setVisible(true);
            panelAbout.setVisible(false);
            panelAccount.setVisible(false);
            buttonLogOut.setVisible(false);
            setSelectedNavButton(historyNavButton);
        } else if (e.getSource() == aboutNavButton) {
            panelDashboard.setVisible(false);
            panelInventory.setVisible(false);
            panelHistory.setVisible(false);
            panelAbout.setVisible(true);
            panelAccount.setVisible(false);
            buttonLogOut.setVisible(false);
            setSelectedNavButton(aboutNavButton);
        } else if (e.getSource() == accountNavButton) {
            panelDashboard.setVisible(false);
            panelInventory.setVisible(false);
            panelHistory.setVisible(false);
            panelAbout.setVisible(false);
            panelAccount.setVisible(true);
            buttonLogOut.setVisible(true);
            setSelectedNavButton(accountNavButton);
        } else if (e.getSource() == buttonAdd) {
            addProduct();
        } else if (e.getSource() == buttonSold) {
            soldProduct();
        } else if (e.getSource() == buttonUpdate) {
            updateProduct();
        } else if (e.getSource() == buttonDelete) {
            deleteProduct();
        } else if (e.getSource() == buttonUndo) {
            // Perform undo logic
            if (!historyStack.isEmpty()) {
                // Pop the last action from historyStack
                Action lastAction = historyStack.pop();

                // Perform reverse action based on the action type
                if (lastAction.getType().equals("ADD")) {
                undoAddProduct(lastAction);
                } else if (lastAction.getType().equals("SOLD")) {
                    undoSoldProduct(lastAction);
                } else if (lastAction.getType().equals("UPDATE")) {
                    undoUpdateProduct(lastAction);
                } else if (lastAction.getType().equals("DELETE")) {
                    undoDeleteProduct(lastAction);
                } 

                // Push the undone action to redoStack
                redoStack.push(lastAction);
            } else {
                JOptionPane.showMessageDialog(null, "No actions to undo");
            }
        } else if (e.getSource() == buttonRedo) {
            // Perform redo logic
            if (!redoStack.isEmpty()) {
                // Pop the last undone action from redoStack
                Action lastUndoneAction = redoStack.pop();

                // Reapply the action based on its type
                if (lastUndoneAction.getType().equals("ADD")) {
                    redoAddProduct(lastUndoneAction);
                } else if (lastUndoneAction.getType().equals("SOLD")) {
                    redoSoldProduct(lastUndoneAction);
                } else if (lastUndoneAction.getType().equals("UPDATE")) {
                    redoUpdateProduct(lastUndoneAction);
                } else if (lastUndoneAction.getType().equals("DELETE")) {
                    redoDeleteProduct(lastUndoneAction);
                }

                // Push the redone action back to historyStack
                addActionToHistory(lastUndoneAction);
            } else {
                JOptionPane.showMessageDialog(null, "No actions to redo");
            }
        } else if (e.getSource() == buttonLogOut) {
            int confirm = JOptionPane.showConfirmDialog(
            null, "Are you sure you want to log out?", "Log Out", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);  // Terminate the application
            }
        }
    }
    
    
    
    
    
    public void addProduct() {
        // Create a panel to collect product details
        JPanel panel = new JPanel(new GridLayout(5, 2)); // 5 rows, 2 columns
        JLabel lblProductName = new JLabel("Product Name:");
        JTextField tfProductName = new JTextField();
        JLabel lblCategory = new JLabel("Category:");
        JTextField tfCategory = new JTextField();
        JLabel lblCost = new JLabel("Cost:");
        JTextField tfCost = new JTextField();
        JLabel lblPrice = new JLabel("Price:");
        JTextField tfPrice = new JTextField();
        JLabel lblQuantity = new JLabel("Quantity:");
        JTextField tfQuantity = new JTextField();
        panel.setBackground(new Color(245, 245, 247));

        panel.add(lblProductName);
        panel.add(tfProductName);
        panel.add(lblCategory);
        panel.add(tfCategory);
        panel.add(lblCost);
        panel.add(tfCost);
        panel.add(lblPrice);
        panel.add(tfPrice);
        panel.add(lblQuantity);
        panel.add(tfQuantity);

        // Show the dialog
        int option = JOptionPane.showConfirmDialog(
            null,
            panel,
            "Enter Product Details",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (option == JOptionPane.OK_OPTION) {
            try {
                // Retrieve user input
                String productName = tfProductName.getText().trim();
                String category = tfCategory.getText().trim();
                double cost = Double.parseDouble(tfCost.getText().trim());
                double price = Double.parseDouble(tfPrice.getText().trim());
                int quantity = Integer.parseInt(tfQuantity.getText().trim());

                // Check if product name already exists
                for (int i = 0; i < inventoryTableModel.getRowCount(); i++) {
                    if (inventoryTableModel.getValueAt(i, 0).equals(productName)) {
                        JOptionPane.showMessageDialog(null, "Product already exists in the inventory.");
                        return; // Exit if the product already exists
                    }
                }

                // Add the product to the linked list (if needed)
                ProductNode newProductNode = new ProductNode(productName, category, cost, price, quantity);
                productList.addProduct(newProductNode); // Assuming you have a method to add the product to the list
                refreshInventoryTable();

                // Update inventory and dashboard
                dashboardData.put("In-Stock", (int) dashboardData.get("In-Stock") + quantity);
                labelInStockValue.setText(String.valueOf(dashboardData.get("In-Stock")));

                // Print to console (optional)
                dashboardData.printMap();

                // Add to the history stack and update the table
                Action action = new Action("ADD", productName, category, cost, price, quantity);
                addActionToHistory(action);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter valid numbers for cost, price, and quantity.");
            }
        } else {
            // If user cancels the dialog
            JOptionPane.showMessageDialog(null, "Product addition cancelled.");
        }
        
        // clear redoStack if action is new
        while (!redoStack.isEmpty()) {
            redoStack.pop();
        }
    }
    
    public void undoAddProduct(Action lastAction) {
        // Get the product details from the last action
        String productName = lastAction.getProductName();
        String category = lastAction.getCategory();
        double cost = lastAction.getCost();
        double price = lastAction.getPrice();
        int quantity = lastAction.getQuantity();
        
        
        // Remove the record from history
        for (int i = 0; i < historyTableModel.getRowCount(); i++) {
            if (historyTableModel.getValueAt(i, 1).equals(productName)) {
                historyTableModel.removeRow(i);  // Remove the row from the table
                historyTable.repaint();
                break;  // Exit after removing the product
            }
        }

        // Remove the product from the linked list and inventory table
        ProductNode current = productList.head;
        while (current != null) {
            if (current.getName().equals(productName)) {
                productList.removeProduct(current); // Assuming removeProduct is implemented
                break; // Exit after removing the product
            }
            current = current.next;
        }
        
        // Update the "In-Stock" value in dashboardData
        dashboardData.put("In-Stock", (int) dashboardData.get("In-Stock") - quantity);
        labelInStockValue.setText(String.valueOf(dashboardData.get("In-Stock")));

        // Repaint the history table (refresh it to reflect the undo)
        refreshInventoryTable();
        historyTable.repaint();

        // Optional: Print the updated inventory and dashboard to the console
        dashboardData.printMap();
    }
    
    public void redoAddProduct(Action lastAction) {
        // Get the product details from the last action
        String productName = lastAction.getProductName();
        String category = lastAction.getCategory();
        double cost = lastAction.getCost();
        double price = lastAction.getPrice();
        int quantity = lastAction.getQuantity();

        // Re-add the product to the linked list and inventory table
        ProductNode newProductNode = new ProductNode(productName, category, cost, price, quantity);
        productList.addProduct(newProductNode); // Assuming you have a method to add the product to the list
        refreshInventoryTable();
        System.out.println("REDO ADD");

        // Update the "In-Stock" value in dashboardData
        dashboardData.put("In-Stock", (int) dashboardData.get("In-Stock") + quantity);
        labelInStockValue.setText(String.valueOf(dashboardData.get("In-Stock")));
        
        // Repaint the history table (refresh it to reflect the redo)
        historyTable.repaint();

        // Optional: Print the updated inventory and dashboard to the console
        dashboardData.printMap();
    }



    
    
    public void soldProduct() {
        int selectedRow = inventoryTable.getSelectedRow();
        if (selectedRow != -1) {
            String quantityStr = JOptionPane.showInputDialog("Enter quantity sold:");
            try {
                int quantitySold = Integer.parseInt(quantityStr);
                int currentQuantity = Integer.parseInt(inventoryTable.getValueAt(selectedRow, 4).toString());
                double productPrice = (double) inventoryTable.getValueAt(selectedRow, 3);

                if (quantitySold > currentQuantity) {
                    JOptionPane.showMessageDialog(null, "Quantity sold cannot be greater than available quantity.");
                } else {
                    // Update the product quantity in the table
                    int newQuantity = currentQuantity - quantitySold;
                    inventoryTable.setValueAt(String.valueOf(newQuantity), selectedRow, 4);

                    // Update the quantity in the linked list
                    String productName = inventoryTable.getValueAt(selectedRow, 0).toString();
                    ProductNode productNode = productList.getProductByName(productName);  // Use productList here
                    if (productNode != null) {
                        productNode.setQuantity(productNode.getQuantity() - quantitySold);  // Update quantity in the linked list
                    }

                    // Update inventory and sales info
                    dashboardData.put("Products Sold", (int)dashboardData.get("Products Sold") + quantitySold);
                    dashboardData.put("Revenue", (double)dashboardData.get("Revenue") +  quantitySold * productPrice);
                    dashboardData.put("In-Stock", (int)dashboardData.get("In-Stock") - quantitySold);

                    labelProductSoldValue.setText(String.valueOf(dashboardData.get("Products Sold")));
                    labelRevenueValue.setText("$" + String.valueOf(dashboardData.get("Revenue")));
                    labelInStockValue.setText(String.valueOf(dashboardData.get("In-Stock")));

                    // Create an Action for the "SOLD" event
                    Action soldAction = new Action("SOLD", inventoryTable.getValueAt(selectedRow, 0).toString(),
                                                   inventoryTable.getValueAt(selectedRow, 1).toString(), 
                                                   (double) inventoryTable.getValueAt(selectedRow, 2),
                                                   (double) inventoryTable.getValueAt(selectedRow, 3), quantitySold);

                    // Add the sold action to the history
                    addActionToHistory(soldAction);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number for quantity sold.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "No product selected");
        }
        
        // clear redoStack if action is new
        while (!redoStack.isEmpty()) {
            redoStack.pop();
        }
    }
    
    
    public void undoSoldProduct(Action lastAction) {
        // Get the product details from the last action
        String productName = lastAction.getProductName();
        String category = lastAction.getCategory();
        double cost = lastAction.getCost();
        double price = lastAction.getPrice();
        int quantitySold = lastAction.getQuantity();

        // Find the row of the product in the inventory table
        int selectedRow = -1;
        for (int i = 0; i < inventoryTable.getRowCount(); i++) {
            if (inventoryTable.getValueAt(i, 0).equals(productName)) {
                selectedRow = i;
                break;
            }
        }

        // If the product was found, revert the quantity in the table
        if (selectedRow != -1) {
            int currentQuantity = Integer.parseInt(inventoryTable.getValueAt(selectedRow, 4).toString());
            int newQuantity = currentQuantity + quantitySold;
            inventoryTable.setValueAt(String.valueOf(newQuantity), selectedRow, 4);
        }

        // Revert the product quantity in the linked list
        ProductNode productNode = productList.getProductByName(productName);
        if (productNode != null) {
            productNode.setQuantity(productNode.getQuantity() + quantitySold);
        }

        // Revert dashboard data
        dashboardData.put("Products Sold", (int) dashboardData.get("Products Sold") - quantitySold);
        dashboardData.put("Revenue", (double) dashboardData.get("Revenue") - quantitySold * price);
        dashboardData.put("In-Stock", (int) dashboardData.get("In-Stock") + quantitySold);

        // Update the labels
        labelProductSoldValue.setText(String.valueOf(dashboardData.get("Products Sold")));
        labelRevenueValue.setText("$" + String.valueOf(dashboardData.get("Revenue")));
        labelInStockValue.setText(String.valueOf(dashboardData.get("In-Stock")));

        // Remove the record from history
        for (int i = 0; i < historyTableModel.getRowCount(); i++) {
            if (historyTableModel.getValueAt(i, 1).equals(productName)) {
                historyTableModel.removeRow(i);  // Remove the row from the table
                historyTable.repaint();
                break;  // Exit after removing the product
            }
        }
        
        // Repaint the inventory and history tables
        refreshInventoryTable();
        historyTable.repaint();

        dashboardData.printMap();
    }

    
    public void redoSoldProduct(Action lastAction) {
        // Get the product details from the last action
        String productName = lastAction.getProductName();
        String category = lastAction.getCategory();
        double cost = lastAction.getCost();
        double price = lastAction.getPrice();
        int quantitySold = lastAction.getQuantity();

        // Find the row of the product in the inventory table
        int selectedRow = -1;
        for (int i = 0; i < inventoryTable.getRowCount(); i++) {
            if (inventoryTable.getValueAt(i, 0).equals(productName)) {
                selectedRow = i;
                break;
            }
        }

        // If the product was found, re-apply the quantity change in the table
        if (selectedRow != -1) {
            int currentQuantity = Integer.parseInt(inventoryTable.getValueAt(selectedRow, 4).toString());
            int newQuantity = currentQuantity - quantitySold;
            inventoryTable.setValueAt(String.valueOf(newQuantity), selectedRow, 4);
        }

        // Re-apply the product quantity change in the linked list
        ProductNode productNode = productList.getProductByName(productName);
        if (productNode != null) {
            productNode.setQuantity(productNode.getQuantity() - quantitySold);
        }

        // Re-apply the sales and dashboard data
        dashboardData.put("Products Sold", (int) dashboardData.get("Products Sold") + quantitySold);
        dashboardData.put("Revenue", (double) dashboardData.get("Revenue") + quantitySold * price);
        dashboardData.put("In-Stock", (int) dashboardData.get("In-Stock") - quantitySold);

        // Update the labels
        labelProductSoldValue.setText(String.valueOf(dashboardData.get("Products Sold")));
        labelRevenueValue.setText("$" + String.valueOf(dashboardData.get("Revenue")));
        labelInStockValue.setText(String.valueOf(dashboardData.get("In-Stock")));

        // Repaint the inventory and history tables
        refreshInventoryTable();
        historyTable.repaint();

        dashboardData.printMap();
    }



    
    
    public void updateProduct() {
        int selectedRow = inventoryTable.getSelectedRow();
        if (selectedRow != -1) {
            String productName = inventoryTable.getValueAt(selectedRow, 0).toString();
            String prevName = productName;
            String prevCategory = inventoryTable.getValueAt(selectedRow, 1).toString();
            double prevCost = Double.parseDouble(inventoryTable.getValueAt(selectedRow, 2).toString());
            double prevPrice = Double.parseDouble(inventoryTable.getValueAt(selectedRow, 3).toString());
            int prevQuantity = Integer.parseInt(inventoryTable.getValueAt(selectedRow, 4).toString());

            // Update the product details in the table
            inventoryTable.setValueAt(fieldDetailProduct.getText(), selectedRow, 0);
            inventoryTable.setValueAt(fieldDetailCategory.getText(), selectedRow, 1);
            inventoryTable.setValueAt(fieldDetailCost.getText(), selectedRow, 2);
            inventoryTable.setValueAt(fieldDetailPrice.getText(), selectedRow, 3);
            inventoryTable.setValueAt(fieldDetailQuantity.getText(), selectedRow, 4);

            // Update the product details in the linked list directly
            ProductNode current = productList.head;
            while (current != null) {
                if (current.getName().equals(productName)) {
                    current.setName(fieldDetailProduct.getText());
                    current.setCategory(fieldDetailCategory.getText());
                    current.setCost(Double.parseDouble(fieldDetailCost.getText()));
                    current.setPrice(Double.parseDouble(fieldDetailPrice.getText()));
                    current.setQuantity(Integer.parseInt(fieldDetailQuantity.getText()));
                    break;
                }
                current = current.next;
            }

            // Update in-stock value based on the new quantity
            int newQuantity = Integer.parseInt(fieldDetailQuantity.getText());
            dashboardData.put("In-Stock", (int) dashboardData.get("In-Stock") + (newQuantity - prevQuantity));
            labelInStockValue.setText(String.valueOf(dashboardData.get("In-Stock")));

            // Create an Action for the "UPDATE" event (for undo)
            Action updateAction = new Action("UPDATE", prevName, prevCategory, prevCost, prevPrice, prevQuantity, 
                                             fieldDetailProduct.getText(), fieldDetailCategory.getText(), 
                                             Double.parseDouble(fieldDetailCost.getText()), 
                                             Double.parseDouble(fieldDetailPrice.getText()), newQuantity);

            // Add the update action to the history for undo/redo
            addActionToHistory(updateAction);

        } else {
            JOptionPane.showMessageDialog(null, "No product selected");
        }

        // Clear redoStack if a new action is performed
        while (!redoStack.isEmpty()) {
            redoStack.pop();
        }
    }

    
    
    public void undoUpdateProduct(Action lastAction) {
        // Get the product details from the last action (before the update)
        String prevName = lastAction.getProductName();
        String prevCategory = lastAction.getCategory();
        double prevCost = lastAction.getCost();
        double prevPrice = lastAction.getPrice();
        int prevQuantity = lastAction.getQuantity();

        // Find the row of the product in the inventory table by iterating through rows
        int selectedRow = -1;
        for (int row = 0; row < inventoryTable.getRowCount(); row++) {
            if (inventoryTable.getValueAt(row, 0).toString().equals(prevName)) {
                selectedRow = row;
                break;
            }
        }

        if (selectedRow != -1) {
            // Revert the product details in the table
            inventoryTable.setValueAt(prevName, selectedRow, 0);
            inventoryTable.setValueAt(prevCategory, selectedRow, 1);
            inventoryTable.setValueAt(prevCost, selectedRow, 2);
            inventoryTable.setValueAt(prevPrice, selectedRow, 3);
            inventoryTable.setValueAt(prevQuantity, selectedRow, 4);

            // Revert the product details in the linked list
            ProductNode current = productList.head;
            while (current != null) {
                if (current.getName().equals(prevName)) {
                    current.setName(prevName);
                    current.setCategory(prevCategory);
                    current.setCost(prevCost);
                    current.setPrice(prevPrice);
                    current.setQuantity(prevQuantity);
                    break;
                }
                current = current.next;
            }

            // Revert in-stock value based on the previous quantity
            int currentQuantity = Integer.parseInt(inventoryTable.getValueAt(selectedRow, 4).toString());
            dashboardData.put("In-Stock", (int) dashboardData.get("In-Stock") - (currentQuantity - prevQuantity));
            labelInStockValue.setText(String.valueOf(dashboardData.get("In-Stock")));

            // Remove the record from history
            for (int i = 0; i < historyTableModel.getRowCount(); i++) {
                if (historyTableModel.getValueAt(i, 1).equals(current.getName())) {
                    historyTableModel.removeRow(i);  // Remove the row from the table
                    historyTable.repaint();
                    break;  // Exit after removing the product
                }
            }
        
            // Repaint the inventory table and history table
            refreshInventoryTable();
            historyTable.repaint();
        }
    }
    
    
    public void redoUpdateProduct(Action lastAction) {
        // Get the updated product details from the last action
        String updatedName = lastAction.getUpdatedProductName();
        String updatedCategory = lastAction.getUpdatedCategory();
        double updatedCost = lastAction.getUpdatedCost();
        double updatedPrice = lastAction.getUpdatedPrice();
        int updatedQuantity = lastAction.getUpdatedQuantity();

        // Find the row of the product in the inventory table by iterating through rows
        int selectedRow = -1;
        for (int row = 0; row < inventoryTable.getRowCount(); row++) {
            if (inventoryTable.getValueAt(row, 0).toString().equals(updatedName)) {
                selectedRow = row;
                break;
            }
        }

        if (selectedRow != -1) {
            // Revert the product details in the table with the updated values
            inventoryTable.setValueAt(updatedName, selectedRow, 0);
            inventoryTable.setValueAt(updatedCategory, selectedRow, 1);
            inventoryTable.setValueAt(updatedCost, selectedRow, 2);
            inventoryTable.setValueAt(updatedPrice, selectedRow, 3);
            inventoryTable.setValueAt(updatedQuantity, selectedRow, 4);

            // Revert the product details in the linked list with the updated values
            ProductNode current = productList.head;
            while (current != null) {
                if (current.getName().equals(updatedName)) {
                    current.setName(updatedName);
                    current.setCategory(updatedCategory);
                    current.setCost(updatedCost);
                    current.setPrice(updatedPrice);
                    current.setQuantity(updatedQuantity);
                    break;
                }
                current = current.next;
            }

            // Revert in-stock value based on the updated quantity
            int currentQuantity = Integer.parseInt(inventoryTable.getValueAt(selectedRow, 4).toString());
            dashboardData.put("In-Stock", (int) dashboardData.get("In-Stock") + (updatedQuantity - currentQuantity));
            labelInStockValue.setText(String.valueOf(dashboardData.get("In-Stock")));

            // Repaint the inventory table and history table
            refreshInventoryTable();
            historyTable.repaint();
        }
    }




    
    
    
    
    public void deleteProduct() {
        int selectedRow = inventoryTable.getSelectedRow();
        if (selectedRow != -1) {
            String removedName = inventoryTable.getValueAt(selectedRow, 0).toString();
            int removedProducts = Integer.parseInt(inventoryTable.getValueAt(selectedRow, 4).toString());

            // Remove the product from the table
            DefaultTableModel model = (DefaultTableModel) inventoryTable.getModel();
            model.removeRow(selectedRow);

            // Update inventory stock
            dashboardData.put("In-Stock", (int)dashboardData.get("In-Stock") - removedProducts);
            labelInStockValue.setText(String.valueOf(dashboardData.get("In-Stock")));

            // Remove the product from the linked list
            ProductNode current = productList.head;
            while (current != null) {
                if (current.getName().equals(removedName)) {
                    productList.removeProduct(current); // Assuming you have a remove method in the linked list
                    break; // Exit once the product is removed
                }
                current = current.next;
            }

            // Create an Action for the "DELETE" event
            Action deleteAction = new Action("DELETE", removedName, "", 0, 0, removedProducts);

            // Add the delete action to the history
            addActionToHistory(deleteAction);

        } else {
            JOptionPane.showMessageDialog(null, "No product selected");
        }

        // clear redoStack if action is new
        while (!redoStack.isEmpty()) {
            redoStack.pop();
        }
    }

    
    // Undo the delete action
    public void undoDeleteProduct(Action lastAction) {
        // Get the details of the deleted product from the last action
        String productName = lastAction.getProductName();
        int quantity = lastAction.getQuantity();
        String category = lastAction.getCategory(); // Assuming you saved category in Action
        double cost = lastAction.getCost(); // Assuming you saved cost in Action
        double price = lastAction.getPrice(); // Assuming you saved price in Action

        // Re-add the product to the linked list
        ProductNode newNode = new ProductNode(productName, category, cost, price, quantity);
        productList.addProduct(newNode);  // Assuming addProduct method adds the product to the linked list

        // Restore the in-stock value
        dashboardData.put("In-Stock", (int) dashboardData.get("In-Stock") + quantity);
        labelInStockValue.setText(String.valueOf(dashboardData.get("In-Stock")));
        
        // Remove the record from history
        for (int i = 0; i < historyTableModel.getRowCount(); i++) {
            if (historyTableModel.getValueAt(i, 1).equals(productName)) {
                historyTableModel.removeRow(i);  // Remove the row from the table
                historyTable.repaint();
                break;  // Exit after removing the product
            }
        }

        // Repaint the inventory table and history table
        refreshInventoryTable();
        historyTable.repaint();
    }
    

    // Redo the delete action
    public void redoDeleteProduct(Action lastAction) {
        // Get the details of the deleted product from the last action
        String productName = lastAction.getProductName();
        int quantity = lastAction.getQuantity();

        // Remove the product from the table
        int selectedRow = getProductRowByName(productName);  // We will implement this below
        if (selectedRow != -1) {
            DefaultTableModel model = (DefaultTableModel) inventoryTable.getModel();
            model.removeRow(selectedRow);
        }

        // Remove the product from the linked list
        ProductNode current = productList.head;
        while (current != null) {
            if (current.getName().equals(productName)) {
                productList.removeProduct(current); // Assuming you have a removeProduct method in the linked list
                break;
            }
            current = current.next;
        }

        // Update the in-stock value
        dashboardData.put("In-Stock", (int) dashboardData.get("In-Stock") - quantity);
        labelInStockValue.setText(String.valueOf(dashboardData.get("In-Stock")));

        // Repaint the inventory table and history table
        refreshInventoryTable();
        historyTable.repaint();
    }

    // Method to get the row index of a product by its name in the inventory table
    private int getProductRowByName(String productName) {
        for (int i = 0; i < inventoryTable.getRowCount(); i++) {
            String currentProductName = inventoryTable.getValueAt(i, 0).toString();
            if (currentProductName.equals(productName)) {
                return i; // Return the row index if the product name matches
            }
        }
        return -1; // Return -1 if the product is not found
    }

    
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // run program
                Main program = new Main();                
            }
        });
    }
}




/*  
 * CustomQueue.java  
 *  
 * A custom queue implementation designed to manage the queue of days in the profit chart.  
 * This queue keeps track of data for the last 7 days, ensuring a rolling window of  
 * recent profit information. It supports enqueue and dequeue operations to efficiently  
 * update the profit chart data.  
 */  

class CustomQueue<T> {
    private Object[] elements;
    private int front, rear, size, capacity;

    public CustomQueue(int capacity) {
        this.capacity = capacity;
        this.elements = new Object[capacity];
        this.front = 0;
        this.rear = -1;
        this.size = 0;
    }

    public void enqueue(T item) {
        if (size == capacity) {
            dequeue(); // Dequeue the oldest item if the queue is full
        }
        rear = (rear + 1) % capacity;
        elements[rear] = item;
        size++;
    }

    @SuppressWarnings("unchecked")
    public T dequeue() {
        if (size == 0) {
            throw new IllegalStateException("Queue is empty");
        }
        T item = (T) elements[front];
        front = (front + 1) % capacity;
        size--;
        return item;
    }

    @SuppressWarnings("unchecked")
    public T peek(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        return (T) elements[(front + index) % capacity];
    }

    public int size() {
        return size;
    }
}




/*  
 * Action.java  
 *  
 * A class that represents an action performed by the user and the product it affects.  
 * Each action includes details like product name, category, cost, price, and quantity.  
 * It is used in Stack and RedoStack for managing undo/redo operations, ensuring  
 * precise tracking and restoration of user modifications.  
 */  

class Action {
    private String type;         // Action type (e.g., "ADD", "SOLD")
    private String productName;  // Name of the product involved in the action
    private String category;     // Category of the product
    private double cost;         // Cost of the product
    private double price;        // Price of the product
    private int quantity;        // Quantity involved in the action
    
    private String updatedProductName;
    private String updatedCategory;
    private double updatedCost;
    private double updatedPrice;
    private int updatedQuantity;

    // Constructor for Action (for update actions, including both original and updated values)
    public Action(String actionType, String productName, String category, double cost, double price, int quantity, 
                  String updatedProductName, String updatedCategory, double updatedCost, double updatedPrice, int updatedQuantity) {
        this.type = actionType;
        this.productName = productName;
        this.category = category;
        this.cost = cost;
        this.price = price;
        this.quantity = quantity;

        this.updatedProductName = updatedProductName;
        this.updatedCategory = updatedCategory;
        this.updatedCost = updatedCost;
        this.updatedPrice = updatedPrice;
        this.updatedQuantity = updatedQuantity;
    }


    // Constructor
    public Action(String type, String productName, String category, double cost, double price, int quantity) {
        this.type = type;
        this.productName = productName;
        this.category = category;
        this.cost = cost;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters for each field
    public String getType() {
        return type;
    }

    public String getProductName() {
        return productName;
    }

    public String getCategory() {
        return category;
    }

    public double getCost() {
        return cost;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
    
    // Getters for updated product values
    public String getUpdatedProductName() {
        return updatedProductName;
    }

    public String getUpdatedCategory() {
        return updatedCategory;
    }

    public double getUpdatedCost() {
        return updatedCost;
    }

    public double getUpdatedPrice() {
        return updatedPrice;
    }

    public int getUpdatedQuantity() {
        return updatedQuantity;
    }
}




/*  
 * BarGraphPanel.java  
 *  
 * A panel used to visually display the profit chart data as a bar graph for the last 7 days.  
 * It retrieves data from the CustomQueue, ensuring accurate and dynamic representation  
 * of the most recent profit trends.  
 */  

class BarGraphPanel extends JPanel {
    // QUEUE: Profit Data
    private ProfitQueue profits;
    private CustomQueue<String> days;
    private String title = "Daily Profit (last 7 days)";
    private int hoveredIndex = -1; // To track the hovered index for tooltips

    public BarGraphPanel() {
        setPreferredSize(new Dimension(250, 200)); // Adjust panel size
        setBackground(new Color(220, 240, 240));
        setBorder(BorderFactory.createLineBorder(new Color(220, 240, 240), 2));

        // Initialize queues for profits and days
        profits = new ProfitQueue(7);
        days = new CustomQueue<>(7);

        // Add initial profits
        double[] initialProfits = {950.00, 1150.00, 800.00, 1050.00, 975.00, 1200.00, 1921.29};
        for (double profit : initialProfits) {
            profits.enqueue(profit);
        }

        // Add initial days
        String[] initialDays = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        for (String day : initialDays) {
            days.enqueue(day);
        }

        // Timer to update profits and rotate days
        Timer timer = new Timer(2500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Dequeue the oldest day and profit, then enqueue new data
                String lastDay = days.dequeue();
                profits.dequeue();

                // Enqueue new day and profit
                days.enqueue(lastDay);
                profits.enqueue(800 + Math.random() * 1200); // Random profit

                repaint();
            }
        });
        timer.start();

        // MouseListener for hovering effect
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent e) {
                int barWidth = getWidth() / profits.size();
                int mouseX = e.getX();
                hoveredIndex = -1;
                for (int i = 0; i < profits.size(); i++) {
                    int x = i * barWidth + barWidth / 2;
                    if (Math.abs(mouseX - x) < barWidth / 2) {
                        hoveredIndex = i;
                        break;
                    }
                }
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGraph(g);
    }

    public void drawGraph(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Get the width and height of the panel
        int width = getWidth();
        int height = getHeight();

        // Calculate the width of each bar
        int barWidth = width / profits.size();  

        // Find the maximum profit for scaling the graph
        double maxProfit = 0;
        for (int i = 0; i < profits.size(); i++) {
            double profit = profits.peek(i);  // Use peek() to get the value from the queue
            if (profit > maxProfit) {
                maxProfit = profit;
            }
        }

        // Draw the bars
        for (int i = 0; i < profits.size(); i++) {
            double profit = profits.peek(i);  // Use peek() to get the value from the queue
            int barHeight = (int) ((profit / 2000) * (height - 100)); // Scale bar height
            int x = i * barWidth;
            int y = height - barHeight - 60;

            // Draw bars with light blue color
            g2d.setColor(new Color(72, 145, 220));
            g2d.fillRect(x + 10, y, barWidth - 20, barHeight); // Adjusted bar thickness

            // Draw profit value above each bar
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.PLAIN, 9)); // Smaller text
            g2d.drawString(String.format("%.2f", profit), x + (barWidth / 2) - 12, y - 10);

            // Draw tooltip if hovered
            if (i == hoveredIndex && i != 0) {
                double profit1 = profits.peek(i);
                double profit2 = profits.peek(i - 1);
                String tooltip;
                if (profit1 > profit2) {
                    tooltip = String.format("+%.2f", profit1-profit2);
                    g2d.setColor(new Color(200, 255, 200));
                } else if (profit2 > profit1) {
                    tooltip = String.format("-%.2f", profit2-profit1);
                    g2d.setColor(new Color(255, 220, 220));
                } else { 
                    tooltip = String.format("0.00");
                    g2d.setColor(new Color(255, 255, 220));
                }
                FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
                int tooltipWidth = metrics.stringWidth(tooltip) + 10;
                int tooltipHeight = metrics.getHeight() + 4;
                int tooltipX = x + (barWidth - tooltipWidth) / 2;
                int tooltipY = y - tooltipHeight - 5;
                g2d.fillRect(tooltipX, tooltipY, tooltipWidth, tooltipHeight);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(tooltipX, tooltipY, tooltipWidth, tooltipHeight);
                g2d.drawString(tooltip, tooltipX + 5, tooltipY + metrics.getAscent());
            }
        }

        // Draw x-axis
        g2d.setColor(Color.BLACK);
        g2d.drawLine(0, height - 60, width, height - 60);

        // Draw a background for the labels area (to clear previous labels)
        g2d.setColor(getBackground());  // Use the panel's background color
        g2d.fillRect(0, height - 50, width, 50); // Clear the area where labels will be drawn

        // Now draw the labels
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        for (int i = 0; i < days.size(); i++) {
            g2d.drawString(days.peek(i), (i * barWidth) + (barWidth / 2) - 10, height - 25);
        }

        // Draw title
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.setColor(new Color(84, 84, 84));
        g2d.drawString(title, (width - g2d.getFontMetrics().stringWidth(title)) / 2, 20);

        // Draw the line graph on top of the bars
        g2d.setColor(new Color(255, 165, 0)); // Orange color
        g2d.setStroke(new BasicStroke(3)); // Thicker line
        for (int i = 0; i < profits.size() - 1; i++) {
            double profit1 = profits.peek(i);  // Use peek() to get the value from the queue
            double profit2 = profits.peek(i + 1);  // Use peek() to get the value from the queue
            int x1 = (i * barWidth) + (barWidth / 2);
            int y1 = height - (int) ((profit1 / maxProfit) * (height - 100)) - 60;
            int x2 = ((i + 1) * barWidth) + (barWidth / 2);
            int y2 = height - (int) ((profit2 / maxProfit) * (height - 100)) - 60;
            g2d.drawLine(x1, y1, x2, y2);
        }
    }

}




/*  
 * PieChartPanel.java  
 *  
 * A panel used to display a pie chart that illustrates the share of each category.  
 * This provides users with a clear graphical representation of the inventory's  
 * category distribution.  
 */  

class PieChartPanel extends JPanel {

    // Category data
    private String[] categories = {"Electronics", "Food", "Accessories", "Office Supplies"};
    private int[] counts = {6, 6, 2, 1};
    private Color[] colors = {new Color(72, 145, 220), new Color(232, 77, 98), new Color(255, 165, 0), new Color(109, 191, 115)};
    private String title = "Category Distribution";

    // Constructor to set up the panel
    public PieChartPanel() {
        setPreferredSize(new Dimension(250, 200));
        setBackground(new Color(220, 240, 240)); 
        setBorder(BorderFactory.createLineBorder(new Color(220, 240, 240))); // Border color RGB(130, 184, 167)
    }

    // Method to draw the pie chart
    public void drawPieChart(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Get the width and height of the panel
        int width = getWidth();
        int height = getHeight();
        int minSize = Math.min(width, height);
        int cx = width / 2;
        int cy = height / 2;
        int radius = minSize / 3; // Adjusted radius for a smaller pie chart

        // Draw title
        g2d.setColor(new Color(84, 84, 84));
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        FontMetrics metrics = g2d.getFontMetrics();
        int titleWidth = metrics.stringWidth(title);
        g2d.drawString(title, (width - titleWidth) / 2, 15);

        // Draw pie slices
        double total = 0;
        for (int count : counts) {
            total += count;
        }

        double startAngle = 0;
        for (int i = 0; i < counts.length; i++) {
            double arcAngle = (counts[i] / total) * 360;
            g2d.setColor(colors[i]);
            g2d.fill(new Arc2D.Double(cx - radius, cy - radius, 2 * radius, 2 * radius, startAngle, arcAngle, Arc2D.PIE));

            // Calculate midpoint angle of the slice
            double midAngle = Math.toRadians(startAngle + arcAngle / 2);
            int labelRadius = radius + 20;
            int labelX = (int) (cx + labelRadius * Math.cos(midAngle));
            int labelY = (int) (cy + labelRadius * Math.sin(midAngle));

            // Draw percentage text
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.PLAIN, 10));
            String percentageText = String.format("%.1f%%", (counts[i] / total) * 100);
            g2d.drawString(percentageText, labelX - g2d.getFontMetrics().stringWidth(percentageText) / 2, labelY + 5);

            // Draw category text
            String categoryText = categories[i];
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            g2d.drawString(categoryText, labelX - g2d.getFontMetrics().stringWidth(categoryText) / 2, labelY - 5);

            // Draw arrow-like line indicator
            int arrowLength = 15;
            int arrowX = (int) (cx + (radius + arrowLength) * Math.cos(midAngle));
            int arrowY = (int) (cy + (radius + arrowLength) * Math.sin(midAngle));
            g2d.drawLine(cx, cy, arrowX, arrowY);

            startAngle += arcAngle;
        }
    }

    // Override the paintComponent method to call drawPieChart
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawPieChart(g);
    }
}