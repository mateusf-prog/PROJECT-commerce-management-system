
## Projeto - Gerenciador de comércios
#### *ainda em desenvolvimento...*

O sistema web deverá permitir que os comerciantes gerenciem suas vendas, estoques e pagamentos e clientes. Integração com uma API (mock) para simular um pagamento via boleto ou PIX e enviar esse pagamento para o email do cliente.

#### Requisitos:
- O comerciante terá seu cadastro (login e senha).
- Incluir, alterar, consultar cadastro de seus clientes.
- Incluir, alterar, consultar histórico de vendas.
- Consultar o histórico de pagamentos.
- Cadastrar, alterar, consultar, deletar itens do estoque.
- Aplicar filtros para pesquisa de estoque (código ou nome).
- Fazer a persistência no banco de dados.
- Gerar e processar pagamentos dos clientes (boleto e pix).
- Aplicar filtros para pesquisa de clientes, pagamentos (filtrar por nome, CPF).
- Integrar uma API (mock), para gerar um boleto ou código PIX e enviar para o cliente via email.

### Diagrama de classes
<img src="/uml-class.png">


