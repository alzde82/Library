package Model;
import lombok.*;
@Getter
@Setter
@ToString
@AllArgsConstructor
public class Book implements InterfaceModel{
    private  int bookId;
    private String name;
    private String authorName;
    private double price;
    private int quantity;
    private boolean isAvailable;

//    public Book(String name, String authorName, double price, int quantity, boolean isAvailable) {
//        this.name = name;
//        this.authorName = authorName;
//        this.price = price;
//        this.quantity = quantity;
//        this.isAvailable = isAvailable;
//    }
}
