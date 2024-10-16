package br.ufal.ic.p2.myfood;

import br.ufal.ic.p2.myfood.managers.*;
import br.ufal.ic.p2.myfood.models.*;
import br.ufal.ic.p2.myfood.repositories.CompanyRepository;
import br.ufal.ic.p2.myfood.repositories.OrderRepository;
import br.ufal.ic.p2.myfood.repositories.ProductRepository;
import br.ufal.ic.p2.myfood.repositories.UserRepository;
import br.ufal.ic.p2.myfood.utils.Validators;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class Facade {
    MainManager mainManager = new MainManager();
    UserManager userManager = new UserManager();
    CompanyManager companyManager = new CompanyManager();
    ProductManager productManager = new ProductManager();
    OrderManager orderManager = new OrderManager();

    public void zerarSistema() throws IOException {
        mainManager.cleanRepository();
    }

    public String getAtributoUsuario(int id, String nome) {
        return userManager.getUserAttribute(id, nome);
    }

    public void criarUsuario(String nome,
                             String email,
                             String senha,
                             String endereco) {
        userManager.createCustomer(nome, email, senha, endereco);
    }

    public void criarUsuario(String nome,
                             String email,
                             String senha,
                             String endereco,
                             String cpf) {
        userManager.createOwner(nome, email, senha, endereco, cpf);
    }

    public int login(String email, String senha) {
        return userManager.login(email, senha);
    }

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String tipoCozinha) {
        return companyManager.createCompany(tipoEmpresa, dono, nome, endereco, tipoCozinha);
    }

    public String getEmpresasDoUsuario(int idDono) {
        return companyManager.listCompanyByOwnerId(idDono);
    }

    public int getIdEmpresa(int idDono, String nome, String indice) {
        return companyManager.getCompanyId(idDono, nome, indice);
    }

    public String getAtributoEmpresa(int empresa, String atributo) {
        return companyManager.getCompanyAttribute(empresa, atributo);
    }

    public int criarProduto(int empresa, String nome, float valor, String categoria) {
        return productManager.createProduct(empresa, nome, valor, categoria);
    }

    public void editarProduto(int produto, String nome, float valor, String categoria) {
        productManager.updateProduct(produto, nome, valor, categoria);
    }

    public String getProduto(String nome, int empresa, String atributo) {
        return productManager.getProduct(nome, empresa, atributo);
    }

    public String listarProdutos(int empresa) {
        return productManager.listProducts(empresa);
    }

    public int criarPedido(int cliente, int empresa) {
        return orderManager.createOrder(cliente, empresa);
    }

    public void adicionarProduto(int numero, int produto) {
        orderManager.addProductToOrder(numero, produto);
    }

    public String getPedidos(int numero, String atributo) {
        return orderManager.getOrders(numero, atributo);
    }

    public void fecharPedido(int numero) {
        orderManager.closeOrder(numero);
    }

    public void removerProduto(int pedido, String produto) {
        orderManager.removeProductFromOrder(pedido, produto);
    }

    public int getNumeroPedido(int cliente, int empresa, int indice) {
        return orderManager.getOrderId(cliente, empresa, indice);
    }

    public void encerrarSistema() throws IOException {
        mainManager.saveRepository();
    }
}
