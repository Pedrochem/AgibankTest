import java.util.ArrayList;
import java.util.List;

public class Venda{
    private String id;
    private List<Item> itens;
    private Vendedor vendedor;

    public Venda(String id,Vendedor vendedor) {
        this.id = id;
        this.vendedor = vendedor;
        this.itens = new ArrayList<Item>();
    }


    public double getVendaPrice(){
        return itens.stream().mapToDouble(Item::getValorTotal).sum();
    }

    public void addItem(Item item){
        itens.add(item);
        vendedor.addVenda(item.getValorTotal());
    }

    public List<Item> getItens() {
        return itens;
    }

    public void setItens(List<Item> itens) {
        this.itens = itens;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
