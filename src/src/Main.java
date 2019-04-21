package src;

import com.sun.xml.internal.messaging.saaj.soap.JpegDataContentHandler;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.util.ListIterator;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeListener;

/**
 * A dialog for tracking the price of an item.
 *
 * @author Yoonsik Cheon
 */
@SuppressWarnings("serial")
public class Main extends JFrame {

    private WatchList watchList = new WatchList();
    private String itemName = "LED Monitor";
    private String URL = "http://www.bestbuy.com/site/samsun-ue90-series-28-led-4k-uhd-moniotr-black/5484022.p?skuId=5484022";
    private double maxPrice = 369.99;
    private double minPrice = 61.67;
    private double itemChange;
    private String itemDate = "08/25/2018";
    private JList createJList;


    /** Default dimension of the dialog. */
    private final static Dimension DEFAULT_SIZE = new Dimension(400, 300);

    /** Special panel to display the watched item. */
    private ItemView itemView;

    private DefaultListModel defaultListModel;

    /** Message bar to display various messages. */
    private JLabel msgBar = new JLabel(" ");

    /** Create a new dialog. */
    public Main() {
        this(DEFAULT_SIZE);
    }

    /** Create a new dialog of the given screen dimension. */
    public Main(Dimension dim) {
        super("Price Watcher");
        setSize(dim);
        configureUI();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        //setResizable(false);
        showMessage("Welcome!");
        pack();
    }

    /** Callback to be invoked when the refresh button is clicked.
     * Find the current price of the watched item and display it
     * along with a percentage price change. */
    private void refreshButtonClicked(ActionEvent event) {
        //--
        //-- WRITE YOUR CODE HERE!
        //--
        itemView.getItem().setPreviousPrice(itemView.getItem().getItemPrice());
        itemView.getItem().setItemPrice(itemView.getItem().getRandomPrice());
        itemView.getItem().setItemChange();
        super.repaint();
        showMessage("Refresh clicked!");
    }

    /** Callback to be invoked when the view-page icon is clicked.
     * Launch a (default) web browser by supplying the URL of
     * the item. */
    private void viewPageClicked() {
        //--
        //-- WRITE YOUR CODE HERE!
        //--
        try{
            Desktop d = Desktop.getDesktop();
            d.browse(new URI(itemView.getItem().getURL()));
        }catch(Exception e){
            e.printStackTrace();
        }

        showMessage("View clicked!");
    }

