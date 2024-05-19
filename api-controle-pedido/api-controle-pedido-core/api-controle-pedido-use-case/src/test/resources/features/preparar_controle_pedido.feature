Feature: Preparar Controle de Pedido

  Scenario: Preparar controle de pedido com sucesso
    Given que o controle do pedido está disponível
    When eu preparo o controle do pedido
    Then o status do controle do pedido deve ser alterado para "EM_PREPARACAO"
    And o status do pedido deve ser alterado para "EM_PREPARACAO"

  Scenario: Preparar controle de pedido com falha na validação
    Given que o controle do pedido está disponível mas inválido
    When eu tento preparar o controle do pedido
    Then uma exceção de validação deve ser lançada
    And o status do pedido não deve ser alterado
