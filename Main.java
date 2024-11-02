/**
 * PRODUX: A BUSINESS INVENTORY MANAGEMENT SYSTEM
 * 
 * @author Barata, Nicko James E.
 * @co-author Bagtas, Miguel Grant V.
 * 
 * BSCS-SD1A 
 * CTCC-0323
 *
 */


import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Arc2D;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;




public class Main implements ActionListener, ListSelectionListener {
    // frame and panel components
    JFrame frame;
    JPanel panelLogin, panelBackground, panelNavigation, panelHeader;
    JPanel panelDashboard, panelInventory, panelHistory, panelAccount, panelAbout;
    
    // login and background components
    private ImageIcon bgImage;
    private JLabel bgImageHandler;
    private JLabel labelProdux, labelCompanyID, labelPassword, labelMessage;
    private JTextField fieldCompanyID;
    private JPasswordField fieldPassword;
    private JButton buttonLogIn, buttonRegister;
    private JCheckBox showPassword;
    private JTextField nameField, categoryField, priceField, quantityField;
    private JTextField updateNameField, updateCategoryField;
    private JTextField updatePriceField, updateQuantityField;
    
    // navigation components
    private JLabel appNameLabel;
    private JLabel companyName;
    private JButton dashboardNavButton, inventoryNavButton, historyNavButton;
    private JButton accountNavButton, aboutNavButton;
    
    // dashboard components
    private JLabel labelProductSoldValue,labelRevenueValue, labelInStockValue;
    private int numSoldProducts = 832;
    private double revenue = 7450.49;
    private int inStock = 2671;
    ArrayList<Double> dailyProfitData = new ArrayList<Double>();
    
    // inventory components
    private JPanel tablePanel, detailPanel;
    private JLabel labelHeaderTable, labelDetailProduct, labelDetailCategory;
    private JLabel labelDetailCost, labelDetailPrice, labelDetailQuantity;
    private JTextField fieldHeaderTable, fieldDetailProduct, fieldDetailCategory;
    private JTextField fieldDetailCost, fieldDetailPrice, fieldDetailQuantity;
    private JButton addButton, soldButton, updateButton, deleteButton;
    private JButton buttonAdd, buttonSold, buttonUpdate, buttonRemove;
    private JTable productTable;
    private DefaultTableModel tableModel;
    
    // history components
    private JPanel historyTablePanel;
    private JLabel labelHistoryHeader;
    private JTable historyTable;
    private DefaultTableModel historyTableModel;
    
    // data holder
    HashMap<String, String> accounts = new HashMap<String, String>();
    ArrayList<String> existingAccounts = new ArrayList<String>();
    ArrayList<Product> productList = new ArrayList<Product>();
    
    // color objects
    private final Color defaultButtonColor = new Color(44, 54, 59);
    private final Color selectedButtonColor = new Color(102, 150, 134);
    private final Color highlightColor = new Color(200, 200, 200); // Lighter color for highlight
    private final Color loginPanelBgColor = new Color(54, 69, 77);
    private final Color textColor = new Color(228, 239, 240);

    // other properties
    private int WIDTH = 900;
    private int HEIGHT = 600;
    String headerText;
    
    
    
    
    public Main() {
        // initialize Data and Components
        initializeData();
        initializeLoginPanel();
        initializeBackgroundPanel();
        initializeNavigationPanel();
        initializeHeaderPanel();
        initializeDashboardPanel();
        initializeInventoryPanel();
        initializeHistoryPanel();
        initializeAboutPanel();
        initializeAccountPanel();
        initializeFrame();
        
        // set login and background panel visible to true
        panelLogin.setVisible(true);
        panelBackground.setVisible(true);
    }
    
    
    
    public void initializeFrame() {
        // frame properties
        frame = new JFrame("Produx: A Business Inventory Management System");
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocation(500, 175);
        frame.setLocationRelativeTo(null);
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
    }
    
    
    
    public void initializeBackgroundPanel() {
        // panel properties
        panelBackground = new JPanel();
        panelLogin.setLayout(null);
        panelBackground.setBounds(0, 0, WIDTH, HEIGHT);
        
        // background image
        ImageIcon bgImageFile = new ImageIcon("images/background_login.jpg");
        Image bgImage = bgImageFile.getImage();
        Image scaledImage = bgImage.getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
        ImageIcon background = new ImageIcon(scaledImage);
        JLabel bgHandle = new JLabel(background);
        bgHandle.setBounds(0, 0, 900, 600);
        panelBackground.add(bgHandle);
        panelBackground.setVisible(true);
    }
    
    
    
