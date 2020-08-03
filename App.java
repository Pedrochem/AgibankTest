import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;



public class App {

    public static void processFile(File f, String fileName) throws FileNotFoundException {
        Scanner file = new Scanner(f);

        HashMap<String, Vendedor> vendedores = new HashMap<String, Vendedor>();
        List<Cliente> clientes = new ArrayList<Cliente>();
        List<Venda> vendas = new ArrayList<Venda>();

        while (file.hasNextLine()) {
            String line = file.nextLine();
            String[] campos = line.split("ç");
            switch (campos[0]) {
                case ("001"):
                    Vendedor vendedor = new Vendedor(campos[1], campos[2], Double.parseDouble(campos[3]));
                    vendedores.put(vendedor.getName(), vendedor);
                    break;

                case ("002"):
                    Cliente cliente = new Cliente(campos[1], campos[2], campos[3]);
                    clientes.add(cliente);
                    break;

                case ("003"):
                    String idVenda = campos[1];
                    String nomeVendedor = campos[3];
                    Venda venda = new Venda(idVenda, vendedores.get(nomeVendedor));

                    String[] itens = campos[2].substring(1, campos[2].length() - 1).split(",");

                    for (String item : itens) {
                        String[] dadosItem = item.split("-");
                        Item i = new Item(dadosItem[0], Integer.parseInt(dadosItem[1]), Double.parseDouble(dadosItem[2]));
                        venda.addItem(i);
                    }
                    vendas.add(venda);
            }
        }

        Vendedor piorVendedor = null;
        Venda maiorVenda = null;

        for (Venda v : vendas)
            maiorVenda = ((maiorVenda == null || v.getVendaPrice() > maiorVenda.getVendaPrice()) ? v : maiorVenda);
        for (Vendedor v : vendedores.values())
            piorVendedor = ((piorVendedor == null || v.getTotalVendas() < piorVendedor.getTotalVendas()) ? v : piorVendedor);

        writeNewFile(clientes.size(),vendedores.size(),maiorVenda,piorVendedor,fileName);
    }

    private static void writeNewFile(int qtdClientes, int qtdVendedores, Venda maiorVenda, Vendedor piorVendedor, String fileName) throws FileNotFoundException{

        System.out.println("Entrei aqui"+fileName);
        PrintWriter writer = new PrintWriter(System.getProperty("user.home")+File.separator+"data"+File.separator+"out"+File.separator+"out_"+fileName);

        writer.println("Quantidade de clientes: " + qtdClientes);
        writer.println("Quantidade de vendedores: " + qtdVendedores);
        writer.println("Id da venda mais cara: " + maiorVenda.getId());
        writer.println("Pior vendedor: " + piorVendedor.getName());
        writer.close();

    }

    public static void wait(int s){
        try{
            TimeUnit.SECONDS.sleep(s);    
        }
        catch(InterruptedException ex){
            Thread.currentThread().interrupt();
        }
    }


    public static void main(String[] args) throws FileNotFoundException {
        String homepath = System.getProperty("user.home");
        HashSet<File> arqs = new HashSet<>();
        int i=0;
        while (true){
            arqs.addAll(Arrays.asList(new File(homepath+File.separator+"data"+File.separator+"in").listFiles()));
            File[] f  = arqs.toArray(new File[arqs.size()]);
            try {
                processFile(f[i],f[i++].getName());
                System.out.println("File added");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Waiting for new files...");
                wait(10); //Time for folder scanning in seconds
            }
        }
        
    }
}

