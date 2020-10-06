package com.ibm.fb.lab1;

@FunctionalInterface
interface Resolve {
    void resolve();
}

@FunctionalInterface
interface Reject {
    void reject();
}

public class Login {

    public String username;
    public String password;

    public Login(String uname,String pwd){
        username = uname;
        password = pwd;
    }

    public static void main(String[] args) {

        Resolve resolve = null;
        //refactoring -1 : if body has only one line of code. we can remove{}
        resolve = () -> System.out.println("Login success");

        Reject reject = null;
        //refactoring -1 : if body has only one line of code. we can remove{}
        reject = () -> System.out.println("Login failed");

        Login l = new Login("admin","admin");

        l.validate(resolve, reject);


    }

    public void validate(Resolve resolve, Reject reject){

        if ("admin".equals(this.username) && "admin".equals(this.password))
            resolve.resolve();
        else
            reject.reject();
    }
}
