public class Item {
    private String id;
    private int qtd;
    private double price;
    private double valorTotal;

    public Item(String id, int qtd, double price) {
        this.id = id;
        this.qtd = qtd;
        this.price = price;
        this.valorTotal = price*qtd;
    }

    public double getValorTotal(){
        return valorTotal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getQtd() {
        return qtd;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
