package br.ufal.ic.p2.myfood;

import br.ufal.ic.p2.myfood.managers.*;

import java.io.IOException;

public class Facade {
    MainManager mainManager = new MainManager();
    UserManager userManager = new UserManager();
    CompanyManager companyManager = new CompanyManager();
    ProductManager productManager = new ProductManager();
    OrderManager orderManager = new OrderManager();
    RouteManager routeManager = new RouteManager();

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
        userManager.createUser("customer", nome, email, senha, endereco, null, null, null);
    }

    public void criarUsuario(String nome,
                             String email,
                             String senha,
                             String endereco,
                             String cpf) {
        userManager.createUser("owner", nome, email, senha, endereco, cpf, null, null);
    }

    public void criarUsuario(String nome,
                             String email,
                             String senha,
                             String endereco,
                             String veiculo,
                             String placa) {
        userManager.createUser("delivery", nome, email, senha, endereco, null, veiculo, placa);
    }

    public int login(String email, String senha) {
        return userManager.login(email, senha);
    }

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String tipoCozinha) {
        return companyManager.createCompany(tipoEmpresa, dono, nome, endereco, tipoCozinha, null, null, false, 0);
    }

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, boolean aberto24horas, int numeroFuncionarios) {
        return companyManager.createCompany(tipoEmpresa, dono, nome, endereco, null, null, null,  aberto24horas, numeroFuncionarios);
    }

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String abre, String fecha, String tipoMercado) {
        return companyManager.createCompany(tipoEmpresa, dono, nome, endereco, tipoMercado, abre, fecha, false, 0);
    }

    public String getEmpresas(int entregador) {
        return userManager.listDeliveryCompanies(entregador);
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

    public String getEntregadores(int empresa) {
        return companyManager.getDelivery(empresa);
    }

    public void alterarFuncionamento(int mercado, String abre, String fecha) {
        companyManager.updateMarketTime(mercado, abre, fecha);
    }

    public void cadastrarEntregador(int empresa, int entregador) {
        companyManager.registerDelivery(empresa, entregador);
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

    public void liberarPedido(int numero) {
        orderManager.releaseOrder(numero);
    }

    public int obterPedido(int entregador) {
        return orderManager.getLastOrder(entregador);
    }

    public void removerProduto(int pedido, String produto) {
        orderManager.removeProductFromOrder(pedido, produto);
    }

    public int getNumeroPedido(int cliente, int empresa, int indice) {
        return orderManager.getOrderId(cliente, empresa, indice);
    }

    public int criarEntrega(int pedido, int entregador, String destino) {
        return routeManager.createRoute(pedido, entregador, destino);
    }

    public String getEntrega(int id, String atributo) {
        return routeManager.getRouteAttribute(id, atributo);
    }

    public int getIdEntrega(int pedido) {
        return routeManager.getRouteId(pedido);
    }

    public void entregar(int entrega) {
        routeManager.setRouteDelivered(entrega);
    }

    public void encerrarSistema() throws IOException {
        mainManager.saveRepository();
    }
}
