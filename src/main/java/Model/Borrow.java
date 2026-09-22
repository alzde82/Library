package Model;
import java.time.LocalDate;
import lombok.*;
import java.util.Date;
@Getter
@ToString
@NoArgsConstructor
public class Borrow implements InterfaceModel {
    private int userId;
    private String username;
    private  int bookId;
    private String bookName;
    private LocalDate borrowedDate;
    private LocalDate returnedDate;
    private double penalty;

    public Borrow(int userId,String username,int bookId, String bookName,LocalDate borrowedDate){
        this.userId=userId;
        this.username=username;
        this.bookId=bookId;
        this.bookName=bookName;
        this.borrowedDate=borrowedDate;

    }
public void setReturnedDate(LocalDate returnedDate){
    this.returnedDate=returnedDate;
}
public void setPenalty(double penalty){
    this.penalty=penalty;
}
}






