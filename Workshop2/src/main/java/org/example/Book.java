package org.example;

public class Book {
    private int id;
    private String isnb;
    private String title;
    private boolean isCheckedOut;
    private String checkedOutTo;

    public Book(int id, String isnb, String title, boolean isCheckedOut, String checkedOutTo) {
        this.id = id;
        this.isnb = isnb;
        this.title = title;
        this.isCheckedOut = isCheckedOut;
        this.checkedOutTo = checkedOutTo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsnb() {
        return isnb;
    }

    public void setIsnb(String isnb) {
        this.isnb = isnb;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCheckedOut() {
        return isCheckedOut;
    }

    public void setCheckedOut(boolean checkedOut) {
        isCheckedOut = checkedOut;
    }

    public String getCheckedOutTo() {
        return checkedOutTo;
    }

    public void checkedOut(String name){
        if (!isCheckedOut){
            isCheckedOut = true;
            checkedOutTo = name;
        }else{
            System.out.println("Book has been checked out");
        }
    }
    public void checkedIn(){
        isCheckedOut = false;
        checkedOutTo = "";
    }
}
