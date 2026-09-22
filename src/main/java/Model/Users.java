package Model;
import lombok.*;
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Users implements InterfaceModel {
    private int userId ;
    private String firstname;
    private String lastname;
    private String nationalCode;
    private String username;
    private String password;
    private boolean isActive;





}











