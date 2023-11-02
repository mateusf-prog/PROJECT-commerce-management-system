
## Projeto - Gerenciador de comércios
#### *ainda em desenvolvimento...*

O sistema web deverá permitir que os comerciantes gerenciem suas vendas, estoques e pagamentos e clientes. Integração com uma API (mock) para simular um pagamento via boleto ou PIX e enviar esse pagamento para o email do cliente.

#### Requisitos:
- O comerciante terá seu cadastro (login e senha).
- Incluir, alterar, consultar cadastro de seus clientes.
- Incluir e consultar histórico de pedidos.
- Consultar o histórico e status de pagamentos.
- Cadastrar, alterar, consultar, deletar itens do estoque.
- Aplicar filtros para pesquisa de estoque (código ou nome).
- Fazer a persistência no banco de dados.
- Gerar e processar pagamentos dos clientes (boleto e pix).
- Aplicar filtros para pesquisa de clientes, pagamentos (filtrar por nome, CPF).
- Integrar uma API de paganentos, para gerar um boleto ou código PIX e enviar para o cliente via email.

  OBS:Este projeto tem como objetivo principal a prática e o estudo, sendo passível de conter eventuais erros. Comprometo-me a corrigi-los e a aprimorar o código, buscando mantê-lo o mais limpo possível.

### Camada modelo:
<img src="/uml-class.png">

### Camada serviço:
<img src="/service-layer.png">
