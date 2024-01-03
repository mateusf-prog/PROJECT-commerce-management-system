
## Projeto - Gerenciador de comércios
#### *ainda em desenvolvimento...*

O sistema web deverá permitir que os comerciantes gerenciem suas vendas, estoques e pagamentos e clientes. Integração com uma API (mock) para simular um pagamento via boleto ou PIX e enviar esse pagamento para o email do cliente.

#### Requisitos:
- O comerciante terá seu cadastro (login e senha).
- Incluir, alterar, consultar cadastro de seus clientes. 
- Incluir e consultar histórico de pedidos.
- Consultar o histórico e status de pagamentos.
- CRUD produtos do estoque.
- Aplicar filtros para pesquisa de estoque (código ou nome).
- Gerar e processar pagamentos dos clientes (boleto e pix).
- Aplicar filtros para pesquisa de clientes, pagamentos (filtrar por nome, CPF).
- Integrar uma API de pagamentos, para gerar um boleto ou código PIX e enviar para o cliente via email.

  Observações:
- Este projeto tem como objetivo principal a prática e o estudo, sendo passível de conter eventuais erros. Comprometo-me a corrigi-los e a aprimorar o código, buscando mantê-lo o mais limpo possível.
- O token Asaas salvo no application.properties é um token referente a uma conta somente de testes, em caso de produção o token não pode ficar explícito desta forma. Fiz somente por questão de didática e teste.
- Os dados do import.sql são gerados pelo site 4devs para fins de estudo e testes.