package br.com.ismyburguer.controlepedido.gateway.out;

import br.com.ismyburguer.controlepedido.entity.ControlePedido;

import java.util.List;

public interface ListarControlePedidoRepository {

    List<ControlePedido> listar();

}
