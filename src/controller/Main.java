package controller;


import database.EnderecoDAO;
import model.Animal;
import model.DonoCliente;
import model.Endereco;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    static Scanner scan = new Scanner(System.in);
    static Controller sistema;

    static {
        try {
            sistema = Controller.getInstance();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void dono() throws SQLException {
        System.out.println("Digite o nome:");
        String nome = scan.nextLine();
        System.out.println("Digite o cpf");
        int cpf = Integer.parseInt(scan.nextLine());
        System.out.println("Digite o telefone");
        int telefone = Integer.parseInt(scan.nextLine());

        sistema.insertDono(new DonoCliente(cpf,nome,telefone,endereco()));

    }

    public static void animal() throws SQLException {
        System.out.println("Digite o nome:");
        String nome = scan.nextLine();
        System.out.println("Digite a descricao:");
        String descricao = scan.nextLine();

        sistema.insertAnimal(new Animal(0,nome,descricao));
    }

    public static Endereco endereco() throws SQLException {
        Scanner c = new Scanner(System.in);
        System.out.println("Digite o bairro:");
        String bairro = c.nextLine();
        System.out.println("Digite o cep:");
        int cep = Integer.parseInt(scan.nextLine());
        System.out.println("Digite o estado:");
        String estado = c.nextLine();
        System.out.println("Digite a cidade:");
        String cidade = c.nextLine();
        System.out.println("Digite a rua:");
        String rua = c.nextLine();
        System.out.println("Digite o numero:");
        int numero = Integer.parseInt(scan.nextLine());

        Endereco g = new Endereco(0,estado,rua,numero,bairro,cidade,cep);

        sistema.insertEndereco(g);

        return sistema.getAllEndereco().get(sistema.getAllEndereco().size()-1);

    }

    public static void showAnimais() throws SQLException {
        for(Animal x : sistema.getAllAnimal())
            System.out.println(x);
    }

    public static void showAnimaisDono() throws SQLException {
        Map<DonoCliente, List<Animal>> map = sistema.getAllAnimalWithDono();
        for (DonoCliente x : map.keySet()) {
            System.out.println(x);
            for(Animal y : map.get(x)){
                System.out.println("\t"+y);
            }
        }
    }

    public static void showDonos() throws SQLException {
        for(DonoCliente x: sistema.getAllDono())
            System.out.println(x);
    }

    public static void showEnderecos() throws SQLException {
        for(Endereco x: sistema.getAllEndereco())
            System.out.println(x);
    }

    public static void showMenu(){
        System.out.println("0 - Sair\n" +
                "1 - Adicioanr Dono\n" +
                "2 - Adicionar Animal\n" +
                "3 - Atribuir Animal a Dono\n" +
                "4 - Mostrar Animais\n" +
                "5 - Mostrar Donos e Animais\n" +
                "6 - Mostrar Donos\n" +
                "7 - Mostrar Endereços");
    }

    public static void atribuirDonoAnimal() throws SQLException {
        for(DonoCliente x : sistema.getAllDono())
            System.out.println(x);
        System.out.println("Qual o cpf do dono que deseja utilizar?");
        int cpf = Integer.parseInt(scan.nextLine());
        DonoCliente tempDono = sistema.selectDono(cpf);

        for(Animal x : sistema.getAllAnimal())
            System.out.println(x);
        System.out.println("Qual o id do Animal que deseja atribuir?");
        int id = Integer.parseInt(scan.nextLine());
        Animal tempAnimal = sistema.selectAnimal(id);

        sistema.insertDonoAnimal(tempDono,tempAnimal);
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

         loop:
        while (true) {

            System.out.println("O que deseja fazer?");
            showMenu();
            int op = Integer.parseInt(scan.nextLine());

            switch (op){
                case 0:
                    break loop;
                case 1:
                    dono();
                    break;
                case 2:
                    animal();
                    break;
                case 3:
                    atribuirDonoAnimal();
                    break;
                case 4:
                    showAnimais();
                    break;
                case 5:
                    showAnimaisDono();
                    break;
                case 6:
                    showDonos();
                    break;
                case 7:
                    showEnderecos();
                    break;
                default:
                    System.out.println("Opção Inválida");
                    break;

            }

        }
    }

}
