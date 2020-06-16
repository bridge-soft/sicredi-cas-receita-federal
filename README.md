# sicredi-cas-receita-federal
Para executar, basta abrir o proj. em alguma IDE, ex: STS (Spring Tool Suite) e rodar via: Run As / Java Application.


## algumas considerações:
```
1 - A IDE em que o código for executado, deve estar com encoding UTF-8.
2 - A Pasta "data\in" e "data\out" devem existir.  Ex: C:\Users\meu_usuario\data\in e data\out"
3 - Conforme solicitado, a extensão dos arquivos dentro da pasta "data\in" devem ser ".cvs" e o separador ";".
4 - O arquivo ".cvs" de entrada, deve estar no seguinte formato:
```

#### Dados da Agência
```
Os dados da agência possuem o identificador 001 e seguem o seguinte formato:
001;Nro.Agência;Nome do Gerente;Salário
```

#### Dados do cliente
```
Os dados do cliente possuem o identificador 002 e seguem o seguinte formato:
002;Nro.Agência;Nome do Cliente;Área de atuação
```

#### Dados de venda
```
Os dados de venda possuem o identificador 003 e seguem o seguinte formato:
003;Id da Venda;[Item ID-Item Quantity-Item Price];Nome do Gerente que vendeu 
```

Segue um exemplo de arquivo a ser processado pela receita, o mesmo segue cópia em anexo arquivo: achutti.cvs
```
001;12225-1;Pedro;50000
001;12225-2;Paulo;40000.99
001;12225-1;João;40000.99
002;12225-2;Jose da Silva;Rural
002;12225-3;Eduardo Pereira;Rural
003;10;[1-10-100,2-30-2.50,3-40-3.10];Pedro
003;08;[1-34-10,2-33-1.50,3-40-0.10];Paulo
```

na pasta data\in, podem ser adicionados diversos arquivos de nomes distintos, onde serão processados automaticamente, tendo o resultado
exposto na pasta data\out ... a implementação é um listener da "Receita Federal", que fica escutando a pasta data\in, e processando
cada arquivo que é adicionado nela.
