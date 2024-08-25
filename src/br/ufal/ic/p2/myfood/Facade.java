package br.ufal.ic.p2.myfood;

import br.ufal.ic.p2.myfood.exceptions.UniqueFieldException;
import br.ufal.ic.p2.myfood.models.*;
import br.ufal.ic.p2.myfood.repositories.CompanyRepository;
import br.ufal.ic.p2.myfood.repositories.OrderRepository;
import br.ufal.ic.p2.myfood.repositories.ProductRepository;
import br.ufal.ic.p2.myfood.repositories.UserRepository;
import br.ufal.ic.p2.myfood.utils.Validator;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class Facade {
    UserRepository userRepository = UserRepository.getInstance();
    CompanyRepository companyRepository = CompanyRepository.getInstance();
    ProductRepository productRepository = ProductRepository.getInstance();
    OrderRepository orderRepository = OrderRepository.getInstance();

    public void zerarSistema() throws IOException {
        userRepository.clean();
        companyRepository.clean();
        productRepository.clean();
        orderRepository.clean();
    }

    public String getAtributoUsuario(int id, String nome) {
        Optional<User> userOptional = userRepository.getById(id);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("Usuario nao cadastrado.");
        }
        User user = userOptional.get();

        return user.getAttribute(nome);
    }

    public void criarUsuario(String nome,
                             String email,
                             String senha,
                             String endereco) {
        Customer customer = Customer.create(nome, email, senha, endereco);

        try {
            userRepository.add(customer);
        } catch (UniqueFieldException e) {
            if (e.getField().getName().equals("email")) {
                throw new RuntimeException("Conta com esse email ja existe");
            }
        }
    }

    public void criarUsuario(String nome,
                             String email,
                             String senha,
                             String endereco,
                             String cpf) {
        Owner owner = Owner.create(nome, email, senha, endereco, cpf);

        try {
            userRepository.add(owner);
        } catch (UniqueFieldException e) {
            throw new RuntimeException("Conta com esse email ja existe");
        }
    }

    public int login(String email, String senha) {
        Optional<User> userOptional = userRepository.getByEmail(email);

        if (userOptional.isEmpty()) {
            throw new RuntimeException("Login ou senha invalidos");
        }

        User user = userOptional.get();

        boolean loginSuccessful = user.login(email, senha);

        if (!loginSuccessful) {
            throw new RuntimeException("Login ou senha invalidos");
        }

        return user.getId();
    }

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String tipoCozinha) {
        if (tipoEmpresa.equals("restaurante")) {
            Optional<User> userOptional = userRepository.getById(dono);

            if (userOptional.isEmpty()) {
                throw new RuntimeException();
            }

            User user = userOptional.get();

            if (!(user instanceof Owner)) {
                throw new RuntimeException("Usuario nao pode criar uma empresa");
            }

            Optional<Company> sameNameRestaurantOptional = companyRepository.getByName(nome);

            if (sameNameRestaurantOptional.isPresent()) {
                Company sameNameRestaurant = sameNameRestaurantOptional.get();

                if (sameNameRestaurant.getOwnerId() != dono) {
                    throw new RuntimeException("Empresa com esse nome ja existe");
                }

                if (sameNameRestaurant.getAddress().equals(endereco)) {
                    throw new RuntimeException("Proibido cadastrar duas empresas com o mesmo nome e local");
                }
            }

            Restaurant restaurant = Restaurant.create(nome, endereco, tipoCozinha, dono);
            companyRepository.add(restaurant);

            return restaurant.getId();
        }

        return -1;
    }

    public String getEmpresasDoUsuario(int idDono) {
        Optional<User> userOptional = userRepository.getById(idDono);

        if (userOptional.isEmpty()) {
            throw new RuntimeException();
        }

        User user = userOptional.get();

        if (!(user instanceof Owner)) {
            throw new RuntimeException("Usuario nao pode criar uma empresa");
        }

        List<Company> companies = companyRepository.listByOwnerId(idDono);

        // TODO: Do this String format in a better way
        return "{" + companies.toString() + "}";
    }

    public int getIdEmpresa(int idDono, String nome, String indice) {
        int index;

        try {
            index = Integer.parseInt(indice);
        } catch (RuntimeException e) {
            throw new RuntimeException("Indice invalido");
        }

        if (Validator.isNullOrEmpty(nome)) {
            throw new RuntimeException("Nome invalido");
        }

        List<Company> companies = companyRepository.listByOwnerId(idDono);
        List<Company> companiesWithSearchedName = companies.stream()
                .filter(c -> c.getName().equals(nome))
                .toList();

        if (companiesWithSearchedName.isEmpty()) {
            throw new RuntimeException("Nao existe empresa com esse nome");
        }

        Company company;
        try {
            company = companiesWithSearchedName.get(index);
        } catch(IndexOutOfBoundsException e) {
            throw new RuntimeException("Indice maior que o esperado");
        }

        return company.getId();
    }

    public String getAtributoEmpresa(int empresa, String atributo) {
        Optional<Company> companyOptional = companyRepository.getById(empresa);
        if (companyOptional.isEmpty()) {
            throw new RuntimeException("Empresa nao cadastrada");
        }
        Company company = companyOptional.get();

        if (atributo == null) {
            return "Atributo invalido";
        }

        if (Validator.isNullOrEmpty(atributo)) {
            throw new RuntimeException("Atributo invalido");
        }

        String attribute = company.getAttribute(atributo);

        if (attribute == null) {
            throw new RuntimeException("Atributo invalido");
        }

        return attribute;
    }

    public int criarProduto(int empresa, String nome, float valor, String categoria) {
        Product product = Product.create(nome, valor, categoria, empresa);

        Optional<Product> sameNameProduct = productRepository.list()
                .stream()
                .filter(p -> p.getCompanyId() == empresa)
                .filter(p -> p.getName().equals(nome))
                .findFirst();

        if (sameNameProduct.isPresent()) {
            throw new RuntimeException("Ja existe um produto com esse nome para essa empresa");
        }

        productRepository.add(product);

        return product.getId();
    }

    public void editarProduto(int produto, String nome, float valor, String categoria) {
        if (Validator.isNullOrEmpty(nome)) {
            throw new RuntimeException("Nome invalido");
        }

        if (Validator.isNullOrEmpty(categoria)) {
            throw new RuntimeException("Categoria invalido");
        }

        if (valor < 0) {
            throw new RuntimeException("Valor invalido");
        }

        Optional<Product> product = productRepository.list()
                .stream()
                .filter(p -> p.getId() == produto)
                .findFirst();

        if (product.isEmpty()) {
            throw new RuntimeException("Produto nao cadastrado");
        }

        Product productObj = product.get();

        productObj.setName(nome);
        productObj.setPrice(valor);
        productObj.setCategory(categoria);

        try {
            productRepository.update(productObj);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Produto nao cadastrado");
        }
    }

    public String getProduto(String nome, int empresa, String atributo) {
        Optional<Product> productOptional = productRepository.listByCompanyId(empresa)
                .stream()
                .filter(p -> p.getName().equals(nome))
                .findFirst();

        if (productOptional.isEmpty()) {
            throw new RuntimeException("Produto nao encontrado");
        }

        Product product = productOptional.get();

        String productAttribute = product.getAttribute(atributo);

        if (productAttribute == null) {
            throw new RuntimeException("Atributo nao existe");
        }

        return productAttribute;
    }

    public String listarProdutos(int empresa) {
        Optional<Company> company = companyRepository.getById(empresa);

        if (company.isEmpty()) {
            throw new RuntimeException("Empresa nao encontrada");
        }

        List<Product> products = productRepository.listByCompanyId(empresa);

        // TODO: Melhorar o jeito que retorna essa String
        return "{" + products.toString() + "}";
    }

    public int criarPedido(int cliente, int empresa) {
        Optional<User> userOptional = userRepository.getById(cliente);

        if (userOptional.isEmpty()) {
            throw new RuntimeException();
        }
        User user = userOptional.get();

        if (user instanceof Owner) {
            throw new RuntimeException("Dono de empresa nao pode fazer um pedido");
        }

        List<Order> companyOrders = orderRepository.listByCompanyId(empresa);

        Optional<Order> existentOrder = companyOrders.stream()
                .filter(o -> o.getCustomerId() == cliente)
                .findFirst();

        if (existentOrder.isPresent()) {
            throw new RuntimeException("Nao e permitido ter dois pedidos em aberto para a mesma empresa");
        }

        Order order = Order.create(cliente, empresa);
        orderRepository.add(order);

        return order.getId();
    }

    public void adcionarProduto(int numero, int produto) {
        Optional<Order> orderOptional = orderRepository.getById(numero);

        if (orderOptional.isEmpty()) {
            throw new RuntimeException("Nao existe pedido em aberto");
        }
        Order order = orderOptional.get();

        List<Product> productList = productRepository
                .listByCompanyId(order.getCompanyId());

        Optional<Product> productOptional = productList
                .stream()
                .filter(p -> p.getId() == produto)
                .findFirst();

        if (productOptional.isEmpty()) {
            throw new RuntimeException("O produto nao pertence a essa empresa");
        }
        Product product = productOptional.get();

        order.getProductList().add(product);
    }

    public void encerrarSistema() throws IOException {
        userRepository.save();
        companyRepository.save();
        productRepository.save();
        orderRepository.save();
    }
}