    /** Configure UI. */
    private void configureUI() {
        setLayout(new BorderLayout());
        watchList.add(createItem(itemName,URL,maxPrice,minPrice,itemDate));
        watchList.add(createItem(itemName,URL,maxPrice,minPrice,itemDate));
        watchList.add(createItem(itemName,URL,maxPrice,minPrice,itemDate));
        watchList.add(createItem(itemName,URL,maxPrice,minPrice,itemDate));
        watchList.add(createItem(itemName,URL,maxPrice,minPrice,itemDate));
        watchList.add(createItem(itemName,URL,maxPrice,minPrice,itemDate));
        watchList.add(createItem(itemName,URL,maxPrice,minPrice,itemDate));

        defaultListModel = createListModel();
        createJList = new JList<>(defaultListModel);
        createJList.setCellRenderer(new ItemView());
        JScrollPane creatScroll = new JScrollPane(createJList);
        JPanel control = makeControlPanel();
        control.setBorder(BorderFactory.createEmptyBorder(10,16,0,16));
        add(control, BorderLayout.NORTH);
        JPanel board = new JPanel();
        board.add(creatScroll);
        board.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10,16,0,16),
                BorderFactory.createLineBorder(Color.GRAY)));
        board.setLayout(new GridLayout(1,1));
        itemView = new ItemView();
        itemView.setClickListener(this::viewPageClicked);
        //board.add(itemView);
        add(board, BorderLayout.CENTER);
        msgBar.setBorder(BorderFactory.createEmptyBorder(10,16,10,0));
        add(msgBar, BorderLayout.SOUTH);
    }

    /** Create a control panel consisting of a refresh button. */
    private JPanel makeControlPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JToolBar buttonBar = new JToolBar();

        JButton butRefresh = new JButton();
        butRefresh.setIcon(new ImageIcon("src/image/refresh3.png"));
        buttonBar.add(butRefresh);


        JButton butAdd = new JButton();
        butAdd.setIcon(new ImageIcon("src/image/add.png"));
        buttonBar.add(butAdd);

        JButton butSearch = new JButton();
        butSearch.setIcon(new ImageIcon("src/image/search-icon1.png"));
        buttonBar.add(butSearch);

        JButton butSelFirst = new JButton();
        butSelFirst.setIcon(new ImageIcon("src/image/firstItem.png"));
        butSelFirst.addActionListener(new butSelFirstListener());
        buttonBar.add(butSelFirst);

        JButton butSelLast = new JButton();
        butSelLast.setIcon(new ImageIcon("src/image/lastItem.png"));
        buttonBar.add(butSelLast);
        buttonBar.addSeparator();

        JButton butCheckSingle = new JButton();
        butCheckSingle.setIcon(new ImageIcon("src/image/blueRefresh.png"));
        buttonBar.add(butCheckSingle);
        butCheckSingle.addActionListener(new butCheckSingleListener());

        JButton butViewPage = new JButton();
        butViewPage.setIcon(new ImageIcon("src/image/URL.png"));
        buttonBar.add(butViewPage);

        JButton butEdit = new JButton();
        butEdit.setIcon(new ImageIcon("src/image/edit.png"));
        buttonBar.add(butEdit);

        JButton butDeleteSel = new JButton();
        butDeleteSel.setIcon(new ImageIcon("src/image/delete.png"));
        buttonBar.add(butDeleteSel);
        buttonBar.addSeparator();

        panel.add(buttonBar, BorderLayout.CENTER);

        JMenuBar dropBar = new JMenuBar();

        JMenu app = new JMenu("App");
        JMenu item = new JMenu("Item");
        JMenu sort = new JMenu("Sort");
        JMenu select = new JMenu("Selected");

        dropBar.add(app);
        dropBar.add(item);
        dropBar.add(sort);
        //app dropdown
        JMenuItem dropExit = new JMenuItem("Exit");
        app.add(dropExit);
        //item drop down
        JMenuItem dropCheckPrice = new JMenuItem("Check Price", new ImageIcon("src/image/refresh1.png"));
        item.add(dropCheckPrice);
        JMenuItem dropAddItem = new JMenuItem("Add", new ImageIcon("src/image/add1.png"));
        item.add(dropAddItem);
        dropAddItem.addActionListener(new dropAddItemListener());
        item.addSeparator();
        JMenuItem dropSearch = new JMenuItem("Search", new ImageIcon("src/image/Search-icon2.png"));
        item.add(dropSearch);
        JMenuItem dropSearchFirst = new JMenuItem("First Item", new ImageIcon("src/image/firstItem1.png"));
        item.add(dropSearchFirst);
        JMenuItem dropSearchLast = new JMenuItem("Last Item", new ImageIcon("src/image/lastItem1.png"));
        item.add(dropSearchLast);
        item.addSeparator();
        //select drop down
        item.add(select);
        JMenuItem dropSelPrice = new JMenuItem("Price", new ImageIcon("src/image/refresh1.png"));
        select.add(dropSelPrice);
        JMenuItem dropSelView = new JMenuItem("View Webpage", new ImageIcon("src/image/URL1.png"));
        select.add(dropSelView);
        JMenuItem dropSelEdit = new JMenuItem("Edit", new ImageIcon("src/image/edit1.png"));
        select.add(dropSelEdit);
        JMenuItem dropSelRemove = new JMenuItem("Remove", new ImageIcon("src/image/delete1.png"));
        select.add(dropSelRemove);
        select.addSeparator();
        JMenuItem dropSelCopyName = new JMenuItem("Copy Name");
        select.add(dropSelCopyName);
        JMenuItem dropSelCopyUrl = new JMenuItem("Copy URL");
        select.add(dropSelCopyUrl);
        JMenuItem dropSelCopyItem = new JMenuItem("Copy Item");
        select.add(dropSelCopyItem);
        //radio buttons
        ButtonGroup radioGroup = new ButtonGroup();
        JRadioButtonMenuItem dropOldestAdded = new JRadioButtonMenuItem("Oldest Added");
        radioGroup.add(dropOldestAdded);
        sort.add(dropOldestAdded);
        JRadioButtonMenuItem dropNewestAdded = new JRadioButtonMenuItem("Newest Added");
        radioGroup.add(dropNewestAdded);
        sort.add(dropNewestAdded);
        sort.addSeparator();
        JRadioButtonMenuItem dropNameAsc = new JRadioButtonMenuItem("Name Ascending");
        radioGroup.add(dropNameAsc);
        sort.add(dropNameAsc);
        JRadioButtonMenuItem dropNameDesc = new JRadioButtonMenuItem("Name Descending");
        radioGroup.add(dropNameDesc);
        sort.add(dropNameDesc);
        sort.addSeparator();
        JRadioButtonMenuItem dropPriceChange = new JRadioButtonMenuItem("Price Change (%)");
        radioGroup.add(dropPriceChange);
        sort.add(dropPriceChange);
        JRadioButtonMenuItem dropPriceHigh = new JRadioButtonMenuItem("Price High ($)");
        radioGroup.add(dropPriceHigh);
        sort.add(dropPriceHigh);
        JRadioButtonMenuItem dropPriceLow = new JRadioButtonMenuItem("Price Low ($)");
        radioGroup.add(dropPriceLow);
        sort.add(dropPriceLow);

        panel.add(dropBar, BorderLayout.NORTH);
        return panel;
    }


    /**
     * Show briefly the given string in the message bar.
     * @param msg
     */
    private void showMessage(String msg) {
        msgBar.setText(msg);
        new Thread(() -> {
            try {
                Thread.sleep(3 * 1000); // 3 seconds
            } catch (InterruptedException e) {
            }
            if (msg.equals(msgBar.getText())) {
                SwingUtilities.invokeLater(() -> msgBar.setText(" "));
            }
        }).start();
    }


    //method to populate watchList
    public Item createItem(String itemName, String URL, Double maxPrice, Double minPrice, String itemDate){
        Item testItem = new Item(itemName, URL, maxPrice,
        minPrice, itemDate);

        return testItem;
    }

    /**
     * @return list
     */
    public DefaultListModel createListModel(){
        DefaultListModel list = new DefaultListModel<>();
        watchList.getHolder().forEach((iter) -> {
            list.addElement(iter);
        });
        return list;
    }

    public static void main(String[] args) {
        new Main();

    }

    private class AboutListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, "      " +
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

   // question mark button
    private class butInfoListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            JOptionPane.showMessageDialog(null, "      " +
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    //add new item
    private class dropAddItemListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            JOptionPane testOption = new JOptionPane();

            JTextField nameField = new JTextField(10);
            JTextField urlField = new JTextField(10);
            JTextField priceField = new JTextField(10);
            JTextField dateField = new JTextField(10);

            JPanel namePanel = new JPanel();
            JPanel URLPanel = new JPanel();
            JPanel pricePanel = new JPanel();
            JPanel datePanel = new JPanel();
            JPanel outer = new JPanel();

            outer.setLayout(new BoxLayout(outer, BoxLayout.Y_AXIS));

            namePanel.add(new JLabel("Name:\t"));
            namePanel.add(nameField);
            namePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            URLPanel.add(new JLabel("URL:\t"), urlField);
            URLPanel.add(urlField);
            URLPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            pricePanel.add(new JLabel("Price:\t"));
            pricePanel.add(priceField);
            pricePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            datePanel.add(new JLabel("Date Added:\t"));
            datePanel.add(dateField);
            datePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            outer.add(namePanel);
            outer.add(URLPanel);
            outer.add(pricePanel);
            outer.add(datePanel);

            testOption.showConfirmDialog(null, outer, "Add Item", JOptionPane.OK_CANCEL_OPTION);

            String name = nameField.getText();
            String URL = urlField.getText();
            double price =  Double.parseDouble(priceField.getText());
            String date = dateField.getText();

            Item addItem = new Item(name, URL, price, 0, date);

            defaultListModel.addElement(addItem);
        }
    }

    private class butCheckSingleListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            if(createJList.getSelectedIndex() > -1) {
                Item refreshItem;
                refreshItem = (Item) defaultListModel.get(createJList.getSelectedIndex());
                refreshItem.setPreviousPrice(refreshItem.getItemPrice());
                refreshItem.setItemPrice(refreshItem.getRandomPrice());
                refreshItem.setItemChange();
                repaint();
            }
        }
    }


    private class butSelFirstListener implements ActionListener {

        public void actionPerformed(ActionEvent e){


        }

    }




}

