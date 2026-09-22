import JdbcConnection.*;

import java.lang.reflect.Field;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Map.BookMapper;
import Map.BorrowMapper;
import Map.RowMapper;
import Map.UsersMapper;
import Model.*;
import ORM.InsertORM;
import Repository.*;
import Validation.SqlValidation;


public class Main {
    public static  void main(String [] args){
        SqlValidation v = new SqlValidation();
        Book book = new Book(0,"java","javasing",13.2,3,true);
        Users user= new Users(2,"ali","alizade","2283653711","alzde82","!1Aaaaaaaa",false);
        Borrow borrow = new Borrow(1,"test",1,"Masnavi", LocalDate.of(1,1,1));
        Admin admin= new Admin();
        BookRepository br1= new BookRepository();
        BorrowRepository br= new BorrowRepository();
        AdminRepository ar = new AdminRepository();

        InsertORM <Borrow>orm= new InsertORM<>();

        UserRepository ur= new UserRepository();
        BookMapper bm1= new BookMapper();
        UsersMapper um= new UsersMapper();
        BorrowMapper bm= new BorrowMapper();
        br.create(borrow);
        ar.create(admin);
        ur.create(user);
        br1.create(book);
        List<Book> list=br1.readBy(book,"price",bm1);
        for (Book l: list){
            System.out.println(l.toString());
        }

//        List<Users> list1=ur.readAll(user,um);
//        List<Book> list2=br1.readAll(book,bm);


    }


}