    public void initializeLoginPanel() {
        // login panel properties
        panelLogin = new JPanel();
        panelLogin.setLayout(null);
        panelLogin.setBounds(WIDTH / 3, HEIGHT / 4 - 60, WIDTH / 3, HEIGHT / 2 + 50);
        panelLogin.setBackground(loginPanelBgColor);
        panelLogin.setVisible(true);
        
        // create components
        labelProdux = new JLabel("LOG IN");
        labelCompanyID = new JLabel("Company ID:");
        labelPassword = new JLabel("Password:");
        labelMessage = new JLabel("");
        fieldCompanyID = new JTextField();
        fieldPassword = new JPasswordField();
        showPassword = new JCheckBox("Show Password");
        buttonLogIn = new JButton("Log In");
        buttonRegister = new JButton("Register");

        // set fonts
        labelProdux.setFont(new Font("Poppins", Font.BOLD, 34));
        labelCompanyID.setFont(new Font("Poppins", Font.BOLD, 14));
        labelPassword.setFont(new Font("Poppins", Font.BOLD, 14));
        labelMessage.setFont(new Font("Poppins", Font.BOLD, 12));
        fieldCompanyID.setFont(new Font("Lato", Font.PLAIN, 12));
        fieldPassword.setFont(new Font("Lato", Font.PLAIN, 12));
        showPassword.setFont(new Font("Poppins", Font.PLAIN, 12));
        buttonLogIn.setFont(new Font("Lato", Font.BOLD, 16));
        buttonRegister.setFont(new Font("Lato", Font.PLAIN, 14));
        labelProdux.setHorizontalAlignment(SwingConstants.CENTER);
        labelMessage.setHorizontalAlignment(SwingConstants.CENTER);

        // set colors
        labelProdux.setForeground(new Color(146, 222, 192));
        labelProdux.setBackground(loginPanelBgColor);
        labelCompanyID.setForeground(textColor);
        labelPassword.setForeground(textColor);
        labelMessage.setForeground(textColor);
        fieldCompanyID.setForeground(Color.BLACK);
        fieldPassword.setForeground(Color.BLACK);
        showPassword.setForeground(textColor);
        showPassword.setBackground(loginPanelBgColor);
        buttonLogIn.setForeground(textColor);
        buttonLogIn.setBackground(selectedButtonColor);
        buttonRegister.setForeground(textColor);
        buttonRegister.setBackground(defaultButtonColor);
        
        // set focus and border painted to false
        showPassword.setFocusPainted(false);
        showPassword.setBorderPainted(false);
        buttonLogIn.setFocusPainted(false);
        buttonLogIn.setBorderPainted(false);
        buttonRegister.setFocusPainted(false);
        buttonRegister.setBorderPainted(true);

        // modern text field look
        fieldCompanyID.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        fieldPassword.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        fieldCompanyID.setBackground(new Color(242, 245, 255));
        fieldPassword.setBackground(new Color(242, 245, 255));
        
        // setBounds and add components
        int labelWidth = 100;
        int fieldWidth = 200;
        int height = 30;
        int verticalSpacing = 10;
        int xLabel = (WIDTH / 3 - fieldWidth) / 2 - 3;
        int xField = xLabel;
        int yStart = 75;
        int buttonWidth = 95;
            
        labelProdux.setBounds(75, 15, 150, 50);
        labelCompanyID.setBounds(xLabel, yStart, labelWidth, height);
        fieldCompanyID.setBounds(xField, yStart + height + 5, fieldWidth, height);
        labelPassword.setBounds(xLabel, yStart + 2 * (height + verticalSpacing), labelWidth, height);
        fieldPassword.setBounds(xField, yStart + 3 * (height + verticalSpacing)-6, fieldWidth, height);
        showPassword.setBounds(xField, yStart + 4 * (height + verticalSpacing-2)-5, fieldWidth, height);
        labelMessage.setBounds(0, yStart + 6 * (height + verticalSpacing), 300, height);
        buttonLogIn.setBounds(xField, yStart + 5 * (height + verticalSpacing)-5, buttonWidth, 40);
        buttonRegister.setBounds(xField + buttonWidth + 10, yStart + 5 * (height + verticalSpacing)-5, buttonWidth, 40);

        // add components to panel
        panelLogin.add(labelProdux);
        panelLogin.add(labelCompanyID);
        panelLogin.add(fieldCompanyID);
        panelLogin.add(labelPassword);
        panelLogin.add(fieldPassword);
        panelLogin.add(showPassword);
        panelLogin.add(labelMessage);
        panelLogin.add(buttonLogIn);
        panelLogin.add(buttonRegister);

        // add action listeners
        buttonLogIn.addActionListener(this);
        buttonRegister.addActionListener(this);
        showPassword.addActionListener(this);  
        
        panelLogin.setVisible(true);
    }
    
    
    
