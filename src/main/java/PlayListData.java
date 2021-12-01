public class PlayListData {
    private int SrP_Id;
    private int Sno;
    private String Name;
    private String Item_Name;

    public PlayListData(int srP_Id, int sno, String name, String item_Name) {
        SrP_Id = srP_Id;
        Sno = sno;
        Name = name;
        Item_Name = item_Name;
    }

    public int getSrP_Id() {
        return SrP_Id;
    }

    public int getSno() {
        return Sno;
    }

    public String getName() {
        return Name;
    }

    public String getItem_Name() {
        return Item_Name;
    }

    @Override
    public String toString() {
        return SrP_Id+"\t\t"+Sno+"\t\t"+Name+"\t\t"+Item_Name;
    }
}
