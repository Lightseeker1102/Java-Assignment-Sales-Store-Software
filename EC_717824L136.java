package assignment;
import java.util.*;
import java.text.SimpleDateFormat;
	
	class ProductTaxCategory {
	    private String categoryName;
	    private double taxRate;
	    
	    public ProductTaxCategory(String categoryName, double taxRate) {
	        this.categoryName = categoryName;
	        setTaxRate(taxRate);
	    }
	    
	    // Getters and Setters with validation
	    public String getCategoryName() { return categoryName; }
	    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
	    
	    public double getTaxRate() { return taxRate; }
	    public void setTaxRate(double taxRate) {
	        if (taxRate < 0) throw new IllegalArgumentException("Tax rate cannot be negative");
	        this.taxRate = taxRate;
	    }
	    
	    @Override
	    public String toString() {
	        return String.format("%s (%.1f%%)", categoryName, taxRate);
	    }
	}

	// 2. Product Class
	class Product {
	    private static int idCounter = 1;
	    
	    private int productId;
	    private String productName;
	    private String description;
	    private double price;
	    private int stockQuantity;
	    private ProductTaxCategory taxCategory;
	    
	    public Product(String productName, String description, double price, int stockQuantity, ProductTaxCategory taxCategory) {
	        this.productId = idCounter++;
	        setProductName(productName);
	        setDescription(description);
	        setPrice(price);
	        setStockQuantity(stockQuantity);
	        this.taxCategory = taxCategory;
	    }
	    
	    // Getters and Setters with validation
	    public int getProductId() { return productId; }
	    
	    public String getProductName() { return productName; }
	    public void setProductName(String productName) {
	        if (productName == null || productName.trim().isEmpty()) {
	            throw new IllegalArgumentException("Product name cannot be empty");
	        }
	        this.productName = productName;
	    }
	    
	    public String getDescription() { return description; }
	    public void setDescription(String description) {
	        this.description = description;
	    }
	    
	    public double getPrice() { return price; }
	    public void setPrice(double price) {
	        if (price < 0) throw new IllegalArgumentException("Price cannot be negative");
	        this.price = price;
	    }
	    
	    public int getStockQuantity() { return stockQuantity; }
	    public void setStockQuantity(int stockQuantity) {
	        if (stockQuantity < 0) throw new IllegalArgumentException("Stock quantity cannot be negative");
	        this.stockQuantity = stockQuantity;
	    }
	    
	    public ProductTaxCategory getTaxCategory() { return taxCategory; }
	    public void setTaxCategory(ProductTaxCategory taxCategory) { this.taxCategory = taxCategory; }
	    
	    // Business methods
	    public boolean hasSufficientStock(int quantity) {
	        return stockQuantity >= quantity;
	    }
	    
	    public void reduceStock(int quantity) {
	        if (!hasSufficientStock(quantity)) {
	            throw new IllegalArgumentException("Insufficient stock");
	        }
	        stockQuantity -= quantity;
	    }
	    
	    public void addStock(int quantity) {
	        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive");
	        stockQuantity += quantity;
	    }
	    
	    public double calculateTaxAmount(int quantity) {
	        return (price * quantity) * (taxCategory.getTaxRate() / 100);
	    }
	    
	    @Override
	    public String toString() {
	        return String.format("ID: %d, Name: %s, Price: ₹%.2f, Stock: %d, Tax: %s", 
	                           productId, productName, price, stockQuantity, taxCategory);
	    }
	}

	// 3. Customer Class (Base Class)
	class Customer {
	    private static int idCounter = 1;
	    
	    private int customerId;
	    private String name;
	    private String email;
	    private String phone;
	    
	    public Customer(String name, String email, String phone) {
	        this.customerId = idCounter++;
	        setName(name);
	        setEmail(email);
	        setPhone(phone);
	    }
	    
	    // Getters and Setters with validation
	    public int getCustomerId() { return customerId; }
	    
	    public String getName() { return name; }
	    public void setName(String name) {
	        if (name == null || name.trim().isEmpty()) {
	            throw new IllegalArgumentException("Customer name cannot be empty");
	        }
	        this.name = name;
	    }
	    
	    public String getEmail() { return email; }
	    public void setEmail(String email) {
	        if (email == null || !email.contains("@")) {
	            throw new IllegalArgumentException("Invalid email format");
	        }
	        this.email = email;
	    }
	    
	    public String getPhone() { return phone; }
	    public void setPhone(String phone) {
	        if (phone == null || phone.trim().isEmpty()) {
	            throw new IllegalArgumentException("Phone cannot be empty");
	        }
	        this.phone = phone;
	    }
	    
	    // Polymorphic method
	    public double calculateDiscount(double amount) {
	        return 0.0; // Regular customers get no discount
	    }
	    
	    // Polymorphic method
	    public void updateRewardPoints(double amount) {
	        // Regular customers don't earn points
	    }
	    
	    // Polymorphic method
	    public int getRewardPoints() {
	        return 0; // Regular customers have no points
	    }
	    
	    @Override
	    public String toString() {
	        return String.format("ID: %d, Name: %s, Email: %s, Phone: %s", 
	                           customerId, name, email, phone);
	    }
	}

	// 4. PremiumCustomer Class (Inherits from Customer)
	class PremiumCustomer extends Customer {
	    private int rewardPoints;
	    
	    public PremiumCustomer(String name, String email, String phone) {
	        super(name, email, phone);
	        this.rewardPoints = 0;
	    }
	    
	    public int getRewardPoints() { return rewardPoints; }
	    
	    @Override
	    public double calculateDiscount(double amount) {
	        if (rewardPoints >= 200) {
	            int pointsToRedeem = (rewardPoints / 100) * 100; // Redeem in multiples of 100
	            double discount = (pointsToRedeem / 100) * 10; // ₹10 per 100 points
	            rewardPoints -= pointsToRedeem;
	            return discount;
	        }
	        return 0.0;
	    }
	    
	    @Override
	    public void updateRewardPoints(double amount) {
	        // Earn 1 point for every ₹100 spent
	        rewardPoints += (int)(amount / 100);
	    }
	    
	    @Override
	    public String toString() {
	        return String.format("%s [Premium], Reward Points: %d", 
	                           super.toString(), rewardPoints);
	    }
	}

	// 5. SaleDetail Class
	class SaleDetail {
	    private Product product;
	    private int quantity;
	    private double unitPrice;
	    
	    public SaleDetail(Product product, int quantity) {
	        this.product = product;
	        setQuantity(quantity);
	        this.unitPrice = product.getPrice();
	    }
	    
	    // Getters
	    public Product getProduct() { return product; }
	    public int getQuantity() { return quantity; }
	    public double getUnitPrice() { return unitPrice; }
	    
	    public void setQuantity(int quantity) {
	        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive");
	        if (!product.hasSufficientStock(quantity)) {
	            throw new IllegalArgumentException("Insufficient stock for product: " + product.getProductName());
	        }
	        this.quantity = quantity;
	    }
	    
	    public double getSubtotal() {
	        return unitPrice * quantity;
	    }
	    
	    public double getTaxAmount() {
	        return product.calculateTaxAmount(quantity);
	    }
	    
	    public double getTotal() {
	        return getSubtotal() + getTaxAmount();
	    }
	    
	    @Override
	    public String toString() {
	        return String.format("%s, Qty: %d, Unit Price: ₹%.2f, Subtotal: ₹%.2f, Tax: ₹%.2f", 
	                           product.getProductName(), quantity, unitPrice, getSubtotal(), getTaxAmount());
	    }
	}

	// 6. Sale Class (Aggregation with SaleDetail)
	class Sale {
	    private static int idCounter = 1;
	    
	    private int saleId;
	    private Customer customer;
	    private Date saleDate;
	    private List<SaleDetail> saleDetails;
	    private double totalAmount;
	    private double discount;
	    private double finalAmount;
	    
	    public Sale(Customer customer) {
	        this.saleId = idCounter++;
	        this.customer = customer;
	        this.saleDate = new Date();
	        this.saleDetails = new ArrayList<>();
	        this.totalAmount = 0;
	        this.discount = 0;
	        this.finalAmount = 0;
	    }
	    
	    // Getters
	    public int getSaleId() { return saleId; }
	    public Customer getCustomer() { return customer; }
	    public Date getSaleDate() { return saleDate; }
	    public List<SaleDetail> getSaleDetails() { return saleDetails; }
	    public double getTotalAmount() { return totalAmount; }
	    public double getDiscount() { return discount; }
	    public double getFinalAmount() { return finalAmount; }
	    
	    public void addSaleDetail(SaleDetail saleDetail) {
	        // Verify stock before adding
	        if (!saleDetail.getProduct().hasSufficientStock(saleDetail.getQuantity())) {
	            throw new IllegalArgumentException("Insufficient stock for product: " + 
	                                             saleDetail.getProduct().getProductName());
	        }
	        saleDetails.add(saleDetail);
	        recalculateTotals();
	    }
	    
	    private void recalculateTotals() {
	        totalAmount = saleDetails.stream().mapToDouble(SaleDetail::getTotal).sum();
	        discount = customer.calculateDiscount(totalAmount);
	        finalAmount = totalAmount - discount;
	        
	        // Update reward points for premium customers
	        customer.updateRewardPoints(finalAmount);
	    }
	    
	    public void processSale() {
	        // Update stock quantities
	        for (SaleDetail detail : saleDetails) {
	            detail.getProduct().reduceStock(detail.getQuantity());
	        }
	    }
	    
	    public void printBill() {
	        System.out.println("\n========== SALE BILL ==========");
	        System.out.println("Sale ID: " + saleId);
	        System.out.println("Date: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(saleDate));
	        System.out.println("Customer: " + customer.getName());
	        System.out.println("Customer Type: " + (customer instanceof PremiumCustomer ? "Premium" : "Regular"));
	        System.out.println("\n--- Products ---");
	        
	        for (SaleDetail detail : saleDetails) {
	            System.out.println(detail);
	        }
	        
	        System.out.println("\n--- Summary ---");
	        System.out.printf("Total Amount: ₹%.2f%n", totalAmount);
	        System.out.printf("Discount: ₹%.2f%n", discount);
	        System.out.printf("Final Amount: ₹%.2f%n", finalAmount);
	        
	        if (customer instanceof PremiumCustomer) {
	            System.out.printf("Updated Reward Points: %d%n", ((PremiumCustomer) customer).getRewardPoints());
	        }
	        System.out.println("================================\n");
	    }
	}

	// 7. PurchaseDetail Class
	class PurchaseDetail {
	    private Product product;
	    private int quantity;
	    private double unitCost;
	    
	    public PurchaseDetail(Product product, int quantity, double unitCost) {
	        this.product = product;
	        setQuantity(quantity);
	        setUnitCost(unitCost);
	    }
	    
	    // Getters and Setters
	    public Product getProduct() { return product; }
	    public int getQuantity() { return quantity; }
	    public double getUnitCost() { return unitCost; }
	    
	    public void setQuantity(int quantity) {
	        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive");
	        this.quantity = quantity;
	    }
	    
	    public void setUnitCost(double unitCost) {
	        if (unitCost < 0) throw new IllegalArgumentException("Unit cost cannot be negative");
	        this.unitCost = unitCost;
	    }
	    
	    public double getTotalCost() {
	        return unitCost * quantity;
	    }
	    
	    @Override
	    public String toString() {
	        return String.format("%s, Qty: %d, Unit Cost: ₹%.2f, Total Cost: ₹%.2f", 
	                           product.getProductName(), quantity, unitCost, getTotalCost());
	    }
	}

	// 8. Purchase Class (Aggregation with PurchaseDetail)
	class Purchase {
	    private static int idCounter = 1;
	    
	    private int purchaseId;
	    private Date purchaseDate;
	    private List<PurchaseDetail> purchaseDetails;
	    private double totalCost;
	    
	    public Purchase() {
	        this.purchaseId = idCounter++;
	        this.purchaseDate = new Date();
	        this.purchaseDetails = new ArrayList<>();
	        this.totalCost = 0;
	    }
	    
	    // Getters
	    public int getPurchaseId() { return purchaseId; }
	    public Date getPurchaseDate() { return purchaseDate; }
	    public List<PurchaseDetail> getPurchaseDetails() { return purchaseDetails; }
	    public double getTotalCost() { return totalCost; }
	    
	    public void addPurchaseDetail(PurchaseDetail purchaseDetail) {
	        purchaseDetails.add(purchaseDetail);
	        recalculateTotal();
	    }
	    
	    private void recalculateTotal() {
	        totalCost = purchaseDetails.stream().mapToDouble(PurchaseDetail::getTotalCost).sum();
	    }
	    
	    public void processPurchase() {
	        // Update stock quantities
	        for (PurchaseDetail detail : purchaseDetails) {
	            detail.getProduct().addStock(detail.getQuantity());
	        }
	    }
	    
	    public void printPurchaseSummary() {
	        System.out.println("\n========== PURCHASE SUMMARY ==========");
	        System.out.println("Purchase ID: " + purchaseId);
	        System.out.println("Date: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(purchaseDate));
	        System.out.println("\n--- Products ---");
	        
	        for (PurchaseDetail detail : purchaseDetails) {
	            System.out.println(detail);
	        }
	        
	        System.out.printf("%nTotal Cost: ₹%.2f%n", totalCost);
	        System.out.println("======================================\n");
	    }
	}

	// Main Application Class
	public class EC_717824L136 {
	    private static Scanner scanner = new Scanner(System.in);
	    private static List<Product> products = new ArrayList<>();
	    private static List<Customer> customers = new ArrayList<>();
	    private static List<ProductTaxCategory> taxCategories = new ArrayList<>();
	    
	    public static void main(String[] args) {
	        initializeTaxCategories();
	        showMainMenu();
	    }
	    
	    private static void initializeTaxCategories() {
	        taxCategories.add(new ProductTaxCategory("Standard", 18.0));
	        taxCategories.add(new ProductTaxCategory("Reduced", 12.0));
	        taxCategories.add(new ProductTaxCategory("Zero", 0.0));
	        taxCategories.add(new ProductTaxCategory("Luxury", 28.0));
	    }
	    
	    private static void showMainMenu() {
	        while (true) {
	            System.out.println("========== INVENTORY & BILLING SYSTEM ==========");
	            System.out.println("1. Add Product");
	            System.out.println("2. Add Customer");
	            System.out.println("3. Make a Purchase (Restock)");
	            System.out.println("4. Make a Sale");
	            System.out.println("5. Display Products");
	            System.out.println("6. Display Customers");
	            System.out.println("7. Exit");
	            System.out.print("Choose an option: ");
	            
	            try {
	                int choice = scanner.nextInt();
	                scanner.nextLine(); // Consume newline
	                
	                switch (choice) {
	                    case 1 -> addProduct();
	                    case 2 -> addCustomer();
	                    case 3 -> makePurchase();
	                    case 4 -> makeSale();
	                    case 5 -> displayProducts();
	                    case 6 -> displayCustomers();
	                    case 7 -> {
	                        System.out.println("Thank you for using the system!");
	                        return;
	                    }
	                    default -> System.out.println("Invalid option! Please try again.");
	                }
	            } catch (InputMismatchException e) {
	                System.out.println("Invalid input! Please enter a number.");
	                scanner.nextLine(); // Clear invalid input
	            } catch (Exception e) {
	                System.out.println("Error: " + e.getMessage());
	            }
	        }
	    }
	    
	    private static void addProduct() {
	        try {
	            System.out.println("\n--- Add New Product ---");
	            System.out.print("Product Name: ");
	            String name = scanner.nextLine();
	            
	            System.out.print("Description: ");
	            String description = scanner.nextLine();
	            
	            System.out.print("Price: ₹");
	            double price = scanner.nextDouble();
	            
	            System.out.print("Initial Stock Quantity: ");
	            int stock = scanner.nextInt();
	            scanner.nextLine(); // Consume newline
	            
	            // Display tax categories
	            System.out.println("\nAvailable Tax Categories:");
	            for (int i = 0; i < taxCategories.size(); i++) {
	                System.out.println((i + 1) + ". " + taxCategories.get(i));
	            }
	            System.out.print("Select tax category (1-" + taxCategories.size() + "): ");
	            int taxChoice = scanner.nextInt() - 1;
	            scanner.nextLine(); // Consume newline
	            
	            if (taxChoice < 0 || taxChoice >= taxCategories.size()) {
	                System.out.println("Invalid tax category selection!");
	                return;
	            }
	            
	            ProductTaxCategory taxCategory = taxCategories.get(taxChoice);
	            Product product = new Product(name, description, price, stock, taxCategory);
	            products.add(product);
	            
	            System.out.println("Product added successfully!");
	            System.out.println(product);
	            
	        } catch (Exception e) {
	            System.out.println("Error adding product: " + e.getMessage());
	            scanner.nextLine(); // Clear any remaining input
	        }
	    }
	    
	    private static void addCustomer() {
	        try {
	            System.out.println("\n--- Add New Customer ---");
	            System.out.print("Customer Name: ");
	            String name = scanner.nextLine();
	            
	            System.out.print("Email: ");
	            String email = scanner.nextLine();
	            
	            System.out.print("Phone: ");
	            String phone = scanner.nextLine();
	            
	            System.out.print("Is this a premium customer? (y/n): ");
	            String isPremium = scanner.nextLine();
	            
	            Customer customer;
	            if (isPremium.equalsIgnoreCase("y")) {
	                customer = new PremiumCustomer(name, email, phone);
	            } else {
	                customer = new Customer(name, email, phone);
	            }
	            
	            customers.add(customer);
	            System.out.println("Customer added successfully!");
	            System.out.println(customer);
	            
	        } catch (Exception e) {
	            System.out.println("Error adding customer: " + e.getMessage());
	        }
	    }
	    
	    private static void makePurchase() {
	        if (products.isEmpty()) {
	            System.out.println("No products available! Please add products first.");
	            return;
	        }
	        
	        try {
	            Purchase purchase = new Purchase();
	            boolean addingMore = true;
	            
	            while (addingMore) {
	                displayProducts();
	                System.out.print("Select product by ID: ");
	                int productId = scanner.nextInt();
	                System.out.print("Quantity to purchase: ");
	                int quantity = scanner.nextInt();
	                System.out.print("Unit cost: ₹");
	                double unitCost = scanner.nextDouble();
	                scanner.nextLine(); // Consume newline
	                
	                Product product = findProductById(productId);
	                if (product == null) {
	                    System.out.println("Product not found!");
	                    continue;
	                }
	                
	                PurchaseDetail purchaseDetail = new PurchaseDetail(product, quantity, unitCost);
	                purchase.addPurchaseDetail(purchaseDetail);
	                
	                System.out.print("Add another product to purchase? (y/n): ");
	                String choice = scanner.nextLine();
	                addingMore = choice.equalsIgnoreCase("y");
	            }
	            
	            purchase.processPurchase();
	            purchase.printPurchaseSummary();
	            
	            // Display updated stock
	            displayProducts();
	            
	        } catch (Exception e) {
	            System.out.println("Error processing purchase: " + e.getMessage());
	            scanner.nextLine(); // Clear any remaining input
	        }
	    }
	    
	    private static void makeSale() {
	        if (products.isEmpty() || customers.isEmpty()) {
	            System.out.println("No products or customers available! Please add them first.");
	            return;
	        }
	        
	        try {
	            displayCustomers();
	            System.out.print("Select customer by ID: ");
	            int customerId = scanner.nextInt();
	            scanner.nextLine(); // Consume newline
	            
	            Customer customer = findCustomerById(customerId);
	            if (customer == null) {
	                System.out.println("Customer not found!");
	                return;
	            }
	            
	            Sale sale = new Sale(customer);
	            boolean addingMore = true;
	            
	            while (addingMore) {
	                displayProducts();
	                System.out.print("Select product by ID: ");
	                int productId = scanner.nextInt();
	                System.out.print("Quantity to sell: ");
	                int quantity = scanner.nextInt();
	                scanner.nextLine(); // Consume newline
	                
	                Product product = findProductById(productId);
	                if (product == null) {
	                    System.out.println("Product not found!");
	                    continue;
	                }
	                
	                if (!product.hasSufficientStock(quantity)) {
	                    System.out.println("Insufficient stock! Available: " + product.getStockQuantity());
	                    continue;
	                }
	                
	                SaleDetail saleDetail = new SaleDetail(product, quantity);
	                sale.addSaleDetail(saleDetail);
	                
	                System.out.print("Add another product to sale? (y/n): ");
	                String choice = scanner.nextLine();
	                addingMore = choice.equalsIgnoreCase("y");
	            }
	            
	            sale.processSale();
	            sale.printBill();
	            
	            // Display updated stock
	            displayProducts();
	            
	        } catch (Exception e) {
	            System.out.println("Error processing sale: " + e.getMessage());
	            scanner.nextLine(); // Clear any remaining input
	        }
	    }
	    
	    private static void displayProducts() {
	        System.out.println("\n--- PRODUCT LIST ---");
	        if (products.isEmpty()) {
	            System.out.println("No products available.");
	        } else {
	            for (Product product : products) {
	                System.out.println(product);
	            }
	        }
	        System.out.println();
	    }
	    
	    private static void displayCustomers() {
	        System.out.println("\n--- CUSTOMER LIST ---");
	        if (customers.isEmpty()) {
	            System.out.println("No customers available.");
	        } else {
	            for (Customer customer : customers) {
	                System.out.println(customer);
	            }
	        }
	        System.out.println();
	    }
	    
	    private static Product findProductById(int productId) {
	        return products.stream()
	                      .filter(p -> p.getProductId() == productId)
	                      .findFirst()
	                      .orElse(null);
	    }
	    
	    private static Customer findCustomerById(int customerId) {
	        return customers.stream()
	                       .filter(c -> c.getCustomerId() == customerId)
	                       .findFirst()
	                       .orElse(null);
	    }
	}

