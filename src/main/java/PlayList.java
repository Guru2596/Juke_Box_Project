public class PlayList {
    private String users_Id;
    private int Sno;
    private String Name;

    public PlayList(String users_Id, int sno, String name) {
        this.users_Id = users_Id;
        Sno = sno;
        Name = name;
    }

    public String getUsers_Id() {
        return users_Id;
    }

    public int getSno() {
        return Sno;
    }

    public String getName() {
        return Name;
    }

    @Override
    public String toString() {
        return users_Id+"\t\t"+Sno+"\t\t"+Name;
    }

}
