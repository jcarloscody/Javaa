package com.github.jcarloscody.service.implementacao;

import com.github.jcarloscody.domain.entity.Cliente;
import com.github.jcarloscody.domain.entity.ItemPedido;
import com.github.jcarloscody.domain.entity.Pedido;
import com.github.jcarloscody.domain.entity.Produto;
import com.github.jcarloscody.domain.repository.Clientes;
import com.github.jcarloscody.domain.repository.ItemsPedido;
import com.github.jcarloscody.domain.repository.Pedidos;
import com.github.jcarloscody.domain.repository.Produtos;
import com.github.jcarloscody.exception.RegraNegocioException;
import com.github.jcarloscody.rest.dto.ItemPedidoDTO;
import com.github.jcarloscody.rest.dto.PedidoDTO;
import com.github.jcarloscody.service.PedidoServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor //do lombok, gera um construtor com todos os argumentos obrigatorios(os args obrigatorios tem final)
public class PedidoServiceImplementacao implements PedidoServiceInterface {

    private final Pedidos repositoryPedidos;
    private final Clientes repositoryClientes;
    private final Produtos repositoryProdutos;
    private final ItemsPedido itemsPedidoRepository;

    @Override
    @Transactional  //garante salvar tudo ou nada
    public Pedido salvar(PedidoDTO pedidoDTO) {
        Integer idCliente = pedidoDTO.getCliente();
        Cliente cliente = repositoryClientes.findById(idCliente).orElseThrow(()->
            new RegraNegocioException("CODIGO DO CLIENTE INVÁLIDO!")
        );

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setDatePedido(LocalDate.now());
        pedido.setTotal(pedido.getTotal());
        repositoryPedidos.save(pedido);

        List<ItemPedido> itemPedidos = converterItens(pedido, pedidoDTO.getListaItemPedidoDTO());
        itemsPedidoRepository.saveAll(itemPedidos);

        pedido.setItens(itemPedidos);

        return pedido;
    }


    private List<ItemPedido> converterItens(Pedido pedido, List<ItemPedidoDTO> itensDTO){
        if (itensDTO.isEmpty()){
            throw new RegraNegocioException("Não é possível realizar um pedido sem itens!");
        }

        return itensDTO
                .stream()
                .map(itemDTO -> {
                    Integer idProduto = itemDTO.getProduto();
                    Produto produto = repositoryProdutos.findById(idProduto).orElseThrow(()->
                            new RegraNegocioException("CODIGO DO CLIENTE INVÁLIDO!" + idProduto));

                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(itemDTO.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    return itemPedido;

                }).collect(Collectors.toList());
    }
}
