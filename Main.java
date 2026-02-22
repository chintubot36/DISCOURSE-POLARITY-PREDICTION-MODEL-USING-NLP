import com.review.service.ReviewService;
import com.shoppingcartnlp.model.Product;
import java.util.*;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ReviewService service = new ReviewService();

        while (true) {
            System.out.println("\n=== Review Management System ===");
            System.out.println("1. Add Product");
            System.out.println("2. Add Review");
            System.out.println("3. View Products");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter category: ");
                    String category = sc.nextLine();
                    System.out.print("Enter brand: ");
                    String brand = sc.nextLine();
                    service.addProduct(name, category, brand);
                    break;

                case 2:
                    System.out.print("Enter product ID: ");
                    int pid = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter review: ");
                    String review = sc.nextLine();
                    service.addReview(pid, review);
                    break;

                case 3:
                    List<Product> products = service.getAllProducts();
                    if (products.isEmpty()) {
                        System.out.println("❌ No products available!");
                    } else {
                        System.out.println("Available Products:");
                        for (Product p : products) {
                            System.out.println(p);
                        }
                    }
                    break;

                case 4:
                    System.out.println("👋 Exiting...");
                    sc.close();
                    System.exit(0);

                default:
                    System.out.println("❌ Invalid choice!");
            }
        }
    }
}