    public void initializeNavigationPanel() {
        // navigation panel properties
        panelNavigation = new JPanel();
        panelNavigation = new JPanel();
        panelNavigation.setLayout(null);
        panelNavigation.setBounds(0, 0, WIDTH / 5, HEIGHT);
        panelNavigation.setBackground(new Color(71, 93, 105));

        // initialize components
        appNameLabel = new JLabel("PRODUX");
        appNameLabel.setFont(new Font("Poppins", Font.BOLD, 26));
        appNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        appNameLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        appNameLabel.setForeground(new Color(146, 222, 192));
        appNameLabel.setBounds(33, 0, 180, 80);
        panelNavigation.add(appNameLabel);
        JSeparator appNameSeparator = new JSeparator();
        appNameSeparator.setForeground(new Color(50, 50, 50));

        // create modern navigation buttons
        dashboardNavButton = createModernButton("Dashboard");
        inventoryNavButton = createModernButton("Inventory");
        historyNavButton = createModernButton("History");
        accountNavButton = createModernButton("Account");
        aboutNavButton = createModernButton("About");

        // setbounds and positioning
        dashboardNavButton.setBounds(0, 80, 180, 40);
        inventoryNavButton.setBounds(0, 120, 180, 40);
        historyNavButton.setBounds(0, 160, 180, 40);
        accountNavButton.setBounds(0, 200, 180, 40);
        aboutNavButton.setBounds(0, 240, 180, 40);

        // border for modern button
        dashboardNavButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 2));
        inventoryNavButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 14));
        historyNavButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 32));
        accountNavButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 23));
        aboutNavButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 39));

        // add components to panel
        panelNavigation.add(dashboardNavButton);
        panelNavigation.add(inventoryNavButton);
        panelNavigation.add(historyNavButton);
        panelNavigation.add(accountNavButton);
        panelNavigation.add(aboutNavButton);

        // add action listeners to navigation buttons
        dashboardNavButton.addActionListener(this);
        inventoryNavButton.addActionListener(this);
        historyNavButton.addActionListener(this);
        accountNavButton.addActionListener(this);
        aboutNavButton.addActionListener(this);

        panelNavigation.setVisible(false);
    }
    
    
    
    public void initializeHeaderPanel() {
        // header panel properties
        panelHeader = new JPanel();
        panelHeader.setLayout(null);
        panelHeader.setBackground(new Color(230, 230, 230));
        panelHeader.setBounds(WIDTH / 5, 0, WIDTH - (WIDTH / 5), 50);
        
        // initialize components
        companyName = new JLabel("Bagtas Retail Company Ltd.");
        companyName.setFont(new Font("Poppins", Font.PLAIN, 18));
        companyName.setForeground(Color.GRAY);
        companyName.setHorizontalAlignment(SwingConstants.CENTER);
        companyName.setBounds(0, 0, 720, 50);
        panelHeader.add(companyName);
        
        panelHeader.setVisible(false);
    }
    
    
    
    public void initializeDashboardPanel() {
        // dashboard panel properties
        panelDashboard = new JPanel();
        panelDashboard.setLayout(null);
        panelDashboard.setBackground(new Color(209, 222, 222));
        panelDashboard.setBounds(WIDTH / 5, 50, WIDTH - (WIDTH / 5), HEIGHT);
        panelDashboard.setVisible(false);
        
        // three dashboard card and two visualizations
        JPanel productSoldPanel = new JPanel();
        JPanel revenuePanel = new JPanel();
        JPanel inStockPanel = new JPanel();
        JPanel profitChartPanel = new JPanel();
        JPanel pieChartPanel = new JPanel();
        
        // set color for each panel outline layout
        Color cardColor = new Color(250, 255, 253);
        productSoldPanel.setBackground(cardColor);
        revenuePanel.setBackground(cardColor);
        inStockPanel.setBackground(cardColor);
        profitChartPanel.setBackground(cardColor);
        pieChartPanel.setBackground(cardColor);
        
        // setBounds for each panel position and size
        productSoldPanel.setBounds(65, 75, 150, 100);
        revenuePanel.setBounds(275, 75, 150, 100);
        inStockPanel.setBounds(485, 75, 150, 100);
        profitChartPanel.setBounds(65, 225, 250, 200);
        pieChartPanel.setBounds(385, 225, 250, 200);
        
        // set colors
        productSoldPanel.setBackground(new Color(168, 96, 219, 200));
        revenuePanel.setBackground(new Color(96, 219, 116, 200));
        inStockPanel.setBackground(new Color(90, 166, 242, 200));
        
        // set layout to null
        productSoldPanel.setLayout(null);
        revenuePanel.setLayout(null);
        inStockPanel.setLayout(null);
        
        // product sold panel card
        JLabel labelProductSold = new JLabel("Product Sold");
        labelProductSoldValue = new JLabel(String.valueOf(numSoldProducts));
        labelProductSold.setForeground(textColor);
        labelProductSold.setBackground(new Color(168, 96, 219, 250));
        labelProductSold.setOpaque(true);
        labelProductSoldValue.setForeground(textColor);
        labelProductSold.setFont(new Font("Poppins", Font.BOLD, 20));
        labelProductSoldValue.setFont(new Font("Poppins", Font.BOLD, 32));
        labelProductSold.setHorizontalAlignment(SwingConstants.CENTER);
        labelProductSoldValue.setHorizontalAlignment(SwingConstants.CENTER);
        labelProductSold.setBounds(0, 0, 150, 40);
        labelProductSoldValue.setBounds(0, 47, 150, 40);
        productSoldPanel.add(labelProductSold);
        productSoldPanel.add(labelProductSoldValue);
        
        // revenue panel card
        JLabel labelRevenue = new JLabel("Revenue");
        labelRevenueValue = new JLabel("$"+String.valueOf(revenue));
        labelRevenue.setForeground(textColor);
        labelRevenue.setBackground(new Color(96, 219, 116, 250));
        labelRevenue.setOpaque(true);
        labelRevenueValue.setForeground(textColor);
        labelRevenue.setFont(new Font("Poppins", Font.BOLD, 20));
        labelRevenueValue.setFont(new Font("Poppins", Font.BOLD, 32));
        labelRevenue.setHorizontalAlignment(SwingConstants.CENTER);
        labelRevenueValue.setHorizontalAlignment(SwingConstants.CENTER);
        labelRevenue.setBounds(0, 0, 150, 40);
        labelRevenueValue.setBounds(0, 47, 150, 40);
        revenuePanel.add(labelRevenue);
        revenuePanel.add(labelRevenueValue);
        
        // inStock panel card
        JLabel labelInStock = new JLabel("In-Stock");
        labelInStockValue = new JLabel(String.valueOf(inStock));
        labelInStock.setForeground(textColor);
        labelInStock.setBackground(new Color(90, 166, 242, 250));
        labelInStock.setOpaque(true);
        labelInStockValue.setForeground(textColor);
        labelInStock.setFont(new Font("Poppins", Font.BOLD, 20));
        labelInStockValue.setFont(new Font("Poppins", Font.BOLD, 32));
        labelInStock.setHorizontalAlignment(SwingConstants.CENTER);
        labelInStockValue.setHorizontalAlignment(SwingConstants.CENTER);
        labelInStock.setBounds(0, 0, 150, 40);
        labelInStockValue.setBounds(0, 47, 150, 40);
        inStockPanel.add(labelInStock);
        inStockPanel.add(labelInStockValue);
        
        // profitChartPanel
        BarGraphPanel profitGraph = new BarGraphPanel();
        profitGraph.setBounds(0, 0, 250, 200);
        profitChartPanel.setBackground(new Color(101, 133, 135));
        profitChartPanel.add(profitGraph);
        
        // pieChartPanel
        PieChartPanel pieGraph = new PieChartPanel();
        pieGraph.setBounds(0, 0, 250, 200);
        pieChartPanel.setBackground(new Color(101, 133, 135));
        pieChartPanel.add(pieGraph);
        
        // add to panelDashboard
        panelDashboard.add(productSoldPanel);
        panelDashboard.add(revenuePanel);
        panelDashboard.add(inStockPanel);
        panelDashboard.add(profitChartPanel);
        panelDashboard.add(pieChartPanel);
        
        panelDashboard.setVisible(false);
    }
    
    
    
    public void initializeInventoryPanel() {
        // inventory panel properties
        panelInventory = new JPanel();
        panelInventory.setLayout(null);
        panelInventory.setBackground(new Color(209, 222, 222));
        panelInventory.setBounds(WIDTH / 5, 50, WIDTH - (WIDTH / 5), HEIGHT);
        panelInventory.setVisible(false);
        
        // initialize panels and components
        tablePanel = new JPanel();
        detailPanel = new JPanel();
        labelHeaderTable = new JLabel("PRODUCT INVENTORY");
        addButton = new JButton("ADD");
        soldButton = new JButton("SOLD");                
        updateButton = new JButton("UPDATE");
        deleteButton = new JButton("DELETE");
        
        // setBounds for each panel positions and size
        tablePanel.setBounds(50, 125, 350, 350);
        detailPanel.setBounds(425, 125, 225, 275);
        labelHeaderTable.setBounds(50, 60, 300, 50);
        addButton.setBounds(435, 415, 80, 40);
        soldButton.setBounds(560, 415, 80, 40);
        updateButton.setBounds(25, 200, 87, 30);
        deleteButton.setBounds(112, 200, 88, 30);

        // constumize buttons
        addButton.setFocusPainted(false);
        soldButton.setFocusPainted(false);
        addButton.setBorderPainted(false);
        soldButton.setBorderPainted(false);
        addButton.setForeground(textColor);
        addButton.setBackground(selectedButtonColor);
        soldButton.setForeground(textColor);
        soldButton.setBackground(defaultButtonColor);

        // table header
        labelHeaderTable.setForeground(new Color(15, 34, 51));
        labelHeaderTable.setFont(new Font("Poppins", Font.BOLD, 18));
        labelHeaderTable.setHorizontalAlignment(SwingConstants.LEFT);
        
        // detail Panel
        detailPanel.setLayout(null);
        JLabel labelDetails = new JLabel("Product");
        labelDetails.setBounds(0, 0, 225, 40);
        labelDetails.setFont(new Font("Poppins", Font.BOLD, 20));
        labelDetails.setHorizontalAlignment(SwingConstants.CENTER);
        detailPanel.add(labelDetails);
        
        // labels for detail panel
        labelDetailProduct = new JLabel("Product:");
        labelDetailCategory = new JLabel("Category:");
        labelDetailCost = new JLabel("Cost:");
        labelDetailPrice = new JLabel("Price:");
        labelDetailQuantity = new JLabel("Quantity:");

        // text fields for detail panel
        fieldDetailProduct = new JTextField();
        fieldDetailCategory = new JTextField();
        fieldDetailCost = new JTextField();
        fieldDetailPrice = new JTextField();
        fieldDetailQuantity = new JTextField();
        
        // set bounds
        labelDetailProduct.setBounds(25, 50, 75, 20);
        labelDetailCategory.setBounds(25, 80, 75, 20);
        labelDetailCost.setBounds(25, 110, 75, 20);
        labelDetailPrice.setBounds(25, 140, 75, 20);
        labelDetailQuantity.setBounds(25, 170, 75, 20);
        
        // set bounds
        fieldDetailProduct.setBounds(100, 50, 100, 20);
        fieldDetailCategory.setBounds(100, 80, 100, 20);
        fieldDetailCost.setBounds(100, 110, 100, 20);
        fieldDetailPrice.setBounds(100, 140, 100, 20);
        fieldDetailQuantity.setBounds(100, 170, 100, 20);
        
        // set color and fonts
        detailPanel.setBackground(new Color(190, 205, 207));
        labelDetailProduct.setFont(new Font("Poppins", Font.BOLD, 14));
        labelDetailCategory.setFont(new Font("Poppins", Font.BOLD, 14));
        labelDetailCost.setFont(new Font("Poppins", Font.BOLD, 14));
        labelDetailPrice.setFont(new Font("Poppins", Font.BOLD, 14));
        labelDetailQuantity.setFont(new Font("Poppins", Font.BOLD, 14));
        
        labelDetailProduct.setForeground(new Color(71, 71, 71));
        labelDetailCategory.setForeground(new Color(71, 71, 71));
        labelDetailCost.setForeground(new Color(71, 71, 71));
        labelDetailPrice.setForeground(new Color(71, 71, 71));
        labelDetailQuantity.setForeground(new Color(71, 71, 71));
        
        // add components to detail panel
        detailPanel.add(labelDetailProduct);
        detailPanel.add(labelDetailCategory);
        detailPanel.add(labelDetailCost);
        detailPanel.add(labelDetailPrice);
        detailPanel.add(labelDetailQuantity);
        detailPanel.add(fieldDetailProduct);
        detailPanel.add(fieldDetailCategory);
        detailPanel.add(fieldDetailCost);
        detailPanel.add(fieldDetailPrice);
        detailPanel.add(fieldDetailQuantity);
        
        // helper method for refactoring
        _initializeInventoryTable();   
    }
    
    
    
    public void _initializeInventoryTable() {
        // table panel properties
        String[] headers = {"Products", "Category", "Cost", "Price", "Quantity"};
        tableModel = new DefaultTableModel(headers, 0);
        productTable = new JTable(tableModel);
        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        productTable.setFillsViewportHeight(true);
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        productTable.setRowSorter(sorter);
        productTable.getTableHeader().setReorderingAllowed(false);
        productTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        productTable.setDefaultEditor(Object.class, null);
        
        // set width for each column
        productTable.getColumnModel().getColumn(0).setPreferredWidth(100); // Products
        productTable.getColumnModel().getColumn(1).setPreferredWidth(80);  // Category
        productTable.getColumnModel().getColumn(2).setPreferredWidth(60);  // Cost Price
        productTable.getColumnModel().getColumn(3).setPreferredWidth(60);  // Selling Price
        productTable.getColumnModel().getColumn(4).setPreferredWidth(60);  // Quantity
        
        // table selection function
        productTable.getSelectionModel().addListSelectionListener(this);
        
        // inventory table dimensions
        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setPreferredSize(new Dimension(350, 350));
        tablePanel.add(scrollPane);
        tablePanel.setBackground(new Color(101, 133, 135));
        tablePanel.setBorder(new LineBorder(new Color(101, 133, 135), 2));                   
        
        // add action listener to each event
        addButton.addActionListener(this);
        soldButton.addActionListener(this);
        updateButton.addActionListener(this);
        deleteButton.addActionListener(this);
        
        // add components to panelInventory
        panelInventory.add( addButton);
        panelInventory.add( soldButton);
        detailPanel.add(updateButton);
        detailPanel.add(deleteButton);
        panelInventory.add(labelHeaderTable);
        panelInventory.add(tablePanel);
        panelInventory.add(detailPanel);
        
        panelInventory.setVisible(false);
    }
    
    
    
    public void initializeHistoryPanel() {
        // history panel properties
        panelHistory = new JPanel();
        panelHistory.setLayout(null);
        panelHistory.setBackground(new Color(209, 222, 222));
        panelHistory.setBounds(WIDTH / 5, 50, WIDTH - (WIDTH / 5), HEIGHT);
        panelHistory.setVisible(false);        
        
        // history table panel
        historyTablePanel = new JPanel();
        historyTablePanel.setBounds(50, 50, 620, 400);
        panelHistory.add(historyTablePanel);
        
        // history table properties
        String[] headers = {"Action", "Products", "Category", "Cost", "Price", "Quantity"};
        historyTableModel = new DefaultTableModel(headers, 0);
        historyTable = new JTable(historyTableModel);
//        historyTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        historyTable.setFillsViewportHeight(true);
//        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
//        historyTable.setRowSorter(sorter);
        
        // history table costumization, not selectable and editable
        historyTable.getTableHeader().setReorderingAllowed(false);
        historyTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        historyTable.setDefaultEditor(Object.class, null);
        historyTable.setCellSelectionEnabled(false);
        historyTable.setRowSelectionAllowed(false);
        historyTable.setColumnSelectionAllowed(false);
        
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
        historyTablePanel.add(scrollPane);
        historyTablePanel.setBackground(new Color(101, 133, 135));
        historyTablePanel.setBorder(new LineBorder(new Color(101, 133, 135), 2));
        
        panelHistory.setVisible(false);
        
    }

    
    
    public void initializeAccountPanel() {
        panelAccount = new JPanel();
        panelAccount.setBounds(WIDTH / 5, 50, WIDTH - (WIDTH / 5), HEIGHT);
        panelAccount.setLayout(null);
        
        // company information name
        JLabel labelCompanyName = new JLabel("Bagtas Retail Company Ltd.");
        labelCompanyName.setBounds(200, 150, 300, 60);
        labelCompanyName.setFont(new Font("Poppins", Font.BOLD, 18));
        labelCompanyName.setForeground(new Color(71, 71, 71));
        labelCompanyName.setHorizontalAlignment(SwingConstants.CENTER);
        panelAccount.add(labelCompanyName);
        
        // company information address
        JLabel labelCompanyAddress = new JLabel("123, J.P. Rizal St., Abucay, Bataan");
        labelCompanyAddress.setBounds(200, 190, 300, 60);
        labelCompanyAddress.setFont(new Font("Poppins", Font.BOLD, 18));
        labelCompanyAddress.setForeground(new Color(71, 71, 71));
        labelCompanyAddress.setHorizontalAlignment(SwingConstants.CENTER);
        panelAccount.add(labelCompanyAddress);
        
        // company information contact no.
        JLabel labelCompanyContacts = new JLabel("0909 123 4567");
        labelCompanyContacts.setBounds(200, 230, 300, 60);
        labelCompanyContacts.setFont(new Font("Poppins", Font.BOLD, 18));
        labelCompanyContacts.setForeground(new Color(71, 71, 71));
        labelCompanyContacts.setHorizontalAlignment(SwingConstants.CENTER);
        panelAccount.add(labelCompanyContacts);
        
        // company information contact no.
        JLabel labelCompanyEmail = new JLabel("bagtas1024@company.com");
        labelCompanyEmail.setBounds(200, 270, 300, 60);
        labelCompanyEmail.setFont(new Font("Poppins", Font.BOLD, 18));
        labelCompanyEmail.setForeground(new Color(71, 71, 71));
        labelCompanyEmail.setHorizontalAlignment(SwingConstants.CENTER);
        panelAccount.add(labelCompanyEmail);
        
        // profile icon
        ImageIcon profImageFile = new ImageIcon(getClass().getResource("images/bagtas_profile.png"));
        Image profImage = profImageFile.getImage();
        Image scaledImg = profImage.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        ImageIcon profIcon = new ImageIcon(scaledImg);
        JLabel profHandle = new JLabel(profIcon);
        profHandle.setBounds(330, 50, 60, 60);
        panelAccount.add(profHandle);
        
        // background image
        ImageIcon bgImageFile = new ImageIcon(getClass().getResource("images/account_background.jpg"));
        Image bgImage = bgImageFile.getImage();
        Image scaledImage = bgImage.getScaledInstance(720, 550, Image.SCALE_SMOOTH);
        ImageIcon background = new ImageIcon(scaledImage);
        JLabel bgHandle = new JLabel(background);
        bgHandle.setBounds(0, 0, 720, 550);
        panelAccount.add(bgHandle);
        
        
        
    }
    
    
    
    public void initializeAboutPanel() {
        panelAbout = new JPanel();
        panelAbout.add(new JLabel("About Content"));
    }
    
    
    
    
    
    
    
    
    // METHODS FOR LOGIN AND REGISTER
    
    public void authenticateLogin() {
        String companyID = fieldCompanyID.getText();
        String password = new String(fieldPassword.getPassword());
        
        if (accounts.containsKey(companyID)) {
            if (accounts.get(companyID).equals(password)) {                
                // login success
                labelMessage.setText("");
                panelBackground.setVisible(false);
                panelLogin.setVisible(false);
                panelNavigation.setVisible(true);
                panelHeader.setVisible(true);
                panelDashboard.setVisible(true);
                setSelectedNavButton(dashboardNavButton);
                loadCompanyData(companyID);
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
        String companyID = fieldCompanyID.getText();
        String password = new String(fieldPassword.getPassword());
        
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
        // ilalagay sa hashmap yung content ng accounts.txt
        loadAccounts();
    }
    
    
    public void loadAccounts() {
        
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
                        writer.write(entry.getKey() + "," + entry.getValue() + "\n");
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
        int lineNumber = 1;
        try (BufferedReader reader = new BufferedReader(new FileReader("database/"+compID.toLowerCase()+"database.txt"))) {
            String line = reader.readLine();
            
            headerText = line;
            // transfer each dashboard data from txt to their respective variables
            String[] dashboardData = reader.readLine().split(",");
            numSoldProducts = Integer.parseInt(dashboardData[0].trim());
            revenue = Double.parseDouble(dashboardData[1].trim());

            // transfer each daily profit data from txt to their respective variables
            String[] profitData = reader.readLine().split(",");
            for (String profit : profitData) {
                double value = Double.parseDouble(profit.trim());
                dailyProfitData.add(value);
            }
            System.out.println();

            reader.readLine(); // skip header
            // transfer each product data from txt to Product
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                String[] data = line.split(",");
                String name = data[0].trim();
                String category = data[1].trim();
                double costPrice = Double.parseDouble(data[2].trim());
                double sellingPrice = Double.parseDouble(data[3].trim());
                int quantity = Integer.parseInt(data[4].trim());

                // make product object
                addProductRow(name, category, costPrice, sellingPrice, quantity);
                Product product = new Product(name, category, costPrice, sellingPrice, quantity);
                productList.add(product);
                inStock += 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
    
    
    
    
    
    // METHODS FOR INVENTORY MODIFICATIONS AND HISTORY ACTIONS

    public void addProductRow(String product, String category, double costPrice, double sellingPrice, int quantity) {
        Object[] rowData = {product, category, costPrice, sellingPrice, quantity};
        tableModel.addRow(rowData);
    }
    
    
    
    public void addHistoryRow(String action, int row) {
        String name = String.valueOf(productTable.getValueAt(row, 0));
        String category = String.valueOf(productTable.getValueAt(row, 1));
        String cost = String.valueOf(productTable.getValueAt(row, 2));
        String price = String.valueOf(productTable.getValueAt(row, 3));
        String quantity = String.valueOf(productTable.getValueAt(row, 4));
        Object[] rowData = {action, name, category, cost, price, quantity};
        
        // change color according to action
        historyTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Check action column (assuming action column is the first column)
                String action = (String) table.getValueAt(row, 0);

                // Set row color based on action
                if ("ADD".equals(action)) {
                    c.setBackground(Color.BLUE);
                } else if ("SOLD".equals(action)) {
                    c.setBackground(Color.GREEN);
                } else if ("UPDATE".equals(action)) {
                    c.setBackground(Color.YELLOW);
                } else if ("DELETE".equals(action)) {
                    c.setBackground(Color.RED);
                } else {
                    c.setBackground(Color.WHITE); // Default color for other actions
                }

                return c;
            }
        });

        
        // add the history action into table
        historyTableModel.insertRow(0, rowData);
    }
    
    
    
    public void addProduct() {
        // get the product details from text field
        DefaultTableModel model = (DefaultTableModel) productTable.getModel();
        String name = fieldDetailProduct.getText();
        String category = fieldDetailCategory.getText();
        String cost = fieldDetailCost.getText();
        String price = fieldDetailPrice.getText();
        String quantity = fieldDetailQuantity.getText();
        model.addRow(new Object[]{name, category, cost, price, quantity});

        inStock += Integer.parseInt(quantity);;
        labelInStockValue.setText(String.valueOf(inStock));
        
        historyTableModel.insertRow(0, new Object[]{"ADD", name, category, cost, price, quantity});
    }
    
    
    
    public void soldProduct() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow != -1) {
            String quantityStr = JOptionPane.showInputDialog("Enter quantity sold:");
            try {
                int quantitySold = Integer.parseInt(quantityStr);
                int currentQuantity = Integer.parseInt(productTable.getValueAt(selectedRow, 4).toString());
                double productPrice = (double) productTable.getValueAt(selectedRow, 3);
                if (quantitySold > currentQuantity) {
                    JOptionPane.showMessageDialog(null, "Quantity sold cannot be greater than available quantity.");
                } else {
                    int newQuantity = currentQuantity - quantitySold;
                    productTable.setValueAt(String.valueOf(newQuantity), selectedRow, 4);
                    numSoldProducts += quantitySold;
                    revenue += quantitySold * productPrice;
                    inStock -= quantitySold;
                    labelRevenueValue.setText("$"+String.valueOf(revenue));
                    labelProductSoldValue.setText(String.valueOf(numSoldProducts));
                    labelInStockValue.setText(String.valueOf(inStock));
                    
                    addHistoryRow("SOLD", selectedRow);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number for quantity sold.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "No product selected");
        }
    }
    
    
    
    public void updateProduct() {
        int selectedRow = productTable.getSelectedRow();
        String productName = productTable.getValueAt(selectedRow, 0).toString();
        int prev = Integer.parseInt(productTable.getValueAt(selectedRow, 4).toString());
        if (selectedRow != -1) {
            productTable.setValueAt(fieldDetailProduct.getText(), selectedRow, 0);
            productTable.setValueAt(fieldDetailCategory.getText(), selectedRow, 1);
            productTable.setValueAt(fieldDetailCost.getText(), selectedRow, 2);
            productTable.setValueAt(fieldDetailPrice.getText(), selectedRow, 3);
            productTable.setValueAt(fieldDetailQuantity.getText(), selectedRow, 4);
        } else {
            JOptionPane.showMessageDialog(null, "No product selected");
        }

        for (Product product : productList) {
            if (product.getName().equals(productName)) {
                product.setName(fieldDetailProduct.getText());
                product.setCategory(fieldDetailCategory.getText());
                product.setCost(Double.parseDouble(fieldDetailCost.getText()));
                product.setPrice(Double.parseDouble(fieldDetailPrice.getText()));
                product.setQuantity(Integer.parseInt(fieldDetailQuantity.getText()));
            }
        }

        int quantity = Integer.parseInt(fieldDetailQuantity.getText());
        inStock += (quantity-prev);
        labelInStockValue.setText(String.valueOf(inStock));
        
        addHistoryRow("UPDATE", selectedRow);
    }
    
    
    
    public void deleteProduct() {
        
        int selectedRow = productTable.getSelectedRow();
        String removedName = productTable.getValueAt(selectedRow, 0).toString();
        int removedProducts = Integer.parseInt(productTable.getValueAt(selectedRow, 4).toString());
        if (selectedRow != -1) {
            DefaultTableModel model = (DefaultTableModel) productTable.getModel();
            model.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(null, "No product selected");
        }
        inStock -= removedProducts;
        labelInStockValue.setText(String.valueOf(inStock));

        for (Product product : productList) {
            if (product.getName().equals(removedName)) {
                productList.remove(product);
            }
        }
        addHistoryRow("DELETE", selectedRow);
    }
    
    
    
    
    
    
    
   
    // METHODS FOR COSTUMIZED NAVIGATION BUTTONS
    
    private void setSelectedNavButton(JButton selectedButton) {
        JButton[] buttons = {dashboardNavButton, inventoryNavButton, historyNavButton, accountNavButton, aboutNavButton};
        for (JButton button : buttons) {
            if (button == selectedButton) {
                button.setForeground(loginPanelBgColor); // Change text color to indicate selected
                button.setOpaque(true); // Ensure button is opaque to show background color
                button.setBackground(new Color(137, 179, 167)); // Use highlight color for background
            } else {
                button.setForeground(textColor); // Reset text color for non-selected buttons
                button.setOpaque(false); // Ensure non-selected buttons are not opaque
            }
        }
    }
    
    
    
    private JButton createModernButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFont(new Font("Poppins", Font.BOLD, 17));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding for spacing
        button.setForeground(textColor);
        button.addActionListener(this); // Add action listener to handle button clicks
        return button;
    }
    
    
    
    
    
    
    
    
    // METHODS FOR ACTIONLISTENER AND LISTSELECTIONLISTENER
    
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
            setSelectedNavButton(dashboardNavButton);
        } else if (e.getSource() == inventoryNavButton) {
            panelDashboard.setVisible(false);
            panelInventory.setVisible(true);
            panelHistory.setVisible(false);
            panelAbout.setVisible(false);
            panelAccount.setVisible(false);
            setSelectedNavButton(inventoryNavButton);
        } else if (e.getSource() == historyNavButton) {
            panelDashboard.setVisible(false);
            panelInventory.setVisible(false);
            panelHistory.setVisible(true);
            panelAbout.setVisible(false);
            panelAccount.setVisible(false);
            setSelectedNavButton(historyNavButton);
        } else if (e.getSource() == aboutNavButton) {
            panelDashboard.setVisible(false);
            panelInventory.setVisible(false);
            panelHistory.setVisible(false);
            panelAbout.setVisible(true);
            panelAccount.setVisible(false);
            setSelectedNavButton(aboutNavButton);
        } else if (e.getSource() == accountNavButton) {
            panelDashboard.setVisible(false);
            panelInventory.setVisible(false);
            panelHistory.setVisible(false);
            panelAbout.setVisible(false);
            panelAccount.setVisible(true);
            setSelectedNavButton(accountNavButton);
        } else if (e.getSource() == addButton) {
            addProduct();
        } else if (e.getSource() == soldButton) {
            soldProduct();
        } else if (e.getSource() == updateButton) {
            updateProduct();
        } else if (e.getSource() == deleteButton) {
            deleteProduct();
        }
    }
    
    
    
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow != -1) {
                // retrieve data from the selected row
                String productName = productTable.getValueAt(selectedRow, 0).toString();
                String category = productTable.getValueAt(selectedRow, 1).toString();
                String cost = productTable.getValueAt(selectedRow, 2).toString();
                String price = productTable.getValueAt(selectedRow, 3).toString();
                String quantity = productTable.getValueAt(selectedRow, 4).toString();

                // set the retrieved data into your text fields
                fieldDetailProduct.setText(productName);
                fieldDetailCategory.setText(category);
                fieldDetailCost.setText(cost);
                fieldDetailPrice.setText(price);
                fieldDetailQuantity.setText(quantity);
            }
        }
    }
    
    public static void main(String[] args) {
        new Main();
    }
}
